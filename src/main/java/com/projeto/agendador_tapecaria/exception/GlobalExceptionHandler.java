package com.projeto.agendador_tapecaria.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------- Exceções de regra de negócio ----------

    @ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<RespostaErro> tratarHorarioOcupado(HorarioOcupadoException e,
                                                             HttpServletRequest request) {
        log.warn("Conflito de horário: {}", e.getMessage());
        return montarResposta(HttpStatus.CONFLICT, e.getMessage(), request, null);
    }

    @ExceptionHandler(AgendamentoNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarAgendamentoNaoEncontrado(AgendamentoNaoEncontradoException e,
                                                                       HttpServletRequest request) {
        log.warn("Agendamento não encontrado: {}", e.getMessage());
        return montarResposta(HttpStatus.NOT_FOUND, e.getMessage(), request, null);
    }

    // ---------- Erros de entrada do cliente ----------

    // @Valid falhou no body (campo em branco, nulo, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErro> tratarValidacao(MethodArgumentNotValidException e,
                                                        HttpServletRequest request) {
        List<RespostaErro.CampoInvalido> campos = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new RespostaErro.CampoInvalido(fe.getField(), fe.getDefaultMessage()))
                .toList();

        log.warn("Erro de validação em {}: {}", request.getRequestURI(), campos);
        return montarResposta(HttpStatus.BAD_REQUEST, "Dados inválidos", request, campos);
    }

    // body ausente ou JSON malformado / data em formato errado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErro> tratarCorpoIlegivel(HttpMessageNotReadableException e,
                                                            HttpServletRequest request) {
        log.warn("Corpo da requisição ilegível: {}", e.getMessage());
        return montarResposta(HttpStatus.BAD_REQUEST,
                "Corpo da requisição ausente ou em formato inválido", request, null);
    }

    // parâmetro de URL com tipo errado (ex: dataHoraAgendamento=abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespostaErro> tratarTipoParametroInvalido(MethodArgumentTypeMismatchException e,
                                                                    HttpServletRequest request) {
        log.warn("Parâmetro inválido '{}': {}", e.getName(), e.getValue());
        return montarResposta(HttpStatus.BAD_REQUEST,
                "Valor inválido para o parâmetro '" + e.getName() + "'", request, null);
    }

    // ---------- Rede de segurança ----------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarException(Exception e, HttpServletRequest request) {

        // Exceções do próprio Spring (405, 404, 415, parâmetro ausente...) já trazem o status correto.
        // Sem esse if, elas virariam 500 aqui.
        if (e instanceof org.springframework.web.ErrorResponse erroSpring) {
            return montarResposta(erroSpring.getStatusCode(), erroSpring.getBody().getDetail(), request, null);
        }

        log.error("Erro inesperado em {} {}", request.getMethod(), request.getRequestURI(), e);
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", request, null);
    }

    // ---------- Auxiliar ----------

    private ResponseEntity<RespostaErro> montarResposta(HttpStatusCode status,
                                                        String mensagem,
                                                        HttpServletRequest request,
                                                        List<RespostaErro.CampoInvalido> campos) {
        HttpStatus httpStatus = HttpStatus.resolve(status.value());
        String titulo = httpStatus != null ? httpStatus.getReasonPhrase() : "Erro";

        RespostaErro corpo = new RespostaErro(
                status.value(),
                titulo,
                mensagem,
                request.getRequestURI(),
                LocalDateTime.now(),
                campos);

        return ResponseEntity.status(status).body(corpo);
    }
}
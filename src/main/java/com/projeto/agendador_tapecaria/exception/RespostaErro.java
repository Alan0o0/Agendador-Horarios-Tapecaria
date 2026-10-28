package com.projeto.agendador_tapecaria.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

//Formato único de erro devolvido ao cliente por todos os handlers.

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RespostaErro(
        int status,
        String erro,
        String mensagem,
        String caminho,
        LocalDateTime dataHora,
        List<CampoInvalido> camposInvalidos) {

    public record CampoInvalido(String campo, String mensagem) {
    }
}

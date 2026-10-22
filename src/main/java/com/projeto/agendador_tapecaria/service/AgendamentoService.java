package com.projeto.agendador_tapecaria.service;

import com.projeto.agendador_tapecaria.exception.AgendamentoNaoEncontradoException;
import com.projeto.agendador_tapecaria.exception.HorarioOcupadoException;
import com.projeto.agendador_tapecaria.infrastructure.entity.AgendamentoEntity;
import com.projeto.agendador_tapecaria.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento) {

        LocalDateTime inicioServico = agendamento.getDataHoraAgendamento();
        LocalDateTime fimServico = inicioServico.plusHours(1); // define a duração de cada atendimento

        AgendamentoEntity agendamentoExistente = agendamentoRepository.findByTipoServicoAndDataHoraAgendamentoBetween(
                agendamento.getTipoServico(),
                inicioServico,
                fimServico);

        if (Objects.nonNull(agendamentoExistente)) { // se já existe, o horário está ocupado
            throw new HorarioOcupadoException("Horário já está ocupado");
        }
        return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {
        AgendamentoEntity agendamentoAtual = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agendamentoAtual)) { // se não achou, não há o que alterar
            throw new AgendamentoNaoEncontradoException("Agendamento não encontrado");
        }
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public List<AgendamentoEntity> buscarAgendamentosDia(LocalDate data) {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23, 59, 59);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }

    public AgendamentoEntity alterarAgendamento(AgendamentoEntity agendamento, String cliente, LocalDateTime dataHoraAgendamento) {
        AgendamentoEntity agendamentoAtual = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agendamentoAtual)) { // se não achou, não há o que alterar
            throw new AgendamentoNaoEncontradoException("Agendamento não encontrado");
        }
        agendamento.setId(agendamentoAtual.getId());
        agendamento.setDataInsercao(agendamentoAtual.getDataInsercao());// mantém o id original para o save() atualizar em vez de criar outro
        return agendamentoRepository.save(agendamento);
    }
}

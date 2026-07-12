package com.projeto.agendador_horarios.service;

import com.projeto.agendador_horarios.exception.AgendamentoNaoEncontradoException;
import com.projeto.agendador_horarios.exception.HorarioOcupadoException;
import com.projeto.agendador_horarios.infrastructure.entity.AgendamentoEntity;
import com.projeto.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento){

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);// aqui delimita de qunatas em quantas horas serao os agendamentos

        AgendamentoEntity agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(
                agendamento.getServico(),
                horaAgendamento,
                horaFim );

        if (Objects.nonNull(agendados)){// se esta preenchido n pode salvar nele
            throw new HorarioOcupadoException("Horário já esta ocupado");
        }
        return agendamentoRepository.save(agendamento);

    }

    public void deletarAgendamento (LocalDateTime dataHoraAgendamento,String cliente){
            agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public List<AgendamentoEntity> buscarAgendamentosDia(LocalDate data){
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23,59, 59);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }

    public AgendamentoEntity alterarAgendamento( AgendamentoEntity agendamento, String cliente, LocalDateTime dataHoraAgendamento){
        AgendamentoEntity agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente( dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)){// se esta preenchido n pode salvar nele
            throw new AgendamentoNaoEncontradoException("Agendamento não encontrado");
        }
        agendamento.setId(agenda.getId());//substitui o id antigo pelo novo com as alteracoes
        return agendamentoRepository.save(agendamento);

    }
}

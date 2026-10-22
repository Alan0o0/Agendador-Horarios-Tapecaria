package com.projeto.agendador_tapecaria.infrastructure.repository;

import com.projeto.agendador_tapecaria.infrastructure.entity.AgendamentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {

    AgendamentoEntity findByTipoServicoAndDataHoraAgendamentoBetween(String tipoServico,
                                                                     LocalDateTime dataHoraInicio,
                                                                     LocalDateTime dataHoraFim);

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);

    List<AgendamentoEntity> findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);

    AgendamentoEntity findByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);

}

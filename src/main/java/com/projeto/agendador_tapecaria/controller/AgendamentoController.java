package com.projeto.agendador_tapecaria.controller;

import com.projeto.agendador_tapecaria.infrastructure.entity.AgendamentoEntity;
import com.projeto.agendador_tapecaria.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoEntity> salvarAgendamento(@Valid @RequestBody AgendamentoEntity agendamento) {
        return ResponseEntity.accepted().body(agendamentoService.salvarAgendamento(agendamento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento) {

        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoEntity>> buscarAgendamentosDia(@RequestParam LocalDate data) {
        return ResponseEntity.ok().body(agendamentoService.buscarAgendamentosDia(data));
    }

    @PutMapping
    public ResponseEntity<AgendamentoEntity> alterarAgendamento(@Valid @RequestBody AgendamentoEntity agendamento,
                                                                @RequestParam String cliente,
                                                                @RequestParam LocalDateTime dataHoraAgendamento) {

        return ResponseEntity.accepted()
                .body(agendamentoService.alterarAgendamento(agendamento, cliente, dataHoraAgendamento));
    }

}




package com.projeto.agendador_tapecaria.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agendamento")
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera automaticamente os ids
    private Long id;

    @NotBlank(message = "O tipo de serviço é obrigatório (ex: reforma de sofá, troca de tecido)")
    private String tipoServico;

    @NotBlank(message = "O tapeceiro responsável é obrigatório")
    private String tapeceiro;

    @NotNull(message = "A data e hora do agendamento são obrigatórias")
    private LocalDateTime dataHoraAgendamento;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String cliente;

    @NotBlank(message = "O telefone do cliente é obrigatório")
    private String telefoneCliente;

    private LocalDateTime dataInsercao;

    @PrePersist
    void definirDataInsercao() {
        dataInsercao = LocalDateTime.now();
    }

    private LocalDateTime dataAtualizada;

    @PreUpdate
    void definirDataAtualizada() {
        dataAtualizada = LocalDateTime.now();
    }
}

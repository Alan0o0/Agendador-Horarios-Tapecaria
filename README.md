# Agendador de Serviços de Tapeçaria

CRUD simples para agendar horários, feito para aprendizado. O cliente escolhe o serviço, o tapeceiro, a data e o horário, e o sistema registra o agendamento, impedindo que dois clientes marquem o mesmo horário.

> **Projeto de estudo.** Ele usa o banco H2 em memória, então os dados **não ficam salvos**: tudo é apagado quando a aplicação é reiniciada.

## Funcionalidades

- Agendamento de serviços pelo site (tipo de serviço, tapeceiro, data, horário, nome e telefone do cliente)
- Horários em horas cheias, escolhidos em uma lista (ex.: 14:00)
- Bloqueio de horário já ocupado (retorna erro 409)
- Tratamento centralizado de exceções com mensagens claras para o usuário
- Front-end responsivo, com cores pastel e fontes grandes, pensado para boa leitura por pessoas mais velhas

## Tecnologias

**Back-end**
- Java
- Spring Boot (Maven)
- Banco de dados H2 (em memória)
- Arquitetura em camadas: entity, repository, service e controller

**Front-end**
- HTML, CSS e JavaScript puro (sem frameworks)
- Comunicação com a API via `fetch`

## Banco de dados

O projeto usa o **H2**, um banco que roda dentro da própria aplicação e fica na memória. Isso facilita o desenvolvimento e o aprendizado, porque não precisa instalar nem configurar nada. Em compensação:

- Os agendamentos **somem ao reiniciar** a aplicação.
- Não serve para uso real em produção. Para guardar os dados de verdade, seria preciso trocar o H2 por um banco persistente (como PostgreSQL ou MySQL) ou configurar o H2 em modo arquivo.

## Estrutura do projeto

```
src/main/java/com/projeto/agendador_horarios/
├── controller/     # Endpoints da API
├── service/        # Regras de negócio (AgendamentoService)
├── repository/     # Acesso aos dados
├── entity/         # Entidades
└── exception/      # HorarioOcupadoException,
                    # AgendamentoNaoEncontradoException,
                    # GlobalExceptionHandler

src/main/resources/static/
├── index.html
├── style/tela_inicial.css
└── js/script.js
```

## Como executar

Pré-requisito: JDK instalado. Não é preciso configurar banco de dados.

```bash
# clonar o repositório
git clone https://github.com/Alan0o0/Agendador-Horarios-Tapecaria.git
cd Agendador-Horarios-Tapecaria

# executar
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

Depois, acesse `http://localhost:8081` no navegador.

Se quiser acessar o banco de dados em memória pode acessar `http://localhost:8081/h2-console` com usuario "sa" e sem nenhuma senha

## API

### Criar agendamento

`POST /agendamentos`

```json
{
  "tipoServico": "Reforma de sofá",
  "tapeceiro": "Nome do tapeceiro",
  "dataHoraAgendamento": "2026-10-25T14:00",
  "cliente": "Nome do cliente",
  "telefoneCliente": "(00) 00000-0000"
}
```

| Status | Significado |
|--------|-------------|
| 202 | Agendamento realizado com sucesso |
| 409 | Horário já ocupado |

## Próximos passos

- [ ] Validar no back-end que o horário tem minutos iguais a zero
- [ ] Trocar o H2 em memória por um banco que guarde os dados tipo o postgreSQL ou um MYsql
- [ ] Add tela de login com dashboard para administrar os agendamentos 

## Autor

Alan Benvenuto de Oliveira
Estudante de Ciência da Computação (UNINTER)

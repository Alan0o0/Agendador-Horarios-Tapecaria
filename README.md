# 📅 Agendador de Horários (CRUD)

Sistema de agendamento de horários desenvolvido com **Java 25 + Spring Boot**, utilizando **Spring Data JPA**, **Lombok**, **Maven** e banco de dados em memória **H2**.

Este projeto é um **CRUD completo** para gerenciamento de agendamentos.

---

## 🚀 Sobre o projeto

O **Agendador de Horários** é uma API REST desenvolvida para cadastro e gerenciamento de horários onde pose ser usada tanto pra servicos onde tem nessecidade de agendar um horario fixo podendo mudar e deletar este horario.

O sistema permite realizar operações básicas de um CRUD:

- Criar agendamentos
- Consultar agendamentos
- Atualizar agendamentos
- Remover agendamentos

Foi desenvolvido com foco em prática de backend com Spring Boot e Arquitetura em camadas.

---

## 🛠️ Tecnologias utilizadas

- Java 25
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- Maven
- H2 Database (em memória)

---

## 📌 Funcionalidades (CRUD)

- ➕ Criar agendamento (salvarAgendamento)
- 🔎 Listar todos os agendamentos do dia (buscarAgendamentosDia)
- ✏️ Atualizar agendamento (alterarAgendamentos)
- ❌ Deletar agendamento (deletarAgendamento)

---

## 📡 Endpoints da API

### 📍 Agendamentos

| Método | Endpoint              | Descrição                  |
|--------|----------------------|--------------------------|
| GET    | `/agendamentos?data=`                               | Lista todos os agendamentos do dia |
| POST   | `/agendamentos`                                     | Cria novo agendamento passando os body | 
| PUT    | `/agendamentos?cliente=xxx?dataHoraAgendamento=xxx` | Atualiza agendamento conforme os parametros|
| DELETE | `/agendamentos?cliente=xxx?dataHoraAgendamento=xxx` | Remove agendamento |

```
## 🧱 Estrutura do projeto

src/
└── main/
├── java/
│ └── com.projeto.agendador_horarios/
│ ├── controller/
│ ├── service/
│ └── infrastructure/
│     ├── entity/
│     └── repository/
│
└── resources/
└── application.properties

```
## ⚙️ Como executar o projeto

### 🔧 Pré-requisitos
- Java 25
- Maven

---

### ▶️ Executando localmente

```bash
# Clonar o repositório
git clone https://github.com/Alan0o0/Agendador-Horarios.git

# Entrar na pasta
cd Agendador-Horarios

# Executar o projeto
mvn spring-boot:run

#Sobre o banco de dados

#O projeto utiliza banco de dados em memória H2 então os dados são temporarios todos são perdidos ao reiniciar a aplicacão, pode ser testado com H2 Console ou Postman.

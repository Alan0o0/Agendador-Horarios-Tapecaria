# Agendador de Serviços de Tapeçaria

Aplicação web desenvolvida com Java e Spring Boot para agendamento de serviços de tapeçaria. O projeto permite que clientes escolham o serviço desejado, o tapeceiro, a data e o horário, registrando os agendamentos por meio de uma API REST.

O projeto foi desenvolvido para colocar em prática conceitos de desenvolvimento back-end, integração entre front-end e API, organização em camadas e tratamento de exceções.

## Funcionalidades

- **Agendamento de serviços:** registro de informações do cliente, serviço, tapeceiro, data e horário.
- **Consulta de horários disponíveis:** seleção de horários em uma lista com horas cheias.
- **Prevenção de conflitos:** impede que dois clientes reservem o mesmo horário.
- **Tratamento de exceções:** respostas centralizadas com mensagens claras para situações como horários ocupados e agendamentos não encontrados.
- **Interface responsiva:** layout com cores suaves, fontes grandes e foco na legibilidade, especialmente para pessoas mais velhas.

## Tecnologias utilizadas

### Back-end

- Java 25
- Spring Boot
- Spring Data JPA
- Maven
- H2 Database
- Bean Validation
- Lombok

### Front-end

- HTML5
- CSS3
- JavaScript
- Fetch API para comunicação com o back-end

### Ferramentas

- Git e GitHub
- Maven Wrapper
- Docker, para empacotamento e implantação da aplicação

## Arquitetura

O back-end utiliza uma arquitetura em camadas para separar as responsabilidades do sistema:

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints da API.
- **Service:** concentra as regras de negócio dos agendamentos.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Entity:** representa as entidades persistidas no banco de dados.
- **Exception:** contém exceções personalizadas e o tratamento centralizado de erros.

## Estrutura do projeto

```text
src/
└── main/
    ├── java/
    │   └── com/projeto/agendador_horarios/
    │       ├── controller/
    │       ├── service/
    │       ├── repository/
    │       ├── entity/
    │       └── exception/
    └── resources/
        └── static/
            ├── index.html
            ├── style/
            │   └── tela_inicial.css
            └── js/
                └── script.js
```

## Como executar localmente

### Pré-requisitos

- JDK 25 instalado.
- Git instalado.

O projeto inclui o Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

### 1. Clone o repositório

```bash
git clone https://github.com/Alan0o0/Agendador-Horarios-Tapecaria.git
cd Agendador-Horarios-Tapecaria
```

### 2. Execute a aplicação

**Windows — PowerShell:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux ou macOS:**

```bash
./mvnw spring-boot:run
```

### 3. Acesse a aplicação

Abra o navegador e acesse:

```text
http://localhost:8081
```

A porta acima corresponde à configuração local descrita neste projeto. Caso a porta seja alterada na configuração do Spring Boot, utilize a porta configurada.

## Banco de dados

O projeto utiliza o H2 Database em memória, integrado à aplicação por meio do Spring Data JPA.

Essa configuração simplifica o desenvolvimento, pois não exige a instalação de um servidor de banco de dados.

**Limitações do armazenamento atual:**

- Os agendamentos são perdidos quando a aplicação é encerrada ou reiniciada.
- O banco em memória não é adequado para manter os dados de uma operação real.
- Uma implantação em hospedagem não torna os dados persistentes automaticamente.

Para manter os agendamentos entre reinicializações, será necessário configurar um banco de dados persistente, como PostgreSQL ou MySQL.

### Console do H2

Durante a execução local, o console do H2 pode estar disponível em:

```text
http://localhost:8081/h2-console
```

A disponibilidade depende da configuração da aplicação. Quando habilitado, utilize as credenciais e a URL JDBC definidas no projeto. Na configuração padrão descrita, o usuário é `sa` e a senha fica vazia.

## API REST

### Criar agendamento

**Endpoint:** `POST /agendamentos`

Exemplo de corpo da requisição:

```json
{
  "tipoServico": "Reforma de sofá",
  "tapeceiro": "Nome do tapeceiro",
  "dataHoraAgendamento": "2026-10-25T14:00",
  "cliente": "Nome do cliente",
  "telefoneCliente": "(00) 00000-0000"
}
```

### Respostas principais

| Código HTTP | Significado |
|---|---|
| `202` | Agendamento realizado com sucesso |
| `409` | Horário já ocupado |

## Docker e implantação

O projeto pode ser empacotado em um container Docker para facilitar sua execução em ambientes compatíveis, como o Render.

O Dockerfile deve estar na raiz do repositório, junto do `pom.xml`. A imagem pode utilizar o JDK 25 na etapa de compilação e um ambiente Java 25 para executar a aplicação.

Em ambientes de hospedagem, a porta de execução deve respeitar a configuração exigida pela plataforma, como a variável de ambiente `PORT` no Render.

**Importante:** a utilização de Docker não garante a persistência dos agendamentos. Enquanto o sistema utilizar o H2 em memória, os dados continuarão sujeitos a perda quando a aplicação for reiniciada.

## Próximas melhorias

- [ ] Validar no back-end se os horários possuem minutos iguais a zero.
- [ ] Substituir o H2 em memória por PostgreSQL ou MySQL.
- [ ] Implementar autenticação para o administrador.
- [ ] Criar um dashboard para gerenciar os agendamentos.
- [ ] Adicionar testes automatizados para as regras de negócio e os endpoints.
- [ ] Melhorar o gerenciamento de horários disponíveis.

## Objetivo do projeto

Este projeto faz parte dos meus estudos em Ciência da Computação e tem como objetivo aplicar conhecimentos de Java, Spring Boot, APIs REST, persistência de dados e desenvolvimento web em uma aplicação prática.

## Autor

**Alan Benvenuto de Oliveira**

Estudante de Ciência da Computação — UNINTER

[GitHub](https://github.com/Alan0o0)


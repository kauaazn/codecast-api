# CodeCast API 🎙️

API RESTful para gerenciamento de agendamentos do estúdio de podcast CodeCast.

## Tecnologias

- Java 21
- Spring Boot 4.1
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- Swagger/OpenAPI 3.1

## Como rodar localmente

### Pré-requisitos
- JDK 21 instalado

### Passos

```bash
# Clone o repositório
git clone https://github.com/kauaazn/codecast-api.git

# Entre na pasta do projeto
cd codecast-api/codecast

# Rode a aplicação
./mvnw spring-boot:run  # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

A API estará disponível em: `http://localhost:8080`

## Documentação interativa

Acesse o Swagger UI para testar os endpoints:

http://localhost:8080/swagger-ui/index.html

## Console do banco de dados

http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:codecastdb

User: sa

Password: (vazio)

## Endpoints

### Estúdios
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/studios` | Cadastrar estúdio |
| GET | `/studios` | Listar todos |
| GET | `/studios/{id}` | Buscar por ID |
| PUT | `/studios/{id}` | Atualizar |
| DELETE | `/studios/{id}` | Remover |

### Hosts
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/hosts` | Cadastrar host |
| GET | `/hosts` | Listar todos |
| GET | `/hosts/{id}` | Buscar por ID |
| PUT | `/hosts/{id}` | Atualizar |
| DELETE | `/hosts/{id}` | Remover |

### Agendamentos
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/bookings` | Criar agendamento |
| GET | `/bookings` | Listar todos |
| GET | `/bookings/{id}` | Buscar por ID |
| DELETE | `/bookings/{id}` | Cancelar |

## Exemplos de requisição

### Criar estúdio
```json
POST /studios
{
    "name": "Studio A",
    "maxCapacity": 10,
    "equipments": ["Microfone", "Câmera", "Mesa de som"]
}
```

### Criar host
```json
POST /hosts
{
    "name": "João Silva",
    "email": "joao@codecast.com",
    "phone": "31999999999"
}
```

### Criar agendamento
```json
POST /bookings
{
    "studioId": 1,
    "hostId": 1,
    "recordingDate": "2026-07-01",
    "startTime": "14:00",
    "endTime": "16:00",
    "timezone": "America/Sao_Paulo"
}
```

## Interface Gráfica (GUI) — Extra

O projeto inclui uma interface web desenvolvida em React, localizada na pasta `frontend-app/`.

### Pré-requisitos
- Node.js 18+ instalado

### Como rodar a interface

```bash
# Entre na pasta do frontend
cd codecast-api/frontend-app

# Instale as dependências
npm install

# Inicie o servidor de desenvolvimento
npm start
```

A interface estará disponível em: `http://localhost:3000`

> **Importante:** a API (Spring Boot) deve estar rodando em `http://localhost:8080` antes de iniciar a interface.

### Funcionalidades da interface

- Listagem, cadastro e remoção de **estúdios**
- Listagem, cadastro e remoção de **hosts**
- Criação e cancelamento de **agendamentos** com seleção de fuso horário
- Feedback visual de erro ao tentar criar agendamento com conflito de horário

---

## Arquitetura

```
src/main/java/com/code/codecast/
├── controller/    → Endpoints HTTP
├── service/       → Regras de negócio
├── repository/    → Acesso ao banco
├── model/         → Entidades do banco
├── dto/           → Objetos de entrada e saída
└── exception/     → Tratamento de erros

frontend-app/
└── src/
    ├── App.js        → Navegação e layout (sidebar)
    ├── Studios.js    → Tela de estúdios
    ├── Hosts.js      → Tela de hosts
    ├── Bookings.js   → Tela de agendamentos
    └── index.css     → Estilos globais
```
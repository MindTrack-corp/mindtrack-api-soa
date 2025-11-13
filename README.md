# MindTrack API

# 👥 Integrantes

 - Deivison Pertel – RM 550803
 - Eduardo Akira Murata – RM 98713
 - Wesley Souza de Oliveira – RM 97874


API REST corporativa desenvolvida para a Global Solution FIAP 2025, alinhada ao tema:

> **“Ferramentas de monitoramento de bem-estar e saúde mental no trabalho.”**

A MindTrack fornece recursos para que colaboradores registrem seu estado emocional e para que equipes de RH visualizem **relatórios anônimos agregados**, contribuindo para um ambiente de trabalho mais saudável, ético e produtivo.

---

# ✅ 1. Objetivos do Projeto

A **MindTrack API** possibilita:

### 👤 Para colaboradores
- Registrar **check-ins diários de bem-estar**  
- Consultar histórico de humor

### 🧑‍💼 Para RH / Admin
- Criar equipes
- Vincular colaboradores às equipes
- Gerar **relatórios anônimos agregados por período**
- Acompanhar níveis de humor de cada time sem expor dados individuais

### 🔐 Segurança
- API 100% protegida via **JWT (stateless)**  
- Perfis de acesso: `ROLE_COLABORADOR`, `ROLE_RH`, `ROLE_ADMIN`  
- Regras de autorização aplicadas com `@PreAuthorize`

---

# 🛠️ 2. Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3** (Web, Security, Data JPA, Validation)
- **H2 Database** (em memória)
- **Spring Security com JWT**
- **Lombok**
- **Maven**

---

# ▶️ 3. Como executar o projeto

### Pré-requisitos:
- Java **17+**
- Maven **3.8+**

### Rodando a API:

```bash
mvn clean spring-boot:run
```

### Endpoints base:
```
http://localhost:8080
```

### Console do H2:
```
http://localhost:8080/h2-console
User: sa
Password: (vazio)
```

---

# 📁 4. Arquitetura do Projeto

Organização modular por domínios:

```
src/main/java/com/example/mindtrack
│
├── auth
├── checkin
├── user
├── team
├── report
├── security
├── common
└── MindtrackApplication.java
```

Este formato favorece **baixa acoplagem, alta coesão, reuso e manutenção**.

---

# 🔐 5. Segurança

A API utiliza:

- **JWT** com chave segura e expiração configurável  
- **Filtro customizado (`JwtAuthFilter`)**  
- Sessão **STATELESS**  
- Rotas com permissões:
  - `COLABORADOR`: criar e listar seus check-ins
  - `RH`: relatórios de times + gerenciamento de equipes
  - `ADMIN`: permissões de RH + gestão de usuários

---

# 📌 6. Endpoints da API — Com exemplos completos

---

# 🔑 6.1 Autenticação

## **POST /auth/register**
 - Cadastra um novo usuário.
 - Variáveis permitidas: 'ROLE_COLABORADOR', 'ROLE_RH', 'ROLE_ADMIN'
### Body:
```json
{
  "name": "Eduardo Akira Murata",
  "email": "edug@gmail.com",
  "password": "123456",
  "role": "ROLE_COLABORADOR"
}
```

### Response:
```json
{
  "success": true,
  "message": "Usuário registrado com sucesso",
  "data": {
    "token": "eyJh...",
    "tokenType": "Bearer"
  }
}
```

---

## **POST /auth/login**
 - Autentica um usuário e retorna JWT.

### Body:
```json
{
  "email": "edug@gmail.com",
  "password": "123456"
}
```

### Response:
```json
{
  "success": true,
  "message": "Autenticado com sucesso",
  "data": {
    "token": "eyJh...",
    "tokenType": "Bearer"
  }
}
```

---

## **GET /api/users/me**
 - Retorna dados do usuário autenticado.
 - Token do colaborador deve ser informado na requisição.

### Response:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Eduardo Akira Murata",
    "email": "edug@gmail.com",
    "role": "ROLE_COLABORADOR",
    "team": {
      "id": 1,
      "name": "Squad Backend"
    }
  }
}
```

---

# 🟦 6.2 Check-ins (COLABORADOR)

## **POST /api/checkins**
 - Cria o check-in do dia.
 - Token do colaborador deve ser informado na requisição.s
 - Variáveis permitidas: 'VERY_BAD', 'BAD', 'NEUTRAL', 'GOOD', 'VERY_GOOD'

### Body:
```json
{
  "mood": "VERY_GOOD",
  "note": "Hoje estou ótimo!"
}
```

### Response:
```json
{
  "success": true,
  "message": "Check-in criado com sucesso",
  "data": {
    "id": 1,
    "date": "2025-11-12",
    "mood": "VERY_GOOD",
    "note": "Hoje estou ótimo!"
  }
}
```

---

## **GET /api/checkins/me**
 - Lista todos os check-ins do usuário logado.
 - Token do colaborador deve ser informado na requisição.

### Response:
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "date": "2025-11-12",
      "mood": "VERY_BAD",
      "note": "Teste"
    }
  ]
}
```

---

# 🟩 6.3 Equipes (RH / ADMIN)

## **POST /api/teams**
 - Cria uma equipe.
 - Token do RH/Admin deve ser informado na requisição.

### Body:
```json
{
  "name": "Squad Backend"
}
```

### Response:
```json
{
  "success": true,
  "message": "Equipe criada com sucesso",
  "data": {
    "id": 1,
    "name": "Squad Backend"
  }
}
```

---

## **POST /api/teams/{teamId}/members/{userId}**
 - Vincula um usuário a uma equipe.
 - Token do RH/Admin deve ser informado na requisição.

### Response:
```json
{
  "success": true,
  "message": "Usuário vinculado à equipe com sucesso",
  "data": {
    "id": 1,
    "name": "Squad Backend"
  }
}
```

---

# 🟧 6.4 Relatórios (RH / ADMIN)

## **GET /api/reports/teams/{id}?start=yyyy-mm-dd&end=yyyy-mm-dd**
 - Token do RH/Admin deve ser informado na requisição.
### Exemplo:
```
GET /api/reports/teams/1?start=2025-11-01&end=2025-11-30
```

### Response:
```json
{
  "success": true,
  "data": {
    "teamId": 1,
    "teamName": "Squad Backend",
    "avgMood": 1,
    "checkinsCount": 1
  }
}
```

---

# 🧪 7. Critérios atendidos da disciplina (SOA & WebServices)

### ✔ Entities, VOs, Enums, Controllers, DTOs  
### ✔ Padronização ResponseEntity  
### ✔ Tratamento global de exceções  
### ✔ Autenticação e Autorização (JWT + Roles)  
### ✔ Política STATELESS  
### ✔ Regras de negócio em serviços  
### ✔ Organização modular por domínios  

---
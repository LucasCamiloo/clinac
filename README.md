# CLINAC+

Sistema de Gestão de Clínica Médica (Projeto Integrador)

## Descrição

O **CLINAC+** é um sistema web para gerenciamento de uma clínica médica universitária. Ele permite o cadastro e gerenciamento de pacientes, médicos, recepcionistas, agendamento de consultas, prescrições e controle de acesso por tipo de usuário.

## Funcionalidades

- Cadastro e login de pacientes, médicos e recepcionistas
- Painel do paciente: agendamento de consultas, visualização de agendamentos
- Painel do médico: login via CRM (em desenvolvimento)
- Painel da recepcionista: gerenciamento de pacientes, médicos e agendamentos
- Gerenciador de médicos e pacientes (CRUD)
- Agendamento de consultas pelo paciente e pela recepcionista
- Controle de acesso por sessão
- Integração com MongoDB

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data MongoDB
- Thymeleaf
- HTML5, CSS3, JavaScript
- MongoDB

## Como rodar o projeto

1. **Pré-requisitos:**
   - Java 17 ou superior
   - Maven
   - MongoDB rodando em `localhost:27017`

2. **Clone o repositório:**
   ```
   git clone <url-do-repositorio>
   cd ProjetoIntegrador
   ```

3. **Instale as dependências e rode o projeto:**
   ```
   mvn spring-boot:run
   ```

4. **Acesse no navegador:**
   ```
   http://localhost:8080/
   ```

## Usuários padrão

- **Recepcionista administradora:**  
  E-mail: `admin@clinac.com`  
  Senha: `admin`

## Estrutura de Pastas

- `src/main/java/com/senac/projetointegrador/` - Código Java (controllers, entities, repositories, services)
- `src/main/resources/templates/Views/` - Templates HTML (Thymeleaf)
- `src/main/resources/static/` - Arquivos estáticos (CSS, imagens)

## Observações

- O sistema foi desenvolvido para fins acadêmicos.
- Para acessar funcionalidades administrativas, faça login como recepcionista.
- O cadastro de médicos e pacientes pode ser feito via painel da recepcionista.
- O sistema utiliza autenticação por sessão, protegendo as rotas principais.

---

Desenvolvido por alunos para o Projeto Integrador SENAC.

# Sistema de Questionário 🚀
## Roadmap Java
Sistema feito baseado no roadmap proposto pelo Dev Magro | Carrilho para adquirir conhecimentos em em Desenvolvimento Back-End com Java,
Link: https://docs.google.com/document/d/12ek1Wsd_ibuwTOjHtLPZwEWdy5-A7cRoO2Bf-v5G1_s/edit?tab=t.0
## Descrição do Projeto
Um sistema de questionário desenvolvido com Spring Boot que permite cadastrar pessoas e perguntas no banco de dados, além de registrar respostas. O sistema utiliza uma estrutura RESTful com controladores para gerenciar as operações CRUD de pessoas, perguntas e respostas.

## Estrutura do Projeto

### Controladores (Controllers)
O sistema possui três controladores principais:

1. **QuestionController**: Gerencia as operações relacionadas às perguntas
2. **PersonController**: Gerencia as operações relacionadas às pessoas
3. **AnswerController**: Gerencia as operações relacionadas às respostas

### Endpoints da API

#### Perguntas (Questions)
- `POST /question` - Cadastrar uma nova pergunta
- `GET /question` - Listar todas as perguntas
- `GET /question/{id}` - Buscar pergunta por ID
- `PUT /question/{id}` - Atualizar uma pergunta existente
- `DELETE /question/{id}` - Remover uma pergunta

#### Pessoas (Persons)
- `POST /person` - Cadastrar uma nova pessoa
- `GET /person` - Listar todas as pessoas
- `GET /person/{id}` - Buscar pessoa por ID
- `PUT /person/{id}` - Atualizar uma pessoa existente
- `DELETE /person/{id}` - Remover uma pessoa

#### Respostas (Answers)
- `POST /answer` - Registrar uma nova resposta
- `GET /answer/{id}` - Buscar respostas por ID
- `PUT /answer/{id}` - Atualizar uma resposta existente
- `DELETE /answer/{id}` - Remover uma resposta

## Arquitetura

O projeto segue uma arquitetura em camadas:
- **Controller**: Recebe as requisições HTTP e direciona para os serviços apropriados
- **Service**: Contém a lógica de negócio da aplicação
- **Repository**: Responsável pela comunicação com o banco de dados
- **DTO**: Objetos de transferência de dados para entrada e saída
- **Domain**: Entidades que representam as tabelas do banco de dados

## Tecnologias Utilizadas 🛠

- **Spring Boot**: Framework para desenvolvimento de aplicações Java
- **Spring MVC**: Para desenvolvimento da API RESTful
- **Spring Data JPA**: Para persistência de dados
- **JUnit & Mockito**: Para testes unitários
- **Testes de Integração**: Para validar o funcionamento completo do sistema

## Testes

O projeto inclui testes unitários para todas as camadas:
- Testes unitários para os controladores usando Mockito
- Testes unitários para as camadas de serviço
- Testes unitários para os repositórios
- Testes de integração para validar o fluxo completo da aplicação

## Como Executar

1. Clone o repositório
2. Configure o banco de dados no arquivo `application.properties`
3. Execute o comando: `mvn spring-boot:run`
4. A API estará disponível em: `http://localhost:8080`

## Como Executar os Testes

Para executar os testes unitários:
```
╰─❯ mvn test
```

Para executar os testes de integração:
```
╰─❯ mvn verify
```

Para executar todos os teste
```
╰─❯ mvn test -Pintegration-tests                                                                                                                  ─╯
```

## Requisitos

- Java 8 ou superior
- Maven
- Banco de dados compatível com JPA (MySQL, PostgreSQL, H2, etc.)
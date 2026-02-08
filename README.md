# Inventory Management API

Aplicação backend simples para gestão de itens e movimentos de stock, desenvolvida em Spring Boot no contexto da UC de Arquitetura e Desenho de Software.

## Tecnologias
- Java 17
- Spring Boot
- Spring Data JPA
- SQLite
- Flyway
- Swagger / OpenAPI

## Funcionalidades
- CRUD de itens
- Registo de movimentos de stock (entrada, saída, ajuste)
- Cálculo de stock atual
- Relatórios de stock e itens abaixo do mínimo
- API documentada via Swagger

## Base de Dados
A aplicação utiliza SQLite com criação do schema via Flyway.  
O ficheiro da base de dados é criado automaticamente em `./data/inventory.db`.

## Como executar
1. Clonar o repositório
2. Abrir o projeto no IntelliJ
3. Executar a classe `InventoryApplication`
4. Aceder a:
    - API: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui.html

## Notas
O schema da base de dados é validado pelo Hibernate (`ddl-auto=validate`) e gerido exclusivamente pelo Flyway.
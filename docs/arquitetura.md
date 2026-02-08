# Arquitetura da Aplicação

## Visão Geral
A aplicação segue uma arquitetura em camadas, separando responsabilidades de apresentação, lógica de negócio e acesso a dados.

## Camadas
- Controller: exposição da API REST e validação de pedidos
- Service: regras de negócio e coordenação de operações
- Repository: acesso à base de dados via Spring Data JPA
- Domain: entidades e exceções de domínio
- DTO: objetos de transferência de dados entre API e serviços

## Persistência
- Base de dados SQLite
- Mapeamento ORM com JPA/Hibernate
- Conversor personalizado para tipos temporais (`Instant`)

## Gestão de Schema
- Flyway para criação e versionamento do schema
- Hibernate configurado apenas para validação do schema

## Documentação da API
- Swagger/OpenAPI para visualização e teste dos endpoints
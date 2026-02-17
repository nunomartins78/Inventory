# Arquitetura da Aplicação

## Visão Geral

A aplicação segue uma arquitetura em camadas, separando responsabilidades de apresentação, lógica de negócio
e acesso a dados.

O sistema é composto por:

- Backend REST desenvolvido em Spring Boot
- Base de dados SQLite
- Interface web estática (HTML + JavaScript + Bootstrap) servida pelo próprio Spring Boot

A interface comunica com o backend exclusivamente através da API REST.

---

## Camadas

### Controller
Responsável pela exposição da API REST.

- Mapeamento dos endpoints HTTP
- Validação de pedidos
- Conversão entre DTOs e objetos de domínio
- Tratamento centralizado de exceções através de `@ControllerAdvice`

### Service
Contém a lógica de negócio da aplicação.

- Regras de validação de stock
- Cálculo do stock atual com base nos movimentos
- Implementação das regras para Entrada, Saída e Ajuste
- Coordenação entre repositórios

### Repository
Responsável pelo acesso à base de dados.

- Implementado com Spring Data JPA
- Abstrai operações CRUD
- Permite interação com SQLite via Hibernate

### Domain
Contém:

- Entidades JPA (`Item`, `StockMovement`)
- Enum (`MovementType`)
- Exceções de domínio (`NotFoundException`, `BusinessRuleException`)
- Conversor personalizado para tipos temporais (`InstantToLongConverter`)

### DTO
Objetos de transferência de dados utilizados na API:

- `CreateMovementRequestDto`
- `StockReportRowDto`
- `MovementRowDto`
- `ApiErrorDto`

Permitem desacoplar a API das entidades internas.

---

## Interface Web

A aplicação inclui uma interface web estática localizada em:

src/main/resources/static/index.html


Características:

- HTML simples
- Bootstrap via CDN para styling
- JavaScript puro com `fetch()` para consumir a API
- Sem frameworks adicionais (React, Angular, etc.)

A interface:

- Cria itens
- Lista itens com stock atual
- Regista movimentos (Entrada, Saída, Ajuste)
- Remove itens
- Destaca visualmente stock abaixo do mínimo

Toda a lógica de negócio permanece exclusivamente no backend.

---

## Persistência

- Base de dados SQLite
- Mapeamento ORM com JPA/Hibernate
- Conversor personalizado para persistência de `Instant` como `INTEGER`
- Relação Many-to-One entre `StockMovement` e `Item`

---

## Gestão de Schema

- Flyway para criação e versionamento do schema
- Scripts SQL versionados
- Hibernate configurado apenas para validação do schema (`validate`)

---

## Documentação da API

- Swagger / OpenAPI para visualização e teste dos endpoints
- Permite executar todas as operações diretamente via interface interativa

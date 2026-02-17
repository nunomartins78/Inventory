# Manual do Utilizador

## 1. Iniciar a aplicação

Após iniciar a aplicação Spring Boot, o sistema fica disponível em:

http://localhost:8080

---

## 2. Interface Web

A aplicação disponibiliza uma interface web simples baseada em HTML, JavaScript e Bootstrap.

Pode ser acedida em:

http://localhost:8080/index.html


### Funcionalidades disponíveis na interface:

- Criar item (nome e stock mínimo)
- Listar itens com:
    - ID
    - Nome
    - Stock atual
    - Stock mínimo
- Registar movimentos de stock:
    - **Entrada**
    - **Saída**
    - **Ajuste** (para perdas ou correções)
- Remover item

### Destaque visual

Quando o stock atual de um item fica abaixo do stock mínimo definido, o valor do stock é apresentado:

- A **negrito**
- A **vermelho**

Isto permite identificar rapidamente situações de stock crítico.

---

## 3. API REST

A API REST continua disponível para testes diretos em:

http://localhost:8080


---

## 4. Swagger

A documentação interativa da API pode ser acedida em:

http://localhost:8080/swagger-ui.html


No Swagger é possível:

- Criar, atualizar e remover itens
- Registar movimentos de stock
- Consultar relatórios de stock
- Testar diretamente todos os endpoints disponíveis

---

## 5. Operações principais do sistema

- Criar item
- Atualizar item
- Remover item
- Registar movimentos de stock (Entrada, Saída, Ajuste)
- Consultar stock atual por item
- Consultar itens abaixo do stock mínimo
- Exportar relatórios em CSV (stock e movimentos)

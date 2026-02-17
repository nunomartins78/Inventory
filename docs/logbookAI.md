## Pergunta à IA
Nunca usei Flyway. Como posso criar uma migration inicial simples para uma aplicação Spring Boot?


## Resposta da IA
Pode criar um ficheiro SQL na pasta db/migration com o nome V1__init.sql.
Nesse ficheiro define as tabelas necessárias com SQL puro.
Ao arrancar a aplicação, o Flyway executa automaticamente essa migration e cria o schema antes do Hibernate validar as entidades.

Uma abordagem “limpa” é: Flyway cria/atualiza o schema e Hibernate valida que as entidades batem certo com a BD.


###  Dependência
   Garante que tens o Flyway no pom.xml (ex.: flyway-core).


### Configuração (application.properties)
O essencial é:

- spring.flyway.enabled=true
- spring.flyway.locations=classpath:db/migration
- spring.jpa.hibernate.ddl-auto=validate (ou none se não quiseres validação)

A ideia é: ao arrancar, o Flyway corre as migrations; depois, o Hibernate valida o schema resultante.


### Estrutura de pastas
Cria a pasta src/main/resources/db/migration/


### Nome das migrations
Usa o formato V1__init.sql, V2__add_something.sql, etc.
O __ separa versão e descrição.


### Conteúdo da migration inicial (V1)
No V1__init.sql, define as tabelas essenciais. Exemplo conceptual:

- items com id, name, min_stock
- stock_movements com id, item_id, type, quantity, created_at

Se houver relação ManyToOne, adiciona FOREIGN KEY (item_id) REFERENCES items(id).


### Ordem e comportamento no arranque
- Se a BD estiver vazia, o Flyway cria a tabela flyway_schema_history e aplica V1.
- Se a BD já tiver migrations aplicadas, ele valida e só executa o que falta.


### Diagnóstico se algo falhar
Se o Hibernate falhar em validate, normalmente é por:
- tipo de coluna diferente (ex.: created_at guardado como texto vs número)
- nome de coluna diferente do @Column
- falta de coluna/constraint

### Recomendações:

- Confirmar no log que aparece “Successfully applied X migrations” e a versão atual.
- Abrir a BD e inspecionar as tabelas geradas.
- Se precisares de alterar um detalhe, não edites a migration já aplicada: cria uma V2__...sql para corrigir.

---

## Pergunta à IA
A minha app arranca, mas quando tento abrir o Swagger dá erro 500 em /v3/api-docs.
No log aparece isto (excerto):

*inseri a stackrace*


## Resposta da IA
O erro NoSuchMethodError quase sempre indica conflito de versões no classpath: a aplicação está a compilar com uma versão de uma classe, mas em runtime está a carregar outra versão onde esse método não existe.
Neste caso: ControllerAdviceBean.<init>(Object) é uma assinatura que mudou entre versões do Spring Framework.
O Swagger (springdoc) depende fortemente das mesmas versões do Spring Web, por isso um desalinhamento dá exatamente este tipo de crash, muitas vezes ao gerar /v3/api-docs.

Checklist de debug (ordem prática):

### Confirmar compatibilidade Spring Boot ↔ springdoc

- Estás a usar Spring Boot 3.x (ok).
- Precisas de um springdoc-openapi-starter-* compatível com essa geração.

Se a versão do springdoc for “antiga” (ou demasiado específica) pode puxar dependências incompatíveis.

### Procurar “dependency hell” (versões duplicadas)
O sintoma típico é teres simultaneamente jars do tipo:

- org.springframework.boot.autoconfigure.web.servlet...
- org.springframework.boot.webmvc.autoconfigure...

duas variantes que não deviam coexistir daquele modo, ou então teres múltiplas versões de spring-web / spring-webmvc.

### Ver o dependency tree
A forma clássica é correr:

- mvn dependency:tree

e procurar:

- múltiplas versões de spring-web, spring-webmvc, spring-core
- múltiplas versões de springdoc-*

### Resolver a causa mais comum
Regra geral, em projetos Spring Boot:

- não fixes versões manualmente para dependências que o BOM do Spring Boot já gere (ex.: Jackson, Spring, Hibernate core, etc.).
- fixa apenas o que for estritamente necessário (e mesmo assim com cuidado).

### Limpeza e rebuild
Mesmo que já tenhas corrigido o pom, o IntelliJ às vezes fica com jars antigos.
Faz:

- Maven “Reimport”
- “clean” + “package”
- apagar target/ se necessário

### Confirmar no runtime
Depois da correção, validar:

- /v3/api-docs já devolve JSON
- /swagger-ui.html abre

### O que eu faria já como hipótese principal
Pela mensagem, eu suspeito de incompatibilidade entre Spring Boot 3.5.x e a versão do springdoc (ou uma dependência transitiva desalinhada).
A correção típica é alinhar para uma versão do springdoc compatível com o teu Spring Boot e remover overrides desnecessários no pom.xml.

---

## Pergunta à IA
A aplicação Spring Boot não arranca.
Durante o arranque surge um erro e o contexto do Spring falha ao inicializar.

*inseri a stackrace*


## Resposta da IA
Este erro acontece quando o Spring não consegue detetar/criar o bean do ItemRepository, por isso não consegue injetá-lo no InventoryService.

O que verificar (ordem típica):

### Package scanning (causa mais comum)

- O @SpringBootApplication define o “ponto de partida” do scanning.
- Garante que a classe InventoryApplication está no package raiz (ex.: inventory) e que repository está abaixo desse package (ex.: inventory.repository).
- Se a classe principal estiver noutro package (ex.: pt.umaia.inventory ou com.example), os repositories fora desse “sub-tree” não são encontrados.

### O repository tem de estar correto
Confirma que ItemRepository está declarado como public interface ItemRepository extends JpaRepository<Item, Long> e que existe spring-boot-starter-data-jpa nas dependências (já tens).

### Entidade e JPA válidos

- O Item deve estar anotado com @Entity e ter um @Id. 
- Se o Spring falhar a inicializar JPA/Entities, também pode não chegar a criar repositories.

### Correções possíveis
A correção mais “limpa” é garantir que o package principal é mesmo inventory e que todos os packages (controller, service, repository, domain) estão dentro dele.
Alternativa (se tiveres mesmo de manter packages fora do root): configurar explicitamente scanning, por exemplo com @SpringBootApplication(scanBasePackages = "...") ou @EnableJpaRepositories(basePackages = "...").
Se após isto continuar, o próximo passo é olhar para as primeiras exceções no log (às vezes o erro real está acima e este é só consequência).

---

## Pedido à IA

Pretendo criar um frontend muito simples para o meu sistema de gestão de inventário desenvolvido em Spring Boot.

O objetivo é:
- Criar uma página HTML estática servida pelo próprio Spring Boot.
- Usar Bootstrap via CDN apenas para styling.
- Implementar JavaScript simples com `fetch()` para consumir a API REST já existente.
- Permitir:
    - Criar itens
    - Listar itens com stock atual (via endpoint de relatório)
    - Registar movimentos (Entrada, Saída e Ajuste para perdas)
    - Remover itens
- Destacar visualmente quando o stock atual fica abaixo do stock mínimo.

O frontend deve ser simples, coerente com alguém que fez apenas um bootcamp, sem frameworks adicionais (React, Angular, etc.).

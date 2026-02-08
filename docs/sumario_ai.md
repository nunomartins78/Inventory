# Sumário IA

## Sistemas de IA utilizados
Durante o desenvolvimento deste trabalho utilizei uma ferramenta de Inteligência Artificial generativa (ChatGPT) como apoio pontual ao processo de desenvolvimento.

A IA foi usada principalmente como ferramenta de esclarecimento e apoio técnico, e não como substituto da implementação ou das decisões finais.

---

## Contexto e forma de utilização

A IA foi utilizada sobretudo em momentos em que surgiram dúvidas técnicas ou problemas de arranque da aplicação, nomeadamente em:

- compreensão da organização típica de uma aplicação Spring Boot em camadas (controller, service, repository);
- interpretação de erros de arranque da aplicação e análise de mensagens de erro no log;
- configuração inicial do Flyway para criação do schema da base de dados;
- resolução de problemas de compatibilidade de dependências.

Na maioria dos casos, a interação consistiu em colocar uma questão concreta sobre um erro ou conceito específico e analisar a explicação fornecida, decidindo depois como aplicar (ou não) essa informação ao projeto.

---

## System prompt (síntese)

A system prompt utilizada definiu a IA como um apoio técnico, com foco em:

- explicações claras e práticas;
- evitar soluções excessivamente complexas;
- privilegiar abordagens simples e fáceis de entender;
- explicar causas prováveis dos erros em vez de apenas apresentar código pronto.

O objetivo foi manter controlo sobre o desenvolvimento e compreender as decisões tomadas, em vez de aceitar soluções automaticamente.

---

## Tipos de prompts utilizados (síntese)

Os pedidos feitos à IA foram maioritariamente do tipo:

- pedidos de explicação;
- pedidos de orientação;
- pedidos de validação.



---

## Avaliação crítica do output da IA

O output da IA foi útil sobretudo para:
- acelerar a identificação da causa de erros técnicos;
- clarificar conceitos que eu ainda não dominava totalmente (ex.: Flyway, lifecycle do Spring Boot);
- confirmar boas práticas comuns em projetos Spring simples.


A configuração final do projeto resultou de ajustes manuais após testar e validar as sugestões dadas.

---

## Reflexão pessoal

O uso de IA ajudou-me a desbloquear problemas técnicos e a avançar mais rapidamente em momentos de impasse, sobretudo ao nível de configuração e debugging.

Ao mesmo tempo, tornou-se claro que confiar cegamente nas respostas não é suficiente: foi necessário interpretar, testar e, por vezes, rejeitar soluções propostas.

Este trabalho levou-me a usar a IA de forma mais crítica e controlada, como uma ferramenta de apoio ao raciocínio e não como substituto do desenvolvimento ou da aprendizagem prática.

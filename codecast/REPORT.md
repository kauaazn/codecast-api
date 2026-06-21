# Relatório Técnico — CodeCast API

## Contextualização do problema

O CodeCast é um estúdio de gravação de podcasts que enfrenta problemas de conflitos de agenda, falta de controle sobre alocação de estúdios e dificuldade na gestão de disponibilidade. O sistema precisa ser acessado simultaneamente por múltiplos produtores em diferentes dispositivos e fusos horários, tornando a integridade da agenda um requisito crítico.

## Interpretação do desafio

O desafio consiste em construir o núcleo de uma API RESTful capaz de gerenciar estúdios, apresentadores e agendamentos, com ênfase especial na validação de conflitos de horário e suporte a fusos horários distintos.

## Arquitetura escolhida

A solução foi desenvolvida em Java com Spring Boot, seguindo uma arquitetura em três camadas:

- **Controller**: responsável por receber as requisições HTTP e devolver as respostas
- **Service**: responsável pelas regras de negócio, validações e orquestração
- **Repository**: responsável pelo acesso ao banco de dados via Spring Data JPA

Além disso, foi utilizado o padrão DTO (Data Transfer Object) para separar os objetos de entrada e saída da API das entidades do banco de dados, evitando exposição desnecessária da camada de persistência.

## Principais decisões técnicas

**Banco de dados H2 in-memory**: optou-se pelo H2 para simplificar o setup do ambiente, permitindo que qualquer avaliador execute o projeto sem necessidade de instalar ou configurar um banco externo.

**Conversão de fusos horários para UTC**: todos os horários são convertidos para UTC antes de serem persistidos no banco. Isso garante que agendamentos de hosts em fusos horários diferentes sejam comparados corretamente na detecção de conflitos.

**Detecção de conflito via query JPQL**: a validação de sobreposição de horários é feita com uma query customizada no repositório. A lógica utilizada é: dois agendamentos conflitam quando um começa antes do outro terminar e termina depois do outro começar. Isso cobre todos os cenários de sobreposição.

**Tratamento global de exceções**: foi implementado um GlobalExceptionHandler com @RestControllerAdvice para padronizar todas as respostas de erro da API, retornando sempre um objeto com status, mensagem e timestamp.

**Swagger/OpenAPI**: foi adicionada documentação interativa da API via springdoc-openapi, permitindo visualizar e testar todos os endpoints diretamente no navegador.

## Dificuldades encontradas

- Compatibilidade entre a versão do Spring Boot 4.x e a versão do springdoc-openapi, resolvida com a atualização para a versão 2.8.9 da dependência.
- Configuração do ambiente no Windows, que exige o uso de `mvnw.cmd` em vez de `./mvnw`.

## Possíveis melhorias futuras

- Migração para PostgreSQL em ambiente de produção
- Implementação de autenticação e autorização com Spring Security e JWT
- Adição de testes automatizados unitários e de integração
- Containerização com Docker e docker-compose
- Paginação nos endpoints de listagem
- Endpoint para consulta de disponibilidade de um estúdio por data

## Conclusão

A solução entregue cobre todos os requisitos obrigatórios do desafio: CRUD completo de estúdios e hosts, módulo de agendamentos com validação de conflito de horários, suporte a fusos horários e tratamento padronizado de erros. A arquitetura foi pensada para ser clara, modular e de fácil manutenção, seguindo as boas práticas de engenharia de software descritas no edital.
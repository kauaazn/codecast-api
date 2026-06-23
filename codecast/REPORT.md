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

## Interface Gráfica (Desafio Extra — GUI)

### Decisões técnicas

A interface foi desenvolvida em **React** (Create React App) e consumindo diretamente os endpoints da API via `fetch`. A escolha do React foi motivada pela familiaridade com o ecossistema e pela facilidade de componentização das três telas (Estúdios, Hosts e Agendamentos).

**Estrutura da interface:**

- `App.js` — layout com sidebar fixa de navegação
- `Studios.js` — formulário de cadastro + tabela de estúdios
- `Hosts.js` — formulário de cadastro + tabela de hosts
- `Bookings.js` — formulário de agendamento com seleção de fuso horário + tabela de reservas
- `index.css` — sistema de design com variáveis CSS (cores, sombras, raios de borda)

**Feedback visual de conflito:** quando a API retorna HTTP 400 (horário indisponível), a interface exibe um alerta vermelho com o título "Horário Indisponível" e a mensagem de erro devolvida pela API, conforme exigido no enunciado.

**Fuso horário:** o seletor de timezone no formulário de agendamento repassa o valor diretamente para a API, que é responsável pela conversão para UTC antes de persistir.

### Uso de Inteligência Artificial na GUI

Conforme a política declarada no edital, o uso de Inteligência Artificial é **totalmente liberado e encorajado** para a construção exclusiva da interface gráfica.

**Ferramenta utilizada:** Claude (Anthropic) — via Claude Code (CLI).

**O que foi feito com auxílio de IA:**

- Geração da estrutura inicial dos componentes React (`Studios.js`, `Hosts.js`, `Bookings.js`)
- Criação do sistema de estilos em `index.css` com CSS Variables e layout de sidebar
- Refatoração da UI original (inline styles genéricos) para uma interface com design system consistente
- Atualização do `README.md` com instruções de execução do frontend

**Como foi feito:** os prompts descreveram o contexto do desafio, a API existente (endpoints, DTOs e regras de negócio) e o nível de qualidade esperado (UI profissional, não genérica). A IA gerou o código; o autor revisou cada arquivo, validou a integração com a API rodando localmente e ajustou detalhes de UX (labels, placeholders, empty states e formatação de horários).

O autor é capaz de executar, explicar a arquitetura e dar manutenção em todo o código da interface gerado.

---

## Uso de Inteligência Artificial (Backend)

Durante o desenvolvimento da API, foi utilizado o auxílio de Inteligência Artificial (Claude - Anthropic) como ferramenta de apoio nos seguintes aspectos:

- Orientação sobre a estrutura e arquitetura do projeto
- Auxílio na resolução de problemas de configuração do ambiente (JDK, Spring Boot, Swagger)
- Suporte na escrita e revisão do código

Todo o código foi compreendido, revisado e é de total responsabilidade do autor. O candidato é capaz de explicar cada decisão técnica, a arquitetura escolhida e dar manutenção em qualquer parte do código produzido.

As decisões técnicas — como a escolha da arquitetura em camadas, a estratégia de conversão de fusos horários para UTC e a lógica de detecção de conflitos de agendamento — foram tomadas com pleno entendimento do problema e das soluções aplicadas.

## Conclusão

A solução entregue cobre todos os requisitos obrigatórios do desafio: CRUD completo de estúdios e hosts, módulo de agendamentos com validação de conflito de horários, suporte a fusos horários e tratamento padronizado de erros. A arquitetura foi pensada para ser clara, modular e de fácil manutenção, seguindo as boas práticas de engenharia de software descritas no edital.

O desafio extra da interface gráfica foi aceito e implementado: a GUI consome todos os endpoints da API, exibe as listagens em tabelas, oferece formulários para cada entidade e apresenta feedback visual de erro nos conflitos de agendamento.
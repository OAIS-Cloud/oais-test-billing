# Teste Técnico para Desenvolvedor(a) Full Stack - Sistema de Cobranças

## Objetivo do Projeto

Desenvolver um sistema de cobrança que gerencia moedas e contratos. Este sistema utiliza Vue.js no front-end e Quarkus com Kotlin na última versão do Java para o back-end. O banco de dados é PostgreSQL, configurado via Docker.

## Descrição do Projeto

### Frontend

- Interface para cadastro de moedas, com campos para nome e sigla.
- Interface para listagem de todas as moedas cadastradas.
- Interface para cadastro de contratos, permitindo vincular a uma moeda e definir um valor.
- Interface para listagem de todos os contratos, mostrando a moeda vinculada e o valor.

### Backend

- API para criação, edição e listagem de moedas.
- API para criação, edição e listagem de contratos, incluindo a moeda associada.

### Geração de Relatórios

- Funcionalidade que permite ao usuário gerar relatórios dos contratos cadastrados em formatos PDF ou XLS.
- Os relatórios incluem detalhes como moeda vinculada, valor do contrato e outras informações relevantes.
- Implementação de lógica de geração de relatório no backend.

### Banco de Dados

- Utilizar PostgreSQL, configurado via Docker, para armazenar as informações.
- A modelagem do banco de dados é de sua responsabilidade.

## Requisitos Técnicos

- Utilizar a última versão do Java para o desenvolvimento do backend.
- O projeto deve ser configurado usando Docker, especialmente para o banco de dados.

## Entrega

- O código deve ser versionado usando Git e disponibilizado em um PR neste repositório no GitHub.
- Incluir um `README.md` no repositório com instruções básicas sobre o projeto.

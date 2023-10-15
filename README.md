# API de Autenticação de Produtos com Spring Security

![Spring](https://img.shields.io/badge/spring_security-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Este projeto é uma API construída utilizando **Java, Java Spring, Flyway Migrations, PostgresSQL como banco de dados e Spring Security e JWT para controle de autenticação.**


## Sumário

- [Instalação](#instalação)
- [Configuração](#configuração)
- [Uso](#uso)
- [Pontos de Extremidade da API](#pontos-de-extremidade-da-api)
- [Autenticação](#autenticação)
- [Banco de Dados](#banco-de-dados)

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/Fernanda-Kipper/auth-api.git
```

2. Instale as dependências com o Maven.

3. Instale o [PostgresSQL](https://www.postgresql.org/).

## Uso

1. Inicie a aplicação com o Maven.
2. A API estará acessível em http://localhost:8080.

## EndPoints da API
A API oferece os seguintes pontos de extremidade:

```markdown
GET /product - Recupere uma lista de todos os produtos. (todos os usuários autenticados)

POST /product - Registre um novo produto (acesso de ADMIN necessário).

POST /auth/login - Faça login no aplicativo.

POST /auth/register - Registre um novo usuário no aplicativo.
```

## Autenticação
A API utiliza o Spring Security para o controle de autenticação. Os seguintes papéis estão disponíveis:

```markdown
USUÁRIO 
ADMIN -> Papel de administrador para gerenciar parceiros (registro de novos parceiros).
ANALYST -> Papel de analista para publicar novos produtos (registro de novos produtos).
 ```
Para acessar os pontos de extremidade protegidos como um usuário ADMIN, forneça as credenciais de autenticação apropriadas no cabeçalho da solicitação com token tipo bearer.

### Insomnia
Anexado em /utilidades o arquivo insomnia usado.

#### USUÁRIO -> Papel padrão do usuário para usuários logados (obter a lista de produtos).
![USUÁRIO](utilidades/insomnia/product-get-role-user.png)

#### ANALYST -> Papel de analista para publicar novos produtos (registro de novos produtos).
![ANALYST](utilidades/insomnia/product-post-role-analyst.png)


## Banco de Dados
O projeto utiliza o [PostgresSQL](https://www.postgresql.org/) como banco de dados.

## Flyway
As migrações necessárias do banco de dados são gerenciadas usando o Flyway.

![Flyway_Diagrama](utilidades/diagramas/flyway-diagram.png)
![Flyway_Estrutura](utilidades/diagramas/flyway-estrutura.png)



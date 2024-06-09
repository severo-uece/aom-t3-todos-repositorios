# Banco de dados da aplicação

## Executar container PostgreSQL com Docker
    
`$ docker run --name postgresql-docker --restart=always -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres`

Caso a porta 5432 já esteja em uso, substitua a primeira parte do parâmetro de publicação (-p) por outra porta. Exemplo: -p **54320**:5432 
    
## Criar banco de dados da aplicação

Utilize o cliente de SQL da sua preferência para criar um banco de dados chamado "customers-db".

`CREATE DATABASE customers-db;`

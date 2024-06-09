# Projetos da disciplina de Arquitetura Orientada a Microsserviços

Este arquivo descreve o processo de execução dos projetos e suas depedências de infraestrutura.
Em termos de componentes de infraestrutura temos: banco de dados PostgreSQL, RabbitMQ, Jaeger, Prometheus e Grafana.
Disponibilizados esses componentes, podemos executar os projetos dos microsserviços individuais.

#### Dependências gerais para execução

- Docker
- Java 17+

## Componentes de Infraestrutura (docker compose)

Para executar os componentes de infraestrutura, como banco de dados e mensageria,
utilize o comando a seguir. Todos os componentes serão executados como containers Docker.

```
$ docker compose up
```

## Projetos Individuais

### Service Discovery 
Serve como catálogo de microsserviços em execução, com seus nomes e endereços.

```
$ cd service-discovery
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

### Config Server
Distribui configurações centralizadas para os demais microsserviços.

```
$ cd config-server
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

### Microsserviços de Negócios
Podem ser inicializados em qualquer ordem.

#### Customer-MS
Microsserviço de gestão de clientes.

```
$ cd customer-ms
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

#### Cart-MS
Microsserviço de gestão de carrinho de compra.

```
$ cd cart-ms
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

#### Payment-MS
Microsserviço de gestão de fluxo de pagamento.

```
$ cd payment-ms
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

#### Email-MS
Microsserviço de envio de email (mock).

```
$ cd email-ms
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

#### Firebase-MS
Microsserviço de envio de notificação Firebase (mock).

```
$ cd firebase-ms
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```

### API Gateway
Microsserviço de entreprosto de comunicação entre outros serviços. Se alimenta do catálogo do Service Discovery e fornece balaceamento de carga automático.

```
$ cd api-gateway
$ mvn clean package -DskipTests
$ mvn spring-boot:run
```
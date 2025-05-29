# Ecommerce Events Project

Este projeto é um desafio técnico em Java para processar eventos de pedidos (orders) usando:
- Spring Boot  
- Spring Batch  
- Spring Data JPA  
- RabbitMQ  
- PostgreSQL  
- Strategy, Factory, Observer, Singleton

---

## Funcionalidades

- Consome mensagens da fila `ecommerce.orders` no RabbitMQ.
- Processa pedidos com lógica diferenciada (padrão ou expresso).
- Persiste os pedidos no banco PostgreSQL.
- Aplica reprocessamento (retry) e envia mensagens falhadas para Dead Letter Queue (DLQ).
- Conta globalmente os pedidos processados.
- Segue arquitetura com múltiplos padrões de projeto.

---

## Padrões de Projeto Aplicados

| Padrão       | Onde está?                                                                                      |
|--------------|-------------------------------------------------------------------------------------------------|
| Strategy     | Pacote `strategy` → Escolhe lógica de processamento (Standard ou Express)                       |
| Factory      | Pacote `factory` → Cria objetos `CustomerOrder` com base no tipo de pedido                      |
| Observer     | Pacote `observer` → Serviços que são notificados automaticamente após processar um pedido       |
| Singleton    | Pacote `singleton` → Classe `OrderStatistics` conta global de pedidos processados               |

---

## Como Rodar

1. Suba o RabbitMQ (pode usar `docker-compose` ou instalação local).
Build + start com Docker:
```bash
docker compose build
docker compose up
```

2. Rode a aplicação:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

3. Acesse:

App → http://localhost:8080

RabbitMQ Management → http://localhost:15672 (login: guest / guest)

Postgres → localhost:5433

## Endpoints disponíveis
| Método | Rota        | Descrição                       |
| ------ |-------------| ------------------------------- |
| GET    | /api/orders | Lista todos os pedidos          |
| POST   | /api/orders | Cria novo pedido e envia à fila |
| GET    | /ping       | Health check (verificar app)    |


## Como Testar
- Crie pedidos via:
```
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"description": "Pedido de teste"}'

```

- Publique uma mensagem simples no RabbitMQ, fila ecommerce.orders.

- Veja nos logs:
  - Pedido processado como padrão ou expresso.
  - Observadores chamados (notificação, faturamento, envio).
  - Pedido persistido no banco.

- Para testar DLQ:
  - Envie mensagem contendo FAIL → deve falhar e ir para a fila ecommerce.orders.dlq.

## Testes
Execute os testes unitários:
```
./mvnw test
```

## Estrutura do Projeto
```
src/main/java/com/ecommerce/ecommerce_events
├── config
│   └── RabbitMQConfig.java
│   └── JobConfig.java
├── domain
│   └── CustomerOrder.java
├── factory
│   └── OrderFactory.java
├── observer
│   ├── OrderObserver.java
│   ├── OrderSubject.java
│   ├── OrderManager.java
│   ├── NotificationService.java
│   ├── BillingService.java
│   └── ShippingService.java
├── processor
│   └── OrderItemProcessor.java
├── singleton
│   └── OrderStatistics.java
├── strategy
│   ├── OrderProcessingStrategy.java
│   ├── StandardOrderProcessingStrategy.java
│   ├── ExpressOrderProcessingStrategy.java
│   └── OrderProcessor.java
├── repository
│   └── OrderRepository.java
├── listener
│   └── OrderEventListener.java
└── EcommerceEventsApplication.java
```

## Autor
Igor Santos

Desafio Técnico Java 2025
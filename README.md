# Ecommerce Events Project

Este projeto é um desafio técnico em Java para processar eventos de pedidos (orders) usando:
- Spring Boot  
- Spring Batch  
- Spring Data JPA  
- RabbitMQ  
- H2 Database  
- Strategy, Factory, Observer, Singleton

---

## Funcionalidades

- Consome mensagens da fila `ecommerce.orders` no RabbitMQ.
- Processa pedidos com lógica diferenciada (padrão ou expresso).
- Persiste os pedidos no banco H2.
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

2. Rode a aplicação:
```bash
./mvnw clean install
./mvnw spring-boot:run

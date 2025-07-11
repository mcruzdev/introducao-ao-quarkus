# Aula 3

---
references:
 - https://quarkus.io/guides/rest-client
 - https://pt.quarkus.io/guides/rest
---

## Em aula

Vamos criar duas aplicações, a primeira vai ser responsável por receber notificações de eventos.

Exemplo de um evento:

```json
{
    "eventName": "PRODUCT_CREATED",
    "event": "{ \"name\": \"Mouse\", \"price\": 200.99 }"
}
```

A segunda vai ser responsável pelo CRUD de usuários, onde cada operação deve enviar uma notificação para o microserviço de `notificações` com a versão mais atualizada do usuário (quando for deleção enviar o produto antes da deleção).
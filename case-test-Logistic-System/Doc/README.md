# Sistema de Processamento de Pedidos – Logística

Este projeto simula um sistema de backend para logística, focado em
absorver picos de requisições e processar pedidos de forma assíncrona.

## Problema
Em horários de pico, milhares de pedidos chegam simultaneamente via webhooks
de diferentes plataformas (marketplace, checkout, afiliados).

## Solução
Foi implementado um pipeline orientado a estados:

1. Ingestão rápida do pedido (Webhook)
2. Persistência imediata no banco
3. Processamento assíncrono via filas
4. Validação de negócio
5. Reserva de estoque com controle de concorrência
6. Cotação de frete
7. Geração de etiqueta

## Tecnologias
- PHP 8+
- Laravel
- Filas (Redis)
- Jobs assíncronos
- MySQL/PostgreSQL

## Arquitetura
Webhook → Controller → Banco → Queue → Workers → Atualização de Status

## Estados do Pedido
- RECEBIDO
- INVALIDO
- AGUARDANDO_ESTOQUE
- ESTOQUE_RESERVADO
- FRETE_COTADO
- ETIQUETA_GERADA

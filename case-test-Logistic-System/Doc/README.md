🔹 PROCESSO 1 — RECEBIMENTO DOS PEDIDOS (WEBHOOK)
- O que acontece

O sistema recebe pedidos de várias plataformas externas (checkout, marketplace, afiliados).
Essas plataformas não entram no sistema “normalmente”, elas chamam um webhook.

- Conceito principal

Entrada externa e imprevisível de dados.

Você não controla:

quando o pedido chega

quantos chegam

se os dados vêm corretos

- Problema real

Em horários específicos, milhares de pedidos podem chegar ao mesmo tempo.

- Abordagem correta

Receber o pedido rapidamente

Fazer apenas validações mínimas (ex: payload existe)

Não processar nada pesado

Enviar o pedido para processamento assíncrono (fila)

- Objetivo

Garantir que o sistema aguente o pico sem cair ou ficar lento.

🔹 PROCESSO 2 — VALIDAÇÃO DO PEDIDO
- O que acontece

Depois que o pedido entra, ele não pode seguir se tiver problemas como:

estoque insuficiente

dados incompletos

produto inexistente

- Conceito principal

Validação de regras de negócio.

Aqui o sistema decide:
- esse pedido é válido ou não?

- Problema real

Confiar cegamente nos dados externos pode:

gerar erro interno

vender produto inexistente

quebrar estoque

- Abordagem correta

Validar campos obrigatórios

Verificar se o produto existe

Conferir se faz sentido continuar

Tudo isso fora da requisição, em background.

- Objetivo

Bloquear pedidos inválidos antes de consumir recursos do sistema.

🔹 PROCESSO 3 — GARANTIA DE ESTOQUE (CONCORRÊNCIA)
- O que acontece

O pedido passou na validação, agora precisamos garantir estoque.

- Conceito principal

Concorrência.

Vários pedidos do mesmo produto podem chegar ao mesmo tempo.

- Problema real

Se dois pedidos checam estoque ao mesmo tempo:

ambos veem “1 unidade disponível”

ambos tentam vender

estoque fica negativo

- Abordagem correta

Garantir o estoque imediatamente após validação

Reservar ou baixar estoque

Se não conseguir → pedido falha

- Objetivo

Evitar venda duplicada e inconsistência de dados.

🔹 PROCESSO 4 — COTAÇÃO DE FRETE
- O que acontece

Com o pedido validado e estoque garantido, o sistema chama:

várias transportadoras externas

APIs de frete

- Conceito principal

Integração com serviços externos.

- Problema real

APIs externas são lentas

podem falhar

custam tempo e dinheiro

- Abordagem correta

Só cotar frete depois que o pedido é válido

Evitar chamadas desnecessárias

Escolher a melhor opção

- Objetivo

Usar recursos externos apenas quando o pedido está garantido.

🔹 PROCESSO 5 — EMISSÃO DE ETIQUETA E GERAÇÃO DE PDF
- O que acontece

Depois da escolha da transportadora:

emitir etiqueta

gerar PDF

armazenar arquivos

- Conceito principal

Processamento pesado e persistência.

- Problema real

Geração de PDF é lenta

envolve I/O

não deve ser refeita se algo falhar antes

- Abordagem correta

Essa etapa só acontece no final

Pedido já está validado

Estoque garantido

Frete definido

- Objetivo

Finalizar o pedido com segurança, sem retrabalho.

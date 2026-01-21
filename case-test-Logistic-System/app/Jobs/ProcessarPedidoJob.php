<?php

namespace App\Jobs;

use App\Models\Pedido;
use App\Models\Produto;
use App\Enums\StatusPedido;

/**
 * Job responsável por processar o pedido em background.
 */
class ProcessarPedidoJob
{
    protected array $dadosPedido;

    public function __construct(array $dadosPedido)
    {
        $this->dadosPedido = $dadosPedido;
    }

    /**
     * Método principal do Job.
     * Aqui acontece todo o fluxo do pedido.
     */
    public function handle()
    {
        // 1️⃣ Validação do pedido
        if (empty($this->dadosPedido['produto_id'])) {
            return;
        }

        // 2️⃣ Criação do pedido
        $pedido = Pedido::create([
            'status' => StatusPedido::PENDENTE
        ]);

        // 3️⃣ Simulação de verificação de estoque
        $produto = Produto::find($this->dadosPedido['produto_id']);

        if (!$produto || $produto->quantidade <= 0) {
            $pedido->status = StatusPedido::ERRO;
            $pedido->save();
            return;
        }

        // 4️⃣ Reserva de estoque (simples)
        $produto->quantidade -= 1;
        $produto->save();

        // 5️⃣ Simulação de cotação de frete
        // Aqui futuramente entrariam APIs externas
        $transportadoraEscolhida = 'Correios';

        // 6️⃣ Simulação de geração de etiqueta
        // PDF e armazenamento seriam feitos aqui
        $pedido->status = StatusPedido::PROCESSADO;
        $pedido->save();
    }
}

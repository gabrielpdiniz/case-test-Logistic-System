<?php

namespace app\Http\Controllers;

use Illuminate\Http\Request;
use App\Jobs\ProcessarPedidoJob;

/**
 * Controller responsável por receber pedidos externos (webhooks).
 */
class PedidoWebhookController extends Controller
{
    /**
     * Endpoint chamado pelas plataformas externas.
     */
    public function receberPedido(Request $request)
    {
        /**
         * Aqui fazemos apenas uma validação básica,
         * só para garantir que algo foi enviado.
         * Validações profundas ficam no Job.
         */
        if (!$request->has('pedido')) {
            return response()->json(['erro' => 'Pedido inválido'], 400);
        }

        /**
         * Dispara o processamento do pedido para uma fila.
         * Assim evitamos travar o sistema em horários de pico.
         */
        ProcessarPedidoJob::dispatch($request->pedido);

        return response()->json(['status' => 'Pedido recebido com sucesso']);
    }
}

<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * Model que representa um pedido.
 */
class Pedido extends Model
{
    protected $fillable = [
        'status'
    ];
}

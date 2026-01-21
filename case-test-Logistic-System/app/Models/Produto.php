<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * Model simples de produto.
 * Usado apenas para controle de estoque.
 */
class Produto extends Model
{
    protected $fillable = [
        'nome',
        'quantidade'
    ];
}

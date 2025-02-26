import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {
    static final int MAX_NOVOS_PRODUTOS = 10;
    static String nomeArquivoDados;
    static Scanner teclado;
    static Produto[] produtosCadastrados;
    static int quantosProdutos;

    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    static void cabecalho() {
        System.out.println("AEDII COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e listar um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    static Produto[] lerProdutos(String nomeArquivoDados) {
        Produto[] vetorProdutos;
        Scanner arqDados = null;
        
        try {
            arqDados = new Scanner(new File(nomeArquivoDados), Charset.forName("UTF-8"));
            int quantosProdutos = Integer.parseInt(arqDados.nextLine());
            vetorProdutos = new Produto[quantosProdutos + MAX_NOVOS_PRODUTOS];

            for (int i = 0; i < quantosProdutos; i++) {
                String linha = arqDados.nextLine();
                vetorProdutos[i] = Produto.criarDoTexto(linha);
            }
        } catch (IOException e) {
            vetorProdutos = null;
        } finally {
            if (arqDados != null) {
                arqDados.close();
            }
        }
        
        return vetorProdutos;
    }
}


    static void listarTodosOsProdutos() {
        for (int i = 0; i < quantosProdutos; i++) {
            System.out.println((i + 1) + " - " + produtosCadastrados[i].toString());
        }
    }

    static void localizarProdutos() {
        String desc;
        String mensagem = "Produto não localizado";
        
        System.out.print("Descrição do produto a localizar: ");
        desc = teclado.nextLine();
        
        ProdutoNaoPercevel produtoALocalizar = new ProdutoNaoPercevel(desc, precoCusto:1);
        Produto localizado = null;

        for (int i = 0; i < produtosCadastrados.length && localizado == null; i++) {
            if (produtosCadastrados[i] != null && produtosCadastrados[i].equals(produtoALocalizar)) {
                localizado = produtosCadastrados[i];
            }
        }
        
        if (localizado != null) {
            mensagem = "Localizado: " + localizado.toString();
        }
        
        System.out.println(mensagem);
    }
}

    static void cadastrarProduto() {
        System.out.print("Digite o tipo do produto (1 - Não Perecível, 2 - Perecível): ");
        int tipo = Integer.parseInt(teclado.nextLine().trim());
        System.out.print("Digite a descrição do produto: ");
        String descricao = teclado.nextLine().trim();
        System.out.print("Digite o preço de custo: ");
        double precoCusto = Double.parseDouble(teclado.nextLine().trim());
        System.out.print("Digite a margem de lucro: ");
        double margemLucro = Double.parseDouble(teclado.nextLine().trim());

        Produto novoProduto = null;
        if (tipo == 1) {
            novoProduto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
        } else if (tipo == 2) {
            System.out.print("Digite a data de validade (dd/MM/yyyy): ");
            LocalDate validade = LocalDate.parse(teclado.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            novoProduto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, validade);
        } else {
            System.out.println("Tipo de produto inválido.");
            return;
        }

        if (quantosProdutos < produtosCadastrados.length) {
            produtosCadastrados[quantosProdutos++] = novoProduto;
        } else {
            System.out.println("Não há espaço para novos produtos.");
        }
    }

    public static void salvarProdutos(String nomeArquivo) {
        try {
            FileWriter arquivoSaida = new FileWriter(nomeArquivo, Charset.forName("UTF-8"));
            arquivoSaida.append("quantosProdutos\n");
            
            for (int i = 0; i < produtosCadastrados.length; i++) {
                if (produtosCadastrados[i] != null) {
                    arquivoSaida.append(produtosCadastrados[i].gerarDadosTexto() + "\n");
                }
            }
            
            arquivoSaida.close();
            System.out.println("Arquivo \"" + nomeArquivo + "\" salvo.");
        } catch (IOException e) {
            System.out.println("Problemas no arquivo \"" + nomeArquivo + "\". Tente novamente.");
        }
    }
}

}

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
        nomeArquivoDados = "dadosProdutos.csv";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        int opcao;
        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> localizarProdutos();
                case 3 -> cadastrarProduto();
            }
            pausa();
        } while (opcao != 0);
        salvarProdutos(nomeArquivoDados);
        teclado.close();
    }
}
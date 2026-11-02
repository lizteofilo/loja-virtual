package Interface;

import modelo.Carrinho;
import modelo.Compra;
import modelo.ItemCarrinho;
import modelo.ItemCompra;
import modelo.Produto;
import pagamento.Credito;
import pagamento.Debito;
import pagamento.Pagamento;
import pagamento.Pix;
import servicos.CompraService;
import servicos.ProdutoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Scanner entrada = new Scanner(System.in);
    private ProdutoService produtoService;
    private CompraService compraService;
    private Carrinho carrinho;
    private String comprador; 

    public Menu(ProdutoService produtoService, CompraService compraService, Carrinho carrinho) {
        this.produtoService = produtoService;
        this.compraService = compraService;
        this.carrinho = carrinho;
    }

    public void iniciar() {
        int opcao;

        do {
            System.out.println("\n===== LOJA VIRTUAL =====");
            System.out.println("1 - Entrar como administrador");
            System.out.println("2 - Entrar como comprador");
            System.out.println("0 - Sair");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuAdministrador();
                case 2 -> menuComprador();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== ADMINISTRADOR ====================

    private void menuAdministrador() {
        int opcao;

        do {
            System.out.println("\n===== MENU DO ADMINISTRADOR =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Cadastrar produto");
            System.out.println("3 - Editar produto");
            System.out.println("4 - Remover produto");
            System.out.println("5 - Consultar produto por ID");
            System.out.println("6 - Ver todas as vendas");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> listarProdutos();
                    case 2 -> cadastrarProduto();
                    case 3 -> editarProduto();
                    case 4 -> removerProduto();
                    case 5 -> consultarProduto();
                    case 6 -> listarTodasVendas();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IOException e) {
                
                System.out.println("Erro ao gravar o arquivo: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoService.listar();
        System.out.println("\n===== PRODUTOS CADASTRADOS =====");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        }

        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    private void cadastrarProduto() throws IOException {
        System.out.println("\n===== CADASTRAR PRODUTO =====");
        int id = lerInteiro("ID: ");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        BigDecimal preco = lerDecimal("Preço: ");
        int estoque = lerInteiro("Estoque: ");

        produtoService.cadastrar(new Produto(id, nome, preco, estoque));
        System.out.println("Produto cadastrado com sucesso!");
    }

    private void editarProduto() throws IOException {
        System.out.println("\n===== EDITAR PRODUTO =====");
        int id = lerInteiro("Informe o ID do produto: ");
        System.out.print("Novo nome: ");
        String nome = entrada.nextLine();
        BigDecimal preco = lerDecimal("Novo preço: ");
        int estoque = lerInteiro("Novo estoque: ");

        produtoService.editar(id, nome, preco, estoque);
        System.out.println("Produto editado com sucesso!");
    }

    private void removerProduto() throws IOException {
        System.out.println("\n===== REMOVER PRODUTO =====");
        int id = lerInteiro("Informe o ID do produto: ");
        produtoService.remover(id);
        System.out.println("Produto removido com sucesso!");
    }

    private void consultarProduto() {
        System.out.println(buscarProduto(lerInteiro("Informe o ID do produto: ")));
    }

    private Produto buscarProduto(int id) {
        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        return produto;
    }

    private void listarTodasVendas() {
        List<Compra> compras = compraService.listarCompras();
        System.out.println("\n===== TODAS AS VENDAS =====");

        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra realizada.");
        }

        for (Compra compra : compras) {
            System.out.println("\n" + compra);
            System.out.println("Data/hora: " + compra.getDataHora().format(DATA_HORA));

            for (ItemCompra item : compra.getItens()) {
                System.out.println("  " + item);
            }
        }
    }

    // ==================== COMPRADOR ====================

    private void menuComprador() {
       
        comprador = lerNome();
        carrinho.limpar(); 
        int opcao;

        do {
            System.out.println("\n===== MENU DO COMPRADOR (" + comprador + ") =====");
            System.out.println("1 - Visualizar produtos disponíveis");
            System.out.println("2 - Adicionar produto ao carrinho");
            System.out.println("3 - Remover produto do carrinho");
            System.out.println("4 - Alterar quantidade");
            System.out.println("5 - Visualizar carrinho");
            System.out.println("6 - Finalizar compra");
            System.out.println("7 - Meu histórico de compras");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> listarProdutos();
                    case 2 -> adicionarCarrinho();
                    case 3 -> removerCarrinho();
                    case 4 -> alterarQuantidadeCarrinho();
                    case 5 -> visualizarCarrinho();
                    case 6 -> finalizarCompra();
                    case 7 -> listarCompras();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao salvar a compra: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void adicionarCarrinho() {
        listarProdutos();
        int id = lerInteiro("\nInforme o ID do produto: ");
        int quantidade = lerInteiro("Quantidade: ");

        carrinho.adicionarProduto(buscarProduto(id), quantidade);
        System.out.println("Produto adicionado ao carrinho!");
    }

    private void removerCarrinho() {
        visualizarCarrinho();
        carrinho.removerProduto(lerInteiro("\nInforme o ID do produto que deseja remover: "));
        System.out.println("Produto removido do carrinho!");
    }

    private void alterarQuantidadeCarrinho() {
        visualizarCarrinho();
        int id = lerInteiro("\nInforme o ID do produto: ");
        int quantidade = lerInteiro("Nova quantidade: ");

        carrinho.alterarQuantidade(id, quantidade);
        System.out.println("Quantidade atualizada!");
    }

    private void visualizarCarrinho() {
        System.out.println("\n===== MEU CARRINHO =====");

        if (carrinho.estaVazio()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }

        for (ItemCarrinho item : carrinho.listarItens()) {
            System.out.println(item);
        }

        System.out.println("------------------------------");
        System.out.println("Subtotal: R$ " + carrinho.calcularTotal());
    }

    private void finalizarCompra() throws IOException {
        System.out.println("\n===== FINALIZAR COMPRA =====");
        visualizarCarrinho();

        if (carrinho.estaVazio()) {
            return;
        }

        System.out.println("\nEscolha a forma de pagamento:");
        System.out.println("1 - Cartão de crédito");
        System.out.println("2 - Cartão de débito (10% de desconto)");
        System.out.println("3 - Pix (5% de desconto)");
        Pagamento pagamento;

        switch (lerInteiro("Opção: ")) {
            case 1 -> pagamento = new Credito();
            case 2 -> pagamento = new Debito();
            case 3 -> pagamento = new Pix();
            default -> {
                System.out.println("Forma de pagamento inválida!");
                return;
            }
        }

        Compra compra = compraService.finalizarCompra(comprador, carrinho, pagamento);

        System.out.println("\n===== COMPRA FINALIZADA =====");
        System.out.println("Compra nº: " + compra.getId());
        System.out.println("Data/hora: " + compra.getDataHora().format(DATA_HORA));
        System.out.println("Comprador: " + compra.getComprador());
        System.out.println("Produtos:");

        for (ItemCompra item : compra.getItens()) {
            System.out.println("  " + item);
        }

        System.out.println("Subtotal: R$ " + compra.getSubtotal());
        System.out.println("Forma de pagamento: " + compra.getFormaPagamento());
        System.out.println("Desconto: R$ " + compra.getDesconto());
        System.out.println("Valor final: R$ " + compra.getValorFinal());
    }

    private void listarCompras() {
        List<Compra> compras = compraService.historico(comprador);
        System.out.println("\n===== HISTÓRICO DE COMPRAS =====");

        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra realizada.");
        }

        for (Compra compra : compras) {
            System.out.println("Compra nº " + compra.getId()
                + " | " + compra.getDataHora().format(DATA_HORA)
                + " | " + compra.getFormaPagamento()
                + " | Valor pago: R$ " + compra.getValorFinal());
        }

        System.out.println("--------------------------------");
        System.out.println("Total gasto: R$ " + compraService.totalGasto(comprador));
    }

    // ==================== LEITURA DO TECLADO ====================


    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private BigDecimal lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return new BigDecimal(entrada.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor válido. Exemplo: 89.90");
            }
        }
    }

    private String lerNome() {
        while (true) {
            System.out.print("Nome do comprador: ");
            String nome = entrada.nextLine().trim();

            if (nome.isEmpty() || nome.contains(";") || nome.contains(",") || nome.contains("|")) {
                System.out.println("Nome inválido (não pode ser vazio nem conter ; , |).");
            } else {
                return nome;
            }
        }
    }
}

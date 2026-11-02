package servicos;

import excecao.ProdutoNaoEncontradoException;
import modelo.ItemCarrinho;
import modelo.Produto;
import repositorio.ProdutoRepositorio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private List<Produto> produtos;
    private final ProdutoRepositorio repositorio;

    public ProdutoService(ProdutoRepositorio repositorio) throws IOException {
        this.repositorio = repositorio;
        this.produtos = repositorio.carregar();
    }

    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    public boolean existe(int id) {
        return buscarPorId(id) != null;
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    private Produto buscarObrigatorio(int id) {
        Produto produto = buscarPorId(id);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado.");
        }

        return produto;
    }

    public void cadastrar(Produto produto) throws IOException {
        if (existe(produto.getId())) {
            throw new IllegalArgumentException("Já existe um produto com esse ID.");
        }

        List<Produto> novaLista = new ArrayList<>(produtos);
        novaLista.add(produto);

        repositorio.salvar(novaLista);
        produtos = novaLista;
    }

    public void editar(int id, String nome, BigDecimal preco, int estoque) throws IOException {
        Produto atual = buscarObrigatorio(id);
        Produto editado = new Produto(id, nome, preco, estoque); 

        List<Produto> novaLista = new ArrayList<>(produtos);
        novaLista.set(novaLista.indexOf(atual), editado);
        repositorio.salvar(novaLista);
        atual.setNome(nome);
        atual.setPreco(preco);
        atual.setEstoque(estoque);
    }

    public void remover(int id) throws IOException {
        Produto produto = buscarObrigatorio(id);

        List<Produto> novaLista = new ArrayList<>(produtos);
        novaLista.remove(produto);

        repositorio.salvar(novaLista);
        produtos = novaLista;
    }

    public void baixarEstoque(List<ItemCarrinho> itens) throws IOException {
        alterarEstoque(itens, -1);
    }

    public void devolverEstoque(List<ItemCarrinho> itens) throws IOException {
        alterarEstoque(itens, +1);
    }

    private void alterarEstoque(List<ItemCarrinho> itens, int sinal) throws IOException {

        List<Produto> copia = new ArrayList<>();

        for (Produto p : produtos) {
            int novoEstoque = p.getEstoque();

            for (ItemCarrinho item : itens) {
                if (item.getProduto().getId() == p.getId()) {
                    novoEstoque += sinal * item.getQuantidade();
                }
            }

            copia.add(new Produto(p.getId(), p.getNome(), p.getPreco(), novoEstoque));
        }

        repositorio.salvar(copia);
        for (ItemCarrinho item : itens) {
            Produto p = buscarObrigatorio(item.getProduto().getId());
            p.setEstoque(p.getEstoque() + sinal * item.getQuantidade());
        }
    }
}

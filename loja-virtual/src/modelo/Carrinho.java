package modelo;

import excecao.ProdutoNaoEncontradoException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemCarrinho item = buscarItem(produto.getId());

        if (item != null) {
            item.alterarQuantidade(item.getQuantidade() + quantidade);
        } else {
            itens.add(new ItemCarrinho(produto, quantidade));
        }
    }

    public void removerProduto(int id) {
        itens.remove(buscarItemObrigatorio(id));
    }

    public void alterarQuantidade(int id, int quantidade) {
        buscarItemObrigatorio(id).alterarQuantidade(quantidade);
    }

    private ItemCarrinho buscarItem(int id) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == id) {
                return item;
            }
        }

        return null;
    }

    private ItemCarrinho buscarItemObrigatorio(int id) {
        ItemCarrinho item = buscarItem(id);

        if (item == null) {
            throw new ProdutoNaoEncontradoException(
                "O produto " + id + " não está no carrinho."
            );
        }

        return item;
    }

    public List<ItemCarrinho> listarItens() {
        return new ArrayList<>(itens);
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrinho item : itens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }

    public void limpar() {
        itens.clear();
    }
}

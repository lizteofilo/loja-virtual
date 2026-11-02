package modelo;

import excecao.EstoqueInsuficienteException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Produto {

    private final int id; 
    private String nome;
    private BigDecimal preco;
    private int estoque;

    public Produto(int id, String nome, BigDecimal preco, int estoque) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        this.id = id;
        setNome(nome);
        setPreco(preco);
        setEstoque(estoque);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        if (nome.contains(";") || nome.contains(",") || nome.contains("|")) {
            throw new IllegalArgumentException("O nome não pode conter ; , |");
        }

        this.nome = nome.trim();
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }

        this.preco = preco.setScale(2, RoundingMode.HALF_UP);
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }

        this.estoque = estoque;
    }

    public void retirarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        if (quantidade > estoque) {
            throw new EstoqueInsuficienteException(nome, estoque);
        }

        estoque -= quantidade;
    }

    @Override
    public String toString() {
        return "ID: " + id
            + " | Nome: " + nome
            + " | Preço: R$ " + preco
            + " | Estoque: " + estoque;
    }
}

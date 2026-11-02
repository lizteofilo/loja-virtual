package modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private int id;
    private LocalDateTime dataHora;
    private String comprador;
    private List<ItemCompra> itens;
    private String formaPagamento;
    private BigDecimal desconto;

    public Compra(int id, LocalDateTime dataHora, String comprador,
                  List<ItemCompra> itens, String formaPagamento,
                  BigDecimal desconto) {
        this.id = id;
        this.dataHora = dataHora;
        this.comprador = comprador;
        this.itens = new ArrayList<>(itens);
        this.formaPagamento = formaPagamento;
        this.desconto = desconto;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getComprador() {
        return comprador;
    }

    public List<ItemCompra> getItens() {
        return new ArrayList<>(itens);
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    // Soma dos itens, antes do desconto.
    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemCompra item : itens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }

    public BigDecimal getValorFinal() {
        return getSubtotal().subtract(desconto);
    }

    @Override
    public String toString() {
        return "Compra ID: " + id
            + " | Comprador: " + comprador
            + " | Pagamento: " + formaPagamento
            + " | Valor final: R$ " + getValorFinal();
    }
}

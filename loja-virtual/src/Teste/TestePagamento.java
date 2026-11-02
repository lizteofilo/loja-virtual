package Teste;
import pagamento.Pagamento;
import pagamento.Credito;
import pagamento.Debito;
import pagamento.Pix;

import java.math.BigDecimal;

public class TestePagamento {

    public static void main(String[] args) {

        BigDecimal valor = new BigDecimal("100.00");

        Pagamento pagamento;

        // Crédito
        pagamento = new Credito();
        testarPagamento(pagamento, valor);

        // Débito
        pagamento = new Debito();
        testarPagamento(pagamento, valor);

        // PIX
        pagamento = new Pix();
        testarPagamento(pagamento, valor);
    }

    public static void testarPagamento(
            Pagamento pagamento, BigDecimal valor) {

        BigDecimal valorFinal = pagamento.calcularValorFinal(valor);

        System.out.println("\nValor original: R$ " + valor);
        System.out.println("Valor final: R$ " + valorFinal);

        pagamento.pagar(valorFinal);
    }
}

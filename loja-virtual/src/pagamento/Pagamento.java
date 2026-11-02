package pagamento;

import java.math.BigDecimal;

// Cada forma de pagamento sabe calcular o próprio valor final e processar o
// pagamento. O CompraService só usa o tipo Pagamento, sem if/switch para
// descobrir qual é (polimorfismo).
public abstract class Pagamento {

    public abstract String getNome();

    public abstract BigDecimal calcularValorFinal(BigDecimal subtotal);

    public abstract boolean pagar(BigDecimal valor);

    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        return subtotal.subtract(calcularValorFinal(subtotal));
    }
}

package pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Credito extends Pagamento {

    @Override
    public String getNome() {
        return "Cartão de crédito";
    }

    @Override
    public BigDecimal calcularValorFinal(BigDecimal subtotal) {
        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean pagar(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}

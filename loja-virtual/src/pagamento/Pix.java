package pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Pix extends Pagamento {

    @Override
    public String getNome() {
        return "Pix";
    }

    @Override
    public BigDecimal calcularValorFinal(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean pagar(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}

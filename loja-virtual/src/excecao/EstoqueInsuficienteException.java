package excecao;

public class EstoqueInsuficienteException extends IllegalArgumentException {

    public EstoqueInsuficienteException(String produto, int disponivel) {
        super("Estoque insuficiente para " + produto
            + ". Disponível: " + disponivel + ".");
    }
}

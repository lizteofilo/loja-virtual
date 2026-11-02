package excecao;

public class ProdutoNaoEncontradoException extends IllegalArgumentException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

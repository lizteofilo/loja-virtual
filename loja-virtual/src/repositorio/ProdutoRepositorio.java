package repositorio;

import modelo.Produto;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorio {

    private final Path arquivo;

    public ProdutoRepositorio() throws IOException {
        this(Paths.get("dados", "produtos.txt"));
    }

    public ProdutoRepositorio(Path arquivo) throws IOException {
        this.arquivo = arquivo;
        Files.createDirectories(arquivo.toAbsolutePath().getParent());

        if (Files.notExists(arquivo)) {
            Files.createFile(arquivo);
        }
    }

    public void salvar(List<Produto> produtos) throws IOException {
        List<String> linhas = new ArrayList<>();

        for (Produto p : produtos) {
            linhas.add(p.getId() + ";" + p.getNome() + ";" + p.getPreco() + ";" + p.getEstoque());
        }

        Path temporario = Paths.get(arquivo + ".tmp");
        Files.write(temporario, linhas);
        Files.move(temporario, arquivo,
            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public List<Produto> carregar() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        List<String> linhas = Files.readAllLines(arquivo);

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);

            if (linha.isBlank()) {
                continue;
            }

            try {
                String[] dados = linha.split(";");

                produtos.add(new Produto(
                    Integer.parseInt(dados[0]),
                    dados[1],
                    new BigDecimal(dados[2]),
                    Integer.parseInt(dados[3])
                ));

            } catch (RuntimeException e) {
                throw new IOException("Linha " + (i + 1) + " do arquivo de produtos está inválida.");
            }
        }

        return produtos;
    }
}

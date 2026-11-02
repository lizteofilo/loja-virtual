package repositorio;

import modelo.Compra;
import modelo.ItemCompra;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraRepositorio {

    private final Path arquivo;

    public CompraRepositorio() throws IOException {
        this(Paths.get("dados", "compras.txt"));
    }

    public CompraRepositorio(Path arquivo) throws IOException {
        this.arquivo = arquivo;
        Files.createDirectories(arquivo.toAbsolutePath().getParent());

        if (Files.notExists(arquivo)) {
            Files.createFile(arquivo);
        }
    }

    public void salvar(List<Compra> compras) throws IOException {
        List<String> linhas = new ArrayList<>();

        for (Compra c : compras) {
            List<String> itens = new ArrayList<>();

            for (ItemCompra item : c.getItens()) {
                itens.add(item.getNomeProduto() + "," + item.getPrecoUnitario() + "," + item.getQuantidade());
            }

            linhas.add(c.getId() + ";" + c.getDataHora() + ";" + c.getComprador() + ";"
                + c.getFormaPagamento() + ";" + c.getSubtotal() + ";" + c.getDesconto() + ";"
                + c.getValorFinal() + ";" + String.join("|", itens));
        }

        Path temporario = Paths.get(arquivo + ".tmp");
        Files.write(temporario, linhas);
        Files.move(temporario, arquivo,
            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public List<Compra> carregar() throws IOException {
        List<Compra> compras = new ArrayList<>();
        List<String> linhas = Files.readAllLines(arquivo);

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);

            if (linha.isBlank()) {
                continue;
            }

            try {
                String[] dados = linha.split(";");
                List<ItemCompra> itens = new ArrayList<>();

                for (String texto : dados[7].split("\\|")) {
                    String[] item = texto.split(",");
                    itens.add(new ItemCompra(item[0], new BigDecimal(item[1]), Integer.parseInt(item[2])));
                }

                compras.add(new Compra(
                    Integer.parseInt(dados[0]),
                    LocalDateTime.parse(dados[1]),
                    dados[2],
                    itens,
                    dados[3],
                    new BigDecimal(dados[5])
                ));

            } catch (RuntimeException e) {
                throw new IOException("Linha " + (i + 1) + " do arquivo de compras está inválida.");
            }
        }

        return compras;
    }
}

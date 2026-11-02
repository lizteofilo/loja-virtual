# Loja Virtual

## Como executar

Abra a pasta do projeto no VS Code e rode `src/main/Main.java`, ou use o terminal na pasta do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out main.Main
```

O programa precisa ser executado a partir da pasta do projeto, porque lê e grava na pasta `dados/`.

## Arquivos de dados

`dados/produtos.txt`, um produto por linha:
```
id;nome;preco;estoque
1;Teclado mecânico;189.90;10
```

`dados/compras.txt`, uma compra por linha. Os itens são separados por `|`, no formato `nome,preço,quantidade`:
```
id;dataHora;comprador;formaPagamento;subtotal;desconto;valorFinal;itens
1;2026-11-02T18:28;Maria;Pix;484.80;24.24;460.56;Teclado mecânico,189.90,2|Cabo HDMI,35.00,3
```

Como `;`, `,` e `|` são separadores, eles não são aceitos em nomes.

## Pacotes

- `modelo`: Produto, Carrinho, ItemCarrinho, Compra, ItemCompra
- `pagamento`: Pagamento (abstrata), Credito, Debito, Pix
- `repositorio`: leitura e gravação dos arquivos
- `servicos`: regras de negócio (ProdutoService, CompraService)
- `excecao`: ProdutoNaoEncontradoException, EstoqueInsuficienteException
- `Interface`: Menu
- `Teste`: testes

## Polimorfismo (item 4)

Cada subclasse de `Pagamento` tem o próprio `calcularValorFinal()` e `pagar()`.
O `CompraService` só usa o tipo `Pagamento`, então não precisa de `if` para
saber qual é a forma de pagamento.

## Estratégia de consistência (item 5)

1. Tudo é verificado antes de gravar: o carrinho, o estoque e o pagamento.
2. Cada arquivo é gravado primeiro num `.tmp` e depois renomeado. Se o
   programa cair no meio, o arquivo original não estraga.
3. Primeiro o estoque é gravado, depois a compra. Se a compra não puder ser
   gravada, o estoque é devolvido (`devolverEstoque`).
4. A memória só é alterada depois que o arquivo foi gravado com sucesso.

Assim nunca existe estoque reduzido sem compra registrada, nem compra
registrada sem baixa no estoque. O `TesteCompraService` simula uma falha ao
gravar a compra e confere que o estoque volta ao valor anterior.

Cada item da compra guarda uma cópia do preço, então mudar o preço do produto
depois não altera o histórico.

## Exceções tratadas (item 8)

- Produto inexistente: `ProdutoNaoEncontradoException`
- Estoque insuficiente: `EstoqueInsuficienteException`
- Quantidade inválida, preço negativo, ID duplicado, carrinho vazio: `IllegalArgumentException`
- Letra digitada onde se espera número: o programa pergunta de novo
- Erro ao gravar arquivo: `IOException`, a operação é desfeita e uma mensagem aparece

Em todos os casos o programa mostra a mensagem e continua funcionando.

## Testes

`TesteCompraService`, `TesteCompraRepositorio` e `TesteProdutoService` usam
uma pasta temporária e não alteram `dados/`.

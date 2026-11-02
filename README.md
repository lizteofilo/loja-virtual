<h1 align="center">🛒 Loja Virtual em Java</h1>

```text
          ██╗      ██████╗      ██╗ █████╗
          ██║     ██╔═══██╗     ██║██╔══██╗
          ██║     ██║   ██║     ██║███████║
          ██║     ██║   ██║██   ██║██╔══██║
          ███████╗╚██████╔╝╚█████╔╝██║  ██║
          ╚══════╝ ╚═════╝  ╚════╝ ╚═╝  ╚═╝

██╗   ██╗██╗██████╗ ████████╗██╗   ██╗ █████╗ ██╗
██║   ██║██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██║
██║   ██║██║██████╔╝   ██║   ██║   ██║███████║██║
╚██╗ ██╔╝██║██╔══██╗   ██║   ██║   ██║██╔══██║██║
 ╚████╔╝ ██║██║  ██║   ██║   ╚██████╔╝██║  ██║███████╗
  ╚═══╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝

  [ 1 ] Administrador   [ 2 ] Comprador   [ 0 ] Sair
```

<p align="center">
  <b>☕ Java 14+</b> · <b>🧱 POO</b> · <b>🖥️ Terminal</b> · <b>💾 Dados em TXT</b> · <b>✅ Concluído</b>
</p>

<p align="center">
  <b>Uma loja virtual completa no terminal</b>: cadastro de produtos, carrinho de compras,<br>
  três formas de pagamento e histórico de compras salvo em arquivo.
</p>

<p align="center">
  <a href="#-como-executar">Executar</a> •
  <a href="#-o-sistema-em-ação">Demonstração</a> •
  <a href="#-consistência-dos-dados">Consistência</a> •
  <a href="#️-tratamento-de-exceções">Exceções</a>
</p>

---

## ✨ Funcionalidades

<table>
<tr>
<td width="50%" valign="top">

### 🛠️ Administrador
- ➕ Cadastrar produtos
- 📋 Listar e 🔍 consultar por ID
- ✏️ Editar e 🗑️ remover
- 📊 Ver todas as vendas
- ✅ Validação: ID duplicado, preço negativo e estoque inválido são recusados

</td>
<td width="50%" valign="top">

### 🛍️ Comprador
- 👀 Ver produtos disponíveis
- 🛒 Adicionar, remover e alterar itens do carrinho
- 🧾 Ver o carrinho com o subtotal
- 💳 Finalizar a compra
- 📜 Consultar o histórico e o total gasto

</td>
</tr>
</table>

### 💰 Formas de pagamento

| | Forma | Regra | Exemplo (R$ 100,00) |
|:-:|---|---|---|
| 💳 | **Cartão de crédito** | Valor integral | R$ 100,00 |
| 🏦 | **Cartão de débito** | 10% de desconto | R$ 90,00 |
| ⚡ | **Pix** | 5% de desconto | R$ 95,00 |

---

## 🚀 Como executar

> **Requisito:** Java 14 ou superior

**No VS Code:** abra a pasta do projeto, abra `src/main/Main.java` e clique em ▶️ **Run**.

**Pelo terminal**, na pasta raiz do projeto:

```bash
# Linux / macOS
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out main.Main
```

```powershell
# Windows (PowerShell)
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out main.Main
```

> [!IMPORTANT]
> Execute sempre a partir da **pasta raiz** do projeto, porque o programa lê e grava os arquivos da pasta `dados/`.

---

## 🎬 O sistema em ação

### 1. O administrador consulta os produtos
```text
===== LOJA VIRTUAL =====
1 - Entrar como administrador
2 - Entrar como comprador
0 - Sair
Escolha uma opção: 1

===== MENU DO ADMINISTRADOR =====
1 - Listar produtos
2 - Cadastrar produto
3 - Editar produto
4 - Remover produto
5 - Consultar produto por ID
6 - Ver todas as vendas
0 - Voltar
Escolha uma opção: 1

===== PRODUTOS CADASTRADOS =====
ID: 1 | Nome: Teclado mecânico | Preço: R$ 189.90 | Estoque: 10
ID: 2 | Nome: Mouse sem fio | Preço: R$ 79.90 | Estoque: 25
ID: 3 | Nome: Monitor 24" | Preço: R$ 899.00 | Estoque: 5
ID: 4 | Nome: Headset | Preço: R$ 149.90 | Estoque: 8
ID: 5 | Nome: Webcam Full HD | Preço: R$ 229.00 | Estoque: 6
```

### 2. O comprador monta o carrinho
```text
===== LOJA VIRTUAL =====
Escolha uma opção: 2
Nome do comprador: Maria

===== MENU DO COMPRADOR (Maria) =====
1 - Visualizar produtos disponíveis
2 - Adicionar produto ao carrinho
3 - Remover produto do carrinho
4 - Alterar quantidade
5 - Visualizar carrinho
6 - Finalizar compra
7 - Meu histórico de compras
0 - Voltar
Escolha uma opção: 2
Informe o ID do produto: 1
Quantidade: 2
Produto adicionado ao carrinho!

Escolha uma opção: 2
Informe o ID do produto: 2
Quantidade: 1
Produto adicionado ao carrinho!

Escolha uma opção: 5

===== MEU CARRINHO =====
1 | Teclado mecânico | Quantidade: 2 | Preço unitário: R$ 189.90 | Subtotal: R$ 379.80
2 | Mouse sem fio | Quantidade: 1 | Preço unitário: R$ 79.90 | Subtotal: R$ 79.90
------------------------------
Subtotal: R$ 459.70
```

### 3. A compra é finalizada no Pix e aparece no histórico
```text
===== FINALIZAR COMPRA =====
Escolha a forma de pagamento:
1 - Cartão de crédito
2 - Cartão de débito (10% de desconto)
3 - Pix (5% de desconto)
Opção: 3

===== COMPRA FINALIZADA =====
Compra nº: 1
Data/hora: 02/11/2026 20:03
Comprador: Maria
Produtos:
  Teclado mecânico | Quantidade: 2 | Preço unitário: R$ 189.90 | Subtotal: R$ 379.80
  Mouse sem fio | Quantidade: 1 | Preço unitário: R$ 79.90 | Subtotal: R$ 79.90
Subtotal: R$ 459.70
Forma de pagamento: Pix
Desconto: R$ 22.98
Valor final: R$ 436.72

Escolha uma opção: 7

===== HISTÓRICO DE COMPRAS =====
Compra nº 1 | 02/11/2026 20:03 | Pix | Valor pago: R$ 436.72
--------------------------------
Total gasto: R$ 436.72
```

> Saída de uma execução real do programa. Os números depois de `Escolha uma opção:`, `Quantidade:` e similares são o que o usuário digitou.

---

<details>
<summary><b>📁 Estrutura de pastas</b> (clique para abrir)</summary>

```
loja-virtual/
├── 📂 dados/
│   ├── produtos.txt
│   └── compras.txt
└── 📂 src/
    ├── main/        Main.java
    ├── Interface/   Menu.java
    ├── modelo/      Produto · Carrinho · ItemCarrinho · Compra · ItemCompra
    ├── pagamento/   Pagamento · Credito · Debito · Pix
    ├── servicos/    ProdutoService · CompraService
    ├── repositorio/ ProdutoRepositorio · CompraRepositorio
    ├── excecao/     ProdutoNaoEncontradoException · EstoqueInsuficienteException
    └── Teste/       testes
```
</details>

---

## 🧩 Polimorfismo no pagamento

Cada forma de pagamento sabe **calcular o próprio valor**. O `CompraService` trabalha só com o tipo abstrato `Pagamento` e não precisa de nenhum `if` para saber qual forma foi escolhida:

```java
// CompraService: funciona igual para crédito, débito ou Pix
BigDecimal valorFinal = pagamento.calcularValorFinal(subtotal);
BigDecimal desconto   = pagamento.calcularDesconto(subtotal);

if (!pagamento.pagar(valorFinal)) { ... }
```

```java
public class Pix extends Pagamento {
    @Override
    public BigDecimal calcularValorFinal(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }
}
```

> 💡 Para criar uma nova forma de pagamento (um boleto, por exemplo), basta criar uma nova subclasse. O `CompraService` não muda.

---

## 🔒 Consistência dos dados

> **Objetivo:** se algo der errado na finalização, **nunca** pode existir uma compra registrada sem baixa no estoque, nem estoque reduzido sem a compra registrada.

A estratégia tem quatro partes:

| | Técnica | Como funciona |
|:-:|---|---|
| 1️⃣ | **Validar antes de gravar** | Carrinho, estoque e pagamento são verificados antes de mexer em qualquer arquivo |
| 2️⃣ | **Gravação segura** | Cada arquivo é gravado primeiro em `.tmp` e depois renomeado por cima do original. Se o programa cair no meio, o original continua inteiro |
| 3️⃣ | **Desfazer em caso de erro** | O estoque é gravado antes da compra. Se a compra falhar, `devolverEstoque()` restaura o estoque |
| 4️⃣ | **Memória depois do disco** | Os dados em memória só mudam depois que o arquivo foi gravado com sucesso |

> 🧪 O `TesteCompraService` **simula um disco cheio** na hora de gravar a compra e confirma que o estoque volta ao valor anterior, tanto na memória quanto no arquivo.

---

## 💾 Armazenamento

<details>
<summary><b>📄 produtos.txt</b></summary>

```
id;nome;preco;estoque
1;Teclado mecânico;189.90;10
2;Mouse sem fio;79.90;25
```
</details>

<details>
<summary><b>📄 compras.txt</b></summary>

```
id;dataHora;comprador;formaPagamento;subtotal;desconto;valorFinal;itens
1;2026-11-02T20:03;Maria;Pix;459.70;22.98;436.72;Teclado mecânico,189.90,2|Mouse sem fio,79.90,1
```
Os itens são separados por `|`, no formato `nome,preço unitário,quantidade`.
</details>

> 🏷️ Cada item da compra guarda uma **cópia do preço** do momento da compra. Se o preço do produto mudar depois, o histórico continua com o valor que foi pago.

---

## ⚠️ Tratamento de exceções

| Situação | Exceção | O que acontece |
|---|---|---|
| 🔍 Produto inexistente | `ProdutoNaoEncontradoException` | Mensagem e volta ao menu |
| 📦 Estoque insuficiente | `EstoqueInsuficienteException` | Mensagem; o carrinho é mantido |
| 🔢 Quantidade ou preço inválido, ID duplicado | `IllegalArgumentException` | Mensagem e volta ao menu |
| ⌨️ Letra digitada onde se espera número | `NumberFormatException` | O sistema pergunta de novo |
| 💾 Erro ao ler ou gravar arquivo | `IOException` | A operação é desfeita e uma mensagem aparece |

> Em todos os casos o sistema mostra uma mensagem clara e **continua funcionando**.

---

## 👥 Integrantes

| | Nome | GitHub |
|:-:|---|---|
| 👩‍💻 | Eliza Teofilo | [@usuario1](https://github.com/) |
| 👨‍💻 | Lucas Davi | [@usuario2](https://github.com/) |


<p align="center">
  <sub>Desenvolvido para a disciplina de <b>Programação Orientada a Objetos</b> 🎓</sub>
</p>

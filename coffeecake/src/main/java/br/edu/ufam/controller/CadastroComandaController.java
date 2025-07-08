package br.edu.ufam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.Main;
import br.edu.ufam.model.ClienteModel;
import br.edu.ufam.model.ComandaModel;
import br.edu.ufam.model.ComandaProdutoTableModel;
import br.edu.ufam.model.UsuarioModel;
import br.edu.ufam.service.ComandaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadastroComandaController {
    private final ComandaService comandaService = new ComandaService();

    private int idComanda = -1;

    private ClienteModel clienteComanda;
    private List<ComandaProdutoTableModel> produtosComanda;

    @FXML
    private Label lblUsuarioLogado;
    @FXML
    private Label lblNomeCliente;
    @FXML
    private TextField txtValorTotal;
    @FXML
    private TextField txtValorPago;
    @FXML
    private TextField txtTroco;
    @FXML
    private TextArea txtObservacoes;
    @FXML
    private TableView<ComandaProdutoTableModel> tableView;
    @FXML
    private TableColumn<ComandaProdutoTableModel, Integer> colId;
    @FXML
    private TableColumn<ComandaProdutoTableModel, String> colNomeProduto;
    @FXML
    private TableColumn<ComandaProdutoTableModel, Integer> colQuantidade;
    @FXML
    private TableColumn<ComandaProdutoTableModel, Float> colPrecoUnitario;
    @FXML
    private TableColumn<ComandaProdutoTableModel, Void> colAcao;

    @FXML
    private Label lblTitulo;

    @FXML
    public void initialize() {
        lblTitulo.setText("Comanda");
        lblUsuarioLogado.setText("Usuário Logado: " + Main.usuarioLogado.getNome());

        configurarColunaRemover();
        txtValorPago.textProperty().addListener((obs, oldValue, newValue) -> atualizarTroco());
        aplicarMascaraMonetaria(txtValorPago);
    }

    private void setClienteComanda(ClienteModel cliente) {
        lblNomeCliente.setText("Cliente: " + cliente.getNome());
    }

    @FXML
    public void adicionarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("controller/dialogo_buscar_cliente.fxml"));
            VBox layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(Main.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setTitle("Buscar Cliente");
            dialogStage.setScene(new Scene(layout));

            DialogoBuscarClienteController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            ClienteModel cliente = controller.getClienteSelecionado();
            if (cliente != null) {
                clienteComanda = cliente;
                setClienteComanda(clienteComanda);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarProdutos() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tableView.getItems().clear();
        tableView.getItems().addAll(produtosComanda != null ? produtosComanda : new ArrayList<>());
    }

    @FXML
    public void adicionarProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("controller/dialogo_buscar_produto.fxml"));
            VBox layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(Main.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setTitle("Adicionar Produto");
            dialogStage.setScene(new Scene(layout));

            DialogoBuscarProdutoController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            ComandaProdutoTableModel produto = controller.getProdutoSelecionado();
            if (produto != null) {
                if (produtosComanda == null) {
                    produtosComanda = new ArrayList<>();
                }
                produtosComanda.add(produto);
                carregarProdutos();
                atualizarValorTotal();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarValorTotal() {
        if (produtosComanda == null || produtosComanda.isEmpty()) {
            txtValorTotal.setText("R$ 0,00");
            return;
        }

        float total = 0;
        for (ComandaProdutoTableModel p : produtosComanda) {
            total += p.getQuantidade() * p.getPreco();
        }

        txtValorTotal.setText(String.format("R$ %.2f", total));
    }

    private void configurarColunaRemover() {
        colAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Remover");

            {
                btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 11px;");
                btn.setOnAction(event -> {
                    ComandaProdutoTableModel produto = getTableView().getItems().get(getIndex());
                    produtosComanda.remove(produto);
                    carregarProdutos();
                    atualizarValorTotal(); // se estiver usando
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private void atualizarTroco() {
        try {
            String valorPagoStr = txtValorPago.getText().replace("R$", "").replace(",", ".").trim();
            String valorTotalStr = txtValorTotal.getText().replace("R$", "").replace(",", ".").trim();

            float valorPago = Float.parseFloat(valorPagoStr);
            float valorTotal = Float.parseFloat(valorTotalStr);

            float troco = valorPago - valorTotal;

            txtTroco.setText(String.format("R$ %.2f", troco));
        } catch (NumberFormatException e) {
            txtTroco.setText("Valor inválido");
        }
    }

    private void aplicarMascaraMonetaria(TextField campo) {
        campo.textProperty().addListener((obs, oldValue, newValue) -> {
            String valorNumerico = newValue.replaceAll("[^\\d]", "");

            if (valorNumerico.isEmpty()) {
                campo.setText("");
                return;
            }

            double valor = Double.parseDouble(valorNumerico) / 100;

            campo.setText(String.format("R$ %.2f", valor));

            campo.positionCaret(campo.getText().length());
        });
    }

    @FXML
    public void clickSalvar() {
        salvarComanda("ABERTA");
    }

    @FXML
    public void clickFinalizar() {
        if (produtosComanda == null || produtosComanda.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao finalizar comanda");
            alert.setContentText(
                    "Nenhum produto adicionado à comanda. Por favor, adicione produtos antes de finalizar.");
            alert.showAndWait();
            return;
        }

        salvarComanda("FECHADA");
    }

    public void salvarComanda(String operacao) {
        if (clienteComanda != null) {
            int id = idComanda;
            String dataCriacao = new java.sql.Timestamp(System.currentTimeMillis()).toString();
            String status = operacao;
            ClienteModel cliente = clienteComanda;
            UsuarioModel usuario = Main.usuarioLogado;
            float valorTotal = txtValorTotal.getText().replace("R$", "").replace(",", ".").trim().isEmpty() ? 0
                    : Float.parseFloat(txtValorTotal.getText().replace("R$", "").replace(",", ".").trim());

            ComandaModel comanda = new ComandaModel(id, dataCriacao, status, cliente, usuario, valorTotal);
            if (idComanda == -1) {
                comandaService.cadastrarComanda(comanda, produtosComanda);
            } else {
                comandaService.atualizarComanda(comanda, produtosComanda);
            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar comanda");
            alert.setContentText(
                    "Nenhum cliente selecionado. Por favor, adicione um cliente antes de finalizar a comanda.");
            alert.showAndWait();
        }

        try {
            Main.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setComanda(ComandaModel comanda) {
        idComanda = comanda.getId();
        clienteComanda = comanda.getCliente();
        produtosComanda = comandaService.listarComandaProduto(idComanda);
        carregarProdutos();
        atualizarValorTotal();
        setClienteComanda(clienteComanda);
    }
}

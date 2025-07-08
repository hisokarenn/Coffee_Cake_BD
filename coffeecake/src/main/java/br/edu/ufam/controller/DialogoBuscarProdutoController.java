package br.edu.ufam.controller;

import br.edu.ufam.model.ComandaProdutoTableModel;
import br.edu.ufam.model.ProdutoModel;
import br.edu.ufam.service.ProdutoService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DialogoBuscarProdutoController {
    private final ProdutoService produtoService = new ProdutoService();

    private ComandaProdutoTableModel comandaProdutoTableModel;
    private Stage dialogStage;

    @FXML
    private TextField txtBuscarProduto;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TableView<ProdutoModel> tableView;
    @FXML
    private TableColumn<ProdutoModel, Integer> colId;
    @FXML
    private TableColumn<ProdutoModel, String> colNome;
    @FXML
    private TableColumn<ProdutoModel, String> colDescricao;
    @FXML
    private TableColumn<ProdutoModel, Integer> colQuantidade;
    @FXML
    private TableColumn<ProdutoModel, Double> colPreco;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        carregarProdutos();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                int quantidade = 1;
                try {
                    quantidade = Integer.parseInt(txtQuantidade.getText());
                    if (quantidade <= 0) {
                        txtQuantidade.setText("1");
                        quantidade = 1;
                    }
                } catch (NumberFormatException e) {
                    txtQuantidade.setText("1");
                }

                ProdutoModel produtoSelecionado = tableView.getSelectionModel().getSelectedItem();

                comandaProdutoTableModel = new ComandaProdutoTableModel(
                        produtoSelecionado.getId(),
                        produtoSelecionado.getNome(),
                        quantidade,
                        produtoSelecionado.getPreco());

                dialogStage.close();
            }
        });
    }

    private void carregarProdutos() {
        tableView.getItems().clear();
        tableView.getItems().addAll(produtoService.listarProdutos());
    }

    public ComandaProdutoTableModel getProdutoSelecionado() {
        return comandaProdutoTableModel;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

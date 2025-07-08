package br.edu.ufam.controller;

import br.edu.ufam.Main;
import br.edu.ufam.model.ProdutoModel;
import br.edu.ufam.service.ProdutoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaProdutoController {
    private final ProdutoService produtoService = new ProdutoService();

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

        carregarIngredientes();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                ProdutoModel ingredienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                abrirTelaEdicao(ingredienteSelecionado);
            }
        });
    }

    private void carregarIngredientes() {
        tableView.getItems().clear();
        tableView.getItems().addAll(produtoService.listarProdutos());
    }

    private void abrirTelaEdicao(ProdutoModel ingrediente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_produto.fxml"));
            Parent root = loader.load();

            CadastroProdutoController controller = loader.getController();
            controller.setProduto(ingrediente);

            Scene scene = Main.getScene();
            scene.setRoot(root);

            Main.setMinWindowSize(600, 400);
            Main.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

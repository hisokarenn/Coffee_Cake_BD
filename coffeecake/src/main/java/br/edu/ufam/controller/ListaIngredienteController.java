package br.edu.ufam.controller;

import br.edu.ufam.Main;
import br.edu.ufam.model.IngredienteModel;
import br.edu.ufam.service.IngredienteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaIngredienteController {
    private final IngredienteService ingredienteService = new IngredienteService();

    @FXML
    private TableView<IngredienteModel> tableView;
    @FXML
    private TableColumn<IngredienteModel, Integer> colId;
    @FXML
    private TableColumn<IngredienteModel, String> colNome;
    @FXML
    private TableColumn<IngredienteModel, String> colDescricao;
    @FXML
    private TableColumn<IngredienteModel, Integer> colQuantidade;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        carregarIngredientes();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                IngredienteModel ingredienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                abrirTelaEdicao(ingredienteSelecionado);
            }
        });
    }

    private void carregarIngredientes() {
        tableView.getItems().clear();
        tableView.getItems().addAll(ingredienteService.listarIngredientes());
    }

    private void abrirTelaEdicao(IngredienteModel ingrediente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_ingrediente.fxml"));
            Parent root = loader.load();

            CadastroIngrediente controller = loader.getController();
            controller.setIngrediente(ingrediente);

            Scene scene = Main.getScene();
            scene.setRoot(root);

            Main.setMinWindowSize(600, 400);
            Main.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

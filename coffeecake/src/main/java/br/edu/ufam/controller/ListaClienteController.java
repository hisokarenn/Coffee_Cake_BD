package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import br.edu.ufam.model.ClienteModel;
import br.edu.ufam.service.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaClienteController {
    @FXML
    private TableView<ClienteModel> tableView;
    @FXML
    private TableColumn<ClienteModel, Integer> colId;
    @FXML
    private TableColumn<ClienteModel, String> colCpf;
    @FXML
    private TableColumn<ClienteModel, String> colNome;
    @FXML
    private TableColumn<ClienteModel, String> colEmail;
    @FXML
    private TableColumn<ClienteModel, String> colTelefone;

    private final ClienteService clienteService = new ClienteService();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarClientes();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                ClienteModel clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                abrirTelaEdicao(clienteSelecionado);
            }
        });
    }

    private void carregarClientes() {
        ObservableList<ClienteModel> clientes = FXCollections.observableArrayList(clienteService.listarClientes());
        tableView.setItems(clientes);
    }

    private void abrirTelaEdicao(ClienteModel cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_cliente.fxml"));
            Parent root = loader.load();

            CadastroClienteController controller = loader.getController();
            controller.preencherFormulario(cliente);

            Scene scene = Main.getScene();
            scene.setRoot(root);

            Main.setMinWindowSize(600, 400);
            Main.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

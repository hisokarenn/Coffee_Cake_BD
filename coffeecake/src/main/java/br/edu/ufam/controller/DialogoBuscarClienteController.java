package br.edu.ufam.controller;

import br.edu.ufam.model.ClienteModel;
import br.edu.ufam.service.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DialogoBuscarClienteController {
    private final ClienteService clienteService = new ClienteService();

    private ClienteModel clienteSelecionado;
    private Stage dialogStage;

    @FXML
    private TextField txtCpf;
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
                clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                dialogStage.close();
            }
        });
    }

    private void carregarClientes() {
        ObservableList<ClienteModel> clientes = FXCollections.observableArrayList(clienteService.listarClientes());
        tableView.setItems(clientes);
    }

    public ClienteModel getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

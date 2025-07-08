package br.edu.ufam.controller;

import br.edu.ufam.Main;
import br.edu.ufam.model.UsuarioModel;
import br.edu.ufam.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaUsuarioController {
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private TableView<UsuarioModel> tableView;
    @FXML
    private TableColumn<UsuarioModel, Integer> colId;
    @FXML
    private TableColumn<UsuarioModel, String> colNome;
    @FXML
    private TableColumn<UsuarioModel, String> colEmail;
    @FXML
    private TableColumn<UsuarioModel, String> colTelefone;
    @FXML
    private TableColumn<UsuarioModel, String> colLogin;
    @FXML
    private TableColumn<UsuarioModel, String> colFuncao;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));

        carregarUsuarios();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                UsuarioModel usuarioSelecionado = tableView.getSelectionModel().getSelectedItem();
                abrirTelaEdicao(usuarioSelecionado);
            }
        });
    }

    private void carregarUsuarios() {
        tableView.getItems().clear();
        tableView.getItems().addAll(usuarioService.listarUsuarios());
    }

    private void abrirTelaEdicao(UsuarioModel usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_usuario.fxml"));
            Parent root = loader.load();

            CadastroUsuarioController controller = loader.getController();
            controller.setUsuario(usuario);

            Scene scene = Main.getScene();
            scene.setRoot(root);

            Main.setMinWindowSize(600, 400);
            Main.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

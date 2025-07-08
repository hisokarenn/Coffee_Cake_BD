package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import br.edu.ufam.model.UsuarioModel;
import br.edu.ufam.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroUsuarioController {
    private final UsuarioService service = new UsuarioService();

    private int idUsuario = -1;

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private ChoiceBox<String> choiceBoxFuncao;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnSalvar;

    @FXML
    public void initialize() {
        lblTitulo.setText("Cadastrar Usuário");
        btnSalvar.setText("Salvar");

        choiceBoxFuncao.getItems().addAll("FUNCIONARIO", "GERENTE");
        choiceBoxFuncao.setValue("FUNCIONARIO");
    }

    @FXML
    private void clickSalvar() throws IOException {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        String funcao = choiceBoxFuncao.getValue();
        if (idUsuario == -1) {
            UsuarioModel usuario = new UsuarioModel(0, nome, telefone, email, login, senha, funcao);
            service.cadastrarUsuario(usuario);
        } else {
            UsuarioModel usuario = new UsuarioModel(idUsuario, nome, telefone, email, login, senha, funcao);
            service.alterarUsuario(usuario);
        }

        Main.setRoot("lista_usuario");
    }

    public void setUsuario(UsuarioModel usuario) {
        idUsuario = usuario.getId();
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        txtTelefone.setText(usuario.getTelefone());
        txtLogin.setText(usuario.getLogin());
        txtSenha.setText(usuario.getSenha());
        choiceBoxFuncao.setValue(usuario.getFuncao());

        lblTitulo.setText("Alterar Usuário");
        btnSalvar.setText("Alterar");
    }
}

package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import br.edu.ufam.model.ClienteModel;
import br.edu.ufam.service.ClienteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroClienteController {
    private final ClienteService clienteService = new ClienteService();

    private int idCliente = -1;

    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnSalvar;

    @FXML
    public void initialize() {
        lblTitulo.setText("Cadastrar Cliente");
        btnSalvar.setText("Salvar");
    }

    @FXML
    public void clickSalvar() throws IOException {
        String cpf = txtCpf.getText();
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (idCliente == -1) {
            ClienteModel cliente = new ClienteModel(0, cpf, nome, email, telefone);
            clienteService.cadastrarCliente(cliente);
        } else {
            ClienteModel cliente = new ClienteModel(idCliente, cpf, nome, email, telefone);
            clienteService.alterarCliente(cliente);
        }

        Main.setRoot("home");
    }

    public void preencherFormulario(ClienteModel cliente) {
        idCliente = cliente.getId();
        txtCpf.setText(cliente.getCpf());
        txtNome.setText(cliente.getNome());
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());

        lblTitulo.setText("Alterar Cliente");
        btnSalvar.setText("Alterar");
    }
}

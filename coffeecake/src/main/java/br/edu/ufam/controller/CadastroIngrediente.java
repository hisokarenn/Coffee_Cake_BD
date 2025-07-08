package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import br.edu.ufam.model.IngredienteModel;
import br.edu.ufam.service.IngredienteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadastroIngrediente {
    private IngredienteService service = new IngredienteService();

    private int idIngrediente = -1;

    @FXML
    private TextField txtNome;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtQuantidade;

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnSalvar;

    @FXML
    public void initialize() {
        lblTitulo.setText("Cadastrar Ingrediente");
        btnSalvar.setText("Salvar");
    }

    @FXML
    private void clickSalvar() throws IOException{
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        int quantidade = Integer.parseInt(txtQuantidade.getText());

        if (idIngrediente == -1) {
            IngredienteModel ingrediente = new IngredienteModel(0, nome, descricao, quantidade);
            service.cadastrarIngrediente(ingrediente);
        } else {
            IngredienteModel ingrediente = new IngredienteModel(idIngrediente, nome, descricao, quantidade);
            service.alterarIngrediente(ingrediente);
        }

        Main.setRoot("lista_ingrediente");
    }

    public void setIngrediente(IngredienteModel ingrediente) {
        idIngrediente = ingrediente.getId();
        txtNome.setText(ingrediente.getNome());
        txtDescricao.setText(ingrediente.getDescricao());
        txtQuantidade.setText(String.valueOf(ingrediente.getQuantidade()));

        lblTitulo.setText("Alterar Ingrediente");
        btnSalvar.setText("Alterar");
    }
}

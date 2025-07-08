package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @FXML
    private Label lblUsuarioLogado;

    @FXML
    private void initialize() {
        lblUsuarioLogado.setText("Olá: " + br.edu.ufam.Main.usuarioLogado.getNome());
    }

    @FXML
    private void clickComanda() throws IOException {
        Main.setRoot("cadastro_comanda");
    }

    @FXML
    private void clickCliente() throws IOException {
        Main.setRoot("lista_cliente");
    }
}

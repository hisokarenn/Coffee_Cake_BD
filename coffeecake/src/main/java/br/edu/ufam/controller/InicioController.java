package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import javafx.fxml.FXML;

public class InicioController {
    @FXML
    public void ClickLogin() throws IOException {
        Main.setRoot("login");
    }
}

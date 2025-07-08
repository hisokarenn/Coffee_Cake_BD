package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import br.edu.ufam.service.AutenticacaoService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;

    @FXML
    private void clickLogin() throws IOException {
        AutenticacaoService autenticacaoService = new AutenticacaoService();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        autenticacaoService.login(login, senha);

        if (autenticacaoService.isAutenticado()) {
            System.out.println("Usuário autenticado com sucesso: " + autenticacaoService.getUsuarioLogado().getNome());
            Main.usuarioLogado = autenticacaoService.getUsuarioLogado();
            Main.setRoot("home");
        } else {
            System.out.println("Falha na autenticação. Verifique suas credenciais.");
        }
    }
}

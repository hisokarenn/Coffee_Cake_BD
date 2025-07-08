package br.edu.ufam.controller;

import java.io.IOException;

import br.edu.ufam.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBarController {
    @FXML
    private Menu menuUsuario;
    @FXML
    private MenuItem relatorio;

    @FXML
    public void initialize() {
        if (Main.usuarioLogado.getFuncao().equals("GERENTE")) {
            menuUsuario.setVisible(true);
            relatorio.setVisible(true);
        } else {
            menuUsuario.setVisible(false);
            relatorio.setVisible(false);
        }
    }

    @FXML
    public void clickHome() throws IOException {
        System.out.println("Home clicado.");
        Main.setRoot("home");
    }

    @FXML
    public void clickListarComandasAbertas() throws IOException {
        System.out.println("Listar comandas abertas clicado.");
        listarComandas("ABERTA");
    }

    @FXML
    public void clickListarComandasFechadas() throws IOException {
        System.out.println("Listar comandas fechadas clicado.");
        listarComandas("FECHADA");
    }

    @FXML
    public void clickListarComandasCanceladas() throws IOException {
        System.out.println("Listar comandas canceladas clicado.");
        listarComandas("CANCELADA");
    }

    @FXML
    public void clickListarComandasTodas() throws IOException {
        System.out.println("Listar todas comandas clicado.");
        listarComandas("ALL");
    }

    private void listarComandas(String filtro) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lista_comanda.fxml"));
            Parent root = loader.load();

            ListaComandaController controller = loader.getController();
            controller.setFiltroStatus(filtro);

            Scene scene = Main.getScene();
            scene.setRoot(root);

            Main.setMinWindowSize(600, 400);
            Main.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickCadastrarComanda() throws IOException {
        System.out.println("Cadastrar comanda clicado.");
        Main.setRoot("cadastro_comanda");
    }

    @FXML
    public void clickCadastrarUsuario() throws IOException {
        System.out.println("Cadastro de usuário clicado.");
        Main.setRoot("cadastro_usuario");
    }

    @FXML
    public void clickListarUsuarios() throws IOException {
        System.out.println("Listar Usuários clicado.");
        Main.setRoot("lista_usuario");
    }

    @FXML
    public void clickCadastrarIngrediente() throws IOException {
        System.out.println("Cadastro de Ingrediente clicado.");
        Main.setRoot("cadastro_ingrediente");
    }

    @FXML
    public void clickListarIngredientes() throws IOException {
        System.out.println("Listar Ingredientes clicado.");
        Main.setRoot("lista_ingrediente");
    }

    @FXML
    public void clickCadastrarProduto() throws IOException {
        System.out.println("Cadastro de Produto clicado.");
        Main.setRoot("cadastro_produto");
    }

    @FXML
    public void clickListarProduto() throws IOException {
        System.out.println("Listar Produtos clicado.");
        Main.setRoot("lista_produto");
    }

    @FXML
    public void clickListarClientes() throws IOException {
        System.out.println("Listar Clientes clicado.");
        Main.setRoot("lista_cliente");
    }

    @FXML
    public void clickCadastrarCliente() throws IOException {
        System.out.println("Cadastro de Cliente clicado.");
        Main.setRoot("cadastro_cliente");
    }

    @FXML
    public void clickRelatorio() throws IOException {
        System.out.println("Relatorio clicado.");
        Main.setRoot("relatorio");
    }

    @FXML
    public void clickLogout() throws IOException {
        System.out.println("Logout clicado.");
        Main.usuarioLogado = null;
        Main.setRoot("login");
    }
}

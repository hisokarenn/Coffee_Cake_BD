package br.edu.ufam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufam.Main;
import br.edu.ufam.model.IngredienteModel;
import br.edu.ufam.model.ProdutoIngredienteModel;
import br.edu.ufam.model.ProdutoModel;
import br.edu.ufam.service.IngredienteService;
import br.edu.ufam.service.ProdutoService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroProdutoController {
    private final ProdutoService service = new ProdutoService();
    private final IngredienteService ingredienteService = new IngredienteService();

    private final List<IngredienteModel> ingredientesSelecionados = new ArrayList<>();
    private final Map<IngredienteModel, Integer> quantidadeSelecionadaMap = new HashMap<>();

    private int idProduto = -1;

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TextField textPreco;
    @FXML
    private TextArea txtDescricao;

    @FXML
    private TableView<IngredienteModel> tableView;
    @FXML
    private TableColumn<IngredienteModel, Void> colSelecionar;
    @FXML
    private TableColumn<IngredienteModel, Integer> colId;
    @FXML
    private TableColumn<IngredienteModel, String> colNome;
    @FXML
    private TableColumn<IngredienteModel, Integer> colQuantidade;
    @FXML
    private TableColumn<IngredienteModel, Void> colQuantidadeUsar;

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnSalvar;

    @FXML
    public void initialize() {
        lblTitulo.setText("Cadastrar Produto");
        btnSalvar.setText("Salvar");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        configurarColunaSelecionar();
        configurarColunaQuantidadeUsar();
        carregarIngredientes();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                IngredienteModel ingredienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                System.out.println("Ingrediente selecionado: " + ingredienteSelecionado.getNome());
            }
        });
    }

    private void carregarIngredientes() {
        List<IngredienteModel> ingredientes = ingredienteService.listarIngredientes();

        tableView.getItems().clear();
        tableView.getItems().addAll(ingredientes);
    }

    private void configurarColunaSelecionar() {
        colSelecionar.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    IngredienteModel ingrediente = getTableView().getItems().get(getIndex());
                    if (checkBox.isSelected()) {
                        ingredientesSelecionados.add(ingrediente);
                    } else {
                        ingredientesSelecionados.remove(ingrediente);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    IngredienteModel ingrediente = getTableView().getItems().get(getIndex());

                    checkBox.setSelected(ingredientesSelecionados.contains(ingrediente));
                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected()) {
                            if (!ingredientesSelecionados.contains(ingrediente)) {
                                ingredientesSelecionados.add(ingrediente);
                            }
                        } else {
                            ingredientesSelecionados.remove(ingrediente);
                        }
                    });

                    setGraphic(checkBox);
                }
            }
        });
    }

    private void configurarColunaQuantidadeUsar() {
        colQuantidadeUsar.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    IngredienteModel ingrediente = getTableView().getItems().get(getIndex());

                    TextField textField = new TextField();
                    textField.setPrefWidth(60);
                    textField.setStyle("-fx-background-radius: 3; -fx-font-size: 12px;");

                    // Define o valor atual
                    int valor = quantidadeSelecionadaMap.getOrDefault(ingrediente, 0);
                    textField.setText(String.valueOf(valor));

                    // Define listener para atualizar o map
                    textField.textProperty().addListener((obs, oldValue, newValue) -> {
                        try {
                            int qtd = Integer.parseInt(newValue);
                            quantidadeSelecionadaMap.put(ingrediente, qtd);
                        } catch (NumberFormatException e) {
                            quantidadeSelecionadaMap.remove(ingrediente);
                        }
                    });

                    setGraphic(textField);
                }
            }
        });
    }

    @FXML
    void clickSalvar() throws IOException {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        int quantidade = Integer.parseInt(txtQuantidade.getText());
        float preco = Float.parseFloat(textPreco.getText());

        List<ProdutoIngredienteModel> produtoIngredientes = new ArrayList<>();

        for (IngredienteModel ing : ingredientesSelecionados) {
            int quantidadeUsar = quantidadeSelecionadaMap.getOrDefault(ing, 0);
            if (quantidadeUsar > 0) {
                ProdutoIngredienteModel produtoIngrediente = new ProdutoIngredienteModel(null, ing, quantidadeUsar);
                produtoIngredientes.add(produtoIngrediente);
            }
        }

        if (idProduto == -1) {
            ProdutoModel produto = new ProdutoModel(-1, nome, preco, descricao, quantidade);
            service.cadastrarProduto(produto, produtoIngredientes);
        } else {
            ProdutoModel produto = new ProdutoModel(idProduto, nome, preco, descricao, quantidade);
            service.alterarProduto(produto, produtoIngredientes);
        }

        Main.setRoot("lista_produto");
    }

    public void setProduto(ProdutoModel produto) {
        idProduto = produto.getId();
        txtNome.setText(produto.getNome());
        txtDescricao.setText(produto.getDescricao());
        txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        textPreco.setText(String.valueOf(produto.getPreco()));

        lblTitulo.setText("Alterar Produto");
        btnSalvar.setText("Alterar");

        List<ProdutoIngredienteModel> ingredientesDoProduto = service.listarIngredientesDoProduto(produto.getId());
        ingredientesSelecionados.clear();
        quantidadeSelecionadaMap.clear();

        for (ProdutoIngredienteModel pi : ingredientesDoProduto) {
            IngredienteModel ing = pi.getIngrediente();
            ingredientesSelecionados.add(ing);
            quantidadeSelecionadaMap.put(ing, pi.getQuantidadeUsar());
        }
        carregarIngredientes();
        tableView.refresh();
    }
}

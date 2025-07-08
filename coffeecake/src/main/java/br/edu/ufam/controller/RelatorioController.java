package br.edu.ufam.controller;

import br.edu.ufam.model.RelatorioModel;
import br.edu.ufam.service.RelatorioService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RelatorioController {
    private final RelatorioService service = new RelatorioService();

    @FXML
    private TableView<RelatorioModel> tableView;
    @FXML
    private TableColumn<RelatorioModel, Integer> colId;
    @FXML
    private TableColumn<RelatorioModel, String> colNome;
    @FXML
    private TableColumn<RelatorioModel, Integer> colQuantidadeComandas;
    @FXML
    private TableColumn<RelatorioModel, Integer> colQuantidadeFinalizadas;
    @FXML
    private TableColumn<RelatorioModel, Integer> colQuantidadeCancelada;
    @FXML
    private TableColumn<RelatorioModel, Float> colValorVendas;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidadeComandas.setCellValueFactory(new PropertyValueFactory<>("quantidadeComandas"));
        colQuantidadeFinalizadas.setCellValueFactory(new PropertyValueFactory<>("quantidadeFinalizadas"));
        colQuantidadeCancelada.setCellValueFactory(new PropertyValueFactory<>("quantidadeCanceladas"));
        colValorVendas.setCellValueFactory(new PropertyValueFactory<>("totalVendas"));

        carregarRelatorio();
    }

    private void carregarRelatorio() {
        tableView.getItems().clear();
        tableView.getItems().addAll(service.listarRelatorios());
    }
}

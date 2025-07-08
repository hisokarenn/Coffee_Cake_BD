package br.edu.ufam.model;

import java.time.LocalDateTime;

public class EstoqueProduto {
    private int id;
    private ProdutoModel produto;
    private int quantidade;
    private LocalDateTime dataEntrada;

    public EstoqueProduto(int id, ProdutoModel produto, int quantidade, LocalDateTime dataEntrada) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataEntrada = dataEntrada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}

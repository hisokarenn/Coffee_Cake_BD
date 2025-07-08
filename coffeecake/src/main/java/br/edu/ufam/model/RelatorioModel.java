package br.edu.ufam.model;

public class RelatorioModel {
    private int id;
    private String nome;
    private int quantidadeComandas;
    private int quantidadeFinalizadas;
    private int quantidadeCanceladas;
    private float totalVendas;

    public RelatorioModel(int id, String nome, int quantidadeComandas, int quantidadeFinalizadas,
            int quantidadeCanceladas, float totalVendas) {
        this.id = id;
        this.nome = nome;
        this.quantidadeComandas = quantidadeComandas;
        this.quantidadeFinalizadas = quantidadeFinalizadas;
        this.quantidadeCanceladas = quantidadeCanceladas;
        this.totalVendas = totalVendas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeComandas() {
        return quantidadeComandas;
    }

    public void setQuantidadeComandas(int quantidadeComandas) {
        this.quantidadeComandas = quantidadeComandas;
    }

    public int getQuantidadeFinalizadas() {
        return quantidadeFinalizadas;
    }

    public void setQuantidadeFinalizadas(int quantidadeFinalizadas) {
        this.quantidadeFinalizadas = quantidadeFinalizadas;
    }

    public int getQuantidadeCanceladas() {
        return quantidadeCanceladas;
    }

    public void setQuantidadeCanceladas(int quantidadeCanceladas) {
        this.quantidadeCanceladas = quantidadeCanceladas;
    }

    public float getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(float totalVendas) {
        this.totalVendas = totalVendas;
    }
}

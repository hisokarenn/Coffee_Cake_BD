package br.edu.ufam.model;

public class ProdutoIngredienteModel {
    private ProdutoModel produto;
    private IngredienteModel ingrediente;
    private int quantidadeUsar;

    public ProdutoIngredienteModel(ProdutoModel produto, IngredienteModel ingrediente, int quantidadeUsar) {
        this.produto = produto;
        this.ingrediente = ingrediente;
        this.quantidadeUsar = quantidadeUsar;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public IngredienteModel getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteModel ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getQuantidadeUsar() {
        return quantidadeUsar;
    }

    public void setQuantidadeUsar(int quantidadeUsar) {
        this.quantidadeUsar = quantidadeUsar;
    }

}

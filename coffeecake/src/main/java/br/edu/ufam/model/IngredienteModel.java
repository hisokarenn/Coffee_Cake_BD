package br.edu.ufam.model;

public class IngredienteModel {
    private int id;
    private String nome;
    private String descricao;
    private int quantidade;

    public IngredienteModel(int id, String nome, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public void setQuantodade(int quantidade){
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IngredienteModel other = (IngredienteModel) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

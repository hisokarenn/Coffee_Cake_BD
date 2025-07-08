package br.edu.ufam.model;

public class ComandaModel {
    int id;
    String data;
    String status;
    ClienteModel cliente;
    UsuarioModel usuario;
    float valorTotal;

    public ComandaModel(int id, String data, String status, ClienteModel cliente, UsuarioModel usuario,
            float valorTotal) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.cliente = cliente;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String dataCriacao) {
        this.data = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}

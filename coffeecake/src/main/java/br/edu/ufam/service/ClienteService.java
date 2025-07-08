package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.ClienteModel;

public class ClienteService {
    public List<ClienteModel> listarClientes() {
        List<ClienteModel> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, cpf, nome, email, telefone FROM cliente";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClienteModel cliente = new ClienteModel(
                        rs.getInt("id_cliente"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public void cadastrarCliente(ClienteModel cliente) {
        String sql = "INSERT INTO cliente(cpf, nome, email, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Cliente cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar cliente.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void alterarCliente(ClienteModel cliente) {
        String sql = "UPDATE cliente SET cpf = ?, nome = ?, email = ?, telefone = ? WHERE id_cliente = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setInt(5, cliente.getId());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Cliente alterado com sucesso!");
            } else {
                System.out.println("Falha ao alterar cliente.");
            }
        } catch (SQLException e) {
            System.out.println("Error ao alterar produto!" + e.getMessage());
        }
    }

    public ClienteModel pesquisarCliente(int id) {
        ClienteModel cliente = null;
        String sql = "SELECT id_cliente, cpf, nome, email, telefone FROM cliente WHERE id_cliente = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new ClienteModel(
                        rs.getInt("id_cliente"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar cliente: " + e.getMessage());
        }

        return cliente;
    }
}

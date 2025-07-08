package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.IngredienteModel;

public class IngredienteService {
    public List<IngredienteModel> listarIngredientes() {
        List<IngredienteModel> ingredientes = new ArrayList<>();
        String sql = "SELECT id_ingrediente, nome, descricao, quantidade_disponivel FROM ingrediente";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                IngredienteModel ingrediente = new IngredienteModel(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade_disponivel"));

                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes: " + e.getMessage());
        }

        return ingredientes;
    }

    public void cadastrarIngrediente(IngredienteModel ingrediente) {
        String sql = "INSERT INTO ingrediente (nome,descricao,quantidade_disponivel) VALUES (?,?,?)";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setString(2, ingrediente.getDescricao());
            stmt.setInt(3, ingrediente.getQuantidade());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Ingrediente cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar ingrediente.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar ingrediente: " + e.getMessage());
        }
    }

    public void alterarIngrediente(IngredienteModel ingrediente) {
        String sql = "UPDATE ingrediente SET nome = ?, descricao = ?, quantidade_disponivel = ?  WHERE id_ingrediente = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setString(2, ingrediente.getDescricao());
            stmt.setInt(3, ingrediente.getQuantidade());
            stmt.setInt(4, ingrediente.getId());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Ingrediente alterado com sucesso!");
            } else {
                System.out.println("Falha ao alterar ingrediente.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar ingrediente: " + e.getMessage());
        }
    }

    public IngredienteModel pesquisarIngrediente(int idIngrediente) {
        IngredienteModel ingrediente = null;
        String sql = "SELECT id_ingrediente, nome, descricao, quantidade_disponivel FROM ingrediente WHERE id_ingrediente = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIngrediente);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ingrediente = new IngredienteModel(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade_disponivel"));
            } else {
                System.out.println("Ingrediente não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar ingrediente (" + idIngrediente + "): " + e.getMessage());
        }

        return ingrediente;
    }
}

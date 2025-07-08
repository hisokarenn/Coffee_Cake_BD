package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.IngredienteModel;
import br.edu.ufam.model.ProdutoIngredienteModel;
import br.edu.ufam.model.ProdutoModel;

public class ProdutoService {
    private final IngredienteService ingredienteService = new IngredienteService();

    public List<ProdutoModel> listarProdutos() {
        List<ProdutoModel> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome, preco, descricao, quantidade_disponivel from produto";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutoModel produto = new ProdutoModel(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade_disponivel"));
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pridutos!" + e.getMessage());
        }
        return produtos;
    }

    public ProdutoModel cadastrarProduto(ProdutoModel produto, List<ProdutoIngredienteModel> ingredientes) {
        String sql = "INSERT INTO produto(nome, preco, descricao, quantidade_disponivel) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getQuantidade());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        produto.setId(generatedKeys.getInt(1));
                        System.out.println("Produto cadastrado com sucesso!");
                    }
                }
            } else {
                System.out.println("Falha ao cadastrar produto.");
            }
        } catch (SQLException e) {
            System.out.println("Error ao cadastrar produto!" + e.getMessage());
        }

        if (ingredientes != null && !ingredientes.isEmpty() && produto.getId() > 0) {
            String sqlIngrediente = "INSERT INTO produto_ingrediente(fk_produto, fk_ingrediente, quantidade_ingrediente) VALUES (?, ?, ?)";

            try (Connection conn = ConexaoDatabase.getConnection();
                    PreparedStatement stmtIngrediente = conn.prepareStatement(sqlIngrediente)) {
                for (ProdutoIngredienteModel ingrediente : ingredientes) {
                    stmtIngrediente.setInt(1, produto.getId());
                    stmtIngrediente.setInt(2, ingrediente.getIngrediente().getId());
                    stmtIngrediente.setInt(3, ingrediente.getQuantidadeUsar());
                    stmtIngrediente.addBatch();
                }
                stmtIngrediente.executeBatch();
                System.out.println("Ingredientes cadastrados com sucesso!");
                return produto;
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar ingredientes do produto: " + e.getMessage());
            }
        }

        return null;
    }

    public void alterarProduto(ProdutoModel produto, List<ProdutoIngredienteModel> ingredientes) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, quantidade_disponivel = ?, preco = ? WHERE id_produto = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setFloat(4, produto.getPreco());
            stmt.setInt(5, produto.getId());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Produto alterado com sucesso!");
            } else {
                System.out.println("Falha ao alterar produto.");
            }
        } catch (SQLException e) {
            System.out.println("Error ao alterar produto!" + e.getMessage());
        }

        String sqlIngrediente = "DELETE FROM produto_ingrediente WHERE fk_produto = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmtIngrediente = conn.prepareStatement(sqlIngrediente)) {
            stmtIngrediente.setInt(1, produto.getId());
            stmtIngrediente.executeUpdate();
            System.out.println("Ingredientes removidos com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao remover ingredientes do produto: " + e.getMessage());
        }

        if (ingredientes != null && !ingredientes.isEmpty()) {
            String sqlInserirIngrediente = "INSERT INTO produto_ingrediente(fk_produto, fk_ingrediente, quantidade_ingrediente) VALUES (?, ?, ?)";

            try (Connection conn = ConexaoDatabase.getConnection();
                    PreparedStatement stmtInserirIngrediente = conn.prepareStatement(sqlInserirIngrediente)) {
                for (ProdutoIngredienteModel ingrediente : ingredientes) {
                    stmtInserirIngrediente.setInt(1, produto.getId());
                    stmtInserirIngrediente.setInt(2, ingrediente.getIngrediente().getId());
                    stmtInserirIngrediente.setInt(3, ingrediente.getQuantidadeUsar());
                    stmtInserirIngrediente.addBatch();
                }
                stmtInserirIngrediente.executeBatch();
                System.out.println("Ingredientes atualizados com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar ingredientes do produto: " + e.getMessage());
            }
        }
    }

    public ProdutoModel pesquisarProduto(String nome) {
        ProdutoModel produto = null;
        String sql = " SELECT id, nome, preco, descricao FROM produto WHERE nome = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new ProdutoModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade_disponivel"));
            } else {
                System.out.println("Produto não encontrado!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar produto(" + nome + "): " + e.getMessage());
        }

        return produto;
    }

    public List<ProdutoIngredienteModel> listarIngredientesDoProduto(int idProduto) {
        List<ProdutoIngredienteModel> ingredientes = new ArrayList<>();
        String sql = "SELECT pi.fk_ingrediente as id_ingrediente, i.nome, pi.quantidade_ingrediente FROM produto_ingrediente pi "
                + "JOIN ingrediente i ON pi.fk_ingrediente = i.id_ingrediente "
                + "WHERE pi.fk_produto = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                IngredienteModel ingrediente = ingredienteService.pesquisarIngrediente(rs.getInt("id_ingrediente"));
                int quantidadeUsar = rs.getInt("quantidade_ingrediente");
                ProdutoIngredienteModel produtoIngrediente = new ProdutoIngredienteModel(null, ingrediente,
                        quantidadeUsar);
                ingredientes.add(produtoIngrediente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes do produto: " + e.getMessage());
        }

        return ingredientes;
    }
}

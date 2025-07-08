package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.ComandaModel;
import br.edu.ufam.model.ComandaProdutoTableModel;

public class ComandaService {
    private final UsuarioService usuarioService = new UsuarioService();
    private final ClienteService clienteService = new ClienteService();

    public List<ComandaModel> listarComandas(String filtroStatus) {
        String sql = "SELECT id_comanda, data_criacao, status, fk_cliente, fk_usuario, valor_total FROM comanda";
        if (filtroStatus != null && !filtroStatus.isEmpty()) {
            if (filtroStatus.equals("ALL")) {
                sql += " WHERE status IN ('ABERTA', 'FECHADA', 'CANCELADA')";
            } else if (filtroStatus.equals("ABERTA")) {
                sql += " WHERE status = 'ABERTA'";
            } else if (filtroStatus.equals("FECHADA")) {
                sql += " WHERE status = 'FECHADA'";
            } else if (filtroStatus.equals("CANCELADA")) {
                sql += " WHERE status = 'CANCELADA'";
            }
        }

        List<ComandaModel> comandas = new ArrayList<>();

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ComandaModel comanda = new ComandaModel(
                        rs.getInt("id_comanda"),
                        rs.getString("data_criacao"),
                        rs.getString("status"),
                        clienteService.pesquisarCliente(rs.getInt("fk_cliente")),
                        usuarioService.pesquisarUsuario(rs.getInt("fk_usuario")),
                        rs.getFloat("valor_total"));
                comandas.add(comanda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar comandas: " + e.getMessage());
        }

        return comandas;
    }

    public List<ComandaProdutoTableModel> listarComandaProduto(int id) {
        String sql = "SELECT cp.fk_produto as id_produto, p.nome, cp.quantidade_produto, p.preco FROM comanda_produto cp "
                + "INNER JOIN produto p ON cp.fk_produto = p.id_produto "
                + "WHERE cp.fk_comanda = ?";

        List<ComandaProdutoTableModel> comandas = new ArrayList<>();

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ComandaProdutoTableModel comanda = new ComandaProdutoTableModel(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getInt("quantidade_produto"),
                        rs.getFloat("preco"));
                comandas.add(comanda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar comandas por ID: " + e.getMessage());
        }

        return comandas;
    }

    public void cadastrarComanda(ComandaModel comanda, List<ComandaProdutoTableModel> produtosComanda) {
        String sql = "INSERT INTO comanda (data_criacao, status, fk_cliente, fk_usuario, valor_total) VALUES (?, ?, ?, ?, ?)";

        int idComanda = -1;

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, new Timestamp(System.currentTimeMillis()).toString());
            stmt.setString(2, comanda.getStatus());
            stmt.setInt(3, comanda.getCliente().getId());
            stmt.setInt(4, comanda.getUsuario().getId());
            stmt.setFloat(5, comanda.getValorTotal());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idComanda = rs.getInt(1);
                }
                System.out.println("Comanda cadastrada com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar comanda.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar comanda: " + e.getMessage());
        }

        if (produtosComanda != null && !produtosComanda.isEmpty() && idComanda > 0) {
            String sqlProduto = "INSERT INTO comanda_produto (quantidade_produto, fk_comanda, fk_produto) VALUES (?, ?, ?)";

            try (Connection conn = ConexaoDatabase.getConnection();
                    PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto)) {
                for (ComandaProdutoTableModel produto : produtosComanda) {
                    stmtProduto.setInt(1, produto.getQuantidade());
                    stmtProduto.setInt(2, idComanda);
                    stmtProduto.setInt(3, produto.getId());
                    stmtProduto.addBatch();
                }
                int[] linhasProdutos = stmtProduto.executeBatch();
                System.out.println("Produtos da comanda cadastrados com sucesso: " + linhasProdutos.length);
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar produtos da comanda: " + e.getMessage());
            }

            String sqlAtualizarEstoque = "UPDATE produto SET quantidade_disponivel = quantidade_disponivel - ? WHERE id_produto = ?";
            try (Connection conn = ConexaoDatabase.getConnection();
                    PreparedStatement stmtAtualizarEstoque = conn.prepareStatement(sqlAtualizarEstoque)) {
                for (ComandaProdutoTableModel produto : produtosComanda) {
                    stmtAtualizarEstoque.setInt(1, produto.getQuantidade());
                    stmtAtualizarEstoque.setInt(2, produto.getId());
                    stmtAtualizarEstoque.addBatch();
                }
                int[] linhasEstoque = stmtAtualizarEstoque.executeBatch();
                System.out.println("Estoque atualizado com sucesso: " + linhasEstoque.length);
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar estoque dos produtos: " + e.getMessage());
            }
        }
    }

    public void atualizarComanda(ComandaModel comanda, List<ComandaProdutoTableModel> produtosComanda) {
        String sql = "UPDATE comanda SET data_criacao = ?, status = ?, fk_cliente = ?, fk_usuario = ?, valor_total = ? WHERE id_comanda = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, new Timestamp(System.currentTimeMillis()).toString());
            stmt.setString(2, comanda.getStatus());
            stmt.setInt(3, comanda.getCliente().getId());
            stmt.setInt(4, comanda.getUsuario().getId());
            stmt.setFloat(5, comanda.getValorTotal());
            stmt.setInt(6, comanda.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Comanda atualizada com sucesso!");
            } else {
                System.out.println("Falha ao atualizar comanda.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar comanda: " + e.getMessage());
        }

        String sqlComandaProduto = "DELETE FROM comanda_produto WHERE fk_comanda = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmtComandaProduto = conn.prepareStatement(sqlComandaProduto)) {
            stmtComandaProduto.setInt(1, comanda.getId());
            stmtComandaProduto.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover produtos da comanda: " + e.getMessage());
        }

        if (produtosComanda != null && !produtosComanda.isEmpty()) {
            String sqlProduto = "INSERT INTO comanda_produto (quantidade_produto, fk_comanda, fk_produto) VALUES (?, ?, ?)";

            try (Connection conn = ConexaoDatabase.getConnection();
                    PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto)) {
                for (ComandaProdutoTableModel produto : produtosComanda) {
                    stmtProduto.setInt(1, produto.getQuantidade());
                    stmtProduto.setInt(2, comanda.getId());
                    stmtProduto.setInt(3, produto.getId());
                    stmtProduto.addBatch();
                }
                int[] linhasProdutos = stmtProduto.executeBatch();
                System.out.println("Produtos da comanda atualizados com sucesso: " + linhasProdutos.length);
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar produtos da comanda: " + e.getMessage());
            }
        }
    }
}

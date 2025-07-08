package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.RelatorioModel;

public class RelatorioService {
    public List<RelatorioModel> listarRelatorios() {
        String sql = "SELECT " +
                "u.id_usuario, " +
                "u.nome, " +
                "COUNT(c.id_comanda) AS total_comandas, " +
                "COUNT(CASE WHEN c.status = 'FECHADA' THEN 1 END) AS qtd_vendas_finalizadas, " +
                "COUNT(CASE WHEN c.status = 'CANCELADA' THEN 1 END) AS qtd_vendas_canceladas, " +
                "COALESCE(SUM(CASE WHEN c.status = 'FECHADA' THEN c.valor_total ELSE 0 END), 0) AS valor_total_vendas "
                +
                "FROM usuario u " +
                "LEFT JOIN comanda c ON c.fk_usuario = u.id_usuario " +
                "GROUP BY u.id_usuario, u.nome " +
                "ORDER BY u.nome";

        List<RelatorioModel> relatorios = new ArrayList<>();

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RelatorioModel relatorio = new RelatorioModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getInt("total_comandas"),
                        rs.getInt("qtd_vendas_finalizadas"),
                        rs.getInt("qtd_vendas_canceladas"),
                        rs.getFloat("valor_total_vendas"));

                relatorios.add(relatorio);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários! " + e.getMessage());
        }

        return relatorios;
    }
}

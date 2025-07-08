package br.edu.ufam.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.config.ConexaoDatabase;
import br.edu.ufam.model.UsuarioModel;

public class UsuarioService {
    public List<UsuarioModel> listarUsuarios() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nome, email, telefone, login, funcao FROM usuario";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioModel usuario = new UsuarioModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("login"),
                        "********",
                        rs.getString("funcao"));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários! " + e.getMessage());
        }

        return usuarios;
    }

    public void cadastrarUsuario(UsuarioModel usuario) {
        String sql = "INSERT INTO usuario(nome, telefone, email, login, senha, funcao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getLogin());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getFuncao());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar usuário.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void alterarUsuario(UsuarioModel usuario) {
        String sql = "UPDATE usuario SET nome = ?, telefone = ?, email = ?, login = ?, senha = ?, funcao = ?  WHERE id_usuario = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getLogin());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getFuncao());
            stmt.setInt(7, usuario.getId());

            int linha = stmt.executeUpdate();

            if (linha > 0) {
                System.out.println("Usuário alterado com sucesso!");
            } else {
                System.out.println("Falha ao alterar usuário.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar usuário! " + e.getMessage());
        }
    }

    public UsuarioModel pesquisarUsuario(int id) {
        UsuarioModel usuario = null;
        String sql = "SELECT id_usuario, nome, telefone, email, login, funcao FROM  usuario WHERE id_usuario = ?";

        try (Connection conn = ConexaoDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new UsuarioModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("login"),
                        "********",
                        rs.getString("funcao"));
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar usuários (" + id + "): " + e.getMessage());
        }

        return usuario;
    }
}

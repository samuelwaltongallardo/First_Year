/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.crudvehiculosccohes;

/**
 *
 * @author samwe
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Moto extends Vehiculo {
    private int cilindrada;

    public Moto(int id, String nombre, String marca, int cilindrada) {
        super(id, nombre, marca);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() { return cilindrada; }
    public void setCilindrada(int cilindrada) { this.cilindrada = cilindrada; }

    @Override
    public void guardar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "INSERT INTO motos (nombre, marca, cilindrada) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, cilindrada);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "DELETE FROM motos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "UPDATE motos SET nombre = ?, marca = ?, cilindrada = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, cilindrada);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet obtenerMotos() {
        try {
            Connection conn = ConexionMySQL.getConnection();
            String sql = "SELECT * FROM motos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

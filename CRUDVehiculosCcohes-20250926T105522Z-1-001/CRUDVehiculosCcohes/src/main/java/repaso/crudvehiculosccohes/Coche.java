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

public class Coche extends Vehiculo {
    private int puertas;
    private String tipoCombustible;

    public Coche(int id, String nombre, String marca, int puertas, String tipoCombustible) {
        super(id, nombre, marca);
        this.puertas = puertas;
        this.tipoCombustible = tipoCombustible;
    }

    public int getPuertas() { return puertas; }
    public String getTipoCombustible() { return tipoCombustible; }
    public void setPuertas(int puertas) { this.puertas = puertas; }
    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }

    @Override
    public void guardar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "INSERT INTO coches (nombre, marca, puertas, tipoCombustible) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, puertas);
            stmt.setString(4, tipoCombustible);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "DELETE FROM coches WHERE id = ?";
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
            String sql = "UPDATE coches SET nombre = ?, marca = ?, puertas = ?, tipoCombustible = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, puertas);
            stmt.setString(4, tipoCombustible);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet obtenerCoches() {
        try {
            Connection conn = ConexionMySQL.getConnection();
            String sql = "SELECT * FROM coches";
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.versionconlogger;

/**
 *
 * @author samwe
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pedido extends Entidad {
    private int cantidad;
    private int idCliente;
    private double precio;

    public Pedido(int id, String nombre, int cantidad, int idCliente, double precio) {
        super(id, nombre);
        this.cantidad = cantidad;
        this.idCliente = idCliente;
        this.precio = precio;
    }

    public int getCantidad() { return cantidad; }
    public int getIdCliente() { return idCliente; }
    public double getPrecio() { return precio; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public void guardar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "INSERT INTO pedidos (nombre, cantidad, idCliente, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, idCliente);
            stmt.setDouble(4, precio);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar() {
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "DELETE FROM pedidos WHERE id = ?";
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
            String sql = "UPDATE pedidos SET nombre = ?, cantidad = ?, idCliente = ?, precio = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, idCliente);
            stmt.setDouble(4, precio);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet obtenerPedidos() {
        try {
            Connection conn = ConexionMySQL.getConnection();
            String sql = "SELECT * FROM pedidos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

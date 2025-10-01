/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.versionconlogger;

/**
 *
 * @author samwe
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterfazPrincipal extends JFrame {
    // Componentes para Clientes
    private JTextField txtNombreCliente, txtTelefono, txtDireccion, txtIdPedidoCliente;
    private JTable tablaClientes;
    private DefaultTableModel modeloClientes;

    // Componentes para Pedidos
    private JTextField txtNombrePedido, txtCantidadPedido, txtIdClientePedido, txtPrecioPedido;
    private JTable tablaPedidos;
    private DefaultTableModel modeloPedidos;

    // Logger de cambios
    private JTextArea textAreaLogger;

    public InterfazPrincipal() {
        setTitle("CRUD Clientes y Pedidos con Logger");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal dividido verticalmente para Clientes y Pedidos
        JPanel panelPrincipal = new JPanel(new GridLayout(2, 1));
        add(panelPrincipal, BorderLayout.CENTER);

        // PANEL CLIENTES
        JPanel panelClientes = new JPanel(new BorderLayout());
        panelClientes.setBackground(new Color(200, 255, 200));

        JLabel lblClientes = new JLabel("Clientes");
        lblClientes.setFont(new Font("Arial", Font.BOLD, 18));
        panelClientes.add(lblClientes, BorderLayout.NORTH);

        modeloClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Dirección", "ID Pedido"}, 0);
        tablaClientes = new JTable(modeloClientes);
        panelClientes.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        JPanel panelInputsClientes = new JPanel();

        txtNombreCliente = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtDireccion = new JTextField(10);
        txtIdPedidoCliente = new JTextField(5);

        panelInputsClientes.add(new JLabel("Nombre:"));
        panelInputsClientes.add(txtNombreCliente);
        panelInputsClientes.add(new JLabel("Teléfono:"));
        panelInputsClientes.add(txtTelefono);
        panelInputsClientes.add(new JLabel("Dirección:"));
        panelInputsClientes.add(txtDireccion);
        panelInputsClientes.add(new JLabel("ID Pedido:"));
        panelInputsClientes.add(txtIdPedidoCliente);

        JButton btnAgregarCliente = new JButton("Agregar");
        JButton btnEliminarCliente = new JButton("Eliminar");
        JButton btnActualizarCliente = new JButton("Actualizar");

        panelInputsClientes.add(btnAgregarCliente);
        panelInputsClientes.add(btnEliminarCliente);
        panelInputsClientes.add(btnActualizarCliente);

        panelClientes.add(panelInputsClientes, BorderLayout.SOUTH);

        // PANEL PEDIDOS
        JPanel panelPedidos = new JPanel(new BorderLayout());
        panelPedidos.setBackground(new Color(255, 200, 200));

        JLabel lblPedidos = new JLabel("Pedidos");
        lblPedidos.setFont(new Font("Arial", Font.BOLD, 18));
        panelPedidos.add(lblPedidos, BorderLayout.NORTH);

        modeloPedidos = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "ID Cliente", "Precio"}, 0);
        tablaPedidos = new JTable(modeloPedidos);
        panelPedidos.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JPanel panelInputsPedidos = new JPanel();

        txtNombrePedido = new JTextField(10);
        txtCantidadPedido = new JTextField(5);
        txtIdClientePedido = new JTextField(5);
        txtPrecioPedido = new JTextField(7);

        panelInputsPedidos.add(new JLabel("Nombre:"));
        panelInputsPedidos.add(txtNombrePedido);
        panelInputsPedidos.add(new JLabel("Cantidad:"));
        panelInputsPedidos.add(txtCantidadPedido);
        panelInputsPedidos.add(new JLabel("ID Cliente:"));
        panelInputsPedidos.add(txtIdClientePedido);
        panelInputsPedidos.add(new JLabel("Precio:"));
        panelInputsPedidos.add(txtPrecioPedido);

        JButton btnAgregarPedido = new JButton("Agregar");
        JButton btnEliminarPedido = new JButton("Eliminar");
        JButton btnActualizarPedido = new JButton("Actualizar");

        panelInputsPedidos.add(btnAgregarPedido);
        panelInputsPedidos.add(btnEliminarPedido);
        panelInputsPedidos.add(btnActualizarPedido);

        panelPedidos.add(panelInputsPedidos, BorderLayout.SOUTH);

        panelPrincipal.add(panelClientes);
        panelPrincipal.add(panelPedidos);

        // Logger Panel abajo
        JPanel panelLogger = new JPanel(new BorderLayout());
        panelLogger.setBorder(BorderFactory.createTitledBorder("Logger"));
        textAreaLogger = new JTextArea(6, 80);
        textAreaLogger.setEditable(false);
        JScrollPane scrollLogger = new JScrollPane(textAreaLogger);
        panelLogger.add(scrollLogger, BorderLayout.CENTER);

        add(panelLogger, BorderLayout.SOUTH);

        // Carga inicial de datos
        cargarDatosClientes();
        cargarDatosPedidos();

        // Listeners Clientes
        btnAgregarCliente.addActionListener(e -> {
            String nombre = txtNombreCliente.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            int idPedido = 0;
            try {
                idPedido = Integer.parseInt(txtIdPedidoCliente.getText().trim());
            } catch (NumberFormatException ignored) {}

            if(nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Teléfono y Dirección son obligatorios.");
                return;
            }

            Cliente cliente = new Cliente(0, nombre, telefono, direccion, idPedido);
            cliente.guardar();
            cargarDatosClientes();
            limpiarCamposCliente();
            loguear("Cliente agregado: " + nombre);
        });

        btnEliminarCliente.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
                return;
            }
            int id = (int)modeloClientes.getValueAt(fila, 0);
            String nombre = modeloClientes.getValueAt(fila, 1).toString();
            Cliente cliente = new Cliente(id, "", "", "", 0);
            cliente.eliminar();
            cargarDatosClientes();
            loguear("Cliente eliminado: ID " + id + " Nombre " + nombre);
        });

        btnActualizarCliente.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
                return;
            }
            int id = (int)modeloClientes.getValueAt(fila, 0);
            String nombre = txtNombreCliente.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            int idPedido = 0;
            try {
                idPedido = Integer.parseInt(txtIdPedidoCliente.getText().trim());
            } catch (NumberFormatException ignored) {}

            if(nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Teléfono y Dirección son obligatorios.");
                return;
            }

            Cliente cliente = new Cliente(id, nombre, telefono, direccion, idPedido);
            cliente.actualizar();
            cargarDatosClientes();
            limpiarCamposCliente();
            loguear("Cliente actualizado: ID " + id + " Nuevo nombre: " + nombre);
        });

        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if(fila >= 0) {
                    txtNombreCliente.setText(modeloClientes.getValueAt(fila, 1).toString());
                    txtTelefono.setText(modeloClientes.getValueAt(fila, 2).toString());
                    txtDireccion.setText(modeloClientes.getValueAt(fila, 3).toString());
                    txtIdPedidoCliente.setText(modeloClientes.getValueAt(fila, 4).toString());
                }
            }
        });

        // Listeners Pedidos
        btnAgregarPedido.addActionListener(e -> {
            String nombre = txtNombrePedido.getText().trim();
            int cantidad, idCliente;
            double precio;
            try {
                cantidad = Integer.parseInt(txtCantidadPedido.getText().trim());
                idCliente = Integer.parseInt(txtIdClientePedido.getText().trim());
                precio = Double.parseDouble(txtPrecioPedido.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos de pedido inválidos.");
                return;
            }
            if(nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre es obligatorio para el pedido.");
                return;
            }
            Pedido pedido = new Pedido(0, nombre, cantidad, idCliente, precio);
            pedido.guardar();
            cargarDatosPedidos();
            limpiarCamposPedido();
            loguear("Pedido agregado: " + nombre);
        });

        btnEliminarPedido.addActionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.");
                return;
            }
            int id = (int)modeloPedidos.getValueAt(fila, 0);
            String nombre = modeloPedidos.getValueAt(fila, 1).toString();
            Pedido pedido = new Pedido(id, "", 0, 0, 0);
            pedido.eliminar();
            cargarDatosPedidos();
            loguear("Pedido eliminado: ID " + id + " Nombre " + nombre);
        });

        btnActualizarPedido.addActionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido para actualizar.");
                return;
            }
            int id = (int)modeloPedidos.getValueAt(fila, 0);
            String nombre = txtNombrePedido.getText().trim();
            int cantidad, idCliente;
            double precio;
            try {
                cantidad = Integer.parseInt(txtCantidadPedido.getText().trim());
                idCliente = Integer.parseInt(txtIdClientePedido.getText().trim());
                precio = Double.parseDouble(txtPrecioPedido.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos de pedido inválidos.");
                return;
            }
            if(nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre es obligatorio para el pedido.");
                return;
            }
            Pedido pedido = new Pedido(id, nombre, cantidad, idCliente, precio);
            pedido.actualizar();
            cargarDatosPedidos();
            limpiarCamposPedido();
            loguear("Pedido actualizado: ID " + id + " Nuevo nombre: " + nombre);
        });

        tablaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaPedidos.getSelectedRow();
                if(fila >= 0) {
                    txtNombrePedido.setText(modeloPedidos.getValueAt(fila,1).toString());
                    txtCantidadPedido.setText(modeloPedidos.getValueAt(fila,2).toString());
                    txtIdClientePedido.setText(modeloPedidos.getValueAt(fila,3).toString());
                    txtPrecioPedido.setText(modeloPedidos.getValueAt(fila,4).toString());
                }
            }
        });
    }

    private void cargarDatosClientes() {
        modeloClientes.setRowCount(0);
        ResultSet rs = Cliente.obtenerClientes();
        try {
            if(rs != null) {
                while(rs.next()) {
                    modeloClientes.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("direccion"),
                            rs.getInt("idPedido")
                    });
                }
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosPedidos() {
        modeloPedidos.setRowCount(0);
        ResultSet rs = Pedido.obtenerPedidos();
        try {
            if(rs != null) {
                while(rs.next()) {
                    modeloPedidos.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getInt("idCliente"),
                            rs.getDouble("precio")
                    });
                }
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCamposCliente() {
        txtNombreCliente.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtIdPedidoCliente.setText("");
    }

    private void limpiarCamposPedido() {
        txtNombrePedido.setText("");
        txtCantidadPedido.setText("");
        txtIdClientePedido.setText("");
        txtPrecioPedido.setText("");
    }

    private void loguear(String mensaje) {
        textAreaLogger.append(mensaje + "\n");
        textAreaLogger.setCaretPosition(textAreaLogger.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazPrincipal().setVisible(true));
    }
}

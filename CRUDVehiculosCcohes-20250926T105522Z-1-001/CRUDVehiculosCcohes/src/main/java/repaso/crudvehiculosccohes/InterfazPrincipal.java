/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.crudvehiculosccohes;

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
    // Motos
    private JTextField txtNombreMoto, txtMarcaMoto, txtCilindradaMoto;
    private JTable tablaMotos;
    private DefaultTableModel modeloMotos;

    // Coches
    private JTextField txtNombreCoche, txtMarcaCoche, txtPuertasCoche, txtTipoCombustibleCoche;
    private JTable tablaCoches;
    private DefaultTableModel modeloCoches;

    public InterfazPrincipal() {
        setTitle("CRUD Motos y Coches");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Panel motos (verde claro)
        JPanel panelMotos = new JPanel(new BorderLayout(5, 5));
        panelMotos.setBackground(new Color(200, 255, 200));
        add(panelMotos);

        JLabel lblMotos = new JLabel("Motos");
        lblMotos.setFont(new Font("Arial", Font.BOLD, 18));
        panelMotos.add(lblMotos, BorderLayout.NORTH);

        modeloMotos = new DefaultTableModel(new String[]{"ID", "Nombre", "Marca", "Cilindrada"}, 0);
        tablaMotos = new JTable(modeloMotos);
        panelMotos.add(new JScrollPane(tablaMotos), BorderLayout.CENTER);

        // Panel con inputs y botones en layout vertical para motos
        JPanel panelMotosSur = new JPanel();
        panelMotosSur.setLayout(new BoxLayout(panelMotosSur, BoxLayout.Y_AXIS));

        JPanel panelInputsMotos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNombreMoto = new JTextField(10);
        txtMarcaMoto = new JTextField(10);
        txtCilindradaMoto = new JTextField(5);

        panelInputsMotos.add(new JLabel("Nombre:"));
        panelInputsMotos.add(txtNombreMoto);
        panelInputsMotos.add(new JLabel("Marca:"));
        panelInputsMotos.add(txtMarcaMoto);
        panelInputsMotos.add(new JLabel("Cilindrada:"));
        panelInputsMotos.add(txtCilindradaMoto);

        panelMotosSur.add(panelInputsMotos);

        JPanel panelBtnsMotos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregarMoto = new JButton("Agregar Moto");
        JButton btnEliminarMoto = new JButton("Eliminar Moto");
        JButton btnActualizarMoto = new JButton("Actualizar Moto");

        panelBtnsMotos.add(btnAgregarMoto);
        panelBtnsMotos.add(btnEliminarMoto);
        panelBtnsMotos.add(btnActualizarMoto);

        panelMotosSur.add(panelBtnsMotos);

        panelMotos.add(panelMotosSur, BorderLayout.SOUTH);

        // Panel coches (rojo claro)
        JPanel panelCoches = new JPanel(new BorderLayout(5, 5));
        panelCoches.setBackground(new Color(255, 200, 200));
        add(panelCoches);

        JLabel lblCoches = new JLabel("Coches");
        lblCoches.setFont(new Font("Arial", Font.BOLD, 18));
        panelCoches.add(lblCoches, BorderLayout.NORTH);

        modeloCoches = new DefaultTableModel(new String[]{"ID", "Nombre", "Marca", "Puertas", "Tipo Combustible"}, 0);
        tablaCoches = new JTable(modeloCoches);
        panelCoches.add(new JScrollPane(tablaCoches), BorderLayout.CENTER);

        // Panel con inputs y botones en layout vertical para coches
        JPanel panelCochesSur = new JPanel();
        panelCochesSur.setLayout(new BoxLayout(panelCochesSur, BoxLayout.Y_AXIS));

        JPanel panelInputsCoches = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNombreCoche = new JTextField(10);
        txtMarcaCoche = new JTextField(10);
        txtPuertasCoche = new JTextField(5);
        txtTipoCombustibleCoche = new JTextField(10);

        panelInputsCoches.add(new JLabel("Nombre:"));
        panelInputsCoches.add(txtNombreCoche);
        panelInputsCoches.add(new JLabel("Marca:"));
        panelInputsCoches.add(txtMarcaCoche);
        panelInputsCoches.add(new JLabel("Puertas:"));
        panelInputsCoches.add(txtPuertasCoche);
        panelInputsCoches.add(new JLabel("Tipo Combustible:"));
        panelInputsCoches.add(txtTipoCombustibleCoche);

        panelCochesSur.add(panelInputsCoches);

        JPanel panelBtnsCoches = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregarCoche = new JButton("Agregar Coche");
        JButton btnEliminarCoche = new JButton("Eliminar Coche");
        JButton btnActualizarCoche = new JButton("Actualizar Coche");

        panelBtnsCoches.add(btnAgregarCoche);
        panelBtnsCoches.add(btnEliminarCoche);
        panelBtnsCoches.add(btnActualizarCoche);

        panelCochesSur.add(panelBtnsCoches);

        panelCoches.add(panelCochesSur, BorderLayout.SOUTH);

        cargarDatosMotos();
        cargarDatosCoches();

        // Motos listeners
        btnAgregarMoto.addActionListener(e -> {
            String nombre = txtNombreMoto.getText().trim();
            String marca = txtMarcaMoto.getText().trim();
            int cilindrada;
            try {
                cilindrada = Integer.parseInt(txtCilindradaMoto.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cilindrada inválida");
                return;
            }
            if(nombre.isEmpty() || marca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Marca son obligatorios");
                return;
            }
            Moto moto = new Moto(0, nombre, marca, cilindrada);
            moto.guardar();
            limpiarCamposMoto();
            cargarDatosMotos();
        });

        btnEliminarMoto.addActionListener(e -> {
            int fila = tablaMotos.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una moto para eliminar.");
                return;
            }
            int id = (int)modeloMotos.getValueAt(fila, 0);
            Moto moto = new Moto(id, "", "", 0);
            moto.eliminar();
            cargarDatosMotos();
        });

        btnActualizarMoto.addActionListener(e -> {
            int fila = tablaMotos.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una moto para actualizar.");
                return;
            }
            int id = (int)modeloMotos.getValueAt(fila, 0);
            String nombre = txtNombreMoto.getText().trim();
            String marca = txtMarcaMoto.getText().trim();
            int cilindrada;
            try {
                cilindrada = Integer.parseInt(txtCilindradaMoto.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cilindrada inválida");
                return;
            }
            if(nombre.isEmpty() || marca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Marca son obligatorios");
                return;
            }
            Moto moto = new Moto(id, nombre, marca, cilindrada);
            moto.actualizar();
            limpiarCamposMoto();
            cargarDatosMotos();
        });

        tablaMotos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaMotos.getSelectedRow();
                if(fila >= 0) {
                    txtNombreMoto.setText(modeloMotos.getValueAt(fila, 1).toString());
                    txtMarcaMoto.setText(modeloMotos.getValueAt(fila, 2).toString());
                    txtCilindradaMoto.setText(modeloMotos.getValueAt(fila, 3).toString());
                }
            }
        });

        // Coches listeners
        btnAgregarCoche.addActionListener(e -> {
            String nombre = txtNombreCoche.getText().trim();
            String marca = txtMarcaCoche.getText().trim();
            int puertas;
            String tipoCombustible = txtTipoCombustibleCoche.getText().trim();
            try {
                puertas = Integer.parseInt(txtPuertasCoche.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número de puertas inválido");
                return;
            }
            if(nombre.isEmpty() || marca.isEmpty() || tipoCombustible.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Marca y Tipo Combustible son obligatorios");
                return;
            }
            Coche coche = new Coche(0, nombre, marca, puertas, tipoCombustible);
            coche.guardar();
            limpiarCamposCoche();
            cargarDatosCoches();
        });

        btnEliminarCoche.addActionListener(e -> {
            int fila = tablaCoches.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un coche para eliminar.");
                return;
            }
            int id = (int)modeloCoches.getValueAt(fila, 0);
            Coche coche = new Coche(id, "", "", 0, "");
            coche.eliminar();
            cargarDatosCoches();
        });

        btnActualizarCoche.addActionListener(e -> {
            int fila = tablaCoches.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un coche para actualizar.");
                return;
            }
            int id = (int)modeloCoches.getValueAt(fila, 0);
            String nombre = txtNombreCoche.getText().trim();
            String marca = txtMarcaCoche.getText().trim();
            String tipoCombustible = txtTipoCombustibleCoche.getText().trim();
            int puertas;
            try {
                puertas = Integer.parseInt(txtPuertasCoche.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número de puertas inválido");
                return;
            }
            if(nombre.isEmpty() || marca.isEmpty() || tipoCombustible.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Marca y Tipo Combustible son obligatorios");
                return;
            }
            Coche coche = new Coche(id, nombre, marca, puertas, tipoCombustible);
            coche.actualizar();
            limpiarCamposCoche();
            cargarDatosCoches();
        });

        tablaCoches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaCoches.getSelectedRow();
                if(fila >= 0) {
                    txtNombreCoche.setText(modeloCoches.getValueAt(fila, 1).toString());
                    txtMarcaCoche.setText(modeloCoches.getValueAt(fila, 2).toString());
                    txtPuertasCoche.setText(modeloCoches.getValueAt(fila, 3).toString());
                    txtTipoCombustibleCoche.setText(modeloCoches.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private void cargarDatosMotos() {
        modeloMotos.setRowCount(0);
        ResultSet rs = Moto.obtenerMotos();
        try {
            if(rs != null) {
                while(rs.next()) {
                    modeloMotos.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("marca"),
                            rs.getInt("cilindrada")
                    });
                }
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosCoches() {
        modeloCoches.setRowCount(0);
        ResultSet rs = Coche.obtenerCoches();
        try {
            if(rs != null) {
                while(rs.next()) {
                    modeloCoches.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("marca"),
                            rs.getInt("puertas"),
                            rs.getString("tipoCombustible")
                    });
                }
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCamposMoto() {
        txtNombreMoto.setText("");
        txtMarcaMoto.setText("");
        txtCilindradaMoto.setText("");
    }

    private void limpiarCamposCoche() {
        txtNombreCoche.setText("");
        txtMarcaCoche.setText("");
        txtPuertasCoche.setText("");
        txtTipoCombustibleCoche.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazPrincipal().setVisible(true));
    }
}


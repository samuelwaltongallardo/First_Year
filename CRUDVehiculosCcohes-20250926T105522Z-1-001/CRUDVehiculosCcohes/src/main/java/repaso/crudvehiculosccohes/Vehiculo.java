/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.crudvehiculosccohes;

/**
 *
 * @author samwe
 */
public abstract class Vehiculo {
    protected int id;
    protected String nombre;
    protected String marca;

    public Vehiculo(int id, String nombre, String marca) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMarca(String marca) { this.marca = marca; }

    public abstract void guardar();
    public abstract void eliminar();
    public abstract void actualizar();
}
/*
CREATE TABLE motos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    cilindrada INT NOT NULL
);

CREATE TABLE coches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    puertas INT NOT NULL,
    tipoCombustible VARCHAR(50) NOT NULL
);
*/
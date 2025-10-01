/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso.versionconlogger;

/**
 *
 * @author samwe
 */
public abstract class Entidad {
    protected int id;
    protected String nombre;

    public Entidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public abstract void guardar();
    public abstract void eliminar();
    public abstract void actualizar();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajo_ipc.controllers;
import java.time.LocalDate;

/**
 *
 * @author Belén Rodríguez
 */
public class Gastos {
    private String nombre;
    private LocalDate fecha;
    private int unidades;
    private double precio;
    private String descripcion;
    
    public Gastos(String nom, LocalDate fec, int uni, double prec, String desc) {
        nombre = nom;
        unidades = uni;
        precio = prec;
        descripcion = desc;
        fecha = fec;
        
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getUnidades() {
        return unidades;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }   
}

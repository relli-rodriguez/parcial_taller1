package ucom.py.entities;

import java.util.List;

public class Presupuesto {
    // Identificador único del presupuesto
    private int id;
    // Fecha de inicio del presupuesto
    private String fechaInicio;
    // Fecha de fin del presupuesto
    private String fechaFin;
    // Monto total asignado al presupuesto
    private double montoPresupuestado;
    // Lista de gastos asociados al presupuesto
    private List<Gasto> gastos;

    // Obtengo el identificador del presupuesto
    public int getId() {
        return id;
    }

    // Establezco el identificador del presupuesto
    public void setId(int id) {
        this.id = id;
    }

    // Obtengo la fecha de inicio del presupuesto
    public String getFechaInicio() {
        return fechaInicio;
    }

    // Establezco la fecha de inicio del presupuesto
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    // Obtengo la fecha de fin del presupuesto
    public String getFechaFin() {
        return fechaFin;
    }

    // Establezco la fecha de fin del presupuesto
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Obtengo el monto presupuestado
    public double getMontoPresupuestado() {
        return montoPresupuestado;
    }

    // Establezco el monto presupuestado
    public void setMontoPresupuestado(double montoPresupuestado) {
        this.montoPresupuestado = montoPresupuestado;
    }

    // Obtengo la lista de gastos
    public List<Gasto> getGastos() {
        return gastos;
    }

    // Establezco la lista de gastos
    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }
}

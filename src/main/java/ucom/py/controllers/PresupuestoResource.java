package ucom.py.controllers;

import ucom.py.entities.Presupuesto;
import ucom.py.entities.Gasto;
import ucom.py.repository.PresupuestoRepository;

import java.util.List;
import jakarta.inject.Inject; // Importar @Inject
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/presupuestos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PresupuestoResource {

    @Inject
    private PresupuestoRepository presupuestoRepository; // Inyección del repositorio

    @GET
    public Response getPresupuestos() {
        // Devuelvo la lista de todos los presupuestos
        List<Presupuesto> presupuestos = presupuestoRepository.listar();
        return Response.ok(presupuestos).build();
    }

    @GET
    @Path("/{presupuestoId}")
    public Response getPresupuestoById(@PathParam("presupuestoId") int id) {
        // Obtengo un presupuesto específico por su ID
        Presupuesto presupuesto = presupuestoRepository.obtenerById(id);
        if (presupuesto != null) {
            return Response.ok(presupuesto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Presupuesto no encontrado").build();
        }
    }

    @POST
    public Response crearPresupuesto(Presupuesto nuevoPresupuesto) {
        // Agrego un nuevo presupuesto a la lista y lo guardo en el archivo JSON
        presupuestoRepository.agregarPresupuesto(nuevoPresupuesto);
        presupuestoRepository.guardarDatos();
        return Response.status(Response.Status.CREATED).entity(nuevoPresupuesto).build();
    }

    @POST
    @Path("/agregar-gasto/{presupuestoId}")
    public Response agregarGasto(@PathParam("presupuestoId") int id, Gasto nuevoGasto) {
        // Agrego un nuevo gasto al presupuesto específico
        Presupuesto presupuesto = presupuestoRepository.obtenerById(id);
        if (presupuesto != null) {
            // Calculo el total de los gastos actuales usando mapToDouble
            double totalGastos = presupuesto.getGastos().stream()
                .mapToDouble(Gasto::getMonto) // Usa mapToDouble si getMonto() devuelve double
                .sum();
            // Verifico si el nuevo gasto no supera el monto presupuestado
            if (totalGastos + nuevoGasto.getMonto() <= presupuesto.getMontoPresupuestado()) {
                presupuesto.getGastos().add(nuevoGasto);
                presupuestoRepository.guardarDatos();
                return Response.ok(presupuesto).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("El gasto supera el monto presupuestado").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Presupuesto no encontrado").build();
        }
    }

    @GET
    @Path("/total-gastos/{presupuestoId}")
    public Response getTotalGastos(@PathParam("presupuestoId") int id) {
        // Calculo la sumatoria de los gastos de un presupuesto específico
        Presupuesto presupuesto = presupuestoRepository.obtenerById(id);
        if (presupuesto != null) {
            // Calculo el total de los gastos actuales usando mapToDouble
            double totalGastos = presupuesto.getGastos().stream()
                .mapToDouble(Gasto::getMonto) // Usa mapToDouble si getMonto() devuelve double
                .sum();
            return Response.ok(totalGastos).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Presupuesto no encontrado").build();
        }
    }
}

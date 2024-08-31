package ucom.py.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import ucom.py.entities.Presupuesto;

@ApplicationScoped
public class PresupuestoRepository {
    private static final String FILE_PATH = "src/main/resources/data/presupuesto.json";
    private List<Presupuesto> presupuestoList;
    private ObjectMapper objectMapper;

    public PresupuestoRepository() {
        objectMapper = new ObjectMapper();
        presupuestoList = cargarDatos();
    }
    public List<Presupuesto> cargarDatos() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                System.out.println("Datos cargados: " + presupuestoList);
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void guardarDatos() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), presupuestoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Presupuesto obtenerById(Integer id) {
    return presupuestoList.stream()
            .filter(presupuesto -> presupuesto.getId() == id) 
            .findFirst()
            .orElse(null);
}
    


    public List<Presupuesto> listar() {
        return new ArrayList<>(presupuestoList);
    }

    public Presupuesto agregarPresupuesto(Presupuesto param) {
        Integer newId = presupuestoList.isEmpty() ? 1
                : presupuestoList.stream()
                        .mapToInt(Presupuesto::getId)
                        .max()
                        .getAsInt() + 1;

        param.setId(newId);
        presupuestoList.add(param);
        guardarDatos();
        return param;
    }

    public void eliminar(Integer id) {
        presupuestoList = presupuestoList.stream()
                .filter(presupuesto -> !Integer.valueOf(presupuesto.getId()).equals(id)) 
                .collect(Collectors.toList());
        guardarDatos();
    }
    
}

package org.example;

import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {

    void insertarCliente(T cliente);

    T obtenerCliente(Long id);

    List<T> listarMejoresClientes(Long ventas);

    ArrayList<T> getAll();

    T save(T data);

    T update(T data);

    void delete(T data);

    void saveAll(List<T> data);

    void obtenerEstadisticas();

}
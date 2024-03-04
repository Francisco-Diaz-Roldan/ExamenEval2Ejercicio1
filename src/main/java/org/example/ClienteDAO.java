package org.example.cliente;

import org.example.DAO;
import org.example.enumerado.Estado;
import org.example.objectdbutils.ObjectDBUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements DAO<Cliente> {
    @Override
    public void insertarCliente(Cliente cliente) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cliente obtenerCliente(Long id) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Cliente.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Cliente> listarMejoresClientes(Long ventas) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.ventas > :ventas", Cliente.class);
            query.setParameter("ventas", ventas);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public ArrayList<Cliente> getAll() {
        var salida = new ArrayList<Cliente>(0);
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
            salida = new ArrayList<>(query.getResultList());
        } finally {
            entityManager.close();
        }
        return salida;
    }

    @Override
    public Cliente save(Cliente data) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(data);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return data;
    }

    @Override
    public Cliente update(Cliente data) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            data = entityManager.merge(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return data;
    }

    @Override
    public void delete(Cliente data) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(data)) {
                data = entityManager.merge(data);
            }
            entityManager.remove(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void saveAll(List<Cliente> data) {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            for (Cliente cliente : data) {
                entityManager.persist(cliente);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void obtenerEstadisticas() {
        EntityManager entityManager = ObjectDBUtils.getEntityManagerFactory().createEntityManager();

        try {
            // Total de ventas de todos los clientes
            Long totalVentas = entityManager.createQuery("SELECT SUM(c.ventas) FROM Cliente c", Long.class).getSingleResult();
            System.out.println("Total de ventas de todos los clientes: " + totalVentas);

            // Promedio de ventas de todos los clientes activos
            Double promedioVentasActivos = entityManager.createQuery(
                            "SELECT AVG(c.ventas) FROM Cliente c WHERE c.estado = :estado", Double.class)
                    .setParameter("estado", Estado.activo)
                    .getSingleResult();
            System.out.println("Promedio de ventas de clientes activos: " + promedioVentasActivos);

            // Cantidad de clientes inactivos con total de ventas mayores a 300
            Long cantidadInactivosVentasMayoresCero = entityManager.createQuery(
                            "SELECT COUNT(c) FROM Cliente c WHERE c.estado = :estado AND c.ventas > 300", Long.class)
                    .setParameter("estado", Estado.inactivo)
                    .getSingleResult();
            System.out.println("Cantidad de clientes inactivos con ventas mayores a 0: " + cantidadInactivosVentasMayoresCero);

        } finally {
            entityManager.close();
        }
    }

}

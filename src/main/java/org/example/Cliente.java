package org.example.cliente;

import lombok.Data;
import org.example.enumerado.Estado;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Long ventas;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String direccion;

    private String email;

    private Integer edad;

    private String telefono;


    public Cliente(Long id, String nombre, Long ventas, Estado estado, String direccion, String email, Integer edad,
                   String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.ventas = ventas;
        this.estado = estado;
        this.direccion = direccion;
        this.email = email;
        this.edad = edad;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ventas=" + ventas +
                ", estado=" + estado +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}

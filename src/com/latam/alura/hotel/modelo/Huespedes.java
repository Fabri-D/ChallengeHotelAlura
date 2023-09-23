package com.latam.alura.hotel.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * Esta clase representa la entidad de "Huespedes" en la base de datos.
 */
@Entity
public class Huespedes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mi_secuencia")
    @SequenceGenerator(name = "mi_secuencia", sequenceName = "mi_secuencia", initialValue = 1, allocationSize = 1)
    private Long id;
    private String nombre;
    private String apellido;
    private Date fechaDeNacimiento;
    private String nacionalidad;
    private String telefono;
    
    //private Long idReserva; // FK a la entidad Reservas
    

    @OneToMany(mappedBy = "huesped", cascade=CascadeType.ALL)
    private List<Reservas> reservas = new ArrayList<>();


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Long getId() {
		return id;
	}

	public List<Reservas> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) {
		this.reservas = reservas;
	}
	
	public void addReservas(Reservas reserva) {
		this.reservas.add(reserva);
	}

	@Override
    public String toString() {
        return "Huespedes{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", fechaDeNacimiento=" + fechaDeNacimiento +
               ", nacionalidad='" + nacionalidad + '\'' +
               ", telefono='" + telefono + '\'' +
               '}';
    }
    
    
}
package com.latam.alura.hotel.modelo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Esta clase representa la entidad de "Reservas" en la base de datos.
 */
@Entity
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mi_secuencia")
    @SequenceGenerator(name = "mi_secuencia", sequenceName = "mi_secuencia", initialValue = 1, allocationSize = 1)
    private Long id;
    private Date fecha_entrada;
    private Date fecha_salida;
    private BigDecimal valor;
    private String formaPago;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Huespedes huesped;
	
    public Date getFecha_entrada() {
		return fecha_entrada;
	}
	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}
	public Date getFecha_salida() {
		return fecha_salida;
	}
	public void setFecha_salida(Date fecha_salida) {
		this.fecha_salida = fecha_salida;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public Huespedes getHuesped() {
		return huesped;
	}
	public void setHuesped(Huespedes huesped) {
		this.huesped = huesped;
	}
	@Override
	public String toString() {
	    return "Reservas{" +
	            "id=" + id +
	            ", fecha_entrada=" + fecha_entrada +
	            ", fecha_salida=" + fecha_salida +
	            ", valor=" + valor +
	            ", formaPago='" + formaPago + '\'' +
	            '}';
	}
	
    
    
    
    // Constructores, métodos getter/setter y otros campos
}
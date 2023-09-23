package com.latam.alura.hotel.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Esta clase representa la entidad de "Usuarios" en la base de datos.
 */
@Entity
public class Usuarios {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mi_secuencia")
    @SequenceGenerator(name = "mi_secuencia", sequenceName = "mi_secuencia", initialValue = 1, allocationSize = 1)
    private Long id;
    private String login;
    private String contrasenia;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public Long getId() {
		return id;
	}
    
	@Override
    public String toString() {
        return "Usuarios{" +
               "id=" + id + '\'' +
               ", login= '" + login + '\'' +
               ", contrasenia='" + contrasenia +
               '}';
    }
}

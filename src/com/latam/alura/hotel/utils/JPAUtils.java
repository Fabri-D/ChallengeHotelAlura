package com.latam.alura.hotel.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase de utilidad para gestionar el EntityManager en aplicaciones que utilizan JPA.
 * Proporciona métodos para obtener un EntityManager y una fábrica de EntityManager.
 */
public class JPAUtils {
	
	// Fábrica de EntityManager
	static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("AluraHotel");
	
	/**
     * Obtiene un EntityManager para interactuar con la base de datos.
     *
     * @return Un EntityManager listo para su uso.
     */
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
}

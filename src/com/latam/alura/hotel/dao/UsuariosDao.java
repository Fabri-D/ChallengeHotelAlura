package com.latam.alura.hotel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.latam.alura.hotel.modelo.Huespedes;
import com.latam.alura.hotel.modelo.Usuarios;

/**
 * Esta clase proporciona métodos para acceder y gestionar usuarios en la base de datos.
 */
public class UsuariosDao {
	
	private EntityManager em;
	
	 /**
     * Constructor que recibe una instancia de EntityManager para interactuar con la base de datos.
     *
     * @param em EntityManager para la gestión de entidades.
     */
	public UsuariosDao(EntityManager em) {
		this.em = em;
	}
	
	/**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario que se va a guardar.
     */
	@Transactional
	public void guardarUsuario(Usuarios usuario) {
		this.em.getTransaction().begin();
		this.em.persist(usuario);
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
	/**
     * Busca el ID de un usuario por su nombre de login en la base de datos.
     *
     * @param nombre El nombre de login del usuario.
     * @return El ID del usuario encontrado o null si no se encuentra.
     *
     */
	@Transactional
	public Long buscarIdUsuarioPorNombre(String nombre) {
		try {
	        TypedQuery<Long> query = em.createQuery("SELECT id FROM Usuarios u WHERE u.login = :nombre", Long.class);
	        query.setParameter("nombre", nombre);
	        Long id=null;
	        try {
		        id = query.getSingleResult();
	        } catch (NoResultException e) {
	        	System.out.println("El usuario no se encontró en la base de datos");
	        }
		        
	        // Verifica si se encontró un usuario con ese nombre
	        if (id != null) {
	            // Si se encontró un usuario, devuelve su ID
	            return id;
	        } else {
	            return null;
	        }
		
		} catch (Exception e) {
    	   System.out.println("El usuario no se encontró en la base de datos");
    	}
		return null;
	}
	
	/**
     * Busca la clave (contraseña) de un usuario por su ID en la base de datos.
     *
     * @param id El ID del usuario.
     * @return La clave (contraseña) del usuario encontrado o null si no se encuentra.
     */
	@Transactional
	public Object buscarClavePorId(Long id) {
		try {
	        // Realiza la búsqueda directamente en tu capa de acceso a datos (DAO)
	        
	        TypedQuery<Usuarios> query = em.createQuery("FROM Usuarios u WHERE u.id = :id", Usuarios.class);
	        query.setParameter("id", id);
	        
	        Usuarios usuario = query.getSingleResult();
	        var clave = usuario.getContrasenia();
	        
	        if (clave != null) {
	            
	            return clave;
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        System.out.println("No se pudo encontrar la clave por ID");
	        return null;
	    }
	}
	
	/**
     * Elimina todos los usuarios de la base de datos y devuelve una lista con los IDs de los usuarios eliminados.
     *
     * @return Una lista de IDs de usuarios eliminados.
     */
	@Transactional
	public List<Long> eliminarTodosLosUsuarios() {
	    List<Long> idsEliminados = new ArrayList<>();
	    
	    try {
	        this.em.getTransaction().begin();

	        // Consulta para obtener todos los IDs de usuarios
	        TypedQuery<Long> query = em.createQuery("SELECT id FROM Usuarios", Long.class);
	        List<Long> ids = query.getResultList();

	        // Elimina cada usuario y agrega su ID a la lista de eliminados
	        for (Long id : ids) {
	            Usuarios usuario = em.find(Usuarios.class, id);
	            if (usuario != null) {
	                em.remove(usuario);
	                idsEliminados.add(id);
	            }
	        }

	        // Realiza el commit de la transacción
	        this.em.getTransaction().commit();
	    } catch (Exception e) {
	        if (this.em.getTransaction() != null) {
	            this.em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }

	    return idsEliminados;
	}

	
}

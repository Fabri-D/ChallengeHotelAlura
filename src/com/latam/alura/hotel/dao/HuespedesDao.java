package com.latam.alura.hotel.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.latam.alura.hotel.modelo.Huespedes;
import com.latam.alura.hotel.modelo.Reservas;

/**
 * Esta clase proporciona métodos para interactuar con la entidad Huespedes en la base de datos.
 * Se encarga de guardar, buscar, actualizar y eliminar huéspedes.
 */
public class HuespedesDao {
	
	/**
     * Crea una nueva instancia de HuespedesDao.
     *
     * @param em El EntityManager utilizado para acceder a la base de datos.
     */
	private EntityManager em;
	
	public HuespedesDao(EntityManager em) {
		// Constructor de la clase
		this.em = em;
	}
	
	/**
     * Guarda un huésped en la base de datos.
     *
     * @param huesped El objeto Huespedes que se va a guardar.
     */
	@Transactional
	public void guardarHuesped(Huespedes huesped) {
		this.em.getTransaction().begin();
		this.em.persist(huesped);
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
	/**
     * Busca un huésped por sus datos personales.
     *
     * @param nombre          El nombre del huésped.
     * @param apellido        El apellido del huésped.
     * @param fechaSinHora    La fecha de nacimiento del huésped sin la parte de la hora.
     * @param nacionalidad    La nacionalidad del huésped.
     * @param telefono        El número de teléfono del huésped.
     * @return El huésped encontrado o null si no se encontró ningún huésped.
     */
	@Transactional
	public Huespedes buscarHuespedPorDatos(String nombre, String apellido, Date fechaSinHora, String nacionalidad,
			String telefono) {
		try {
	        this.em.getTransaction().begin();	     

	        String jpql = "SELECT h FROM Huespedes h " +
	                      "WHERE h.nombre = :nombre " +
	                      "AND h.apellido = :apellido " +
	                      "AND h.fechaDeNacimiento = :fechaSinHora " +
	                      "AND h.nacionalidad = :nacionalidad " +
	                      "AND h.telefono = :telefono";

	        TypedQuery<Huespedes> query = em.createQuery(jpql, Huespedes.class);
	        query.setParameter("nombre", nombre);
	        query.setParameter("apellido", apellido);
	        query.setParameter("fechaSinHora", fechaSinHora);
	        query.setParameter("nacionalidad", nacionalidad);
	        query.setParameter("telefono", telefono);

	        List<Huespedes> resultados = query.getResultList();
	        
	        this.em.flush();
	        this.em.getTransaction().commit();

	        if (!resultados.isEmpty()) {
	            // Se encontró un huésped que coincide con los datos proporcionados
	            return resultados.get(0); // Devolvemos el primer resultado (asumiendo que no debería haber duplicados)
	        } else {
	            // No se encontró ningún huésped con los datos proporcionados
	            return null;
	        }
	    } catch (Exception e) {
	        this.em.getTransaction().rollback();
	        throw e;
	    }
	}
	
	/**
     * Agrega una reserva a un huésped existente.
     *
     * @param huesped       El huésped al que se le agregará la reserva.
     * @param nuevaReserva  La nueva reserva que se agregará al huésped.
     */
	@Transactional
	public void agregarReservaAHuesped(Huespedes huesped, Reservas nuevaReserva) {
	    try {
	        this.em.getTransaction().begin();

	        // Obtén una instancia gestionada del huésped existente desde la base de datos
	        Huespedes huespedParaActualizar = em.find(Huespedes.class, huesped.getId());

	        if (huespedParaActualizar != null) {
	            // Agrega la nueva reserva a la lista de reservas del huésped
	            huespedParaActualizar.getReservas().add(nuevaReserva);
	            
	            this.em.flush();
	            // Comete los cambios en la base de datos
	            em.getTransaction().commit();
	        } else {
	            // Maneja el caso en el que no se encontró el huésped existente
	        }
	    } catch (Exception e) {
	        this.em.getTransaction().rollback();
	        throw e;
	    }
	}
	
	/**
	 * Realiza una búsqueda en la base de datos de huéspedes que coincidan con un criterio de búsqueda, que puede ser
	 * un nombre, apellido, nacionalidad, teléfono, fecha de nacimiento, id del huésped o reserva asociada.
	 *
	 * @param criterioBusqueda El criterio de búsqueda que puede ser un nombre, apellido, nacionalidad, teléfono o fecha
	 * de nacimiento. Para buscar por fecha de nacimiento, el criterio debe estar en formato "dd/MM/yyyy".
	 * @return Una lista de objetos Huespedes que coinciden con el criterio de búsqueda, incluyendo resultados de nombres,
	 * apellidos, nacionalidades, números de teléfono o fechas de nacimiento que contienen el criterio. También se incluyen
	 * resultados de huéspedes y reservas que coincidan con un ID de huésped o ID de reserva.
	 */
	@Transactional
	public List<Huespedes> buscarEnDB(String criterioBusqueda) {
		if (!this.em.getTransaction().isActive()) {
	        this.em.getTransaction().begin();
	    }
		
		// Consulta para buscar huéspedes por nombre, apellido, nacionalidad o teléfono que coincidan con el criterio
		String queryString = "FROM Huespedes h LEFT JOIN FETCH h.reservas WHERE h.nombre LIKE :criterio OR h.apellido LIKE :criterio OR h.nacionalidad LIKE :criterio OR h.telefono LIKE :criterio";
		List<Huespedes> resultadosHuespedes = em.createQuery(queryString, Huespedes.class)
	        .setParameter("criterio", "%" + criterioBusqueda + "%")
	        .getResultList();
		
		// Consulta para buscar por ID de Huespedes
	    Long idHuespedes = null;
	    try {
	        idHuespedes = Long.parseLong(criterioBusqueda);
	    } catch (NumberFormatException e) {
	        // El criterio no es un Long válido, lo dejamos como null
	    }
	    
	    // Si idHuespedes es un Long válido, realiza la búsqueda por ID de Huespedes
	    List<Huespedes> resultadosPorId = new ArrayList<>();
	    if (idHuespedes != null) {
	        Huespedes huespedPorId = em.find(Huespedes.class, idHuespedes);
	        if (huespedPorId != null) {
	            resultadosPorId.add(huespedPorId);
	        }
	    }
	    
	    // Consulta para buscar el ID de Reservas
	    Long idReservas = null;
	    try {
	        idReservas = Long.parseLong(criterioBusqueda);
	    } catch (NumberFormatException e) {
	        // El criterio no es un Long válido, lo dejamos como null
	    }
	    
	    // Si idReservas es un Long válido, realiza la búsqueda por ID de Reservas,
	    //revisando si el valor está contenido en la lista de reservas de la tabla Huéspedes.
	    List<Huespedes> resultadosReservas = new ArrayList<>();
	    
	    if (idReservas != null) {
	        String queryStringReservas = "SELECT h FROM Huespedes h JOIN h.reservas r WHERE CONCAT(',', r.id, ',') LIKE :idReservasString";
	        resultadosReservas = em.createQuery(queryStringReservas, Huespedes.class)
	            .setParameter("idReservasString", "%," + idReservas + ",%")
	            .getResultList();
	    }
	    
	    // Combina los resultados en una sola lista
	    resultadosHuespedes.addAll(resultadosPorId);
	    resultadosHuespedes.addAll(resultadosReservas);
	    
	    // Intenta buscar por fecha de nacimiento
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha esperado
	    try {
	        Date fechaBusqueda = dateFormat.parse(criterioBusqueda); // Intenta parsear el criterio a fecha
	        String queryStringFechaNac = "FROM Huespedes WHERE fechaDeNacimiento = :fechaBusqueda";
	        
	        List<Huespedes> resultadosFechaNac = em.createQuery(queryStringFechaNac, Huespedes.class)
	            .setParameter("fechaBusqueda", fechaBusqueda)
	            .getResultList();

	        resultadosHuespedes.addAll(resultadosFechaNac);
	    
	    } catch (ParseException e) {
	        // El criterio no es una fecha válida
	    	System.out.println("El criterio no es una fecha válida");
	    	
	    }    
	    
	    // Finaliza la transacción si está activa
	    if (this.em.getTransaction().isActive()) {
	    	this.em.flush();
	        this.em.getTransaction().commit();
	    }
		return resultadosHuespedes;
	}
	
	/**
     * Actualiza los datos de un huésped existente en la base de datos.
     *
     * @param numeroHuesped      El número identificador del huésped a actualizar.
     * @param nuevoNombre         El nuevo nombre del huésped.
     * @param nuevoApellido       El nuevo apellido del huésped.
     * @param nuevaFechaNacimiento La nueva fecha de nacimiento del huésped.
     * @param nuevaNacionalidad   La nueva nacionalidad del huésped.
     * @param nuevoTelefono       El nuevo número de teléfono del huésped.
     */
	@Transactional
	public void actualizarHuesped(Long numeroHuesped, String nuevoNombre, String nuevoApellido,
	        Date nuevaFechaNacimiento, String nuevaNacionalidad, String nuevoTelefono) {

	    Huespedes huesped = obtenerHuespedPorNumero(numeroHuesped);
	    
	    if (huesped != null) {
	        // Actualizar los campos del huésped con los nuevos valores.
	        huesped.setNombre(nuevoNombre);
	        huesped.setApellido(nuevoApellido);
	        huesped.setFechaDeNacimiento(nuevaFechaNacimiento);
	        huesped.setNacionalidad(nuevaNacionalidad);
	        huesped.setTelefono(nuevoTelefono);

	        // Guardar el huésped actualizado en la base de datos.
	        actualizarHuespedTransaction(huesped);
	    } else {
	        // Manejar el caso en el que el huésped no se encontró en la base de datos.
	        System.out.println("El huésped con número " + numeroHuesped + " no se encontró.");
	    }
	}
	
	/**
	 * Busca y devuelve un huésped por su número de identificación en la base de datos.
	 *
	 * @param numeroHuesped El número de identificación del huésped a buscar.
	 * @return El huésped encontrado o null si no se encuentra ningún huésped con ese número.
	 */
	@Transactional
	private Huespedes obtenerHuespedPorNumero(Long numeroHuesped) {
		return em.find(Huespedes.class, numeroHuesped);
	}
	
	/**
	 * Actualiza un huésped en la base de datos utilizando una transacción.
	 *
	 * @param huesped El huésped a actualizar en la base de datos.
	 */
	@Transactional
	private void actualizarHuespedTransaction(Huespedes huesped) {
		this.em.getTransaction().begin();
		this.em.merge(huesped);
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
	/**
     * Busca un huésped por su número de identificación en la base de datos.
     *
     * @param idHuesped El número identificador del huésped.
     * @return El huésped encontrado o null si no se encontró ningún huésped con ese número.
     */
	@Transactional
	public Huespedes buscarHuespedPorID(Long idHuesped) {
        TypedQuery<Huespedes> query = em.createQuery("SELECT h FROM Huespedes h WHERE h.id = :idHuesped", Huespedes.class);
        query.setParameter("idHuesped", idHuesped);

        List<Huespedes> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            // Si se encontró una reserva con el número de reserva especificado, la retornamos
            return resultados.get(0);
        } else {
            // Si no se encontró ninguna reserva, retornamos null
            return null;
        }
    }
	
	/**
     * Elimina un huésped de la base de datos por su número de identificación.
     *
     * @param idHuesped El número identificador del huésped a eliminar.
     */
	@Transactional
	public void eliminarHuespedPorId(Long idHuesped) {
		try {
	        this.em.getTransaction().begin();;
	        Huespedes huesped = buscarHuespedPorID(idHuesped);
	        this.em.remove(huesped);// O session.remove(objeto) en Hibernate 5
	        this.em.flush();
			this.em.getTransaction().commit();
	    } catch (Exception e) {
	        if (this.em.getTransaction() != null) {
	        	this.em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    } finally {
	    	
	    }
		
	}
}

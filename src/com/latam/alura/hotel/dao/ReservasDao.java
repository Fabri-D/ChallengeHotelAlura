package com.latam.alura.hotel.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.latam.alura.hotel.modelo.Huespedes;
import com.latam.alura.hotel.modelo.Reservas;

/**
 * Esta clase proporciona métodos para acceder y gestionar reservas en la base de datos.
 */
public class ReservasDao {
	
	private EntityManager em;
	
	/**
     * Constructor de la clase ReservasDao.
     *
     * @param em El EntityManager utilizado para interactuar con la base de datos.
     */
	public ReservasDao(EntityManager em) {
		this.em = em;
	}
	
	/**
     * Guarda una reserva en la base de datos.
     *
     * @param reserva La reserva que se va a guardar.
     */
	@Transactional
	public void guardarReserva(Reservas reserva) {
		this.em.getTransaction().begin();
		this.em.persist(reserva);
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
	/**
	 * Realiza una búsqueda en la base de datos de reservas que coincidan con un criterio de búsqueda. El criterio de búsqueda
	 * puede ser un valor numérico, una fecha en formato "dd/MM/yyyy", una cadena de texto para buscar por forma de pago.
	 *
	 * @param criterioBusqueda El criterio de búsqueda utilizado para encontrar reservas.
	 * @return Una lista de objetos Reservas que coinciden con el criterio de búsqueda, incluyendo resultados de valor de reserva,
	 * forma de pago, fechas de entrada o salida y resultados que coinciden con un ID de reserva.
	 */
	@Transactional
	public List<Reservas> buscarEnDB(String criterioBusqueda) {
		if (!this.em.getTransaction().isActive()) {
			this.em.getTransaction().begin();
		}

		// Lista que almacenará los resultados de la búsqueda
		List<Reservas> resultados = new ArrayList<>();

		// Intenta convertir el criterio a un valor BigDecimal para buscar por valor de reserva
		BigDecimal valorBusqueda = null;
		try {
			valorBusqueda = new BigDecimal(criterioBusqueda);
		} catch (NumberFormatException e) {
			// El criterio no es un BigDecimal válido, lo dejamos como null
		}

		// Consulta para buscar Reservas por valor de reserva
		List<Reservas> resultadosReservasPorValor = new ArrayList<>();
		if (valorBusqueda != null) {
			String queryStringReservasPorValor = "FROM Reservas WHERE valor = :valorBusqueda";
			resultadosReservasPorValor = em.createQuery(queryStringReservasPorValor, Reservas.class)
					.setParameter("valorBusqueda", valorBusqueda) // Utiliza el valor convertido a BigDecimal
					.getResultList();
		}    
		// Consulta para buscar Reservas por forma de pago
		String queryStringReservasPorFormaPago = "FROM Reservas WHERE formaPago LIKE :criterioFormaPago";
		List<Reservas> resultadosReservasPorFormaPago = em.createQuery(queryStringReservasPorFormaPago, Reservas.class)
				.setParameter("criterioFormaPago", "%" + criterioBusqueda + "%")
				.getResultList();

		// Agregar los resultados de Reservas por valor y forma de pago
		resultados.addAll(resultadosReservasPorValor);
		resultados.addAll(resultadosReservasPorFormaPago);

		// Consulta para buscar Reservas por fecha de entrada o fecha de salida
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha esperado
		try {
			Date fechaBusqueda = dateFormat.parse(criterioBusqueda); // Intenta parsear el criterio a fecha

			// Consulta para buscar Reservas por fecha de entrada
			String queryStringFechaEntrada = "FROM Reservas WHERE fecha_entrada = :fechaBusqueda";
			List<Reservas> resultadosFechaEntrada = em.createQuery(queryStringFechaEntrada, Reservas.class)
					.setParameter("fechaBusqueda", fechaBusqueda)
					.getResultList();

			// Consulta para buscar Reservas por fecha de salida
			String queryStringFechaSalida = "FROM Reservas WHERE fecha_salida = :fechaBusqueda";
			List<Reservas> resultadosFechaSalida = em.createQuery(queryStringFechaSalida, Reservas.class)
					.setParameter("fechaBusqueda", fechaBusqueda)
					.getResultList();
			System.out.println("resultys fecha salida= " + resultadosFechaSalida.toString());

			// Agregar los resultados de Reservas por fechas de entrada y salida a la lista de resultados
			resultados.addAll(resultadosFechaEntrada);
			resultados.addAll(resultadosFechaSalida);
		} catch (ParseException e) {
			// El criterio no es una fecha válida, continúa con la búsqueda de otros criterios
		}

		// Intenta buscar Reservas por ID de Reservas
		Long idReservas = null;
		try {
			idReservas = Long.parseLong(criterioBusqueda);
		} catch (NumberFormatException e) {
			// El criterio no es un Long válido, lo dejamos como null
		}

		if (idReservas != null) {
			String queryStringReservasPorId = "FROM Reservas WHERE id = :idReservas";
			List<Reservas> resultadosReservasPorId = em.createQuery(queryStringReservasPorId, Reservas.class)
					.setParameter("idReservas", idReservas)
					.getResultList();
			
			// Agregar los resultados de Reservas por ID de Reservas a la lista de resultados
			resultados.addAll(resultadosReservasPorId);
		}
		
		// Finaliza la transacción si está activa
		if (this.em.getTransaction().isActive()) {
			this.em.flush();
			this.em.getTransaction().commit();
		}

		return resultados;
	}

	/**
	 * Actualiza una reserva en la base de datos.
     *
     * @param numeroReserva    El número de la reserva que se va a actualizar.
     * @param nuevaFechaCheckIn La nueva fecha de check-in de la reserva.
     * @param nuevaFechaCheckOut La nueva fecha de check-out de la reserva.
     * @param nuevoValor        El nuevo valor de la reserva.
     * @param nuevaFormaPago    La nueva forma de pago de la reserva.
     */
	@Transactional
	public void actualizarReserva(Long numeroReserva, Date nuevaFechaCheckIn, Date nuevaFechaCheckOut, BigDecimal nuevoValor,
			String nuevaFormaPago) {
		
		Reservas reserva = obtenerReservaPorNumero(numeroReserva);
		if (reserva != null) {
	        // 2. Actualizar los campos de la reserva con los nuevos valores.
	        reserva.setFecha_entrada(nuevaFechaCheckIn);
	        reserva.setFecha_salida(nuevaFechaCheckOut);
	        reserva.setValor(nuevoValor);
	        reserva.setFormaPago(nuevaFormaPago);

	        // 3. Guardar la reserva actualizada en la base de datos.
	        actualizarReservaTransaction(reserva);
	    } else {
	        // Manejar el caso en el que la reserva no se encontró en la base de datos.
	        System.out.println("La reserva con número " + numeroReserva + " no se encontró.");
	    }
	}
	
	/**
	 * Actualiza una reserva en la base de datos como parte de una transacción.
	 *
	 * @param reserva La reserva que se va a actualizar.
	 */
	@Transactional
	private void actualizarReservaTransaction(Reservas reserva) {
		this.em.getTransaction().begin();
		this.em.merge(reserva);
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
	/**
     * Obtiene una reserva por su número de reserva.
     *
     * @param numeroReserva El número de reserva de la reserva a buscar.
     * @return La reserva encontrada o null si no se encuentra ninguna reserva con ese número.
     */
	@Transactional
	private Reservas obtenerReservaPorNumero(Long numeroReserva) {
		return em.find(Reservas.class, numeroReserva);
	}
	
	/**
     * Busca una reserva por su número de reserva.
     *
     * @param numeroReserva El número de reserva de la reserva a buscar.
     * @return La reserva encontrada o null si no se encuentra ninguna reserva con ese número.
     */
	@Transactional
	public Reservas buscarReservaPorID(Long numeroReserva) {
        TypedQuery<Reservas> query = em.createQuery("SELECT r FROM Reservas r WHERE r.id = :numeroReserva", Reservas.class);
        query.setParameter("numeroReserva", numeroReserva);

        List<Reservas> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            // Si se encontró una reserva con el número de reserva especificado, la retornamos
            return resultados.get(0);
        } else {
            // Si no se encontró ninguna reserva, retornamos null
            return null;
        }
    }
	
	/**
     * Busca la fecha de entrada de una reserva por su número de reserva.
     *
     * @param numeroReserva El número de reserva de la reserva cuya fecha de entrada se busca.
     * @return La fecha de entrada encontrada o null si no se encuentra ninguna fecha de entrada para esa reserva.
     */
	@Transactional
	public Date buscarFechaEntradaPorId(Long numeroReserva) {
        TypedQuery<Date> query = em.createQuery("SELECT r.fechaEntrada FROM Reservas r WHERE r.numeroReserva = :numeroReserva", Date.class);
        query.setParameter("numeroReserva", numeroReserva);

        List<Date> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            // Si se encontró una fecha de entrada para la reserva especificada, la retornamos
            return resultados.get(0);
        } else {
            // Si no se encontró ninguna fecha de entrada, retornamos null
            return null;
        }
    }
	
	/**
     * Actualiza la fecha de entrada de una reserva en la base de datos.
     *
     * @param nuevaFechaEntrada La nueva fecha de entrada de la reserva.
     * @param numeroReserva     El número de reserva de la reserva que se va a actualizar.
     * @return La fecha de entrada actualizada o null si la reserva no se encuentra.
     */
	@Transactional
	public Date actualizarFechaEntrada(Date nuevaFechaEntrada, Long numeroReserva) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Aquí asumimos que tienes un método para obtener la reserva por ID
            Reservas reserva = buscarReservaPorID(numeroReserva);

            if (reserva != null) {
                reserva.setFecha_entrada(nuevaFechaEntrada);
                em.merge(reserva); // Actualiza la reserva en la base de datos
                transaction.commit();
                return reserva.getFecha_entrada();
            } else {
                // La reserva no se encontró, puedes manejar este caso como desees (lanzar una excepción, retornar -1, etc.)
                transaction.rollback();
                return null; // Retorna un valor que indique que la reserva no se encontró
            }
        } catch (Exception e) {
            // Manejo de excepciones en caso de error durante la actualización
            transaction.rollback();
            System.out.println("No se pudo actualizar la fecha de entrada"); 
            return null; // Retorna un valor que indique un error
        }
    }
	
	/**
     * Parsea una cadena de fecha en el formato especificado.
     *
     * @param cadenaFecha La cadena de fecha a parsear.
     * @return La fecha parseada o null si la cadena no se puede parsear.
     */
	public Date parseoFechaJTable(String cadenaFecha) {
		String formatoFecha = "dd/MM/yyyy";
        // Crear un objeto SimpleDateFormat con el formato deseado
        DateFormat formato = new SimpleDateFormat(formatoFecha);
        Date fechaEntradaFinal = null;
          
        try {
            fechaEntradaFinal = formato.parse(cadenaFecha);
        } catch (ParseException e) {
            System.out.println("No se pudo realizar el parseo de fecha.");;
        }
        
		return fechaEntradaFinal;
	}
	
	/**
     * Elimina una reserva de la base de datos por su ID de reserva.
     *
     * @param idReserva El ID de reserva de la reserva que se va a eliminar.
     */
	@Transactional
	public void eliminarReservaPorId(Long idReserva) {
		try {
	        this.em.getTransaction().begin();;
	        Reservas reserva = buscarReservaPorID(idReserva);
	        Huespedes huesped = reserva.getHuesped();
	        huesped.getReservas().remove(reserva);
	        em.merge(huesped);
	        this.em.remove(reserva);// O session.remove(objeto) en Hibernate 5
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

package com.latam.alura.hotel.views;

import com.latam.alura.hotel.dao.HuespedesDao;
import com.latam.alura.hotel.modelo.Huespedes;
import com.latam.alura.hotel.modelo.Reservas;
import com.latam.alura.hotel.utils.JPAUtils;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;


import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

/**
 * Clase que representa la ventana de registro de huéspedes en una aplicación de gestión de reservas de hotel.
 */
@SuppressWarnings("serial")
public class RegistroHuesped extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtNreserva;
	private JDateChooser txtFechaN;
	private JComboBox<Format> txtNacionalidad;
	private JLabel labelExit;
	private JLabel labelAtras;
	int xMouse, yMouse;
	EntityManager em = JPAUtils.getEntityManager();
	HuespedesDao huespedesDao = new HuespedesDao(em);
	public static long idResrv;
	
	
	
	/**
     * Método principal que inicia la aplicación.
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroHuesped frame = new RegistroHuesped();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Constructor de la clase que configura la interfaz de usuario y agrega componentes.
     */
	public RegistroHuesped() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroHuesped.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 634);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		
		ReservasView.entroEnRegHuesped=true;
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReservasView reservas = new ReservasView();
				reservas.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(new Color(12, 138, 199));
			     labelAtras.setForeground(Color.white);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(new Color(12, 138, 199));
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setForeground(Color.WHITE);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNombre.setBounds(560, 135, 285, 33);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setColumns(10);
		txtNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtNombre);
		
		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtApellido.setBounds(560, 204, 285, 33);
		txtApellido.setColumns(10);
		txtApellido.setBackground(Color.WHITE);
		txtApellido.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtApellido);
		
		txtFechaN = new JDateChooser();
		txtFechaN.setBounds(560, 278, 285, 36);
		txtFechaN.getCalendarButton().setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaN.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaN.setDateFormatString("dd-MM-yyyy");
		contentPane.add(txtFechaN);
						
		txtNacionalidad = new JComboBox();
		txtNacionalidad.setBounds(560, 350, 289, 36);
		txtNacionalidad.setBackground(SystemColor.text);
		txtNacionalidad.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNacionalidad.setModel(new DefaultComboBoxModel(new String[] {"afgano-afgana", "alemán-", "alemana", "árabe-árabe", "argentino-argentina", "australiano-australiana", "belga-belga", "boliviano-boliviana", "brasileño-brasileña", "camboyano-camboyana", "canadiense-canadiense", "chileno-chilena", "chino-china", "colombiano-colombiana", "coreano-coreana", "costarricense-costarricense", "cubano-cubana", "danés-danesa", "ecuatoriano-ecuatoriana", "egipcio-egipcia", "salvadoreño-salvadoreña", "escocés-escocesa", "español-española", "estadounidense-estadounidense", "estonio-estonia", "etiope-etiope", "filipino-filipina", "finlandés-finlandesa", "francés-francesa", "galés-galesa", "griego-griega", "guatemalteco-guatemalteca", "haitiano-haitiana", "holandés-holandesa", "hondureño-hondureña", "indonés-indonesa", "inglés-inglesa", "iraquí-iraquí", "iraní-iraní", "irlandés-irlandesa", "israelí-israelí", "italiano-italiana", "japonés-japonesa", "jordano-jordana", "laosiano-laosiana", "letón-letona", "letonés-letonesa", "malayo-malaya", "marroquí-marroquí", "mexicano-mexicana", "nicaragüense-nicaragüense", "noruego-noruega", "neozelandés-neozelandesa", "panameño-panameña", "paraguayo-paraguaya", "peruano-peruana", "polaco-polaca", "portugués-portuguesa", "puertorriqueño-puertorriqueño", "dominicano-dominicana", "rumano-rumana", "ruso-rusa", "sueco-sueca", "suizo-suiza", "tailandés-tailandesa", "taiwanes-taiwanesa", "turco-turca", "ucraniano-ucraniana", "uruguayo-uruguaya", "venezolano-venezolana", "vietnamita-vietnamita"}));
		contentPane.add(txtNacionalidad);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(562, 119, 253, 14);
		lblNombre.setForeground(SystemColor.textInactiveText);
		lblNombre.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBounds(560, 189, 255, 14);
		lblApellido.setForeground(SystemColor.textInactiveText);
		lblApellido.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblApellido);
		
		JLabel lblFechaN = new JLabel("FECHA DE NACIMIENTO");
		lblFechaN.setBounds(560, 256, 255, 14);
		lblFechaN.setForeground(SystemColor.textInactiveText);
		lblFechaN.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblFechaN);
		
		JLabel lblNacionalidad = new JLabel("NACIONALIDAD");
		lblNacionalidad.setBounds(560, 326, 255, 14);
		lblNacionalidad.setForeground(SystemColor.textInactiveText);
		lblNacionalidad.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNacionalidad);
		
		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(562, 406, 253, 14);
		lblTelefono.setForeground(SystemColor.textInactiveText);
		lblTelefono.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtTelefono.setBounds(560, 424, 285, 33);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtTelefono);
		
		JLabel lblTitulo = new JLabel("REGISTRO HUÉSPED");
		lblTitulo.setBounds(606, 55, 234, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 22));
		contentPane.add(lblTitulo);
		
		JLabel lblNumeroReserva = new JLabel("NÚMERO DE RESERVA");
		lblNumeroReserva.setBounds(560, 474, 253, 14);
		lblNumeroReserva.setForeground(SystemColor.textInactiveText);
		lblNumeroReserva.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNumeroReserva);
		
		txtNreserva = new JTextField();
		txtNreserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNreserva.setBounds(560, 495, 285, 33);
		txtNreserva.setColumns(10);
		txtNreserva.setBackground(Color.WHITE);
		txtNreserva.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtNreserva);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(560, 170, 289, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);
		
		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(560, 240, 289, 2);
		separator_1_2_1.setForeground(new Color(12, 138, 199));
		separator_1_2_1.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_1);
		
		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(560, 314, 289, 2);
		separator_1_2_2.setForeground(new Color(12, 138, 199));
		separator_1_2_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_2);
		
		JSeparator separator_1_2_3 = new JSeparator();
		separator_1_2_3.setBounds(560, 386, 289, 2);
		separator_1_2_3.setForeground(new Color(12, 138, 199));
		separator_1_2_3.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_3);
		
		JSeparator separator_1_2_4 = new JSeparator();
		separator_1_2_4.setBounds(560, 457, 289, 2);
		separator_1_2_4.setForeground(new Color(12, 138, 199));
		separator_1_2_4.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_4);
		
		JSeparator separator_1_2_5 = new JSeparator();
		separator_1_2_5.setBounds(560, 529, 289, 2);
		separator_1_2_5.setForeground(new Color(12, 138, 199));
		separator_1_2_5.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_5);
		
		JPanel btnguardar = new JPanel();
		btnguardar.setBounds(723, 560, 122, 35);
		btnguardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!txtNombre.getText().equals("")
						&& !txtApellido.getText().equals("") &&
						!txtNreserva.getText().equals("") && txtFechaN.getDate()!=null
						&& txtNacionalidad.getSelectedItem()!=null
						&& !txtTelefono.getText().equals("")){
					guardarHuespedJpa();
				}
				else {
					JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
				}
			}
		});
		
		btnguardar.setLayout(null);
		btnguardar.setBackground(new Color(12, 138, 199));
		contentPane.add(btnguardar);
		btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		JLabel labelGuardar = new JLabel("GUARDAR");
		labelGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		labelGuardar.setForeground(Color.WHITE);
		labelGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelGuardar.setBounds(0, 0, 122, 35);
		btnguardar.add(labelGuardar);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 489, 634);
		panel.setBackground(new Color(12, 138, 199));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 121, 479, 502);
		panel.add(imagenFondo);
		imagenFondo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/registro.png")));
		
		JLabel logo = new JLabel("");
		logo.setBounds(194, 39, 104, 107);
		panel.add(logo);
		logo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/Ha-100px.png")));
		
		JPanel btnexit = new JPanel();
		btnexit.setBounds(857, 0, 53, 36);
		contentPane.add(btnexit);
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.white);
		
		labelExit = new JLabel("X");
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(SystemColor.black);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
	}
	
	
	
	
	/**
	 * Método que maneja el evento de presionar el mouse en la barra de título de la ventana.
	 *
	 * @param evt Evento de mouse
	 */	
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}
	
	/**
     * Método que maneja el evento de arrastrar el mouse en la barra de título de la ventana.
     *
     * @param evt Evento de mouse
     */
	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtNreserva() {
		return txtNreserva;
	}

	public void setTxtNreserva(JTextField txtNreserva) {
		this.txtNreserva = txtNreserva;
	}

	public JDateChooser getTxtFechaN() {
		return txtFechaN;
	}

	public void setTxtFechaN(JDateChooser txtFechaN) {
		this.txtFechaN = txtFechaN;
	}

	public JComboBox<Format> getTxtNacionalidad() {
		return txtNacionalidad;
	}

	public void setTxtNacionalidad(JComboBox<Format> txtNacionalidad) {
		this.txtNacionalidad = txtNacionalidad;
	}
	
	/**
	 * Método para guardar la información de un huésped en la base de datos.
	 * Se encarga de tomar los datos del formulario de registro, verificar si el huésped
	 * ya existe en la base de datos y, en función de la existencia o no, realizar
	 * las siguientes acciones:
	 * - Si el huésped ya existe, asocia la reserva especificada al huésped existente.
	 * - Si el huésped no existe, crea un nuevo huésped y asocia la reserva a este nuevo huésped.
	 * Además, maneja posibles errores y muestra mensajes de confirmación o error en la interfaz gráfica.
	 */
	private void guardarHuespedJpa() {
		Date fechaDeNacimiento = txtFechaN.getDate();
		Date fechaSinHora = null;
		
		// Verifica que la fecha no sea nula
		if (fechaDeNacimiento != null) {
			// Crea un objeto Calendar
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaDeNacimiento);

			// Establece la hora, minutos, segundos y milisegundos a cero
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			// Obtiene la fecha sin hora
			fechaSinHora = cal.getTime();
			// Obtener los valores de los componentes de la interfaz
		}
		
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String nacionalidad = txtNacionalidad.getSelectedItem().toString();
		String telefono = txtTelefono.getText();
		Long nroReserva = Long.valueOf(txtNreserva.getText());

		// Buscar si ya existe un huésped con los mismos datos
		Huespedes huespedExistente = huespedesDao.buscarHuespedPorDatos(nombre, apellido, fechaSinHora, nacionalidad, telefono);

		if (huespedExistente != null) {
			// El huésped ya existe, agregamos la reserva al huésped existente
			String jpql = "SELECT r FROM Reservas r WHERE id = " + nroReserva;
			TypedQuery<Reservas> query = em.createQuery(jpql, Reservas.class);
			List<Reservas> reservas = query.getResultList();

			if (!reservas.isEmpty()) {
				// Suponemos que solo hay una reserva con ese ID
				Reservas reservaFinal = reservas.get(0); // Suponemos que solo hay una reserva con ese ID
				huespedExistente.addReservas(reservaFinal);
				reservaFinal.setHuesped(huespedExistente);
				try {
					// Intentar asociar la reserva al huésped existente
					huespedesDao.agregarReservaAHuesped(huespedExistente, reservaFinal);
					// Mostrar un mensaje de confirmación
					JOptionPane.showMessageDialog(this, "El huésped ya estaba registrado. La reserva se ha asociado a su perfil existente.");
				} catch (Exception e) {
					// Mostrar un mensaje de error en caso de fallo
					JOptionPane.showMessageDialog(this, "Error al asociar la reserva al huésped: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// Mostrar un mensaje de error si no se encontró una reserva con el número especificado
				JOptionPane.showMessageDialog(this, "No se encontró una reserva con el número especificado.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// El huésped no existe, creamos un nuevo huésped y agregamos la reserva
			
			Huespedes nuevoHuesped = new Huespedes();
			nuevoHuesped.setNombre(nombre);
			nuevoHuesped.setApellido(apellido);
			nuevoHuesped.setFechaDeNacimiento(fechaSinHora);
			nuevoHuesped.setNacionalidad(nacionalidad);
			nuevoHuesped.setTelefono(telefono);

			String jpql = "SELECT r FROM Reservas r WHERE id = " + nroReserva;
			TypedQuery<Reservas> query = em.createQuery(jpql, Reservas.class);
			List<Reservas> reservas = query.getResultList();

			if (!reservas.isEmpty()) {
				// Suponemos que solo hay una reserva con ese ID
				Reservas reservaFinal = reservas.get(0); // Suponemos que solo hay una reserva con ese ID
				nuevoHuesped.addReservas(reservaFinal);
				reservaFinal.setHuesped(nuevoHuesped);
				
				try {
					// Intentar guardar el nuevo huésped y asociar la reserva
					huespedesDao.guardarHuesped(nuevoHuesped);
					// Mostrar un mensaje de confirmación
					JOptionPane.showMessageDialog(this, "Huésped creado y reserva asociada con éxito.");
				} catch (Exception e) {
					// Mostrar un mensaje de error en caso de fallo
					JOptionPane.showMessageDialog(this, "Error al crear el huésped y asociar la reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// Mostrar un mensaje de error si no se encontró una reserva con el número especificado
				JOptionPane.showMessageDialog(this, "No se encontró una reserva con el número especificado.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}        
	        
	    
											


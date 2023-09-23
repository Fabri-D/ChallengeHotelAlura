package com.latam.alura.hotel.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import javax.swing.JTextField;

import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.modelo.Reservas;
import com.latam.alura.hotel.utils.JPAUtils;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JComboBox;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.DefaultComboBoxModel;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Clase principal que representa la ventana de reservas en la interfaz de usuario.
 * Permite a los usuarios ingresar información sobre la reserva, como fechas, forma de pago y valor.
 */
@SuppressWarnings("serial")
public class ReservasView extends JFrame {

	private JPanel contentPane;
	public static JTextField txtValor;
	public static JDateChooser txtFechaEntrada;
	public static JDateChooser txtFechaSalida;
	public static JComboBox<String> txtFormaPago;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel labelAtras;
	EntityManager em = JPAUtils.getEntityManager();
	private ReservasDao reservasDao= new ReservasDao(em);
	
	/**
     * Variables que indican si se ha realizado una reserva o si el usuario ingresó en la ventana de registro de huéspedes.
     */
	public static boolean reservaHecha;
	public static boolean entroEnRegHuesped;
	
	

	/**
     * Método principal para iniciar la aplicación de reservas.
     * @param args Los argumentos de la línea de comandos.
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservasView frame = new ReservasView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Constructor de la clase ReservasView.
     * Configura y muestra la ventana de reservas.
     */
	public ReservasView() {
		// Configuración inicial de la ventana
		
		super("Reserva");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReservasView.class.getResource("/imagenes/aH-40px.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 560);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		// Inicialización de variables
		reservaHecha=false;
		if (entroEnRegHuesped) {
			reservaHecha=true;
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 910, 560);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// Código que crea los elementos de la interfáz gráfica
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(SystemColor.textHighlight);
		separator_1_2.setBounds(68, 195, 289, 2);
		separator_1_2.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_2);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(SystemColor.textHighlight);
		separator_1_3.setBackground(SystemColor.textHighlight);
		separator_1_3.setBounds(68, 453, 289, 2);
		panel.add(separator_1_3);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1.setBounds(68, 281, 289, 11);
		separator_1_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_1);
		
		JLabel lblCheckIn = new JLabel("FECHA DE CHECK IN");
		lblCheckIn.setForeground(SystemColor.textInactiveText);
		lblCheckIn.setBounds(68, 136, 169, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 17));
		panel.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("FECHA DE CHECK OUT");
		lblCheckOut.setForeground(SystemColor.textInactiveText);
		lblCheckOut.setBounds(68, 221, 187, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 17));
		panel.add(lblCheckOut);
		
		JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 382, 187, 24);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblFormaPago);
		
		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(109, 60, 219, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 18));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(428, 0, 482, 560);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setBounds(197, 68, 104, 107);
		panel_1.add(logo);
		logo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/Ha-100px.png")));
		
		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 140, 500, 409);
		panel_1.add(imagenFondo);
		imagenFondo.setBackground(Color.WHITE);
		imagenFondo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/reservas-img-3.png")));
		
		JLabel lblValor = new JLabel("VALOR DE LA RESERVA");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(72, 303, 196, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 17));
		panel.add(lblValor);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBounds(68, 362, 289, 2);
		separator_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1);
												
		// Componentes para dejar la interfaz con estilo Material Design
		JPanel btnexit = new JPanel();
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
				 btnexit.setBackground(new Color(12, 138, 199));
			     labelExit.setForeground(Color.white);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setBounds(429, 0, 53, 36);
		panel_1.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		
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
		header.setBackground(Color.WHITE);
		panel.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (reservaHecha) {
			        Exito exito = new Exito();
			        exito.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosed(WindowEvent e) {
			                // Esto se ejecutará cuando el diálogo de éxito se cierre
			                MenuUsuario usuario = new MenuUsuario();
			                usuario.setVisible(true);
			                dispose();
			                //frame.setVisible(false);
			            }
			        });
			        exito.setVisible(true);
			        reservaHecha=false;
			        entroEnRegHuesped=false;
			    } else {
			        // Si no se hizo la reserva, simplemente abre MenuUsuario
			        MenuUsuario usuario = new MenuUsuario();
			        usuario.setVisible(true);
			        dispose();
			    }
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		
		JLabel lblSiguiente = new JLabel("SIGUIENTE");
		lblSiguiente.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguiente.setForeground(Color.WHITE);
		lblSiguiente.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblSiguiente.setBounds(0, 0, 122, 35);
		
		
		//Campos que guardaremos en la base de datos
		txtFechaEntrada = new JDateChooser();
		txtFechaEntrada.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaEntrada.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaEntrada.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFechaEntrada.setBounds(68, 161, 289, 35);
		txtFechaEntrada.getCalendarButton().setBounds(268, 0, 21, 33);
		txtFechaEntrada.setBackground(Color.WHITE);
		txtFechaEntrada.setBorder(new LineBorder(SystemColor.window));
		txtFechaEntrada.setDateFormatString("dd-MM-yyyy");
		txtFechaEntrada.setFont(new Font("Roboto", Font.PLAIN, 18));
		panel.add(txtFechaEntrada);

		txtFechaSalida = new JDateChooser();
		txtFechaSalida.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaSalida.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtFechaSalida.setBounds(68, 246, 289, 35);
		txtFechaSalida.getCalendarButton().setBounds(267, 1, 21, 31);
		txtFechaSalida.setDateFormatString("dd-MM-yyyy");
		txtFechaSalida.setBackground(Color.WHITE);
		txtFechaSalida.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		txtFechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//primero validamos que se haya ingresado una fecha de entrada antes
				//de hacer el cálculo y que la fecha de salida no sea anterior a la
				//fecha de entrada.
				Date fechaEntrada = null;
				Date fechaSalida = txtFechaSalida.getDate();
				String fechaString = "16/09/2073";
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					fechaEntrada = dateFormat.parse(fechaString);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if ("date".equals(evt.getPropertyName())) {
					
	
					try {
						if (fechaEntrada!=null && fechaEntrada!=dateFormat.parse(fechaString)){
							fechaEntrada = txtFechaEntrada.getDate();
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} 
				try {
					if (fechaEntrada== dateFormat.parse(fechaString) || fechaEntrada==null) {
						JOptionPane.showMessageDialog(null, "Debe primero ingresar la fecha de entrada y luego la de salida");
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (fechaEntrada != null && fechaSalida != null) {
					// Calcular la diferencia en días entre las fechas
					long diferenciaEnMilisegundos = fechaSalida.getTime() - fechaEntrada.getTime();
					// Convertir la diferencia en días (1 día = 24 horas x 60 minutos x 60 segundos x 1000 milisegundos)
					int diferenciaEnDias = (int) (diferenciaEnMilisegundos / (24 * 60 * 60 * 1000));
					if (diferenciaEnDias<0) {
						JOptionPane.showMessageDialog(null, "La fecha de salida no puede ser anterior a la fecha de entrada");
					}
					else {
						// Calcular el valor basado en la tarifa por día (100 en este caso)
						BigDecimal tarifaPorDia = BigDecimal.valueOf(100.0);
						BigDecimal valorTotal = tarifaPorDia.multiply(BigDecimal.valueOf(diferenciaEnDias));

						// Mostrar el valor calculado en txtValor
						String valorFormateado = valorTotal.toPlainString(); // Esto convierte el BigDecimal a una cadena sin formato
						try {
						    txtValor.setText(valorFormateado);
						} catch (NullPointerException e) {
						    // Manejar la excepción aquí
						}
					}

				}
			}
		}
				);
		
		txtFechaSalida.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaSalida.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtFechaSalida);
		
		txtValor = new JTextField();
		txtValor.setBackground(SystemColor.text);
		txtValor.setHorizontalAlignment(SwingConstants.CENTER);
		txtValor.setForeground(Color.BLACK);
		txtValor.setBounds(70, 328, 73, 33);
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Roboto Black", Font.BOLD, 17));
		txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(txtValor);
		txtValor.setColumns(10);


		txtFormaPago = new JComboBox();
		txtFormaPago.setBounds(68, 417, 289, 38);
		txtFormaPago.setBackground(SystemColor.text);
		txtFormaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		txtFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFormaPago.setModel(new DefaultComboBoxModel(new String[] {"Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo"}));
		panel.add(txtFormaPago);

		JButton btnsiguiente = new JButton();
		btnsiguiente.setText("Siguiente");
		btnsiguiente.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		
		btnsiguiente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ReservasView.txtFechaEntrada.getDate() != null && ReservasView.txtFechaSalida.getDate() != null) {		
					RegistroHuesped registro = new RegistroHuesped();
					guardarReserva();
					registro.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
				}
			}						
		});
		
		btnsiguiente.setLayout(null);
		btnsiguiente.setBackground(SystemColor.textHighlight);
		btnsiguiente.setBounds(238, 493, 122, 35);
		panel.add(btnsiguiente);
		btnsiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	
	}

	/**
     * Método para manejar eventos del mouse al presionar el encabezado de la ventana.
     * Permite arrastrar la ventana por la pantalla.
     * @param evt El evento del mouse.
     */	
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}
	
	/**
     * Método para manejar eventos del mouse al arrastrar el encabezado de la ventana.
     * Permite mover la ventana por la pantalla.
     * @param evt El evento del mouse.
     */
	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	/**
	 * Método para guardar una reserva en la base de datos.
	 * Obtiene los valores de la interfaz de usuario y crea una instancia de Reservas.
	 * Luego, guarda la reserva en la base de datos y muestra mensajes de éxito o error.
	 */
	private void guardarReserva() {
		// Obtener los valores de los componentes de la interfaz
		BigDecimal valor = new BigDecimal(txtValor.getText());
		Date fechaEntrada = txtFechaEntrada.getDate();
		Date fechaSalida = txtFechaSalida.getDate();

		// Crear un objeto Calendar y establecer la fecha y hora de fechaSalida
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSalida);

		// Establecer la hora, minutos, segundos y milisegundos en 0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Obtener la fecha resultante
		fechaSalida = calendar.getTime();
		
		String formaPago = (String) txtFormaPago.getSelectedItem();

		// Crear una instancia de Reservas y establecer sus atributos
		Reservas reserva = new Reservas();
		reserva.setValor(valor);
		reserva.setFecha_entrada(fechaEntrada);
		reserva.setFecha_salida(fechaSalida);
		reserva.setFormaPago(formaPago);


		try {
			// Guardar la reserva en la base de datos
			reservasDao.guardarReserva(reserva);
			// Mostrar un mensaje de éxito
			JOptionPane.showMessageDialog(this, "Reserva guardada con éxito. Su numero de reserva es " + reserva.getId() );
			reservaHecha=true;
			
		} catch (Exception e) {
			// Mostrar un mensaje de error en caso de fallo
			JOptionPane.showMessageDialog(this, "Error al guardar la reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		em.close();

	}


}

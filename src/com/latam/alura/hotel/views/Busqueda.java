package com.latam.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.annotations.Any;

import com.latam.alura.hotel.dao.HuespedesDao;
import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.modelo.Huespedes;
import com.latam.alura.hotel.modelo.Reservas;
import com.latam.alura.hotel.utils.JPAUtils;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase principal que representa la ventana de búsqueda en la aplicación.
 */
@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	final String columnaNoEditable = "Número de Reserva";
	final String columnaNoEditable2 = "Número de Huésped";
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;	
	EntityManager em = JPAUtils.getEntityManager();
	HuespedesDao huespedesDao = new HuespedesDao(em);
	ReservasDao reservasDao = new ReservasDao(em);
	
	private DefaultTableModel modelo = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	        // Devuelve true para permitir la edición en todas las columnas excepto en la columna "Número de Reserva"
	    	return !getColumnName(column).equals(columnaNoEditable);
	    }
	};
	
	private DefaultTableModel modeloHuespedes = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	        // Devuelve true para permitir la edición en todas las columnas excepto en la columna "Número de Reserva"
	    	return !getColumnName(column).equals(columnaNoEditable2) && !getColumnName(column).equals(columnaNoEditable);
	    }
	};

	/**
     * Método principal que inicia la aplicación.
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Constructor de la clase Busqueda.
     * Inicializa la interfaz de usuario y configura los componentes.
     */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 23));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		tbReservas = new JTable(modelo);
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		modelo = (DefaultTableModel) tbReservas.getModel();
		
		modelo.addColumn("Número de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		tbHuespedes = new JTable(modeloHuespedes);
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huésped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
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
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
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
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JButton btnbuscar = new JButton();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pestañaSeleccionada = panel.getSelectedIndex();
				if (pestañaSeleccionada==0) {
					buscarReservas(panel);
				}
				if (pestañaSeleccionada==1) {
					buscarHuespedes(panel);
				}
			}
		});
		
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JButton btnEditar = new JButton();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		btnEditar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Obtener la fila seleccionada en la tabla
		        int filaSeleccionada = tbReservas.getSelectedRow();
		        if (filaSeleccionada == -1) {
		        	filaSeleccionada= tbHuespedes.getSelectedRow();
		        }
		        
		        if (filaSeleccionada != -1) { 
		        	
		        	int pestañaSeleccionada = panel.getSelectedIndex();
		        	if (pestañaSeleccionada==0) {
		        		editarReserva();
		        	}
		        	if (pestañaSeleccionada==1) {
		        		editarHuesped();
		        	}
		        	
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecciona una fila antes de hacer clic en Editar.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JButton btnEliminar = new JButton();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		btnEliminar.addActionListener((new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// Obtener la fila seleccionada en la tabla
		        int filaSeleccionada = tbReservas.getSelectedRow();
		        if (filaSeleccionada == -1) {
		        	filaSeleccionada= tbHuespedes.getSelectedRow();
		        }
		        
		        if (filaSeleccionada != -1) { 
		        	
		        	int pestañaSeleccionada = panel.getSelectedIndex();
		        	if (pestañaSeleccionada==0) {
		        		eliminarReserva();
		        	}
		        	if (pestañaSeleccionada==1) {
		        		eliminarHuesped();
		        	}
				}
		    }
		}));
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	/**
	 * Método para eliminar un huésped.
	 * Obtiene la fila seleccionada en la tabla y elimina la entrada correspondiente en la base de datos.
	 */
	protected void eliminarHuesped() {
	    int filaSeleccionada = tbHuespedes.getSelectedRow();
	    
	    if (filaSeleccionada >= 0) {
	    	Long idHuesped = Long.valueOf(modeloHuespedes.getValueAt(filaSeleccionada, 0).toString());
	        huespedesDao.eliminarHuespedPorId(idHuesped);
	        modeloHuespedes.removeRow(filaSeleccionada);
	        
	        JOptionPane.showMessageDialog(this, "Huésped eliminado exitosamente");
	    } else {
	        System.out.println("No se ha seleccionado ninguna fila válida para eliminar.");
	    }
	}
	
	/**
	 * Método para eliminar una reserva o un huésped según la pestaña seleccionada.
	 * Obtiene la fila seleccionada en la tabla y elimina la entrada correspondiente en la base de datos.
	 */
	protected void eliminarReserva() {
		int filaSeleccionada = tbReservas.getSelectedRow();
		if (filaSeleccionada >= 0) {
			Long idReserva = Long.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString());
			reservasDao.eliminarReservaPorId(idReserva);
			modelo.removeRow(filaSeleccionada);
			JOptionPane.showMessageDialog(this, "Reserva eliminada exitosamente");
		} else {
	        System.out.println("No se ha seleccionado ninguna fila para eliminar.");
	    }
	}
	
	/**
	 * Método para editar un huésped.
	 * Obtiene la fila seleccionada en la tabla y permite la edición de los datos del huésped.
	 */
	protected void editarHuesped() {
	    int filaSeleccionada = tbHuespedes.getSelectedRow();

	    if (filaSeleccionada != -1) {
	        Long numeroHuesped = (Long) modeloHuesped.getValueAt(filaSeleccionada, 0);
	        String nuevoNombre = (String) modeloHuesped.getValueAt(filaSeleccionada, 1);
	        String nuevoApellido = (String) modeloHuesped.getValueAt(filaSeleccionada, 2);
	        String nuevaFechaNacimiento = (String) modeloHuesped.getValueAt(filaSeleccionada, 3);
	        String nuevaNacionalidad = (String) modeloHuesped.getValueAt(filaSeleccionada, 4);
	        String nuevoTelefono = (String) modeloHuesped.getValueAt(filaSeleccionada, 5);
	        String nuevasReservas = (String) modeloHuesped.getValueAt(filaSeleccionada, 6);
	        
	        Date FechaNacFinal = reservasDao.parseoFechaJTable(nuevaFechaNacimiento);
	        
	        huespedesDao.actualizarHuesped(numeroHuesped, nuevoNombre, nuevoApellido, FechaNacFinal, nuevaNacionalidad, nuevoTelefono);
	       
	        JOptionPane.showMessageDialog(this, "Huésped actualizado exitosamente");
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona un huésped para editar");
	    }
	}
	
	/**
	 * Método para editar una reserva o un huésped según la pestaña seleccionada.
	 * Obtiene la fila seleccionada en la tabla y permite la edición de los datos.
	 */
	protected void editarReserva() {
		int filaSeleccionada = tbReservas.getSelectedRow();
		if (filaSeleccionada != -1) {
			Long numeroReserva = (Long) modelo.getValueAt(filaSeleccionada, 0);
			String cadenaFechaEntrada = modelo.getValueAt(filaSeleccionada, 1).toString();
			String cadenaFechaSalida = modelo.getValueAt(filaSeleccionada, 2).toString();
			
			Date fechaEntradaFinal = reservasDao.parseoFechaJTable(cadenaFechaEntrada);
			Date fechaSalidaFinal = reservasDao.parseoFechaJTable(cadenaFechaSalida);
	     
	        Date nuevaFechaCheckIn = fechaEntradaFinal;
	        Date nuevaFechaCheckOut = fechaSalidaFinal;
	        String newValueStr= modelo.getValueAt(filaSeleccionada, 3).toString();
	        BigDecimal nuevoValor = new BigDecimal(newValueStr);
	        String nuevaFormaPago = (String) modelo.getValueAt(filaSeleccionada, 4);
	        
	        reservasDao.actualizarReserva(numeroReserva, nuevaFechaCheckIn, nuevaFechaCheckOut, nuevoValor, nuevaFormaPago);
	        JOptionPane.showMessageDialog(this, "Reserva actualizada exitosamente");
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona una reserva para editar");
	    }
	}

	/**
	 * Método para buscar reservas o huéspedes según la pestaña seleccionada.
	 * Recoge el criterio de búsqueda del campo de texto y muestra los resultados en la tabla correspondiente.
	 */
	protected void buscarReservas(JTabbedPane panel) {
		String criterioBusqueda = txtBuscar.getText().trim();
		int pestañaSeleccionada = panel.getSelectedIndex();
		if (pestañaSeleccionada == 0) {
			List<Reservas> resultados = reservasDao.buscarEnDB(criterioBusqueda);
			
			DefaultTableModel modelo = (DefaultTableModel) tbReservas.getModel();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha
		
		    // Limpia la tabla antes de agregar nuevas filas
		    modelo.setRowCount(0);
		
		    for (Reservas reserva : resultados) {
		        String fechaEntrada = dateFormat.format(reserva.getFecha_entrada());
		        String fechaSalida = dateFormat.format(reserva.getFecha_salida());
		
		        // Agrega una fila a la tabla con los datos de la reserva
		        modelo.addRow(new Object[] {
		            reserva.getId(),
		            fechaEntrada,
		            fechaSalida,
		            reserva.getValor(),
		            reserva.getFormaPago()
		        });
		    }
		}
	}

 	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
 	private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }
    
    /**
     * Método para buscar huéspedes según la pestaña seleccionada.
     * Recoge el criterio de búsqueda del campo de texto y muestra los resultados en la tabla correspondiente.
     */
    private void buscarHuespedes(JTabbedPane panel) {
		Set<Long> huespedesAgregados = new HashSet<>(); // Controla un conjunto de huéspedes ya agregados
		String criterioBusqueda = txtBuscar.getText().trim();
		int pestañaSeleccionada = panel.getSelectedIndex();
		if (pestañaSeleccionada == 1) {
			List<Huespedes> resultados = huespedesDao.buscarEnDB(criterioBusqueda);
            DefaultTableModel modelo = (DefaultTableModel) tbHuespedes.getModel();
            modelo.setRowCount(0);
            
            // Agrega los resultados de la consulta a la tabla
            for (Huespedes huesped : resultados) {
            	if (!huespedesAgregados.contains(huesped.getId())) {
                	List<Reservas> reservas = huesped.getReservas();
                	List<Long> idReservas = new ArrayList<>();
                	for (Reservas reserva : reservas) {
                		idReservas.add(reserva.getId());
                    }
                	// Convertir los IDs de reserva en cadenas
                    List<String> idReservasStr = idReservas.stream()
                            .map(Object::toString)
                            .collect(Collectors.toList());
                    System.out.println("idRESERVAS STR = " + idReservasStr);
                 
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Define the desired date format

                    // Format the date of birth
                    String formattedDate = dateFormat.format(huesped.getFechaDeNacimiento());
                    modelo.addRow(new Object[] {
                        huesped.getId(),
                        huesped.getNombre(),
                        huesped.getApellido(),
                        formattedDate,
                        huesped.getNacionalidad(),
                        huesped.getTelefono(),
                        String.join(", ", idReservasStr) // Combina los IDs de reservas en una cadena separada por comas
                        
                    });
                    huespedesAgregados.add(huesped.getId());
            	}
            }
		}
	}
}

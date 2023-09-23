package com.latam.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.dao.UsuariosDao;
import com.latam.alura.hotel.modelo.Usuarios;
import com.latam.alura.hotel.utils.JPAUtils;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Login: Representa la ventana de inicio de sesión de la aplicación del hotel.
 * Permite a los usuarios ingresar su nombre de usuario y contraseña para iniciar sesión en el sistema.
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	int xMouse, yMouse;
	private JLabel labelExit;
	private Usuarios usuarios;
	EntityManager em = JPAUtils.getEntityManager();
	private UsuariosDao usuariosDao = new UsuariosDao(em);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Constructor de la clase Login.
     * Configura la interfaz de usuario y los manejadores de eventos para la ventana de inicio de sesión.
     */
	public Login() {
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		deleteUsers();
		generateUsers();
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 788, 527);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(12, 138, 199));
		panel_1.setBounds(484, 0, 304, 527);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel imgHotel = new JLabel("");
		imgHotel.setBounds(0, 0, 304, 538);
		panel_1.add(imgHotel);
		imgHotel.setIcon(new ImageIcon(Login.class.getResource("/imagenes/img-hotel-login-.png")));
		
		JPanel btnexit = new JPanel();
		btnexit.setBounds(251, 0, 53, 36);
		panel_1.add(btnexit);
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
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
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setLayout(null);
		btnexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		labelExit = new JLabel("X");
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setForeground(SystemColor.text);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);		
		
		txtUsuario = new JTextField();
		txtUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				 if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
					 txtUsuario.setText("");
					 txtUsuario.setForeground(Color.black);
			        }
			        if (String.valueOf(txtContrasenia.getPassword()).isEmpty()) {
			        	txtContrasenia.setText("********");
			        	txtContrasenia.setForeground(Color.gray);
			        }
			}
		});
		txtUsuario.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtUsuario.setText("Ingrese su nombre de usuario");
		txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtUsuario.setForeground(SystemColor.activeCaptionBorder);
		txtUsuario.setBounds(65, 256, 324, 32);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 120, 215));
		separator.setBounds(65, 292, 324, 2);
		panel.add(separator);
		
		JLabel labelTitulo = new JLabel("INICIAR SESIÓN");
		labelTitulo.setForeground(SystemColor.textHighlight);
		labelTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 26));
		labelTitulo.setBounds(65, 149, 202, 26);
		panel.add(labelTitulo);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(SystemColor.textHighlight);
		separator_1.setBounds(65, 393, 324, 2);
		panel.add(separator_1);
		
		txtContrasenia = new JPasswordField();
		txtContrasenia.setText("********");
		txtContrasenia.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(txtContrasenia.getPassword()).equals("********")) {
					txtContrasenia.setText("");
					txtContrasenia.setForeground(Color.black);
		        }
		        if (txtUsuario.getText().isEmpty()) {
		        	txtUsuario.setText("Ingrese su nombre de usuario");
		        	txtUsuario.setForeground(Color.gray);
		        }
			}
		});
		txtContrasenia.setForeground(SystemColor.activeCaptionBorder);
		txtContrasenia.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtContrasenia.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtContrasenia.setBounds(65, 353, 324, 32);
		panel.add(txtContrasenia);
		
		JLabel LabelUsuario = new JLabel("USUARIO");
		LabelUsuario.setForeground(SystemColor.textInactiveText);
		LabelUsuario.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		LabelUsuario.setBounds(65, 219, 107, 26);
		panel.add(LabelUsuario);
		
		JLabel lblContrasea = new JLabel("CONTRASEÑA");
		lblContrasea.setForeground(SystemColor.textInactiveText);
		lblContrasea.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		lblContrasea.setBounds(65, 316, 140, 26);
		panel.add(lblContrasea);
		
		JButton btnLogin = new JButton();
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setBackground(new Color(0, 156, 223));
			}
		
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(SystemColor.textHighlight);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				concretarLogin();
			}
		});
		btnLogin.setBackground(SystemColor.textHighlight);
		btnLogin.setBounds(65, 431, 122, 44);
		panel.add(btnLogin);
		btnLogin.setLayout(null);
		btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel = new JLabel("ENTRAR");
		lblNewLabel.setBounds(0, 0, 122, 44);
		btnLogin.add(lblNewLabel);
		lblNewLabel.setForeground(SystemColor.controlLtHighlight);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/imagenes/lOGO-50PX.png")));
		lblNewLabel_1.setBounds(65, 65, 48, 59);
		panel.add(lblNewLabel_1);
		
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
		header.setBackground(SystemColor.window);
		header.setBounds(0, 0, 784, 36);
		panel.add(header);
		header.setLayout(null);
	}
	
	/**
     * Método privado para eliminar todos los usuarios de la base de datos.
     * @return Una lista de los IDs de usuarios eliminados.
     */
	private List<Long> deleteUsers() {
		List<Long> idsEliminados = usuariosDao.eliminarTodosLosUsuarios();
		List<String> idsEliminadosStr = idsEliminados.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
		System.out.println("ids de los usuarios eliminados" + String.join(", ",idsEliminadosStr));
		return idsEliminados;
	}
	
	/**
     * Método privado para realizar el inicio de sesión y validar las credenciales ingresadas por el usuario.
     */
	private void concretarLogin() {
		String nombre = txtUsuario.getText();
		char[] contraseniaChars = txtContrasenia.getPassword();
		String clave = new String(contraseniaChars);
		
		try {
			Long id=null;
			try {
				id = usuariosDao.buscarIdUsuarioPorNombre(nombre);
			} catch (NoResultException e) {
				   System.out.println("El usuario no se encontró en la base de datos");
			}
			if (id==null) {
				JOptionPane.showMessageDialog(null, "Usuario no encontrado. Verifica mayúsculas y minúsculas.");
			}
			else {	
				var claveDB = usuariosDao.buscarClavePorId(id);
				if (clave.equals(claveDB)) {
					MenuUsuario menu = new MenuUsuario();
		            menu.setVisible(true);
		            dispose();	 
		        } else {
		            JOptionPane.showMessageDialog(this, "Contraseña no válida. Verifica mayúsculas y minúsculas.");
		        }
			} 
			
		} catch (Exception e) {
			System.out.println("Ocurrió un error al intentar hacer el login");
		}
	}
		 
	/**
     * Método privado para manejar el evento cuando se presiona el mouse en el encabezado
     * y permite arrastrar la ventana.
     * @param evt Evento de mouse.
     */ 
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}//GEN-LAST:event_headerMousePressed
	
	/**
     * Método privado para manejar el evento cuando se arrastra la ventana con el mouse.
     * @param evt Evento de mouse.
     */
	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
	
	/**
     * Método privado para generar usuarios ficticios, guardarlos en la base de datos y devolver una lista de los IDs generados.
     * @return Una lista de los IDs de usuarios generados.
     */
	private List<Long> generateUsers() {

		List<Long> generatedIds = new ArrayList<Long>();
		List<Usuarios> usuariosTotales = new ArrayList<Usuarios>();

		Usuarios usuario1 = new Usuarios();
		usuario1.setLogin("admin");
		usuario1.setContrasenia("admin");
		usuariosTotales.add(usuario1);

		Usuarios usuario2 = new Usuarios();
		usuario2.setLogin("Mario");
		usuario2.setContrasenia("alba");
		usuariosTotales.add(usuario2);

		Usuarios usuario3 = new Usuarios();
		usuario3.setLogin("Raúl");
		usuario3.setContrasenia("campo");
		usuariosTotales.add(usuario3);

		Usuarios usuario4 = new Usuarios();
		usuario4.setLogin("Florencia");
		usuario4.setContrasenia("malla");
		usuariosTotales.add(usuario4);

		Usuarios usuario5 = new Usuarios();
		usuario5.setLogin("Renata");
		usuario5.setContrasenia("palco");
		usuariosTotales.add(usuario5);

		for (Usuarios usuario: usuariosTotales) {
			var id = usuario.getId();
			generatedIds.add(id);
			usuariosDao.guardarUsuario(usuario);
		}

		return generatedIds;
	}
}

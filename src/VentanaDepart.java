
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.swing.*;

/**
 * 
 * Con esta clase mostramos nuestra ventana principal y todas sus funciones
 * @author Luis Cimorra Bertrand
 * 
 *
 */

public class VentanaDepart extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JTextField num = new JTextField(10);
	JTextField nombre = new JTextField(25);
	JTextField loc = new JTextField(25);

	JLabel mensaje = new JLabel(" ----------------------------- ");
	JLabel titulo = new JLabel("GESTI�N DE DEPARTAMENTOS.");

	JLabel lnum = new JLabel("NUMERO DEPARTAMENTO:");
	JLabel lnom = new JLabel("NOMBRE:");
	JLabel lloc = new JLabel("LOCALIDAD:");

	JButton balta = new JButton("Insertar Depar.t");
	JButton consu = new JButton("Consultar Depart.");
	JButton borra = new JButton("Borrar Depart.");
	JButton breset = new JButton("Limpiar datos.");
	JButton modif = new JButton("Modificar Departamento.");
	JButton ver = new JButton("Ver por consola.");
	JButton fin = new JButton("CERRAR");
	Color c; // para poner colores
	// WHITE,LIGHTGRAY,GRAY,DARKGRAY,BLUE,BLACK,RED,MAGENTA,PINK,ORANGE,CYAN,GREEN,YELLOW

	
	/**
	 * 
	 * Representa la ventana de gestión de departamentos
	 * @param f. parametro JFrame que recibe el metodo
	 * @author Luis Cimorra Bertrand
	 */
	public VentanaDepart(JFrame f) {
		setTitle("GESTI�N DE DEPARTAMENTOS.");

		JPanel p0 = new JPanel();
		c = Color.CYAN;
		p0.add(titulo);
		p0.setBackground(c);

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(lnum);
		p1.add(num);
		p1.add(consu);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(lnom);
		p2.add(nombre);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout());
		p3.add(lloc);
		p3.add(loc);

		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		c = Color.YELLOW;
		p4.add(balta);
		p4.add(borra);
		p4.add(modif);
		p4.setBackground(c);

		JPanel p5 = new JPanel();
		p4.setLayout(new FlowLayout());
		c = Color.PINK;
		p5.add(breset);
		p5.add(ver);
		p5.add(fin);
		p5.setBackground(c);

		JPanel p7 = new JPanel();
		p7.setLayout(new FlowLayout());
		p7.add(mensaje);

		// para ver la ventana y colocar los controles verticalmente
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		// a�adir los panel al frame
		add(p0);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p7);
		pack(); // hace que se coloquen alineados los elementos de cada JPanel

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		balta.addActionListener(this);
		breset.addActionListener(this);
		fin.addActionListener(this);
		consu.addActionListener(this);
		borra.addActionListener(this);
		modif.addActionListener(this);
		ver.addActionListener(this);
	}

	
	private static final String NOEXISTEDEPART  = "DEPARTAMENTO NO EXISTE." ;
	
	/**
	 * metodo para manejar los eventos que producen los botones de nuestra ventana al ser activados.
	 * @param e. el evento que se ha generado
	 * @author Luis Cimorra Bertrand
	 */
	public void actionPerformed(ActionEvent e) {
		String depExist = "DEPARTAMENTO EXISTE.";
		int dep, confirm;
		if (e.getSource() == balta) { // SE PULSA EL BOTON alta
			mensaje.setText(" has pulsado el boton alta");
			try {
				dep = Integer.parseInt(num.getText());
				if (dep > 0)
					if (consultar(dep))
						mensaje.setText(depExist);
					else {
						mensaje.setText("NUEVO DEPARTAMENTO.");
						grabar(dep, nombre.getText(), loc.getText());
						mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
			{
				mensaje.setText("DEPARTAMENTO ERR�NEO.");
			} catch (IOException ex2) {
				mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
				// lo creo

			}
		}

		if (e.getSource() == consu) { // SE PULSA EL BOTON consultar
			mensaje.setText(" has pulsado el boton alta");
			try {
				dep = Integer.parseInt(num.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText(depExist);
						visualiza(dep);
					} else {
						mensaje.setText(NOEXISTEDEPART);
						nombre.setText(" ");
						loc.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
			{
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText(" ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
			}

		}

		if (e.getSource() == borra) { // SE PULSA EL BOTON borrar
			mensaje.setText(" has pulsado el boton Borrar");
			try {
				dep = Integer.parseInt(num.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText(depExist);
						visualiza(dep);
						confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE BORRAR...", "AVISO BORRADO.",
								JOptionPane.OK_CANCEL_OPTION);
						// si devuelve 0 es OK
						// mensaje.setText(" has pulsado el boton Borrar "+ confirm);
						if (confirm == 0) {
							borrar(dep);
							mensaje.setText(" REGISTRO BORRADOO: " + dep);
							nombre.setText(" ");
							loc.setText(" ");
						}
					} else {
						mensaje.setText(NOEXISTEDEPART);
						nombre.setText(" ");
						loc.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
			{
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (BORRAR)");
			}
		}
		if (e.getSource() == modif) { // SE PULSA EL BOTON modificar
			mensaje.setText(" has pulsado el boton Modificar.");
			try {
				dep = Integer.parseInt(num.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText(depExist);
						confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE MODIFICAR...",
								"AVISO MODIFICACI�N.", JOptionPane.OK_CANCEL_OPTION);
						// si devuelve 0 es OK
						// mensaje.setText(" has pulsado el boton Borrar "+ confirm);
						if (confirm == 0) {
							modificar(dep);
							mensaje.setText(" REGISTRO MODIFICADO: " + dep);
						}
					} else {
						mensaje.setText(NOEXISTEDEPART);
						nombre.setText(" ");
						loc.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
			{
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText(" ERRORRR EN EL FICHERO. Fichero no existe. (MODIFICAR)");
			}
		}
		if (e.getSource() == fin) { // SE PULSA EL BOTON salir
			System.exit(0);
			// dispose();
		}
		if (e.getSource() == ver) { // SE PULSA EL BOTON ver por consola
			try {
				mensaje.setText("Visualizando el fichero por la consolaa.....");
				verporconsola();
			} catch (IOException e1) {
				System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
				// e1.printStackTrace();
			}
		}
		if (e.getSource() == breset) { // SE PULSA EL BOTON limpiar
			mensaje.setText(" has pulsado el boton limpiar..");
			num.setText(" ");
			nombre.setText(" ");
			loc.setText(" ");
		}
	}

	/**
	 * Metodo que muestra por consola el contenido del archivo "AleatorioDep.dat".
	 * @throws IOException
	 * @author Luis Cimorra Bertrand
	 */
	public void verporconsola() throws IOException {
		String nom = "", loc = "";
		int dep = 0;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		char cad[] = new char[10], aux;
		if (file.length() > 0) {
			pos = 0; // para situarnos al principio
			System.out.println(" ------------------------------------------");
			System.out.println(" - - - VISUALIZO POR CONSOLAAAAA ");
			for (;;) { // recorro el fichero, visualiza tambi�n las posiciones vac�as
				file.seek(pos);
				dep = file.readInt(); // obtengo el dep
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();// recorro uno a uno los caracteres del apellido
					cad[i] = aux; // los voy guardando en el array
				}
				nom = new String(cad);// convierto a String el array
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();
					cad[i] = aux;
				}
				loc = new String(cad);// convierto a String el array
				System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loc);
				pos = pos + 44;
				// Si he recorrido todos los bytes salgo del for
				if (file.getFilePointer() == file.length())
					break;

			} // fin bucle for
			file.close(); // cerrar fichero
			System.out.println(" ------------------------------------------");
		} else // esto s�lo sale la primera vez
			System.out.println(" ---------FICHERO VACI�IOOOO --------------------");
	}// fin verporconsola

	boolean consultar(int dep) throws IOException {
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		// Calculo del reg a leer
		try {
			pos = 44 * (dep - 1);
			if (file.length() == 0)
				return false; // si est� vac�o
			file.seek(pos);
			depa = file.readInt();
			file.close();
			System.out.println("Depart leido:" + depa);
			if (depa > 0)
				return true;
			else
				return false;
		} catch (IOException ex2) {
			System.out.println(" ERRORRR al leerrrrr..");
			return false;
		}
	} // fin consultar

	/**
	 * 
	 * Comprueba si un departamento ya existe en el archivo "AleatorioDep.dat".
	 * @param dep. el numero de departamento a consultar
	 * @return true si el departamento existe, false en caso contrario o si se produce un error al leer el archivo
	 * @throws IOException si se produce un error de lectura en el archivo
	 * @author Luis Cimorra Bertrand
	 */
	void visualiza(int dep) {
		String nom = "", loca = "";
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			depa = file.readInt();
			System.out.println("Depart leido:" + depa);
			char nom1[] = new char[10], aux, loc1[] = new char[10];
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				nom1[i] = aux;
			}
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				loc1[i] = aux;
			}
			nom = new String(nom1);
			loca = new String(loc1);
			System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loca);
			nombre.setText(nom);
			loc.setText(loca);
			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin visualiza

	/**
	 * 
	 * Pone a 0 el departamento que se quiere borrar y a blancos el nombre y la localidad
	 * @param dep. numero de departamento a borrar
	 * @author Luis Cimorra Bertrand
	 */
	void borrar(int dep) { // con borrar ponemos a 0 el dep que se quiere borrar
							// y a blancos el nombre y la localidad
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			int depp = 0;
			file.writeInt(depp);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());

			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO BORRADO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL BORRARRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin borrar

	
	/**
	 * se encarga de asignar los datos tecleados por el usuario en los campos de nombre y
	 * localidad al registro correspondiente al número de departamento especificado.
	 * @param dep. El numero de departamento a modificar
	 * @author Luis Cimorra Bertrand
	 */
	void modificar(int dep) { // con modificar asignamos los datos tecleados
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			file.writeInt(dep);
			nom = nombre.getText();
			loca = loc.getText();
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO MODIFICADOOO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL MODIFICARRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin modificar

	
	/**
	 * 
	 * Graba un registro en el archivo "AleatorioDep.dat" con los datos proporcionados.
	 * @param dep. El número de departamento a grabar.
	 * @param nom. El nombre del departamento a grabar.
	 * @param loc. La localidad del departamento a grabar.
	 * @author Luis Cimorra Bertrand
	 */
	void grabar(int dep, String nom, String loc) {
		long pos;
		StringBuffer buffer = null;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			// if (file.length()==0) return false; // si est� vac�o

			file.seek(pos);
			file.writeInt(dep);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar nombre
			buffer = new StringBuffer(loc);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar loc
			file.close();
			System.out.println(" GRABADOOO el " + dep);
		} catch (IOException e1) {
			System.out.println("ERRROR AL grabarr AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin grabar
}// fin clase

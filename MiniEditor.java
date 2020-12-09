import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MiniEditor extends javax.swing.JFrame implements ActionListener, ItemListener{
	// Componentes
	JFrame JF;
	JToolBar JTB; // Barra que se incrsuta en la aplicacion o puede estar separada de la aplicacion (propio de swing)
	JButton cortar, copiar, pegar, may, min;
	Choice fuentes, size; // Lista de selección
	GraphicsEnvironment amb;
	JTextArea TA;
	JMenuBar JMB;
	JMenu archivo;
	JMenuItem nuevo, abrir, guardar;
	JFileChooser chooser;


	// Variables normales
	String buffer, fuente;
	int tam, estilo;
	File archiv;
	/*Equivalenes numericos para estilos
		0 -> Plain (normal)
		1 -> Negrita
		2 -> Cursiva
	*/

	public MiniEditor(){
		tam = 14; // Inicializamos tamaño deletra
		estilo = 0; // Inicializamos estilo deletra
		fuente = "Consolas";

		GraphicsEnvironment amb;
		String listaNoms[];

		//Cracion de objetos
		JF = new JFrame("MyEdit");
		JTB = new JToolBar("Herramientas de edicion", JToolBar.VERTICAL);
		TA = new JTextArea();
		cortar = new JButton(new ImageIcon(getClass().getResource("./imgs/24/tijeras.png")));
		copiar = new JButton(new ImageIcon(getClass().getResource("./imgs/24/copia.png")));
		pegar = new JButton(new ImageIcon(getClass().getResource("./imgs/24/pegar.png")));
		may = new JButton("M");
		min = new JButton("m");
		JMB = new JMenuBar();
		archivo = new JMenu("Archivo");
		nuevo = new JMenuItem("Nuevo");
		abrir = new JMenuItem("Abrir");
		guardar = new JMenuItem("Guardar");


		// Cambios de apariencia de componentes
		TA.setFont(new Font(fuente, estilo, tam)); // definimos tamaño y estilo de letra del JTextArea
		TA.setBackground(new Color(0, 0, 0)); // definimos el color del JTextArea
		TA.setForeground(new Color(0, 87, 255));
		// fuentes = new Choice();


		// Fuentes del sistema
		amb = GraphicsEnvironment.getLocalGraphicsEnvironment(); // Obtiene las fuentes que tiene el aparato que esta corriendo el programa
		listaNoms = amb.getAvailableFontFamilyNames(); // Saber la familia de fuentes disponibles quese encuentran en el equipo (devolvemos una lista de nombres de fuentes)
		fuentes = new Choice();
		for (int i = 0; i<listaNoms.length; i++) {
			fuentes.add(listaNoms[i]); // Agregamos los nombres de fuentes a ala lista desplegable
		}
		// Tamaño de letra
		size = new Choice();
		for (int i = 10; i<=30; i+=2) {
			size.add(String.valueOf(i)); // Agregamos tamaños de fuentes
		}


		// Agregando metodos a botones (evento ActionListener)
		may.addActionListener(this);
		min.addActionListener(this);
		copiar.addActionListener(this);
		pegar.addActionListener(this);
		cortar.addActionListener(this);

		// Agregando metodos a choice (evento ItemListener)
		size.addItemListener(this);
		fuentes.addItemListener(this);

		// Agregando metodos a los MenuItem (evento ActionListener)
		nuevo.addActionListener(this);
		abrir.addActionListener(this);
		guardar.addActionListener(this);


		// Acomodo
		JTB.setLayout(new FlowLayout()); // Definimos el tipo del Layout del JToolBar
		JTB.add(fuentes); // Agregamos el Choice fuentes al JToolBar
		JTB.add(size); // Agregamos el Choice size al JToolBar


		// Agragamos elementos al JMenuBar
		archivo.add(nuevo);
		archivo.add(abrir);
		archivo.add(guardar);
		JMB.add(archivo);
		JF.setJMenuBar(JMB); // Agregamos el JMenuBar al JFrame
		


		// Agregamos los botones al JToolBar
		JTB.add(cortar);
		JTB.add(copiar);
		JTB.add(pegar);
		JTB.add(may);
		JTB.add(min);

		JF.add(JTB, BorderLayout.NORTH); // Agregamos el JToolBar al JFrame
		JF.add(TA, BorderLayout.CENTER); // Agregamos el TextArea al JFrame
		JF.setSize(600, 600); // Definimos tamño del JFrame
		JF.setLocationRelativeTo(null); //Colocamos el JFrame en el centro de la pantalla
 		JF.setVisible(true); // Hacemos visible el JFrame
 		JF.setResizable(false); // Impide que el JFrame sea redimencionable (Siempre poner despues del setSize())

 		// Cerrando por completo la aplicacion
 		JF.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JF.addWindowListener(new java.awt.event.WindowAdapter(){
        	@Override
        	public void windowClosing(java.awt.event.WindowEvent evt){
        		System.exit(0);
        	}
        });
	}


	// Eventos de botones
	public void actionPerformed(ActionEvent ev){

		// Boton convertir a mayusculas
		if (ev.getSource().equals(may)) {
			// Metodos de TextArea, TextComponent y String
			TA.replaceRange((TA.getSelectedText()).toUpperCase(), TA.getSelectionStart(), TA.getSelectionEnd());
			/*
			replaceRange-- remplaza el contenido en ciero rango dado (metodo de TextArea)
			getSelectedText-- Toma el texto seleccionado del TextArea (metodo deTextComponent)
			toUpperCase-- Convierte una cadena (String) a mayusculas (metodo de String)
			getSelectionStart-- devuelve un entero de donde comienza la seleccion del texto (metodo deTextComponent)
			getSelectionEnd-- devuelve un entero de donde termina la seleccion del texto (metodo deTextComponent)
			*/
		}

		// Boton convertir a minusculas
		else if (ev.getSource().equals(min)) {
			TA.replaceRange((TA.getSelectedText()).toLowerCase(), TA.getSelectionStart(), TA.getSelectionEnd());
		}

		// Boton de copiar texto
		else if (ev.getSource().equals(copiar)) {
			buffer = TA.getSelectedText();
		}

		// Boton de pegar texto
		else if (ev.getSource().equals(pegar)) {
			// Validadmos tener algo copiado en la bariable "buffer"
			if (buffer != null) {
				TA.insert(buffer, TA.getCaretPosition());
				/*
				insert-- Inserca una cadena pasada como parametro en la posicion especificada (metodo de TextArea)
				getCaretPosition-- Nos da la posicion donde se encuentra nuestro cursor (metodo deTextComponent)
				*/
			}
		}

		// Boton de cortar texto
		else if (ev.getSource().equals(cortar)) {
			buffer = TA.getSelectedText();
			TA.replaceRange("", TA.getSelectionStart(), TA.getSelectionEnd());
		}

		// MenuItem nuevo
		else if (ev.getSource().equals(nuevo)) {
			TA.setText(""); // Reemplazamos todo el tecto del JTextArea por una cadena vacia
		}

		// MenuItem abrir
		else if (ev.getActionCommand()=="Abrir") { // Compararamos con el valor del tecxto, no el mombre de variable
			// El chooser es una ventana de dialogo
			chooser = new JFileChooser(); // Abre la ventana donde podemos guardar el archivo (como cuando le damos guardar en el block de notas)
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto", "txt", "bat", "java", "cpp"); // Indicamos que texto va a salir y las extenciones de texto que va a poder abrir
			chooser.setFileFilter(filter); // Asignamos el filtro al chooser
			int returnVal = chooser.showOpenDialog(JF); // Mostramos el chooser y lo hacemos dependiente del JFrame
			if (returnVal == JFileChooser.APPROVE_OPTION) { // Comparamos si se oprimio el boton de aprovado (o aceptar)
				archiv = chooser.getCurrentDirectory(); // Le decimos al chooser que nos de el direcrio actual del archo seleccionado
				archiv = new File(archiv.getPath() + "\\" + chooser.getSelectedFile().getName()); // Pasamos la direccion del archivo y  el nombre del archivo
				System.out.println("Vamos a abrir: " + archiv.getPath());
				abrir(archiv);
			}
			else{
				System.out.println("Se cancelo el abrir");
			}
		}

		// MenuItem guardar
		else if (ev.getActionCommand() == "Guardar") {
			chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto", "txt", "bat", "java", "cpp");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				archiv = new File(chooser.getCurrentDirectory().getPath() + "\\" + chooser.getSelectedFile().getName());
				System.out.println("Vamos a guardar en: " + archiv.getPath());
				guardar(archiv);
			}
		}
	}

	// Eventos de choice
	public void itemStateChanged(ItemEvent ev){

		if (ev.getSource().equals(size)) { // Cmabio en el tamaño de la fuente
			tam = Integer.parseInt(size.getSelectedItem()); // Devuelve la cadena que fue seleccionada
		}
		else if (ev.getSource().equals(fuentes)) {
			fuente = fuentes.getSelectedItem(); // Devuelve la cadena que fue seleccionada
		}
		TA.setFont(new Font(fuente, estilo, tam));
	}

	// Funcion de abrir el archivo
	public void abrir(File archi){
		String lectura;
		BufferedReader br;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(archi)));
			TA.setText("");
			while((lectura=br.readLine())!=null){
				TA.append(lectura+"\n");
			}
			br.close();
		}catch(IOException e){
			System.out.println("Error al leer el archivo " + e.getMessage());
		}
	}

	// Funcion de guardar archivo
	public void guardar(File archi){
		BufferedWriter bw;
		FileWriter fw;
		String text = TA.getText();
		
		try{

			if (!archi.exists()) { //Comprobamos que el archivo no exista
				archi.createNewFile(); // Creamos el archivo
			}
			fw = new FileWriter(archi);
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		}catch(IOException e){
			System.out.println("Error al guardar el archivo");
		}
	}

	public static void main(String[] args) {
		MiniEditor myEdit = new MiniEditor();
	}
}
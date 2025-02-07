package View;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame {

  private JButton btnLexico;
  private JTextArea txtCodigo, txtErrores, txtTokens;
  private JMenuItem menuAbrir, menuGuardar, menuSalir;
  private JTable tblSimbolo;
  private DefaultTableModel modelo;

  public View() {
    super("Compilador Tec++");
    hazInterfaz();
  }

  public void hazInterfaz() {
    setSize(1024, 768);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Menu de la aplicacion
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Archivo");
    menuAbrir = new JMenuItem("Abir archivo");
    menuGuardar = new JMenuItem("Guardar archivo");
    menuSalir = new JMenuItem("Salir");
    menu.add(menuAbrir);
    menu.add(menuGuardar);
    menu.add(menuSalir);
    menuBar.add(menu);
    setJMenuBar(menuBar);

    JLabel lblTitulo = new JLabel("Compilador");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    add(lblTitulo, BorderLayout.NORTH);

    JPanel panelCentro = new JPanel();
    panelCentro.setLayout(new GridLayout(0, 3));

    JPanel panelIzq = new JPanel();
    panelIzq.setLayout(new BorderLayout());
    panelIzq.setBorder(BorderFactory.createTitledBorder("Codigo fuente"));
    txtCodigo = new JTextArea();
    panelIzq.add(new JScrollPane(txtCodigo), BorderLayout.CENTER);
    panelCentro.add(panelIzq);

    JPanel panelCen = new JPanel();
    panelCen.setLayout(new GridLayout(0, 1));

    JPanel panelCen1 = new JPanel();
    panelCen1.setLayout(new BorderLayout());
    panelCen1.setBorder(BorderFactory.createTitledBorder("Tabla de simbolos"));
    modelo = new DefaultTableModel(new String[] { "Nombre", "Tipo", "Alcance", "Parametros", "Retorno" }, 0);
    tblSimbolo = new JTable(modelo);
    panelCen1.add(new JScrollPane(tblSimbolo), BorderLayout.CENTER);
    panelCen.add(panelCen1);

    JPanel panelCen2 = new JPanel();
    panelCen2.setLayout(new BorderLayout());
    panelCen2.setBorder(BorderFactory.createTitledBorder("Errores"));
    txtErrores = new JTextArea();
    txtErrores.setEditable(false);
    panelCen2.add(new JScrollPane(txtErrores));
    panelCen.add(panelCen2);
    panelCentro.add(panelCen);

    JPanel panelDer = new JPanel();
    panelDer.setLayout(new BorderLayout());
    panelDer.setBorder(BorderFactory.createTitledBorder("Tokens"));
    txtTokens = new JTextArea();
    txtTokens.setEditable(false);
    panelDer.add(new JScrollPane(txtTokens), BorderLayout.CENTER);
    panelCentro.add(panelDer);

    add(panelCentro);

    JPanel panelSur = new JPanel();
    btnLexico = new JButton("Analisis Lexico");
    btnLexico.setEnabled(false);
    panelSur.add(btnLexico);
    add(panelSur, BorderLayout.SOUTH);

    setVisible(true);

  }
}

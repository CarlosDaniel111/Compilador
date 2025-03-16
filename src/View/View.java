package View;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;

public class View extends JFrame {

  private JButton btnScanner, btnParser, btnSemantico, btnCodigoIntermedio, btnCodigoObjeto;
  private JTextArea txtCodigo, txtTokens, txtConsolaScanner,
      txtConsolaParser, txtConsolaSemantico, txtCodigoIntermedio, txtCodigoObjeto;
  private JMenuItem menuAbrir, menuGuardar, menuSalir;

  public View() {
    super("Compilador Tec++");
    hazInterfaz();
  }

  private void hazInterfaz() {
    setSize(1224, 768);
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

    JLabel lblTitulo = new JLabel("Compilador Tec++");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    add(lblTitulo, BorderLayout.NORTH);

    JPanel panelPrincipal = new JPanel();
    panelPrincipal.setLayout(new GridLayout(0, 2));

    JPanel panelAnalizadores = new JPanel();
    JPanel panelCodigoTokens = new JPanel();
    panelCodigoTokens.setLayout(new GridLayout(0, 2));

    JPanel panelCodigo = new JPanel();
    panelCodigo.setLayout(new BorderLayout());
    panelCodigo.setBorder(BorderFactory.createTitledBorder("Codigo fuente"));
    txtCodigo = new JTextArea();
    panelCodigo.add(new JScrollPane(txtCodigo), BorderLayout.CENTER);
    panelCodigoTokens.add(panelCodigo);

    JPanel panelTokens = new JPanel();
    panelTokens.setLayout(new BorderLayout());
    panelTokens.setBorder(BorderFactory.createTitledBorder("Tokens"));
    txtTokens = new JTextArea();
    txtTokens.setEditable(false);
    panelTokens.add(new JScrollPane(txtTokens), BorderLayout.CENTER);
    panelCodigoTokens.add(panelTokens);

    panelAnalizadores.setLayout(new GridLayout(0, 1));
    panelAnalizadores.add(panelCodigoTokens);

    JPanel panelConsolas = new JPanel();
    panelConsolas.setLayout(new GridLayout(0, 3));

    // Panel Scanner
    JPanel panelScanner = new JPanel();
    panelScanner.setLayout(new BorderLayout());
    panelScanner.setBorder(BorderFactory.createTitledBorder("Consola Scanner"));
    txtConsolaScanner = new JTextArea();
    txtConsolaScanner.setEditable(false);
    panelScanner.add(new JScrollPane(txtConsolaScanner), BorderLayout.CENTER);
    btnScanner = new JButton("Scanner");
    btnScanner.setContentAreaFilled(true);
    btnScanner.setEnabled(false);
    panelScanner.add(btnScanner, BorderLayout.SOUTH);
    panelConsolas.add(panelScanner);

    // Panel Parser
    JPanel panelParser = new JPanel();
    txtConsolaParser = new JTextArea();
    txtConsolaParser.setEditable(false);
    txtConsolaParser.setPreferredSize(new Dimension(0, 100));
    panelParser.setLayout(new BorderLayout());
    panelParser.setBorder(BorderFactory.createTitledBorder("Consola Parser"));
    panelParser.add(new JScrollPane(txtConsolaParser), BorderLayout.CENTER);
    btnParser = new JButton("Parser");
    btnParser.setContentAreaFilled(true);
    btnParser.setEnabled(false);
    panelParser.add(btnParser, BorderLayout.SOUTH);
    panelConsolas.add(panelParser);

    // Panel Semantico
    JPanel panelSemantico = new JPanel();
    txtConsolaSemantico = new JTextArea();
    txtConsolaSemantico.setEditable(false);
    panelSemantico.setLayout(new BorderLayout());
    panelSemantico.setBorder(BorderFactory.createTitledBorder("Consola Semantico"));
    panelSemantico.add(new JScrollPane(txtConsolaSemantico), BorderLayout.CENTER);
    btnSemantico = new JButton("Semantico");
    btnSemantico.setContentAreaFilled(true);
    btnSemantico.setEnabled(false);
    panelSemantico.add(btnSemantico, BorderLayout.SOUTH);
    panelConsolas.add(panelSemantico);

    panelAnalizadores.add(panelConsolas);
    panelPrincipal.add(panelAnalizadores, BorderLayout.CENTER);

    // Paneles de traductores
    JPanel panelTraductores = new JPanel();
    panelTraductores.setLayout(new GridLayout(0, 1));
    JPanel panelCodigoIntermedio = new JPanel();
    panelCodigoIntermedio.setLayout(new BorderLayout());
    panelCodigoIntermedio.setBorder(BorderFactory.createTitledBorder("Codigo intermedio"));
    txtCodigoIntermedio = new JTextArea();
    txtCodigoIntermedio.setEditable(false);
    panelCodigoIntermedio.add(new JScrollPane(txtCodigoIntermedio), BorderLayout.CENTER);
    btnCodigoIntermedio = new JButton("Codigo intermedio");
    btnCodigoIntermedio.setContentAreaFilled(true);
    btnCodigoIntermedio.setEnabled(false);
    panelCodigoIntermedio.add(btnCodigoIntermedio, BorderLayout.SOUTH);
    panelTraductores.add(panelCodigoIntermedio);

    JPanel panelCodigoObjeto = new JPanel();
    panelCodigoObjeto.setLayout(new BorderLayout());
    panelCodigoObjeto.setBorder(BorderFactory.createTitledBorder("Codigo objeto"));
    txtCodigoObjeto = new JTextArea();
    txtCodigoObjeto.setEditable(false);
    panelCodigoObjeto.add(new JScrollPane(txtCodigoObjeto), BorderLayout.CENTER);
    btnCodigoObjeto = new JButton("Codigo objeto");
    btnCodigoObjeto.setContentAreaFilled(true);
    btnCodigoObjeto.setEnabled(false);
    panelCodigoObjeto.add(btnCodigoObjeto, BorderLayout.SOUTH);
    panelTraductores.add(panelCodigoObjeto);

    panelPrincipal.add(panelTraductores);
    add(panelPrincipal, BorderLayout.CENTER);

    setVisible(true);

  }

  public void hazEscuchas(Controller controlador) {
    menuAbrir.addActionListener(controlador);
    menuGuardar.addActionListener(controlador);
    menuSalir.addActionListener(controlador);
    btnScanner.addActionListener(controlador);
    btnParser.addActionListener(controlador);
    btnSemantico.addActionListener(controlador);
    btnCodigoIntermedio.addActionListener(controlador);
    txtCodigo.addKeyListener(controlador);
  }

  public JMenuItem getMenuAbrir() {
    return menuAbrir;
  }

  public JMenuItem getMenuGuardar() {
    return menuGuardar;
  }

  public JMenuItem getMenuSalir() {
    return menuSalir;
  }

  public JButton getBtnScanner() {
    return btnScanner;
  }

  public JButton getBtnParser() {
    return btnParser;
  }

  public JButton getBtnSemantico() {
    return btnSemantico;
  }

  public JButton getBtnCodigoIntermedio() {
    return btnCodigoIntermedio;
  }

  public JTextArea getTxtCodigo() {
    return txtCodigo;
  }

  public JTextArea getTxtTokens() {
    return txtTokens;
  }

  public JTextArea getTxtConsolaScanner() {
    return txtConsolaScanner;
  }

  public JTextArea getTxtConsolaParser() {
    return txtConsolaParser;
  }

  public JTextArea getTxtConsolaSemantico() {
    return txtConsolaSemantico;
  }

  public JTextArea getTxtCodigoIntermedio() {
    return txtCodigoIntermedio;
  }

  public JTextArea getTxtCodigoObjeto() {
    return txtCodigoObjeto;
  }
}

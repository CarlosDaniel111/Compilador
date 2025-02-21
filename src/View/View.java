package View;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;

public class View extends JFrame {

  private JButton btnScanner, btnParser, btnSemantico;
  private JTextArea txtCodigo, txtTokens, txtConsolaScanner, txtConsolaParser, txtConsolaSemantico;
  private JMenuItem menuAbrir, menuGuardar, menuSalir;

  public View() {
    super("Compilador Tec++");
    hazInterfaz();
  }

  private void hazInterfaz() {
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

    JLabel lblTitulo = new JLabel("Compilador Tec++");
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

    JPanel panelDer = new JPanel();
    panelDer.setLayout(new BorderLayout());
    panelDer.setBorder(BorderFactory.createTitledBorder("Tokens"));
    txtTokens = new JTextArea();
    txtTokens.setEditable(false);
    panelDer.add(new JScrollPane(txtTokens), BorderLayout.CENTER);
    panelCentro.add(panelDer);

    add(panelCentro);

    JPanel panelSur = new JPanel();
    panelSur.setLayout(new GridLayout(0, 3));

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
    panelSur.add(panelScanner);

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
    panelSur.add(panelParser);

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
    panelSur.add(panelSemantico);

    add(panelSur, BorderLayout.SOUTH);

    setVisible(true);

  }

  public void hazEscuchas(Controller controlador) {
    menuAbrir.addActionListener(controlador);
    menuGuardar.addActionListener(controlador);
    menuSalir.addActionListener(controlador);
    btnScanner.addActionListener(controlador);
    btnParser.addActionListener(controlador);
    btnSemantico.addActionListener(controlador);
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
}

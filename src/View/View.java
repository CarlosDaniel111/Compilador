package View;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;

public class View extends JFrame {

  private JButton btnScanner;
  private JTextArea txtCodigo, txtConsola, txtTokens;
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
    panelSur.setLayout(new BorderLayout());
    JPanel panelSur1 = new JPanel();
    panelSur1.setLayout(new BorderLayout());
    panelSur1.setBorder(BorderFactory.createTitledBorder("Consola"));
    txtConsola = new JTextArea();
    txtConsola.setEditable(false);
    txtConsola.setForeground(Color.RED);
    txtConsola.setPreferredSize(new Dimension(0, 100));
    panelSur1.add(new JScrollPane(txtConsola), BorderLayout.CENTER);
    panelSur.add(panelSur1, BorderLayout.CENTER);

    JPanel panelSur2 = new JPanel();
    btnScanner = new JButton("Scanner");
    btnScanner.setEnabled(false);
    panelSur2.add(btnScanner);
    panelSur.add(panelSur2, BorderLayout.SOUTH);
    add(panelSur, BorderLayout.SOUTH);

    setVisible(true);

  }

  public void hazEscuchas(Controller controlador) {
    menuAbrir.addActionListener(controlador);
    menuGuardar.addActionListener(controlador);
    menuSalir.addActionListener(controlador);
    btnScanner.addActionListener(controlador);
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

  public JTextArea getTxtCodigo() {
    return txtCodigo;
  }

  public JTextArea getTxtConsola() {
    return txtConsola;
  }

  public JTextArea getTxtTokens() {
    return txtTokens;
  }
}

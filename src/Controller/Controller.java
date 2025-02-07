package Controller;

import java.awt.event.*;
import Model.Scanner.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Model.utils.Archivo;
import View.View;

public class Controller implements ActionListener, KeyListener {
  private View view;
  private Scanner scanner;

  public Controller(View view) {
    this.view = view;
    this.view.hazEscuchas(this);
    scanner = new Scanner();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Botones de menu
    if (e.getSource() == view.getMenuAbrir()) {
      JFileChooser fc = new JFileChooser();
      int seleccion = fc.showOpenDialog(view);
      if (seleccion == JFileChooser.APPROVE_OPTION) {
        String ruta = fc.getSelectedFile().getAbsolutePath();
        Archivo archivo = new Archivo(ruta);
        String texto = archivo.leerArchivo();
        view.getTxtCodigo().setText(texto);
        view.getBtnScanner().setEnabled(true);
        view.getTxtConsola().setText("");
        view.getTxtTokens().setText("");
      }
      return;
    }
    if (e.getSource() == view.getMenuGuardar()) {
      JFileChooser fc = new JFileChooser();
      int seleccion = fc.showSaveDialog(view);
      if (seleccion == JFileChooser.APPROVE_OPTION) {
        String ruta = fc.getSelectedFile().getAbsolutePath();
        Archivo archivo = new Archivo(ruta);
        String texto = view.getTxtCodigo().getText();
        archivo.escribirArchivo(texto);
      }
      return;
    }
    if (e.getSource() == view.getMenuSalir()) {
      if (JOptionPane.showConfirmDialog(view, "¿Estas seguro que quieres salir?") == 0) {
        System.exit(0);
      }
      return;
    }

    // Botones
    if (e.getSource() == view.getBtnScanner()) {
      scanner.analizar(view.getTxtCodigo().getText());
      view.getTxtConsola().setText(scanner.getErrores());
      if (scanner.getErrores().length() == 0) {
        JOptionPane.showMessageDialog(view, "Scanner exitoso", "Compilador",
            JOptionPane.INFORMATION_MESSAGE);
        view.getTxtTokens().setText(scanner.getTokens());
      } else {
        JOptionPane.showMessageDialog(view, "Scanner con errores", "Compilador",
            JOptionPane.ERROR_MESSAGE);
        view.getTxtTokens().setText("");
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getSource() == view.getTxtCodigo()) {
      if (view.getTxtCodigo().getText().length() == 0) {
        view.getBtnScanner().setEnabled(false);
      } else {
        view.getBtnScanner().setEnabled(true);
      }
    }
  }

}

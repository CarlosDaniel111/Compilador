package Controller;

import java.awt.Color;
import java.awt.event.*;
import Model.Scanner.Scanner;
import Model.Semantico.Semantico;
import Model.GeneradorCodigo.GeneradorCodigo;
import Model.Parser.Parser;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Model.utils.Archivo;
import View.View;

public class Controller implements ActionListener, KeyListener {
  private View view;
  private Scanner scanner;
  private Parser parser;
  private Semantico semantico;
  private GeneradorCodigo codigoIntermedio;

  public Controller(View view) {
    this.view = view;
    this.view.hazEscuchas(this);
    scanner = new Scanner();
    parser = new Parser();
    semantico = new Semantico();
    codigoIntermedio = new GeneradorCodigo();
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
        view.getBtnParser().setEnabled(false);
        view.getBtnSemantico().setEnabled(false);
        view.getBtnCodigoIntermedio().setEnabled(false);
        view.getTxtConsolaScanner().setText("");
        view.getTxtConsolaParser().setText("");
        view.getTxtConsolaSemantico().setText("");
        view.getTxtCodigoIntermedio().setText("");
        view.getTxtCodigoObjeto().setText("");
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
      view.getTxtTokens().setText(scanner.getTokens());
      if (scanner.getErrores().length() == 0) {
        view.getTxtConsolaScanner().setForeground(new Color(31, 185, 62));
        view.getTxtConsolaScanner().setText("Scanner exitoso");
        view.getBtnScanner().setBackground(Color.GREEN);
        view.getBtnParser().setEnabled(true);
      } else {
        view.getTxtConsolaScanner().setForeground(Color.RED);
        view.getTxtConsolaScanner().setText(scanner.getErrores());
        view.getBtnParser().setEnabled(false);
      }
      return;
    }
    if (e.getSource() == view.getBtnParser()) {
      parser.analizar(scanner.getTokensArray());
      if (parser.getError().length() == 0) {
        view.getTxtConsolaParser().setForeground(new Color(31, 185, 62));
        view.getTxtConsolaParser().setText("Parser exitoso");
        view.getBtnParser().setBackground(Color.GREEN);
        view.getBtnSemantico().setEnabled(true);
      } else {
        view.getTxtConsolaParser().setForeground(Color.RED);
        view.getTxtConsolaParser().setText(parser.getError());
      }
      return;
    }

    if (e.getSource() == view.getBtnSemantico()) {
      semantico.analizar(scanner.getTokensArray());
      if (semantico.getError().length() == 0) {
        view.getTxtConsolaSemantico().setForeground(new Color(31, 185, 62));
        view.getTxtConsolaSemantico().setText("Semantico exitoso");
        view.getBtnSemantico().setBackground(Color.GREEN);
        view.getBtnCodigoIntermedio().setEnabled(true);
      } else {
        view.getTxtConsolaSemantico().setForeground(Color.RED);
        view.getTxtConsolaSemantico().setText(semantico.getError());
      }
      return;
    }

    if (e.getSource() == view.getBtnCodigoIntermedio()) {
      codigoIntermedio.generar(scanner.getTokensArray(), semantico.getTablaSimbolos());
      view.getTxtCodigoIntermedio().setText(codigoIntermedio.getCodigoIntermedio());
      view.getBtnCodigoObjeto().setEnabled(true);
    }

    if (e.getSource() == view.getBtnCodigoObjeto()) {
      // Generar el código objeto
      String codigoObjeto = codigoIntermedio.getCodigoObjeto();
      view.getTxtCodigoObjeto().setText(codigoObjeto);
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
      view.getBtnParser().setEnabled(false);
      view.getBtnSemantico().setEnabled(false);
      view.getBtnCodigoIntermedio().setEnabled(false);
      view.getTxtConsolaScanner().setText("");
      view.getTxtConsolaParser().setText("");
      view.getTxtConsolaSemantico().setText("");
      view.getTxtTokens().setText("");
      view.getTxtCodigoIntermedio().setText("");
      view.getTxtCodigoObjeto().setText("");
      if (view.getTxtCodigo().getText().length() == 0) {
        view.getBtnScanner().setEnabled(false);
      } else {
        view.getBtnScanner().setEnabled(true);
      }
    }
  }

}

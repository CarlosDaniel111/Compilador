package Model.Parser;

import Model.Scanner.Token;
import Model.utils.Constantes;

import java.util.ArrayList;

public class Parser {
  private int idx;
  private ArrayList<Token> tokens;
  private String error;

  public void analizar(ArrayList<Token> tokens) {
    idx = 0;
    this.tokens = tokens;
    error = "";
    try {
      programa();
    } catch (Exception e) {
      error = e.getMessage();
    }
  }

  private void programa() throws Exception {
    if (tokens.isEmpty()) {
      throw new Exception("Error sintactico: se esperaba un programa");
    }
    checarToken(Constantes.ITC, "se esperaba la palabra reservada 'ITC'");
    idx++;
    listaDeclaraciones(false);
  }

  private void listaDeclaraciones(boolean dentroIf) throws Exception {
    if (idx == tokens.size()) {
      throw new Exception("Error sintáctico: se esperaba una declaración");
    }
    if (dentroIf) {
      while (!tokens.get(idx).getToken().equals(Constantes.CORCHETE_CIE)) {
        declaracion();
        checarToken(Constantes.PUNTO_Y_COMA, "se esperaba un punto y coma");
        idx++;
        if (idx == tokens.size()) {
          throw new Exception("Error sintáctico: se esperaba un corchete de cierre");
        }
      }
      return;
    }

    while (!tokens.get(idx).getValor().equals(Constantes.TECNM)) {
      declaracion();
      checarToken(Constantes.PUNTO_Y_COMA, "se esperaba un punto y coma");
      idx++;
      if (idx == tokens.size()) {
        throw new Exception("Error sintáctico: se esperaba una declaración");
      }
    }
  }

  private void declaracion() throws Exception {
    if (Constantes.esTipoDato(tokens.get(idx).getValor())) {
      idx++;
      checarToken(Constantes.ID, "se esperaba un identificador");
      idx++;
    } else if (tokens.get(idx).getToken().equals(Constantes.ID)) {
      idx++;
      if (idx == tokens.size()) {
        throw new Exception("Error sintáctico: se esperaba un signo de asignación");
      }
      if (tokens.get(idx).getToken().equals(Constantes.ASIGNACION)) {
        idx++;
        if (idx == tokens.size()) {
          throw new Exception("Error sintáctico: se esperaba una cadena o una expresión");
        }
        if (tokens.get(idx).getToken().equals(Constantes.CADENA)) {
          idx++;
        } else {
          expresion();
        }
      } else {
        throw new Exception("Error sintactico: se esperaba un signo de asignación");
      }
    } else if (tokens.get(idx).getValor().equals(Constantes.SI)) {
      condicional();
    } else if (tokens.get(idx).getValor().equals(Constantes.MIENTRAS)) {
      bucle();
    } else if (tokens.get(idx).getValor().equals(Constantes.LEER)) {
      entrada();
    } else if (tokens.get(idx).getValor().equals(Constantes.MOSTRAR)) {
      salida();
    } else {
      throw new Exception("Error sintactico: se esperaba una declaración");
    }
  }

  private void expresion() throws Exception {
    if (idx == tokens.size()) {
      throw new Exception("Error sintáctico: se esperaba una expresión");
    }

    if (tokens.get(idx).getToken().equals(Constantes.ID) ||
        tokens.get(idx).getToken().equals(Constantes.NUMERO) ||
        tokens.get(idx).getToken().equals(Constantes.DECIMAL)) {
      idx++;
    } else if (tokens.get(idx).getToken().equals(Constantes.PARENTESIS_APE)) {
      idx++;
      expresion();

      if (idx == tokens.size()) {
        throw new Exception("Error sintáctico: se esperaba un operador aritmético");
      }
      if (Constantes.esOperadorAritmetico(tokens.get(idx).getToken())) {
        idx++;
      } else {
        throw new Exception("Error sintactico: se esperaba un operador aritmetico");
      }
      expresion();
      checarToken(Constantes.PARENTESIS_CIE, "se esperaba un parentesis de cierre");
      idx++;
    } else {
      throw new Exception("Error sintactico: se esperaba una expresion");
    }
  }

  private void condicional() throws Exception {
    checarToken(Constantes.SI, "se esperaba la palabra reservada 'si'");
    idx++;

    checarToken(Constantes.PARENTESIS_APE, "se esperaba un paréntesis de apertura");
    idx++;

    expresionComparador();

    checarToken(Constantes.PARENTESIS_CIE, "se esperaba un paréntesis de cierre");
    idx++;

    checarToken(Constantes.CORCHETE_APE, "se esperaba un corchete de apertura");
    idx++;

    listaDeclaraciones(true);

    checarToken(Constantes.CORCHETE_CIE, "se esperaba un corchete de cierre");
    idx++;

    if (tokens.get(idx).getValor().equals(Constantes.SINO)) {
      idx++;
      checarToken(Constantes.CORCHETE_APE, "se esperaba un corchete de apertura");
      idx++;
      listaDeclaraciones(true);
      checarToken(Constantes.CORCHETE_CIE, "se esperaba un corchete de cierre");
      idx++;
    }
  }

  private void expresionComparador() throws Exception {
    expresion();

    if (idx == tokens.size()) {
      throw new Exception("Error sintáctico: se esperaba un operador relacional");
    }

    if (Constantes.esOperadorRelacional(tokens.get(idx).getToken())) {
      idx++;
    } else {
      throw new Exception("Error sintactico: se esperaba un operador relacional");
    }
    expresion();
  }

  private void bucle() throws Exception {
    checarToken(Constantes.MIENTRAS, "se esperaba la palabra reservada 'mientras'");
    idx++;

    checarToken(Constantes.PARENTESIS_APE, "se esperaba un paréntesis de apertura");
    idx++;

    expresionComparador();

    checarToken(Constantes.PARENTESIS_CIE, "se esperaba un paréntesis de cierre");
    idx++;

    checarToken(Constantes.CORCHETE_APE, "se esperaba un corchete de apertura");
    idx++;

    listaDeclaraciones(true);

    checarToken(Constantes.CORCHETE_CIE, "se esperaba un corchete de cierre");
    idx++;
  }

  private void entrada() throws Exception {
    checarToken(Constantes.LEER, "se esperaba la palabra reservada 'leer'");
    idx++;

    checarToken(Constantes.PARENTESIS_APE, "se esperaba un paréntesis de apertura");
    idx++;

    checarToken(Constantes.ID, "se esperaba un identificador");
    idx++;

    checarToken(Constantes.PARENTESIS_CIE, "se esperaba un paréntesis de cierre");
    idx++;
  }

  private void salida() throws Exception {
    checarToken(Constantes.MOSTRAR, "se esperaba la palabra reservada 'mostrar'");
    idx++;

    checarToken(Constantes.PARENTESIS_APE, "se esperaba un paréntesis de apertura");
    idx++;

    if (idx == tokens.size()) {
      throw new Exception("Error sintáctico: se esperaba una cadena o un identificador");
    }

    if (tokens.get(idx).getToken().equals(Constantes.ID) ||
        tokens.get(idx).getToken().equals(Constantes.CADENA)) {
      idx++;
    } else {
      throw new Exception("Error sintactico: se esperaba un identificador o una cadena");
    }

    checarToken(Constantes.PARENTESIS_CIE, "se esperaba un paréntesis de cierre");
    idx++;
  }

  private void checarToken(String esperado, String mensajeError) throws Exception {
    if (idx == tokens.size()) {
      throw new Exception("Error sintáctico: se esperaba " + mensajeError);
    }
    if (Constantes.esPalabraReservada(tokens.get(idx).getValor())) {
      if (!tokens.get(idx).getValor().equals(esperado)) {
        throw new Exception("Error sintáctico: " + mensajeError + " en la fila " + tokens.get(idx).getLinea());
      }
    } else if (!tokens.get(idx).getToken().equals(esperado)) {
      throw new Exception("Error sintáctico: " + mensajeError + " en la fila " + tokens.get(idx).getLinea());
    }
  }

  public String getError() {
    return error;
  }
}

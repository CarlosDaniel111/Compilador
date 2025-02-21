package Model.Semantico;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Scanner.Token;
import Model.utils.Constantes;

public class Semantico {

  private ArrayList<Token> tokens;
  private String error;
  private HashMap<String, Simbolo> tablaSimbolos;
  private int idx;

  public void analizar(ArrayList<Token> tokens) {
    this.tokens = tokens;
    error = "";
    tablaSimbolos = new HashMap<>();
    idx = 0;
    try {
      analizar();
    } catch (Exception e) {
      error = e.getMessage();
    }
  }

  private void analizar() throws Exception {
    while (idx < tokens.size()) {
      if (Constantes.esTipoDato(tokens.get(idx).getValor())) {
        validarDeclaracion();
      } else if (tokens.get(idx).getToken().equals(Constantes.ID)) {
        validarAsignacion();
      } else if (tokens.get(idx).getValor().equals(Constantes.SI)
          || tokens.get(idx).getValor().equals(Constantes.MIENTRAS)) {
        validarExpresionComp();
      } else if (tokens.get(idx).getValor().equals(Constantes.LEER)) {
        validarLeer();
      } else if (tokens.get(idx).getValor().equals(Constantes.MOSTRAR)) {
        validarEscribir();
      } else {
        idx++;
      }
    }
  }

  private void validarDeclaracion() {
    String tipo = tokens.get(idx).getValor();
    idx++;
    String nombre = tokens.get(idx).getValor();
    if (tablaSimbolos.containsKey(nombre)) {
      throw new RuntimeException("Error semantico: la variable " + nombre + " ya fue declarada");
    }
    tablaSimbolos.put(nombre, new Simbolo(nombre, tipo));
    idx++;
  }

  private void validarAsignacion() {
    String nombre = tokens.get(idx).getValor();
    if (!tablaSimbolos.containsKey(nombre)) {
      throw new RuntimeException("Error semantico: la variable " + nombre + " no ha sido declarada");
    }
    idx++;
    idx++;
    String tipoExpresion = validarExpresion();
    String tipoVariable = tablaSimbolos.get(nombre).getTipo();
    if (!tipoVariable.equals(tipoExpresion)) {
      throw new RuntimeException(
          "Error semantico: el tipo de la variable " + nombre + " no coincide con el tipo de la expresion");
    }
  }

  private String validarExpresion() {
    if (tokens.get(idx).getToken() == Constantes.NUMERO) {
      idx++;
      return Constantes.ENT;
    }
    if (tokens.get(idx).getToken() == Constantes.CADENA) {
      idx++;
      return Constantes.CAD;
    }
    if (tokens.get(idx).getToken() == Constantes.DECIMAL) {
      idx++;
      return Constantes.FRACC;
    }
    if (tokens.get(idx).getToken() == Constantes.ID) {
      String nombre = tokens.get(idx).getValor();
      if (!tablaSimbolos.containsKey(nombre)) {
        throw new RuntimeException("Error semantico: la variable " + nombre + " no ha sido declarada");
      }
      idx++;
      return tablaSimbolos.get(nombre).getTipo();
    }
    idx++;
    String tipo1 = validarExpresion();
    idx++;
    String tipo2 = validarExpresion();
    idx++;
    if (tipo1.equals(Constantes.CAD) || tipo2.equals(Constantes.CAD)) {
      throw new RuntimeException("Error semantico: no se pueden realizar operaciones aritmeticas con cadenas");
    }
    if (!tipo1.equals(tipo2)) {
      throw new RuntimeException("Error semantico: los tipos de la expresion no coinciden");
    }
    return tipo1;
  }

  private void validarExpresionComp() {
    idx++;
    idx++;
    String tipoExpresion = validarExpresion();
    idx++;
    String tipoExpresion2 = validarExpresion();
    if (tipoExpresion.equals(Constantes.CAD) || tipoExpresion2.equals(Constantes.CAD)) {
      throw new RuntimeException("Error semantico: no se pueden realizar comparaciones con cadenas");
    }
    if (!tipoExpresion.equals(tipoExpresion2)) {
      throw new RuntimeException("Error semantico: los tipos de la expresion no coinciden");
    }
    idx++;
  }

  private void validarLeer() {
    idx++;
    idx++;
    String nombre = tokens.get(idx).getValor();
    if (!tablaSimbolos.containsKey(nombre)) {
      throw new RuntimeException("Error semantico: la variable " + nombre + " no ha sido declarada");
    }
    idx++;
    idx++;
  }

  private void validarEscribir() {
    idx++;
    idx++;
    if (tokens.get(idx).getToken() == Constantes.CADENA) {
      idx++;
      idx++;
      return;
    }
    String nombre = tokens.get(idx).getValor();
    if (!tablaSimbolos.containsKey(nombre)) {
      throw new RuntimeException("Error semantico: la variable " + nombre + " no ha sido declarada");
    }
    idx++;
    idx++;
  }

  public String getError() {
    return error;
  }
}

package Model.CodigoIntermedio;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Scanner.Token;
import Model.Semantico.Simbolo;
import Model.utils.Constantes;

public class CodigoIntermedio {
  private ArrayList<Token> tokens;
  private HashMap<String, Simbolo> tablaSimbolos;
  String codigoIntermedio;

  public void generar(ArrayList<Token> tokens, HashMap<String, Simbolo> tablaSimbolos) {
    this.tokens = tokens;
    this.tablaSimbolos = tablaSimbolos;
    codigoIntermedio = "";
    generarParteArriba();
    generarData();
    generarCode();
  }

  private void generarParteArriba() {
    codigoIntermedio += "TITLE PROGRAMA\n";
    codigoIntermedio += ".MODEL SMALL\n";
    codigoIntermedio += ".STACK 100H\n";
  }

  private void generarData() {
    codigoIntermedio += ".DATA\n";
    for (Simbolo simbolo : tablaSimbolos.values()) {
      codigoIntermedio += "\t" + simbolo.getNombre() + " " + convertirTipoDato(simbolo.getTipo()) + "\n";
    }
  }

  private String convertirTipoDato(String tipoDato) {
    if (tipoDato.equals(Constantes.ENT)) {
      return "DW ?";
    } else if (tipoDato.equals(Constantes.CAD)) {
      return "DB 255 DUP($)";
    } else {
      return "DD ?";
    }
  }

  private void generarCode() {
    codigoIntermedio += ".CODE\n";
    codigoIntermedio += "MAIN PROC FAR\n";
    codigoIntermedio += "\n";
    codigoIntermedio += "MAIN ENDP\n";
    codigoIntermedio += "END MAIN\n";
  }

  public String getCodigoIntermedio() {
    return codigoIntermedio;
  }
}

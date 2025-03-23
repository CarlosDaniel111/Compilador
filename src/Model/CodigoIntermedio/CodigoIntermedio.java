package Model.CodigoIntermedio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import Model.Scanner.Token;
import Model.Semantico.Simbolo;
import Model.utils.Constantes;

public class CodigoIntermedio {
  private ArrayList<Token> tokens;
  private HashMap<String, Simbolo> tablaSimbolos;
  private int idx;
  private StringBuilder codigoIntermedio;
  private int cntImprimirNumero, cntLeerNumero, cntLeerCadena, cntSino, cntFinSi;
  private Stack<String> pilaIfWhile;

  public void generar(ArrayList<Token> tokens, HashMap<String, Simbolo> tablaSimbolos) {
    this.tokens = tokens;
    this.tablaSimbolos = tablaSimbolos;
    pilaIfWhile = new Stack<>();
    idx = 0;
    cntImprimirNumero = 0;
    cntLeerNumero = 0;
    cntLeerCadena = 0;
    cntSino = 0;
    cntFinSi = 0;
    codigoIntermedio = new StringBuilder();
    generarParteArriba();
    generarData();
    generarCode();
  }

  private void generarParteArriba() {
    agregarLinea("TITLE PROGRAMA");
    agregarLinea(".MODEL SMALL");
    agregarLinea(".386");
    agregarLinea(".STACK 100H");
  }

  private void generarData() {
    codigoIntermedio.append(".DATA\n");
    for (Simbolo simbolo : tablaSimbolos.values()) {
      if (simbolo.getTipo().equals(Constantes.CAD)) {
        agregarLinea(simbolo.getNombre(), "DB", "255 DUP('$')");
      } else if (simbolo.getTipo().equals(Constantes.ENT)) {
        agregarLinea(simbolo.getNombre(), "DW", "?");
      } else {
        agregarLinea(simbolo.getNombre(), "DD", "?");
      }
    }
    agregarLinea("AUX_CAD", "DB", "255 DUP('$')");
  }

  private void generarCode() {
    agregarLinea(".CODE");
    agregarLinea("MAIN PROC FAR");
    agregarLinea("", "MOV", "AX, @DATA");
    agregarLinea("", "MOV", "DS, AX");
    while (idx < tokens.size()) {
      if (Constantes.esTipoDato(tokens.get(idx).getValor())) {
        idx += 3;
      } else if (tokens.get(idx).getToken().equals(Constantes.ID)) {
        generarAsignacion();
      } else if (tokens.get(idx).getValor().equals(Constantes.LEER)) {
        generarLeer();
      } else if (tokens.get(idx).getValor().equals(Constantes.MOSTRAR)) {
        generarMostrar();
      } else if (tokens.get(idx).getValor().equals(Constantes.SI)) {
        generarSi();
      } else if (tokens.get(idx).getToken().equals(Constantes.CORCHETE_CIE)) {
        generarFinBloque();
      } else if (tokens.get(idx).getValor().equals(Constantes.MIENTRAS)) {
        generarMientras();
      } else {
        idx++;
      }
    }
    agregarLinea("", "MOV", "AH, 4CH");
    agregarLinea("", "INT", "21H");
    agregarLinea("MAIN ENDP");
    agregarLinea("END MAIN");
  }

  private void generarAsignacion() {
    // nombre = "Carlos"
    String varDestino = tokens.get(idx).getValor();
    idx++;
    idx++;
    String valor = tokens.get(idx).getValor();
    Simbolo simbolo = tablaSimbolos.get(varDestino);
    if (simbolo.getTipo().equals(Constantes.ENT)) {
      // Expresion (A + B)
      if (tokens.get(idx).getToken().equals(Constantes.PARENTESIS_APE)) {
        idx++;
        String operando1 = tokens.get(idx).getValor();
        idx++;
        String operador = tokens.get(idx).getValor();
        idx++;
        String operando2 = tokens.get(idx).getValor();
        idx++;
        idx++;
        agregarLinea("", "MOV", "AX, " + operando1);
        if (operador.equals("+")) {
          agregarLinea("", "ADD", "AX, " + operando2);
        } else if (operador.equals("-")) {
          agregarLinea("", "SUB", "AX, " + operando2);
        } else if (operador.equals("*")) {
          agregarLinea("", "MOV", "BX, " + operando2);
          agregarLinea("", "MUL", "BX");
        } else {
          agregarLinea("", "MOV", "DX, 0");
          agregarLinea("", "MOV", "BX, " + operando2);
          agregarLinea("", "DIV", "BX");
        }
        agregarLinea("", "MOV", varDestino + ", AX");
      } else {
        if (tokens.get(idx).getToken().equals(Constantes.ID)) {
          // A = B;
          agregarLinea("", "MOV", "AX, " + tokens.get(idx).getValor());
          agregarLinea("", "MOV", varDestino + ", AX");
        } else {
          // A = 5;
          agregarLinea("", "MOV", varDestino + ", " + valor);
        }
        idx++;
      }
    } else {
      // nombre = "Carlos";
      valor = valor.substring(1, valor.length() - 1);
      guardarCadena(varDestino, valor);
      idx++;
    }
  }

  private void guardarCadena(String memoria, String cadena) {
    char[] caracteres = cadena.toCharArray();
    for (int i = 0; i < caracteres.length; i++) {
      agregarLinea("", "MOV", "[" + memoria + " + " + i + "], '" + caracteres[i] + "'");
    }
    agregarLinea("", "MOV", "[" + memoria + " + " + caracteres.length + "], '$'");
  }

  private void generarLeer() {
    idx++;
    idx++;
    String variable = tokens.get(idx).getValor();
    Simbolo simbolo = tablaSimbolos.get(variable);
    if (simbolo.getTipo().equals(Constantes.ENT)) {
      generarLeerNumero(variable);
    } else {
      generarLeerCadena(variable);
    }
    idx++;
    idx++;
  }

  private void generarLeerNumero(String memoria) {
    agregarLinea("", "MOV", "BX,0");
    agregarLinea("LEERNUM" + cntLeerNumero + ":");
    agregarLinea("", "MOV", "AH, 01H");
    agregarLinea("", "INT", "21H");
    agregarLinea("", "CMP", "AL, 0DH");
    agregarLinea("", "JE", "FINLEERNUM" + cntLeerNumero);
    agregarLinea("", "SUB", "AL, 48");
    agregarLinea("", "MOV", "AH, 0");
    agregarLinea("", "MOV", "CX, AX");
    agregarLinea("", "MOV", "AX, 10");
    agregarLinea("", "MUL", "BX");
    agregarLinea("", "MOV", "BX, AX");
    agregarLinea("", "ADD", "BX, CX");
    agregarLinea("", "JMP", "LEERNUM" + cntLeerNumero);
    agregarLinea("FINLEERNUM" + cntLeerNumero + ":");
    agregarLinea("", "MOV", memoria + ", BX");
    cntLeerNumero++;
  }

  private void generarLeerCadena(String memoria) {
    agregarLinea("", "MOV", "SI, 0");
    agregarLinea("LEERCAD" + cntLeerCadena + ":");
    agregarLinea("", "MOV", "AH, 01H");
    agregarLinea("", "INT", "21H");
    agregarLinea("", "CMP", "AL, 0DH");
    agregarLinea("", "JE", "FINLEERCAD" + cntLeerCadena);
    agregarLinea("", "MOV", "[" + memoria + "+SI], AL");
    agregarLinea("", "INC", "SI");
    agregarLinea("", "JMP", "LEERCAD" + cntLeerCadena);
    agregarLinea("FINLEERCAD" + cntLeerCadena + ":");
    agregarLinea("", "MOV", "[" + memoria + "+SI], '$'");
    cntLeerCadena++;
  }

  private void generarMostrar() {
    idx++;
    idx++;
    // Si es una cadena directa
    if (tokens.get(idx).getToken().equals(Constantes.CADENA)) {
      // MOSTRAR("Hola mundo");
      String cadena = tokens.get(idx).getValor();
      cadena = cadena.substring(1, cadena.length() - 1);
      String memoria = "AUX_CAD";
      guardarCadena(memoria, cadena);
      agregarLinea("", "MOV", "AH, 09H");
      agregarLinea("", "LEA", "DX, " + memoria);
      agregarLinea("", "INT", "21H");
    } else {
      String variable = tokens.get(idx).getValor();
      Simbolo simbolo = tablaSimbolos.get(variable);
      if (simbolo.getTipo().equals(Constantes.ENT)) { // Imprimir una variable entera
        generarImprimirNumero(variable);
      } else { // Imprimir una variable cadena
        agregarLinea("", "MOV", "AH, 09H");
        agregarLinea("", "LEA", "DX, " + variable);
        agregarLinea("", "INT", "21H");
      }
    }
    // Salto de linea
    agregarLinea("", "MOV", "AH, 02H");
    agregarLinea("", "MOV", "DL, 0AH");
    agregarLinea("", "INT", "21H");
    agregarLinea("", "MOV", "DL, 0DH");
    agregarLinea("", "INT", "21H");
    idx++;
    idx++;
  }

  private void generarImprimirNumero(String memoria) {
    agregarLinea("", "MOV", "AX, " + memoria);
    agregarLinea("", "MOV", "BX, 10");
    agregarLinea("", "MOV", "CX, 0");
    agregarLinea("PUSHNUM" + cntImprimirNumero + ":");
    agregarLinea("", "MOV", "DX, 0");
    agregarLinea("", "DIV", "BX");
    agregarLinea("", "ADD", "DL, 48");
    agregarLinea("", "PUSH", "DX");
    agregarLinea("", "INC", "CX");
    agregarLinea("", "CMP", "AX, 0");
    agregarLinea("", "JNE", "PUSHNUM" + cntImprimirNumero);

    agregarLinea("MOSTRARNUM" + cntImprimirNumero + ":");
    agregarLinea("", "MOV", "DX, 0");
    agregarLinea("", "POP", "DX");
    agregarLinea("", "MOV", "AH, 02H");
    agregarLinea("", "INT", "21H");
    agregarLinea("", "LOOP", "MOSTRARNUM" + cntImprimirNumero);
    cntImprimirNumero++;
  }

  private void generarSi() {
    idx++;
    idx++;
    String operando1 = tokens.get(idx).getValor();
    idx++;
    String operador = tokens.get(idx).getValor();
    idx++;
    String operando2 = tokens.get(idx).getValor();
    idx++;
    idx++;
    String etiqueta = "SINO" + cntSino;
    cntSino++;
    pilaIfWhile.push(etiqueta);
    agregarLinea("", "MOV", "AX, " + operando1);
    agregarLinea("", "CMP", "AX, " + operando2);
    if (operador.equals("<")) {
      agregarLinea("", "JGE", etiqueta);
    } else if (operador.equals(">")) {
      agregarLinea("", "JLE", etiqueta);
    } else if (operador.equals("<=")) {
      agregarLinea("", "JG", etiqueta);
    } else if (operador.equals(">=")) {
      agregarLinea("", "JL", etiqueta);
    } else if (operador.equals("!=")) {
      agregarLinea("", "JE", etiqueta);
    } else {
      agregarLinea("", "JNE", etiqueta);
    }
    idx++;
  }

  private void generarFinBloque() {
    idx++;
    String etiqueta = pilaIfWhile.pop();
    // viene de un si
    if (etiqueta.startsWith("SINO")) {
      if (tokens.get(idx).getValor().equals(Constantes.SINO)) {
        agregarLinea("", "JMP", "FINSI" + cntFinSi);
        agregarLinea(etiqueta + ":");
        String etiquetaFin = "FINSI" + cntFinSi;
        cntFinSi++;
        pilaIfWhile.push(etiquetaFin);
      } else {
        agregarLinea(etiqueta + ":");
      }
    } else if (etiqueta.startsWith("FINSI")) {
      agregarLinea(etiqueta + ":");
    } else if (etiqueta.startsWith("MIENTRAS")) {
      agregarLinea("", "JMP", etiqueta);
      agregarLinea("FINMIENTRAS" + cntFinSi + ":");
      cntFinSi++;
    }
  }

  private void generarMientras() {
    idx++;
    idx++;
    String operando1 = tokens.get(idx).getValor();
    idx++;
    String operador = tokens.get(idx).getValor();
    idx++;
    String operando2 = tokens.get(idx).getValor();
    idx++;
    idx++;
    String etiqueta = "MIENTRAS" + cntSino;
    cntSino++;
    pilaIfWhile.push(etiqueta);
    agregarLinea(etiqueta + ":");
    agregarLinea("", "MOV", "AX, " + operando1);
    agregarLinea("", "CMP", "AX, " + operando2);
    if (operador.equals("<")) {
      agregarLinea("", "JGE", "FINMIENTRAS" + cntFinSi);
    } else if (operador.equals(">")) {
      agregarLinea("", "JLE", "FINMIENTRAS" + cntFinSi);
    } else if (operador.equals("<=")) {
      agregarLinea("", "JG", "FINMIENTRAS" + cntFinSi);
    } else if (operador.equals(">=")) {
      agregarLinea("", "JL", "FINMIENTRAS" + cntFinSi);
    } else if (operador.equals("!=")) {
      agregarLinea("", "JE", "FINMIENTRAS" + cntFinSi);
    } else {
      agregarLinea("", "JNE", "FINMIENTRAS" + cntFinSi);
    }
    idx++;
  }

  private void agregarLinea(String p1) {
    agregarLinea(p1, "");
  }

  private void agregarLinea(String p1, String p2) {
    agregarLinea(p1, p2, "");
  }

  private void agregarLinea(String p1, String p2, String p3) {
    codigoIntermedio.append(String.format("%-15s %-4s %-22s\n", p1, p2, p3));
  }

  public String getCodigoIntermedio() {
    return codigoIntermedio.toString();
  }
}

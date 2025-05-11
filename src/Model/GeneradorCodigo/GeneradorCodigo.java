package Model.GeneradorCodigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import Model.Scanner.Token;
import Model.Semantico.Simbolo;
import Model.utils.Constantes;
import Model.utils.Convertidor;

public class GeneradorCodigo {
  private ArrayList<Token> tokens;
  private HashMap<String, Simbolo> tablaSimbolos;
  private int idx;
  private StringBuilder codigoIntermedio;
  private int cntImprimirNumero, cntLeerNumero, cntLeerCadena, cntSino, cntFinSi;
  private Stack<String> pilaIfWhile;

  private int direccionData, direccionCodigo;
  private StringBuilder codigoObjeto;
  private HashMap<String, Integer> memoriaADireccion;

  public void generar(ArrayList<Token> tokens, HashMap<String, Simbolo> tablaSimbolos) {
    this.tokens = tokens;
    this.tablaSimbolos = tablaSimbolos;
    pilaIfWhile = new Stack<>();
    idx = 0;
    direccionData = 0;
    direccionCodigo = 0;
    cntImprimirNumero = 0;
    cntLeerNumero = 0;
    cntLeerCadena = 0;
    cntSino = 0;
    cntFinSi = 0;
    codigoIntermedio = new StringBuilder();
    codigoObjeto = new StringBuilder();
    memoriaADireccion = new HashMap<>();
    generarParteArriba();
    generarData();
    generarCode();
  }

  private void generarParteArriba() {
    codigoObjeto.append("          Direccion           |          Codigo\n");
    agregarLinea("TITLE PROGRAMA");
    agregarLinea(".MODEL SMALL");
    agregarLinea(".386");
    agregarLinea(".STACK 100H");
  }

  private void generarData() {
    codigoIntermedio.append(".DATA\n");
    codigoObjeto.append(".DATA\n");
    for (Simbolo simbolo : tablaSimbolos.values()) {
      if (simbolo.getTipo().equals(Constantes.CAD)) {
        agregarLinea(simbolo.getNombre(), "DB", "255 DUP('$')");
        memoriaADireccion.put(simbolo.getNombre(), direccionData);
        for (int i = 0; i < 255; i++) {
          agregarBinarioData(Convertidor.decToBin(36, 8, false));
        }
      } else if (simbolo.getTipo().equals(Constantes.ENT)) {
        agregarLinea(simbolo.getNombre(), "DW", "?");
        memoriaADireccion.put(simbolo.getNombre(), direccionData);
        agregarBinarioData("0000 0000 0000 0000");
      } else {
        agregarLinea(simbolo.getNombre(), "DD", "?");
        memoriaADireccion.put(simbolo.getNombre(), direccionData);
        agregarBinarioData("0000 0000 0000 0000 0000 0000 0000 0000");
      }
    }
    agregarLinea("AUX_CAD", "DB", "255 DUP('$')");
    memoriaADireccion.put("AUX_CAD", direccionData);
    for (int i = 0; i < 255; i++) {
      agregarBinarioData(Convertidor.decToBin(36, 8, false));
    }
    codigoObjeto.append("\n");
  }

  private void generarCode() {
    agregarLinea(".CODE");
    codigoObjeto.append(".CODE\n");
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
    agregarBinarioCodigo("1011 0100 0100 1100");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
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
        if (Character.isDigit(operando1.charAt(0))) {
          agregarBinarioCodigo("1011 1000 " + Convertidor.decToBin(Integer.parseInt(operando1), 16, true));
        } else {
          agregarBinarioCodigo(
              "1000 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(operando1), 16, false));
        }
        if (operador.equals("+")) {
          agregarLinea("", "ADD", "AX, " + operando2);
          if (Character.isDigit(operando2.charAt(0))) {
            agregarBinarioCodigo("0000 0101 " + Convertidor.decToBin(Integer.parseInt(operando2), 16, true));
          } else {
            agregarBinarioCodigo("0000 0011 0000 0110 "
                + Convertidor.decToBin(memoriaADireccion.get(operando2), 16, false));
          }
        } else if (operador.equals("-")) {
          agregarLinea("", "SUB", "AX, " + operando2);
          if (Character.isDigit(operando2.charAt(0))) {
            agregarBinarioCodigo("0010 1101 " + Convertidor.decToBin(Integer.parseInt(operando2), 16, true));
          } else {
            agregarBinarioCodigo("0010 1001 0000 0110 "
                + Convertidor.decToBin(memoriaADireccion.get(operando2), 16, false));
          }
        } else if (operador.equals("*")) {
          agregarLinea("", "MOV", "BX, " + operando2);
          if (Character.isDigit(operando1.charAt(0))) {
            agregarBinarioCodigo("1011 1011 " + Convertidor.decToBin(Integer.parseInt(operando1), 16, true));
          } else {
            agregarBinarioCodigo(
                "1000 1011 0001 1110 " + Convertidor.decToBin(memoriaADireccion.get(operando1), 16, false));
          }
          agregarLinea("", "MUL", "BX");
          agregarBinarioCodigo("1111 0111 1110 0011");
        } else {
          agregarLinea("", "MOV", "DX, 0");
          agregarBinarioCodigo("1011 1010 0000 0000 0000 0000");
          agregarLinea("", "MOV", "BX, " + operando2);
          if (Character.isDigit(operando1.charAt(0))) {
            agregarBinarioCodigo("1011 1011 " + Convertidor.decToBin(Integer.parseInt(operando1), 16, true));
          } else {
            agregarBinarioCodigo(
                "1000 1011 0001 1110 " + Convertidor.decToBin(memoriaADireccion.get(operando1), 16, false));
          }
          agregarLinea("", "DIV", "BX");
          agregarBinarioCodigo("1111 0111 1111 0011");
        }
        agregarLinea("", "MOV", varDestino + ", AX");
        agregarBinarioCodigo(
            "1000 1001 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(varDestino), 16, false));
      } else {
        if (tokens.get(idx).getToken().equals(Constantes.ID)) {
          // A = B;
          String varOrigen = tokens.get(idx).getValor();
          agregarLinea("", "MOV", "AX, " + varOrigen);
          agregarBinarioCodigo(
              "1000 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(varOrigen), 16, false));
          agregarLinea("", "MOV", varDestino + ", AX");
          agregarBinarioCodigo(
              "1000 1001 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(varDestino), 16, false));
        } else {
          // A = 5;
          agregarLinea("", "MOV", varDestino + ", " + valor);
          agregarBinarioCodigo(
              "1100 0111 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(varDestino), 16, false) + " "
                  + Convertidor.decToBin(Integer.parseInt(valor), 16, true));
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
      agregarBinarioCodigo("1100 0110 0000 0110 "
          + Convertidor.decToBin(memoriaADireccion.get(memoria) + i, 16, false) + " "
          + Convertidor.decToBin((int) caracteres[i], 8, true));
    }
    agregarLinea("", "MOV", "[" + memoria + " + " + caracteres.length + "], '$'");
    agregarBinarioCodigo("1100 0110 0000 0110 "
        + Convertidor.decToBin(memoriaADireccion.get(memoria) + caracteres.length, 16, false) + " "
        + Convertidor.decToBin((int) '$', 8, true));
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
    agregarBinarioCodigo("1011 1011 0000 0000 0000 0000");
    agregarLinea("LEERNUM" + cntLeerNumero + ":");
    agregarLinea("", "MOV", "AH, 01H");
    agregarBinarioCodigo("1011 0100 0000 0001");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
    agregarLinea("", "CMP", "AL, 0DH");
    agregarBinarioCodigo("0011 1100 0000 1101");
    agregarLinea("", "JE", "FINLEERNUM" + cntLeerNumero);
    agregarBinarioCodigo("0111 0100 0001 0000");
    agregarLinea("", "SUB", "AL, 48");
    agregarBinarioCodigo("0010 1100 " + Convertidor.decToBin(48, 8, true));
    agregarLinea("", "MOV", "AH, 0");
    agregarBinarioCodigo("1011 0100 0000 0000");
    agregarLinea("", "MOV", "CX, AX");
    agregarBinarioCodigo("1000 1011 1100 0001");
    agregarLinea("", "MOV", "AX, 10");
    agregarBinarioCodigo("1011 1000 0000 1010");
    agregarLinea("", "MUL", "BX");
    agregarBinarioCodigo("1111 0111 1110 0011");
    agregarLinea("", "MOV", "BX, AX");
    agregarBinarioCodigo("1000 1011 1100 0011");
    agregarLinea("", "ADD", "BX, CX");
    agregarBinarioCodigo("0000 0011 1100 1011");
    agregarLinea("", "JMP", "LEERNUM" + cntLeerNumero);
    agregarBinarioCodigo("1110 1011 1001 0110");
    agregarLinea("FINLEERNUM" + cntLeerNumero + ":");
    agregarLinea("", "MOV", memoria + ", BX");
    agregarBinarioCodigo("1000 1001 0001 1110 " + Convertidor.decToBin(memoriaADireccion.get(memoria), 16, false));
    cntLeerNumero++;
  }

  private void generarLeerCadena(String memoria) {
    agregarLinea("", "MOV", "SI, 0");
    agregarBinarioCodigo("1011 1110 0000 0000");
    agregarLinea("LEERCAD" + cntLeerCadena + ":");
    agregarLinea("", "MOV", "AH, 01H");
    agregarBinarioCodigo("1011 0100 0000 0001");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
    agregarLinea("", "CMP", "AL, 0DH");
    agregarBinarioCodigo("0011 1100 0000 1101");
    agregarLinea("", "JE", "FINLEERCAD" + cntLeerCadena);
    agregarBinarioCodigo("0111 0100 0000 0111");
    agregarLinea("", "MOV", "[" + memoria + "+SI], AL");
    agregarBinarioCodigo("1000 1000 1000 0100 " + Convertidor.decToBin(memoriaADireccion.get(memoria) + 1, 16, false));
    agregarLinea("", "INC", "SI");
    agregarBinarioCodigo("0100 0110");
    agregarLinea("", "JMP", "LEERCAD" + cntLeerCadena);
    agregarBinarioCodigo("1110 1011 1000 1011");
    agregarLinea("FINLEERCAD" + cntLeerCadena + ":");
    agregarLinea("", "MOV", "[" + memoria + "+SI], '$'");
    agregarBinarioCodigo("1100 0110 1000 0100 " + Convertidor.decToBin(memoriaADireccion.get(memoria) + 1, 16, false) +
        " " + Convertidor.decToBin((int) '$', 8, true));
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
      agregarBinarioCodigo("1011 0100 0000 1001");
      agregarLinea("", "LEA", "DX, " + memoria);
      agregarBinarioCodigo("1000 1101 0001 0110 " + Convertidor.decToBin(memoriaADireccion.get(memoria), 16, false));
      agregarLinea("", "INT", "21H");
      agregarBinarioCodigo("1100 1101 0010 0001");
    } else {
      String variable = tokens.get(idx).getValor();
      Simbolo simbolo = tablaSimbolos.get(variable);
      if (simbolo.getTipo().equals(Constantes.ENT)) { // Imprimir una variable entera
        generarImprimirNumero(variable);
      } else { // Imprimir una variable cadena
        agregarLinea("", "MOV", "AH, 09H");
        agregarBinarioCodigo("1011 0100 0000 1001");
        agregarLinea("", "LEA", "DX, " + variable);
        agregarBinarioCodigo("1000 1101 0001 0110 " + Convertidor.decToBin(memoriaADireccion.get(variable), 16, false));
        agregarLinea("", "INT", "21H");
        agregarBinarioCodigo("1100 1101 0010 0001");
      }
    }
    // Salto de linea
    agregarLinea("", "MOV", "AH, 02H");
    agregarBinarioCodigo("1011 0010 0000 0010");
    agregarLinea("", "MOV", "DL, 0AH");
    agregarBinarioCodigo("1101 1010 0000 1010");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
    agregarLinea("", "MOV", "DL, 0DH");
    agregarBinarioCodigo("1101 1010 0000 1101");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
    idx++;
    idx++;
  }

  private void generarImprimirNumero(String memoria) {
    agregarLinea("", "MOV", "AX, " + memoria);
    agregarBinarioCodigo("1000 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(memoria), 16, false));
    agregarLinea("", "MOV", "BX, 10");
    agregarBinarioCodigo("1011 1011 0000 1010");
    agregarLinea("", "MOV", "CX, 0");
    agregarBinarioCodigo("1011 1001 0000 0000");
    agregarLinea("PUSHNUM" + cntImprimirNumero + ":");
    agregarLinea("", "MOV", "DX, 0");
    agregarBinarioCodigo("1011 1010 0000 0000");
    agregarLinea("", "DIV", "BX");
    agregarBinarioCodigo("1111 0111 1111 0011");
    agregarLinea("", "ADD", "DL, 48");
    agregarBinarioCodigo("1000 0010 1100 0010 " + Convertidor.decToBin(48, 8, true));
    agregarLinea("", "PUSH", "DX");
    agregarBinarioCodigo("0101 0010");
    agregarLinea("", "INC", "CX");
    agregarBinarioCodigo("0100 0001");
    agregarLinea("", "CMP", "AX, 0");
    agregarBinarioCodigo("0011 1101 0000 0000");
    agregarLinea("", "JNE", "PUSHNUM" + cntImprimirNumero);
    agregarBinarioCodigo("0111 0101 1000 1011");

    agregarLinea("MOSTRARNUM" + cntImprimirNumero + ":");
    agregarLinea("", "MOV", "DX, 0");
    agregarBinarioCodigo("1011 1010 0000 0000");
    agregarLinea("", "POP", "DX");
    agregarBinarioCodigo("0101 1010");
    agregarLinea("", "MOV", "AH, 02H");
    agregarBinarioCodigo("1011 0010 0000 0010");
    agregarLinea("", "INT", "21H");
    agregarBinarioCodigo("1100 1101 0010 0001");
    agregarLinea("", "LOOP", "MOSTRARNUM" + cntImprimirNumero);
    agregarBinarioCodigo("1110 0010 1000 0111");
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
    if (Character.isDigit(operando1.charAt(0))) {
      agregarBinarioCodigo("1011 1000 " + Convertidor.decToBin(Integer.parseInt(operando1), 16, true));
    } else {
      agregarBinarioCodigo(
          "1000 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(operando1), 16, false));
    }
    agregarLinea("", "CMP", "AX, " + operando2);
    if (Character.isDigit(operando2.charAt(0))) {
      agregarBinarioCodigo("0011 1101 " + Convertidor.decToBin(Integer.parseInt(operando2), 16, true));
    } else {
      agregarBinarioCodigo("0011 1011 0000 0110 " +
          Convertidor.decToBin(memoriaADireccion.get(operando2), 16, false));
    }
    if (operador.equals("<")) {
      agregarLinea("", "JGE", etiqueta);
      agregarBinarioCodigo("0111 1101 0000 0000");
    } else if (operador.equals(">")) {
      agregarLinea("", "JLE", etiqueta);
      agregarBinarioCodigo("0111 1110 0000 0000");
    } else if (operador.equals("<=")) {
      agregarLinea("", "JG", etiqueta);
      agregarBinarioCodigo("0111 1111 0000 0000");
    } else if (operador.equals(">=")) {
      agregarLinea("", "JL", etiqueta);
      agregarBinarioCodigo("0111 1100 0000 0000");
    } else if (operador.equals("!=")) {
      agregarLinea("", "JE", etiqueta);
      agregarBinarioCodigo("0111 0100 0000 0000");
    } else {
      agregarLinea("", "JNE", etiqueta);
      agregarBinarioCodigo("0111 0101 0000 0000");
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
        agregarBinarioCodigo("1110 1011 0000 0000");
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
      agregarBinarioCodigo("1110 1011 0000 0000");
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
    if (Character.isDigit(operando1.charAt(0))) {
      agregarBinarioCodigo("1011 1000 " + Convertidor.decToBin(Integer.parseInt(operando1), 16, true));
    } else {
      agregarBinarioCodigo(
          "1000 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(operando1), 16, false));
    }
    agregarLinea("", "CMP", "AX, " + operando2);
    if (Character.isDigit(operando1.charAt(0))) {
      agregarBinarioCodigo("0011 1101 " + Convertidor.decToBin(Integer.parseInt(operando2), 16, true));
    } else {
      agregarBinarioCodigo(
          "0011 1011 0000 0110 " + Convertidor.decToBin(memoriaADireccion.get(operando2), 16, false));
    }
    if (operador.equals("<")) {
      agregarLinea("", "JGE", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 1101 0000 0000");
    } else if (operador.equals(">")) {
      agregarLinea("", "JLE", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 1110 0000 0000");
    } else if (operador.equals("<=")) {
      agregarLinea("", "JG", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 1111 0000 0000");
    } else if (operador.equals(">=")) {
      agregarLinea("", "JL", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 1100 0000 0000");
    } else if (operador.equals("!=")) {
      agregarLinea("", "JE", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 0100 0000 0000");
    } else {
      agregarLinea("", "JNE", "FINMIENTRAS" + cntFinSi);
      agregarBinarioCodigo("0111 0101 0000 0000");
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

  private void agregarBinarioData(String binario) {
    StringBuilder binarioData = new StringBuilder();
    String binarioDireccion = Convertidor.decToBin(direccionData, 16, false);
    binarioData.append(binarioDireccion);
    binarioData.append(" | ");
    binarioData.append(binario);
    binarioData.append("\n");
    direccionData += (binario.length() + 1) / 10;
    codigoObjeto.append(binarioData);
  }

  private void agregarBinarioCodigo(String binario) {
    StringBuilder binarioCodigo = new StringBuilder();
    String binarioDireccion = Convertidor.decToBin(direccionCodigo, 16, false);
    binarioCodigo.append(binarioDireccion);
    binarioCodigo.append(" | ");
    binarioCodigo.append(binario);
    binarioCodigo.append("\n");
    direccionCodigo += (binario.length() + 1) / 10;
    codigoObjeto.append(binarioCodigo);
  }

  public String getCodigoIntermedio() {
    return codigoIntermedio.toString();
  }

  public String getCodigoObjeto() {
    return codigoObjeto.toString();
  }
}

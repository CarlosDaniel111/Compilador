package Model.Scanner;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import Model.utils.Constantes;

public class Scanner {
  private ArrayList<Token> tokens;
  private BufferedReader in;
  private String errores;
  private int nlinea;
  private char c;

  public void analizar(String texto) {
    in = new BufferedReader(new StringReader(texto));
    tokens = new ArrayList<>();
    errores = "";
    nlinea = 1;
    try {
      c = (char) in.read();
      while (c != (char) -1) {
        if (c == '\n') {
          nlinea++;
          c = (char) in.read();
          continue;
        }
        if (c == ' ' || c == '\t' || c == '\r') {
          c = (char) in.read();
          continue;
        }
        if (Character.isDigit(c)) {
          checarNumero();
        } else if (Character.isLetter(c)) {
          checarIdentificador();
        } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
          checarOpAritmetico();
        } else if (c == '<' || c == '>' || c == '=' || c == '!') {
          checarOpRelacional();
        } else if (c == '(' || c == ')' || c == '[' || c == ']' || c == ';') {
          checarSimbolo();
        } else if (c == '\'') {
          checarCadena();
        } else {
          errores += "Error lexico en la linea " + nlinea + ": caracter invalido\n";
          tokens.add(new Token("Invalido", String.valueOf(c), nlinea));
          c = (char) in.read();
        }
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private void checarNumero() throws Exception {
    String num = "";
    while (Character.isDigit(c)) {
      num += c;
      c = (char) in.read();
    }
    if (Character.isLetter(c)) {
      errores += "Error lexico en la linea " + nlinea + ": caracter invalido\n";
      return;
    }
    if (c == '.') {
      num += c;
      c = (char) in.read();
      if (!Character.isDigit(c)) {
        errores += "Error lexico en la linea " + nlinea + ": caracter invalido\n";
        return;
      }
      while (Character.isDigit(c)) {
        num += c;
        c = (char) in.read();
      }
      tokens.add(new Token(Constantes.DECIMAL, num, nlinea));
    } else {
      tokens.add(new Token(Constantes.NUMERO, num, nlinea));
    }
  }

  private void checarIdentificador() throws Exception {
    String id = "";
    while (Character.isLetterOrDigit(c)) {
      id += c;
      c = (char) in.read();
    }
    if (Constantes.esPalabraReservada(id)) {
      tokens.add(new Token("PR", id, nlinea));
    } else {
      tokens.add(new Token(Constantes.ID, id, nlinea));
    }
  }

  private void checarOpRelacional() throws Exception {
    String op = "";
    op += c;
    c = (char) in.read();
    if (op.equals("<")) {
      if (c == '=') {
        op += c;
        c = (char) in.read();
        tokens.add(new Token(Constantes.MENOR_IGUAL, op, nlinea));
      } else {
        tokens.add(new Token(Constantes.MENOR, op, nlinea));
      }
    } else if (op.equals(">")) {
      if (c == '=') {
        op += c;
        c = (char) in.read();

        tokens.add(new Token(Constantes.MAYOR_IGUAL, op, nlinea));
      } else {
        tokens.add(new Token(Constantes.MAYOR, op, nlinea));
      }
    } else if (op.equals("=")) {
      if (c == '=') {
        op += c;
        c = (char) in.read();

        tokens.add(new Token(Constantes.IGUAL, op, nlinea));
      } else {
        tokens.add(new Token(Constantes.ASIGNACION, op, nlinea));
      }
    } else if (op.equals("!")) {
      if (c == '=') {
        op += c;
        c = (char) in.read();
        tokens.add(new Token(Constantes.DIFERENTE, op, nlinea));
      } else {
        errores += "Error lexico en la linea " + nlinea + ": caracter invalido\n";
      }
    }
  }

  private void checarOpAritmetico() throws Exception {
    String op = "";
    op += c;
    c = (char) in.read();

    if (op.equals("+")) {
      tokens.add(new Token(Constantes.MAS, op, nlinea));
    } else if (op.equals("-")) {
      tokens.add(new Token(Constantes.MENOS, op, nlinea));
    } else if (op.equals("*")) {
      tokens.add(new Token(Constantes.MULTIPLICACION, op, nlinea));
    } else if (op.equals("/")) {
      tokens.add(new Token(Constantes.DIVISION, op, nlinea));
    }
  }

  private void checarSimbolo() throws Exception {
    String simbolo = "";
    simbolo += c;
    c = (char) in.read();

    if (simbolo.equals("(")) {
      tokens.add(new Token(Constantes.PARENTESIS_APE, simbolo, nlinea));
    } else if (simbolo.equals(")")) {
      tokens.add(new Token(Constantes.PARENTESIS_CIE, simbolo, nlinea));
    } else if (simbolo.equals("[")) {
      tokens.add(new Token(Constantes.CORCHETE_APE, simbolo, nlinea));
    } else if (simbolo.equals("]")) {
      tokens.add(new Token(Constantes.CORCHETE_CIE, simbolo, nlinea));
    } else if (simbolo.equals(";")) {
      tokens.add(new Token(Constantes.PUNTO_Y_COMA, simbolo, nlinea));
    }
  }

  private void checarCadena() throws Exception {
    String cadena = "";
    cadena += c;
    c = (char) in.read();

    while (c != '\'') {
      cadena += c;
      c = (char) in.read();

    }
    cadena += c;
    c = (char) in.read();

    tokens.add(new Token(Constantes.CADENA, cadena, nlinea));
  }

  public String getErrores() {
    return errores;
  }

  public ArrayList<Token> getTokensArray() {
    return tokens;
  }

  public String getTokens() {
    String texto = "";
    for (Token token : tokens) {
      texto += token + "\n";
    }
    return texto;
  }
}

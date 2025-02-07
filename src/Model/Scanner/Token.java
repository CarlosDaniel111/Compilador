package Model.Scanner;

public class Token {
  private String token, valor;
  private int linea, columna;

  public Token(String token, String valor, int linea, int columna) {
    this.token = token;
    this.valor = valor;
    this.linea = linea;
    this.columna = columna;
  }

  public String getToken() {
    return token;
  }

  public String getValor() {
    return valor;
  }

  @Override
  public String toString() {
    return token + " -> " + valor;
  }
}

package Model.Scanner;

public class Token {
  private String token, valor;
  private int linea;

  public Token(String token, String valor, int linea) {
    this.token = token;
    this.valor = valor;
    this.linea = linea;
  }

  public String getToken() {
    return token;
  }

  public String getValor() {
    return valor;
  }

  public int getLinea() {
    return linea;
  }

  @Override
  public String toString() {
    return token + " -> " + valor;
  }
}

package Model.utils;

public class Constantes {
  public static final String NUMERO = "Numero";
  public static final String DECIMAL = "Decimal";
  public static final String ID = "ID";
  public static final String MAS = "Mas";
  public static final String MENOS = "Menos";
  public static final String MULTIPLICACION = "Multiplicacion";
  public static final String DIVISION = "Division";
  public static final String CADENA = "Cadena";
  public static final String MENOR = "Menor";
  public static final String MENOR_IGUAL = "Menor Igual";
  public static final String MAYOR = "Mayor";
  public static final String MAYOR_IGUAL = "Mayor Igual";
  public static final String IGUAL = "Igual";
  public static final String DIFERENTE = "Diferente";
  public static final String ASIGNACION = "Asignacion";
  public static final String PARENTESIS_APE = "Parentesis Apertura";
  public static final String PARENTESIS_CIE = "Parentesis Cierre";
  public static final String CORCHETE_APE = "Corchete Apertura";
  public static final String CORCHETE_CIE = "Corchete Cierre";
  public static final String PUNTO_Y_COMA = "Punto y Coma";

  // Palabras reservadas
  public static final String ITC = "ITC";
  public static final String TECNM = "TECNM";
  public static final String ENT = "ent";
  public static final String FRACC = "fracc";
  public static final String CAD = "cad";
  public static final String SI = "si";
  public static final String SINO = "sino";
  public static final String MIENTRAS = "mientras";
  public static final String LEER = "LEER";
  public static final String MOSTRAR = "MOSTRAR";

  public static boolean esOperadorAritmetico(String token) {
    return token.equals(MAS) || token.equals(MENOS) || token.equals(MULTIPLICACION) || token.equals(DIVISION);
  }

  public static boolean esOperadorRelacional(String token) {
    return token.equals(MENOR) || token.equals(MENOR_IGUAL) || token.equals(MAYOR) || token.equals(MAYOR_IGUAL)
        || token.equals(IGUAL) || token.equals(DIFERENTE);
  }

  public static boolean esSimbolo(String token) {
    return token.equals(PARENTESIS_APE) || token.equals(PARENTESIS_CIE) || token.equals(CORCHETE_CIE)
        || token.equals(CORCHETE_APE) || token.equals(PUNTO_Y_COMA);
  }

  public static boolean esTipoDato(String token) {
    return token.equals(CAD) || token.equals(ENT) || token.equals(FRACC);
  }

  public static boolean esPalabraReservada(String token) {
    return esTipoDato(token) || token.equals(ITC) || token.equals(TECNM) || token.equals(SI) || token.equals(SINO)
        || token.equals(MIENTRAS) || token.equals(LEER) || token.equals(MOSTRAR);
  }
}
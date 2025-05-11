package Model.utils;

public class Convertidor {
  public static String decToBin(int n, int len, boolean littleEndian) {
    StringBuilder binario = new StringBuilder();
    while (n > 0) {
      binario.insert(0, n % 2);
      n /= 2;
    }
    while (binario.length() < len) {
      binario.insert(0, "0");
    }
    StringBuilder binarioConEspacios = new StringBuilder();
    for (int i = 0; i < binario.length(); i++) {
      binarioConEspacios.append(binario.charAt(i));
      if ((i + 1) % 4 == 0 && i != binario.length() - 1) {
        binarioConEspacios.append(" ");
      }
    }

    if (littleEndian) {
      String[] partes = binarioConEspacios.toString().split(" ");
      StringBuilder binarioLittleEndian = new StringBuilder();
      for (int i = partes.length - 1; i >= 0; i -= 2) {
        binarioLittleEndian.append(partes[i - 1]);
        binarioLittleEndian.append(" ");
        binarioLittleEndian.append(partes[i]);
        if (i != 1) {
          binarioLittleEndian.append(" ");
        }
      }
      return binarioLittleEndian.toString();
    }
    return binarioConEspacios.toString();
  }
}

package Model.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Archivo {

  private String ruta;

  public Archivo(String ruta) {
    this.ruta = ruta;
  }

  public String leerArchivo() {
    String texto = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(ruta));
      while (br.ready()) {
        texto += br.readLine() + "\n";
      }
      br.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return texto;
  }

  public void escribirArchivo(String texto) {
    try {
      FileWriter fw = new FileWriter(ruta + ".txt");
      fw.write(texto);
      fw.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
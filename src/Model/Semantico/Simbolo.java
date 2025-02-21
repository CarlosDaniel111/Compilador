package Model.Semantico;

public class Simbolo {
  private String nombre;
  private String tipo;

  public Simbolo(String nombre, String tipo) {
    this.nombre = nombre;
    this.tipo = tipo;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTipo() {
    return tipo;
  }

}

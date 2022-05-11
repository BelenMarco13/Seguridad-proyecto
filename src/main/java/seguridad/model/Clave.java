package seguridad.model;

public class Clave {
    private String clave;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Clave{" +
                "clave='" + clave + '\'' +
                '}';
    }
}

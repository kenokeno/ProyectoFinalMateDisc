/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

/**
 *
 * @author alfredo
 */
public class Pila {

    private ListaSE lista;
    private String nombre;

    /**
     *
     */
    public Pila() {
        this.nombre = "";
        lista = new ListaSE();
    }

    /**
     * @param nombre
     */
    public Pila(String nombre) {
        super();
        this.nombre = nombre;
        lista = new ListaSE();
    }

    /**
     * @param nombre
     */
    public Pila(String nombre, ListaSE lista) {
        this.lista = (ListaSE) lista.clone();
        this.nombre = nombre;
    }

    /**
     * @param pila
     */
    public Pila(Pila pila) {
        this(pila.getNombre(), pila.getLista());
    }

    /**
     * @param obj
     * @return
     */
    public boolean PUSH(Object objeto) {
        return lista.agregarPrincipio(objeto);
    }

    /**
     * @return (Objetc) Regresa el ultimo elemento que ingreso a la estrcutura
     * de Pila
     */
    public Object POP() {
        return lista.removerPrimero();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clon() {
        Object objeto = null;
        try {
            objeto = nuevaPila(this);
        } catch (Exception ex) {
            System.out.println("No se puede duplicar: " + ex.getStackTrace());
        }
        return objeto;
    }

    /**
     * @return
     */
    public boolean estaVacia() {
        if (lista.tamano() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param cola
     * @return
     */
    public Object siguientePOP() {
        return lista.obtener(0);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param cola
     * @return
     */
    public static Pila nuevaPila(Pila pila) {
        return (new Pila(pila.getNombre(), pila.getLista()));
    }

    /**
     * @return the lista
     */
    private ListaSE getLista() {
        return lista;
    }
}

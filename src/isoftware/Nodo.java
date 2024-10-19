/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

/**
 *
 * @author alfredo
 */
public class Nodo {
    private Object dato;
    private Nodo siguiente;
    private Nodo anterior;

    public Nodo(){}

    public Nodo(Object d){
        dato = d;
    }

	/**
	 * @return the dato
	 */
	public Object getDato() {
		return dato;
	}

	/**
	 * @param dato the dato to set
	 */
	public void setDato(Object dato) {
		this.dato = dato;
	}

	/**
	 * @return the siguiente
	 */
	public Nodo getSiguiente() {
		return siguiente;
	}

	/**
	 * @param siguiente the siguiente to set
	 */
	public void setSiguiente(Nodo siguiente) {
		this.siguiente = siguiente;
	}

	/**
	 * @return the anterior
	 */
	public Nodo getAnterior() {
		return anterior;
	}

	/**
	 * @param anterior the anterior to set
	 */
	public void setAnterior(Nodo anterior) {
		this.anterior = anterior;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("No se puede duplicar: " + ex.getStackTrace());
        }
        return obj;
    }
}

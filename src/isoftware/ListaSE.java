/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

/**
 *
 * @author alfredo
 */
public class ListaSE implements Cloneable{

	private Nodo p = null;
	/**
	 * 
	 */
	public ListaSE() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x
	 */
	public ListaSE(Object x) {
		p = new Nodo(x);
		p.setSiguiente(null);
	}

	/**
	 * @param x
	 * @return
	 */
	public boolean agregar(Object x) {
		if (p == null) {
			p = new Nodo(x);
		} else {
			Nodo q = new Nodo(x);
			q.setSiguiente(p);
			p = q;
			q = p.getSiguiente();
		}
		return true;
	}

	/**
	 * @param i
	 * @param dato
	 * @return
	 */
	public boolean agregar(int i, Object dato) {
		if (p == null) {
			p = new Nodo(dato);
			return true;
		}
		if (i >= 0 && i <= tamano()) {
			Nodo q = new Nodo(dato);
			Nodo anterior = p;
			Nodo actual = p;
			for (int j = 0; j < i; j++) {
				anterior = actual;
				actual = anterior.getSiguiente();
			}
			if (i == 0) {
				q.setSiguiente(p);
				p = q;
				return true;
			}
			if (anterior == actual) {
				q.setSiguiente(p);
				p = q;
			} else {
				anterior.setSiguiente(q);
				q.setSiguiente(actual);
			}
			return true;
		} else {
			System.out.println("Indice fuera de l√≠mites.");
		}

		return false;
	}

	public void mostrarTodo() {
		Nodo q = new Nodo();
		q = p;
		while (q != null) {
			System.out.println(q.getDato());
			q = q.getSiguiente();
		}
	}

	/**
	 * @param i
	 * @return
	 */
	public Object obtener(int i) {
		if (p == null) {
			return null;
		}
		Nodo q = new Nodo();
		q = p;
		if (i >= 0) {
			for (int j = 0; j < i && q != null; j++)
				q = q.getSiguiente();
			if (q != null) {
				return q.getDato();
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	public int tamano() {
		Nodo q = new Nodo();
		q = p;
		int tamaÔøΩo = 0;
		while (q != null) {
			tamaÔøΩo++;
			q = q.getSiguiente();
		}
		return tamaÔøΩo;
	}

	/**
	 * @param i
	 * @return
	 */
	public Object eliminar(int i) {
		if (i >= 0 && i < tamano()) {
			Nodo anterior = p;
			Nodo actual = p;
			for (int j = 0; j < i; j++) {
				anterior = actual;
				actual = anterior.getSiguiente();
			}
			if (actual == p)
				p = p.getSiguiente();
			else
				anterior.setSiguiente(actual.getSiguiente());
			return actual.getDato();
		}
		return null;
	}

	/**
	 * @param dato
	 * @return
	 */
	public boolean agregarPrincipio(Object dato) {
		return agregar(0, dato);
	}

	/**
	 * @param dato
	 * @return
	 */
	public boolean agregarUltimo(Object dato) {
		if(p==null)
			return agregar(0,dato);
		else
			return agregar(tamano(), dato);
	}

	/**
	 * @return
	 */
	public Object removerPrimero() {
		return eliminar(0);
	}

	/**
	 * @return
	 */
	public Object removerUltimo() {
		return eliminar(tamano() - 1);
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
package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
	String s = "";
	Nodo nodo = cabeza;

	while(nodo != null){
	    s += nodo.elemento.toString() + ",";
	    nodo = nodo.siguiente;
	}
	return s;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException ("Elemento no válido.");

	Nodo nodo = new Nodo(elemento);

	if(rabo == null){
	    cabeza = nodo;
	    rabo = nodo;
	    return;
	}
	rabo.siguiente = nodo;
	rabo = nodo;
    }
}

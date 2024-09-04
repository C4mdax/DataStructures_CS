package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
	String s = "";
	Nodo nodo = cabeza;

	while(nodo != null){
	    s += nodo.elemento.toString() + "\n";
	    nodo = nodo.siguiente;
	}
	return s;   	
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
	if (elemento == null)
            throw new IllegalArgumentException("El elemento no es válido.");

        Nodo nodo = new Nodo(elemento);
	
        if (esVacia())
            cabeza = rabo = nodo;
        else {
            nodo.siguiente = cabeza;
            cabeza = nodo;
        }
    }
}

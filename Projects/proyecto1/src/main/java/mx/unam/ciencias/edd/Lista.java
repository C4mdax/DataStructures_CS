package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
	    this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
	    siguiente = cabeza;
	    anterior = null;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
	    return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
	    if (siguiente == null){
		throw new NoSuchElementException("No hay elemento siguiente");
	    }
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
	    return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
	    return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
	    if (anterior == null){
		throw new NoSuchElementException("No hay elemento anterior");
	    }
	    siguiente = anterior;
	    anterior = anterior.anterior;
	    return siguiente.elemento;
	    
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
	    anterior = null;
	    siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
	    anterior = rabo;
	    siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
	return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
	return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
	return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {

        if (elemento == null){
	    throw new IllegalArgumentException("No se puede agregar un elemento vacío");
	}
	Nodo nodo = new Nodo(elemento);
	if (esVacia()){
	    cabeza = rabo = nodo;
	}
	else{
	    rabo.siguiente = nodo;
	    nodo.anterior = rabo;
	    rabo = nodo;
	}
	longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
	agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null){
	    throw new IllegalArgumentException("No se puede agregar un elemento vacío");
	}
	Nodo nodo = new Nodo(elemento);
	if (esVacia()){
	    cabeza = rabo = nodo;
	}
	else{
	    cabeza.anterior = nodo;
	    nodo.siguiente = cabeza;
	    cabeza = nodo;
	}
	longitud++;	
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null){
	    throw new IllegalArgumentException("No se puede agregar un elemento vacío");
	}
	if (i<=0){
	    agregaInicio(elemento);
	}
	else{
	    if (i > longitud-1){
		agregaFinal(elemento);
	    }
	    else{
		longitud++;
		Nodo s = getNodo(i);
		Nodo a = s.anterior;
		Nodo n = new Nodo(elemento);

		n.anterior = a;
		a.siguiente = n;

		n.siguiente = s;
		s.anterior = n;
	    }
	}
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {

        Nodo nodo = cabeza;

        while(nodo != null){
            if(nodo.elemento.equals(elemento)){
                break;
            }
            nodo = nodo.siguiente;
        }
        if(nodo == null){
            return;
        }
        if(nodo.anterior == null){
            cabeza = nodo.siguiente;
        }
        else{
            nodo.anterior.siguiente = nodo.siguiente;
        }
        if(nodo.siguiente == null){
            rabo = nodo.anterior;
        }
        else{
            nodo.siguiente.anterior = nodo.anterior;
        }

        longitud--;
    }
    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
	if (esVacia())
	    throw new NoSuchElementException("La lista es vacía.");

	T aux = cabeza.elemento;
	cabeza = cabeza.siguiente;
	if (cabeza != null)
	    cabeza.anterior = null;
	else{
	    rabo = null;
	}
	longitud--;

	return aux;	
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
	if (rabo == null)
	    throw new NoSuchElementException("La lista es vacía.");
	T aux = rabo.elemento;
	rabo = rabo.anterior;
	if (rabo != null)
	    rabo.siguiente = null;
	else
	    cabeza = null;
	longitud--;

	return aux;	
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	for (T elem : this){
	    if (elem.equals(elemento))
		return true;
	}
	return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
	Lista<T> lista = new Lista<>();
	for (T elem : this)
	    lista.agregaInicio(elem);
	return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
	Lista<T> lista = new Lista<>();
	for (T elem : this)
	    lista.agrega(elem);
	return lista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
	cabeza = null;
	rabo = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
	if (esVacia())
	    throw new NoSuchElementException("La lista es vacía.");
	return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
	if (esVacia())
	    throw new NoSuchElementException("La lista es vacía.");
	return rabo.elemento;	
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
	Nodo nodo = getNodo(i);
	return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
	Nodo nodo = cabeza;
	int cont = 0;
	while (nodo != null){
	    if (nodo.elemento.equals(elemento))
		return cont;
	    else{
		nodo = nodo.siguiente;
		cont++;
	    }
	}
	return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        Nodo nodo = cabeza;
        StringBuilder s = new StringBuilder();

        s.append("[");
        if(esVacia()){
            s.append("]");
            return s.toString();
        }
        else{
            s.append(nodo.elemento);
            nodo = nodo.siguiente;
            while(nodo != null){
                s.append(", " + nodo.elemento);
                nodo = nodo.siguiente;
            }
            return s.append("]").toString();
        }
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;

        if(longitud != lista.longitud)
            return false;
        
        Nodo nodo1 = cabeza;
        Nodo nodo2 = lista.cabeza;

        while(nodo1 != null){
            if(!(nodo1.elemento.equals(nodo2.elemento))){
                return false;
            }
            else{
                nodo1 = nodo1.siguiente;
                nodo2 = nodo2.siguiente;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
    
    /**
     * AUXILIAR
     * Método que devuelve el i-ésimo nodo de la lista
     * @param int i: La posición del nodo al que se quiere acceder
     * @throw ExcepcionIndiceInvalido, si el indice es menor que cero o mayor o igual que la longitud/número de elementos de la lista
     */
    public Nodo getNodo(int i){
	if (i < 0 || i >= longitud){
	    throw new ExcepcionIndiceInvalido("Índice no válido");
	}
	int cont = 0;
	Nodo nodo = cabeza;
	while (cont != i){
	    nodo = nodo.siguiente;
	    cont++;
	}
	return nodo;
    }
}

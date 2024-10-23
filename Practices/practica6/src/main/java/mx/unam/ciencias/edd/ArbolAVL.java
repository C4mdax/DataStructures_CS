package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
	    super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
	    return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
	    return elemento.toString() + " " + altura + "/" + balance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
	    return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
	super();
    }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
	super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
	return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	super.agrega(elemento);
	VerticeAVL vertice = verticeAVL(getUltimoVerticeAgregado());
	rebalancea(verticeAVL(vertice.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
	VerticeAVL vertice = verticeAVL(busca(elemento));
	if (vertice == null)
	    return;
	elementos--;
	if (vertice.izquierdo != null && vertice.derecho != null)
	    vertice = verticeAVL(intercambiaEliminable(vertice));
	eliminaVertice(vertice);
	rebalancea(verticeAVL(vertice.padre));
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
    /**
     * AUXILIAR
     * Método para calcular la altura dado un vértice
     * @param vertice, el vertice a calcular su altura
     */

    private int calculaAltura (VerticeAVL vertice){
        return 1 + Math.max(altura(verticeAVL(vertice.izquierdo)), altura(verticeAVL(vertice.derecho)));
    }
    /**
     * AUXILIAR
     * Método para regresar el valor de la altura de un vértice
     * @param vertice, el vertice a calcular su altura
     */

    private int altura (VerticeAVL vertice){
	return vertice == null ? -1 : vertice.altura;
    }

    /**
     * AUXILIAR
     * Método para hacer una audición de un VérticeArbolBinario a VérticeAVL
     * @param vertice, el vertice a hacer audición
     */
    private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice){
	return (VerticeAVL) vertice;
    }

    /**
     * AUXILIAR
     * Método para calcular el balance de un vértice
     * @param vertice, el vertice a calcular su balance
     */
    private int balance(VerticeAVL vertice){
	return altura(verticeAVL(vertice.izquierdo)) - altura(verticeAVL(vertice.derecho)); 
    }
    
    /**
     * AUXILIAR
     * Método para rebalancear el árbol tanto después de agregar, como después de eliminar
     * @param vertice, el vertice para hacer el rebalanceo sobre el
     */
    private void rebalancea(VerticeAVL vertice){
	if (vertice == null)
	    return;
	/*Caso 1: el balance del vértice es -2 */
	vertice.altura = calculaAltura(vertice);
	
	if (balance(vertice) == -2){
	    VerticeAVL q = verticeAVL(vertice.derecho);
	    
	    if (balance(q) == 1){
		super.giraDerecha(q);
		q.altura = calculaAltura(q);
	    }
	    super.giraIzquierda(vertice);
	    q.altura = calculaAltura(q);
	    vertice.altura = calculaAltura(vertice);
	}

	/*Caso 2: el balance del vértice es 2 */
	else if (balance(vertice) == 2){
	    VerticeAVL i = verticeAVL(vertice.izquierdo);
	    

	    if (balance(i) == -1){
		super.giraIzquierda(i);
		i.altura = calculaAltura(i);
	    }
	    super.giraDerecha(vertice);
	    i.altura = calculaAltura(i);
	    vertice.altura = calculaAltura(vertice);

	}

	rebalancea(verticeAVL(vertice.padre));
    }    
}

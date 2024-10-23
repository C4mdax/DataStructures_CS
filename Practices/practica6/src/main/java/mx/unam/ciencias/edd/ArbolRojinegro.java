package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
	    super(elemento);
	    color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
	    String s = "{" + elemento.toString() + "}";
	    return (color == Color.ROJO ? "R" : "N") + s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
	    return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
	super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
	return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
	return verticeRojinegro(vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	super.agrega(elemento);
	VerticeRojinegro vertice = verticeRojinegro(ultimoAgregado);
	vertice.color = Color.ROJO;
	rebalanceaAgrega(vertice);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
	VerticeRojinegro u, h;
	VerticeRojinegro v = verticeRojinegro(super.busca(elemento));
	if (v == null)
	    return;
	elementos--;
	if (v.hayIzquierdo() && v.hayDerecho())
	    v = verticeRojinegro(intercambiaEliminable(v));
	if (!v.hayIzquierdo() && !v.hayDerecho()){
	    u = verticeRojinegro(nuevoVertice(null));
	    u.color = Color.NEGRO;
	    u.padre = v;
	    v.izquierdo = u;
	}
	h = v.hayIzquierdo() ? verticeRojinegro(v.izquierdo) : verticeRojinegro(v.derecho);
	eliminaVertice(v);
	if (color(h) == Color.ROJO){
	    h.color = Color.NEGRO;
	    return;
	}
	if (color(h) == Color.NEGRO && color(v) == Color.NEGRO)
	    rebalanceaElimina(h);
	eliminaVertice(h);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }

    /**
     * AUXILIAR
     * Método para saber si un vértice es izquierdo.
     * @param v, el vértice del que se quiere saber si es izquierdo.
     */
    private boolean esIzquierdo(VerticeRojinegro v){
	if (v.padre == null)
	    return false;
	return v == v.padre.izquierdo;
    }

    /**
     * AUXILIAR
     * Método para hacer una audición de un vertice de tipo
     * VerticeArbolBinario a VerticeRojinegro (con el previo
     * constructor de vertices rojinegros).
     * @return El vértice de tipo VerticeArbolBinario convertido a tipo VerticeRojinegro.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice){
	return (VerticeRojinegro) vertice;
    }

    /**
     * AUXILIAR
     * Método para obtener el color de un vértice rojinegro, considerando
     * el caso en el que éste sea nulo.
     * @return El color del vértice.
     */
    private Color color(VerticeRojinegro vertice){
	return vertice == null ? Color.NEGRO : vertice.color;
    }

    /**
     * AUXILIAR
     * Método para desconectar un vérice de todo su respectivo árbol.
     */
    private void desconecta(VerticeRojinegro vertice){
	if (vertice == vertice.padre.izquierdo)
	    vertice.padre.izquierdo = null;
	vertice.padre.derecho = null;
    }

    /**
     * AUXILIARES
     * Métodos rebalanceaElimina y rebalanceaAgrega, rebalancean el árbol rojinegro
     * después de aplicar la acción respectiva de cada método (agregar y eliminar).
     */
    private void rebalanceaElimina(VerticeRojinegro v){
	VerticeRojinegro h, p, sIzq, sDer;
	if (!v.hayPadre())
	    return;

	p = verticeRojinegro(v.padre);
	h = (esIzquierdo(v) ? verticeRojinegro(p.derecho) : verticeRojinegro(p.izquierdo));
	if (color(h) == Color.ROJO){
	    p.color = Color.ROJO;
	    h.color = Color.NEGRO;
	    if (esIzquierdo(v)){
		super.giraIzquierda(p);
		h = verticeRojinegro(v.padre.derecho);
	    }else{
		super.giraDerecha(p);
		h = verticeRojinegro(v.padre.izquierdo);
	    }
	}

	sIzq = verticeRojinegro(h.izquierdo);
	sDer = verticeRojinegro(h.derecho);

	if (color(p) == Color.NEGRO && color(h) == Color.NEGRO && color(sIzq) == Color.NEGRO && color(sDer) == Color.NEGRO){
	    h.color = Color.ROJO;
	    rebalanceaElimina(p);
	    return;
	}

	if (color(h) == Color.NEGRO && color(sIzq) == Color.NEGRO && color(sDer) == Color.NEGRO && color(p) == Color.ROJO){
	    h.color = Color.ROJO;
	    p.color = Color.NEGRO;
	    return;
	}

	if((esIzquierdo(v) && color(sIzq) == Color.ROJO && color(sDer) == Color.NEGRO) || (!esIzquierdo(v) && color(sIzq) == Color.NEGRO && color(sDer) == Color.ROJO)){
	    h.color = Color.ROJO;
	    if(color(sIzq) == Color.ROJO)
		sIzq.color = Color.NEGRO;
	    else
		sDer.color = Color.NEGRO;
	    if (esIzquierdo(v)){
		super.giraDerecha(h);
		h = verticeRojinegro(v.padre.derecho);
	    }else{
		super.giraIzquierda(h);
		h = verticeRojinegro(v.padre.izquierdo);
	    }
	}
	sIzq = verticeRojinegro(h.izquierdo);
	sDer = verticeRojinegro(h.derecho);
	h.color = p.color;
	p.color = Color.NEGRO;
	if (esIzquierdo(v)){
	    sDer.color = Color.NEGRO;
	    super.giraIzquierda(p);
	}else{
	    sIzq.color = Color.NEGRO;
	    super.giraDerecha(p);
	}	
    }

    private void rebalanceaAgrega(VerticeRojinegro v){
	/* Caso 1: Padre nulo (el vértice agregado es la raíz). El árbol no está desbalanceado, coloreamos la raíz de negro
	 * por definición de rojinegros.
	 */

	if (v.padre == null){
	    v.color = Color.NEGRO;
	    return;
	}
	/*Caso 2: El padre del vértice es rojo. El árbol no está desbalanceado; terminamos. */
	VerticeRojinegro p = verticeRojinegro(v.padre);
	if (color(p) == Color.NEGRO)
	    return;
	
	/*Caso 3: El color del vértice "tío" es rojo, ésto implica que el vértice "abuelo" es color negro por definición de árboles rojinegros.*/
	VerticeRojinegro a = verticeRojinegro(p.padre);
	VerticeRojinegro t = (p == a.izquierdo ? verticeRojinegro(a.derecho) : verticeRojinegro(a.izquierdo)); 
	if (color(t) == Color.ROJO){
	    t.color = p.color = Color.NEGRO;
	    a.color = Color.ROJO;
	    rebalanceaAgrega(a);
	    return;
	}

	/* Caso 4: Los vértices padre y el vértice dado están cruzados. Los enderezamos girando el padre sobre su dirección.
	 * Actualizamos el vértice para que sea el padre y el padre para que sea el vértice (se mantiene el órden de padre-vértice).
	 */
	if (!(p == a.izquierdo) && v == p.izquierdo){
	    super.giraDerecha(p);
	    VerticeRojinegro u = v;
	    v = p;
	    p = u;
	} else if (p == a.izquierdo && !(v == p.izquierdo)){
	    super.giraIzquierda(p);
	    VerticeRojinegro u = v;
	    v = p;
	    p = u;
	}

	/* Caso 5 El vértice dado y su padre no están cruzados (único posible caso). Coloreamos al vértice padre de negro,
	 * al abuelo de rojo y giramos sobre el abuelo en dirección contraria al vértice dado.
	 */
	p.color = Color.NEGRO;
	a.color = Color.ROJO;
	if (v == p.izquierdo)
	    super.giraDerecha(a);
	else
	    super.giraIzquierda(a);
    }
}

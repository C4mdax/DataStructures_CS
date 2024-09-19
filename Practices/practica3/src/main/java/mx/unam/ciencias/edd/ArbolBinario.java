package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        protected T elemento;
        /** El padre del vértice. */
        protected Vertice padre;
        /** El izquierdo del vértice. */
        protected Vertice izquierdo;
        /** El derecho del vértice. */
        protected Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        protected Vertice(T elemento) {
	    this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
	    return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
	    return izquierdo != null;
	}

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
	    return derecho != null;
	}

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
	    if (!hayPadre())
		throw new NoSuchElementException ("El vértice no tiene padre");
	    return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
	    if (!hayIzquierdo())
		throw new NoSuchElementException ("El vértice no tiene izquierdo");
	    return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
	    if (!hayDerecho())
		throw new NoSuchElementException ("El vértice no tiene derecho");
	    return derecho;
        }
	
        /**
         * ALGORITMO AUXILIAR
         * Algoritmo para calcular la altura de un vértice.
	 * @param v, vértice a calcular su altura
	 * @return la altura del vértice
         */
        private int alturaAux(Vertice v) {
	    if (v == null)
		return -1;
	    return 1 + Math.max(alturaAux(v.izquierdo), alturaAux(v.derecho));
	}

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
	    return alturaAux(this);
	}

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
	    if (!hayPadre())
		return 0;
	    return 1 + padre.profundidad();
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
	    return elemento;
        }
	/**
	 * AUXILIAR
	 * Método que determina si 2 vértices son iguales.
	 * @param v1, v2: vértices a comparar.
	 * @return el resultado de la igualdad.
	 */
    
	private boolean equals(Vertice v1, Vertice v2){
	    if (v1 == null && v2 == null)
		return true;
	    if (v1 == null || v2 == null)
		return false;
	    return v1.elemento.equals(v2.elemento) && equals(v1.izquierdo, v2.izquierdo) && equals(v1.derecho, v2.derecho);

	}

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
	    return equals(this, vertice);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        @Override public String toString() {
	    return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
	for (T elem : coleccion)
	    agrega(elem);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
	return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
	if (raiz == null)
	    return -1;
	return raiz.altura();
    }
    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
	return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	return busca(elemento) != null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
	return busca(elemento, raiz);
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
	if (raiz == null)
	    throw new NoSuchElementException("El árbol es vacío.");
	return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
	return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
	raiz = null;
	elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
	if (raiz == null && arbol.raiz == null)
	    return true;
	return raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
	if (raiz == null)
	    return "";
	int[]a = new int[altura() + 1];
	for(int i = 0; i < altura()+1; i++){
	    a[i] = 0;
	}
	return toString(raiz, 0, a);


    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
	return (Vertice)vertice;
    }    

    /**
     * AUXILIAR
     * Construye la cadena de espacios para un respectivo nivel del arbol binario.
     * @param int l, el nivel para el que se construirá la cadena de espacios
     * @param int[]arreglo, arreglo binario que representa ramas verticales del nivel 
     * @return Si sí se encuentra el vértice, lo retorna, si no, retorna <code>null</code>
     */
    private String dibujaEspacios(int l, int[] arreglo) {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i <= l - 1; i++)
	    sb.append((arreglo[i] == 1) ? "│  " : "   ");
	return sb.toString();
    }

    /**
     * AUXILIAR
     * Regresa una repsentación en cadena del subárbol con raíz en el vértice dado.
     * @return una representación en cadena del árbol.
     */
    private String toString(Vertice v, int l, int[] arreglo){
	String s = v.toString() + "\n";
	arreglo[l] = 1;
	if (v.izquierdo != null && v.derecho != null) {
	    s += dibujaEspacios(l, arreglo);
	    s += "├─›";
	    s += toString(v.izquierdo, l + 1, arreglo);
	    s += dibujaEspacios(l, arreglo);
	    s += "└─»";
	    arreglo[l] = 0; // Se deja de dibijar la rama
	    s += toString(v.derecho, l + 1, arreglo);
	} else if (v.izquierdo != null) {
	    s += dibujaEspacios(l, arreglo);
	    s += "└─›";
	    arreglo[l] = 0; // Se deja de dibujar la rama
	    s += toString(v.izquierdo, l + 1, arreglo);
	} else if (v.derecho != null) {
	    s += dibujaEspacios(l, arreglo);
	    s += "└─»";
	    arreglo[l] = 0; // Se deja de dibujar la rama
	    s += toString(v.derecho, l + 1, arreglo);
	}
	return s;
    }
    /**
     * AUXILIAR
     * Busca de forma recursiva al vértice a partir del subárbol con raíz en el vértice dado.
     * @param vertice, el vértice a para iniciar la búsqueda
     * @param elemento, el elemento a buscar en el árbol
     * @return Si sí se encuentra el vértice, lo retorna, si no, retorna <code>null</code>
     */
    private VerticeArbolBinario<T> busca(T elemento, Vertice vertice) {
	if (vertice == null)
	    return null;
	if(vertice.elemento.equals(elemento))
	    return vertice;
	VerticeArbolBinario<T> vIzq = busca(elemento, vertice.izquierdo);
	VerticeArbolBinario<T> vDer = busca(elemento, vertice.derecho);
	if (vIzq != null)
	    return vIzq;
	return vDer;
    }
}

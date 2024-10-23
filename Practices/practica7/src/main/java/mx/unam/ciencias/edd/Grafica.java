package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
	    this.iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
	    return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
	    return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La lista de vecinos del vértice. */
        private Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
	    this.elemento = elemento;
	    this.color = Color.NINGUNO;
	    vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
	    return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
	    return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
	    return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
	    return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
	vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
	return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
	return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
	if (elemento == null || contiene(elemento))
	    throw new IllegalArgumentException("Elemento no válido.");
	vertices.agrega(new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
	if (!(contiene(a)) || !(contiene(b)))
	    throw new NoSuchElementException("No existe algún elemento en la gráfica.");
	if ((a == b) || (sonVecinos(a,b)))
	    throw new IllegalArgumentException();
	Vertice vA = busca(a);
	Vertice vB = busca(b);
	vA.vecinos.agrega(vB);
	vB.vecinos.agrega(vA);
	aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
	if (!(contiene(a)) || !(contiene(b)))
	    throw new NoSuchElementException("No existe algún elemento en la gráfica.");
	Vertice vA = busca(a);
	Vertice vB = busca(b);
	if (!(sonVecinos(a,b)))
	    throw new IllegalArgumentException("Los elementos no están conectados.");
	vA.vecinos.elimina(vB);
	vB.vecinos.elimina(vA);
	aristas -= 1;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	return busca(elemento) != null;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
	Vertice v = busca(elemento);
	if (v == null)
	    throw new NoSuchElementException("El elemento no está contenido en la gráfica.");
	for (Vertice vertice : v.vecinos)
	    desconecta(v.elemento, vertice.elemento);
	vertices.elimina(v);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
	if (!contiene(a) || !contiene(b))
	    throw new NoSuchElementException("No existe algún elemento en la gráfica.");
	Vertice vA = busca(a);
	Vertice vB = busca(b);
	for (Vertice vert : vA.vecinos){
	    if (vert == vB)
		return true;
	}
	return false;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
	if (!contiene(elemento))
	    throw new NoSuchElementException("El elemento no está contenido en la gráfica.");
	return busca(elemento);
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
	if (!(vertice instanceof Grafica.Vertice))
	    throw new IllegalArgumentException("Vértice no válido.");
	Vertice v = (Vertice) vertice;
	v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
	Vertice vertice = vertices.getPrimero();
        for(Vertice v : vertices)
            v.color = Color.ROJO;
        Cola<Vertice> cola = new Cola<>();
        vertice.color = Color.ROJO;
        cola.mete(vertice);
        while(!cola.esVacia()){
            Vertice m = cola.saca();
            for(Vertice x : m.vecinos)
                if(x.color == Color.ROJO){
                    x.color = Color.NEGRO;
                    cola.mete(x);
                }
        }
        for(Vertice n : vertices)
            if(n.color == Color.ROJO)
                return false;
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
	for (Vertice vertice : vertices)
	    accion.actua(vertice);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
	Cola<Vertice> cola = new Cola<>();
	recorre(elemento, accion, cola);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
	Pila<Vertice> pila = new Pila<>();
	recorre(elemento, accion, pila);
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
	return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
	vertices.limpia();
	aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
	Lista<Vertice> lista = new Lista<>(); 
	StringBuilder verticesString = new StringBuilder();
        verticesString.append("{");
	for (Vertice vertice : vertices){
	    verticesString.append(vertice.elemento.toString() + ", ");
	}
	verticesString.append("}, ");
	StringBuilder aristasString = new StringBuilder();
	aristasString.append("{");
	for (Vertice vertice2 : vertices){
	    for (Vertice vertice3 : vertices){
		if (sonVecinos(vertice2.elemento, vertice3.elemento) && !lista.contiene(vertice3))
		    aristasString.append("(" + vertice2.elemento.toString() + ", " + vertice3.elemento.toString() + "), ");
		lista.agrega(vertice2);
	    }
	}
	aristasString.append("}");
	return verticesString.append(aristasString).toString();
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
	    Grafica<T> grafica = (Grafica<T>)objeto;

	if ((vertices.getLongitud() != grafica.vertices.getLongitud()) || (aristas != grafica.aristas) || (verticeEq(vertices, grafica.vertices)))
	    return false;
	for (Vertice v : vertices)
	    for (Vertice vert : vertices)
		if(v.elemento != vert.elemento && sonVecinos (v.elemento, vert.elemento) && !grafica.sonVecinos(v.elemento, vert.elemento))
		    return false;

	return true;   
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * MÉTODO AUXILIAR
     * Método que devuelve al vértice que contiene al elemento buscado en la lista
     * @return vertice, el vértice con el elemento buscado
     */
    private Vertice busca(T elemento){
	for (Vertice v : vertices){
	    if (v.elemento.equals(elemento))
		return v;
	}
	return null;
    }

    /**
     * MÉTODO AUXILIAR
     * Método que determina si los vértices de 2 listas son iguales
     * @param Lista<Vertice> l1, Lista<Vertice> l2, las listas para trabajar con sus vértices. 
     */

    private boolean verticeEq (Lista<Vertice> l1, Lista<Vertice> l2){
	for (Vertice vertice : l1){
	    if (!l2.contiene(vertice))
		return false;
	}
	return true;
    }

    /**
     * MÉTODO AUXILIAR
     * Método que recorre la lista de vértices, considerando que la implementación de bfs y dfs es practicamente igual, a diferencia de la estrutura usada.
     * El método recorre() tomará como parámetro la estructura MeteSaca, pues será útil para la implementación con cola (dfs) y pila (bfs).
     * @param elemento, el elemento desde donde comenzar el recorrido
     * @param accion, la acción a realizar durante el recorrido.
     * @param estructura, la estructura sea pila o cola que heredan de MeteSaca
     */

    private void recorre (T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> estructura){
	if (!contiene(elemento))
	    throw new NoSuchElementException ("El elemento no está contenido en la gráfica.");
	for (Vertice v : vertices)
	    v.color = Color.ROJO;
	Vertice w = busca(elemento);
	w.color = Color.NEGRO;
        estructura.mete(w);
	while(!estructura.esVacia()){
	    Vertice u = estructura.saca();
	    accion.actua(u);
	    for (Vertice vV : u.vecinos)
		if (vV.color == Color.ROJO){
		    vV.color = Color.NEGRO;
		    estructura.mete(vV);
		}
	}
	for (Vertice vertice : vertices)
	    vertice.color = Color.NINGUNO;
    }
}

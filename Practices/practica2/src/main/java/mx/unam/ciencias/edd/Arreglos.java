package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
	quickSort(T[] arreglo, Comparator<T> comparador) {
	quickSort(arreglo, comparador, 0, arreglo.length-1);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
	quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
	selectionSort(T[] arreglo, Comparator<T> comparador) {
	for(int i = 0; i < arreglo.length; i++){

	    int min = i;

	    for (int j = i+1; j < arreglo.length; j++){

		if (comparador.compare(arreglo[min],arreglo[j]) >= 0){
		    min = j;
		}
	    }
	    intercambia(arreglo, i, min);
	}
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
	selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
	busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
	return busquedaBinaria(arreglo,elemento,comparador,0,arreglo.length-1);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
	busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
    /**
     * ALGORITMO AUXILIAR
     * Método que intercambia 2 objetos dentro de un arreglo
     *
     * @param int i: j-ésimo elemento del arreglo
     * @param int j: j-ésimo elemento del arreglo
     * @param T[] arr: arreglo de tipo genérico.
     * @author Luis Angel Moreno Delgado
     */
	
    public static <T> void intercambia (T[]arr, int i, int j){
	T aux = arr[i];
	arr [i] = arr[j];
	arr[j] = aux;
    }

    /**
     * ALGORITMO AUXILIAR
     * Método quickSort auxiliar
     * @param T[] arr: Arreglo de tipo genérico
     * @param Comparator<T> comparador: Objeto para comparar elementos de tipo genérico
     * @param int a: índice del primer arreglo y subarreglo respectivamente 
     * @param int b: índice del último elemento del arreglo y subarreglo respectivamente
     * @author Luis Angel Moreno Delgado
     */
		
    public static <T> void quickSort (T[]arreglo, Comparator<T> comparador, int ini, int fin){
	if (fin<=ini) return;

	int i = ini + 1;
	int j = fin;


	while (i < j){    		

	    int var = comparador.compare(arreglo[i], arreglo[ini]);

	    if (var > 0 && comparador.compare(arreglo[j], arreglo[ini])<= 0)
		intercambia (arreglo, i++, j--);
	    else if (var <= 0)
		i++;
	    else
		j--;
    	}

	if (comparador.compare(arreglo[i], arreglo[ini]) > 0)
	    i--;
		
	intercambia (arreglo, ini, i);
	quickSort(arreglo, comparador, ini, i-1);
	quickSort (arreglo, comparador, i+1, fin);
    }
	
    /**
     * ALGORITMO AUXILIAR
     * Método busquedaBinaria auxiliar
     * @param T[] arreglo: Arreglo de tipo genérico
     * @param T elemento: Elemento del nodo de tipo genérico
     * @param Comparator<T> comparador: Objeto para comparar elementos de tipo genérico
     * @param int a: índice del primer arreglo y subarreglo respectivamente 
     * @param int b: índice del último elemento del arreglo y subarreglo respectivamente
     * @author Luis Angel Moreno Delgado
     */
		
    public static <T> int busquedaBinaria (T[]arreglo, T elemento,Comparator<T> comparador, int a, int b){
	if (b < a)
	    return -1;

	int piv = ((b-a)/2) + a;

	if (comparador.compare(elemento, arreglo[piv]) == 0)
	    return piv;
	else if (comparador.compare(elemento, arreglo[piv]) < 0)
	    return busquedaBinaria(arreglo, elemento, comparador, a, piv-1);
	else
	    return busquedaBinaria(arreglo, elemento, comparador, piv+1, b);
    }
	

}

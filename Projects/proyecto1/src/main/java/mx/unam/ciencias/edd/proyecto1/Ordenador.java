package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;

public class Ordenador extends Procesador{
    /**
     * Clase Ordenador. Clase para el ordenamiento lexicográfico del programa.
     * La clase extiende de Procesador para utilizar satisfactoriamente el método para normalizar cadenas
     * propio de Procesador.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */
    
    /* Se omitirá la instanciación de la clase Ordenador, pues contendrá un único método y será estático.*/
    private Ordenador(){};
    /**
     * Método ordena. Ordena una lista de cadenas utilizando mergeSort(), manteniendo una complejidad
     * de ordenamiento O(nlogn). El método recibe una lista, y devuelve mediante una lambda el resultado
     * de comparación entre 2 líneas normalizadas, permitiendo así el ordenamiento con líneas normalizadas.
     */
    public static Lista<String> ordena(Lista<String> lista) {
	return lista.mergeSort((l1, l2) -> {
		String normalizada1 = normaliza(l1);
		String normalizada2 = normaliza(l2);
		return normalizada1.compareTo(normalizada2);
	    });
    }
}

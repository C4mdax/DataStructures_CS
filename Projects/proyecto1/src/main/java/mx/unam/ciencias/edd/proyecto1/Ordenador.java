package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;

public class Ordenador extends Procesador{
    /**
     * Clase Proyecto 1. Clase general para el programa de ordenamiento lexicográfico.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */
    
    /* No sería ideal permitir instancias del ordenador, pues contendrá un único método y será estático.*/
    private Ordenador(){};
    
    public static Lista<String> ordena(Lista<String> lista) {
	return lista.mergeSort((l1, l2) -> {
		String normalizada1 = normaliza(l1);
		String normalizada2 = normaliza(l2);
		return normalizada1.compareTo(normalizada2);
	    });
    }
}

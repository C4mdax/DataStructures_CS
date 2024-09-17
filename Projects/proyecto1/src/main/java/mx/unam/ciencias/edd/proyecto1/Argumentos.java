package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;

public class Argumentos {

    /**
     * Clase Proyecto 1. Clase general para el programa de ordenamiento lexicográfico.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */

    /*
     * Atributos dados por las banderas.
     * Ésto permite agregar banderas válidas de forma más dinámica.
     */

    public boolean MODO_REVERSA;
    public boolean MODO_ESCRITURA;

    /*
     * Constructor del objeto para argumentos.
     * En la clase principal se hace manejo de los atributos de éste objeto para el
     * procesamiento de las banderas.
     */
    public Argumentos(boolean reversa, boolean esritura){
	this.MODO_REVERSA = reversa;
	this.MODO_ESCRITURA = reversa;
    }
    /**
     * Método reversa
     * @param lista, la lista.
     * @return la lista en reversa.
     */
    private Lista<String> reversa(Lista<String> lista){
	return lista.reversa();
    }
    private Lista<String> escribe(Lista<String> lista){
	return null;
    }
}

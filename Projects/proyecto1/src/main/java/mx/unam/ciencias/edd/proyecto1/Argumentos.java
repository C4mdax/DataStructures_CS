package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Argumentos {
    /**
     * Clase Argumentos. Contendrá las opciones de banderas (y su procesamiento) para el programa.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */

    /**
     * Los atributos muestran las banderas (modos) disponibles para el programa.
     * Ésto permite agregar banderas válidas de forma más dinámica.
     */

    public boolean MODO_REVERSA;
    public boolean MODO_ESCRITURA;

    /**
     * Constructor del objeto para argumentos.
     * En la clase principal se hace manejo de los atributos de éste objeto para el
     * procesamiento de las banderas.
     */
    public Argumentos(boolean reversa, boolean escritura){
	this.MODO_REVERSA = reversa;
	this.MODO_ESCRITURA = escritura;
    }

    /**
     * Método reversa
     * @param lista, la lista.
     * @return la lista en reversa.
     */
    public Lista<String> reversa(Lista<String> lista){
	return lista.reversa();
    }

    /**
     * Método escribe. Concatena los elementos de una lista en un archivo dado.
     * @param lista, la lista.
     * @return la lista en reversa.
     */
    public void escribe(String archivo, Lista<String> lista){
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String elemento : lista) {
                writer.write(elemento);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

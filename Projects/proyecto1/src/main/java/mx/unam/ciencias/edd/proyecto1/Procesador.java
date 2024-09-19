package mx.unam.ciencias.edd.proyecto1;	
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;

public class Procesador {

    /**
     * clase procesador. clase encargada del procesamiento de la entrada.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */

    /* Constructor vacío para no perderlo.*/
    public Procesador(){};

    /**
     * Método para normalizar cadenas.
     */
    protected String normaliza(String cadena){
	return Normalizer.normalize(cadena, Normalizer.Form.NFD)
	    .replaceAll("[^a-zA-Z0-9]", "")
	    .toLowerCase()
	    .trim();
    }
    
    /**
     * Lectura por argumentos al programa:
     * Método para agregar a una lista el texto dentro de un archivo de texto
     * dada la ruta de éste último. Cada renglón del archivo de texto corresponderá a
     * un elemento de la lista. El método no limpia la lista parámetro, solo concatena
     * los renglones del archivo de texto.
     * @param lista, la lista que contendrá el texto del archivo de texto.
     * @param ruta, la ruta del archivo (dada en el argumento al ejecutar el programa).
     */
    public void getArgumento(String ruta, Lista<String> lista) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.agrega(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Lectura estándar al programa:
     * Método para agregar el texto contenido en un archivo de texto
     * en una lista. Cada renglón del archivo de texto corresponderá a
     * un elemento de la lista.
     * De igual forma, el método no limpia la lista, solo concatena las líneas
     * de texto del archivo dado en la entrada estándar.
     * @param lista, la lista que contendrá el texto del archivo de texto.
     */
    public void getEstandar(Lista<String> lista) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.agrega(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la entrada: " + e.getMessage());
        }
    }
}

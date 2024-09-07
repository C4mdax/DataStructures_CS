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
     * Clase IO. Encargada del manejo y procesamiento de la entrada y salida.
     * @autor Moreno Delgado Luis Angel
     * @version Julio 2024
     */
    
    private String normaliza(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^a-zA-Z0-9]", "").toLowerCase().trim();
    }
    
    public Lista<String> getNormalizada(Lista<String> lista) {
        Lista<String> listaNormalizada = new Lista<String>();
        for (String s : lista) {
            listaNormalizada.agrega(normaliza(s));
        }
        return listaNormalizada;
    }

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

    public void getEstandar(Lista<String> lista) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.agrega(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la entrada estándar: " + e.getMessage());
        }
    }
}

package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Proyecto1 {
    public static void main(String[] args) {
        boolean reversa = false;  // "-r": texto ordenado al revés.
        boolean escritura = false;  // "-o": texto ordenado sobrescribiendo el archivo dado
        String archivoSalida = null;  // Archivo de salida para la bandera -o

        Lista<String> textoEntrada = new Lista<>();
        Lista<String> entrada = new Lista<>();
        Procesador procesador = new Procesador();

        if (args.length == 0) {
            procesador.getEstandar(textoEntrada);
        } else {
            for (int i = 0; i < args.length; i++) {
                String argumento = args[i];
                entrada.agrega(argumento);

                if (argumento.equals("-o")) {
                    escritura = true;
                    // Verificamos que haya un argumento después de "-o" (el archivo de salida)
                    if (i + 1 < args.length) {
                        archivoSalida = args[i + 1];  // Guardamos el archivo de salida
                        i++;  // Saltamos el siguiente argumento que es el nombre del archivo de salida
                    } else {
                        System.err.println("Uso correcto de bandera -o: java -jar target/proyecto1.jar -o <ruta_archivo>");
                        return;  // Terminar si no se especificó archivo de salida
                    }
                } else if (argumento.equals("-r")) {
                    reversa = true;
                } else {
                    // Si no es una bandera, tratamos el argumento como un archivo de entrada
                    procesador.getArgumento(argumento, textoEntrada);
                }
            }
        }

        Argumentos banderas = new Argumentos(reversa, escritura);
        Lista<String> ordenada = Ordenador.ordena(textoEntrada);

        // Si se especificó la bandera -r, imprimimos el texto en orden inverso
        if (banderas.MODO_REVERSA) {
            Lista<String> inversa = banderas.reversa(ordenada);
            for (String linea : inversa) {
                System.out.println(linea);
            }
        }

        // Si se especificó la bandera -o, escribimos el archivo de salida
        if (banderas.MODO_ESCRITURA) {
            if (archivoSalida != null) {
                banderas.escribe(archivoSalida, ordenada);
                System.out.println("Se ha sobrescrito el archivo correctamente.");
            } else {
                System.err.println("Error: No se especificó archivo de salida.");
            }
        }

        // Si no hay bandera -o ni -r, imprimimos el texto normalmente
        if (!banderas.MODO_ESCRITURA && !banderas.MODO_REVERSA) {
            for (String linea : ordenada) {
                System.out.println(linea);
            }
        }
    }
}

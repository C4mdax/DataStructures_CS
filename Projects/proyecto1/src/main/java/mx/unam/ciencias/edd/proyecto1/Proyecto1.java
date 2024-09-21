package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Proyecto1 {
    public static void main(String[] args) {
	/* Banderas disponibles:*/
        boolean reversa = false;  // "-r": texto ordenado al revés.
        boolean escritura = false;  // "-o": texto ordenado sobrescribiendo el archivo dado
	
        Lista<String> textoExtraido = new Lista<>();
        Procesador procesador = new Procesador();

	/* Caso de entrada estándar.*/
        if (args.length == 0) {
            procesador.getEstandar(textoExtraido);
        }
	/* Caso de entrada por argumentos.*/
	else {

            for (String argumento : args) {
		/* Analizamos los argumentos en busca de banderas. */
                if (argumento.equals("-o")) {
                    if (escritura) {
			System.err.println("Advertencia: La bandera '-r' puede ser utilizada únicamente una vez.");
                        return;
                    }
                    escritura = true;
                } else if (argumento.equals("-r")) {
                    if (reversa) {
			System.err.println("Advertencia: La bandera '-o' puede ser utilizada únicamente una vez.");
                        return;
                    }
                    reversa = true;
                } else {
		    /*Se procesan los argumentos que no son banderas.*/
                    procesador.getArgumento(argumento, textoExtraido);
                }
            }
        }

	/* Creamos una instancia de la clase argumentos con los parámetros
	 * booleanos de la respectiva bandera.
	 */
        Argumentos banderas = new Argumentos(reversa, escritura);
	
	
    } 
}

package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Proyecto1 {
    public static void main(String[] args) {
<<<<<<< HEAD
=======

	/* Banderas disponibles:*/
        boolean reversa = false;  // "-r": texto ordenado al revés.
        boolean escritura = false;  // "-o": texto ordenado sobrescribiendo el archivo dado
	
	Lista<String> entradaCruda = new Lista<>();
        Lista<String> textoExtraido = new Lista<>();
	Lista<String> textoOrdenado = new Lista<>();

	/* Caso de entrada estándar.*/
        if (args.length == 0) {
            Procesador.getEstandar(textoExtraido);
        }

	/* Caso de entrada por argumentos.*/
	else {    
            for (String argumento : args) {
		entradaCruda.agrega(argumento);
		/* Analizamos los argumentos en busca de banderas. */		
		if (argumento.equals("-o")) {
                    if (escritura) {
			System.err.println("Error: La bandera '-o' puede ser utilizada únicamente una vez.");
                        return;
                    }
                    escritura = true;
                } else if (argumento.equals("-r")) {
                    if (reversa) {
			System.err.println("Error: La bandera '-r' puede ser utilizada únicamente una vez.");
                        return;
                    }
                    reversa = true;
                } else {
		    /*Procesamos los argumentos que no son banderas.*/
                    Procesador.getArgumento(argumento, textoExtraido);
                }
            }
        }

	/* Creamos una instancia de la clase argumentos con los parámetros
	 * booleanos de la respectiva bandera.
	 */
        Argumentos banderas = new Argumentos(reversa, escritura);
	textoOrdenado = Ordenador.ordena(textoExtraido);

	/* Lógica para el uso de la bandera '-r' */
	if (banderas.MODO_REVERSA){
	    if (entradaCruda.getElementos() == 1)
		System.err.println("No se especificó el archivo de entrada. " +
				   "Uso correcto: java -jar target/proyecto1.jar -r <ruta_archivo1> " +
				   "<ruta_archivo2>...");
	    else{
		Lista<String> ordenadoReversa = banderas.reversa(textoOrdenado);
		for (String linea : ordenadoReversa)
		    System.out.println(linea);
	    }
	}
	
	/* Lógica para el uso de la bandera '-o' */
	else if (banderas.MODO_ESCRITURA){
	    int indiceBandera = entradaCruda.indiceDe("-o");
	    if (indiceBandera == entradaCruda.getElementos()-1)
		System.err.println("No se especificó el archivo de salida. " +
				   "Uso correcto: java -jar target/proyecto1.jar -o <ruta_archivo>");
	    else{
		if (Procesador.esValido(args[indiceBandera + 1])){
		    banderas.escribe(args[indiceBandera + 1], textoOrdenado);
		    System.out.println("Se ha escrito correctamente al archivo " + args[indiceBandera + 1]);
		}
		else{
		    System.err.println("Archivo de salida inválido.");
		}
	    }
	}
	
	/* Lógica para la impresión del texto ordenado en la salida estándar, sin banderas especificadas.*/
	else{
	    for (String linea : textoOrdenado)
		System.out.println(linea);
	}
    } 
}

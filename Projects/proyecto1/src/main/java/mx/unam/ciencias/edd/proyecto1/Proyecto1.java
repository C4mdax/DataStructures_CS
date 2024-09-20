package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Proyecto1 {
    /**
     * Clase Proyecto 1. Clase general para el programa de ordenamiento lexicográfico.
     * @author Luis Angel Moreno Delgado
     * @version Septiembre 2024.
     */
    public static void main(String[] args) {
	/*Banderas disponibles: */
        boolean reversa = false; /*"-r": texto ordenado al revés.*/
        boolean escritura = false; /*"-o": texto ordenado sobrescribiendo el archivo dado*/
	
	Lista<String> textoEntrada = new Lista<>();
	Lista<String> entrada = new Lista<>();
	Procesador procesador = new Procesador();

	if (args.length == 0){
	    procesador.getEstandar(textoEntrada);
	}
	else{
	    for (String argumento : args){
		entrada.agrega(argumento);
		if (argumento.equals("-o")){
		     escritura = true;
		     continue;
		}
		
		else if (argumento.equals("-r")) reversa = true;
		else
		    procesador.getArgumento(argumento, textoEntrada);
	    }
	}
	/* Creamos una instancia de la clase Argumentos la cuál toma como parámetros los valores
	 * booleanos de las respectivas banderas.
	 */
	Argumentos banderas = new Argumentos(reversa, escritura);
	
	Lista<String> ordenada = Ordenador.ordena(textoEntrada);
	/* Se especificó la bandera -r: impresión de la lista en orden inverso.*/
	if (banderas.MODO_REVERSA){
	    Lista<String> inversa = banderas.reversa(ordenada);
	    for (String linea : inversa)
		System.out.println(linea);
	}
	/* Se especificó la bandera -o: se sobrescribirá el archivo dado con el texto ordenado.*/
	else if (banderas.MODO_ESCRITURA){
	    int indiceBandera = entrada.indiceDe("-o");
	    if (indiceBandera == args.length-1)
		System.err.println("Uso correcto de bandera -o: java -jar target/proyecto1.jar -o <ruta_archivo>");
	    else{
		banderas.escribe(args[indiceBandera+1], ordenada);
		System.out.println("Se ha sobrescrito el archivo correctamente.");
	    }
	}
	else{
	    for (String linea : ordenada)
		System.out.println(linea);
	}
    }
}

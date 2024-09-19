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
	
	Lista<String> entrada = new Lista<>();
	Procesador procesador = new Procesador();

	if (args.length == 0){
	    System.out.println("Estándar");
	    procesador.getEstandar(entrada);
	}
	else{
	    for (String argumento : args){
		if (argumento.equals("-o")) escritura = true;
		
		else if (argumento.equals("-r")) reversa = true;
		else
		    procesador.getArgumento(argumento, entrada);
	    }
	}



    }
}
package selecciones;

import cromosoma.Cromosoma;

public class Estocastica {

	public Estocastica() {
		super();
	}


	public Cromosoma[] seleccion(int tamPob, Cromosoma[] poblacion) {
		double distancia = 1.0/tamPob;
        int[] seleccionados = new int[tamPob];
        double primera = Math.random() * (distancia);//random entre 0 y distancia
        int pos = 0;
        Cromosoma[] pobAux = new Cromosoma[tamPob];
        
        for (int i = 0; i < tamPob; i++) {
        	pos = 0;
            while ((primera > poblacion[pos].getPuntAcum()) && (pos < tamPob)) {
                pos++;
            }
            seleccionados[i] = pos;
            primera += distancia;
        }
       
		for (int i = 0; i < tamPob; i++) {
			pobAux[i] = poblacion[seleccionados[i]];
		}
		return pobAux;
	}

}

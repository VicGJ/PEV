package selecciones;

import java.util.Random;

import cromosoma.Cromosoma;

public class Ruleta {
	
	public Ruleta() {
		super();
	}

	
	public Cromosoma[] seleccion(int tamPob, Cromosoma[] poblacion) {
		int[] sel_super = new int[tamPob];
    	double prob; // seleccionados para sobrevivir
        Cromosoma[] aux = new Cromosoma[tamPob];
        Random r = new Random();
        int pos_super; // posicion del superviviente
        
        for(int i = 0; i < tamPob; i++){
            //Seleccionamos dos al azar de poblacion
            pos_super = 0;
            prob = r.nextDouble();
            while((prob > poblacion[pos_super].getPunt_acum()) && (pos_super < tamPob))
            { 
            	pos_super++;
            	sel_super[i] = pos_super;
            }
            
        }
        
        for(int i = 0; i < tamPob; i++)
        {
            aux[i] = poblacion[sel_super[i]];
        }
		
		return aux;
	}

}

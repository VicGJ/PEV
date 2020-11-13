package selecciones;

import java.util.Random;

import cromosoma.Cromosoma;

public class Torneo {

	public Torneo() {
	}

	
	public Cromosoma[] seleccion(int tamPob, Cromosoma[] poblacion) {
		Cromosoma[] pobK = new Cromosoma[2];
        Cromosoma[] aux = new Cromosoma[tamPob];
        Random r = new Random();
        int pos;
        //Empezamos a seleccionar
        for(int i = 0; i < tamPob; i++)
        {
            //Seleccionamos dos al azar de poblacion
            for(int j = 0; j < 2; j++)
            { 
            	pos = (int) (r.nextDouble() * tamPob);
            	pobK[j] = poblacion[pos].clonar();
            }
            
            aux[i] = getMejor(pobK);
            
        }

		return aux;
	}
	
	private Cromosoma getMejor(Cromosoma[] pobK) {
        if(pobK[0].getAdaptacion() < pobK[1].getAdaptacion())
        {
            return pobK[1];
        }
        else
        {
            return pobK[0];
        }
    }

}

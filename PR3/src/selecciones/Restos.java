package selecciones;

import java.util.Random;
import cromosoma.Cromosoma;

public class Restos {
	double prob;
    int pos_super;

    Random r = new Random();

    public Restos() {

    }
    
    public Cromosoma[] seleccion(int tamPob, Cromosoma[] poblacion) {
    	Cromosoma[] aux = new Cromosoma[tamPob];

        int cont = 0;

        for (int i = 0; i < tamPob; i++) {
            pos_super = 0;
            prob = poblacion[i].getPuntuacion();
            double probFinal = (prob * 25);// la probabilidad por el tamaño de la poblacion Pi * K
            if ((probFinal > 1)) {
                aux[i] = poblacion[i];
                cont++;
            }
        }
        int f = tamPob - cont;
        Cromosoma[] aux2 = new Cromosoma[f];
        Ruleta r = new Ruleta();
        aux2 = r.seleccion(f, poblacion);
        
        for(int j = 0; j < tamPob; j++)
        {
            if(aux[j] == null)
            {
                aux[j] = aux2[j].clonar();
            }
        }
        for (int i = 0; i < tamPob; i++) {
            poblacion[i] = aux[i].clonar();
        }

        return poblacion;
    }
    

}

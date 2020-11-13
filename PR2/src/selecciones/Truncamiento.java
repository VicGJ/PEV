package selecciones;

import cromosoma.Cromosoma;

public class Truncamiento {

	double trunc;

	public Truncamiento(double param) {
		this.trunc = param;
	}

	public Cromosoma[] seleccion(int tamPob, Cromosoma[] poblacion) {
		double veces = 1.0 / 0.5;
        Cromosoma[] aux = new Cromosoma[tamPob];
        ordenar(poblacion, tamPob);
        int pos = 0;
        
        for (int i = 0; i < tamPob; i++) {
            for (int j = 0; j < veces; j++) {
                aux[i] = poblacion[pos].clonar();
                i++;
            }
            i--;
            pos++;
        }
		return aux;
	}

	private void ordenar(Cromosoma[] aOrdenar, int n) {
        Cromosoma temp = null;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
            	  if (aOrdenar[i].getAdaptacion() < aOrdenar[j].getAdaptacion()) {
            		  temp = aOrdenar[i];
                      aOrdenar[i] = aOrdenar[j];
                      aOrdenar[j] = temp;
                }
            }
        }
    }
}

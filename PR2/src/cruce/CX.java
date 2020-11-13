package cruce;

import cromosoma.Cromosoma;

public class CX {

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {
		Cromosoma[] hijos = new Cromosoma[2];

		hijos[0] = padre1.clonar();
		hijos[1] = padre2.clonar();

		boolean[] pos = new boolean[padre1.getLongCromosoma()];// Posiciones del ciclo
		for (int i = 0; i < padre1.getLongCromosoma(); i++) {
			pos[i] = false;
		}

		int ind = 0;
		int ini = padre1.getGenes()[ind].getAlelo();
		int fin = padre2.getGenes()[ind].getAlelo();
		pos[ind] = true;

		while (ini != fin) {
			ind = buscarElemento(padre1, fin);
			fin = padre2.getGenes()[ind].getAlelo();
			pos[ind] = true;
		}

		for (int j = 0; j < padre1.getLongCromosoma(); j++) {
			if (!pos[j]) {
				hijos[0].insertar(padre2.getGenes()[j], j);
			}
		}

		for (int i = 0; i < padre1.getLongCromosoma(); i++) {
			pos[i] = false;
		}

		ind = 0;
		ini = padre2.getGenes()[ind].getAlelo();
		fin = padre1.getGenes()[ind].getAlelo();
		pos[ind] = true;

		while (ini != fin) {
			ind = buscarElemento(padre2, fin);
			fin = padre1.getGenes()[ind].getAlelo();
			pos[ind] = true;
		}

		for (int j = 0; j < padre1.getLongCromosoma(); j++) {
			if (!pos[j]) {
				hijos[1].insertar(padre1.getGenes()[j], j);
			}
		}

		hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
		hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));

		return hijos;
	}
	
    private int buscarElemento(Cromosoma padre1, int n) {
    	boolean encontrado = false;
		int pos = 0;
		for (int i = 0; i < padre1.getLongCromosoma() && !encontrado; i++) {
			if (padre1.getGenes()[i].getAlelo() == n) {
				pos = i;
			}
		}
		return pos;
    }
    
}
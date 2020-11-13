package cruce;

import java.util.Random;

import cromosoma.Cromosoma;

public class PMX {

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {
		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = padre1.clonar();
		hijos[1] = padre2.clonar();

		int puntoA, puntoB;
		int pos;
        // Calculo de puntos de cruce
        Random rnd = new Random();
        puntoA = rnd.nextInt(padre1.getNumGenes());
        puntoB = rnd.nextInt(padre1.getNumGenes() - puntoA);
        puntoB += puntoA + 1;
        
        for (int i = puntoA; i < puntoB; i++) {
			hijos[0].insertar(padre2.getGenes()[i], i);
			hijos[1].insertar(padre1.getGenes()[i], i);
		}
        
        for (int i = puntoB; i < padre1.getLongCromosoma(); i++) {
			if (!alredyExists(hijos[0], padre1.getGenes()[i].getAlelo(), puntoA, puntoB))
				hijos[0].insertar(padre1.getGenes()[i], i);
			else {
				int j = i;
				do {
					pos = buscarElemento(padre2, padre1.getGenes()[j].getAlelo(), puntoA, puntoB);
					j = pos;
				} while (alredyExists(hijos[0], padre1.getGenes()[j].getAlelo(), puntoA, puntoB));

				hijos[0].insertar(padre1.getGenes()[j], i);
			}

			if (!alredyExists(hijos[1], padre2.getGenes()[i].getAlelo(), puntoA, puntoB))
				hijos[1].insertar(padre2.getGenes()[i], i);
			else {

				int j = i;
				do {
					pos = buscarElemento(padre1, padre2.getGenes()[j].getAlelo(), puntoA, puntoB);
					j = pos;
				} while (alredyExists(hijos[1], padre2.getGenes()[j].getAlelo(), puntoA, puntoB));

				hijos[1].insertar(padre2.getGenes()[j], i);

			}
		}
        
        for (int i = 0; i < puntoA; i++) {
			if (!alredyExists(hijos[0], padre1.getGenes()[i].getAlelo(), puntoA, padre1.getLongCromosoma()))
				hijos[0].insertar(padre1.getGenes()[i], i);
			else {

				int j = i;
				do {
					pos = buscarElemento(padre2, padre1.getGenes()[j].getAlelo(), puntoA, puntoB);
					j = pos;
				} while (alredyExists(hijos[0], padre1.getGenes()[j].getAlelo(), puntoA,
						padre1.getLongCromosoma()));

				hijos[0].insertar(padre1.getGenes()[j], i);
			}

			if (!alredyExists(hijos[1], padre2.getGenes()[i].getAlelo(), puntoA, padre1.getLongCromosoma()))
				hijos[1].insertar(padre2.getGenes()[i], i);
			else {

				int j = i;
				do {
					pos = buscarElemento(padre1, padre2.getGenes()[j].getAlelo(), puntoA, puntoB);
					j = pos;
				} while (alredyExists(hijos[1], padre2.getGenes()[j].getAlelo(), puntoA,
						padre1.getLongCromosoma()));
				hijos[1].insertar(padre2.getGenes()[j], i);
			}
		}
        hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
        hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));
		return hijos;
	}

	private boolean alredyExists(Cromosoma hijo, int fen, int c1, int c2) {
		for (int i = c1; i < c2; i++) {
			if (hijo.getGenes()[i].getAlelo() == fen)
				return true;
		}
		return false;
	}

	private int buscarElemento(Cromosoma hijo, int fen, int c1, int c2) {
		boolean encontrado = false;
		int pos = 0;
		for (int i = c1; i < c2 && !encontrado; i++) {
			if (hijo.getGenes()[i].getAlelo() == fen) {
				pos = i;
			}
		}
		return pos;
	}
	
}
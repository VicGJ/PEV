package Mutacion;

import java.util.Arrays;
import java.util.Random;

import cromosoma.Cromosoma;

public class Heuristica {

	int n = 3;
	private int[] mejor;
	private double mejor_aptitud;
	private int[] lugares;
    
	public int mutar(Cromosoma[] pob, double probMut, Random random) {

		int mutaciones = 0;

		for (int i = 0; i < pob.length; i++) {
			if (Math.random() < probMut) {
				mutaciones++;

				mejor = new int[n];
				mejor_aptitud = Double.MAX_VALUE;
				lugares = new int[n];

				Cromosoma c = pob[i].clonar();
				int[] genes = new int[n];

				int pos;

				boolean[] posiciones = new boolean[pob[0].getNumGenes()];
				for (int k = 0; k < pob[0].getNumGenes(); k++) {
					posiciones[k] = false;
				}

				
				for (int j = 0; j < n; j++) {
					pos = (int) (Math.random() * pob[0].getNumGenes());
					while (posiciones[pos]) {
						pos = (int) (Math.random() * pob[0].getNumGenes());
					}
					lugares[j] = pos;
					genes[j] = c.getGenes()[pos].getAlelo();
					posiciones[pos] = true;
				}
				Arrays.sort(lugares);

				
				do {
					for (int j = 0; j < n; j++) {
						c.getGenes()[lugares[j]].setAlelo(genes[j]);
					}
					double m = c.operar(c.calcularFenotipo());
					if (m < mejor_aptitud) {
						mejor_aptitud = m;
						for (int j = 0; j < n; j++) {
							mejor[j] = genes[j];
						}
					}

				} while (siguientePer(genes));

				for (int j = 0; j < n; j++) {
					c.getGenes()[lugares[j]].setAlelo(mejor[j]);
				}

				c.setAptitud(c.operar(c.calcularFenotipo()));
				pob[i] = c.clonar();

			}
		}
		return mutaciones;
	}

	private boolean siguientePer(int[] permutacion) {
		int i = permutacion.length - 1;
		while (i > 0 && permutacion[i - 1] >= permutacion[i]) {
			i--;
		}

		if (i <= 0) {
			return false;
		}

		int j = permutacion.length - 1;
		while (permutacion[j] <= permutacion[i - 1]) {
			j--;
		}

		int temp = permutacion[i - 1];
		permutacion[i - 1] = permutacion[j];
		permutacion[j] = temp;

		j = permutacion.length - 1;
		while (i < j) {
			temp = permutacion[i];
			permutacion[i] = permutacion[j];
			permutacion[j] = temp;
			i++;
			j--;
		}
		return true;
	}
}
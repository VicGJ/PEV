package Mutacion;

import java.util.Random;

import cromosoma.Cromosoma;

public class Insercion {
	private final double inserciones = 2;
	
	public int mutar(Cromosoma[] pob, double probMut, Random random) {
		int mutaciones = 0;
        Random rnd = new Random();
        for (int i = 0; i < pob.length; i++) {
            double p = rnd.nextDouble();
            if (p < probMut) {
                mutaciones++;
                Cromosoma c = pob[i];
                for (int j = 0; j < inserciones; j++) {
                    int posicionACambiar = rnd.nextInt(c.getNumGenes());
                    int posicionDondeInsertar = rnd.nextInt(c.getNumGenes());
                    while (posicionACambiar == posicionDondeInsertar) {
                        posicionDondeInsertar = rnd.nextInt(c.getNumGenes());
                    }
                    if (posicionACambiar < posicionDondeInsertar) {
                        int aux = c.getGenes()[posicionACambiar].getAlelo();
                        for (int k = posicionACambiar; k < posicionDondeInsertar; k++) {
                            c.getGenes()[k].setAlelo(c.getGenes()[k + 1].getAlelo());
                        }
                        c.getGenes()[posicionDondeInsertar].setAlelo(aux);
                    } else if (posicionACambiar > posicionDondeInsertar) {
                        int aux = c.getGenes()[posicionACambiar].getAlelo();
                        for (int k = posicionACambiar; k > posicionDondeInsertar; k--) {
                            c.getGenes()[k].setAlelo(c.getGenes()[k - 1].getAlelo());
                        }
                        c.getGenes()[posicionDondeInsertar].setAlelo(aux);
                    }
                    c.setAptitud(c.operar(c.calcularFenotipo()));
                    pob[i] = c.clonar();
                }
            }
        }
        return mutaciones;
	}
}

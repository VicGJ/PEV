package Mutacion;

import java.util.Random;

import cromosoma.Cromosoma;

public class Intercambio {

	public int mutar(Cromosoma[] pob, double probMut, Random random) {
		 int mutaciones = 0;
	        double prob;
	        for (int i = 0; i < pob.length; i++) {
	            prob = Math.random();

	            if (prob < probMut) {
	                mutaciones++;
	                Cromosoma crom = pob[i];

	                int pos1 = (int) (Math.random() * crom.getNumGenes());
	                int pos2 = (int) (Math.random() * crom.getNumGenes());

	                while (pos1 == pos2) {
	                    pos2 = (int) (Math.random() * crom.getNumGenes());
	                }

	                int temp = crom.getGenes()[pos2].getAlelo();
	                crom.getGenes()[pos2].setAlelo(crom.getGenes()[pos1].getAlelo());
	                crom.getGenes()[pos1].setAlelo(temp);

	                crom.setAptitud(crom.operar(crom.calcularFenotipo()));
	                pob[i] = crom.clonar();
	            }
	        }
	        return mutaciones;
	}

}
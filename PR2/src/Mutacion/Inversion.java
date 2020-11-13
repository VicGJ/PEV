package Mutacion;

import java.util.Random;
import java.util.Stack;

import cromosoma.Cromosoma;

public class Inversion {
	int inversiones = 0;

	public int  mutar(Cromosoma[] pob, double probMut, Random random) {
		int mutaciones = 0;
        Random rnd = new Random();
        for (int i = 0; i < pob.length; i++) {
            double p = rnd.nextDouble();
            if (p < probMut) {
                mutaciones++;
                Cromosoma c = pob[i];
                int puntoA = rnd.nextInt(c.getNumGenes());
                int puntoB = rnd.nextInt(c.getNumGenes() - puntoA);
                puntoB += puntoA + 1;
                Stack<Integer> s = new Stack<Integer>();
                for (int j = puntoA; j < puntoB; j++) {
                    s.push(c.getGenes()[j].getAlelo());
                }
                for (int j = puntoA; j < puntoB; j++) {
                    c.getGenes()[j].setAlelo(s.pop());
                }
                c.setAptitud(c.operar(c.calcularFenotipo()));
                pob[i] = c.clonar();
            }
        }
        return mutaciones;
	}
}
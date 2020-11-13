package cruce;

import java.util.ArrayList;
import java.util.Random;

import cromosoma.Cromosoma;

public class OXPos {

	public OXPos() {
		super();

	}

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {
		Cromosoma[] hijos = new Cromosoma[2];
		
		hijos[0] = padre2.clonar();
		hijos[1] = padre1.clonar();

		int max = 0;
        int tam = padre1.getNumGenes();
        tam = tam / 2;
        ArrayList<Integer> points = new ArrayList<>();
        ArrayList<Integer> al1 = new ArrayList<>();
        ArrayList<Integer> al2 = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < tam; i++) {
            int r = rnd.nextInt(padre1.getNumGenes());
            if (!points.contains(r)) {
                points.add(r);
                if (r > max) {
                    max = r;
                }
            } else {
                i--;
            }
        }
        for (int i = 0; i < padre1.getNumGenes(); i++) {
            if (points.contains(i)) {
                // Hijo 1
            	hijos[0].getGenes()[i].setAlelo(padre2.getGenes()[i].getAlelo());
                al1.add(padre2.getGenes()[i].getAlelo());
                // Hijo 2
                hijos[1].getGenes()[i].setAlelo(padre1.getGenes()[i].getAlelo());
                al2.add(padre1.getGenes()[i].getAlelo());
            }
        }
        int j = max + 1;
        int k = max + 1;
        for (int i = 0; i < padre1.getNumGenes(); i++) {
            if (j >= padre1.getNumGenes()) {
                j = 0;
            }
            if (k >= padre1.getNumGenes()) {
                k = 0;
            }
            if (!points.contains(j)) {
                while (al1.contains(padre1.getGenes()[k].getAlelo())) {
                    k++;
                    if (k >= padre1.getNumGenes()) {
                        k = 0;
                    }
                }
                al1.add(padre1.getGenes()[k].getAlelo());
                hijos[0].getGenes()[j].setAlelo(padre1.getGenes()[k].getAlelo());
            }
            j++;
        }
        j = max + 1;
        k = max + 1;
        for (int i = 0; i < padre1.getNumGenes(); i++) {
            if (j >= padre1.getNumGenes()) {
                j = 0;
            }
            if (k >= padre1.getNumGenes()) {
                k = 0;
            }
            if (!points.contains(j)) {
                while (al2.contains(padre2.getGenes()[k].getAlelo())) {
                    k++;
                    if (k >= padre1.getNumGenes()) {
                        k = 0;
                    }
                }

                al2.add(padre2.getGenes()[k].getAlelo());
                hijos[1].getGenes()[j].setAlelo(padre2.getGenes()[k].getAlelo());
            }
            j++;
        }

		hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
		hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));

		return hijos;
	}

}
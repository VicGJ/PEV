/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruce;

import cromosoma.Cromosoma;
import java.util.Random;

/**
 *
 * @author R2D2
 */
public class OX {

    private static int puntoA;
    private static int puntoB;
    
    public OX() {
        puntoA = 0;
        puntoB = 0;
    }

    public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {

    	Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = padre2.clonar();
		hijos[1] = padre1.clonar();

		generaPuntos(padre1.getNumGenes());

        int[] p1 = new int[padre1.getNumGenes()];
        int[] p2 = new int[padre1.getNumGenes()];

        llenaArray(p1, padre2);
        llenaArray(p2, padre1);

        completaArray(p1, padre1);
        completaArray(p2, padre2);

        genesHijo(p1, hijos[0]);
        genesHijo(p2, hijos[1]);

		hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
		hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));

		return hijos;
	}

	
	private void genesHijo(int[] p, Cromosoma hijo) {

        for (int i = 0; i < p.length; i++) {
            hijo.getGenes()[i].setAlelo(p[i]);;
        }
    }

    private void completaArray(int[] p, Cromosoma padre) {
        int k = puntoB;
        boolean enc = false;
        int elem = 0;
        int limit = padre.getNumGenes();

        for (int i = puntoB; i < p.length; i++) {

            while (k < limit && !enc) {
                elem = padre.getGenes()[k].getAlelo();
                if (!buscaElemento(p, elem)) {
                    enc = true;
                }
                k++;
                if (k == padre.getNumGenes()) {
                    k = 0;
                    limit = puntoB;
                }
            }

            if (enc) {
                p[i] = elem;
            }
            enc = false;
        }

        for (int i = 0; i < puntoA; i++) {
            while (k < limit && !enc) {
                elem = padre.getGenes()[k].getAlelo();
                if (!buscaElemento(p, elem)) {
                    enc = true;
                }
                k++;
                if (k == padre.getNumGenes()) {
                    k = 0;
                    limit = puntoB;
                }
            }

            if (enc) {
                p[i] = elem;
            }
            enc = false;
        }

    }

    private boolean buscaElemento(int[] p, int elem) {
        boolean enc = false;
        int i = puntoA;

        while (i < puntoB && !enc) {
            if (p[i] == elem) {
                enc = true;
            }
            i++;
        }
        return enc;
    }

    private void llenaArray(int[] p, Cromosoma padre) {

        for (int i = puntoA; i < puntoB; i++) {
            p[i] = padre.getGenes()[i].getAlelo();

        }
    }

    private void generaPuntos(int longitud) {
        int fin = longitud - 2;
        Random rnd = new Random();

        puntoA = (int) ((rnd.nextDouble() * fin) + 1);

        fin = longitud - 1;
        int ini = puntoA + 1;
        if (fin == ini) {
            puntoB = fin;
        } else {
            do {
                puntoB = (int) ((rnd.nextDouble() * fin) + ini);
            } while (puntoB >= longitud);
        }

    }
}

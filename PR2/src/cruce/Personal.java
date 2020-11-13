/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruce;

import cromosoma.Cromosoma;

/**
 *
 * @author R2D2
 * 
 * Guardamos la primera posicion y el resto de posiciones se van cambiando a 
 * medida que se avanza entre los padres sin repetir en los hijos.
 */
public class Personal {

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce)  {
		Cromosoma[] hijos = new Cromosoma[2];
        int[] p1 = new int[padre1.getNumGenes()];
        int[] p2 = new int[padre1.getNumGenes()];

        p1[0] = padre1.getGenes()[0].getAlelo();
        p2[0] = padre2.getGenes()[0].getAlelo();

        completar(p1, padre2);
        completar(p2, padre1);
        hijos[0] = padre1.clonar();
        hijos[1] = padre2.clonar();
        for (int i = 0; i < p1.length; i++) {
        	hijos[0].getGenes()[i].setAlelo(p1[i]);
        }
        
        for(int j = 0; j < p2.length; j++)
        {
        	hijos[1].getGenes()[j].setAlelo(p2[j]);
        }
        
        hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
        hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));
        return hijos;
    }

    private void completar(int[] p, Cromosoma padre) {
        int j = 1;
        for (int i = 0; i < padre.getNumGenes() && j < p.length; i++) {
            int elem = padre.getGenes()[i].getAlelo();
            if (!buscaElemento(p, elem)) {
                p[j] = elem;
                j++;
            }
        }

    }

    private boolean buscaElemento(int[] p, int elem) {
        int i = 0;
        boolean enc = false;

        while (i < p.length && !enc) {
            if (elem == p[i]) {
                enc = true;
            } else {
                i++;
            }
        }
        return enc;
    }

}

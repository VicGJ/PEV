package cruce;

import cromosoma.Cromosoma;

public class CodiOrdinal {

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {
		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = padre2.clonar();
		hijos[1] = padre1.clonar();
		
		int[] l1 = new int[padre1.getNumGenes()];
        int[] l2 = new int[padre2.getNumGenes()];

        inicializArray(l1, padre1.getLongCromosoma());
        inicializArray(l2, padre2.getLongCromosoma());

        int[] aux1 = obtienePosiciones(padre1, l1);
        int[] aux2 = obtienePosiciones(padre2, l2);

        cruceSimple(aux1, aux2);

        inicializArray(l1, padre1.getNumGenes());
        inicializArray(l2, padre2.getNumGenes());

        llenaHijo(l1, aux1, hijos[0]);
        llenaHijo(l2, aux2, hijos[1]);


		hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
		hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));

		return hijos;
	}

	private void llenaHijo(int[] l, int[] aux, Cromosoma hijo) {

        for (int i = 0; i < aux.length; i++) {
            hijo.getGenes()[i].setAlelo(obtieneElemento(l, aux[i]));
        }
    }

    private int obtieneElemento(int[] l, int pos) {
        int p = -1;
        int j = 0;
        int i = 0;

        while (i < l.length && p == -1) {
            if (pos == j && l[i] != -1) {
                p = l[i];
                l[i] = -1;
            }
            if (l[i] != -1) {
                j++;
            }
            i++;
        }

        return p;
    }

    private void cruceSimple(int[] aux1, int[] aux2) {
        int punto_cruce = (int) (Math.random() * (aux1.length - 1) + 1);
        int temp;

        for (int i = punto_cruce; i < aux1.length; i++) {
            temp = aux1[i];
            aux1[i] = aux2[i];
            aux2[i] = temp;
        }
    }

    private int[] obtienePosiciones(Cromosoma padre, int[] l) {
        int[] posiciones = new int[l.length];

        for (int i = 0; i < padre.getNumGenes(); i++) {
            int p = buscaElemento(l, padre.getGenes()[i].getAlelo());
            posiciones[i] = p;
        }

        return posiciones;
    }

    private int buscaElemento(int[] l, int gen) {
        int p = -1;
        int j = 0;
        int i = 0;

        while (i < l.length && p == -1) {
            if (gen == l[i]) {
                l[i] = -1;
                p = j;
            } else {
                if (l[i] != -1) {
                    j++;
                }
                i++;
            }
        }

        return p;
    }

    private void inicializArray(int[] l, int fin) {
        for (int i = 0; i < fin; i++) {
            l[i] = i;
        }

    }
}
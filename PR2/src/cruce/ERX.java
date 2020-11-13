package cruce;

import java.util.ArrayList;

import cromosoma.Cromosoma;

public class ERX {

	ArrayList<ArrayList<Integer>> tabla;
	boolean encontrada;

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {
		Cromosoma[] hijos = new Cromosoma[2];

		hijos[0] = padre1.clonar();
		hijos[1] = padre2.clonar();

		ArrayList<Integer> h1 = new ArrayList<Integer>();
		ArrayList<Integer> h2 = new ArrayList<Integer>();

		tabla = new ArrayList<ArrayList<Integer>>();
		conexion(padre1, padre2);

		boolean[] posiciones = new boolean[tabla.size()];
		ArrayList<Integer> conexiones = tabla.get(padre1.getGenes()[0].getAlelo());
		posiciones[padre1.getGenes()[0].getAlelo()] = true;
		h1.add(padre1.getGenes()[0].getAlelo());
		int cont = 0;
		int elem;
		boolean fin = false;

		while (cont < padre1.getNumGenes() - 1 && !fin) {

			elem = siguiente(posiciones, conexiones, tabla);
			if (elem != -1) {
				h1.add(elem);
				posiciones[elem] = true;
				conexiones = tabla.get(elem);
				cont++;
			} else {
				fin = true;
			}
		}

		if (!fin) {
			for (int i = 0; i < h1.size(); i++) {
				hijos[0].getGenes()[i].setAlelo(h1.get(i));
			}
		}

		posiciones = new boolean[tabla.size()];
		conexiones = tabla.get(padre2.getGenes()[0].getAlelo());
		posiciones[padre2.getGenes()[0].getAlelo()] = true;
		h2.add(padre2.getGenes()[0].getAlelo());
		cont = 0;
		elem = 0;
		fin = false;

		while (cont < padre1.getNumGenes() - 1 && !fin) {

			elem = siguiente(posiciones, conexiones, tabla);
			if (elem != -1) {
				h2.add(elem);
				posiciones[elem] = true;
				conexiones = tabla.get(elem);
				cont++;
			} else {
				fin = true;
			}
		}

		if (!fin) {
			for (int i = 0; i < h2.size(); i++) {
				hijos[1].getGenes()[i].setAlelo(h2.get(i));
			}
		}

		hijos[0].setAptitud(hijos[0].operar(hijos[0].calcularFenotipo()));
		hijos[1].setAptitud(hijos[1].operar(hijos[1].calcularFenotipo()));

		return hijos;
	}

	private void conexion(Cromosoma padre1, Cromosoma padre2) {
		ArrayList<Integer> c1 = new ArrayList<Integer>();
		ArrayList<Integer> c2 = new ArrayList<Integer>();

		for (int i = 0; i < padre1.getNumGenes(); i++) {
			c1.add(i, padre1.getGenes()[i].getAlelo());
			c2.add(i, padre2.getGenes()[i].getAlelo());
		}

		for (int i = 0; i < padre1.getNumGenes(); i++)
			tabla.add(i, null);
		for (int i = 0; i < padre1.getNumGenes(); i++) {
			int index1 = c1.indexOf(i);
			int index2 = c2.indexOf(i);

			ArrayList<Integer> mapGen = new ArrayList<Integer>();

			if (index1 == 0) {
				if (!mapGen.contains(padre1.getGenes()[padre1.getLongCromosoma() - 1].getAlelo())) {
					mapGen.add(padre1.getGenes()[padre1.getLongCromosoma() - 1].getAlelo());
				}
			} else if (!mapGen.contains(padre1.getGenes()[index1 - 1].getAlelo())) {
				mapGen.add(padre1.getGenes()[index1 - 1].getAlelo());
			}
			if (index1 == padre1.getLongCromosoma() - 1) {
				if (!mapGen.contains(padre1.getGenes()[0].getAlelo())) {
					mapGen.add(padre1.getGenes()[0].getAlelo());
				}
			} else if (!mapGen.contains(padre1.getGenes()[index1 + 1].getAlelo())) {
				mapGen.add(padre1.getGenes()[index1 + 1].getAlelo());
			}

			if (index2 == 0) {
				if (!mapGen.contains(padre2.getGenes()[padre2.getLongCromosoma() - 1].getAlelo())) {
					mapGen.add(padre2.getGenes()[padre2.getLongCromosoma() - 1].getAlelo());
				}
			} else if (!mapGen.contains(padre2.getGenes()[index2 - 1].getAlelo())) {
				mapGen.add(padre2.getGenes()[index2 - 1].getAlelo());
			}
			if (index2 == padre2.getLongCromosoma() - 1) {
				if (!mapGen.contains(padre2.getGenes()[0].getAlelo())) {
					mapGen.add(padre2.getGenes()[0].getAlelo());
				}
			} else if (!mapGen.contains(padre2.getGenes()[index2 + 1].getAlelo())) {
				mapGen.add(padre2.getGenes()[index2 + 1].getAlelo());
			}

			tabla.set(i, mapGen);
		}
	}

	private int siguiente(boolean[] posiciones, ArrayList<Integer> conexiones, ArrayList<ArrayList<Integer>> mapeado) {
		int min = Integer.MAX_VALUE;
		int p = -1;

		for (int i = 0; i < conexiones.size(); i++) {
			if (!posiciones[conexiones.get(i)]) {
				if (mapeado.get(conexiones.get(i)).size() < min) {
					p = conexiones.get(i);
					min = mapeado.get(conexiones.get(i)).size();
				}
			}
		}

		return p;
	}
}
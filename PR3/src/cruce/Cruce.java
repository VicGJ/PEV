package cruce;

import controller.Arbol;
import cromosoma.Cromosoma;

import java.util.ArrayList;

public class Cruce {
	private static final double PROB_FUNC = 0.9;

	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2) {
		Cromosoma hijos[] = new Cromosoma[2];

		// copiamos los cromosomas padre en los hijos
		hijos[0] = padre1.clonar();
		hijos[1] = padre2.clonar();
		
		ArrayList<Arbol> nodos_selec1 = new ArrayList<Arbol>();
		ArrayList<Arbol> nodos_selec2 = new ArrayList<Arbol>();

		// Seleccionamos los nodos más relevantes según la probabilidad
		// 0.9 se cruzará en una función
		// resto se cruzará en un terminal
		nodos_selec1 = obtieneNodos(padre1.getArbol().copia());
		nodos_selec2 = obtieneNodos(padre2.getArbol().copia());

		// obtenemos los puntos de cruce apartir de los nodos seleccionados
		int puntoCruce1 = (int) (Math.random() * nodos_selec1.size());
		int puntoCruce2 = (int) (Math.random() * nodos_selec2.size());

		// Cogemos los nodos de cruce seleccionados
		Arbol temp1 = nodos_selec1.get(puntoCruce1).copia();
		Arbol temp2 = nodos_selec2.get(puntoCruce2).copia();

		// realizamos el corte sobre los arboles de los hijos
		corte(hijos[0], temp2, puntoCruce1, temp1.isEsRaiz());
		corte(hijos[1], temp1, puntoCruce2, temp2.isEsRaiz());
		
		
		int nodos = hijos[0].getArbol().obtieneNodos(hijos[0].getArbol(), 0);
		hijos[0].getArbol().setNumNodos(nodos);
		nodos = hijos[1].getArbol().obtieneNodos(hijos[1].getArbol(), 0);
		hijos[1].getArbol().setNumNodos(nodos);
		
		// Finalmente se evalúan 
		
		hijos[0].setFenotipo(hijos[0].calcularFenotipo());
		hijos[1].setFenotipo(hijos[1].calcularFenotipo());
		
		hijos[0].setAptitud(hijos[0].evalua());
		hijos[1].setAptitud(hijos[1].evalua());

		return hijos;
	}

	private void corte(Cromosoma hijo, Arbol temp, int puntoCruce, boolean esRaiz) {
		if (!esRaiz) {
			// dependiendo de qué tipo era el nodo que ya no va a estar, se inserta el nuevo
			hijo.getArbol().insertTerminal(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		} else {
			hijo.getArbol().insertFuncion(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		}
	}

	private ArrayList<Arbol> obtieneNodos(Arbol arbol) {
		ArrayList<Arbol> nodos = new ArrayList<Arbol>(); // Obtenemos una probabilidad al azar
		if (seleccionaFunciones()) {// Si devuelve true, el corte se hará en una función
			arbol.getFunciones(arbol.getHijos(), nodos);
			if (nodos.size() == 0) {// Si no existen funciones, se seleccionan los terminales
				arbol.getTerminales(arbol.getHijos(), nodos);
			}
		} else {// Si devuelve false, el corte se hará por un terminal
			arbol.getTerminales(arbol.getHijos(), nodos);
		}
		return nodos;

	}

	private boolean seleccionaFunciones() {
		double prob = Math.random();
		if (prob < PROB_FUNC) {
			return true;
		} else {
			return false;
		}
	}
}
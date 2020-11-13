package Mutacion;

import java.util.Random;
import java.util.Stack;

import cromosoma.Cromosoma;
/**
*
* @author R2D2
* Coge dos puntos aleatorios, los mete en una pila y los saca para introducirlos
* de nuevo entre esos puntos. Al ser una pila, los introduce a la inversa.
* Se calcula el fitness y se escoge entre el nuevo calculado o el otro calculado
* mediante la misma técnica con otros dos puntos aleatorios. El 
* mejor es el que se queda.
*/
public class PersonalMut  {

	public int mutar(Cromosoma[] pob, double probMut, Random random) {
        Random rand = new Random();
        int mutaciones = 0;
        for (int i = 0; i < pob.length; i++) {
            double p = rand.nextDouble();
            if (p < probMut) {
            	mutaciones++;
                Cromosoma c = pob[i];
                int puntoA = rand.nextInt(c.getNumGenes());
                int puntoB = rand.nextInt(c.getNumGenes() - puntoA);
                int puntoC = rand.nextInt(c.getNumGenes());
                int puntoD = rand.nextInt(c.getNumGenes() - puntoC);
                puntoB += puntoA + 1;
                puntoD += puntoC + 1;
                Stack<Integer> s = new Stack<Integer>();
                Stack<Integer> s2 = new Stack<Integer>();
                for (int j = puntoA; j < puntoB; j++) {
                    s.push(c.getGenes()[j].getAlelo());
                }
                for (int g = puntoC; g < puntoD; g++) {
                    s2.push(c.getGenes()[g].getAlelo());
                }
                Cromosoma aux = c.clonar();
                Cromosoma aux2 = c.clonar();
                for (int k = puntoA; k < puntoB; k++) {
                    aux.getGenes()[k].setAlelo(s.pop());
                }
                for (int l = puntoC; l < puntoD; l++) {
                    aux2.getGenes()[l].setAlelo(s2.pop());
                }
                double fitAux = aux.operar(aux.calcularFenotipo());
                double fitAux2 = aux2.operar(aux2.calcularFenotipo());
                if (fitAux > fitAux2) {
                	pob[i] = aux2.clonar();
                }
                else{
                	pob[i] = aux.clonar();
                }
                pob[i].setAptitud(pob[i].operar(pob[i].calcularFenotipo()));
            }

        }
        return mutaciones;
    }

}

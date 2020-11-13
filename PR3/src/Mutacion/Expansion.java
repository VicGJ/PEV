/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutacion;

import cromosoma.Cromosoma;
import controller.Arbol;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author R2D2
 */
public class Expansion {

    
    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
        int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            Arbol a = poblacion[i].getArbol().copia();
            ArrayList<Arbol> terminales = new ArrayList<Arbol>();
            a.getTerminales(a.getHijos(), terminales);
            double prob = Math.random();
            if (prob < probMut) {
                double aux = Math.random() * terminales.size();
                Arbol a2 = terminales.get((int)aux);
                if(a2.getProfundidad() < a2.getMax_profundidad()) {
                    a2.inicializacionCreciente(a2.getProfundidad());
                    a.insertFuncion(a.getHijos(), a2, (int)aux, 0);
                    
                    int nodos = a.obtieneNodos(a.copia(), 0);
                    a.setNumNodos(nodos);
                    poblacion[i].setArbol(a);
                    poblacion[i].evalua();
                    mut++;
                }
            }
        }
        return mut;
    }

}

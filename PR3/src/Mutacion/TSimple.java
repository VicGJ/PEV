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
public class TSimple {
    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
        int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            Arbol a = poblacion[i].getArbol().copia();
            ArrayList<Arbol> terminales = new ArrayList<>();
            a.getTerminales(a.getHijos(), terminales);
            double prob = Math.random();
            
            if (prob < probMut) {
                int numAle = rnd.nextInt(terminales.size());
                int terminalNuevo = rnd.nextInt(Cromosoma.terminales.length);
                String nuevo = Cromosoma.terminales[terminalNuevo];
                
                terminales.get(numAle).setValor(nuevo);
                a.insertTerminal(a.getHijos(), terminales.get(numAle), numAle, 0);
                poblacion[i].setArbol(a.copia());
                poblacion[i].evalua();
                mut++;
            }
        }
        return mut;
    }
}

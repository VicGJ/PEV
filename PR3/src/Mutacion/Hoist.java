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
public class Hoist {

    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
       int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            double prob = rnd.nextDouble();
            if (prob < probMut) {
                ArrayList<Arbol> funciones = new ArrayList<Arbol>();
                poblacion[i].getArbol().getFunciones(poblacion[i].getArbol().getHijos(), funciones);
                if(funciones.size() > 0){
                    double aux = Math.random() * funciones.size();
                    poblacion[i].setArbol(funciones.get((int)aux));
                    poblacion[i].evalua();
                    mut++;
                }
            }
        }
        return mut;
    }
}
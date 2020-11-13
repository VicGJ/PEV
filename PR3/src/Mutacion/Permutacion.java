/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutacion;

import cromosoma.Cromosoma;
import controller.Arbol;
import java.util.Random;

/**
 *
 * @author R2D2
 */
public class Permutacion {
    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
        int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            double prob = rnd.nextDouble();
            Arbol a = poblacion[i].getArbol();
            if (prob < probMut) {
                int pos = rnd.nextInt(a.getNumHijos() + 1);
                Arbol subArbol = a.at(pos);

                if (subArbol.getNumHijos() == 2) {
                    Arbol subArbol2 = subArbol.getHijos().get(0);
                    subArbol.getHijos().set(0, subArbol.getHijos().get(1));
                    subArbol.getHijos().set(1, subArbol2);
                }

                poblacion[i].evalua();
                mut++;
            }
        }
        return mut;
    }

}

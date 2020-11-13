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
public class Contraccion {
  
    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
        int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            Arbol a = poblacion[i].getArbol().copia();
            ArrayList<Arbol> funciones = new ArrayList<Arbol>();
            a.getFunciones(a.getHijos(), funciones);
            double prob = Math.random();
            if (prob < probMut) {
                if (funciones.size() > 0) {
                    double aux = Math.random() * funciones.size();
                    Arbol a2 = funciones.get((int)aux);
                    ArrayList<Arbol> hijos = new ArrayList<Arbol>();
                    a2.setHijos(hijos);
                    a2.setNumHijos(0);
                    a2.setEsHoja(true);
                    int terminal = rnd.nextInt(Cromosoma.terminales.length);
                    String nuevo = Cromosoma.terminales[terminal];
                    a2.setValor(nuevo);
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

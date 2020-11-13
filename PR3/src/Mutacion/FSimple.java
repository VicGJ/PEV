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
public class FSimple {
	
    public int mutar(Cromosoma[] poblacion, double probMut, Random rnd) {
        int mut = 0;
        for (int i = 0; i < poblacion.length; i++) {
            double prob = rnd.nextDouble();
            Arbol a = poblacion[i].getArbol().copia();
            if (prob < probMut) {
                ArrayList<Arbol> funciones = new ArrayList<>();
                a.getFunciones(a.getHijos(), funciones);
               
                if (!funciones.isEmpty()) {
                    int numAle = rnd.nextInt(funciones.size());
                    
                    if(funciones.get(numAle).getValor().equals("OR"))
                    {
                        funciones.get(numAle).setValor("AND");
                    }
                    else if(funciones.get(numAle).getValor().equals("AND"))
                    {
                        funciones.get(numAle).setValor("OR");
                    }
                    
                    a.insertFuncion(a.getHijos(), funciones.get(numAle), numAle, 0);
                    poblacion[i].setArbol(a.copia());
                    poblacion[i].evalua();
                    mut++;
                }
            }
        }
        return mut;
    }

}

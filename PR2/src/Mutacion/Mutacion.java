/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutacion;

import cromosoma.Cromosoma;

/**
 *
 * @author R2D2
 */
public abstract class Mutacion {
    protected double prob_mutacion;

    public Mutacion(double prob) {
        this.prob_mutacion = prob;
    }

    public abstract int mutar(Cromosoma[] poblacion);
}

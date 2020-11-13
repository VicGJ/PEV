/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cromosoma.Cromosoma;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author R2D2
 */
public class Arbol {

    private String valor;
    private ArrayList<Arbol> hijos;
    private int numHijos;
    private int numNodos;
    private int max_prof;
    private int profundidad;
    private boolean useIF;
    private boolean esHoja;
    private boolean esRaiz;

    public Arbol() {
        hijos = new ArrayList<Arbol>();
        valor = "";
        numHijos = 0;
        numNodos = 0;
    }

    public Arbol(String v) {
        valor = v;
        hijos = new ArrayList<Arbol>();
        numHijos = 0;
    }

    public Arbol(int p, boolean usoIf) {
        valor = "";
        hijos = new ArrayList<Arbol>();
        numHijos = 0;
        max_prof = p;
        setProfundidad(0);
        numNodos = 0;
        useIF = usoIf;
    }

    private void setProfundidad(int p) {
        this.profundidad = p;
    }

    public String getValor() {
        return valor;
    }

    public ArrayList<Arbol> getHijos() {
        return hijos;
    }

    public int getNumHijos() {
        return numHijos;
    }

    public int getNumNodos() {
        return numNodos;
    }

    public int getMax_profundidad() {
        return max_prof;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public boolean useIf() {
        return useIF;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public boolean isEsRaiz() {
        return esRaiz;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setHijos(ArrayList<Arbol> hijos) {
        this.hijos = hijos;
    }

    public void setNumHijos(int nHijos) {
        this.numHijos = nHijos;
    }

    public void setNumNodos(int nNodos) {
        this.numNodos = nNodos;
    }

    public void setMax_profundidad(int max_profundidad) {
        this.max_prof = max_profundidad;
    }

    public void setUseIf(boolean esIf) {
        this.useIF = esIf;
    }

    public void setEsHoja(boolean hoja) {
        this.esHoja = hoja;
    }

    public void setEsRaiz(boolean raiz) {
        this.esRaiz = raiz;
    }

    // Devuelve el arbol en forma de array
    public ArrayList<String> toArray() {
        ArrayList<String> array = new ArrayList<String>();
        toArrayAux(array, this);
        return array;
    }

    // Insertar un valor en el arbol (nodo simple)
    public Arbol insert(String v, int index) {
        Arbol a = new Arbol(v);
        if (index == -1) {
            hijos.add(a);
            numHijos = hijos.size();
        } else {
            hijos.set(index, a);
        }
        return a;
    }

    // Insertar un arbol en otro arbol.
    public void insert(Arbol a, int index) {
        if (index == -1) {
            hijos.add(a);
            numHijos = hijos.size();
        } else {
            hijos.set(index, a);
        }
    }

    public Arbol at(int index) {
        return at(this, 0, index);
    }

    private Arbol at(Arbol a, int pos, int index) {
        Arbol s = null;
        if (pos >= index) {
            s = a;
        } else if (a.getNumHijos() > 0) {
            for (int i = 0; i < a.getNumHijos(); i++) {
                if (s == null) {
                    s = at(a.getHijos().get(i), pos + i + 1, index);
                }
            }
        }
        return s;
    }

    private void toArrayAux(ArrayList<String> array, Arbol a) {
        array.add(a.valor);
        for (int i = 0; i < a.hijos.size(); i++) {
            toArrayAux(array, a.hijos.get(i));
        }
    }

    public int inicializacionCompleta(int p, int nodos) {
        int n = nodos;
        int nHijos = 2;
        if (p < max_prof) {
            setProfundidad(p);
            Random rnd = new Random();
            int func = 0;
            if (useIF) {
                func = rnd.nextInt(Cromosoma.funciones.length);
            } else {
                func = rnd.nextInt(Cromosoma.funciones.length - 1);
            }
            this.valor = Cromosoma.funciones[func];
            this.setEsRaiz(true);
            if (valor.equals("IF")) {
                nHijos = 3;
            }
            if (valor.equals("NOT")) {
                nHijos = 1;
            }
            for (int i = 0; i < nHijos; i++) {
                Arbol hijo = new Arbol(max_prof, useIF);
                esRaiz = true;
                n++;
                n = hijo.inicializacionCompleta(p + 1, n);
                hijos.add(hijo);
                numHijos++;
            }
        } else {
            setProfundidad(p);
            Random rnd = new Random();
            int terminal;
            this.setEsHoja(true);
            terminal = rnd.nextInt(Cromosoma.terminales.length);
            valor = Cromosoma.terminales[terminal];
            esHoja = true;
            numHijos = 0;
        }
        setNumNodos(n);
        return n;
    }

    private int creaHijos(int p, int nodos) {
        int n = nodos;
        int nHijos = 2;
        if (valor.equals("IF")) {
            nHijos = 3;
        }
        if (valor.equals("NOT")) {
            nHijos = 1;
        }
        for (int i = 0; i < nHijos; i++) {
            Arbol hijo = new Arbol(max_prof, useIF);
            n++;
            n = hijo.inicializacionCrecienteAux(p + 1, n);
            hijos.add(hijo);
            numHijos++;
        }
        return n;
    }

    private int inicializacionCrecienteAux(int p, int nodos) {
        int n = nodos;
        Random rnd = new Random();
        //Cambio 3: Puesto el -1
        if (p < max_prof - 1) {
            setProfundidad(p);
            int rango;

            if (useIF) {
                rango = Cromosoma.funciones.length + Cromosoma.terminales.length;

                int pos = rnd.nextInt(rango);

                if (pos >= Cromosoma.funciones.length) {
                    pos -= Cromosoma.funciones.length;
                    valor = Cromosoma.terminales[pos];
                    esHoja = true;

                } else {
                    valor = Cromosoma.funciones[pos];
                    esRaiz = true;
                    n = creaHijos(p, n);
                }
            } else {
                rango = Cromosoma.funciones.length + Cromosoma.terminales.length - 1;

                int pos = rnd.nextInt(rango);

                if (pos >= (Cromosoma.funciones.length - 1)) {
                    pos -= Cromosoma.funciones.length - 1;
                    valor = Cromosoma.terminales[pos];
                    esHoja = true;
                } else {
                    valor = Cromosoma.funciones[pos];
                    esRaiz = true;
                    n = creaHijos(p, n);
                }
            }
        } else {
            setProfundidad(p);
            int terminal;
            terminal = rnd.nextInt(Cromosoma.terminales.length);
            valor = Cromosoma.terminales[terminal];
            esHoja = true;
        }
        setNumNodos(n);
        return n;
    }

    public void inicializacionCreciente(int p) {
        int n = 0;
        Random rnd = new Random();
        if (p < max_prof) {
            setProfundidad(p);
            int func = 0;

            if (useIF) {
                func = rnd.nextInt(Cromosoma.funciones.length);
            } else {
                func = rnd.nextInt(Cromosoma.funciones.length - 1);
            }

            this.valor = Cromosoma.funciones[func];
            esRaiz = true;
            n = creaHijos(p, n);
        } else {
            setProfundidad(p);
            int terminal;
            terminal = rnd.nextInt(Cromosoma.terminales.length);
            valor = Cromosoma.terminales[terminal];
            esHoja = true;
        }
        setNumNodos(n);
    }

    /**
     * Devuelve los nodos hoja del árbol
     *
     * @param hijos Hijos del árbol a analizar
     * @param nodos Array donde se guardan los terminales
     */
    public void getTerminales(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {
        for (int i = 0; i < hijos.size(); i++) {
            if (hijos.get(i).isEsHoja()) {
                nodos.add(hijos.get(i).copia());
            } else {
                getTerminales(hijos.get(i).getHijos(), nodos);
            }
        }
    }

    public int insertTerminal(ArrayList<Arbol> list_hijos, Arbol terminal, int index, int pos) {
        int p = pos;
        for (int i = 0; i < list_hijos.size() && p != -1; i++) {
            if (list_hijos.get(i).isEsHoja() && (p == index)) {
                list_hijos.set(i, terminal.copia());
                p = -1;
            } else if (list_hijos.get(i).esHoja && (p != index)) {
                p++;
            } else {
                p = insertTerminal(list_hijos.get(i).hijos, terminal, index, p);
            }
        }
        return p;
    }

    public int insertFuncion(ArrayList<Arbol> list_hijos, Arbol terminal, int index, int pos) {
        int p = pos;
        for (int i = 0; i < list_hijos.size() && p != -1; i++) {
            if (list_hijos.get(i).esRaiz && (p == index)) {
//terminal.padre = list_hijos.get(i).padre;
                list_hijos.set(i, terminal.copia());
                p = -1;
            } else if (list_hijos.get(i).esRaiz && (p != index)) {
                p++;
                p = insertFuncion(list_hijos.get(i).hijos, terminal, index, p);
            }
        }
        return p;
    }

    /**
     * Devuelve los nodos internos del árbol
     *
     * @param hijos Hijos del árbol a analizar
     * @param nodos Array donde se guardan las funciones
     */
    public void getFunciones(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {

        for (int i = 0; i < hijos.size(); i++) {
            if (hijos.get(i).isEsRaiz()) {
                nodos.add(hijos.get(i).copia());
                getFunciones(hijos.get(i).hijos, nodos);
            }
        }

    }

    public Arbol copia() {
        Arbol copia = new Arbol(this.max_prof, this.useIF);

        copia.setEsHoja(this.esHoja);
        copia.setEsRaiz(this.esRaiz);
        copia.setNumHijos(this.numHijos);
        copia.setNumNodos(this.numNodos);
        copia.setProfundidad(this.profundidad);
        copia.setValor(this.valor);
        ArrayList<Arbol> aux = new ArrayList<Arbol>();
        aux = copiaHijos();
        copia.setHijos(aux);

        return copia;
    }

    private ArrayList<Arbol> copiaHijos() {
        ArrayList<Arbol> array = new ArrayList<Arbol>();

        for (int i = 0; i < this.hijos.size(); i++) {
            array.add(this.hijos.get(i).copia());
        }

        return array;
    }

    public int obtieneNodos(Arbol nodo, int n) {
        if (nodo.esHoja) {
            return n;
        }

        if (nodo.valor.equals("IF")) {
            n = obtieneNodos(nodo.hijos.get(0), n + 1);
            n = obtieneNodos(nodo.hijos.get(1), n + 1);
            n = obtieneNodos(nodo.hijos.get(2), n + 1);
        } else if (nodo.valor.equals("AND") || nodo.valor.equals("OR")) {
            n = obtieneNodos(nodo.hijos.get(0), n + 1);
            n = obtieneNodos(nodo.hijos.get(1), n + 1);
        } else {
            n = obtieneNodos(nodo.hijos.get(0), n + 1);
        }
        return n;
    }
}

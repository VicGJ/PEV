package controller;

import java.util.Arrays;
import java.util.Random;


import cromosoma.Cromosoma;
import cromosoma.Cromosoma2;
import selecciones.*;
import cruce.*;
import Interfaz.Vista;
import Mutacion.*;


public class AGenetico {

	Cromosoma[] pob, elite;
	int tamPob;
	int numMaxGen; 
	Cromosoma elMejor;
	int posMejor;
	double probCruce; 
	double probMut; 
	private int seleccion;
	private int cruce;
	private int mutacion;
	private boolean elitismo;

	private long semilla;
	private Random random;
	private Matrices matrices;
	private int[][] f;
	private int[][] d;
	private int n;

	private int totalMutaciones;
	private int totalCruces;

	double mejorAbsoluto[];
	double mejor[];
	double media[];
	
	public AGenetico(int tamPoblacion, int numGeneraciones,
			int tipoSeleccion, int tipoCruce, double probabilidadCruce, int tipoMutacion,
			double probabilidadMutacion, boolean hayElitismo, int fichero) {

		
		this.tamPob = tamPoblacion;
		this.numMaxGen = numGeneraciones;
		this.probCruce = probabilidadCruce;
		this.probMut = probabilidadMutacion;
		this.seleccion = tipoSeleccion;
		this.cruce = tipoCruce;
		this.mutacion = tipoMutacion;
		this.elitismo = hayElitismo;
		this.semilla = System.currentTimeMillis();
		this.random = new Random();
		this.random.setSeed(semilla);
		matrices = new Matrices(fichero);
		this.d = matrices.getDist();
		this.f = matrices.getFluj();
		this.n = matrices.getTam();

		this.totalMutaciones = 0;
		this.totalCruces = 0;
		
		this.mejorAbsoluto = new double[this.numMaxGen];
		this.mejor = new double[this.numMaxGen];
		this.media = new double[this.numMaxGen];
		
		this.pob = new Cromosoma[tamPob];

		if (this.elitismo) {
			elite = new Cromosoma[(int) (tamPob * 0.02)];
		}
	}

	public void ejecutar() {
		inicializa();

		evaluarPoblacion();

		for (int i = 0; i < this.numMaxGen; i++) {

			if (this.elitismo)
				this.elite = separarMejores();

			this.pob = seleccion(this.seleccion, 2);
			
			reproducir(this.cruce);
		
			this.totalMutaciones += mutar(this.pob, this.mutacion, probMut, random);
			
			evaluarPoblacion();

			if (this.elitismo) {
				incluirMejores();
				evaluarPoblacion();
			}
			actualizarDatos(i);
		}

		Vista.addData(mejorAbsoluto, mejor, media, this.numMaxGen);

	}

	private Cromosoma[] separarMejores() {
		Cromosoma[] mejores = new Cromosoma[this.elite.length];

		double[] aptitudAdap = new double[this.tamPob];

		for (int i = 0; i < this.tamPob; i++) {
			aptitudAdap[i] = pob[i].getAdaptacion();
		}

		Arrays.sort(aptitudAdap);

		for (int i = 0; i < this.elite.length; i++) {
			for (int j = 0; j < this.tamPob; j++) {
				if (aptitudAdap[this.tamPob - 1 - i] == pob[j].getAdaptacion()) {
					mejores[i] = pob[j];
				}
			}
		}
		return mejores;
	}

	private void incluirMejores() {
		double[] aptitudAdap = new double[this.tamPob];

		for (int i = 0; i < this.tamPob; i++) {
			aptitudAdap[i] = pob[i].getAdaptacion();
		}
		
		Arrays.sort(aptitudAdap);
		boolean cambiado = false;
		for (int i = 0; i < this.elite.length; i++) {
			cambiado = false;
			for (int j = 0; j < this.tamPob && !cambiado; j++) {
				if (aptitudAdap[i] == pob[j].getAdaptacion()) {
					pob[j] = elite[i].clonar();
					cambiado = true;
				}
			}
		}
	}

	private void minimizar_maximizar() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < this.tamPob; i++) {
			if (pob[i].getAptitud() > max) {
				max = pob[i].getAptitud();
			}
			if (pob[i].getAptitud() < min) {
				min = pob[i].getAptitud();
			}
		}

		max = max * 1.05;
		min = min * 0.95;

		for (int i = 0; i < this.tamPob; i++) {
			pob[i].setAdaptacion(max - pob[i].getAptitud());
		}
	}

	private void mejorAbsoluto(int generacion) {
		if (generacion == 0) {
			mejorAbsoluto[0] = this.elMejor.getAptitud();
		} else {
			if (mejorAbsoluto[generacion - 1] > this.elMejor.getAptitud()) {
				this.mejorAbsoluto[generacion] = this.elMejor.getAptitud();
			} else {
				this.mejorAbsoluto[generacion] = this.mejorAbsoluto[generacion - 1];
			}
			
		}
	}

	private void mejor(int generacion) {
		this.mejor[generacion] = this.elMejor.getAptitud();
	}

	private void media(int generacion) {
		double total = 0;

		for (int i = 0; i < this.tamPob; i++) {
			total += this.pob[i].getAptitud();
		}
		this.media[generacion] = total / tamPob;
	}

	private void actualizarDatos(int generacionActual) {

		mejorAbsoluto(generacionActual);
		mejor(generacionActual);
		media(generacionActual);
	}
	
	private void evaluarPoblacion() {
		minimizar_maximizar();
		double fitnessAux;
		
		fitnessAux = 0;
		for (int i = 0; i < this.tamPob; i++) {
			fitnessAux += this.pob[i].getAdaptacion();
		}

		pob[0].evaluarCromosoma(fitnessAux, 0.0);
		this.elMejor = this.pob[0].clonar();

		for (int i = 1; i < this.tamPob; i++) {
			pob[i].evaluarCromosoma(fitnessAux, pob[i - 1].getPunt_acum());

			if (this.pob[i].getAptitud() < this.elMejor.getAptitud()) {
				this.posMejor = i;
				this.elMejor = this.pob[i].clonar();
			}
		}

	}

	private void inicializa() {
		for (int i = 0; i < this.tamPob; i++) {
			this.pob[i] = new Cromosoma2(d, f, random, this.n);
			this.pob[i].inicializarCromosoma();
		}
		this.elMejor = new Cromosoma2(d, f, random, this.n);
		if (this.elitismo) {
			for (int i = 0; i < (int) (tamPob * 0.02); i++) {
				this.elite[i] = new Cromosoma2(d, f, random, this.n);
			}
		}
	}
	
	public Cromosoma[] seleccion(int tipo, double param) {
		switch (tipo) {
		case 0:
			return new Ruleta().seleccion(this.tamPob, this.pob);
		case 1:
			return new Estocastica().seleccion(this.tamPob, this.pob);
		case 2:
			return new Torneo().seleccion(this.tamPob, this.pob);
		case 3:
			return new TorneoProb(param).seleccion(this.tamPob, this.pob);
		case 4:
			return new Truncamiento(param).seleccion(this.tamPob, this.pob);
		case 5:
			return new Ranking().seleccion(this.tamPob, this.pob);
		case 6:
			return new Restos().seleccion(this.tamPob, this.pob);
		default:
			return new Ruleta().seleccion(this.tamPob, this.pob);
		}
	}
	
	public void reproducir(int tipo) {
		int selCruce[] = new int[this.tamPob];
		
        int numSelCruce = 0;
        double prob;
        Random rnd = new Random();

        for (int i = 0; i < tamPob; i++) {
            prob = rnd.nextDouble();
            if (prob < probCruce) {
                selCruce[numSelCruce] = i;
                numSelCruce++;
            }
        }
        
        if ((numSelCruce % 2) == 1) {
            numSelCruce--;
        }
        
        Random r = new Random();
		int punto_cruce = r.nextInt(pob[0].getLongCromosoma());
		Cromosoma[] hijos;
		for (int i = 0; i < numSelCruce; i += 2) {
			hijos = cruce(pob[selCruce[i]], pob[selCruce[i + 1]], tipo, punto_cruce);
			pob[selCruce[i]] = hijos[0].clonar();
			pob[selCruce[i + 1]] = hijos[1].clonar();
			totalCruces++;
		}
	}

	private Cromosoma[] cruce(Cromosoma cromosoma, Cromosoma cromosoma2, int tipo, int puntoCruce) {
        switch (tipo) {
        case 0:
			return new CX().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 1:
			return new CodiOrdinal().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 2:
			return new ERX().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 3:
			return new OX().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 4:
			return new OXPos().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 5:
			return new PMX().cruzar(cromosoma, cromosoma2, puntoCruce);
		case 6:
			return new Personal().cruzar(cromosoma, cromosoma2, puntoCruce);
		default:
			return new PMX().cruzar(cromosoma, cromosoma2, puntoCruce);
        }
	}
	
	private int mutar(Cromosoma[] cromosoma, int tipo, double prob, Random rnd) {
		switch (tipo) {
		case 0:
			return new Insercion().mutar(cromosoma, prob, rnd);
		case 1:
			return new Intercambio().mutar(cromosoma, prob, rnd);
		case 2:
			return new Inversion().mutar(cromosoma, prob, rnd);
		case 3:
			return new Heuristica().mutar(cromosoma, prob, rnd);
		case 4:
			return new PersonalMut().mutar(cromosoma, prob, rnd);
		default:
			return new Heuristica().mutar(cromosoma, prob, rnd);
		}
		
	}
	
	public String toString() {
        String cadena = " ";
        cadena += "* Mejor absoluto: \n";
        cadena += elMejor.toString() + "\n";
        cadena += "\n";

        cadena += "Numero total de cruces: \n";
        cadena += this.totalCruces + "\n\n";

        cadena += "Numero total de mutaciones: \n";
        cadena += this.totalMutaciones + "\n\n";

        return cadena;
    }

}

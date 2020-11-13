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

	private long seed;
	private Random random;

	private int totalMutaciones;
	private int totalCruces;

	double mejorAbsoluto[];
	double mejor[];
	double media[];
	
	private int tipoBloating;
	private boolean bloating;
	private int creacion;
	private int profundidad;
	private int tipoMultiplexor;
	private boolean useIf;

	public AGenetico(int tamPoblacion, int numGeneraciones,
			int tipoSeleccion, double probabilidadCruce, int tipoMutacion,
			double probabilidadMutacion, boolean hayElitismo, boolean bloating, int tipoBloating, int creacion,
			boolean useIf, int tipoMultiplexor, int profundidad) {

		
		this.tamPob = tamPoblacion;
		this.numMaxGen = numGeneraciones;
		this.probCruce = probabilidadCruce;
		this.probMut = probabilidadMutacion;
		this.seleccion = tipoSeleccion;
		this.mutacion = tipoMutacion;
		this.elitismo = hayElitismo;
		this.seed = System.currentTimeMillis();
		this.random = new Random();
		this.random.setSeed(seed);
		
		this.totalMutaciones = 0;
		this.totalCruces = 0;
		
		this.mejorAbsoluto = new double[this.numMaxGen];
		this.mejor = new double[this.numMaxGen];
		this.media = new double[this.numMaxGen];
		
		this.pob = new Cromosoma[tamPob];

		if (this.elitismo) {
			elite = new Cromosoma[(int) (tamPob * 0.02)];
		}
		
		this.profundidad = profundidad;
		this.creacion = creacion;
		this.useIf = useIf;
		this.tipoMultiplexor = tipoMultiplexor;
		this.tipoBloating = tipoBloating;
		this.bloating = bloating;
	}

	public void ejecutar() {
		inicializa();

		evaluarPoblacion();
		
		if (this.bloating) {
			bloating();
		}
		
		for (int i = 0; i < this.numMaxGen; i++) {

			if (this.elitismo)
				this.elite = separarMejores();

			this.pob = seleccion(this.seleccion, 2);
			
			reproducir(this.cruce);
		
			this.totalMutaciones += mutar(this.pob, this.mutacion, probMut, random);
			
			evaluarPoblacion();
			if (this.bloating) {
				bloating();
			}
			
			if (this.elitismo) {
				incluirMejores();
				evaluarPoblacion();
			}
			actualizarDatos(i);
		}
		Vista.addData(mejorAbsoluto, mejor, media, this.numMaxGen);
	}

	private void bloating() {
		if (this.tipoBloating != 2) {
            double media, mediaTam;
            double aux = 0, aux2 = 0;
            for (int i = 0; i < tamPob; i++) {
                aux += this.pob[i].getAdaptacion();
                aux2 += this.pob[i].getArbol().getNumNodos();
            }

            media = (aux / tamPob);
            mediaTam = (aux2 / tamPob);

            if (this.tipoBloating == 0) {
            	tarpeianBloating(mediaTam, 2);
            } else {
                penalizacionBloating(media, mediaTam);
            }

        }
	}
	
	private void tarpeianBloating(double mediaTam, int n) {
        double prob = 1 / n;
        Random rnd = new Random();
        for (int i = 0; i < tamPob; i++) {
            Arbol a = this.pob[i].getArbol();
            if (a.getNumNodos() > mediaTam) {
                double p = rnd.nextDouble();
                if (p < prob) {
                	this.pob[i] = new Cromosoma2(profundidad, creacion, useIf, tipoMultiplexor);
                }
            }
        }
    }

    private void penalizacionBloating(double media, double mediaTam) {
        double var = 0;
        double cov = 0;

        for (int i = 0; i < tamPob; i++) {
            Cromosoma c = this.pob[i];
            cov += (c.getAdaptacion() - media) * (c.getArbol().getNumNodos() - mediaTam);
            var += (c.getArbol().getNumNodos() - mediaTam) * (c.getArbol().getNumNodos() - mediaTam);
        }

        var = var / tamPob;
        cov = cov / tamPob;
        double k = (cov / var);
        for (int j = 0; j < tamPob; j++) {
            Cromosoma c = this.pob[j];
            double fitness = c.getAdaptacion() - k * c.getArbol().getNumNodos();
            c.setAdaptacion(fitness);
        }
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
			pob[i].setAdaptacion(pob[i].getAptitud() + min);
		}
		

	}

	private void mejorAbsoluto(int generacion) {
		if (generacion == 0) {
			mejorAbsoluto[0] = this.elMejor.getAptitud();
		} else {

			if (mejorAbsoluto[generacion - 1] < this.elMejor.getAptitud()) {
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
			pob[i].evaluarCromosoma(fitnessAux, pob[i - 1].getPuntAcum());

			if (this.pob[i].getAptitud() > this.elMejor.getAptitud()) {
					this.posMejor = i;
					this.elMejor = this.pob[i].clonar();
			}
		}
	}

	private void inicializa() {
		if (this.creacion == 2) {
			int numGrupos = this.profundidad - 1;
			int tamGrupo = this.tamPob / numGrupos;

			for (int i = 0; i < this.tamPob; i++) {
				int profundidad = (i / tamGrupo) + 2; 
				this.pob[i] = new Cromosoma2(profundidad, creacion, useIf, tipoMultiplexor);
				this.pob[i].inicializarCromosoma();
			}

		} else {
			for (int i = 0; i < this.tamPob; i++) {
				this.pob[i] = new Cromosoma2(this.profundidad, creacion, useIf, tipoMultiplexor);
				this.pob[i].inicializarCromosoma();
			}
		}
		
		this.elMejor = new Cromosoma2(profundidad, creacion, useIf, tipoMultiplexor);

		if (this.elitismo) {
			for (int i = 0; i < (int) (tamPob * 0.02); i++) {
				this.elite[i] = new Cromosoma2(profundidad, creacion, useIf, tipoMultiplexor);
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

		int sel_cruce[] = new int[pob.length];
		int num_sele = 0;
		Cromosoma[] hijos;
		double prob;

		for (int i = 0; i < pob.length; i++) {
			prob = Math.random();
			if (prob < probCruce) {
				sel_cruce[num_sele] = i;
				num_sele++;
			}
		}

		if ((num_sele % 2) == 1) {
			num_sele--;
		}

		for (int i = 0; i < num_sele; i += 2) {
			hijos = cruce(pob[sel_cruce[i]], pob[sel_cruce[i + 1]]);

			pob[sel_cruce[i]] = hijos[0].clonar();
			pob[sel_cruce[i + 1]] = hijos[1].clonar();
			this.totalCruces++;
		}
	}

	private Cromosoma[] cruce(Cromosoma cromosoma, Cromosoma cromosoma2) {
			return new Cruce().cruzar(cromosoma, cromosoma2);
        
	}
	
	private int mutar(Cromosoma[] cromosoma, int tipo, double prob, Random rnd) {
		switch (tipo) {
		case 0:
			return new FSimple().mutar(cromosoma, prob, rnd);
		case 1:
			return new Permutacion().mutar(cromosoma, prob, rnd);
		case 2:
			return new TSimple().mutar(cromosoma, prob, rnd);
		case 3:
			return new deArbol().mutar(cromosoma, prob, rnd);
		case 4:
			return new Contraccion().mutar(cromosoma, prob, rnd);
		case 5:
			return new Expansion().mutar(cromosoma, prob, rnd);
		case 6:
			return new Hoist().mutar(cromosoma, prob, rnd);
		default:
			return new Expansion().mutar(cromosoma, prob, rnd);
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

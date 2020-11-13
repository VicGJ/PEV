package cromosoma;

import genes.Gen;

public abstract class Cromosoma {
	protected Gen[] genes;
	protected int[] fenotipo;

	protected double aptitud;
	protected double puntuacion;
	protected double punt_acum;
	protected int longCromosoma;

	protected int numGenes;
	protected double adaptacion;

	public Cromosoma() {
	}

	public abstract int[] calcularFenotipo();

	public abstract void mostrarGenotipo();

	public abstract void calcularLongitud();

	public abstract void inicializarCromosoma();

	public abstract void insertar(Gen gn, int pos);

	public abstract Cromosoma clonar();

	public abstract double operar(int[] x);

	public void evaluarCromosoma(double fitnessTotal, double punt) {
		this.puntuacion = this.adaptacion / fitnessTotal;
		this.punt_acum = puntuacion + punt;
	}

	public Gen[] getGenes() {
		return genes;
	}

	public void setGenes(Gen[] genes) {
		this.genes = genes;
	}

	public int[] getFenotipo() {
		return fenotipo;
	}

	public void setFenotipo(int[] ds) {
		this.fenotipo = ds;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public double getPunt_acum() {
		return punt_acum;
	}

	public void setPunt_acum(double punt_acum) {
		this.punt_acum = punt_acum;
	}

	public double getAptitud() {
		return aptitud;
	}

	public void setAptitud(double aptitud) {
		this.aptitud = aptitud;
	}

	public int getNumGenes() {
		return numGenes;
	}

	public int getLongCromosoma() {
		return this.numGenes;
	}

	public void setLongCromosoma(int longCromosoma) {
		this.longCromosoma = longCromosoma;
	}

	public double getAdaptacion() {
		return adaptacion;
	}

	public void setAdaptacion(double adaptacion) {
		this.adaptacion = adaptacion;
	}

	@Override
	public String toString() {
		String cadena = "";
		cadena += "[";
		for (int i = 0; i < numGenes - 1; i++) {
			cadena += genes[i].getAlelo() + ", ";
		}
		cadena += genes[numGenes - 1].getAlelo();
		cadena += "]";
		return cadena;
	}

}

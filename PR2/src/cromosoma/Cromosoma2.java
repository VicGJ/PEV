package cromosoma;

import java.util.Random;

import genes.Gen;

public class Cromosoma2 extends Cromosoma {
	
	int[][] distancia;
	int[][] flujo;
	Random random;
	
	public Cromosoma2(int[][] distancia, int[][] flujo, Random random, int n) {
		super();
		this.distancia = distancia;
		this.flujo = flujo;
		this.random = random;
		this.numGenes = n;
		calcularLongitud();
	}

	@Override
	public int[] calcularFenotipo() {
		int[] fen = new int[numGenes];

		for (int i = 0; i < this.numGenes; i++) {
			fen[i] = this.getGenes()[i].getAlelo();
		}

		return fen;
	}

	@Override
	public void mostrarGenotipo() {
		String gen = "";
		for (int i = 0; i < numGenes; i++) {
			int b = genes[i].getAlelo();

			gen += " " + b;

		}
		System.out.println(gen);
		
	}

	@Override
	public void calcularLongitud() {
		this.longCromosoma = this.numGenes;
		
	}

	@Override
	public void inicializarCromosoma() {
		boolean existe = false;
		this.genes = new Gen[this.numGenes];

		for (int i = 0; i < this.numGenes; i++) {
			this.genes[i] = new Gen(random, this.numGenes);

			do {
				this.genes[i].inicializarGen();
				existe = repetido(i, this.genes[i].getAlelo());
			} while (existe);

		}

		int fenotipo[] = this.calcularFenotipo();

		this.aptitud = this.operar(fenotipo);
		
	}
	
	private boolean repetido(int punto, int aleloNuevo) {
		for (int i = 0; i < punto; i++) {
			if (this.getGenes()[i].getAlelo() == aleloNuevo)
				return true;
		}
		return false;
	}
	
	@Override
	public void insertar(Gen gn, int pos) {
		this.genes[pos] = gn.clonar();
		
	}

	@Override
	public Cromosoma clonar() {
		Cromosoma2 f1 = new Cromosoma2(distancia, flujo, this.random, this.numGenes);
		f1.genes = new Gen[this.numGenes];

		for (int i = 0; i < this.numGenes; i++) {
			f1.genes[i] = this.genes[i].clonar();
		}

		f1.setFenotipo(calcularFenotipo());
		f1.setLongCromosoma(getLongCromosoma());
		f1.setPuntuacion(getPuntuacion());
		f1.setPunt_acum(getPunt_acum());
		f1.setAptitud(getAptitud());
		f1.setAdaptacion(getAdaptacion());

		return f1;
	}

	@Override
	public double operar(int[] x) {
		double sol = 0;

		for (int i = 0; i < this.getNumGenes(); i++) {
			for (int j = 0; j < this.getNumGenes(); j++) {
				sol += distancia[i][j] * flujo[x[i]][x[j]];
			}
		}

		return sol;
	}
	
	public String toString() {
		int fenotipo[] = this.calcularFenotipo();

		String solucion = "Mejor coste = " + (int) getAptitud();
		solucion += "\n";
		solucion += "Permutación: [" + fenotipo[0];

		for (int i = 1; i < fenotipo.length; i++) {
			solucion += " " + fenotipo[i];
		}
		solucion += "]";
		return solucion;
	}

}

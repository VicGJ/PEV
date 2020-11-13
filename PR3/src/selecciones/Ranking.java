package selecciones;

import cromosoma.Cromosoma;

public class Ranking {

	public static double beta = 1.5;

	
	public Ranking() {
		super();
	}

	public Cromosoma[] seleccion(int TamParti, Cromosoma[] participantes) {
		Cromosoma[] pobOrdenada = ordena(TamParti, participantes);
		Cromosoma[] nuevosParticipantes = new Cromosoma[TamParti];
		nuevosParticipantes[0] = pobOrdenada[0].clonar();
		nuevosParticipantes[1] = pobOrdenada[1].clonar();

		int numPadres = 2;
		double[] fitnessSegments = rankPopulation(TamParti);
		double entireSegment = fitnessSegments[fitnessSegments.length - 1];

		while (numPadres < nuevosParticipantes.length) {
			double x = (double) (Math.random() * entireSegment);

			if (x <= fitnessSegments[0]) {
				nuevosParticipantes[numPadres] = pobOrdenada[0];
				numPadres++;
			} else {
				for (int i = 1; i < nuevosParticipantes.length; i++) {
					if (x > fitnessSegments[i - 1] && x <= fitnessSegments[i]) {
						nuevosParticipantes[numPadres] = pobOrdenada[i];
						numPadres++;
					}
				}
			}
		}
		return nuevosParticipantes;
	}

	private Cromosoma[] ordena(int n, Cromosoma[] aOrdenar) {
		Cromosoma temp = null;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (aOrdenar[i].getAdaptacion() < aOrdenar[j].getAdaptacion()) {
                    temp = aOrdenar[i];
                    aOrdenar[i] = aOrdenar[j];
                    aOrdenar[j] = temp;
                }
            }
        }
        return aOrdenar;
	}

	private double[] rankPopulation(int TamParti) {
		double[] fitnessSegments = new double[TamParti];
		for (int i = 0; i < fitnessSegments.length; i++) {
			double prob = (double) i / TamParti;
			prob = prob * 2 * (beta - 1);
			prob = beta - prob;
			prob = (double) prob * ((double) 1 / TamParti);
			if (i != 0) {
				fitnessSegments[i] = fitnessSegments[i - 1] + prob;
			} else {
				fitnessSegments[i] = prob;
			}
		}

		return fitnessSegments;
	}
}
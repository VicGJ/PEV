package cromosoma;

import java.util.ArrayList;
import java.util.Random;

import controller.Arbol;

public class Cromosoma2 extends Cromosoma {
	int tipoCreacion;
	boolean useIf;
	int profundidad;
	
	public Cromosoma2(int profundidad, int tipoCreacion, boolean useIf, int tipoMultiplexor) {
		this.tipoCreacion = tipoCreacion;
		this.tipoMultiplexor = tipoMultiplexor;
		this.useIf = useIf;
		this.profundidad = profundidad;

		arbol = new Arbol(profundidad, useIf);

		if (tipoMultiplexor == 0) {
			Cromosoma.terminales = Cromosoma.terminales6.clone();
			this.numCasos = 64;
		} else {
			Cromosoma.terminales = Cromosoma.terminales11.clone();
			this.numCasos = 2048;
		}
		cargarCasos();
	}
	
	public Cromosoma2(int profundidad, int tipoCreacion, boolean useIf) {
		this.tipoCreacion = tipoCreacion;
		this.useIf = useIf;
		this.profundidad = profundidad;

		arbol = new Arbol(profundidad, useIf);

	}
	
	@Override
	public void inicializarCromosoma() {
		switch (tipoCreacion) {
		case 0:
			arbol.inicializacionCompleta(0, 0);
			break;
		case 1:
			arbol.inicializacionCreciente(0);
			break;
		case 2:
			int ini = new Random().nextInt(2);
			if (ini == 0)
				arbol.inicializacionCompleta(0, 0);
			else
				arbol.inicializacionCreciente(0);

			break;
		}

		evalua();

		String fenotipo = this.calcularFenotipo();
		this.setFenotipo(fenotipo);
		
	}
	@Override
	public Cromosoma clonar() {
		Cromosoma2 f = new Cromosoma2(profundidad, tipoCreacion, useIf);

		f.setArbol(arbol.copia());

		f.setAptitud(this.getAptitud());
		f.setNumCasos(getNumCasos());
		if (tipoMultiplexor == 0) {
			Cromosoma.terminales = Cromosoma.terminales6.clone();
		} else {
			Cromosoma.terminales = Cromosoma.terminales11.clone();
		}
		f.setCasos(getCasos());
		f.setTipoMultiplexor(getTipoMultiplexor());
		f.setPuntuacion(getPuntuacion());
		f.setPuntAcum(getPuntAcum());
		f.setAptitud(getAptitud());
		f.setAdaptacion(getAdaptacion());

		return f;
	}
	private String fenotipoHijos(ArrayList<Arbol> h) {
		String cad = "";

		for (int i = 0; i < h.size(); i++) {
			if (h.get(i).isEsRaiz()) {
				cad += "(" + h.get(i).getValor() +" ("+ fenotipoHijos(h.get(i).getHijos());
				cad += ")";
				if(h.size() != i+1) {
					cad+=", ";
				}
			} else {
				cad += h.get(i).getValor();
				if(h.size() != i+1) {
					cad+=", ";
				}
			}
		}
		return cad;
	}

	@Override
	public String calcularFenotipo() {

		String fen = "(" + arbol.getValor() + " ";

		fen = fen + fenotipoHijos(arbol.getHijos());
		fen += ")";

		return fen;
	}
	
	public String toString() {
		String fen;
		fen = "Aciertos = " + (int) getAptitud() +" de " + this.numCasos + " casos \n";
		fen += "(" + arbol.getValor() + " ";

		fen += fenotipoHijos(arbol.getHijos());
		fen += ")";

		return fen;
	}
}

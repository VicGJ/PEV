package cromosoma;


import controller.Arbol;

public abstract class Cromosoma {
	
	public static String terminales[];
    public static final String terminales6[] = {"A0", "A1", "D0", "D1", "D2", "D3"};
    public static final String terminales11[] = {"A0", "A1", "A2", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7"};
    public static final String funciones[] = {"AND", "OR", "NOT", "IF"};
    protected Arbol arbol;
    protected String fenotipo;
    protected double aptitud;
	protected double puntuacion;
	private double puntAcum;
	private double adaptacion;
	protected int[][] casos;
	protected int numCasos;
	protected int tipoMultiplexor;

	protected void cargarCasos() {
		if (tipoMultiplexor == 0) {
			casos = new int[numCasos][7];
			for (int i = 0; i < 64; i++) {
				String bin = Integer.toBinaryString(i);
				while (bin.length() < 6) {
					bin = "0" + bin;
				}
				char[] chars = bin.toCharArray();
				for (int j = 0; j < chars.length; j++) {
					casos[i][j] = chars[j] == '0' ? 0 : 1;
				}
				int aux = (i) / 16;
				switch (aux) {
				case 0:
					casos[i][6] = casos[i][2];
					break;
				case 1:
					casos[i][6] = casos[i][3];
					break;
				case 2:
					casos[i][6] = casos[i][4];
					break;
				case 3:
					casos[i][6] = casos[i][5];
					break;
				}

			}
		}
		if (tipoMultiplexor == 1) {
			casos = new int[numCasos][12];

			for (int i = 0; i < 2048; i++) {
				String bin = Integer.toBinaryString(i);
				while (bin.length() < 11) {
					bin = "0" + bin;
				}
				char[] chars = bin.toCharArray();
				for (int j = 0; j < chars.length; j++) {
					casos[i][j] = chars[j] == '0' ? 0 : 1;
				}
				int aux = (i) / 256;
				switch (aux) {
				case 0:
					casos[i][11] = casos[i][3];
					break;
				case 1:
					casos[i][11] = casos[i][4];
					break;
				case 2:
					casos[i][11] = casos[i][5];
					break;
				case 3:
					casos[i][11] = casos[i][6];
					break;
				case 4:
					casos[i][11] = casos[i][7];
					break;
				case 5:
					casos[i][11] = casos[i][8];
					break;
				case 6:
					casos[i][11] = casos[i][9];
					break;
				case 7:
					casos[i][11] = casos[i][10];
					break;
				}

			}
		}

	}

	public double evalua() {
		double aciertos = 0;

		if (this.numCasos == 64) {
			for (int i = 0; i < 64; i++) {

				int res = evaluar(arbol, i);
				if (this.numCasos == 64) {
					if (res == casos[i][6])
						aciertos++;
				}
			}
		} else {
			for (int i = 0; i < 2048; i++) {
				int res = evaluar(arbol, i);
				if (this.numCasos == 2048) {
					if (res == casos[i][11])
						aciertos++;
				}
			}
		}
		aptitud = aciertos;
		return aciertos;

	}

	private int evaluar(Arbol arbol, int numCaso) {
		String funcion = arbol.getValor();
		int resul = 0;
		if (funcion.equals("IF")) {
			int hijo1 = evaluar(arbol.getHijos().get(0), numCaso);
			int hijo2 = evaluar(arbol.getHijos().get(1), numCaso);
			int hijo3 = evaluar(arbol.getHijos().get(2), numCaso);
			if (hijo1 == 1)
				resul = hijo2;
			else
				resul = hijo3;
		} else if (funcion.equals("AND")) {
			int hijo1 = evaluar(arbol.getHijos().get(0), numCaso);
			int hijo2 = evaluar(arbol.getHijos().get(1), numCaso);
			if (hijo1 == 1 && hijo2 == 1)
				resul = 1;
			else
				resul = 0;
		} else if (funcion.equals("OR")) {
			int hijo1 = evaluar(arbol.getHijos().get(0), numCaso);
			int hijo2 = evaluar(arbol.getHijos().get(1), numCaso);
			if (hijo1 == 1 || hijo2 == 1)
				resul = 1;
			else
				resul = 0;
		} else if (funcion.equals("NOT")) {
			int hijo1 = evaluar(arbol.getHijos().get(0), numCaso);
			if (hijo1 == 1)
				resul = 0;
			else
				resul = 1;
		} else {
			resul = comprobar(funcion, numCaso);
		}
		return resul;
	}
	
	private int comprobar(String funcion, int numCaso) {
		int resul = 0;
		if (tipoMultiplexor == 0) {
			switch (funcion) {
			case "A0":
				resul = casos[numCaso][0];
				break;
			case "A1":
				resul = casos[numCaso][1];
				break;
			case "D0":
				resul = casos[numCaso][2];
				break;
			case "D1":
				resul = casos[numCaso][3];
				break;
			case "D2":
				resul = casos[numCaso][4];
				break;
			case "D3":
				resul = casos[numCaso][5];
				break;
			}
		}
		if (tipoMultiplexor == 1) {
			switch (funcion) {
			case "A0":
				resul = casos[numCaso][0];
				break;
			case "A1":
				resul = casos[numCaso][1];
				break;
			case "A2":
				resul = casos[numCaso][2];
				break;
			case "D0":
				resul = casos[numCaso][3];
				break;
			case "D1":
				resul = casos[numCaso][4];
				break;
			case "D2":
				resul = casos[numCaso][5];
				break;
			case "D3":
				resul = casos[numCaso][6];
				break;
			case "D4":
				resul = casos[numCaso][7];
				break;
			case "D5":
				resul = casos[numCaso][8];
				break;
			case "D6":
				resul = casos[numCaso][9];
				break;
			case "D7":
				resul = casos[numCaso][10];
				break;
			}
		}
		return resul;
	}

	public abstract String calcularFenotipo();

	public abstract void inicializarCromosoma();

	public abstract Cromosoma clonar();
	
	public void evaluarCromosoma(double fitnessTotal, double punt) {
		this.puntuacion = this.adaptacion / fitnessTotal;
		this.puntAcum = puntuacion + punt;
	}

	public double getAdaptacion() {
		return this.adaptacion;
	}

	public double getAptitud() {
		return aptitud;
	}

	public void setAptitud(double aptitud) {
		this.aptitud = aptitud;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public double getPuntAcum() {
		return puntAcum;
	}

	public void setPuntAcum(double puntAcum) {
		this.puntAcum = puntAcum;
	}

	public String getFenotipo() {
		return fenotipo;
	}

	public void setFenotipo(String fenotipo) {
		this.fenotipo = fenotipo;
	}

	public void setAdaptacion(double adaptacion) {
		this.adaptacion = adaptacion;
	}

	public Arbol getArbol() {
		return arbol;
	}

	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}

	public int[][] getCasos() {
		return casos;
	}

	public void setCasos(int[][] casos) {
		this.casos = casos;
	}

	public int getNumCasos() {
		return numCasos;
	}

	public void setNumCasos(int numCasos) {
		this.numCasos = numCasos;
	}

	public int getTipoMultiplexor() {
		return tipoMultiplexor;
	}

	public void setTipoMultiplexor(int tipoMultiplexor) {
		this.tipoMultiplexor = tipoMultiplexor;
	}
}

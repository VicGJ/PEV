package genes;

import java.util.Random;

public class Gen {
	private int alelo;
    private int longitud;
    Random rnd = new Random();
    
    public Gen() {
    }

    public Gen(Random random, int n) {
		this.rnd = random;
		this.longitud = n;
	}
    
    public void inicializarGen() {
		this.alelo = rnd.nextInt(longitud);
	}
    
    
    public Gen clonar() {
		Gen aux = new Gen(this.rnd, this.longitud);
		aux.alelo = this.alelo;
		aux.setAlelo(this.alelo);
		return aux;
	}

	public int getAlelo() {
		return alelo;
	}

	public void setAlelo(int alelo) {
		this.alelo = alelo;
	}
	
	//Borrar
	public String toString() {
		return Double.toString(this.getAlelo());
	}
}

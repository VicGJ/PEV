package controller;

import java.io.File;
import java.util.Scanner;

public class Matrices {
	private int[][] dist;
	private int[][] fluj;
	private int tam;
	
	final static String ARCH_0 = "Datos/ajuste.txt";
    final static String ARCH_1 = "Datos/datos12.txt";
    final static String ARCH_2 = "Datos/datos15.txt";
    final static String ARCH_3 = "Datos/datos30.txt";
    final static String ARCH_4 = "Datos/tai100a.txt";
    final static String ARCH_5 = "Datos/tai256c.txt";
    
    public Matrices() {
        tam = 0;
        fluj = null;
        dist = null;
    }

    public Matrices(int tipoFich) {
        readData(tipoFich);
    }

	public int[][] getDist() {
		return dist;
	}

	public int[][] getFluj() {
		return fluj;
	}

	public int getTam() {
		return tam;
	}
	
	 private void readData(int tipoFich) {
	        File archivo = null;
	        Scanner in = null;

	        try {

	            String file = chooseFile(tipoFich);
	            archivo = new File(file);

	            in = new Scanner(archivo);

	            this.tam = in.nextInt();

	            fluj = new int[tam][tam];
	            dist = new int[tam][tam];
	            
	            llenaMatriz(fluj, in);
	            llenaMatriz(dist, in);

	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception ex) {
	                System.out.println("ERROR: " + ex.getMessage());
	            }
	        }
	    }
	    
	    private String chooseFile(int archivo) {
	        String nombFile = " ";
	        switch (archivo) {
	            case 0:
	                nombFile = ARCH_0;
	                break;
	            case 1:
	                nombFile = ARCH_1;
	                break;
	            case 2:
	                nombFile = ARCH_2;
	                break;
	            case 3:
	                nombFile = ARCH_3;
	                break;
	            case 4:
	                nombFile = ARCH_4;
	                break;
	            case 5:
	                nombFile = ARCH_5;
	                break;   
	        }
	        return nombFile;
	    }
	    

	    private void llenaMatriz(int[][] flujo, Scanner in) {
	        for (int i = 0; i < this.tam; i++) {
	            for (int j = 0; j < this.tam; j++) {
	                flujo[i][j] = in.nextInt();
	            }
	        }
	    }

}

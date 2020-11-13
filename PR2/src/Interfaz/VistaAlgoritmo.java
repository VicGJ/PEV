/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import controller.AGenetico;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author R2D2
 */
public class VistaAlgoritmo {

    int nGeneracion, tamPoblacion, funcion, tipoSel;
    int tmutacion, tcruce;
    double probCruce;
    double probMutacion;
    double probOperador;
    boolean elitismo = true;

    public VistaAlgoritmo(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion, /*JTextField tOperador,*/ JComboBox<String> cseleccion, JComboBox<String> celitismo, JComboBox<String> cfuncion, JComboBox<String> cmutacion, JComboBox<String> ccruce) {
        try {
            nGeneracion = Integer.parseInt(tnGen.getText());
            tamPoblacion = Integer.parseInt(ttamPob.getText());
            probCruce = Double.parseDouble(tproCruce.getText());
           

            if (probCruce <= 100 && probCruce >= 0 && probOperador <= 100 && probOperador >= 0) {
                probMutacion = Double.parseDouble(tproMutacion.getText());
                if (probMutacion <= 100 && probMutacion >= 0) {
                    probCruce = probCruce / 100;
                    probMutacion = probMutacion / 100;
                    probOperador = probOperador / 100;
                    if (celitismo.getSelectedIndex() == 0) {
                        elitismo = false;
                    }
                    tipoSel = cseleccion.getSelectedIndex();
                    funcion = cfuncion.getSelectedIndex();
                    tmutacion = cmutacion.getSelectedIndex();
                    tcruce = ccruce.getSelectedIndex();

                } else {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "El porcentaje debe de ser entre 0 y 100%",
                            "Probabilidad incorrecta",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El porcentaje debe de ser entre 0 y 100%",
                        "Probabilidad incorrecta",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Campos incorrectos",
                    "¡Deben ser números!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void action() {
        AGenetico AG = new AGenetico(tamPoblacion, nGeneracion,this.tipoSel,this.tcruce,
        		probCruce, this.tmutacion, probMutacion, elitismo, funcion);

        algoritmoGenetico(AG);
    }

    private void algoritmoGenetico(AGenetico aG) {
        String cadena = "";
        cadena += "***************** Archivo " + funcion + " *********************\n";

        aG.ejecutar();
        cadena += aG.toString();
        Vista.addText(cadena);
    }

}

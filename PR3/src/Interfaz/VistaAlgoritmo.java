
package Interfaz;

import controller.AGenetico;

import java.io.FileNotFoundException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author R2D2
 */
public class VistaAlgoritmo {

    int nGeneracion, tamPoblacion, multiplexor, tipoSel;
    int tmutacion;
    int tinicializacion;
    double probCruce;
    double probMutacion;
    boolean elitismo = true;
    boolean useIf = true;
    int profundidad;
    int tbloating;

    public VistaAlgoritmo(JTextField tprofundidad, JTextField tnGen,
            JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
            JComboBox<String> cif, JComboBox<String> cseleccion,
            JComboBox<String> celitismo, JComboBox<String> cfuncion,
            JComboBox<String> cmutacion, JComboBox<String> ccreacion,
            JComboBox<String> cbloating) {
        try {
            nGeneracion = Integer.parseInt(tnGen.getText());
            tamPoblacion = Integer.parseInt(ttamPob.getText());
            probCruce = Double.parseDouble(tproCruce.getText());
            profundidad = Integer.parseInt(tprofundidad.getText());

            if (probCruce <= 100 && probCruce >= 0) {
                probMutacion = Double.parseDouble(tproMutacion.getText());
                if (probMutacion <= 100 && probMutacion >= 0) {
                    probCruce = probCruce / 100;
                    probMutacion = probMutacion / 100;
                    if (celitismo.getSelectedIndex() == 0) {
                        elitismo = false;
                    }
                    if (cif.getSelectedIndex() == 1) {
                        useIf = false;
                    }
                    tipoSel = cseleccion.getSelectedIndex();
                    multiplexor = cfuncion.getSelectedIndex();
                    tmutacion = cmutacion.getSelectedIndex();
                    tinicializacion = ccreacion.getSelectedIndex();
                    tbloating = cbloating.getSelectedIndex();

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

    public void action() throws FileNotFoundException {
    	boolean bloating = false;
    	if(this.tbloating != 2) {
    		bloating = true;
    	}
        AGenetico AG = new AGenetico(tamPoblacion, nGeneracion,this.tipoSel,
        		probCruce, this.tmutacion, probMutacion, elitismo, bloating, this.tbloating, this.tinicializacion,
        		this.useIf, this.multiplexor, this.profundidad);

        algoritmoGenetico(AG);
    }

    private void algoritmoGenetico(AGenetico aG) throws FileNotFoundException {
    	String cadena = "";
        cadena += "***************** Archivo " + this.multiplexor + " *********************\n";

        aG.ejecutar();
        cadena += aG.toString();
        Vista.addText(cadena);
    }

}

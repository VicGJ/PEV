
package Interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.border.Border;

import org.math.plot.Plot2DPanel;

/**
 *
 * @author R2D2
 */
public class Vista extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel lnGen, ltamPob, lproCruce, lproMutacion, lfuncion, lelitismo,
            lseleccion, ltipoMut, lif, ltipoCreacion, lprofundidad, lbloating;
    private JTextField tnGen, ttamPob, tproCruce, tproMutacion, tProfundidad;
    private JComboBox<String> cFuncion, cseleccion, celitismo, cmutacion, cif,
            ccreacion, cbloating;
    private JButton button;
    private static JTextArea area;
    private JScrollPane scroll;
    private static JProgressBar progressBar;
    private static Plot2DPanel plot;
    private JPanel window, panelS, panelE, panelW;

    public Vista() {
        initComponents();

        addTop();
        addCenter();
        addWest();
        addEast();
        addBottom();

        this.add(window);

    }

    static void addText(String cadena) {
        area.setText(cadena);
    }

    private void initComponents() {
        this.setTitle("Practica 3 - PEV");
        this.setResizable(true);
        this.setMinimumSize(new Dimension(1200, 650));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        window = new JPanel();
        window.setLayout(new BorderLayout());
        panelE = new JPanel();
        panelW = new JPanel();
        panelS = new JPanel();

        //label
        lnGen = new JLabel("Numero de Generaciones:");
        ltamPob = new JLabel("Tamaño de la Poblacion:");
        lproCruce = new JLabel("Probabilidad de cruce:");
        lproMutacion = new JLabel("Probabilidad de Mutacion:");
        lfuncion = new JLabel("Tipo de multiplexor:");
        lseleccion = new JLabel("Tipo de seleccion");
        lelitismo = new JLabel("Seleccion por elitismo");
        ltipoMut = new JLabel("Tipo mutacion: ");
        lif = new JLabel("¿Funcion IF?: ");
        ltipoCreacion = new JLabel("Creacion de arboles:");
        lprofundidad = new JLabel("Maxima Profundidad: ");
        lbloating = new JLabel("Tipo de bloating: ");

        //texto
        tnGen = new JTextField();
        tnGen.setText("100");
        ttamPob = new JTextField();
        ttamPob.setText("100");
        tproCruce = new JTextField();
        tproCruce.setText("60");
        tproMutacion = new JTextField();
        tproMutacion.setText("10");
        this.tProfundidad = new JTextField();
        tProfundidad.setText("4");

        //combo
        cFuncion = new JComboBox<String>();
        cFuncion.addItem("6 Entradas");
        cFuncion.addItem("11 Entradas");

        cseleccion = new JComboBox<String>();
        cseleccion.addItem("Ruleta");
        cseleccion.addItem("Estocastico");
        cseleccion.addItem("Deterministico");
        cseleccion.addItem("Probabilitico");
        cseleccion.addItem("Truncamiento");
        cseleccion.addItem("Ranking");
        cseleccion.addItem("Restos");

        celitismo = new JComboBox<String>();
        celitismo.addItem("No");
        celitismo.addItem("Si");

        cmutacion = new JComboBox<String>();
        cmutacion.addItem("Funcional");
        cmutacion.addItem("Permutacion");
        cmutacion.addItem("Terminal");
        cmutacion.addItem("Arbol");
        cmutacion.addItem("Contraccion");
        cmutacion.addItem("Expansion");
        cmutacion.addItem("Hoist");
        
        cif = new JComboBox<String>();
        cif.addItem("Si");
        cif.addItem("No");

        ccreacion = new JComboBox<String>();
        ccreacion.addItem("Creciente");
        ccreacion.addItem("Completa");
        ccreacion.addItem("Ramped and Half");

        cbloating = new JComboBox<String>();
        cbloating.addItem("Tarpeian");
        cbloating.addItem("Penalizacion");
        cbloating.addItem("Sin bloating");

        //Grafica
        plot = new Plot2DPanel();

        //textArea
        area = new JTextArea();
        area.setColumns(20);
        area.setRows(31);
        scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //boton
        button = new JButton("Run");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                plot.removeAllPlots();
                try {
					new VistaAlgoritmo(tProfundidad, tnGen, ttamPob, tproCruce,
					        tproMutacion, cif, cseleccion, celitismo, cFuncion,
					        cmutacion, ccreacion, cbloating).action();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
                plot.setFixedBounds(0, 0, Integer.parseInt(tnGen.getText()));
            }
        });

        //barra de progreso
        progressBar = new JProgressBar();
        progressBar.setForeground(Color.GREEN);
    }

    private void addTop() {
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Progreso");
        progressBar.setBorder(border);

        window.add(progressBar, BorderLayout.NORTH);
    }

    private void addCenter() {
        plot.addLegend("SOUTH");

        window.add(plot, BorderLayout.CENTER);
    }

    private void addWest() {
        panelW.setLayout(new GridLayout(12, 2, 20, 20));
        panelW.setBorder(BorderFactory.createTitledBorder("ParÃ¡metros"));

        panelW.add(lnGen);
        panelW.add(tnGen);

        panelW.add(ltamPob);
        panelW.add(ttamPob);

        panelW.add(lfuncion);
        panelW.add(cFuncion);

        panelW.add(lif);
        panelW.add(cif);

        panelW.add(ltipoCreacion);
        panelW.add(ccreacion);

        panelW.add(lprofundidad);
        panelW.add(tProfundidad);

        panelW.add(ltipoMut);
        panelW.add(cmutacion);

        panelW.add(lproCruce);
        panelW.add(tproCruce);

        panelW.add(lproMutacion);
        panelW.add(tproMutacion);

        panelW.add(lseleccion);
        panelW.add(cseleccion);

        panelW.add(lelitismo);
        panelW.add(celitismo);

        panelW.add(lbloating);
        panelW.add(cbloating);

        window.add(panelW, BorderLayout.WEST);
    }

    private void addBottom() {
        panelS.setLayout(new FlowLayout());

        panelS.add(button);

        window.add(panelS, BorderLayout.SOUTH);
    }

    private void addEast() {
        panelE.setLayout(new FlowLayout());
        panelE.setBorder(BorderFactory.createTitledBorder("DescripciÃ³n"));

        panelE.add(scroll);

        window.add(panelE, BorderLayout.EAST);
    }

    public static void addData(double[] mejorAbs, double[] mejor, double[] media, int numGeneraciones) {

		plot.removeAllPlots();

		double[] x = new double[numGeneraciones];

		for (int i = 0; i < x.length; i++) {
			x[i] = i + 1;
		}

		plot.addLegend("SOUTH");
		plot.addLinePlot("Mejor Absoluto", Color.BLUE, x, mejorAbs);
		plot.addLinePlot("Mejor de la Generación", Color.RED, x, mejor);
		plot.addLinePlot("Media Generación", Color.GREEN, x, media);
	}

}

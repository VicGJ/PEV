package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.math.plot.Plot2DPanel;


public class Vista extends JFrame {
	private static final long serialVersionUID = 1L;

    private JLabel lnGen, ltamPob, lproCruce, lproMutacion, lfuncion, lelitismo, lseleccion, ltipoMut, ltipoCruce;
    private JTextField tnGen, ttamPob, tproCruce, tproMutacion;
    private JComboBox<String> cFuncion, cseleccion, celitismo, cmutacion, ccruce;
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
        this.setTitle("Practica 2 - PEV");
        this.setResizable(false);
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
        ltamPob = new JLabel("TamaÒo de la Poblacion:");
        lproCruce = new JLabel("Probabilidad de cruce:");
        lproMutacion = new JLabel("Probabilidad de Mutacion:");
        lfuncion = new JLabel("Datos a optimizar:");
        lseleccion = new JLabel("Tipo de seleccion");
        lelitismo = new JLabel("Seleccion por elitismo");
        ltipoMut = new JLabel("Tipo mutacion: ");
        ltipoCruce = new JLabel("Tipo cruce: ");

        //texto
        tnGen = new JTextField();
        tnGen.setText("100");
        ttamPob = new JTextField();
        ttamPob.setText("100");
        tproCruce = new JTextField();
        tproCruce.setText("60");
        tproMutacion = new JTextField();
        tproMutacion.setText("10");
        
        //combos
        cFuncion = new JComboBox<String>();
        cFuncion.addItem("Prueba");
        cFuncion.addItem("Datos 12");
        cFuncion.addItem("Datos 15");
        cFuncion.addItem("Datos 30");
        cFuncion.addItem("Tai 100a");
        cFuncion.addItem("Tai 256c");

        cseleccion = new JComboBox<String>();
        cseleccion.addItem("Ruleta");
        cseleccion.addItem("Estocastico");
        cseleccion.addItem("Torneo");
        cseleccion.addItem("T-Probabilistico");
        cseleccion.addItem("Truncamiento");
        cseleccion.addItem("Ranking");
        cseleccion.addItem("Restos");

        celitismo = new JComboBox<String>();
        celitismo.addItem("No");
        celitismo.addItem("Si");

        cmutacion = new JComboBox<String>();
        cmutacion.addItem("Insercion");
        cmutacion.addItem("Intercambio");
        cmutacion.addItem("Inversion");
        cmutacion.addItem("Heuristica");
        cmutacion.addItem("Personal");

        ccruce = new JComboBox<String>();
        ccruce.addItem("CX");
        ccruce.addItem("Codi-Ordinal");
        ccruce.addItem("ERX");
        ccruce.addItem("OX");
        ccruce.addItem("OXPos");
        ccruce.addItem("PMX");
        ccruce.addItem("Personal");

        //Grafica
        plot = new Plot2DPanel();

        //textArea
        area = new JTextArea();
        area.setColumns(20);
        area.setRows(31);
        scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //boton
        button = new JButton("Comenzar");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                plot.removeAllPlots();
                new VistaAlgoritmo(tnGen, ttamPob, tproCruce,
                		tproMutacion, cseleccion, celitismo, cFuncion,
                		cmutacion, ccruce).action();
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
        panelW.setLayout(new GridLayout(10, 2, 20, 20));
        panelW.setBorder(BorderFactory.createTitledBorder("Par√°metros"));

        panelW.add(lnGen);
        panelW.add(tnGen);

        panelW.add(ltamPob);
        panelW.add(ttamPob);

        panelW.add(ltipoCruce);
        panelW.add(ccruce);

        panelW.add(lproCruce);
        panelW.add(tproCruce);

        panelW.add(ltipoMut);
        panelW.add(cmutacion);

        panelW.add(lproMutacion);
        panelW.add(tproMutacion);
        

        panelW.add(lfuncion);
        panelW.add(cFuncion);

        panelW.add(lseleccion);
        panelW.add(cseleccion);

        panelW.add(lelitismo);
        panelW.add(celitismo);

        window.add(panelW, BorderLayout.WEST);
    }

    private void addBottom() {
        panelS.setLayout(new FlowLayout());

        panelS.add(button);

        window.add(panelS, BorderLayout.SOUTH);
    }

    private void addEast() {
        panelE.setLayout(new FlowLayout());
        panelE.setBorder(BorderFactory.createTitledBorder("Descripci√≥n"));

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
		plot.addLinePlot("Mejor de la GeneraciÛn", Color.RED, x, mejor);
		plot.addLinePlot("Media GeneraciÛn", Color.GREEN, x, media);
	}
}

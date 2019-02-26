package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.JouerBoutton;
import model.Plateau;

public class VuePlateau implements Observer {
	
	private GridLayout layout;
	private ArrayList<JButton> bouttons;
	private HashMap<Point,JLabel> labels;
	private JFrame frame;
	private JPanel panelGrid;
	private PanelDetails details;
	private Plateau plateau;

	public VuePlateau(Plateau p) {
		this.plateau = p;
		labels = new HashMap<Point,JLabel>();
		bouttons = new ArrayList<JButton>();
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame("Puissance 4");
		panelGrid = new JPanel();
		details = new PanelDetails(plateau);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		
		layout = new GridLayout(plateau.getNbLignes()+1, plateau.getNbColonnes());
		panelGrid.setLayout(layout);
		
		JButton b;
		JLabel label;
		for (int i = 0 ; i < plateau.getNbLignes()+1 ; i++){
			for (int j = 0 ; j < plateau.getNbColonnes() ; j++){
				if (i == 0){
					b = new JButton();
					b.addActionListener(new JouerBoutton(j, plateau));
					bouttons.add(b);
					panelGrid.add("Boutton"+j, b);
				} else {
					label = new JLabel();
					label.setOpaque(true);
					label.setBackground(Color.GRAY);
					label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
					labels.put(new Point(i-1,j),label);
					panelGrid.add("Label"+(i*plateau.getNbLignes()+j), label);					
				}
			}
		}
		
		frame.setLayout(new BorderLayout());
		frame.add(panelGrid);
		frame.add(details, BorderLayout.EAST);

		frame.setVisible(true);
		
	}
	
	private JLabel getLabel(int ligne, int colonne){
		return labels.get(new Point(ligne,colonne));
	}

	@Override
	public void update(Observable o, Object arg) {
		int valCase;
		
		for (int i = 0 ; i < plateau.getNbLignes() ; i++){
			for (int j = 0 ; j < plateau.getNbColonnes() ; j++){
				valCase = plateau.getCase(i, j);
				if (valCase != 0){
					getLabel(i, j).setBackground(valCase == 1 ? Color.RED : Color.YELLOW);
				}
			}
		}	
		
		if (plateau.isEnded()){
			disableBouttons();
			return;
		}
		
		details.updateLabels();
	}

	private void disableBouttons() {
		for (JButton jButton : bouttons) {
			jButton.setEnabled(false);
		}
	}

}

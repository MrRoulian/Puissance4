package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Plateau;

public class JouerBoutton implements ActionListener{
	
	private Plateau p;
	private int colonne;

	public JouerBoutton(int colonne, Plateau p) {
		this.p = p;
		this.colonne = colonne;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!p.joueurCourantIsRobot()){
			p.jouer(colonne);		
		}
	}

}

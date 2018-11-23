package ihm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algo.Labyrinthe;

public class IHMLabyrinthe extends JPanel {
	
	private static final long serialVersionUID = -8174131410184784009L;
	private int largeurLaby;
	private int longueurLaby;
	
	public IHMLabyrinthe(int largeurLaby, int longueurLaby, Labyrinthe laby) {
		super(new GridLayout(largeurLaby, longueurLaby));
		
		this.largeurLaby = largeurLaby;
		this.longueurLaby = longueurLaby;
		
		for (int y = 0; y < largeurLaby; y++) {
			for (int x = 0; x < longueurLaby; x++) {

				// creation d'un JLabel
				JLabel caseLabel = new JLabel();
				caseLabel.setPreferredSize(new Dimension(20,20));
				caseLabel.setOpaque(true);
				
				int caseLaby = laby.getleLaby()[y][x];
				
				if (caseLaby == 0) {
					caseLabel.setBackground(new Color(255, 255, 255));
				} else if (caseLaby == 1) {
					caseLabel.setBackground(new Color(0, 0, 0));
				} else if (caseLaby == 2) {
					caseLabel.setBackground(new Color(56, 255, 56));
				} else if (caseLaby == 3) {
					caseLabel.setBackground(new Color(255, 20, 20));
				}

				// Ajout du JLabel dans le JPanel
				this.add(caseLabel);
			}
		}
	}

	public void setLabelIndividu(int positionY, int positionX, ImageIcon imageIcon) {
		JLabel label = (JLabel) this.getComponent((positionY*longueurLaby) + positionX);
		label.setIcon(imageIcon);
		this.repaint();
	}

	public void setLabelNoIndividu(int positionY, int positionX) {
		JLabel label = (JLabel) this.getComponent((positionY*longueurLaby) + positionX);
		label.setIcon(null);
		this.repaint();
	}

	public void reset() {
		
		for (int y = 0; y < largeurLaby; y++) {
			for (int x = 0; x < longueurLaby; x++) {
				this.setLabelNoIndividu(y, x);
			}
		}
	}
	
	public void printCheminGagnant(ArrayList<String> genome, Labyrinthe laby) {
		int currentPositonX = laby.getxPositionDepart();
		int currentPositonY = laby.getyPositionDepart();
		
		for (String gene : genome) {
			if (gene.equalsIgnoreCase("N")) {
				currentPositonY -= 1;
			}
			else if (gene.equalsIgnoreCase("S")) {
				currentPositonY += 1;
			}
			else if (gene.equalsIgnoreCase("E")) {
				currentPositonX -= 1;
			}
			else if (gene.equalsIgnoreCase("O")) {
				currentPositonX += 1;
			}
			this.setLabelCheminGagnant(currentPositonY, currentPositonX);
		}
	}
	
	public void setLabelCheminGagnant(int positionY, int positionX) {
		JLabel label = (JLabel) this.getComponent((positionY*longueurLaby) + positionX);
		
		int couleurJLabel = label.getBackground().getRed();
		
		if (couleurJLabel > 100) {
			label.setBackground(new Color(couleurJLabel-50, 0, 0));
		}
		
		this.repaint();
	}

}

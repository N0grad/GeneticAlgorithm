package algo;
import javax.swing.JFrame;

import ihm.IHMFenetre;
import ihm.IHMLabyrinthe;


public class Main extends JFrame {

	private static final long serialVersionUID = -7301569443693871687L;
	
	private final int NB_INDIVIDUS;
	private final int LARGEUR_LABY;
	private final int LONGUEUR_LABY;
	
	public Main(int largeurLaby, int longueurLaby, int nbIndividu) {
		
		LARGEUR_LABY = largeurLaby;
		LONGUEUR_LABY = longueurLaby;
		NB_INDIVIDUS = nbIndividu;

		//Génération du labyrinthe
		Labyrinthe laby = new Labyrinthe(LARGEUR_LABY, LONGUEUR_LABY);

		//Génération de la fenêtre
		IHMFenetre fenetre = new IHMFenetre(LARGEUR_LABY, LONGUEUR_LABY);
		
		//Génération du graphoique du labyrinthe
		IHMLabyrinthe labyrintheIHM = new IHMLabyrinthe(LARGEUR_LABY, LONGUEUR_LABY, laby);
		
		//Ajout et affichage de la fenetre
		fenetre.ajout(labyrintheIHM);
		fenetre.hide();

		
		//Génération d'une génération constitué d'individus
		Generation lesIndividus = new Generation(NB_INDIVIDUS);
		
		//On demande à chaque individu de parcours le labyrinthe
		Individu[] population = lesIndividus.getPopulation();
		
		boolean fini = false;
		
		for (int nb = 1; !fini; nb++) {
			System.out.println("\n----------------GENERATION : "+nb+"---------------------");
			
			population = lesIndividus.getPopulation();
			for (int i = 0; i < population.length; i++) {
				Individu individu = population[i];
				Thread t = new Thread() {
					public void run() {
						individu.parcourirLeLaby(laby, labyrintheIHM, individu.getApparence());
					}
				};
				
				t.start();
				
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			lesIndividus.notation(lesIndividus, laby);
			lesIndividus.classement();
			
			for (int i = 0; i < 20; i++) {
				lesIndividus.getPopulation()[i].printPoint();
				if (fini == false) {
					fini = lesIndividus.getPopulation()[i].getPoints() == 0;
				}
			}
			
			labyrintheIHM.reset();
			
			lesIndividus.resetElite();

			lesIndividus.nextGeneration();
			
		}

		lesIndividus.getPopulation()[0].printGenome();
		labyrintheIHM.printCheminGagnant(lesIndividus.getPopulation()[0].getGenome(), laby);

	}
	
}

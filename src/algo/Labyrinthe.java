package algo;

public class Labyrinthe {

	public static int xPositionArrive;
	public static int yPositionArrive;
	public static int xPositionDepart;
	public static int yPositionDepart;
	
	private int longueur;
	private int largeur;
	private int[][] leLaby;
	
	public Labyrinthe(int largeur, int longueur) {
		
		this.longueur = longueur;
		this.largeur = largeur;
		this.leLaby = new int[largeur][longueur];
		
		this.generate();
		
	}
	
	// 0 = espace
	// 1 = mur
	// 2 = départ
	// 3 = arrivé
	private void generate() {
		
		//Génération du mur d'en haut
		for (int i = 0; i < this.longueur; i++) {
			this.leLaby[0][i] = 1;
		}
		
		
		//Mur de gauche et de droite
		for (int i = 1; i < this.largeur; i++) {
			this.leLaby[i][0] = 1;
			this.leLaby[i][longueur-1] = 1;
		}
		
		
		//Mur du bas
		for (int i = 0; i < this.longueur; i++) {
			this.leLaby[this.largeur-1][i] = 1;
		}
		
		
		//Placement des points de départ et d'arrivée
		xPositionDepart = 1;
		yPositionDepart = 1;
		this.leLaby[yPositionDepart][xPositionDepart] = 2;
		
		xPositionArrive = this.longueur - 2;
		yPositionArrive = this.largeur - 2;
		this.leLaby[yPositionArrive][xPositionArrive] = 3;
		
		
		//Placement des murs aux hasard
		for (int i = 1; i < this.largeur; i++) {
			for (int j = 1; j < this.longueur; j++) {
				if (this.leLaby[i][j] != 2 && this.leLaby[i][j] != 3 && Math.random() > 0.8) {
					this.leLaby[i][j] = 1;
				}
			}
		}
		
		//Création du parcours à partir du point de départ jusqu'au point d'arrivée
		boolean pointArrivee = false;
		int i = 1;
		int j = 1;
		
		while (!pointArrivee) {
			
			double nbRandom = Math.random();
			
			if (nbRandom < 0.5 && (i+1) != this.largeur-1) {
				i++; //SUD
			}
			else if ((j+1) != this.longueur-1){
				j++; //OUEST
			}
			
			this.leLaby[i][j] = 0;
			
			//on regarde si au alentour il y a le point d'arrivée (= chemin fini)
			pointArrivee = this.leLaby[i+1][j] == 3 || this.leLaby[i][j+1] == 3;

		}
		
		
		
	}
	
	
	public int[][] getleLaby() {
		return this.leLaby;
	}
	
	public int getxPositionArrive() {
		return xPositionArrive;
	}

	public int getyPositionArrive() {
		return yPositionArrive;
	}

	public int getxPositionDepart() {
		return xPositionDepart;
	}

	public int getyPositionDepart() {
		return yPositionDepart;
	}

	
}

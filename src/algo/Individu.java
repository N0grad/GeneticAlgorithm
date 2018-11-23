package algo;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import ihm.IHMLabyrinthe;

public class Individu implements Comparable<Individu> {

	private ArrayList<String> genome;
	private final String[] MOUVEMENT_POSSIBLE = {"N", "S", "E", "O"};
	private int positionX;
	private int positionY;
	private int points;
	private boolean enVie;
	
	private ImageIcon apparence;

	public Individu(ArrayList<String> genome) {
		this.genome = genome;
		this.positionX = 1;
		this.positionY = 1;
		this.points = 0;
		this.enVie = true;
		
		BufferedImage img = null;
		try {
			img = colorImage(ImageIO.read(new File("image/individu.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.apparence = new ImageIcon(img);
	}
	
	public ArrayList<String> getGenome() {
		return this.genome;
	}
	
	private BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        Color colorReplace = new Color(new Random().nextInt(233), new Random().nextInt(233), new Random().nextInt(233));
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = colorReplace.getBlue();
                pixels[1] = colorReplace.getRed();
                pixels[2] = colorReplace.getGreen();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
	
	public void mutation(double CHANCE_DE_MUTER) {
		
		//Pour toute la longueur du genome
		for (int i = 0; i < genome.size(); i++) {
			
			//si une mutation à lieu
			if (Math.random() < CHANCE_DE_MUTER) {
				
				int indexGenomeRandom = new Random().nextInt(this.genome.size());
				int indexMoveRandom = new Random().nextInt(this.MOUVEMENT_POSSIBLE.length);
				
				this.genome.set(indexGenomeRandom, this.MOUVEMENT_POSSIBLE[indexMoveRandom]);
				
			}
		}
	}
	
	public Individu accouplement(Individu partenaire) {
		
		ArrayList<String> newGenome = new ArrayList<String>();
		ArrayList<String> partenaireGenome = partenaire.getGenome();
		
		for (int i = 0 ; i < this.genome.size() / 2 ; i++) {
			newGenome.add(this.genome.get(i));
		}
		for (int i = (partenaireGenome.size() / 2) ; i < partenaireGenome.size() ; i++) {
			newGenome.add(partenaire.genome.get(i));
		}
		
		return new Individu(newGenome);
	}
	
	public void setPoint(int nbPoints) {
		this.points = nbPoints;
	}
	
	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getPoints() {
		return points;
	}

	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	public void ajouterUnGene() {
		int nbRandom = new Random().nextInt(MOUVEMENT_POSSIBLE.length);
		
		if (!this.genome.isEmpty()) {
			String dernierMove = this.genome.get(this.genome.size() - 1);
			boolean bonMove = false;
	
			while (!bonMove) {
				
				String moveChoisi = this.MOUVEMENT_POSSIBLE[nbRandom];
				
				if (dernierMove.equals("N") && !moveChoisi.equals("S")) {
					this.genome.add(moveChoisi);
					bonMove = true;
				}
				else if (dernierMove.equals("S") && !moveChoisi.equals("N")) {
					this.genome.add(moveChoisi);	
					bonMove = true;
				}
				else if (dernierMove.equals("E") && !moveChoisi.equals("O"))  {
					this.genome.add(moveChoisi);
					bonMove = true;
				}
				else if (dernierMove.equals("O") && !moveChoisi.equals("E")){
					this.genome.add(moveChoisi);
					bonMove = true;
				}
				else {
					nbRandom = new Random().nextInt(MOUVEMENT_POSSIBLE.length);
				}
				
			}
		}
		else {
			this.genome.add(this.MOUVEMENT_POSSIBLE[nbRandom]);
		}
		
		/*int nbRandom = new Random().nextInt(MOUVEMENT_POSSIBLE.length);
		this.genome.add(this.MOUVEMENT_POSSIBLE[nbRandom]);*/
		
	}

	public void effectueLeMouvement(String orientation, IHMLabyrinthe iHMLaby, ImageIcon imageIcon) {
		
		iHMLaby.setLabelNoIndividu(this.positionY, this.positionX);
		
		if (orientation.equals("N")) {
			this.positionY -= 1;
		}
		else if (orientation.equals("S")) {
			this.positionY += 1;
		}
		else if (orientation.equals("E")) {
			this.positionX -= 1;
		}
		else if (orientation.equals("O")) {
			this.positionX += 1;
		}
		
		iHMLaby.setLabelIndividu(this.positionY, this.positionX, imageIcon);
		
		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	public void parcourirLeLaby(Labyrinthe laby, IHMLabyrinthe IHMLaby, ImageIcon imageIcon) {
		
		int i = 0;
		
		//tant qu'il est en vie et qu'on a pas parcour tout son génome
		while(this.enVie && i < this.genome.size()) {
			
			//on récupère le gène 
			String leGene = this.genome.get(i);
			
			//on effectue le mouvement
			this.effectueLeMouvement(leGene, IHMLaby, imageIcon);
			
			//s'il se mange un mur, il meurt.
			if (laby.getleLaby()[this.positionY][this.positionX] == 1) {
				this.enVie = false;
			}
			
			//on continue le parcours du génome
			i++;
		}
		
		//si jamais il est encore en vie alors que TOUT son génome a été parcour on en ajoute et on continue
		while (this.enVie) {
			this.ajouterUnGene();
			this.effectueLeMouvement(this.genome.get(this.genome.size()-1), IHMLaby, imageIcon);
			
			if (laby.getleLaby()[this.positionY][this.positionX] == 1 || laby.getleLaby()[this.positionY][this.positionX] == 3) {
				this.enVie = false;
			}

		}
		
		//on enlève le dernier gene où il s'est mangé le mur
		if (this.genome.size() > 0) {
			this.genome.remove(this.genome.size()-1);
		}
		
	}

	public void printPoint() {
		System.out.print("Point: "+this.points+" / ");
	}
	
	public void printGenome() {
		System.out.println("Genome : ");
		for (String gene : this.genome) {
			System.out.print(gene.toString());
		}
	}
	
	@Override
	public int compareTo(Individu autreIndividu) {
		return this.points - autreIndividu.getPoints();
	}

	public ImageIcon getApparence() {
		return this.apparence;
	}
}

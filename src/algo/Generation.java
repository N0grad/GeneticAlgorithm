package algo;
import java.util.ArrayList;
import java.util.Arrays;

public class Generation {

	private Individu[] population;
	private final double CHANCE_DE_MUTER = 0.1;
	private final double POURCENTAGE_ELITE_RETENU = 0.2;
	
	public Generation (int nbIndividu) {
		this.population = new Individu[nbIndividu];
		this.fillGeneration();
	}
	
	private void fillGeneration() {
		for (int i = 0; i < this.population.length; i++) {
			this.population[i] = new Individu(new ArrayList<String>());
		}
	}
	
	public void notation(Generation lesIndividus, Labyrinthe laby) {
		
		Individu[] laPopulation = lesIndividus.getPopulation();
		
		for (Individu individu : laPopulation) {
			int point = Math.abs((laby.getxPositionArrive() - individu.getPositionX()) + (laby.getyPositionArrive() - individu.getPositionY()));
			individu.setPoint(point);
		}
	}
	
	public void classement() {
		Arrays.sort(this.population);
	}
	
	public void nextGeneration() {
		int nombreEliteRetenu = (int) (this.population.length * this.POURCENTAGE_ELITE_RETENU);
		int indexAjoutEnfant = nombreEliteRetenu;
		
		for (int i = 0; i < nombreEliteRetenu; i++) {
			Individu enfant1 = this.population[i].accouplement(this.population[i+1]);
			enfant1.mutation(CHANCE_DE_MUTER);
			this.population[indexAjoutEnfant] = enfant1;
			
			indexAjoutEnfant++;
			
			Individu enfant2 = this.population[i+1].accouplement(this.population[i]);
			enfant2.mutation(CHANCE_DE_MUTER);
			this.population[indexAjoutEnfant] = enfant2;
			
			indexAjoutEnfant++;
		}
	}
	
	public Individu[] getPopulation() {
		return this.population;
	}

	public void resetElite() {
		int nombreEliteRetenu = (int) (this.population.length * this.POURCENTAGE_ELITE_RETENU);
		
		for (int i = 0; i < nombreEliteRetenu; i++) {
			Individu elite = this.population[i];
			
			elite.setEnVie(true);
			elite.setPoint(0);
			elite.setPositionX(1);
			elite.setPositionY(1);
		}
	}
}

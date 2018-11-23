package ihm;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

public class IHMFenetre extends JFrame {
	
	private static final long serialVersionUID = 3543573055587951883L;

	public IHMFenetre(int largeurLaby, int longueurLaby) {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Genetic Algorithm GUI");
		this.getContentPane().setLayout(new BorderLayout());
		
	}
	
	public void ajout(Component obj) {
		this.add(obj);
		this.pack();
	}
	
	public void hide() {
		this.setVisible(true);
	}

}

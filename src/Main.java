import model.Plateau;
import view.VuePlateau;

public class Main {

	public static void main(String[] args) {
		Plateau p = new Plateau(6, 7);
		VuePlateau vp = new VuePlateau(p);
		p.addObserver(vp);
		
		vp.update(p, null);
	}

}

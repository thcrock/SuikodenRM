package utilities;

import animations.ImageCache;
import entities.GameCharacter;
import entities.GameWorldCharacter;
import entities.game.characters.GameClive;
import entities.game.characters.GameKilley;
import entities.world.characters.Clive;
import entities.world.characters.Townfolk1;
import entities.world.characters.Townfolk2;
import entities.world.characters.Townfolk3;
import entities.world.characters.Townfolk4;
import entities.world.characters.OldMan;
import entities.world.characters.Dog;
import entities.world.characters.Kid1;
import entities.world.characters.Killey;
import entities.world.characters.Leknaat;
import gamestate.BoxWorld;

public class CharacterGeneration {

	
	public static GameWorldCharacter getWorldCharacter(String name, BoxWorld bw, float x, float y) {
        System.out.println(name);
		if(name.equals("Killey")) return new Killey(ImageCache.getFrame("killeyWalkLeft", 2), bw, x, y);
		else if(name.equals("Leknaat")) return new Leknaat(ImageCache.getFrame("leknaatWalkLeft", 2), bw, x, y);
		else if(name.equals("Clive")) return new Clive(ImageCache.getFrame("cliveWalkLeft", 2), bw, x, y);
		else if(name.equals("Townfolk1")) return new Townfolk1(ImageCache.getFrame("townfolk0", 0), bw, x, y);
		else if(name.equals("Townfolk2")) return new Townfolk2(ImageCache.getFrame("townfolk02", 7), bw, x, y);
		else if(name.equals("Townfolk3")) return new Townfolk3(ImageCache.getFrame("townfolk03", 6), bw, x, y);
		else if(name.equals("Townfolk4")) return new Townfolk4(ImageCache.getFrame("townfolk04", 5), bw, x, y);
		else if(name.equals("OldMan")) return new OldMan(ImageCache.getFrame("oldman", 7), bw, x, y);
		else if(name.equals("Dog")) return new Dog(ImageCache.getFrame("dog_run", 1), bw, x, y);
		else if(name.equals("Kid1")) return new Kid1(ImageCache.getFrame("kid1", 1), bw, x, y);
		return null;
	}
	
	public static GameCharacter getGameCharacter(String name) {
		if(name.equals("Clive")) return new GameClive();
		if(name.equals("Killey")) return new GameKilley();
		return null;
	}
}

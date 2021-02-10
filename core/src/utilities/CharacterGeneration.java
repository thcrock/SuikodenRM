package utilities;

import animations.ImageCache;
import entities.GameCharacter;
import entities.GameWorldCharacter;
import entities.game.characters.GameClive;
import entities.game.characters.GameKilley;
import entities.world.characters.Clive;
import entities.world.characters.Haia;
import entities.world.characters.Townfolk1;
import entities.world.characters.Townfolk2;
import entities.world.characters.Townfolk3;
import entities.world.characters.Townfolk4;
import entities.world.characters.OldMan;
import entities.world.characters.Barrel;
import entities.world.characters.Chair;
import entities.world.characters.Dog;
import entities.world.characters.Friend;
import entities.world.characters.Kid1;
import entities.world.characters.Fisherman;
import entities.world.characters.BirdRed;
import entities.world.characters.Rabbit;
import entities.world.characters.RuinsFloor;
import entities.world.characters.Invisible;
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
		else if(name.equals("Friend")) return new Friend(ImageCache.getFrame("girlbluehair", 4), bw, x, y);
		else if(name.equals("Fisherman")) return new Fisherman(ImageCache.getFrame("fishing", 1), bw, x, y);
		else if(name.equals("Haia")) return new Haia(ImageCache.getFrame("haia", 1), bw, x, y);
		else if(name.equals("Chair")) return new Chair(ImageCache.getFrame("chair", 1), bw, x, y);
		else if(name.equals("Barrel")) return new Barrel(ImageCache.getFrame("barrel", 1), bw, x, y);
		else if(name.equals("BirdRed")) return new BirdRed(ImageCache.getFrame("bird_red", 1), bw, x, y);
		else if(name.equals("Rabbit")) return new Rabbit(ImageCache.getFrame("rabbit", 1), bw, x, y);
		else if(name.equals("RuinsFloor")) return new RuinsFloor(ImageCache.getFrame("ruins_stone", 1), bw, x, y);
		return new Invisible(ImageCache.getFrame("chair", 1), bw, x, y);
	}
	
	public static GameCharacter getGameCharacter(String name) {
		if(name.equals("Clive")) return new GameClive();
		if(name.equals("Killey")) return new GameKilley();
		return null;
	}
}

package entities;

import com.orangeegames.suikorm.SuikodenRM;


public class Collider {

	public String message;
	
	public Collider(String message) {
		this.message = message;
	}

	public void interact(Player player) {
		SuikodenRM.gsm.setInfo(this.message);
	}
}

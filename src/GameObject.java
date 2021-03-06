import java.io.IOException;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Movable;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resizable;
import com.senac.SimpleJava.Graphics.Sprite;

public class GameObject extends Sprite implements Movable, Resizable {
	
	private static GameObject object;

	public GameObject(String filename, Color background) throws IOException {
		super(filename);
	}
	public static GameObject createObject(String pathToFile, int x, int y, Color background){
		try {
			object = new GameObject(pathToFile, background);
		} catch (IOException e) {
			e.printStackTrace();
		}
		object.setPosition(x, y);
		return object;
	}
	public boolean clicked(Point p) {
		int objX = this.getBounds().x;
		int objY = this.getBounds().y;
		int objHeight = this.getHeight();
		int objWidth = this.getWidth();
		
		if (p.x >= objX && p.y >= objY && p.x <= objX+objHeight && p.y <= objY+objWidth) {
			return true;
		}
		return false;
	}
}

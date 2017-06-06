import java.io.IOException;

import com.senac.SimpleJava.Graphics.Movable;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resizable;
import com.senac.SimpleJava.Graphics.Sprite;

public class GameObject extends Sprite implements Movable, Resizable {
	
	private static GameObject object;

	public GameObject(String filename) throws IOException {
		super(filename);
	}
	
	public static GameObject createObject(String pathToImg, int x, int y){
		try {
			object = new GameObject(pathToImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		object.setPosition(x, y);
		return object;
	}

	public boolean clicked(Point p) {
		int objX = this.getBounds().x;
		int objY = this.getBounds().y;
		int objHeight = objX+this.getHeight();
		int objWidth = objY+this.getWidth();
		
		if (p.x >= objX && p.y >= objY && p.x <= objHeight && p.y <= objWidth) {
			return true;
		}
		
		return false;
	}
}

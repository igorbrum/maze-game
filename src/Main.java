
import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.MouseEvent;
import com.senac.SimpleJava.Graphics.events.MouseObserver;

public class Main extends GraphicApplication implements MouseObserver {
	public GameObject heroObject;
	public GameObject doorObject[] = new GameObject[6];
	public Door door;
	public Maze maze;
	public Room room;
	public String pathToFileHero, pathToFileDoor, pathToBackground, pathToFileDungeonMap;
	
	@Override
	protected void setup() {
		maze = new Maze();
		room = new Room();
		door = new Door();
		
		Resolution res = Resolution.HIGHRES;
		
		//Arquivos iniciais de configuracao
		pathToFileHero = "img/hero-noitem.png";
		pathToFileDoor = "img/dungeon-door.png";
		pathToFileDungeonMap = "files/Labirinto.txt";
		
		setTitle("MazeGame");
		setResolution(res);
		heroObject = GameObject.createObject(pathToFileHero, 375, 250);
		
		doorObject[0] = GameObject.createObject(pathToFileDoor, 368, 10);//Saida NORTEs
		doorObject[1] = GameObject.createObject(pathToFileDoor, 368, 522);//Saida SUL
		doorObject[2] = GameObject.createObject(pathToFileDoor, 93, 254);//Saida LESTE
		doorObject[3] = GameObject.createObject(pathToFileDoor, 647, 254);//Saida OESTE
		doorObject[4] = GameObject.createObject(pathToFileDoor, 470, 150);//Saida ACIMA
		doorObject[5] = GameObject.createObject(pathToFileDoor, 230, 415);//Saida ABAIXO
		
		maze.createMaze(pathToFileDungeonMap);
		room = maze.randomInitialRoom();

		addMouseObserver(MouseEvent.CLICK, this);
	}

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		pathToBackground = "img/dungeon.png";
		int roomNumber = room.getRoomNumber();
		room.setRoomBackgroundImage(pathToBackground);
		Image background = room.getRoomBackgroundImage();

		canvas.drawImage(background, 0, 0);
		heroObject.draw(canvas);
		canvas.putText(10, 0, 30, "Sala Numero: "+roomNumber);
		
		if (room.getNorthNumber() >= 0) {
			door.setLeadTo(room.getNorthNumber());
			door.setObj(doorObject[0]);
			GameObject d = door.getObj();
			d.draw(canvas);
		}
	}

	@Override
	protected void loop() {
		
	}
	
	@Override
	public void notify(MouseEvent evento, int b, Point p) {
		if (door.getObj().clicked(p)) {
			Console.println("Clicou certo");
		}
	}
}
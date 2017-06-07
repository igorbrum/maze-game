import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.MouseEvent;
import com.senac.SimpleJava.Graphics.events.MouseObserver;

public class Main extends GraphicApplication implements MouseObserver {
	public String pathToFileHero, pathToFileDoor, pathToBackground, pathToFileDungeonMap, pathToFileKey;
	public GameObject doorObject[] = new GameObject[6];
	public Door door[] = new Door[6];
	public GameObject heroObject, keyObject;
	public Maze maze;
	public Room room;
	
	@Override
	protected void setup() {
		maze = new Maze();
		room = new Room();
		
		//ARQUIVO QUE CONTEM A CONFIGURACAO DE SALAS DO LABIRINTO
		pathToFileDungeonMap = "files/Labirinto.txt";
		
		//IMAGENS USADAS NOS SPRITES
		pathToFileHero = "img/hero-noitem.png";
		pathToFileDoor = "img/dungeon-door.png";
		pathToFileKey = "img/key.png";
		
		//TITULO DA TELA DO JOGO E RESOLUCAO
		setTitle("MazeGame");
		setResolution(Resolution.HIGHRES);
		
		//CRIACAO DO SPRITE DO HEROI
		heroObject = GameObject.createObject(pathToFileHero, 375, 250);
		keyObject = GameObject.createObject(pathToFileKey, 0, 0);
		
		//CRIACAO DE SPRITES DAS PORTAS E ESCADAS (A.K.A SAIDAS) NORTE-SUL-OESTE-LESTE-ACIMA-ABAIXO (NESTA ORDEM)
		doorObject[0] = GameObject.createObject(pathToFileDoor, 368, 10);
		doorObject[1] = GameObject.createObject(pathToFileDoor, 368, 522);
		doorObject[2] = GameObject.createObject(pathToFileDoor, 647, 254);
		doorObject[3] = GameObject.createObject(pathToFileDoor, 93, 254);
		doorObject[4] = GameObject.createObject(pathToFileDoor, 470, 150);
		doorObject[5] = GameObject.createObject(pathToFileDoor, 230, 415);
		
		//INICIA O LABIRINTO
		maze.createMaze(pathToFileDungeonMap);
		room = maze.randomInitialRoom();

		//MouseObserver PARA CAPTURAR O CLICK NA TELA
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
		keyObject.draw(canvas);
		canvas.putText(10, 0, 30, "Sala Numero: "+roomNumber);
		
		if (room.getNorthNumber() >= 0) {
			door[0] = maze.createDoor(room.getNorthNumber(), doorObject[0]);
			door[0].getObj().draw(canvas);
		}
		if (room.getSouthNumber() >= 0) {
			door[1] = maze.createDoor(room.getSouthNumber(), doorObject[1]);
			door[1].getObj().draw(canvas);
		}
		if (room.getEastNumber() >= 0) {
			door[2] = maze.createDoor(room.getEastNumber(), doorObject[2]);
			door[2].getObj().draw(canvas);
		}
		if (room.getWestNumber() >= 0) {
			door[3] = maze.createDoor(room.getWestNumber(), doorObject[3]);
			door[3].getObj().draw(canvas);
		}
		if (room.getUpNumber() >= 0) {
			door[4] = maze.createDoor(room.getUpNumber(), doorObject[4]);
			door[4].getObj().draw(canvas);
		}
		if (room.getDownNumber() >= 0) {
			door[5] = maze.createDoor(room.getDownNumber(), doorObject[5]);
			door[5].getObj().draw(canvas);
		}
	}
	
	@Override
	protected void loop() {}
	
	@Override
	public void notify(MouseEvent evento, int b, Point p) {
		for (int i = 0; i < door.length; i++) {
			if (door[i] != null) {
				if (door[i].getObj().clicked(p)) {
					if (door[i].getLeadTo() == 0) {
						Console.println("Você ganhou o jogo!");
						System.exit(0);
					} else {
						room = maze.callNextRoom(door[i].getLeadTo());
						redraw();
					}
				}
			}
		}
	}
}
import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.MouseEvent;
import com.senac.SimpleJava.Graphics.events.MouseObserver;

public class Main extends GraphicApplication implements MouseObserver {
	public String pathToFileHero, pathToFileDoor, pathToBackground, pathToFileDungeonMap, pathToFileKey, pathToFileContainer;
	public GameObject doorObject[] = new GameObject[6];
	public GameObject keyObject[] = new GameObject[4];
	public Door door[] = new Door[6];
	public Key key[] = new Key[4];
	public GameObject heroObject, containerObject;
	public Maze maze;
	public Room room;
	public Item item[] = new Item[2];
	
	@Override
	protected void setup() {
		maze = new Maze();
		room = new Room();
		
		//ARQUIVOS INICIAIS DE CONFIGURACAO
		initialFiles();
		
		//TITULO DA TELA DO JOGO E RESOLUCAO
		setTitle("MazeGame");
		setResolution(Resolution.HIGHRES);
		
		//INICIA O LABIRINTO
		maze.createMaze(pathToFileDungeonMap);
		
		//CRIA OS OBJETOS DO LABIRINTO
		createObjects();
		
		//POSICAO ALEATORIA INICIAL DAS CHAVES 
		for (int i = 0; i < key.length; i++) {
			key[i] = randomKeys(i);
		}
		
		//SALA INICIAL DO LABIRINTO EM MODO ALEATORIO
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
		containerObject.draw(canvas);
		canvas.putText(10, 0, 30, "Sala Numero: "+roomNumber);
		
		if (hasNorthDoor()) {
			door[0].getObj().draw(canvas);
		}
		if (hasSouthDoor()) {
			door[1].getObj().draw(canvas);
		}
		if (hasEastDoor()) {
			door[2].getObj().draw(canvas);
		}
		if (hasWestDoor()) {
			door[3].getObj().draw(canvas);
		}
		if (hasUpDoor()) {
			door[4].getObj().draw(canvas);
		}
		if (hasDownDoor()) {
			door[5].getObj().draw(canvas);
		}
		for (int i = 0; i < key.length; i++) {
			if (roomNumber == key[i].getRoomNumber() && key[i].isItemShow()) {
				key[i].getObj().draw(canvas);
			}
		}
	}
	
	@Override
	protected void loop() {}
	
	@Override
	public void notify(MouseEvent evento, int b, Point p) {
		//VERIFICACAO DO CLIQUE NA PORTA
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
		//VERIFICACAO DO CLIQUE NA CHAVE
		for (int i = 0; i < key.length; i++) {
			if (key[i].getObj().clicked(p) && key[i].isItemShow()) {
				key[i].setItemShow(false);
				key[i].setItemTaked(true);
				redraw();
			}
		}
	}
	// <-- CONFIGURACAO DO LABIRINTO -->
	private void initialFiles() {
		//ARQUIVO QUE CONTEM A CONFIGURACAO DE SALAS DO LABIRINTO
		pathToFileDungeonMap = "files/Labirinto.txt";
		//IMAGENS USADAS NOS SPRITES
		pathToFileHero = "img/hero-noitem.png";
		pathToFileDoor = "img/dungeon-door.png";
		pathToFileKey = "img/key.png";
		pathToFileContainer = "img/square.png";
	}
	
	// <-- LOGICA DO LABIRINTO -->
	protected void createObjects(){
		//CRIACAO DO SPRITE DO HEROI
		containerObject = GameObject.createObject(pathToFileContainer, 0, 525, Color.BLUE);
		heroObject = GameObject.createObject(pathToFileHero, 375, 250, Color.BLUE);		
		//CRIACAO DE SPRITES DAS PORTAS E ESCADAS (A.K.A SAIDAS) NORTE-SUL-OESTE-LESTE-ACIMA-ABAIXO (NESTA ORDEM)
		doorObject[0] = GameObject.createObject(pathToFileDoor, 368, 10, Color.BLUE);
		doorObject[1] = GameObject.createObject(pathToFileDoor, 368, 522, Color.BLUE);
		doorObject[2] = GameObject.createObject(pathToFileDoor, 647, 254, Color.BLUE);
		doorObject[3] = GameObject.createObject(pathToFileDoor, 93, 254, Color.BLUE);
		doorObject[4] = GameObject.createObject(pathToFileDoor, 470, 150, Color.BLUE);
		doorObject[5] = GameObject.createObject(pathToFileDoor, 230, 415, Color.BLUE);
		//CRIACAO DE SPRITES DAS CHAVES
		keyObject[0] = GameObject.createObject(pathToFileKey, 230, 145, Color.BLUE);
		keyObject[1] = GameObject.createObject(pathToFileKey, 600, 200, Color.BLUE);
		keyObject[2] = GameObject.createObject(pathToFileKey, 350, 350, Color.BLUE);
		keyObject[3] = GameObject.createObject(pathToFileKey, 600, 400, Color.BLUE);
	}
	protected Key randomKeys(int i) {
		Key k = new Key();
		//int randomRoomNumber = (int)(Math.random()*(31-1));
		int randomRoomNumber = 10;
		int randomColor = (int)(Math.random()*(3-0));
		k = maze.createKeys(randomRoomNumber, randomColor, keyObject[i], true);
		return k;
	}
	private boolean hasNorthDoor(){
		if (room.getNorthNumber() >= 0) {
			door[0] = maze.createDoor(room.getNorthNumber(), doorObject[0]);
			return true;
		}
		return false;
	}
	private boolean hasSouthDoor(){
		if (room.getSouthNumber() >= 0) {
			door[1] = maze.createDoor(room.getSouthNumber(), doorObject[1]);
			return true;
		}
		return false;
	}
	private boolean hasEastDoor(){
		if (room.getEastNumber() >= 0) {
			door[2] = maze.createDoor(room.getEastNumber(), doorObject[2]);
			return true;
		}
		return false;
	}
	private boolean hasWestDoor(){
		if (room.getWestNumber() >= 0) {
			door[3] = maze.createDoor(room.getWestNumber(), doorObject[3]);
			return true;
		}
		return false;
	}
	private boolean hasUpDoor(){
		if (room.getUpNumber() >= 0) {
			door[4] = maze.createDoor(room.getUpNumber(), doorObject[4]);
			return true;
		}
		return false;
	}
	private boolean hasDownDoor(){
		if (room.getDownNumber() >= 0) {
			door[5] = maze.createDoor(room.getDownNumber(), doorObject[5]);
			return true;
		}
		return false;
	}
}
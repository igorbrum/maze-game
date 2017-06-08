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
	private String pathToFileHero, pathToFileDoor, pathToBackground, pathToFileDungeonMap, pathToFileKey, pathToFileContainer;
	private String pathToArmorLeather, pathToArmorChainMail, pathToArmorMithril;
	private GameObject doorObject[] = new GameObject[6];
	private GameObject keyObject[] = new GameObject[4];
	private GameObject armorObject[] = new GameObject[3];
	private Door door[] = new Door[6];
	private Key key[] = new Key[4];
	private Item item[] = new Item[2];
	private Armor armor[] = new Armor[3];
	private GameObject heroObject, containerObject;
	private Maze maze;
	private Room room;
	
	@Override
	protected void setup() {
		maze = new Maze();
		room = new Room();
		int randomRoom = (int)(Math.random()*(31-0));
		
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
		//POSICAO ALEATORIA INICIAL DAS ARMADURAS
		for (int i = 0; i < armor.length; i++) {
			armor[i] = randomArmor(i);
		}
		
		//SALA INICIAL DO LABIRINTO EM MODO ALEATORIO
		room = maze.callNextRoom(10);

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
		
		hasNorthDoor(canvas, 0);
		hasSouthDoor(canvas, 1);
		hasEastDoor(canvas, 2);
		hasWestDoor(canvas, 3);
		hasUpDoor(canvas, 4);
		hasDownDoor(canvas, 5);
		
		hasKey(canvas, roomNumber);
		hasArmor(canvas, roomNumber);
	}

	@Override
	protected void loop() {}
	
	@Override
	public void notify(MouseEvent evento, int b, Point p) {
		//VERIFICACAO DO CLIQUE NA PORTA
		doorClicked(p);
		//VERIFICACAO DO CLIQUE NO ITEM
		itemClicked(p);
		/**/
	}
	private void itemClicked(Point p) {
		for (int i = 0; i < key.length; i++) {
			if (key[i].getObj().clicked(p) && key[i].isItemShow()) {
				key[i].setItemShow(false);
				key[i].setItemTaked(true);
				Console.println(key[i].getObj().getBounds(), " - chave:", i);
				redraw();
			}
		}
	}
	private void doorClicked(Point p) {
		for (int i = 0; i < door.length; i++) {
			if (door[i] != null && door[i].getObj().clicked(p)) {
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

	// <-- CONFIGURACAO DO LABIRINTO -->
	private void initialFiles() {
		//ARQUIVO QUE CONTEM A CONFIGURACAO DE SALAS DO LABIRINTO
		pathToFileDungeonMap = "files/Labirinto.txt";
		//IMAGENS USADAS NOS SPRITES
		pathToFileHero = "img/hero-noitem.png";
		pathToFileDoor = "img/dungeon-door.png";
		pathToFileKey = "img/key.png";
		pathToFileContainer = "img/square.png";
		pathToArmorLeather = "img/armor-leather.png";
		pathToArmorChainMail = "img/armor-chain.png";
		pathToArmorMithril = "img/armor-mithril.png";
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
		//CRIACAO DE SPRITES DAS ARMADURAS
		armorObject[0] = GameObject.createObject(pathToArmorLeather, 100, 100, Color.BLUE);
		//armorObject[1] = GameObject.createObject(pathToArmorChainMail, 200, 200, Color.BLUE);
		//armorObject[2] = GameObject.createObject(pathToArmorMithril, 300, 300, Color.BLUE);
		//armorObject[0] = GameObject.createObject(pathToFileDoor, 100, 100, Color.BLUE);
		armorObject[1] = GameObject.createObject(pathToFileDoor, 200, 200, Color.BLUE);
		armorObject[2] = GameObject.createObject(pathToFileDoor, 300, 300, Color.BLUE);
	}
	private void hasNorthDoor(Canvas canvas, int i){
		if (room.getNorthNumber() >= 0) {
			door[i] = maze.createDoor(room.getNorthNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasSouthDoor(Canvas canvas, int i){
		if (room.getSouthNumber() >= 0) {
			door[i] = maze.createDoor(room.getSouthNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasEastDoor(Canvas canvas, int i){
		if (room.getEastNumber() >= 0) {
			door[i] = maze.createDoor(room.getEastNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasWestDoor(Canvas canvas, int i){
		if (room.getWestNumber() >= 0) {
			door[i] = maze.createDoor(room.getWestNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasUpDoor(Canvas canvas, int i){
		if (room.getUpNumber() >= 0) {
			door[i] = maze.createDoor(room.getUpNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasDownDoor(Canvas canvas, int i){
		if (room.getDownNumber() >= 0) {
			door[i] = maze.createDoor(room.getDownNumber(), doorObject[i]);
			door[i].getObj().draw(canvas);
		}
	}
	private Key randomKeys(int i) {
		Key k = new Key();
		//int randomRoomNumber = (int)(Math.random()*(31-1));
		int randomRoomNumber = 10;
		int randomColor = (int)(Math.random()*(3-0));
		k = maze.createKeys(randomRoomNumber, randomColor, keyObject[i], true);
		return k;
	}
	private void hasKey(Canvas canvas, int roomNumber) {
		for (int i = 0; i < key.length; i++) {
			if (roomNumber == key[i].getRoomNumber() && key[i].isItemShow()) {
				key[i].getObj().draw(canvas);
			}
		}
	}
	private Armor randomArmor(int i){
		Armor a = new Armor();
		//int randomRoomNumber = (int)(Math.random()*(31-1));
		int randomRoomNumber = 10;
		a = maze.createArmor(randomRoomNumber, armorObject[i], true);
		return a;
	}
	private void hasArmor(Canvas canvas, int roomNumber) {
		for (int i = 0; i < armor.length; i++) {
			if (roomNumber == armor[i].getRoomNumber() && armor[i].isItemShow()) {
				armor[i].getObj().draw(canvas);
			}
		}
	}
}
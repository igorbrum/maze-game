import java.io.IOException;

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
	private String pathToFileHero, pathToFileDoor, pathToBackground, pathToFileDungeonMap, pathToFileKey, pathToFileContainerBackground;
	private String pathToArmorLeather, pathToArmorChainMail, pathToArmorMithril;
	private String pathToFileWeaponDagger, pathToFileWeaponKnife, pathToFileWeaponLongSword, pathToFileWeaponShortSword;
	private String pathToFileEnemyGoblin, pathToFileEnemyOrc, pathToFileEnemyTroll;
	private GameObject doorObject[] = new GameObject[6];
	private GameObject keyObject[] = new GameObject[4];
	private GameObject armorObject[] = new GameObject[3];
	private GameObject weaponObject[] = new GameObject[4];
	private GameObject enemyObject[] = new GameObject[3];
	private Door door[] = new Door[6];
	private Key key[] = new Key[4];
	private Item item[] = new Item[2];
	private Armor armor[] = new Armor[3];
	private Weapon weapon[] = new Weapon[4];
	private Enemy enemy[] = new Enemy[3];
	private String typeEnemy[] = new String [3];
	private GameObject heroObject;
	private Maze maze;
	private Room room;
	private Hero hero;
	private int countItem = 0;
	private int countArmor = 0;
	private int countWeapon;
	
	// <-- GAME CORE -->
	@Override
	protected void setup() {
		maze = new Maze();
		room = new Room();
		String typeArmor[] = new String [3];
		String typeWeapon[] = new String [4];
		
		typeArmor[0] = "leatherMail";
		typeArmor[1] = "chainMail";
		typeArmor[2] = "mithrilMail";
		
		typeWeapon[0] = "dagger";
		typeWeapon[1] = "knife";
		typeWeapon[2] = "shortsword";
		typeWeapon[3] = "longsword";
		
		typeEnemy[0] = "goblin";
		typeEnemy[1] = "orc";
		typeEnemy[2] = "troll";
		//ARQUIVOS INICIAIS DE CONFIGURACAO
		initialFiles();
		
		//TITULO DA TELA DO JOGO E RESOLUCAO
		setTitle("MazeGame");
		setResolution(Resolution.HIGHRES);
		
		//INICIA O LABIRINTO
		maze.createMaze(pathToFileDungeonMap);
		
		//CRIA OS OBJETOS DO LABIRINTO
		createObjects();
		
		hero = maze.createHero();
		
		//POSICAO ALEATORIA INICIAL DAS CHAVES 
		for (int i = 0; i < key.length; i++) {
			key[i] = randomKeys(i);
		}
		//POSICAO ALEATORIA INICIAL DAS ARMADURAS
		for (int i = 0; i < armor.length; i++) {
			armor[i] = randomArmor(i, typeArmor[i]);
		}
		//POSICAO ALEATORIA INICIAL DAS ARMAS
		for (int i = 0; i < weapon.length; i++) {
			weapon[i] = randomWeapon(i, typeWeapon[i]);
		}
		for (int i = 0; i < enemy.length; i++) {
			enemy[i] = randomEnemy(i, typeEnemy[i]);
		}
		
		//SALA INICIAL DO LABIRINTO EM MODO ALEATORIO
		room = maze.callNextRoom((int)(Math.random()*(31-0)));

		//MouseObserver PARA CAPTURAR O CLICK NA TELA
		addMouseObserver(MouseEvent.CLICK, this);
	}
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		int roomNumber = room.getRoomNumber();
		room.setRoomBackgroundImage(pathToBackground);
		Image background = room.getRoomBackgroundImage();
		Image backgroundContainer = setBackgroundContainer(pathToFileContainerBackground);;
		
		canvas.drawImage(background, 0, 0);
		canvas.drawImage(backgroundContainer, 25, 528);
		heroObject.draw(canvas);
		canvas.putText(620, 500, 17, "Room Number: "+roomNumber);
		canvas.putText(620, 520, 17, "Damage: "+hero.getDamage());
		canvas.putText(620, 540, 17, "Armor Class: "+hero.getArmorClass());
		canvas.putText(620, 560, 17, "Change to Hit: "+hero.getAccuracyHit()*100+"%");
		canvas.putText(620, 580, 17, "Hit Points: "+hero.getLife());
		
		//DESENHA PORTAS QUANDO A CONFIGURACAO DA SALA PERMITE ISSO
		hasNorthDoor(canvas, 0);
		hasSouthDoor(canvas, 1);
		hasEastDoor(canvas, 2);
		hasWestDoor(canvas, 3);
		hasUpDoor(canvas, 4);
		hasDownDoor(canvas, 5);
		//DESENHA OS ITENS QUANDO A CONFIGURACAO DA SALA PERMITE ISSO
		hasItem(canvas);
		hasKey(canvas, roomNumber);
		hasArmor(canvas, roomNumber);
		hasWeapon(canvas, roomNumber);
	}
	@Override
	protected void loop() {}
	@Override
	public void notify(MouseEvent evento, int b, Point p) {
		//VERIFICACAO DO CLIQUE NA PORTA
		doorClicked(p);
		//VERIFICACAO DO CLIQUE NA CHAVE
		keyClicked(p);
		//VERIFICACAO DO CLIQUE NA ARMADURA
		armorClicked(p);
		//VERIFICACAO DO CLIQUE NA ARMA
		weaponClicked(p);
		enemyClicked(p);
	}
	
	// <-- CONFIGURACAO DO LABIRINTO -->
	private void initialFiles() {
		//ARQUIVO QUE CONTEM A CONFIGURACAO DE SALAS DO LABIRINTO
		pathToFileDungeonMap = "files/Labirinto.txt";
		//IMAGENS USADAS NOS SPRITES
		pathToBackground = "img/dungeon.png";
		pathToFileHero = "img/hero-noitem.png";
		pathToFileContainerBackground = "img/square-container.png";
		pathToFileDoor = "img/dungeon-door.png";
		pathToFileKey = "img/item-key.png";
		//IMAGENS USADAS NOS SPRITES DE ARMADURA
		pathToArmorLeather = "img/armor-leather.png";
		pathToArmorChainMail = "img/armor-chainmail.png";
		pathToArmorMithril = "img/armor-mithril.png";
		//IMAGENS USADAS NOS SPRITES DE ARMAS
		pathToFileWeaponDagger = "img/weapon-dagger.png";
		pathToFileWeaponKnife = "img/weapon-knife.png";
		pathToFileWeaponShortSword = "img/weapon-shortsword.png";
		pathToFileWeaponLongSword = "img/weapon-longsword.png";
		//IMAGENS USADAS NOS SPRITES DE INIMIGOS
		pathToFileEnemyGoblin = "img/enemy-goblin.png";
		pathToFileEnemyOrc = "img/enemy-orc.png";
		pathToFileEnemyTroll = "img/enemy-troll.png";
	}
	private void createObjects(){
		//CRIACAO DO SPRITE DO HEROI
		heroObject = GameObject.createObject(pathToFileHero, 375, 250, Color.GREEN);
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
		armorObject[0] = GameObject.createObject(pathToArmorLeather, 250, 155, Color.BLUE);
		armorObject[1] = GameObject.createObject(pathToArmorChainMail, 550, 250, Color.BLUE);
		armorObject[2] = GameObject.createObject(pathToArmorMithril, 250, 250, Color.BLUE);
		//CRIACAO DE SPRITES DAS ARMAS
		weaponObject[0] = GameObject.createObject(pathToFileWeaponDagger, 280, 475, Color.RED);
		weaponObject[1] = GameObject.createObject(pathToFileWeaponKnife, 350, 475, Color.BLUE);
		weaponObject[2] = GameObject.createObject(pathToFileWeaponShortSword, 400, 475, Color.BLUE);
		weaponObject[3] = GameObject.createObject(pathToFileWeaponLongSword, 450, 475, Color.BLUE);
		//CRIACAO DE SPRITES DOS INIMIGOS
		enemyObject[0] = GameObject.createObject(pathToFileEnemyGoblin, 0, 0, Color.BLUE);
		enemyObject[1] = GameObject.createObject(pathToFileEnemyOrc, 0, 0, Color.BLUE);
		enemyObject[2] = GameObject.createObject(pathToFileEnemyTroll, 0, 0, Color.BLUE);
	}
	private Image setBackgroundContainer(String pathToFile) {
		try {
			Image backgroundContainer = new Image(pathToFile);
			return backgroundContainer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// <-- LOGICA DO LABIRINTO -->
	private void hasNorthDoor(Canvas canvas, int i){
		if (room.getNorthNumber() >= 0) {
			door[i] = maze.createDoor(room.getNorthNumber(), doorObject[i], 90);
			door[i].getObj().draw(canvas);
			hasEnemy(canvas, i);
		}
	}
	private void hasSouthDoor(Canvas canvas, int i){
		if (room.getSouthNumber() >= 0) {
			door[i] = maze.createDoor(room.getSouthNumber(), doorObject[i], 10);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasEastDoor(Canvas canvas, int i){
		if (room.getEastNumber() >= 0) {
			door[i] = maze.createDoor(room.getEastNumber(), doorObject[i], 10);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasWestDoor(Canvas canvas, int i){
		if (room.getWestNumber() >= 0) {
			door[i] = maze.createDoor(room.getWestNumber(), doorObject[i], 10);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasUpDoor(Canvas canvas, int i){
		if (room.getUpNumber() >= 0) {
			door[i] = maze.createDoor(room.getUpNumber(), doorObject[i], 10);
			door[i].getObj().draw(canvas);
		}
	}
	private void hasDownDoor(Canvas canvas, int i){
		if (room.getDownNumber() >= 0) {
			door[i] = maze.createDoor(room.getDownNumber(), doorObject[i], 10);
			door[i].getObj().draw(canvas);
		}
	}
	private Key randomKeys(int i) {
		Key k = new Key();
		int randomRoomNumber = (int)(Math.random()*(31-1));
		int randomColor = (int)(Math.random()*(3-0));
		k = maze.createKeys(randomRoomNumber, randomColor, keyObject[i], true);
		return k;
	}
	private void hasKey(Canvas canvas, int roomNumber) {
		for (int i = 0; i < key.length; i++) {
			if (key[i].getRoomNumber() == 0) {
				key[i].getObj().draw(canvas);
			}
			if (roomNumber == key[i].getRoomNumber() && key[i].isItemShow()) {
				key[i].getObj().draw(canvas);
			}
		}
	}
	private Armor randomArmor(int i, String typeArmor){
		Armor a = new Armor();
		int randomRoomNumber = (int)(Math.random()*(31-1));
		a = maze.createArmor(randomRoomNumber, typeArmor, armorObject[i], true);
		return a;
	}
	private void hasArmor(Canvas canvas, int roomNumber) {
		for (int i = 0; i < armor.length; i++) {
			if (armor[i].getRoomNumber() == 0) {
				armor[i].getObj().draw(canvas);
			}
			if (roomNumber == armor[i].getRoomNumber() && armor[i].isItemShow()) {
				armor[i].getObj().draw(canvas);
			}
		}
	}
	private Weapon randomWeapon(int i, String typeArmor) {
		Weapon w = new Weapon();
		int randomRoomNumber = (int)(Math.random()*(31-1));
		w = maze.createWeapon(randomRoomNumber, typeArmor, weaponObject[i], true);
		return w;
	}
	private void hasWeapon(Canvas canvas, int roomNumber) {
		for (int i = 0; i < weapon.length; i++) {
			if (weapon[i].getRoomNumber() == 0) {
				weapon[i].getObj().draw(canvas);
			}
			if (roomNumber == weapon[i].getRoomNumber() && weapon[i].isItemShow()) {
				weapon[i].getObj().draw(canvas);
			}
		}
	}
	private Enemy randomEnemy(int i, String string) {
		Enemy e = new Enemy();
		e = maze.createEnemy(string, enemyObject[i]);
		return e;
	}
	private void hasEnemy(Canvas canvas, int i) {
		double rand = (Math.random()*(100-0));
		int randEnemy = (int)(Math.random()*(3-0));
		if (door[i].getChangeEnemy() >= rand) {
			door[i].setEnemyAlive(true);
			enemy[randEnemy].getObj().setPosition(door[i].getObj().getBounds().x, door[i].getObj().getBounds().y);
			enemy[randEnemy].getObj().draw(canvas);
		}
	}
	private void hasItem(Canvas canvas) {
		for (int i = 0; i < item.length; i++) {
			if (item[i] != null) {
				item[i].getObj().draw(canvas);
			}
		}
	}
	private boolean inventorySlotOne(Object obj, int i){
		Class<? extends Object> str = obj.getClass();
		if (str == key[i].getClass()) {
			if (countItem == 0) {
				key[i].getObj().setPosition(60, 530);
				return true;
			}
		}
		if (str == armor[i].getClass()) {
			if (countItem == 0 && countArmor == 0) {
				armor[i].getObj().setPosition(60, 530);
				return true;
			}
		}
		if (str == weapon[i].getClass()) {
			if (countItem == 0 && countWeapon == 0) {
				weapon[i].getObj().setPosition(60, 530);
				return true;
			}
		}
		return false;
	}
	private boolean inventorySlotTwo(Object obj, int i){
		Class<? extends Object> str = obj.getClass();
		if (str == key[i].getClass()) {
			key[i].getObj().setPosition(150, 530);
			return true;
		}
		if (str == armor[i].getClass()) {
			if (countItem == 1 && countArmor == 0) {
				armor[i].getObj().setPosition(150, 530);
				return true;
			}
		}
		if (str == weapon[i].getClass()) {
			if (countItem == 1 && countWeapon == 0) {
				weapon[i].getObj().setPosition(150, 530);
				return true;
			}
		}
		return false;
	}
	private boolean dropItemInventorySlotOne(Object obj, int i) {
		Class<? extends Object> str = obj.getClass();
		if (str == key[i].getClass()) {
			key[i].getObj().setPosition(400, 100);
			return true;
		}
		if (str == armor[i].getClass()) {
			armor[i].getObj().setPosition(400, 100);
			return true;
		}
		if (str == weapon[i].getClass()) {
			weapon[i].getObj().setPosition(400, 100);
			return true;
		}
		return false;
	}
	private boolean dropItemInventorySlotTwo(Object obj, int i) {
		Class<? extends Object> str = obj.getClass();
		if (str == key[i].getClass()) {
			key[i].getObj().setPosition(300, 100);
			return true;
		}
		if (str == armor[i].getClass()) {
			armor[i].getObj().setPosition(300, 100);
			return true;
		}
		if (str == weapon[i].getClass()) {
			weapon[i].getObj().setPosition(300, 100);
			return true;
		}
		return false;
	}
	private void keyClicked(Point p) {
		for (int i = 0; i < key.length; i++) {
			if (key[i].getObj().clicked(p) && !key[i].isItemTaked()) {
				if (inventorySlotOne(key[i], i) || inventorySlotTwo(key[i], i)) {
					key[i].setItemTaked(true);
					key[i].setRoomNumber(0);
					countItem++;
					redraw();
				}
			}
			if (key[i].getObj().clicked(p) && key[i].isItemTaked()) {
				if (dropItemInventorySlotOne(key[i], i) || dropItemInventorySlotTwo(key[i], i)) {
					key[i].setItemTaked(false);
					key[i].setRoomNumber(room.getRoomNumber());
					countItem--;
					redraw();
				}
			}
		}
	}
	private void armorClicked(Point p) {
		for (int i = 0; i < armor.length; i++) {
			if (armor[i].getObj().clicked(p) && !armor[i].isItemTaked()) {
				if (inventorySlotOne(armor[i], i) || inventorySlotTwo(armor[i], i)) {
					hero.setArmorClass(armor[i].getArmorClass());
					armor[i].setItemTaked(true);
					armor[i].setRoomNumber(0);
					countArmor++;
					countItem++;
					redraw();
				}
			}
			if (armor[i].getObj().clicked(p) && armor[i].isItemTaked()) {
				if (dropItemInventorySlotOne(armor[i], i) || dropItemInventorySlotOne(armor[i], i)) {
					int ac;
					ac = hero.getArmorClass() - armor[i].getArmorClass();
					hero.setArmorClass(ac);
					armor[i].setItemTaked(false);
					armor[i].setRoomNumber(room.getRoomNumber());
					countItem--;
					countArmor--;
					redraw();
				}
			}
		}
	}
	private void weaponClicked(Point p) {
		for (int i = 0; i < weapon.length; i++) {
			if (weapon[i].getObj().clicked(p) && !weapon[i].isItemTaked()) {
				if (inventorySlotOne(weapon[i], i) || inventorySlotTwo(weapon[i], i)) {
					hero.setDamage(hero.getDamage() + weapon[i].getDamageWeapon());
					weapon[i].setItemTaked(true);
					weapon[i].setRoomNumber(0);
					countWeapon++;
					countItem++;
					redraw();
				}
			}
			if (weapon[i].getObj().clicked(p) && weapon[i].isItemTaked()) {
				if (dropItemInventorySlotOne(weapon[i], i) || dropItemInventorySlotOne(weapon[i], i)) {
					int damage;
					damage = hero.getDamage() - weapon[i].getDamageWeapon();
					hero.setDamage(damage);
					weapon[i].setItemTaked(false);
					weapon[i].setRoomNumber(room.getRoomNumber());
					countItem--;
					countWeapon--;
					redraw();
				}
			}
		}
	}
	private void doorClicked(Point p) {
		for (int i = 0; i < door.length; i++) {
			if (door[i] != null && door[i].getObj().clicked(p) && !door[i].isEnemyAlive()) {
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
	private void enemyClicked(Point p) { 
		for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].getObj().clicked(p) && enemy[i].getLife() > 0) {
				int hpEnemy = enemy[i].getLife();
				int hpHero = hero.getLife();
				enemy[i].setLife(hpEnemy - hero.getDamage());
				hero.setLife(hpHero - enemy[i].getDamage());
			}
		}
	}
}
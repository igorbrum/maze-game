import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.senac.SimpleJava.Console;

public class Maze {
	public Room room[] = new Room[32];
	
	private void readFile(String nameFile) {
		try {
			Scanner fileToRead = new Scanner(new FileInputStream(nameFile));
			int i = 1;
			while (fileToRead.hasNext()) {
				String coordinates = fileToRead.nextLine();
				room[i] = createRoom(coordinates);
				i++;
			}
			fileToRead.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println("Arquivo nao encontrado");
		} catch (Exception e) {
			System.err.println("Erro interno do sistema");
		}
	}
	public void createMaze(String pathToFile) {
		String nameFile = pathToFile;
		readFile(nameFile);
	}
	private Room createRoom(String coordinates) {
		Room r = new Room();
		r.setRoomCoordinates(coordinates);
		return r;
	}
	public Room randomInitialRoom() {
		Room r = new Room();
		int i = (int)(Math.random() * (31 - 0));
		r = room[28];
		return r;
	}
	public Room callNextRoom(int i) {
		Room r = new Room();
		r = room[i];
		return r;
	}
}

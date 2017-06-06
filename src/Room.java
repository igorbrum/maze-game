import java.io.IOException;
import java.util.Scanner;

import com.senac.SimpleJava.Graphics.Image;

public class Room {
	
	private String roomCoordinates;
	private int roomNumber;
	private int southNumber;
	private int northNumber;
	private int westNumber;
	private int eastNumber;
	private int upNumber;
	private int downNumber;
	private Image roomBackgroundImage;

	public Room(){
		this.southNumber = -1;
		this.northNumber = -1;
		this.eastNumber = -1;
		this.westNumber = -1;
		this.upNumber = -1;
		this.downNumber = -1;
	}
	
	public void setRoomBackgroundImage(String pathToFile){
		try {
			this.roomBackgroundImage = new Image(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getRoomBackgroundImage(){
		return this.roomBackgroundImage;
	}

	public void setRoomCoordinates(String coordinates) {
		@SuppressWarnings("unused")
		String orientacao[] = new String[6];
		Scanner sc = new Scanner(coordinates);
		while (sc.hasNext()) {
			String string = (String) sc.next();
			
			if (string.equals("room")) {
				setRoomNumber(sc.nextInt());
			}
			if (string.equals("north")) {
				setNorthNumber(sc.nextInt());
			}
			if (string.equals("south")) {
				setSouthNumber(sc.nextInt());
			}
			if (string.equals("west")) {
				setWestNumber(sc.nextInt());
			}
			if (string.equals("east")) {
				setEastNumber(sc.nextInt());
			}
			if (string.equals("up")) {
				setUpNumber(sc.nextInt());
			}
			if (string.equals("down")) {
				setDownNumber(sc.nextInt());
			}
		}
		sc.close();
	}
	
	public String getRoomCoordinates(){
		return this.roomCoordinates;
	}
	
	public void setRoomNumber(int roomNumber){
		this.roomNumber = roomNumber;
	}
	
	public int getRoomNumber(){
		return this.roomNumber;
	}

	public int getSouthNumber() {
		return this.southNumber;
	}

	public void setSouthNumber(int southNumber) {
		this.southNumber = southNumber;
	}

	public int getNorthNumber() {
		return this.northNumber;
	}

	public void setNorthNumber(int northNumber) {
		this.northNumber = northNumber;
	}

	public int getWestNumber() {
		return this.westNumber;
	}

	public void setWestNumber(int westNumber) {
		this.westNumber = westNumber;
	}

	public int getEastNumber() {
		return this.eastNumber;
	}

	public void setEastNumber(int eastNumber) {
		this.eastNumber = eastNumber;
	}

	public int getUpNumber() {
		return this.upNumber;
	}

	public void setUpNumber(int upNumber) {
		this.upNumber = upNumber;
	}

	public int getDownNumber() {
		return this.downNumber;
	}

	public void setDownNumber(int downNumber) {
		this.downNumber = downNumber;
	}
}

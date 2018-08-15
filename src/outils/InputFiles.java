package outils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import evenement.Pos;

public class InputFiles {
	
	public static void writeArrayString(ObjectOutputStream file, ArrayList<String> object){
		try {
			file.writeInt(object.size());
			for(int i=0;i<object.size();i++){
				file.writeUTF(object.get(i));
			}
		} catch (IOException e) {
			System.out.println("Ecriture impossible");
			e.printStackTrace();
		}

	}
	
	public static void writeArrayPos2(ObjectOutputStream file, ArrayList<Pos2> object){
		try {
			file.writeInt(object.size());
			for(int i=0;i<object.size();i++){
				file.writeObject(object.get(i));
			}
		} catch (IOException e) {
			System.out.println("Ecriture impossible");
			e.printStackTrace();
		}

	}
	
	public static ArrayList<String> readArrayString(ObjectInputStream file, int size){
		ArrayList<String> object = new ArrayList<String>();
		try {
			for(int i=0;i<size;i++){
				object.add(file.readUTF());
			}
		} catch (IOException e) {
			System.out.println("Lecture impossible");
			e.printStackTrace();
		}
		return object;
	}
	
	public static ArrayList<Pos2> readArrayPos2(ObjectInputStream file, int size){
		ArrayList<Pos2> object = new ArrayList<Pos2>();
		try {
			for(int i=0;i<size;i++){
				object.add((Pos2)file.readObject());
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Lecture impossible");
			e.printStackTrace();
		}
		return object;
	}
	
	public static ArrayList<Pos> readArrayPos(ObjectInputStream file, int size){
		ArrayList<Pos> object = new ArrayList<Pos>();
		try {
			for(int i=0;i<size;i++){
				object.add((Pos)file.readObject());
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Lecture impossible");
			e.printStackTrace();
		}
		return object;
	}
	
	public static void writeArrayPos(ObjectOutputStream file, ArrayList<Pos> object){
		try {
			file.writeInt(object.size());
			for(int i=0;i<object.size();i++){
				file.writeObject(object.get(i));
			}
		} catch (IOException e) {
			System.out.println("Ecriture impossible");
			e.printStackTrace();
		}
	}

}

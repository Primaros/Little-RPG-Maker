package zone;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Map{
	private Tuile[][][] tab_tuile;
	public static int TAILLE_TUILE = 32;
	public static int NB_TUILE_TOTAL_X = 41;
	public static int NB_TUILE_TOTAL_Y = 41;
	public static int NB_TUILE_X = 15;
	public static int NB_TUILE_Y = 15;
	public static final Tuile tuile_vide = new Tuile("icon"+File.separator+"tile_default.png", 0, 0, 0, true);

	public Map(){
		this.init();
	}

	public void init(){
		this.tab_tuile = new Tuile[Map.NB_TUILE_TOTAL_Y][Map.NB_TUILE_TOTAL_X][4];
		for (int y = 0; y < Map.NB_TUILE_TOTAL_Y ; y++){
			for (int x = 0; x < Map.NB_TUILE_TOTAL_X ; x++){
				this.tab_tuile[y][x][Map.tuile_vide.getNiveaux()] = Map.tuile_vide;
			}
		}
	}

	public void charger(ObjectInputStream file){
		try {
			String nom_tilset_lu = "";
			ArrayList<String> nom_tilset = new ArrayList<String>();
			Tileset t = new Tileset();
			ArrayList<Tileset> til_tilset = new ArrayList<Tileset>();
			
			for(int y=0;y<this.tab_tuile.length;y++){
				for(int x=0;x<this.tab_tuile[y].length;x++){
					for(int z=0;z<this.tab_tuile[y][x].length;z++){
						
						if (file.readBoolean()){
							nom_tilset_lu = file.readUTF();
							if (nom_tilset_lu.equals("icon"+File.separator+"tile_default.png"))
							{
								this.tab_tuile[y][x][z] = Map.tuile_vide;
								file.readInt();
								file.readInt();
								file.readInt();
								file.readInt();
								file.readBoolean();
							} else {
								boolean found = false;
								for (int i=0;i<nom_tilset.size();i++){
										if(nom_tilset.get(i).equals(nom_tilset_lu)){
											t = til_tilset.get(i);
											found = true;
											break;
										}
									} 
								if (!found){
										nom_tilset.add(nom_tilset_lu);
										til_tilset.add(new Tileset(nom_tilset_lu));
										t = til_tilset.get(til_tilset.size()-1);
									}
								this.tab_tuile[y][x][z] = new Tuile(t,file.readInt(),file.readInt(),file.readInt(),file.readInt(),z,file.readBoolean());
								}
							
							
						} else
								this.tab_tuile[y][x][z]= null;
							
						
						}
					}
				}
			
		} catch (IOException e1) {
			System.out.println("Map : Le chargement de la map n'a pu s'effectuer");
			e1.printStackTrace();
		} 
		
	}

	public void sauvgarder(ObjectOutputStream file){
		try {
			for(int y=0;y<this.tab_tuile.length;y++){
				for(int x=0;x<this.tab_tuile[y].length;x++){
					for(int z=0;z<this.tab_tuile[y][x].length;z++){
						if (this.tab_tuile[y][x][z] != null){
							file.writeBoolean(true);
							file.writeUTF(this.tab_tuile[y][x][z].getNomTilset());
							file.writeInt(this.tab_tuile[y][x][z].getX());
							file.writeInt(this.tab_tuile[y][x][z].getY());
							file.writeInt(this.tab_tuile[y][x][z].getImage().getHeight());
							file.writeInt(this.tab_tuile[y][x][z].getImage().getWidth());
							//file.writeInt(this.tab_tuile[y][x][z].getNiveaux());
							file.writeBoolean(this.tab_tuile[y][x][z].isSolide());
						} else
							file.writeBoolean(false);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setTile(int x, int y,Tuile t){
		this.tab_tuile[y][x][t.getNiveaux()] = t;
	}

	public Tuile getTile(int y, int x,int z){
		return this.tab_tuile[y][x][z];
	}

	public void setTileNull(int x, int y, int z){
		this.tab_tuile[y][x][z] = null;
	}

}

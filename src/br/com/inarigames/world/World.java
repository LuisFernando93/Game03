package br.com.inarigames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.com.inarigames.entities.Entity;
import br.com.inarigames.entities.Player;
import br.com.inarigames.main.Game;

public class World {
	
	private static Tile[][] tiles;
	private static int WIDTH, HEIGHT;
	public final static int TILE_SIZE = 32;
	
	public static Tile[][] getTiles() {
		return World.tiles;
	}
	
	public void createWorld(String path) {
		try {
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH][HEIGHT];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, WIDTH);
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					tiles[i][j] = new FloorTile(i*TILE_SIZE, j*TILE_SIZE, Tile.TILE_FLOOR);
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao carregar mundo");
			e.printStackTrace();
		}
	}
	
	public static void newWorld(String world) {
		Game.entities = new ArrayList<Entity>();
		Game.player = new Player(176, 128, 32, 32, Game.spritesheet.getSprite(0, 0, 32, 32));
		Game.entities.add(Game.player);
		return;
	}
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height-1) / TILE_SIZE;
		
		int x4 = (xnext+width-1) / TILE_SIZE;
		int y4 = (ynext+height-1) / TILE_SIZE;
		
		boolean isFree = !(tiles[x1][y1] instanceof WallTile || 
						tiles[x2][y2] instanceof WallTile || 
						tiles[x3][y3] instanceof WallTile || 
						tiles[x4][y4] instanceof WallTile);
		
		return isFree;
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		boolean isFree = !(tiles[x1][y1] instanceof WallTile || 
						tiles[x2][y2] instanceof WallTile || 
						tiles[x3][y3] instanceof WallTile || 
						tiles[x4][y4] instanceof WallTile);
		
		return isFree;
	}
	
	public void render(Graphics graphics) {
		int xstart = Camera.getX()/TILE_SIZE;
		int ystart = Camera.getY()/TILE_SIZE;
		
		int xfinal = xstart + (Game.WIDTH/TILE_SIZE);
		int yfinal = ystart + (Game.HEIGHT/TILE_SIZE);
		
		for (int i = xstart; i <= xfinal; i++) {
			for(int j = ystart; j <= yfinal; j++) {
				if(i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT)
					continue;
				Tile tile = tiles[i][j];
				tile.render(graphics);
			}
		}
	}
}

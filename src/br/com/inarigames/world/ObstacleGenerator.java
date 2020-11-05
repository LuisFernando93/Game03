package br.com.inarigames.world;

import br.com.inarigames.entities.Obstacle;
import br.com.inarigames.main.Game;

public class ObstacleGenerator {
	
	public int time = 0;
	public int targetTime = 32;
	
	public void update() {
		time++;
		if (time == targetTime) {
			int altura1 = Game.random.nextInt(100 - 40) + 40;
			Obstacle obstaculo1 = new Obstacle(Game.WIDTH, 0, 32, altura1, null);
			
			int altura2 = Game.random.nextInt(100 - 40) + 40;
			Obstacle obstaculo2 = new Obstacle(Game.WIDTH, Game.HEIGHT - altura2, 32, altura2, null);
			
			Game.entities.add(obstaculo1);
			Game.entities.add(obstaculo2);
			time = 0;
		}
	}
}

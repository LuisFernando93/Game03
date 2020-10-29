package br.com.inarigames.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import br.com.inarigames.main.Game;

public class UI {
	
	
	public void render(Graphics graphics) {
		
		graphics.setColor(Color.white);
		graphics.setFont(new Font("arial", Font.BOLD, 18));
		String score = "Score: " + Game.getScore();
		graphics.drawString(score, 30, 30);
		
	}
}

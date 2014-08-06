package com.webs.faragames.fallen.entity.mob;

import org.lwjgl.input.Keyboard;

import com.webs.faragames.fallen.entity.Entity;
import com.webs.faragames.fallen.graphics.Texture;
import com.webs.faragames.fallen.settings.GeneralSettings;

public class Player extends Entity {

	public Player() {
		this.width = 64;
		this.height = 64;
		this.isLightCollidable(true);
		this.texture = Texture.Player;
	}

	/*
	 * TODO: REMOVE SHADER RECOMPILE
	 */
	@Override
	public void update() {
		float xa = 0;
		float ya = 0;
		float speed = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) speed = getSpeed(true);
		else speed = getSpeed(false);

		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) ya -= speed;
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) ya += speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) xa -= speed;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) xa += speed;

		getSide(xa, ya);

		this.x += xa;
		this.y += ya;

		this.world.setOffset((int) ((this.x + (SIZE / 2)) - GeneralSettings.WIDTH / 2), (int) (this.y + (SIZE / 2) - GeneralSettings.HEIGHT / 2));
		shade.recompile();
	}

	public void tick() {
	}

}
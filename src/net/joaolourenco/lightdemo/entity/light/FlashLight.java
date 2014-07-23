package net.joaolourenco.lightdemo.entity.light;

import org.lwjgl.util.vector.Vector2f;

public class FlashLight extends Light {

	public FlashLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
	}

	public void tick() {
	}

}
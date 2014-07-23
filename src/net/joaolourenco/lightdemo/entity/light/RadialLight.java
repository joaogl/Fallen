package net.joaolourenco.lightdemo.entity.light;

import org.lwjgl.util.vector.Vector2f;

public class RadialLight extends Light {

	public RadialLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
	}

	public RadialLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
	}

	public void tick() {
	}

}
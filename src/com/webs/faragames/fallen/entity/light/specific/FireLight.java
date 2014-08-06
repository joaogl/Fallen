package com.webs.faragames.fallen.entity.light.specific;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.webs.faragames.fallen.entity.light.PointLight;

public class FireLight extends PointLight {

	int time = 0;

	public FireLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.hasLightSpot = 0;
	}

	public void update() {
		super.update();
		time++;
		if (time % (Integer) generateRandom(10, 50, 0) == 0) this.intensity = (Float) generateRandom(2f, 2.5f, 1);
		this.green = (Float) generateRandom(0f, 0.4f, 1);
	}

	public Object generateRandom(float min, float max, int type) {
		if (type == 0) {
			Random rand = new Random();
			int out = rand.nextInt((int) max);
			while (out > max || out < min)
				out = rand.nextInt((int) max);
			return out;
		} else if (type == 1) {
			Random rand = new Random();
			double out = min + (max - min) * rand.nextDouble();
			while (out > max || out < min)
				out = min + (max - min) * rand.nextDouble();
			return (float) out;
		}
		return 0f;
	}

	public void tick() {
	}

}
package com.webs.faragames.fallen.entity.light.specific;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.webs.faragames.fallen.entity.light.PointLight;

/**
 * Class for the Fire Light.
 * 
 * @author FARA Games
 *
 */
public class FireLight extends PointLight {

	/**
	 * Animation time.
	 */
	int time = 0;

	/**
	 * Constructor for the Fire Light.
	 * 
	 * @param location
	 *            : where is the fire light.
	 * @param red
	 *            : red color value for the light.
	 * @param green
	 *            : green color value for the light.
	 * @param blue
	 *            : blue color value for the light.
	 */
	public FireLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.hasLightSpot = 0;
	}

	/**
	 * Method for update called 60 times per second by the World Class.
	 */
	public void update() {
		// Calling the super method.
		super.update();
		// Adding time.
		time++;
		// Generating random value to animate the fire. 
		if (time % (Integer) generateRandom(10, 50, 0) == 0) this.intensity = (Float) generateRandom(2f, 2.5f, 1);
		this.green = (Float) generateRandom(0f, 0.4f, 1);
	}

	/**
	 * Method for tick called once per second by the World Class.
	 */
	public void tick() {
	}

	/**
	 * Method to generate a random value.
	 * 
	 * @param min
	 *            : from
	 * @param max
	 *            : to
	 * @param type
	 *            : 0 for Integers, 1 for Floats
	 * @return Object, if type is 0 will return integer, if its 1 will return float.
	 */
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
}
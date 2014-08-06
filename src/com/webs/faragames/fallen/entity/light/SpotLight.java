package com.webs.faragames.fallen.entity.light;

import org.lwjgl.util.vector.Vector2f;

/**
 * Class for the Spot Light.
 * 
 * @author FARA Games
 *
 */
public class SpotLight extends Light {

	/**
	 * Constructor to create the Light.
	 * 
	 * @param location
	 *            : Vector2f with the light location.
	 * @param red
	 *            : float with the red value for the light color.
	 * @param green
	 *            : float with the green value for the light color.
	 * @param blue
	 *            : float with the blue value for the light color.
	 */
	public SpotLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.type = 2;
		this.size = 10;
		this.facing = 10;
	}

	/**
	 * Constructor to create the Light.
	 * 
	 * @param location
	 *            : Vector2f with the light location.
	 * @param red
	 *            : float with the red value for the light color.
	 * @param green
	 *            : float with the green value for the light color.
	 * @param blue
	 *            : float with the blue value for the light color.
	 * @param inte
	 *            : float with the light intensity.
	 */
	public SpotLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
		this.type = 2;
		this.size = 10;
		this.facing = 10;
	}

	/**
	 * Method called once per second by the World Class.
	 */
	public void tick() {
	}

}
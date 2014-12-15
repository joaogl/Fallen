package net.joaolourenco.fallen.entity.light;

import net.joaolourenco.fallen.utils.Vector2f;

/**
 * Class for the Point Light.
 * 
 * @author Joao Lourenco
 *
 */
public class PointLight extends Light {

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
	public PointLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.type = 1;
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
	public PointLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
		this.type = 1;
	}

	/**
	 * Method called once per second by the World Class.
	 */
	public void tick() {
	}

}
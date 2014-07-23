package net.joaolourenco.lightdemo.world.tile;

import java.util.Random;

import net.joaolourenco.lightdemo.entity.light.FireLight;
import net.joaolourenco.lightdemo.graphics.Texture;
import net.joaolourenco.lightdemo.world.World;

import org.lwjgl.util.vector.Vector2f;

public class FireTile extends Tile {

	int time = 0, texture = 0;
	protected World w;
	protected int lightID;

	public FireTile(int size, int tex, World w) {
		super(size, tex);
		this.w = w;
		this.lightCollidable = false;

		Vector2f location = new Vector2f(0f, 0f);
		FireLight l = new FireLight(location, 1f, 0f, 0f);
		l.init(w);
		this.lightID = w.entities.size();
		w.entities.add(l);
	}

	public void update() {
		super.update();
		if (this.x != 9999999) {
			w.entities.get(this.lightID).setX(this.x + (this.width / 2));
			w.entities.get(this.lightID).setY(this.y + (this.height / 2) + 20);
		}
		time++;
		if (time % (Integer) generateRandom(6, 7, 0) == 0) {
			if (texture > 3) texture = 0;
			else texture++;
		}
		this.tex = Texture.Fire[texture];
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

}
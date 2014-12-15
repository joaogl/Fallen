package net.joaolourenco.fallen.graphics.font;

import net.joaolourenco.fallen.settings.GeneralSettings;

public class AnimatedText {

	private Font font;
	private String text, finalText;
	private int x, y, size;
	private long last = 0;
	private boolean finished = false, remove = false;

	public AnimatedText(String text, int x, int y, int size) {
		this.font = new Font();
		this.text = "";
		this.finalText = text;
		this.x = x;
		this.y = y;
		this.size = size;
		this.last = System.currentTimeMillis();
		GeneralSettings.animatedText.add(this);
	}

	public void render() {
		font.drawString(this.text, this.x, this.y, this.size, 5);
	}

	public void update() {
		long current = System.currentTimeMillis();
		if (current - this.last >= 200 && !this.finished) {
			this.last += 200;
			addLetter();
		} else if (this.finished && current - this.last >= (300 * this.text.length())) this.remove = true;
	}

	public void addLetter() {
		if (this.text.length() < this.finalText.length()) this.text += this.finalText.charAt(this.text.length());
		else this.finished = true;
	}

	public boolean isRemoved() {
		return this.remove;
	}
}
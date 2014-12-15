/*
 * Copyright 2014 Joao Lourenco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.joaolourenco.fallen.graphics.font;

import net.joaolourenco.fallen.graphics.Shader;
import net.joaolourenco.fallen.graphics.Texture;
import net.joaolourenco.fallen.settings.GeneralSettings;
import net.joaolourenco.fallen.utils.Buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Font {

	private int[] texIDs;
	private int size = 128;
	private Shader shader;
	private String chars = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"abcdefghijklm" + //
			"nopqrstuvwxyz" + //
			"0123456789?!." + //
			"-,_% #$&'[]*+" + //
			":;<=>/^´`";

	protected int vao, vbo, vio, vto;

	protected float[] vertices = new float[] { 0.0f, 0.0f, 0.0f, //
			size, 0.0f, 0.0f, //
			size, size, 0.0f, //
			0.0f, size, 0.0f //
	};

	protected byte[] indices = new byte[] { 0, 1, 2, //
			2, 3, 0 //
	};

	protected byte[] texCoords = new byte[] { 0, 0, //
			1, 0, //
			1, 1, //
			1, 1, //
			0, 1, //
			0, 0 //
	};

	public Font() {
		GeneralSettings.font.add(this);
		texIDs = Texture.loadFont("res/font.png", 13, 7, size);
		compile();
		shader = new Shader("res/shaders/font.frag", "res/shaders/font.vert");

		glActiveTexture(GL_TEXTURE3);
		shader.bind();
		int uniform = glGetUniformLocation(shader.getShader(), "texture");
		glUniform1i(uniform, 3);

		glActiveTexture(GL_TEXTURE4);
		uniform = glGetUniformLocation(shader.getShader(), "texture2");
		glUniform1i(uniform, 3);
		shader.release();
	}

	protected void compile() {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		{
			vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			{
				glBufferData(GL_ARRAY_BUFFER, Buffer.createFloatBuffer(vertices), GL_STATIC_DRAW);
				glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			}
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			vio = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vio);
			{
				glBufferData(GL_ARRAY_BUFFER, Buffer.createByteBuffer(indices), GL_STATIC_DRAW);
			}
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			glDeleteBuffers(vto);
			vto = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vto);
			{
				glBufferData(GL_ARRAY_BUFFER, Buffer.createByteBuffer(texCoords), GL_STATIC_DRAW);
				glVertexAttribPointer(1, 3, GL_UNSIGNED_BYTE, false, 0, 1);
			}
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
		glBindVertexArray(0);
	}

	public void drawString(String text, int x, int y, int size, int spacing) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glActiveTexture(GL_TEXTURE3);
		shader.bind();
		float scale = size / 20.0f;
		int xx = x;
		int yy = y;
		for (int i = 0; i < text.length(); i++) {
			float xOffset = xx / scale;
			float yOffset = yy / scale;
			int currentChar = text.charAt(i);
			int index = chars.indexOf(currentChar);
			if (index >= 0 && currentChar != ' ') {
				if (currentChar == 'p' || currentChar == 'g' || currentChar == 'j' || currentChar == 'q' || currentChar == 'y' || currentChar == ',') yOffset += 40;
				glPushMatrix();
				glLoadIdentity();
				glScalef(scale, scale, 0);
				glTranslatef(xOffset, yOffset, 0);

				glActiveTexture(GL_TEXTURE3);
				glBindTexture(GL_TEXTURE_2D, texIDs[index]);

				glBindVertexArray(vao);
				glEnableVertexAttribArray(0);
				glEnableVertexAttribArray(1);
				{
					glTranslatef(x, y, 0);
					glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vio);
					glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
					glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
				}
				glEnableVertexAttribArray(1);
				glEnableVertexAttribArray(0);
				glBindVertexArray(0);

				glBindTexture(GL_TEXTURE_2D, 0);
				glPopMatrix();
			}
			xx += (this.size + spacing) * scale;
		}
		shader.release();
		glActiveTexture(GL_TEXTURE0);
		glDisable(GL_BLEND);
	}

	public void drawString(String text, int x, int y, int size, int spacing, int texture) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glActiveTexture(GL_TEXTURE3);
		shader.bind();
		float scale = size / 20.0f;
		int xx = x;
		int yy = y;
		for (int i = 0; i < text.length(); i++) {
			float xOffset = xx / scale;
			float yOffset = yy / scale;
			int currentChar = text.charAt(i);
			int index = chars.indexOf(currentChar);
			if (index >= 0 && currentChar != ' ') {
				if (currentChar == 'p' || currentChar == 'g' || currentChar == 'j' || currentChar == 'q' || currentChar == 'y' || currentChar == ',') yOffset += 40;
				glPushMatrix();
				glLoadIdentity();
				glScalef(scale, scale, 0);
				glTranslatef(xOffset, yOffset, 0);

				glActiveTexture(GL_TEXTURE4);
				glBindTexture(GL_TEXTURE_2D, texture);

				glActiveTexture(GL_TEXTURE3);
				glBindTexture(GL_TEXTURE_2D, texIDs[index]);

				glBindVertexArray(vao);
				glEnableVertexAttribArray(0);
				glEnableVertexAttribArray(1);
				{
					glTranslatef(x, y, 0);
					glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vio);
					glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
					glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
				}
				glEnableVertexAttribArray(1);
				glEnableVertexAttribArray(0);
				glBindVertexArray(0);

				glBindTexture(GL_TEXTURE_2D, 0);
				glPopMatrix();
			}
			xx += (this.size + spacing) * scale;
		}
		shader.release();
		glActiveTexture(GL_TEXTURE0);
		glDisable(GL_BLEND);
	}

	public void cleanup() {
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
		glDeleteBuffers(vio);
		glDeleteBuffers(vto);
	}

}
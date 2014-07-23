package net.joaolourenco.lightdemo.graphics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int shader;
	private String fragmentPath;
	private String vertexPath;

	public Shader(String fragPath) {
		String frag = loadAsString(fragPath);
		this.fragmentPath = fragPath;
		create(frag);
	}

	public Shader(String fragPath, String vertPath) {
		String frag = loadAsString(fragPath);
		String vert = loadAsString(vertPath);
		this.fragmentPath = fragPath;
		this.vertexPath = vertPath;
		create(frag, vert);
	}

	private String loadAsString(String path) {
		StringBuilder fragmentShaderSource = new StringBuilder();

		try {
			String line;
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while ((line = reader.readLine()) != null) {
				fragmentShaderSource.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fragmentShaderSource.toString();
	}

	private void create(String frag) {
		this.shader = glCreateProgram();
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, frag);
		glCompileShader(fragmentShader);
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Fragment shader not compiled!");
		}
		glAttachShader(this.shader, fragmentShader);
		glLinkProgram(this.shader);
		glValidateProgram(this.shader);
	}

	private void create(String frag, String vert) {
		this.shader = glCreateProgram();
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(fragmentShader, frag);
		glShaderSource(vertexShader, vert);
		glCompileShader(fragmentShader);
		glCompileShader(vertexShader);
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Fragment shader not compiled!");
		}
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Vertex shader not compiled!");
		}
		glAttachShader(this.shader, fragmentShader);
		glAttachShader(this.shader, vertexShader);
		glLinkProgram(this.shader);
		glValidateProgram(this.shader);
	}

	public void recompile() {
		String frag = loadAsString(this.fragmentPath);
		if (this.vertexPath != null) {
			String vert = loadAsString(this.vertexPath);
			create(frag, vert);
		} else create(frag);
	}

	public void bind() {
		glUseProgram(this.shader);
	}

	public void release() {
		glUseProgram(0);
	}

	public int getShade() {
		return this.shader;
	}

	public void cleanUp() {
		glDeleteProgram(this.shader);
	}

	public String getFragPath() {
		return this.fragmentPath;
	}

}
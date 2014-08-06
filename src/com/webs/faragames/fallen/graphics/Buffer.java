package com.webs.faragames.fallen.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Buffer {

	/**
	 * Method to move a normal float array to a FloatBuffer.
	 * @param array : float[] with data.
	 * @return FloatBuffer
	 */
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = BufferUtils.createFloatBuffer(array.length);
		result.put(array);
		result.flip();
		return result;
	}

	/**
	 * Method to move a normal byte array to a ByteBuffer.
	 * @param array : byte[] with data.
	 * @return ByteBuffer
	 */
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = BufferUtils.createByteBuffer(array.length);
		result.put(array);
		result.flip();
		return result;
	}


	/**
	 * Method to move a normal int array to a result.
	 * @param array : int[] with data.
	 * @return result
	 */
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = BufferUtils.createIntBuffer(array.length);
		result.put(array);
		result.flip();
		return result;
	}
}

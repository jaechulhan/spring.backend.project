/**
 * 
 */
package com.prolancer.core.common.utils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author jaechulhan
 *
 */
public class StringUtils {

	public static final String encoding = "UTF-8";

	public static String compress(String str) throws Exception {
		return compress(str.getBytes(encoding));
	}

	public static String compress(byte[] bytes) throws Exception {
		Deflater deflater = new Deflater();
		deflater.setInput(bytes);
		deflater.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			bos.write(buffer, 0, count);
		}
		bos.close();
		byte[] output = bos.toByteArray();
		return encodeBase64(output);
	}

	public static String decompress(byte[] bytes) throws Exception {
		Inflater inflater = new Inflater();
		inflater.setInput(bytes);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			bos.write(buffer, 0, count);
		}
		bos.close();
		byte[] output = bos.toByteArray();
		return new String(output);
	}

	public static String encodeBase64(byte[] bytes) throws Exception {
		return Base64.getEncoder().encodeToString(bytes).replace("\r\n", "").replace("\n", "");
	}

	public static byte[] decodeBase64(String str) throws Exception {
		return Base64.getDecoder().decode(str);
	}

}

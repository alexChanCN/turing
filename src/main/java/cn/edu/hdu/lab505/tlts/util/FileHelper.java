package cn.edu.hdu.lab505.tlts.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class FileHelper {
	private static final Logger LOGGER = Logger.getLogger(FileHelper.class);

	public static final String UPLOAD_FILE_PATH = "upload";

	public static void write(InputStream is, String path, String name) {
		StringBuilder fileNameSb = new StringBuilder(path);
		if (!path.endsWith(File.separator)) {
			fileNameSb.append(File.separator);
		}
		fileNameSb.append(name);
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(fileNameSb.toString());
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream outputStream = new FileOutputStream(file);
			int length = 0;
			byte[] buff = new byte[256];
			while (-1 != (length = is.read(buff))) {
				outputStream.write(buff, 0, length);
			}
			is.close();
			outputStream.close();
		} catch (IOException e) {
			LOGGER.error("file save failed." + e);
		}
	}

	public static String buildFileName(String fileName, String fileFullName) {
		return fileName + System.currentTimeMillis()
				+ fileFullName.substring(fileFullName.indexOf("."), fileFullName.length());
	}

	// 文件上传后的web路径
	public static String getWebUploadUrl(ServletContext context) {

		return context.getContextPath() + File.separator + UPLOAD_FILE_PATH;
	}

	// 文件上传的真实路径
	public static String getUploadPath(ServletContext context) {
		return context.getRealPath("/") + File.separator + UPLOAD_FILE_PATH;
	}
}

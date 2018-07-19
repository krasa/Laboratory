package krasa.laboratory.commons;

import java.io.*;

public class FileUtils {
	public static File newFile(String path, String content) {
		try {
			File file = new File(path);
			// file.createNewFile();
			org.apache.commons.io.FileUtils.write(file, content);
			return file;
		} catch (IOException e) {
			throw new RuntimeException();
		}

	}

	public static boolean contentEquals(File file1, File file2) throws IOException {
		boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) {
			return false;
		} else if (!file1Exists) {
			return true;
		} else if (!file1.isDirectory() && !file2.isDirectory()) {
			InputStream input1 = null;
			FileInputStream input2 = null;

			boolean var5;
			try {
				input1 = new FileInputStream(file1);
				input2 = new FileInputStream(file2);
				var5 = contentEquals(input1, input2);
			} finally {
				close(input1);
				close(input2);
			}

			return var5;
		} else {
			return false;
		}
	}

	public static void close(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException var2) {
			}
		}
	}

	public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
		InputStream bufferedInput1 = new BufferedInputStream(input1);
		InputStream bufferedInput2 = new BufferedInputStream(input2);

		int ch2;
		for (int ch = bufferedInput1.read(); -1 != ch; ch = bufferedInput1.read()) {
			ch2 = bufferedInput2.read();
			if (ch != ch2) {
				return false;
			}
		}

		ch2 = bufferedInput2.read();
		if (-1 != ch2) {
			return false;
		} else {
			return true;
		}
	}
}

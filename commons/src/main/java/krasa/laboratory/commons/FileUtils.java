package krasa.laboratory.commons;

import java.io.File;
import java.io.IOException;

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
}

package krasa.laboratory.utils.cleanup;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class ClearDirs {

	public static void main(String[] args) throws IOException {
		String first = "D:\\foo";
		File parent = new File(first);
		URI uri = parent.toURI();
		Files.walk(Paths.get(first)).forEach(new Consumer<Path>() {
			@Override
			public void accept(Path path) {
				File file = path.toFile();
				if (file.getName().endsWith("txt") || file.getName().endsWith("jpg")) {
					System.err.println("deleting " + file.getAbsolutePath());
					file.delete();
				}
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					if (files.length == 0) {
						System.err.println("deleting " + file.getAbsolutePath());
						file.delete();
					}
				}
			}
		});

	}

}

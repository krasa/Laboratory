package krasa.laboratory.utils.cleanup;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FindDuplicatesByName {
	public static final String DUPLICATE = "__duplicate";
	public static final String BAR = "bar";

	public static void main(String[] args) throws IOException {
		Multimap<String, File> map = HashMultimap.create();

		String first = "D:\\foo";
		File parent = new File(first);
		URI uri = parent.toURI();
		Files.walk(Paths.get(first)).forEach(new Consumer<Path>() {
			@Override
			public void accept(Path path) {
				File file = path.toFile();
				if (file.getName().endsWith("txt")) {
					return;
				}
				if (file.getAbsolutePath().contains(DUPLICATE)) {
					return;
				}
				if (file.isDirectory()) {
					return;
				}
				long length = file.length();

				map.put(file.getName(), file);

			}
		});

		for (Map.Entry<String, Collection<File>> entry : map.asMap().entrySet()) {
			List<File> list = new ArrayList<>(entry.getValue());

			for (int i = 0; i < list.size(); i++) {
				for (int j = i + 1; j < list.size(); j++) {

					File file1 = list.get(i);
					File file2 = list.get(j);

					String path1 = uri.relativize(file1.toURI()).getPath();
					String path2 = uri.relativize(file2.toURI()).getPath();

					if (file1.exists() && file2.exists()) {

//						System.err.println(file1.getAbsolutePath() + " \n= " + file2.getAbsolutePath());

						if (file1.length() < file2.length() && file2.getAbsolutePath().contains(BAR)) {
							System.err.println(file1.getAbsolutePath() + " \n< " + file2.getAbsolutePath());
							String s = path1.contains("/") ? StringUtils.substringBeforeLast(path1, "/") : "";
							org.apache.commons.io.FileUtils.moveFileToDirectory(file1, new File(parent, DUPLICATE + "/" + s), true);
						} else if (file1.length() > file2.length() && file1.getAbsolutePath().contains(BAR)) {
							System.err.println(file1.getAbsolutePath() + " \n> " + file2.getAbsolutePath());
							String s = path2.contains("/") ? StringUtils.substringBeforeLast(path2, "/") : "";
							org.apache.commons.io.FileUtils.moveFileToDirectory(file2, new File(parent, DUPLICATE + "/" + s), true);
						}
					}
				}


			}

		}
	}
}
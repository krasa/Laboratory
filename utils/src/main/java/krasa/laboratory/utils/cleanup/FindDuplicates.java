package krasa.laboratory.utils.cleanup;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import krasa.laboratory.commons.FileUtils;

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

public class FindDuplicates {
	public static final String DUPLICATE = "__duplicate";

	public static void main(String[] args) throws IOException {
		Multimap<Long, File> map = HashMultimap.create();

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

				map.put(length, file);

			}
		});

		for (Map.Entry<Long, Collection<File>> entry : map.asMap().entrySet()) {
			List<File> list = new ArrayList<>(entry.getValue());

			for (int i = 0; i < list.size(); i++) {
				for (int j = i + 1; j < list.size(); j++) {

					File file1 = list.get(i);
					File file2 = list.get(j);
					if (file1.exists() && file2.exists()) {
						boolean b = FileUtils.contentEquals(file1, file2);

						String path = uri.relativize(file1.toURI()).getPath();
						String path1 = uri.relativize(file2.toURI()).getPath();
						if (b) {
							System.err.println(file1.getAbsolutePath() + " \n= " + file2.getAbsolutePath());

//							if (!path.contains("/")) {
//								org.apache.commons.io.FileUtils.moveFileToDirectory(file1, new File(parent, DUPLICATE), true);
//							} else {
//								String s = path1.contains("/") ? StringUtils.substringBeforeLast(path1, "/") : "";
//								org.apache.commons.io.FileUtils.moveFileToDirectory(file2, new File(parent, DUPLICATE + "/" + s), true);
//							}
						} else {
							System.out.println(file1 + " \n\t!= " + path1);
						}
					}
				}


			}

		}
	}
}
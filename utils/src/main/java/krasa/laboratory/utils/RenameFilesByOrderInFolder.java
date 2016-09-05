package krasa.laboratory.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenameFilesByOrderInFolder {
	public static void main(String[] args) {
		File dir = new File("path");

		for (File subDir : getSubdirs(dir)) {
			int i = 0;
			for (File file : geFiles(subDir)) {
				String name = file.getName();
				String[] split = name.split("\\.");
				if (split.length > 3) {
					String newName = split[1] + "." + i + "." + split[split.length - 1];
					System.out.println("Renaming from " + file.getName() + " to " + newName);
					file.renameTo(new File(file.getParent(), newName));
				}
				i++;
			}
		}
	}

	private static File[] geFiles(File file) {
		return file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return !f.isDirectory();
			}
		});
	}

	private static List<File> getSubdirs(File file) {
		List<File> subdirs = Arrays.asList(file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory();
			}
		}));
		subdirs = new ArrayList<File>(subdirs);

		List<File> deepSubdirs = new ArrayList<File>();
		for (File subdir : subdirs) {
			deepSubdirs.addAll(getSubdirs(subdir));
		}
		subdirs.addAll(deepSubdirs);
		return subdirs;
	}
}

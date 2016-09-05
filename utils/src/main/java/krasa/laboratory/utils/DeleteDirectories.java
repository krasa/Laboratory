package krasa.laboratory.utils;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteDirectories {

	public static void main(String[] args) throws IOException {
		Path startingDir = new File("xxx").toPath();
		Files.walkFileTree(startingDir, new RecursiveDeleter());
	}

	public static class RecursiveDeleter extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.delete(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir,
				IOException exc) throws IOException {
			Files.delete(dir);
			return CONTINUE;
		}

	}

}

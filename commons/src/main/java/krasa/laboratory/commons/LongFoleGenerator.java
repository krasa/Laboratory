package krasa.laboratory.commons;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;

/**
 * @author Vojtech Krasa
 */
public class LongFoleGenerator {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("longFile.txt");
//		file.delete();
//		file.createNewFile();
        PrintWriter s = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        for (int i = 0; i < 10; i++) {
            new RuntimeException().printStackTrace(s);
            Thread.sleep(1000);
            String data = i + " " + RandomStringUtils.randomAlphabetic(100) + "\n";
            org.apache.commons.io.FileUtils.write(file, data, true);
        }
        s.close();
//		for (int i = 0; i < 1000; i++) {
//			Thread.sleep(1000);
//			String data = i + " " + RandomStringUtils.randomAlphabetic(100) + "\n";
//			org.apache.commons.io.FileUtils.write(file, data, true);
//		}

    }
}

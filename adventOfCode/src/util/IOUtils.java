package util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class IOUtils {

	public static final String PATH_TO_INPUT = "C:/workspace/adventOfCode/input/";
	
	private IOUtils() {}
	
	public static Integer[] readIntegerInputToArray(String inputFile) {
		
		Path file = Paths.get(inputFile);
		
		ArrayList<Integer> list = new ArrayList<>(); 
		
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextInt()) {
				int token = scanner.nextInt();
				list.add(token);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Integer [] input = new Integer[list.size()];
		input = list.toArray(input);
		
		return input;
	}
	
	public static int[] convertIntegerArrayToIntArray(Integer[] array) {
		
		int[] intArray = new int[array.length];
		
		for (int i=0; i<array.length; i++) {
			intArray[i] = array[i];
		}
		
		return intArray;
	}
	
	/**
	 * To fully understand this method, read javadoc for the methods 
	 * {@link Files#notExists(Path, java.nio.file.LinkOption...)}
	 * and {@link Files#exists(Path, java.nio.file.LinkOption...)}.
	 */
	public static boolean fileNotExistsAndIsWritable(Path filePath) {
		// Determine whether the given path is valid and if the file is writable.
		if (!Files.notExists(filePath) && !Files.exists(filePath)) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		// Open the input folder.
		Desktop desktop = Desktop.getDesktop();
		File inputDir = new File(PATH_TO_INPUT);
		try {
			desktop.open(inputDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

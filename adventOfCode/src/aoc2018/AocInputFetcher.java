package aoc2018;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Fetches the input for a day from the input page for that day. The input is saved as 
 * a 
 */
public class AocInputFetcher {

	public final static String INPUT_FOLDER = "C:/workspace/adventOfCode/input";
	
	public final static String BASE_INPUT_URL = "https://adventofcode.com/";
	
	private int year;
	
	private boolean isTest;
	
	private String inputFileName;
	
	// Output
	/** The input that was retrieved for the given day as a list of Strings. */
	private List<String> inputLines;
	
	/** The location of the input file. */
	private String inputFile;
	
	public AocInputFetcher(int year) {
		this.year = year;
	}
	
	public AocInputFetcher(int year, boolean isTest) {
		this(year);
		this.isTest = isTest;
	}
	
	public boolean fetchInput(int day) {
		return fetchInput(day, null);
	}
	
	public boolean fetchInput(int day, String inputUrl) {
		// Determine the location and file name for the input file.
		if (inputFileName == null) {
			inputFileName = "inputDay" + day;
			if (isTest)
				inputFileName += "Test";
			inputFileName += ".txt";
		}
		inputFile = INPUT_FOLDER + File.separator + year + File.separator + inputFileName;
		// Check whether an input file has already been created for this day.
		Path inputPath = Paths.get(inputFile);		
		if (Files.notExists(inputPath)) {
			// File has not yet been created. Retrieve the input now.
			if (inputUrl == null) {
				inputUrl = BASE_INPUT_URL + year + "/day/" + day + "/input";
			}

			Connection connection = Jsoup.connect(inputUrl)
					.maxBodySize(1024 * 1024 * 5)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
							+ "(KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36")
					.referrer("https://www.google.nl")
					// Wait for 10 seconds for response.
					.timeout(10_000);

			try {
				
				Document document = connection.execute().parse();
				if (!readDocument(document, day)) {
					System.out.println("Failed to save the html document as text file!");
					return false;
				}				
			} catch (IOException ioex) {
				ioex.printStackTrace();
				return false;
			}
		}
		
		try {
			inputLines = Files.readAllLines(Paths.get(inputFile));
			inputFileName = null;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean readDocument(Document document, int day) {
		
		Element inputElement = document.selectFirst("pre");		
		String completeInput = inputElement.wholeText();
		
		try (FileWriter writer = new FileWriter(inputFile)) {
			writer.write(completeInput);
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return false;
		}
		
		return true;
	}

	public List<String> getInputLines() {
		return inputLines;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public static void main(String[] args) {
		// Open the input folder.
		Desktop desktop = Desktop.getDesktop();
		File inputDir = new File(INPUT_FOLDER);
		try {
			desktop.open(inputDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

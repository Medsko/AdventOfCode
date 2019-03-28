package adventOfCode.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import util.Util;

public class CorruptionChecksumCalculator {

	private FileReader reader = null;
	private BufferedReader bfReader = null;
	private boolean calculateChecksumPerCell;
	private boolean calculateSumOfEvenDividers;
	private int cellCounter = 0;
	private int rowCounter = 1;
	private int differenceOfCurrentRow = 0;
	
	public int calculateCheckSum() {
		
		return calculateCheckSum("C:/Users/Medsko/Documents/ExcelInputForAOC.txt");
	}
	
	/**
	 * Main method of this class: reads the given file per line and calculates the checksum of each row of integers,
	 * then returns the sum of all checksums.
	 * @param fileName the path to the file and the name of the file
	 * @return sumOfCheckSum the sum of all checksums of all rows in the file
	 */
	public int calculateCheckSum(String fileName) {
		int checksum = 0;
		
		try {
			
			reader = new FileReader(fileName);
			bfReader = new BufferedReader(reader);
			
			String currentLine;
			
			while ((currentLine = bfReader.readLine()) != null) {
				
				if (calculateChecksumPerCell) {
					
					checksum += calculateChecksumForLinePerCell(currentLine);
					
					System.out.println("Sum of all checksums for this line: " + checksum);
					
					cellCounter = 0;
					rowCounter++;
					
				} else {
					
					int[] numbersAsInts = convertLineToIntArray(currentLine);
					
					if (calculateSumOfEvenDividers) {
						
						checksum += calculateSumOfEvenDividers(numbersAsInts);
						
					} else {
						
						checksum += Util.calculateCheckSumOfRow(numbersAsInts);
					}
				}
			}
			
		} catch (IOException e) {
			System.out.println("calculateCheckSum() - file not found.");
		} finally {
			try {
				if (bfReader != null) {
					bfReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("calculateCheckSum() - failed to close the (buffered) reader");
				e.printStackTrace();
			}
		}
		
		return checksum;
	}
	
	private int calculateChecksumForLinePerCell(String line) {
		char[] numbersAsChars = (line + " ").toCharArray();
		char[] currentRow = null;
		int checksumForLine = 0;

		for (int index = 0; index < numbersAsChars.length; index++) {
			
			if (Character.isWhitespace(numbersAsChars[index])) {
				
				currentRow = Arrays.copyOfRange(numbersAsChars, 0, index);
				numbersAsChars = Arrays.copyOfRange(numbersAsChars, index + 1, numbersAsChars.length);
				
				if (currentRow.length > 1) {
					// The evaluated row is not a single whitespace, but an actual row of numbers. Calculate the checksum.
					differenceOfCurrentRow = Util.calculateCheckSumOfRow(currentRow);
					checksumForLine += differenceOfCurrentRow;
					cellCounter++;
					System.out.println("Doing great! Difference for cell: " + rowCounter + "x" + cellCounter + " is: " + differenceOfCurrentRow);
				}
				
				index = 0;
			}
		}
		
		return checksumForLine;
	}
	
	private int calculateSumOfEvenDividers(int[] numbersAsInts) {
		
		int divisionOfEvenDividers = 0;
		
		Arrays.sort(numbersAsInts);
		
		finder:
			for (int i=0; i<numbersAsInts.length; i++) {
				
				for (int ii=i+1; ii<numbersAsInts.length; ii++) {
					
					if (numbersAsInts[ii] % numbersAsInts[i] == 0) {
						
						divisionOfEvenDividers = numbersAsInts[ii] / numbersAsInts[i];
						break finder;
					}
				}	
			}

		return divisionOfEvenDividers;
	}
	
	/**
	 * Helper method that takes in a string, splits it around tabs and returns the result as an int array.
	 * Breaks if input does not match the pattern int-tab-int.
	 * @param line the current line of ints seperated by tabs
	 * @return numbersAsInts the converted string as an int array
	 */
	private int[] convertLineToIntArray(String line) {
		String[] numbersAsStrings = line.split("\t");
		int[] numbersAsInts = new int[numbersAsStrings.length];
		
		for (int i=0; i<numbersAsStrings.length; i++) {
			numbersAsInts[i] = Integer.parseInt(numbersAsStrings[i]);
		}
		
		return numbersAsInts;
	}
	
	public void setCalculateChecksumPerCell(boolean calculateChecksumPerCell) {
		this.calculateChecksumPerCell = calculateChecksumPerCell;
	}

	public void setCalculateSumOfEvenDividers(boolean calculateSumOfEvenDividers) {
		this.calculateSumOfEvenDividers = calculateSumOfEvenDividers;
	}
}

package adventOfCode.day11;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import util.IOUtils;

public class HexEdClass {

	private HexPosition childProcess;

	public HexEdClass() {
		childProcess = new HexPosition();
	}
	
	public HexEdClass(boolean isChallengeB) {
		childProcess = new HexPosition(isChallengeB);
	}
	
	public void retraceChild() {
		
		Path inputFile = Paths.get(IOUtils.PATH_TO_INPUT + "inputDay11.txt");
		
		try (Scanner scanner = new Scanner(inputFile).useDelimiter(",")) {
			
			String instruction;
			
			while(scanner.hasNext()) {
				instruction = scanner.next();
				childProcess.move(instruction);
			}

		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	public int getDistanceToStart() {
		return childProcess.distanceFromStart();
	}
}

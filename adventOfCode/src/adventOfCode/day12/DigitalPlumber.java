package adventOfCode.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventOfCode.InputFileReader;
import util.IOUtils;
import util.Util;

public class DigitalPlumber {
	
	private List<Pipe> pipes;
	
	private Set<Integer> connectedPipeIds;
	
	private Set<Pipe> currentPipeGroup;
	
	private List<Set<Pipe>> pipeGroups;

	public DigitalPlumber() {
		connectedPipeIds = new HashSet<>();
		pipeGroups = new ArrayList<>();
	}
	
	public void processPipes() {
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay12.txt";
		PipeInputConverter converter = new PipeInputConverter();
		InputFileReader inputReader = new InputFileReader(InputFileReader.Day.TWELVE);
		inputReader.setInputConverter(converter);
		inputReader.readInput(inputFile);
		
		pipes = converter.getConvertedInput();
		
		for (int pipeId=0; pipeId<pipes.size(); pipeId++) {
			if (connectedPipeIds.contains(pipeId))
				continue;
			currentPipeGroup = new HashSet<>();
			Pipe pipe = pipes.get(pipeId);
			connectPipe(pipe);
			pipeGroups.add(currentPipeGroup);
		}
	}
	
	private void connectPipe(Pipe connectedPipe) {
		connectedPipeIds.add(connectedPipe.getId());
		currentPipeGroup.add(connectedPipe);
		
		System.out.println("Now connecting pipe: " + connectedPipe.getId());
		System.out.println("Pipe is connected to: " 
				+ Arrays.toString(connectedPipe.getConnectedPipes()));
		
		for (int pipeId : connectedPipe.getConnectedPipes()) {
			// Skip pipes that were already processed.
			if (connectedPipeIds.contains(pipeId)) {
				continue;
			}
			Pipe subConnectedPipe = pipes.get(pipeId);
			connectPipe(subConnectedPipe);
		}
	}
	
	public void printGroups() {
		int groupId = 0;
		for (Set<Pipe> group : pipeGroups) {
			
			System.out.println("Group " + groupId + " contains pipes: ");
			
			for (Pipe pipe : group)
				System.out.println(pipe);
			
			Util.underline(100);
			groupId++;
		}
	}
	
	public void printGroupsSuccint(){
		int groupId = 0;
		for (Set<Pipe> group : pipeGroups) {
			
			String output = "Group " + groupId + " contains pipes: ";
			
			for (Pipe pipe : group) {
				output += pipe + ", ";
			}
			output = output.substring(0, output.lastIndexOf(","));
			System.out.println(output);
			Util.underline(100);
			groupId++;
		}
	}
	
	public int getNrOfGroups() {
		return pipeGroups.size();
	}
	
	public int getNrOfPipesConnectedToZero() {
		return connectedPipeIds.size();
	}
}

package adventOfCode.day12;

import java.util.ArrayList;
import java.util.List;

import adventOfCode.InputConverter;

public class PipeInputConverter implements InputConverter<Pipe> {

	private List<Pipe> inputList;
	
	public PipeInputConverter() {
		inputList = new ArrayList<Pipe>();
	}
	
	@Override
	public boolean convertLine(String line) {
		// Get everything left of the first white space.
		String idString = line.substring(0, line.indexOf(" "));
		int id = Integer.parseInt(idString);
		
		// Get everything right of the '<->' divider
		String pipesString = line.substring(line.indexOf("> ") + 2);
		
		String[] pipesStringArray = pipesString.split(",");
		int[] pipesIntArray = new int[pipesStringArray.length];
		
		for (int i=0; i<pipesStringArray.length; i++)
			pipesIntArray[i] = Integer.parseInt(pipesStringArray[i].trim());
		
		Pipe pipe = new Pipe();
		pipe.setId(id);
		pipe.setConnectedPipes(pipesIntArray);
		
		inputList.add(pipe);
		
		return true;
	}

	@Override
	public List<Pipe> getConvertedInput() {
		return inputList;
	}

}

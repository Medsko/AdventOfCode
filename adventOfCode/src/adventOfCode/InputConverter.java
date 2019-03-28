package adventOfCode;

import java.util.Collection;

public interface InputConverter<T> {

	boolean convertLine(String line);
	
	public Collection<T> getConvertedInput();
}

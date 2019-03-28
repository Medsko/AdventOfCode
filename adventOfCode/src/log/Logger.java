package log;

import java.io.IOException;

public interface Logger {

	void initialize() throws IOException;
	
	void log(Object message);

	void deInitialize();
}

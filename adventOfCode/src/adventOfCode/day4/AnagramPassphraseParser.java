package adventOfCode.day4;

import java.nio.file.Path;
import java.util.ArrayList;

public class AnagramPassphraseParser extends PassphraseParser {

	private ArrayList<AnagramString> passphraseWords;
	
	public AnagramPassphraseParser(Path inputFile) {
		super(inputFile);
	}
	
	protected boolean isValidPassphrase(String line) {
		
		passphraseWords = new ArrayList<>();
		
		return super.isValidPassphrase(line);		
	}
	
	
	protected boolean isAnagramOfPassphraseWord(String currentWord) {
		
		AnagramString nextWord = new AnagramString(currentWord);
		
		for (AnagramString passphraseWord : passphraseWords) {
			
			if (passphraseWord.equals(nextWord)) {
				// Anagram found. No need to update the list, as this passphrase has been found wanting, 
				// so we will now move on to the next line.
				return true;
			}
		}
		
		passphraseWords.add(nextWord);
		
		return false;
	}
	

}

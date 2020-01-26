package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
	
	public List<List<String>> getTextFileComponents(String filename) throws IOException {
		List<List<String>> components = new ArrayList<>();
		List<String> textFile = readTextFile(filename);
		int index = 0;
		for (String s : textFile) {
			if (s.equals("")) {
				index++;
			} else {
				List<String> ary;
				if (components.size() <= index) {
					ary = new ArrayList<>();
					components.add(ary);
				} else {
					ary = components.get(index);
				}
				ary.add(s);
				components.set(index, ary);
			}
		}
		return components;
	}
	
	public List<String> readTextFile(String filename) throws IOException {
		BufferedReader buffy = new BufferedReader(new FileReader(filename));
		List<String> linesToRead = new ArrayList<>();
		String line = buffy.readLine();
		while (line != null) {
			linesToRead.add(line);
			line = buffy.readLine();
		}
		buffy.close();
		return linesToRead;
	}

}

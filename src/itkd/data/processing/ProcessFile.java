package itkd.data.processing;

import itkd.data.structures.main.Tweet;
import itkd.data.structures.main.Tweets;
import itkd.userinterface.data.structures.UserInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class ProcessFile {

	public final static Tweets tweets = new Tweets();

	private final String path;
	private final long upperMemoryBound = Runtime.getRuntime().freeMemory();

	public ProcessFile (String path) {
		this.path = path;
		processInteligentInput(path);

	}

	public void processInteligentInput (String path) {

		FileInputStream fstream;
		try {
			fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			long currentMemory = 0;
			int count = 0;
			while ((strLine = br.readLine()) != null
					&& currentMemory < 0.1 * upperMemoryBound) {
				int sizeofString = strLine.length() * 2;
				long memoString = sizeofString * 8;
				currentMemory += memoString;
				count++;
				insertDataInMemory(strLine, count);
			}

			br.close();
			UserInterface.console.print("done reading " + tweets.getList().size()
					+ " objects and " + currentMemory + " memory used.\n");
			UserInterface.console.print("done. \n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertDataInMemory (String strLine, int count) {
		String[] coordinates = strLine.split(",");
		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i].trim();
		}
		Tweet t = new Tweet(Integer.toString(count),
				new BigDecimal(coordinates[0]), new BigDecimal(coordinates[1]));
		tweets.addTweet(t);
	}
}

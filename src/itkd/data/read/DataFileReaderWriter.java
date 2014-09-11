package itkd.data.read;

import itkd.data.processing.ProcessFile;
import itkd.data.structures.main.Tweet;
import itkd.data.structures.main.Tweets;
import itkd.userinterface.data.structures.UserInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;

public class DataFileReaderWriter {
	/** Percent to use in memory when dealing with the large data */
	private final double procentToUseMemory = 0.5;

	/** Upper bound memory that is ready to use */
	private final long upperMemoryBound = Runtime.getRuntime().freeMemory();

	public void loadData (String path, Tweets tweets) {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			long currentMemory = 0;
			int count = 0;
			while ((strLine = br.readLine()) != null
					&& currentMemory < procentToUseMemory * upperMemoryBound) {
				int sizeofString = strLine.length() * 2;
				long memoString = sizeofString * 8;
				currentMemory += memoString;
				count++;
				insertDataInMemory(strLine, count, tweets);
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

	private void insertDataInMemory (String strLine, int count, Tweets tweets) {
		String[] coordinates = strLine.split(",");
		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i].trim();
		}
		Tweet t = new Tweet(Integer.toString(count),
				new BigDecimal(coordinates[0]), new BigDecimal(coordinates[1]));
		tweets.addTweet(t);
	}

	public void insetIntoFile (String fileName, String filePath) {
		File file = new File(filePath + "" + fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (int i = 0; i < ProcessFile.getTweets().getList().size(); i++) {
				Tweet t = ProcessFile.getTweets().getList().get(i);
				bw.write(t.getId() + "," + t.get1Dimension() + "," + t.get2Dimension()
						+ "," + t.getCluster());
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

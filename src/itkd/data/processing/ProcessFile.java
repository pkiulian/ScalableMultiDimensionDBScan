package itkd.data.processing;

import itkd.algorithms.clustering.dbscan.epsilonanalysis.DensityCluster;
import itkd.data.read.DataFileReaderWriter;
import itkd.data.structures.main.Tweets;

import java.util.HashSet;
import java.util.Set;

public class ProcessFile {

	/** For now these are the tweets to be clustered */
	private static Tweets tweets = new Tweets();

	private final String path;

	public ProcessFile (String path) {
		setTweets(new Tweets());
		this.path = path;

	}

	public void readInputBasedOnMemorySize () {
		DataFileReaderWriter f = new DataFileReaderWriter();
		f.loadData(path, ProcessFile.getTweets());
	}

	public static Tweets getTweets () {
		return tweets;
	}

	public static void setTweets (Tweets tweets) {
		ProcessFile.tweets = tweets;
	}

	public void insertInFile (String filePath) {
		DataFileReaderWriter f = new DataFileReaderWriter();
		f.insetIntoFile("DBScan" + System.currentTimeMillis() + ".csv", filePath);
	}

	public static Set<Integer> getSetOfCluster () {
		Set<Integer> ClusterIdSet = new HashSet<Integer>();
		for (int i = 0; i < tweets.getList().size(); i++) {
			if (tweets.getList().get(i).getCluster() != DensityCluster.NOISE) {
				ClusterIdSet.add(tweets.getList().get(i).getCluster());
			}
		}
		return ClusterIdSet;
	}

	public static int getNrOfObjectsWithGiveClusterId (int intValue) {
		int result = 0;
		for (int i = 0; i < tweets.getList().size(); i++) {
			if (intValue == tweets.getList().get(i).getCluster())
				result++;
		}
		return result;
	}
}

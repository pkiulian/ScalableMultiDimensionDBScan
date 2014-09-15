package itkd.data.processing;

import itkd.algorithms.clustering.dbscan.epsilonanalysis.DensityCluster;
import itkd.data.read.DataFileReaderWriter;
import itkd.data.structures.main.Tweet;
import itkd.data.structures.main.Tweets;

import java.util.HashSet;
import java.util.Set;

public class WorkingSet {

	/** For now these are the tweets to be clustered */
	private final Tweets tweets = new Tweets();

	private final String path;

	public WorkingSet (String path) {
		setWorkingSetTweets(new Tweets());
		this.path = path;

	}

	public void readInputBasedOnMemorySize () {
		DataFileReaderWriter f = new DataFileReaderWriter();
		f.loadData(path, this.tweets);
	}

	public Tweets getWorkingSetTweets () {
		return this.tweets;
	}

	public void setWorkingSetTweets (Tweets tweets) {
		for (Tweet t : tweets.getList()) {
			tweets.getList().add(t);
		}
	}

	public void insertInFile (String filePath) {
		DataFileReaderWriter f = new DataFileReaderWriter();
		f.insetIntoFile("DBScan" + System.currentTimeMillis() + ".csv", filePath,
				this.tweets);
	}

	public Set<Integer> getSetOfCluster () {
		Set<Integer> ClusterIdSet = new HashSet<Integer>();
		for (int i = 0; i < this.tweets.getList().size(); i++) {
			if (this.tweets.getList().get(i).getCluster() != DensityCluster.NOISE) {
				ClusterIdSet.add(this.tweets.getList().get(i).getCluster());
			}
		}
		return ClusterIdSet;
	}

	public int getNrOfObjectsWithGiveClusterId (int intValue) {
		int result = 0;
		for (int i = 0; i < getWorkingSetTweets().getList().size(); i++) {
			if (intValue == getWorkingSetTweets().getList().get(i).getCluster())
				result++;
		}
		return result;
	}

}

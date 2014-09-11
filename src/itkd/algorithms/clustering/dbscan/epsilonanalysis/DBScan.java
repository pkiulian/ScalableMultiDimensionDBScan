package itkd.algorithms.clustering.dbscan.epsilonanalysis;

import itkd.data.processing.ProcessFile;
import itkd.data.structures.main.Tweet;

import java.util.ArrayList;

public class DBScan extends DensityCluster {

	public static int clusterID = 0;

	public DBScan (double parseDouble, int parseInt) {
		super(parseDouble, parseInt);
		clusterID = 0;
	}

	public void clusterData () {
		ProcessFile.getTweets().prepareTheSpatialIndex(epsilonSpace);
		for (int i = 0; i < ProcessFile.getTweets().getList().size(); i++) {
			Tweet t = ProcessFile.getTweets().getList().get(i);
			if (t.getCluster() == UNCLASSIFIED) {
				if (expandCluster(t) == true) {
					clusterID++;
				}
			}
		}
	}

	private boolean expandCluster (Tweet dataObjectp) {
		ArrayList<Tweet> seedListp = ProcessFile.getTweets().regionQuery(
				dataObjectp, epsilonSpace);
		if (seedListp.size() < getMinPoints()) {
			dataObjectp.setCluster(NOISE);
			return false;
		}
		dataObjectp.setCore(true); // CORE
		for (int i = 0; i < seedListp.size(); i++) {
			Tweet seedListDataObjectp = seedListp.get(i);
			seedListDataObjectp.setCluster(clusterID);
			if (seedListDataObjectp.equals(dataObjectp)) {
				seedListp.remove(i);
				i--;
			}
		}
		for (int j = 0; j < seedListp.size(); j++) {
			Tweet seedListDataObjectp = seedListp.get(j);
			ArrayList<Tweet> seedListDataObject_Neighbourhoodp = ProcessFile
					.getTweets().regionQuery(seedListDataObjectp, epsilonSpace);
			if (seedListDataObject_Neighbourhoodp.size() >= getMinPoints()) {
				seedListDataObjectp.setCore(true); // CORE
				for (int i = 0; i < seedListDataObject_Neighbourhoodp.size(); i++) {
					Tweet p = seedListDataObject_Neighbourhoodp.get(i);
					if (p.getCluster() == NOISE || p.getCluster() == UNCLASSIFIED) {
						if (p.getCluster() == UNCLASSIFIED) {
							seedListp.add(p);
						}
						ProcessFile.getTweets().changeClusterId(p, clusterID);
					}
				}
			}
			seedListp.remove(j);
			j--;
		}
		return true;
	}
}

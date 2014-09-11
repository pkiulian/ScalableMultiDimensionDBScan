package itkd.algorithms.clustering.dbscan.epsilonanalysis;

import itkd.data.structures.main.Tweets;
import itkd.userinterface.data.structures.UserInterface;
import itkd.userinterface.main.Application;

import java.util.ArrayList;
import java.util.List;

public class EpsilonEsitimation {

	/** the list of distances */
	public List<Integer> NeighboursForAtleastAtMostNeighbours (Tweets tweets,
			int minpts, int[] intervals) {

		List<Integer> numberOfPointsperInterval = getnumberOfPoints(tweets, minpts,
				intervals);

		return numberOfPointsperInterval;
	}

	private List<Integer> getnumberOfPoints (Tweets tweets, int minpts,
			int[] intervals) {
		List<Integer> result = new ArrayList<Integer>();
		UserInterface.console.print("\n");

		for (int i = 0; i < intervals.length - 1; i++) {
			int distanceStart = intervals[i];
			int distanceEnd = intervals[i + 1];
			result.add(getNumberOfNeighboursConstrained(tweets, distanceStart,
					distanceEnd, minpts));
			System.out.println("for " + i
					+ " interval number of tweets with at least " + minpts
					+ "neighbours are " + result.get(i));
			final int percent = ((i + 1) * 100) / (intervals.length - 1);

			Thread t = new Thread(new Runnable() {
				@Override
				public void run () {
					Application.updateBar(percent);
				}

			});
			t.start();
		}
		System.out.println("done");
		UserInterface.console.println("done");
		return result;
	}

	private Integer getNumberOfNeighboursConstrained (Tweets tweets,
			int distanceStart, int distanceEnd, int minpts) {
		Integer result = new Integer(0);
		tweets.prepareTheSpatialIndex(distanceEnd);
		for (int i = 0; i < tweets.getList().size(); i++) {
			Tweets firstSet = new Tweets();
			firstSet.getList().addAll(
					tweets.regionQuery(tweets.getList().get(i), distanceStart));
			Tweets secondSet = new Tweets();
			secondSet.getList().addAll(
					tweets.regionQuery(tweets.getList().get(i), distanceEnd));
			if (firstSet.size() < minpts && secondSet.size() >= minpts) {
				result++;
			}
		}
		return result;
	}
}

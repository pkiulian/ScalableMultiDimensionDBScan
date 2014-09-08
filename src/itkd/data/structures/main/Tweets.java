package itkd.data.structures.main;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.index.strtree.STRtree;

public class Tweets {

	private STRtree index = null;
	List<Tweet> list = new ArrayList<Tweet>();

	public void addTweet (Tweet t) {
		list.add(t);
	}

	public List<Tweet> getList () {
		return list;
	}

	public void prepareTheSpatialIndex (double epsilonS) {
		index = new STRtree(50);
		for (int i = 0; i < this.list.size(); i++) {
			double rad = GeoObject.distToAngle(epsilonS * 1.1);
			Envelope e = new Envelope(this.list.get(i).get1Dimension().doubleValue()
					- rad, this.list.get(i).get1Dimension().doubleValue() + rad,
					this.list.get(i).get2Dimension().doubleValue() - rad, this.list
							.get(i).get2Dimension().doubleValue()
							+ rad);
			index.insert(e, this.list.get(i));
		}
	}

	public ArrayList<Tweet> regionQuery (Tweet t, double epsilonS) {
		ArrayList<Tweet> neighborPoints = new ArrayList<Tweet>();
		double rad = epsilonS;
		rad = GeoObject.distToAngle(epsilonS * 1.1);
		Envelope e = new Envelope(t.get1Dimension().doubleValue() - rad, t
				.get1Dimension().doubleValue() + rad, t.get2Dimension().doubleValue()
				- rad, t.get2Dimension().doubleValue() + rad);
		neighborPoints.addAll(index.query(e));
		return neighborPoints;
	}
}

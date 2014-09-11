package itkd.data.structures.main;

import itkd.algorithms.clustering.dbscan.epsilonanalysis.DensityCluster;
import itkd.data.structures.interfaces.Point2Dimension;

import java.math.BigDecimal;

public class Tweet implements Point2Dimension {
	private final String id;
	private final BigDecimal[] coordinates = new BigDecimal[2];
	private int cluster = DensityCluster.UNCLASSIFIED;
	private boolean core = false;

	public Tweet (String id, BigDecimal x, BigDecimal y) {
		this.id = id;
		coordinates[0] = x;
		coordinates[1] = y;
	}

	@Override
	public BigDecimal get1Dimension () {
		return coordinates[0];
	}

	@Override
	public BigDecimal get2Dimension () {
		return coordinates[1];
	}

	@Override
	public void set1Dimension (BigDecimal x) {
		coordinates[0] = x;

	}

	@Override
	public void set2Dimension (BigDecimal y) {
		coordinates[1] = y;

	}

	@Override
	public void setId (String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId () {
		return this.id;
	}

	public int getCluster () {
		return this.cluster;
	}

	public void setCluster (int cluster) {
		this.cluster = cluster;

	}

	public void setCore (boolean b) {
		this.core = true;

	}

}

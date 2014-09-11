package itkd.algorithms.clustering.dbscan.epsilonanalysis;

import java.io.IOException;

public abstract class DensityCluster {

	public double epsilonSpace;
	public int minPoints;
	public final static int NOISE = Integer.MIN_VALUE;
	public final static int UNCLASSIFIED = -1;

	public DensityCluster (double parseDouble, int parseInt) {
		this.epsilonSpace = parseDouble;
		this.minPoints = parseInt;
	}

	public void setEpsilonSpace (double epsilonSpace) throws IOException {
		this.epsilonSpace = epsilonSpace;
	}

	public double getEpsilonSpace () {
		return this.epsilonSpace;
	}

	public void setMinPoints (int minPoints) throws IOException {
		this.minPoints = minPoints;
	}

	public int getMinPoints () {
		return this.minPoints;
	}
}

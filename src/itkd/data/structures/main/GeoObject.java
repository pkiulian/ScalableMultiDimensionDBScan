package itkd.data.structures.main;

import weka.clusterers.forOPTICSAndDBScan.DataObjects.DataObject;
import weka.clusterers.forOPTICSAndDBScan.DataObjects.EuclideanDataObject;
import weka.clusterers.forOPTICSAndDBScan.Databases.Database;
import weka.core.Instance;

public class GeoObject extends EuclideanDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final long R_EARTH = 6371000;

	public GeoObject (Instance originalInstance, String key, Database database) {
		super(originalInstance, key, database);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main (String[] args) {
		// TODO Auto-generated method stub

		// calling distance

		System.out.println("final GeoObject");

	}

	/**
	 * Computes the metric distance between two points on the Earths specified
	 * by their geograogical coordinates (latitudes and longitudes).
	 * Note that longitude is X and latitude is Y!
	 * Returns the distance in meters.
	 */
	public static double geoDist (double lon1, double lat1, double lon2,
			double lat2) {
		double rlon1 = lon1 * Math.PI / 180, rlon2 = lon2 * Math.PI / 180, rlat1 = lat1
				* Math.PI / 180, rlat2 = lat2 * Math.PI / 180;
		double dlon = (rlon1 - rlon2) / 2, dlat = (rlat1 - rlat2) / 2, lat12 = (rlat1 + rlat2) / 2;
		double sindlat = Math.sin(dlat), sindlon = Math.sin(dlon);
		double cosdlon = Math.cos(dlon), coslat12 = Math.cos(lat12),

		f = sindlat * sindlat * cosdlon * cosdlon + sindlon * sindlon * coslat12
				* coslat12;
		f = Math.sqrt(f);
		f = Math.asin(f) * 2;// the angle between the points
		f *= R_EARTH;
		// System.out.println("distance_="+(double)f);
		return f;

	}

	@Override
	public double distance (DataObject dobject) {
		// System.out.println("in distance");
		double x1, y1, x2, y2;
		x1 = this.getInstance().valueSparse(0);
		y1 = this.getInstance().valueSparse(1);
		x2 = dobject.getInstance().valueSparse(0);
		y2 = dobject.getInstance().valueSparse(1);
		return geoDist(x1, y1, x2, y2);
	}

	/**
	 * Computes the angular distance, in degrees, for the given metric distance
	 * on the Earth surface, in meters.
	 */
	public static double distToAngle (double dist) {
		return dist * 180 / Math.PI / R_EARTH;
	}
}

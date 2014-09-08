package itkd.data.structures.interfaces;

import java.math.BigDecimal;

public interface Point2Dimension {

	public void setId (String id);

	public String getId ();

	public void set1Dimension (BigDecimal x);

	public void set2Dimension (BigDecimal y);

	public BigDecimal get1Dimension ();

	public BigDecimal get2Dimension ();

}

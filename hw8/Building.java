/**
 * 
 */
package hw8;

/**
 * @author Haoming Liu
 *
 */
public class Building implements Comparable<Building> {
	
	public static final int PRIME = 31; // this is the prime number used to calculate the hash code
	
	private String shortName; 
	private String longName;
	private double xCor;
	private double yCor;
	
	// AF:
	// The shortName stores the abbreviated name of the building. Empty if it is no a building
	// The longName stores the complete name of the building. Empty if it is not a building
	// The xCor stores the x coordinates on the picture
	// The yCor stores the y coordinates on the picture
	
	// RI:
	// The short name and the long name cannot be null.
	
	/**
	 * This function constructs an object of building
	 * @param shortName short name of the building
	 * @param longName long name of the building
	 * @param xCor x coordinate on the picture
	 * @param yCor y coordinate on the picture
	 */
	public Building (String shortName, String longName, double xCor, double yCor) {
		this.shortName = shortName;
		this.longName = longName;
		this.xCor = xCor;
		this.yCor = yCor;
		checkRep();
	}

	private void checkRep() {
		assert(this.shortName != null);
		assert(this.longName != null);
	}

	@Override
	/** 
	 * This function compares two buildings. This function gives the compare method to be used to
	 * sort the building. So it treats the point (not a building) as equal.
	 * @param other is the other building used to compare with this.
	 * @return negative numbers if it is smaller, positive numbers if it greater, 0 if they are equal. 
	 */
	public int compareTo(Building other) {
		return shortName.compareTo(other.shortName);
	}
	
	@Override
	/**
	 * return a string representation of the building.
	 */
	public String toString() {
		return this.shortName + ": " + this.longName;
	}
	
	@Override
	/**
	 * Two buildings are exactly equal if they have the same x coordinates and y coordinates.
	 * This is different from the compare method.
	 * @param the other object used to be determined
	 * @return true if they are equal, false otherwise
	 */
	public boolean equals(Object other) {
		if (other instanceof Building) {
			return this.xCor == ((Building) other).xCor && this.yCor == ((Building)other).yCor;
		} else {
			return false;
		}
	}
	
	@Override
	/**
	 * @return return the hash code for this building.
	 */
	public int hashCode() {
		return (int)(this.xCor * PRIME + this.yCor);
	}
	
	/**
	 * 
	 * @return the short name of the building
	 */
	public String getShortName() {
		return shortName;
	}
	
	/**
	 * 
	 * @return the complete name of the building
	 */
	public String getLongName() {
		return longName;
	}
	
	/**
	 * 
	 * @return the x coordinate of the building
	 */
	public double getXCor() {
		return xCor;
	}
	
	/**
	 * 
	 * @return the y coordinate of the building
	 */
	public double getYCor() {
		return yCor;
	}
}

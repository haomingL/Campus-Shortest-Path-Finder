/**
 * 
 */
package hw8;

/**
 * @author Haoming Liu
 * This class represents the position of the point on the pictures.
 */
public class Position {
	
	public static final int PRIME = 31; // This is the prime number used to compute the hash function
	
	public static final double ANGLE_FACTOR = 1.0 / 8; // The angle factor is used to find the correct direction
													   // of the angle.
	
	private double xCor;
	private double yCor;
	
	// AF
	// xCor represents the x coordinate of the position 
	// yCor represents the y coordinate of the position
	
	// RI
	// The points cannot be negative.
	
	/**
	 * This function initializes the Position object.
	 * @param xCor is the given x coordinate
	 * @param yCor is the given y coordinate
	 */
	public Position(double xCor, double yCor) {
		this.xCor = xCor;
		this.yCor = yCor;
		checkRep();
	}
	
	private void checkRep() {
		assert(xCor >= 0);
		assert(yCor >= 0);
	}

	/**
	 * @return the x coordinate of the position
	 */
	public double getXCor() {
		return xCor;
	}
	
	/**
	 * @return the y coordinate of the position
	 */
	public double getYCor() {
		return yCor;
	}
	
	@Override
	/**
	 * @param the object to be determined to be equal
	 * @return true if two objects has the same position, false otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof Position) {
			return this.xCor == ((Position)other).xCor && this.yCor == ((Position)other).yCor;
		} else {
			return false;
		}
	}
	
	@Override
	/**
	 * @return the hash code of this position object.
	 */
	public int hashCode() {
		return (int) (xCor) + (int)(yCor) * PRIME;
	}
	
	/**
	 * This function takes in the other position and determine the direction from this position to
	 * the other. If the point is on the boundary, it is considered as the single letter direction.
	 * @param other is the other position to decide the direction.
	 * @return the string representation of the direction.
	 */
	public String findDirection(Position other) {
		double angle = Math.atan2(this.yCor - other.yCor, other.xCor - this.xCor) / Math.PI;
		if (angle <= ANGLE_FACTOR && angle >= 0 - ANGLE_FACTOR) {
			return "E";
		} else if (angle <= ANGLE_FACTOR * 5 && angle >= ANGLE_FACTOR * 3) {
			return "N";
		} else if (angle <= 0 - ANGLE_FACTOR * 7 || angle >= ANGLE_FACTOR * 7) {
			return "W";
		} else if (angle <= 0 - ANGLE_FACTOR * 3 && angle >= 0 - ANGLE_FACTOR * 5) {
			return "S";
		} else if (angle < ANGLE_FACTOR * 3 && angle > ANGLE_FACTOR) {
			return "NE";
		} else if (angle < ANGLE_FACTOR * 7 && angle > ANGLE_FACTOR * 5) {
			return "NW";
		} else if (angle < 0 - ANGLE_FACTOR && angle > 0 - ANGLE_FACTOR * 3) {
			return "SE";
		} else {
			return "SW";
		}
	}
}

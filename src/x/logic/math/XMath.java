package x.logic.math;

public class XMath {
	
	public static int getValueInRange(int value, int min, int max) {
		return Math.max(Math.min(value, max), min);
	}

}

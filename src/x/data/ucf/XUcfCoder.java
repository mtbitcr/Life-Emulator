package x.data.ucf;

public class XUcfCoder {

	// LANDSCAPE - TYPE
	private final static long LANDSCAPE_TYPE_MASK      = 0x0000_0000_0000_0007L;
	private final static int LANDSCAPE_TYPE_SHIFT      = 0;
	public final static int LANDSCAPE_TYPE_EMPTY       = 0x0;
	public final static int LANDSCAPE_TYPE_WATER_LOW   = 0x1;
	public final static int LANDSCAPE_TYPE_WATER_HIGH  = 0x2;
	public final static int LANDSCAPE_TYPE_GROUND_LOW  = 0x3;
	public final static int LANDSCAPE_TYPE_GROUND_HIGH = 0x4;
	public final static int LANDSCAPE_TYPE_GRASS_LOW   = 0x5;
	public final static int LANDSCAPE_TYPE_GRASS_HIGH  = 0x6;
	// HUMAN - TYPE
	private final static long HUMAN_TYPE_MASK          = 0x0000_0000_0000_0018L;
	private final static int HUMAN_TYPE_SHIFT          = 3;
	public final static int HUMAN_TYPE_EMPTY           = 0x0;
	public final static int HUMAN_TYPE_MAN             = 0x1;
	public final static int HUMAN_TYPE_WOMAN           = 0x2;
	// HUMAN - AGE
	private final static long HUMAN_AGE_MASK           = 0x0000_0000_000F_FFE0L;
	private final static int HUMAN_AGE_SHIFT           = 5;
	// HUMAN - ENERGY
	private final static long HUMAN_ENERGY_MASK        = 0x0000_0000_03F0_0000L;
	private final static int HUMAN_ENERGY_SHIFT        = 20;	
	// HUMAN - SATIETY
	private final static long HUMAN_SATIETY_MASK       = 0x0000_0000_FC00_0000L;
	private final static int HUMAN_SATIETY_SHIFT       = 26;	
	// HUMAN - PREGNANCY
	private final static long HUMAN_PREGNANCY_MASK     = 0x0000_01FF_0000_0000L;
	private final static int HUMAN_PREGNANCY_SHIFT     = 32;	
	// PLANT - TYPE
	private final static long PLANT_TYPE_MASK          = 0x0000_0200_0000_0000L;
	private final static int PLANT_TYPE_SHIFT          = 41;
	public final static int PLANT_TYPE_EMPTY           = 0x0;
	public final static int PLANT_TYPE_APPLE           = 0x1;
	// PLANT - FRUITS
	private final static long PLANT_FRUITS_MASK        = 0x0000_FC00_0000_0000L;
	private final static int PLANT_FRUITS_SHIFT        = 42;
	// ACTIVE FLAGS
	private final static long ACTIVE_FLAG_HUMAN_MASK   = 0x4000_0000_0000_0000L;
	private final static int ACTIVE_FLAG_HUMAN_SHIFT   = 62;
	private final static long ACTIVE_FLAG_PLANT_MASK   = 0x8000_0000_0000_0000L;
	private final static int ACTIVE_FLAG_PLANT_SHIFT   = 63;
	
	public final static long encodeLandscapeType(long uc, int u) {
		return encode(uc, u, LANDSCAPE_TYPE_MASK, LANDSCAPE_TYPE_SHIFT);
	}
	
	public final static int decodeLandscapeType(long uc) {
		return decode(uc, LANDSCAPE_TYPE_MASK, LANDSCAPE_TYPE_SHIFT);
	}
	
	public final static long encodeHumanType(long uc, int u) {
		return encode(uc, u, HUMAN_TYPE_MASK, HUMAN_TYPE_SHIFT);
	}
	
	public final static int decodeHumanType(long uc) {
		return decode(uc, HUMAN_TYPE_MASK, HUMAN_TYPE_SHIFT);
	}
	
	public final static long encodeHumanAge(long uc, int u) {
		return encode(uc, u, HUMAN_AGE_MASK, HUMAN_AGE_SHIFT);
	}
	
	public final static int decodeHumanAge(long uc) {
		return decode(uc, HUMAN_AGE_MASK, HUMAN_AGE_SHIFT);
	}
	
	public final static long encodeHumanEnergy(long uc, int u) {
		return encode(uc, u, HUMAN_ENERGY_MASK, HUMAN_ENERGY_SHIFT);
	}
	
	public final static int decodeHumanEnergy(long uc) {
		return decode(uc, HUMAN_ENERGY_MASK, HUMAN_ENERGY_SHIFT);
	}

	public final static long encodeHumanSatiety(long uc, int u) {
		return encode(uc, u, HUMAN_SATIETY_MASK, HUMAN_SATIETY_SHIFT);
	}
	
	public final static int decodeHumanSatiety(long uc) {
		return decode(uc, HUMAN_SATIETY_MASK, HUMAN_SATIETY_SHIFT);
	}
	
	public final static long encodeHumanPregnancy(long uc, int u) {
		return encode(uc, u, HUMAN_PREGNANCY_MASK, HUMAN_PREGNANCY_SHIFT);
	}
	
	public final static int decodeHumanPregnancy(long uc) {
		return decode(uc, HUMAN_PREGNANCY_MASK, HUMAN_PREGNANCY_SHIFT);
	}
	
	public final static long encodePlantType(long uc, int u) {
		return encode(uc, u, PLANT_TYPE_MASK, PLANT_TYPE_SHIFT);
	}
	
	public final static int decodePlantType(long uc) {
		return decode(uc, PLANT_TYPE_MASK, PLANT_TYPE_SHIFT);
	}
	
	public final static long encodePlantFruits(long uc, int u) {
		return encode(uc, u, PLANT_FRUITS_MASK, PLANT_FRUITS_SHIFT);
	}
	
	public final static int decodePlantFruits(long uc) {
		return decode(uc, PLANT_FRUITS_MASK, PLANT_FRUITS_SHIFT);
	}

	public final static long encodeActiveFlagHuman(long uc, int u) {
		return encode(uc, u, ACTIVE_FLAG_HUMAN_MASK, ACTIVE_FLAG_HUMAN_SHIFT);
	}
	
	public final static int decodeActiveFlagHuman(long uc) {
		return decode(uc, ACTIVE_FLAG_HUMAN_MASK, ACTIVE_FLAG_HUMAN_SHIFT);
	}

	public final static long encodeActiveFlagPlant(long uc, int u) {
		return encode(uc, u, ACTIVE_FLAG_PLANT_MASK, ACTIVE_FLAG_PLANT_SHIFT);
	}
	
	public final static int decodeActiveFlagPlant(long uc) {
		return decode(uc, ACTIVE_FLAG_PLANT_MASK, ACTIVE_FLAG_PLANT_SHIFT);
	}
	
	// Example:
	// ===================================================================================

	// 1. ~mask
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
	// ~
	// -----------------------------------------------------------------------------------
	// 1111 1111 1111 1111 1111 1111 1111 1111   1111 1111 1111 1111 1111 1111 111|0 0|111 

	// 2. (uc & ~mask)	
	// 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|1 0|000
	// &
	// 1111 1111 1111 1111 1111 1111 1111 1111   1111 1111 1111 1111 1111 1111 111|0 0|111
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 0|000

	// 3. (long) u
	// 0000 0000 0000 0000 0000 0000 0000 00|01|
	// (long)
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|
	
	// 4. (long) u << shift
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|
	// << 3
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000


	// 5. ((long) u << shift & mask) - additional protection
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
	// &
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
	
	// 6. (uc & ~mask) | ((long) u << shift & mask)
	// 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 0|000
	// |
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 1|000

	private final static long encode(long uc, int u, long mask, int shift) {
		return (uc & ~mask) | ((long) u << shift & mask);
	}
	
	// Example:
	// ===================================================================================

	// 1. (uc & mask)	
	// 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 1|000
	// &
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000

	// 2. ((uc & mask) >>> 3)
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
	// >>> 3
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|

	// 3. (int) ((uc & mask) >>> shift)
	// 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|
	// (int)
	// -----------------------------------------------------------------------------------
	// 0000 0000 0000 0000 0000 0000 0000 00|01|

	private final static int decode(long uc, long mask, int shift) {
		return (int) ((uc & mask) >>> shift);
	}
	
}

package x.gui.format;

public class XFormatter {
	
	public static String formatRaw(long raw) {		
		return String.format("%64s", Long.toBinaryString(raw))
				.replace(' ', '0')
				.replaceFirst("(.{61})", "$1 ")
				.replaceFirst("(.{59})", "$1 ")
				.replaceFirst("(.{44})", "$1 ")
				.replaceFirst("(.{38})", "$1 ")
				.replaceFirst("(.{32})", "$1 ")
				.replaceFirst("(.{23})", "$1 ")
				.replaceFirst("(.{22})", "$1 ")
				.replaceFirst("(.{16})", "$1 ")
				.replaceFirst("(.{2})", "$1 ")
				.replaceFirst("(.{1})", "$1 ");
	}
	
	public static String formatDate(int days) {		
		int months = days / 30;
		int years = months / 10;
		return String.format("%s years %s months %s days", years, months % 10, days % 30);
	}
	
	public static String formatDateShort(int days) {
		int months = days / 30;
		int years = months / 10;
		return String.format("%04d.%02d.%02d", years, months % 10, days % 30);
	}

}

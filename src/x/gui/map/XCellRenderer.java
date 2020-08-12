package x.gui.map;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import x.data.ucf.XUcfCoder;

@SuppressWarnings("serial")
public class XCellRenderer extends DefaultTableCellRenderer {

	private final static int COMMON_COLOR_EMPTY               = 0xF0F0F0;
	private final static int COMMON_COLOR_UNDEFINED           = 0xC22E35;
	private final static int LANDSCAPE_TYPE_COLOR_WATER_LOW   = 0x60A4B1;
	private final static int LANDSCAPE_TYPE_COLOR_WATER_HIGH  = 0x6CB8C6;
	private final static int LANDSCAPE_TYPE_COLOR_GROUND_LOW  = 0xDDB985;
	private final static int LANDSCAPE_TYPE_COLOR_GROUND_HIGH = 0xD1AF7D;
	private final static int LANDSCAPE_TYPE_COLOR_GRASS_LOW   = 0xB3D77E;
	private final static int LANDSCAPE_TYPE_COLOR_GRASS_HIGH  = 0xA8C976;
	private final static int HUMAN_TYPE_COLOR_MAN             = 0x1D8EA3;
	private final static int HUMAN_TYPE_COLOR_WOMAN           = 0xB82C8F;
	private final static int TREE_TYPE_COLOR_APPLE            = 0x62B122;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBackground(new Color(getBackgroundColor((long) value)));
		setForeground(new Color(getForegroundColor((long) value)));
		setValue(getUnit((long) value));
		return this;
	}

	private static int getBackgroundColor(long cellData) {
		int color = COMMON_COLOR_EMPTY;
		int landscapeType = XUcfCoder.decodeLandscapeType(cellData);
		switch (landscapeType) {
			case XUcfCoder.LANDSCAPE_TYPE_WATER_LOW: {
				color = LANDSCAPE_TYPE_COLOR_WATER_LOW;
				break;
			}
			case XUcfCoder.LANDSCAPE_TYPE_WATER_HIGH: {
				color = LANDSCAPE_TYPE_COLOR_WATER_HIGH;
				break;
			}
			case XUcfCoder.LANDSCAPE_TYPE_GROUND_LOW: {
				color = LANDSCAPE_TYPE_COLOR_GROUND_LOW;
				break;
			}
			case XUcfCoder.LANDSCAPE_TYPE_GROUND_HIGH: {
				color = LANDSCAPE_TYPE_COLOR_GROUND_HIGH;
				break;
			}
			case XUcfCoder.LANDSCAPE_TYPE_GRASS_LOW: {
				color = LANDSCAPE_TYPE_COLOR_GRASS_LOW;
				break;
			}
			case XUcfCoder.LANDSCAPE_TYPE_GRASS_HIGH: {
				color = LANDSCAPE_TYPE_COLOR_GRASS_HIGH;
				break;
			}
			default: {
				color = COMMON_COLOR_UNDEFINED;
				break;
			}
		}
		return color;
	}

	private static int getForegroundColor(long cellData) {
		int color = COMMON_COLOR_EMPTY;
		int humanType = XUcfCoder.decodeHumanType(cellData);
		int plantType = XUcfCoder.decodePlantType(cellData);
		if (humanType != XUcfCoder.HUMAN_TYPE_EMPTY) {
			switch (humanType) {
				case XUcfCoder.HUMAN_TYPE_MAN: {
					color = HUMAN_TYPE_COLOR_MAN;
					break;
				}
				case XUcfCoder.HUMAN_TYPE_WOMAN: {
					color = HUMAN_TYPE_COLOR_WOMAN;
					break;
				}
				default: {
					color = COMMON_COLOR_UNDEFINED;
					break;
				}
			}
		}
		else if (plantType != XUcfCoder.PLANT_TYPE_EMPTY) {
			switch (plantType) {
				case XUcfCoder.PLANT_TYPE_APPLE: {
					color = TREE_TYPE_COLOR_APPLE;
					break;
				}
				default: {
					color = COMMON_COLOR_UNDEFINED;
					break;
				}
			}
		}
		else {
			color = COMMON_COLOR_UNDEFINED;
		}
		return color;
	}

	private static char getUnit(long cellData) {
		char unit = ' ';
		int humanType = XUcfCoder.decodeHumanType(cellData);
		int plantType = XUcfCoder.decodePlantType(cellData);
		if (humanType != XUcfCoder.HUMAN_TYPE_EMPTY) {
			unit = 'E';
		}
		if (plantType != XUcfCoder.PLANT_TYPE_EMPTY) {
			unit = 'F';
		}
		return unit;
	}
}

package x.gui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import x.data.ucf.XUcfCoder;
import x.gui.main.XMainPanel;
import x.logic.math.XMath;

@SuppressWarnings("serial")
public class XMapPanel extends JTable {

	public final static int MAP_SIZE = 65;
	public final static int CELL_SIZE = 10;

	public XMapPanel() {
		setupView();
		setupCells();
		setupListeners();
		setVisible(true);
	}

	public void setLandscapeTypeAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeLandscapeType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 7)), y, x);
	}

	public int getLandscapeTypeAt(int y, int x) {
		return XUcfCoder.decodeLandscapeType(getRawDataAt(y, x));
	}

	public void setHumanTypeAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeHumanType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 3)), y, x);
	}

	public int getHumanTypeAt(int y, int x) {
		return XUcfCoder.decodeHumanType(getRawDataAt(y, x));
	}

	public void setHumanAgeAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeHumanAge(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 32767)), y, x);
	}

	public int getHumanAgeAt(int y, int x) {
		return XUcfCoder.decodeHumanAge(getRawDataAt(y, x));
	}

	public void setHumanEnergyAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeHumanEnergy(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
	}

	public int getHumanEnergyAt(int y, int x) {
		return XUcfCoder.decodeHumanEnergy(getRawDataAt(y, x));
	}

	public void setHumanSatietyAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeHumanSatiety(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
	}

	public int getHumanSatietyAt(int y, int x) {
		return XUcfCoder.decodeHumanSatiety(getRawDataAt(y, x));
	}

	public void setHumanPregnancyAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeHumanPregnancy(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 511)), y, x);
	}

	public int getHumanPregnancyAt(int y, int x) {
		return XUcfCoder.decodeHumanPregnancy(getRawDataAt(y, x));
	}

	public void setPlantTypeAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodePlantType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
	}

	public int getPlantTypeAt(int y, int x) {
		return XUcfCoder.decodePlantType(getRawDataAt(y, x));
	}

	public void setPlantFruitsAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodePlantFruits(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
	}

	public int getPlantFruitsAt(int y, int x) {
		return XUcfCoder.decodePlantFruits(getRawDataAt(y, x));
	}

	public void setActiveFlagHumanAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeActiveFlagHuman(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
	}

	public int getActiveFlagHumanAt(int y, int x) {
		return XUcfCoder.decodeActiveFlagHuman(getRawDataAt(y, x));
	}

	public void setActiveFlagPlantAt(int u, int y, int x) {
		setRawDataAt(XUcfCoder.encodeActiveFlagPlant(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
	}

	public int getActiveFlagPlantAt(int y, int x) {
		return XUcfCoder.decodeActiveFlagPlant(getRawDataAt(y, x));
	}

	public void setRawDataAt(long uc, int y, int x) {
		setValueAt(uc, y, x);
	}

	public long getRawDataAt(int y, int x) {
		return (long) getValueAt(y, x);
	}

	public void reset() {
		setupCells();
	}

	private void setupView() {
		setModel(new DefaultTableModel(MAP_SIZE, MAP_SIZE) {
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		setRowHeight(CELL_SIZE);
		for (int i = 0; i < MAP_SIZE; i++) {
			this.getColumnModel().getColumn(i).setMinWidth(CELL_SIZE);
			this.getColumnModel().getColumn(i).setMaxWidth(CELL_SIZE);
		}
		setBorder(BorderFactory.createLineBorder(new Color(0x333333)));
		setDefaultRenderer(Object.class, new XCellRenderer());
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\gui\\fonts\\map-units.ttf")).deriveFont(7f);
			setFont(customFont);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupCells() {
		long[][] data = new long[MAP_SIZE][MAP_SIZE];
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("src\\resources\\data\\test-data.dat");
			ois = new ObjectInputStream(fis);
			data = (long[][]) ois.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (int y = 0; y < MAP_SIZE; y++) {
			for (int x = 0; x < MAP_SIZE; x++) {
				setValueAt(data[y][x], y, x);
			}
		}
	}

	private void setupListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = rowAtPoint(e.getPoint());
				int y = columnAtPoint(e.getPoint());
				XMainPanel.cellInfoPanel.update(x, y);
			}
		});
	}

}

package x.gui.info.cell;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import x.data.ucf.XUcfCoder;
import x.gui.format.XFormatter;
import x.gui.main.XMainPanel;

@SuppressWarnings("serial")
public class XCellInfoPanel extends JPanel {
	
	private JLabel info;
	
	public XCellInfoPanel() {
		setupView();
		setupInfo();
		setVisible(true);
	}
	
	public void update(int y, int x) {
		long cellData = XMainPanel.mapPanel.getRawDataAt(y, x);		
		String info = String.format("<html>Landscape type: %s"
				+ "<br> "
				+ "<br> Human type: %s"
				+ "<br> Human age: %s"
				+ "<br> Human energy: %s"
				+ "<br> Human satiety: %s"
				+ "<br> Human pregnancy: %s"
				+ "<br> "
				+ "<br> Plant type: %s"
				+ "<br> Plant fruits: %s"
				+ "<br> "
				+ "<br> Active flag (Human): %s"
				+ "<br> Active flag (Plant): %s</html>",
				XUcfCoder.decodeLandscapeType(cellData),
				XUcfCoder.decodeHumanType(cellData),
				XFormatter.formatDate(XUcfCoder.decodeHumanAge(cellData)),
				XUcfCoder.decodeHumanEnergy(cellData),
				XUcfCoder.decodeHumanSatiety(cellData),
				XFormatter.formatDate(XUcfCoder.decodeHumanPregnancy(cellData)),
				XUcfCoder.decodePlantType(cellData),
				XUcfCoder.decodePlantFruits(cellData),
				XUcfCoder.decodeActiveFlagHuman(cellData),
				XUcfCoder.decodeActiveFlagPlant(cellData));
		this.info.setText(info);
		this.setToolTipText(XFormatter.formatRaw(cellData));
	}
	
	public void reset() {
		this.info.setText("-");
		this.setToolTipText(null);
	}
	
	private void setupView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder("Cell Info"));
		setPreferredSize(new Dimension(400, 0));
	}
	
	private void setupInfo() {
		this.info = new JLabel("-");
		add(this.info);
	}

}

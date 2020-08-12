package x.gui.info.map;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import x.gui.format.XFormatter;
import x.logic.statistic.XStatistic;

@SuppressWarnings("serial")
public class XMapInfoPanel extends JPanel {
	
	private JLabel info;
	
	public XMapInfoPanel() {
		setupView();
		setupInfo();
		setVisible(true);
	}
	
	public final void update(int days) {	
		String info = String.format("<html>Date: %s"
				+ "<br> "
				+ "<br> Common area: %s"
				+ "<br> &nbsp ┗ Water area: %s"
				+ "<br> &nbsp ┗ Land area: %s"
				+ "<br> People: %s"
				+ "<br> &nbsp ┗ Men: %s"
				+ "<br> &nbsp ┗ Women: %s"
				+ "<br> &nbsp&nbsp&nbsp&nbsp ┗ Pregnant: %s"
				+ "<br> Children were born: %s"
				+ "<br> Children died: %s"
				+ "<br> People died: %s"
				+ "<br> &nbsp ┗ Low Energy: %s"
				+ "<br> &nbsp ┗ Low Satiety: %s"
				+ "<br> &nbsp ┗ Age: %s"
				+ "<br> &nbsp ┗ Lost: %s"
				+ "<br> People Density (Land): %.2f"
				+ "<br> People Age Mean: %s"
				+ "<br> Plants: %s"
				+ "<br> Plants Fruits: %s"
				+ "<br> Plants Density (Land): %.2f"
				+ "<br> Plants Fruits Mean: %.2f"
				+ "<br> Plants Fruits / People Ratio: %.2f</html>",
				XFormatter.formatDateShort(days),
				XStatistic.CELLS,
				XStatistic.CELLS_WATER,
				XStatistic.CELLS_LAND,
				XStatistic.people,
				XStatistic.peopleMen,
				XStatistic.peopleWomen,
				XStatistic.peopleWomenPregnant,
				XStatistic.childrenWereBorn,
				XStatistic.childrenDied,
				XStatistic.peopleDied,
				XStatistic.peopleDiedByEnergy,
				XStatistic.peopleDiedBySatiety,
				XStatistic.peopleDiedByAge,
				XStatistic.peopleDiedByLost,
				XStatistic.getPeopleLandDensity(),
				XFormatter.formatDate((int) XStatistic.getPeopleAgeMean()),
				XStatistic.plants,
				XStatistic.plantsFruits,
				XStatistic.getPlantsLandDensity(),
				XStatistic.getPlantsFruitsMean(),
				XStatistic.getPlantsFruitsPeopleRatio());
		this.info.setText(info);
	}
	
	public void reset() {
		this.info.setText("-");
	}
	
	private void setupView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder("Map Info"));
		setPreferredSize(new Dimension(300, 0));
	}
	
	private void setupInfo() {
		this.info = new JLabel("-", SwingConstants.LEFT);
		add(this.info);
	}

}

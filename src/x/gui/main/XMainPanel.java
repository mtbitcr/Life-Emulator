package x.gui.main;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import x.gui.control.XControlPanel;
import x.gui.info.cell.XCellInfoPanel;
import x.gui.info.events.XEventsInfoPanel;
import x.gui.info.map.XMapInfoPanel;
import x.gui.map.XMapPanel;

@SuppressWarnings("serial")
public class XMainPanel extends JPanel {
	
	public static JPanel leftGroupPanel;
	public static JPanel centerGroupPanel;
	public static JPanel rightGroupPanel;
	public static XMapInfoPanel mapInfoPanel;
	public static XEventsInfoPanel eventsInfoPanel;
	public static XMapPanel mapPanel;
	public static XControlPanel controlPanel;
	public static XCellInfoPanel cellInfoPanel;
	
	public XMainPanel() {
		setupView();
		setupGroupPanels();
		setupMapInfoPanel();
		setupEventsInfoPanel();
		setupMapPanel();
		setupControlPanel();
		setupCellInfoPanel();
		setVisible(true);
	}
	
	private void setupView() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	private void setupGroupPanels() {
		leftGroupPanel = new JPanel();
		leftGroupPanel.setLayout(new BoxLayout(leftGroupPanel, BoxLayout.Y_AXIS));
		add(leftGroupPanel);
		centerGroupPanel = new JPanel();
		centerGroupPanel.setLayout(new BoxLayout(centerGroupPanel, BoxLayout.Y_AXIS));
		add(centerGroupPanel);
		rightGroupPanel = new JPanel();
		rightGroupPanel.setLayout(new BoxLayout(rightGroupPanel, BoxLayout.Y_AXIS));
		add(rightGroupPanel);
	}
	
	private void setupMapInfoPanel() {
		mapInfoPanel = new XMapInfoPanel();
		leftGroupPanel.add(mapInfoPanel);
	}
	
	private void setupEventsInfoPanel() {
		eventsInfoPanel = new XEventsInfoPanel();
		leftGroupPanel.add(eventsInfoPanel);
	}
	
	private void setupMapPanel() {
		centerGroupPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		mapPanel = new XMapPanel();
		centerGroupPanel.add(mapPanel);
	}
	
	private void setupControlPanel() {
		controlPanel = new XControlPanel();
		centerGroupPanel.add(controlPanel);
	}
	
	private void setupCellInfoPanel() {
		cellInfoPanel = new XCellInfoPanel();
		rightGroupPanel.add(cellInfoPanel);
	}	
	
}

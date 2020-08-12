package x.gui.control;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import x.gui.main.XMainPanel;
import x.logic.force.XForce;
import x.logic.statistic.XStatistic;

@SuppressWarnings("serial")
public class XControlPanel extends JPanel {
	
	private JButton startButton;
	private JButton pauseButton;
	private JButton stopButton;
	
	public XControlPanel() {
		setupView();
		setupButtons();
		setVisible(true);
	}
	
	private void setupView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void setupButtons() {
		// START
		this.startButton = new JButton();
		startButton.setIcon(new ImageIcon("src\\resources\\gui\\icons\\control-start.png"));
		startButton.setPreferredSize(new Dimension(25, 25));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XForce.start();
				startButton.setEnabled(false);
				pauseButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
		});
		add(startButton);
		// PAUSE
		this.pauseButton = new JButton();
		pauseButton.setIcon(new ImageIcon("src\\resources\\gui\\icons\\control-pause.png"));
		pauseButton.setPreferredSize(new Dimension(25, 25));
		pauseButton.setEnabled(false);
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XForce.pause();
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		});
		add(pauseButton);
		// STOP
		this.stopButton = new JButton();
		stopButton.setIcon(new ImageIcon("src\\resources\\gui\\icons\\control-stop.png"));
		stopButton.setPreferredSize(new Dimension(25, 25));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XForce.stop();
				XStatistic.reset();
				XMainPanel.mapPanel.reset();
				XMainPanel.mapInfoPanel.reset();
				XMainPanel.eventsInfoPanel.reset();
				XMainPanel.cellInfoPanel.reset();
			}
		});
		add(stopButton);
	}

}

package x.logic.force;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import x.data.ucf.XUcfCoder;
import x.gui.info.cell.XCellInfoPanel;
import x.gui.info.events.XEventsInfoPanel;
import x.gui.main.XMainPanel;
import x.gui.map.XMapPanel;
import x.logic.random.XRandom;
import x.logic.statistic.XStatistic;

public class XForce {
	
	private static int date = 1;
	private static Timer timer;
	private static int timerDelay = 100;
	
	public static void start() {
		if (timer == null) {
			timer = new Timer(timerDelay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					XMapPanel map = XMainPanel.mapPanel;
					for (int y = 0; y < XMapPanel.MAP_SIZE; y++) {
						for (int x = 0; x < XMapPanel.MAP_SIZE; x++) {
							map.setActiveFlagHumanAt(1, y, x);
							map.setActiveFlagPlantAt(1, y, x);
						}
					}
					for (int y = 0; y < XMapPanel.MAP_SIZE; y++) {
						for (int x = 0; x < XMapPanel.MAP_SIZE; x++) {
							act(map.getRawDataAt(y, x), y, x);
						}
					}
					map.repaint();					
					int y = map.getSelectedRow();
					int x = map.getSelectedColumn();
					XCellInfoPanel cellInfo = XMainPanel.cellInfoPanel;
					if (y != -1 && x != -1) {
						cellInfo.update(y, x);
					}
					else {
						cellInfo.reset();
					}
					XStatistic.update();
					XMainPanel.mapInfoPanel.update(++date);
				}
			});
		}
		timer.start();
	}
	
	public static void pause() {
		if (timer != null) {
			timer.stop();	
		}
	}
	
	public static void stop() {
		if (timer != null) {
			timer.stop();	
		}
		date = 1;
	}
	
	// ACT
	private static void act(long cellData, int y, int x) {
		if (XUcfCoder.decodeActiveFlagHuman(cellData) == 1 && XUcfCoder.decodeHumanType(cellData) != XUcfCoder.HUMAN_TYPE_EMPTY) {
			if (tryToDie(cellData, y, x) 
					|| tryToGiveBirth(cellData, y, x)
					|| tryToSleep(cellData, y, x) 
					|| tryToEat(cellData, y, x)
					|| tryToMakeChild(cellData, y, x)
					|| tryToMove(cellData, y, x, 0, 0)) {/*Do nothing*/}
		}
		if (XUcfCoder.decodeActiveFlagPlant(cellData) == 1 && XUcfCoder.decodePlantType(cellData) != XUcfCoder.PLANT_TYPE_EMPTY) {
			tryToMakeFruits(cellData, y, x);
			tryToDropFruit(cellData, y, x);
		}
	}
	
	// HUMAN - DIE
	private static boolean tryToDie(long cellData, int y, int x) {
		XEventsInfoPanel events = XMainPanel.eventsInfoPanel;
		int energy = XUcfCoder.decodeHumanEnergy(cellData);
		if (energy == 0) {
			clearHuman(y, x);
			XStatistic.peopleDied++;
			XStatistic.peopleDiedByEnergy++;
			events.update(date, "Human died by [Low Energy].");
			return true;
		}
		int satiety = XUcfCoder.decodeHumanSatiety(cellData);
		if (satiety == 0) {
			clearHuman(y, x);
			XStatistic.peopleDied++;
			XStatistic.peopleDiedBySatiety++;
			events.update(date, "Human died [Low Satiety].");
			return true;
		}
		// f(x): f(0..9000) <= 0, f(24000..32767) >= 100;
		// f(x) = 2*(x/300) - 60;
		// Every year + 2% (since 30 years)
		boolean decision = XRandom.generateBoolean(2 * (XUcfCoder.decodeHumanAge(cellData) / 300) - 60);
		if (decision) {
			clearHuman(y, x);
			XStatistic.peopleDied++;
			XStatistic.peopleDiedByAge++;
			events.update(date, "Human died [Age].");
			return true;
		}
		return false;
	}
	
	// HUMAN - EAT
	private static boolean tryToEat(long cellData, int y, int x) {
		XMapPanel map = XMainPanel.mapPanel;
		// f(x): f(0..10) >= 100, f(60..64) <= 0;
		// f(x) = 120 - 2*x;
		boolean decision = XRandom.generateBoolean(120 - 2 * XUcfCoder.decodeHumanSatiety(cellData));
		if (decision) {
			for (int yShift = -1; yShift < 2; yShift++) {
				for (int xShift = -1; xShift < 2; xShift++) {
					int yTarget = y + yShift;
					int xTarget = x + xShift;
					if (isCellInMapRange(yTarget, xTarget)) {
						int fruitsTarget = map.getPlantFruitsAt(yTarget, xTarget);
						if (fruitsTarget != 0) {
							map.setPlantFruitsAt(--fruitsTarget, yTarget, xTarget);
							if (map.getHumanAgeAt(y, x) < 10) {
								map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) + 32, y, x);
							}
							else {
								map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) + 16, y, x);
							}
							map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 1, y, x);
							map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
							map.setActiveFlagHumanAt(0, y, x);							
							return true;
						}
					}
				}	
			}
			int minTarget = XMapPanel.MAP_SIZE * XMapPanel.MAP_SIZE + XMapPanel.MAP_SIZE * XMapPanel.MAP_SIZE;
			int yTarget = y;
			int xTarget = x;
			for (int yTemp = 0; yTemp < XMapPanel.MAP_SIZE; yTemp++) {
				for (int xTemp = 0; xTemp < XMapPanel.MAP_SIZE; xTemp++) {					
					if (map.getPlantFruitsAt(yTemp, xTemp) != 0) {
						int yDelta = Math.abs(yTemp - y);
						int xDelta = Math.abs(xTemp - x);
						int minTemp = yDelta * yDelta + xDelta * xDelta;
						if (minTemp < minTarget) {
							minTarget = minTemp;
							yTarget = yTemp;
							xTarget = xTemp;
						}
					}
				}
			}
			if (yTarget != y || xTarget != x) {
				int yShift = 0;
				int xShift = 0;
				int yDelta = yTarget - y;
				int xDelta = xTarget - x;
				if (yDelta < 0) {
					yShift = -1;
				}
				else if (yDelta > 0) {
					yShift = 1;
				}
				if (xDelta < 0) {
					xShift = -1;
				}
				else if (xDelta > 0) {
					xShift = 1;
				}
				return tryToMove(cellData, y, x, yShift, xShift);
			}
		}
		return false;
	}
	
	// HUMAN - SLEEP
	private static boolean tryToSleep(long cellData, int y, int x) {
		// f(x): f(0..10) >= 100, f(60..64) <= 0;
		// f(x) = 120 - 2*x;
		boolean decision = XRandom.generateBoolean(120 - 2 * XUcfCoder.decodeHumanEnergy(cellData));
		if (decision) {
			XMapPanel map = XMainPanel.mapPanel;
			map.setHumanEnergyAt(63, y, x);
			map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 1, y, x);
			map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
			map.setActiveFlagHumanAt(0, y, x);
			return true;
		}
		return false;
	}
	
	// HUMAN - MAKE CHILD
	private static boolean tryToMakeChild(long cellData, int y, int x) {
		XMapPanel map = XMainPanel.mapPanel;
		if (map.getHumanTypeAt(y, x) == XUcfCoder.HUMAN_TYPE_WOMAN && map.getHumanPregnancyAt(y, x) == 0) {
			for (int yShift = -1; yShift < 2; yShift++) {
				for (int xShift = -1; xShift < 2; xShift++) {
					int yTarget = y + yShift;
					int xTarget = x + xShift;
					if (isCellInMapRange(yTarget, xTarget)
							&& map.getHumanTypeAt(yTarget, xTarget) == XUcfCoder.HUMAN_TYPE_MAN 
							&& map.getActiveFlagHumanAt(yTarget, xTarget) == 1) {
						boolean decision = XRandom.generateBoolean(30);
						if (decision) {
							map.setHumanPregnancyAt(1, y, x);
							map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 4, y, x);
							map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 4, y, x);
							map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
							map.setActiveFlagHumanAt(0, y, x);
							map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 4, yTarget, xTarget);
							map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 4, yTarget, xTarget);
							map.setHumanAgeAt(map.getHumanAgeAt(yTarget, xTarget) + 1, yTarget, xTarget);
							map.setActiveFlagHumanAt(0, yTarget, xTarget);
							XMainPanel.eventsInfoPanel.update(date, "Woman got pregnant.");
							return true;
						}
					}
				}	
			}
		}
		return false;
	}
	
	// HUMAN - GIVE BIRTH
	private static boolean tryToGiveBirth(long cellData, int y, int x) {
		XMapPanel map = XMainPanel.mapPanel;
		int pregnacy = map.getHumanPregnancyAt(y, x);
		if (pregnacy != 0 && pregnacy < 300) {
			map.setHumanPregnancyAt(map.getHumanPregnancyAt(y, x) + 1, y, x);
		}
		else if (pregnacy == 300) {
			map.setHumanPregnancyAt(0, y, x);
			map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 4, y, x);
			map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 4, y, x);
			map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
			map.setActiveFlagHumanAt(0, y, x);
			for (int yShift = -1; yShift < 2; yShift++) {
				for (int xShift = -1; xShift < 2; xShift++) {
					int yTarget = y + yShift;
					int xTarget = x + xShift;
					if (isCellInMapRange(yTarget, xTarget) && map.getHumanTypeAt(yTarget, xTarget) == XUcfCoder.HUMAN_TYPE_EMPTY) {
						map.setHumanTypeAt(XRandom.generateBoolean() ? XUcfCoder.HUMAN_TYPE_MAN : XUcfCoder.HUMAN_TYPE_WOMAN, yTarget, xTarget);
						map.setHumanAgeAt(301, yTarget, xTarget);
						map.setHumanEnergyAt(63, yTarget, xTarget);
						map.setHumanSatietyAt(63, yTarget, xTarget);
						map.setHumanPregnancyAt(0, yTarget, xTarget);
						map.setActiveFlagHumanAt(0, yTarget, xTarget);
						XStatistic.childrenWereBorn++;
						XMainPanel.eventsInfoPanel.update(date, "Child was born.");
						return true;
					}
				}
			}
			map.setHumanPregnancyAt(0, y, x);
			XStatistic.childrenDied++;
			XMainPanel.eventsInfoPanel.update(date, "Child died.");
			return true;
		}
		return false;
	}
	
	// HUMAN - MOVE
	private static boolean tryToMove(long cellData, int y, int x, int yShift, int xShift) {
		XMapPanel map = XMainPanel.mapPanel;
		if (yShift == 0 && xShift == 0) {
			yShift = XRandom.generateInteger(-1, 1);	
			xShift = XRandom.generateInteger(-1, 1);
		}
		if (yShift != 0 || xShift != 0) {
			int yTarget = y + yShift;
			int xTarget = x + xShift;
			if (!isCellInMapRange(yTarget, xTarget)) {
				clearHuman(y, x);
				XStatistic.peopleDied++;
				XStatistic.peopleDiedByLost++;
				XMainPanel.eventsInfoPanel.update(date, "Human died [Lost].");
				return true;
			}
			if (map.getHumanTypeAt(yTarget, xTarget) == 0) {
				moveHuman(y, x, yTarget, xTarget);
				int landscapeTarget = map.getLandscapeTypeAt(yTarget, xTarget);
				if (landscapeTarget == XUcfCoder.LANDSCAPE_TYPE_WATER_LOW || landscapeTarget == XUcfCoder.LANDSCAPE_TYPE_WATER_HIGH) {
					map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 3, yTarget, xTarget);
					map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 3, yTarget, xTarget);
				}
				else {
					map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 2, yTarget, xTarget);
					map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 2, yTarget, xTarget);
				}
				map.setHumanAgeAt(map.getHumanAgeAt(yTarget, xTarget) + 1, yTarget, xTarget);
				map.setActiveFlagHumanAt(0, yTarget, xTarget);
				return true;
			}
		}
		map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 1, y, x);
		map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 1, y, x);
		map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
		map.setActiveFlagHumanAt(0, y, x);
		return false;
	}
	
	// PLANT - MAKE FRUITS
	private static void tryToMakeFruits(long cellData, int y, int x) {
		if (date % 30 == 0) {
			XMapPanel map = XMainPanel.mapPanel;
			map.setPlantFruitsAt(map.getPlantFruitsAt(y, x) + XRandom.generateInteger(5, 15), y, x);
		}
	}
	
	// PLANT - DROP FRUIT
	private static void tryToDropFruit(long cellData, int y, int x) {
		XMapPanel map = XMainPanel.mapPanel;
		if (map.getPlantFruitsAt(y, x) == 0) {
			return;
		}
		// 0.01 * 0.50 = 0.005
		boolean decision = XRandom.generateBoolean(1) && XRandom.generateBoolean(50);
		if (decision) {
			map.setPlantFruitsAt(map.getPlantFruitsAt(y, x) - 1, y, x);
			int yTarget = y + XRandom.generateInteger(-2, 2);
			int xTarget = x + XRandom.generateInteger(-2, 2);
			if (isCellInMapRange(yTarget, xTarget) && (yTarget != y || xTarget != x)) { 
				int landscapeTarget = map.getLandscapeTypeAt(yTarget, xTarget);
				int humanTarget = map.getHumanTypeAt(yTarget, xTarget);
				int plantTarget = map.getPlantTypeAt(yTarget, xTarget);
				if (landscapeTarget != XUcfCoder.LANDSCAPE_TYPE_WATER_LOW 
						&& landscapeTarget != XUcfCoder.LANDSCAPE_TYPE_WATER_HIGH
						&& humanTarget == XUcfCoder.HUMAN_TYPE_EMPTY 
						&& plantTarget == XUcfCoder.PLANT_TYPE_EMPTY) {
					map.setPlantTypeAt(XUcfCoder.PLANT_TYPE_APPLE, yTarget, xTarget);
				}
			}
		}
	}
	
	private static void moveHuman(int yFrom, int xFrom, int yTo, int xTo) {
		XMapPanel map = XMainPanel.mapPanel;
		map.setHumanTypeAt(map.getHumanTypeAt(yFrom, xFrom), yTo, xTo);
		map.setHumanAgeAt(map.getHumanAgeAt(yFrom, xFrom), yTo, xTo);
		map.setHumanEnergyAt(map.getHumanEnergyAt(yFrom, xFrom), yTo, xTo);
		map.setHumanSatietyAt(map.getHumanSatietyAt(yFrom, xFrom), yTo, xTo);
		map.setHumanPregnancyAt(map.getHumanPregnancyAt(yFrom, xFrom), yTo, xTo);
		clearHuman(yFrom, xFrom);
	}
	
	private static void clearHuman(int y, int x) {
		XMapPanel map = XMainPanel.mapPanel;
		map.setHumanTypeAt(0, y, x);
		map.setHumanAgeAt(0, y, x);
		map.setHumanEnergyAt(0, y, x);
		map.setHumanSatietyAt(0, y, x);
		map.setHumanPregnancyAt(0, y, x);
	}
	
	private static boolean isCellInMapRange(int y, int x) {
		return y >= 0 && y < XMapPanel.MAP_SIZE && x >= 0 && x < XMapPanel.MAP_SIZE;
	}
	
}

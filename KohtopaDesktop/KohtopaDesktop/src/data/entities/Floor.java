package data.entities;

import Language.Language;


public class Floor implements Comparable{

	private int floorNumber;

	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getFloorfloorNumber() {
		return floorNumber;
	}

	@Override
	public String toString() {
		return Language.getString("floor") + " " + floorNumber;
	}

	public int compareTo(Object floor2) {
		int floorNumber2 = ((Floor)floor2).getFloorfloorNumber();
		if(floorNumber < floorNumber2){
			return -1;
		}else if(floorNumber == floorNumber2){
			return 0;
		}else{
			return 1;
		}
	}
}

package communityhousebean;

import communityhousemodel.FacilityModel;

public class HireBean {
	private FacilityModel facility;
	private int hiredQuantity;
	
	public FacilityModel getFacility() {
		return facility;
	}
	public void setFacility(FacilityModel facility) {
		this.facility = facility;
	}
	public int getHiredQuantity() {
		return hiredQuantity;
	}
	public void setHiredQuantity(int hiredQuantity) {
		this.hiredQuantity = hiredQuantity;
	}
	
}


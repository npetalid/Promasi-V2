package org.promasi.ui.exportwizard.resources;

/**
 * 
 * @author antoxron
 *
 */
public class CompanyModel {
	
	private String _companyName;
	private String _description;
	private String _htmlDescription;
	private String _startTime;
	private String _endTime;
	private String _budget;
	private String _prestigePoints;
	private String _imagePath;
	
	public void setHtmlDescription(String htmlDescription) {
		_htmlDescription = htmlDescription;
	}
	public String getHtmlDescription() {
		return _htmlDescription;
	}
	
	public void setCompanyName(String companyName) {
		_companyName = companyName;
	}
	public String getCompanyName() {
		return _companyName;
	}
	public void setDescription(String description) {
		_description = description;
	}
	public String getDescrition() {
		return _description;
	}
	public void setStartTime(String startTime) {
		_startTime = startTime;
	}
	public String getStartTime() {
		return _startTime;
	}
	public void setEndTime(String endTime) {
		_endTime = endTime;
	}
	public String getEndTime() {
		return _endTime;
	}
	public void setBudget(String budget) {
		_budget = budget;
	}
	public String getBudget() {
		return _budget;
	}
	public void setPrestigePoints(String prestigePoints) {
		_prestigePoints = prestigePoints;
	}
	public String getPrestigePoints() {
		return _prestigePoints;
	}
	public void setImagePath(String imagePath) {
		_imagePath = imagePath;
	}
	public String getImagePath() {
		return _imagePath;
	}
	

}

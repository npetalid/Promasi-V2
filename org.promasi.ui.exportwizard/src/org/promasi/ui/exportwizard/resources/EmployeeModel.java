package org.promasi.ui.exportwizard.resources;

/**
 * 
 * @author antoxron
 *
 */
public class EmployeeModel {

	private String _id;
	private String _name;
	private String _lastName;
	private String _salary;
	private String _developerSkill;
	
	private String _testerSkill;
	private String _teamPlayerSkill;
	private String _systemKnowledgeSkill;
	private String _designerSkill;
	private String _curriculumVitae;
	private String _htmlCurriculumVitae;

	private String _imagePath;
	
	
	public void setHtmlCurriculumVitae(String htmlCurriculumVitae) {
		_htmlCurriculumVitae = htmlCurriculumVitae;
	}
	public String getHtmlCurriculumVitae() {
		return _htmlCurriculumVitae;
	}
	
	
	public void setImagePath(String imagePath) {
		_imagePath = imagePath;
	}
	public String getImagePath() {
		return _imagePath;
	}
	
	
	public void setDesignerSkill(String designerSkill) {
		_designerSkill = designerSkill;
	}
	public String getDesignerSkill() {
		return _designerSkill;
	}
	
	
	public String getid() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String lastName) {
		this._lastName = lastName;
	}
	public String getSalary() {
		return _salary;
	}
	public void setSalary(String salary) {
		this._salary = salary;
	}
	public String getDeveloperSkill() {
		return _developerSkill;
	}
	public void setDeveloperSkill(String developerSkill) {
		this._developerSkill = developerSkill;
	}
	public String getTesterSkill() {
		return _testerSkill;
	}
	public void setTesterSkill(String testerSkill) {
		this._testerSkill = testerSkill;
	}
	public String getTeamPlayerSkill() {
		return _teamPlayerSkill;
	}
	public void setTeamPlayerSkill(String teamPlayerSkill) {
		this._teamPlayerSkill = teamPlayerSkill;
	}
	public String getSystemKnowledgeSkill() {
		return _systemKnowledgeSkill;
	}
	public void setSystemKnowledgeSkill(String systemKnowledgeSkill) {
		this._systemKnowledgeSkill = systemKnowledgeSkill;
	}
	public String getCurriculumVitae() {
		return _curriculumVitae;
	}
	public void setCurriculumVitae(String curriculumVitae) {
		this._curriculumVitae = curriculumVitae;
	}
}
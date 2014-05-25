package org.promasi.ui.exportwizard.resources;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author antoxron
 *
 */
public class Companies {

	private List<CompanyModel> _companies;
	
	
	public Companies() {
		_companies = new ArrayList<CompanyModel>();
	}
	
	public void addCompany(CompanyModel company) {
		_companies.add(company);
	}
	
	public void setCompanies(List<CompanyModel> companies) {
		_companies = companies;
	}
	public List<CompanyModel> getCompanies() {
		return _companies;
	}
	
}

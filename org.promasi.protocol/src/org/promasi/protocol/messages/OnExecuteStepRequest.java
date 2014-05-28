/**
 *
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.ProjectModel;

/**
 * @author m1cRo
 *
 */
public class OnExecuteStepRequest extends Message {

    /**
     *
     */
    private ProjectModel _project;

    /**
     *
     */
    private String _dateTime;

    /**
     *
     */
    private CompanyModel _company;

    /**
     *
     */
    public OnExecuteStepRequest() {
    }

    /**
     *
     * @param project
     * @param company
     * @param dateTime
     */
    public OnExecuteStepRequest(ProjectModel project, CompanyModel company, String dateTime) {
        _project = project;
        _dateTime = dateTime;
        setCompany(company);
    }

    /**
     * @param project the project to set
     */
    public void setProject(ProjectModel project) {
        _project = project;
    }

    /**
     * @return the project
     */
    public ProjectModel getProject() {
        return _project;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(String dateTime) {
        _dateTime = dateTime;
    }

    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return _dateTime;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(CompanyModel company) {
        _company = company;
    }

    /**
     * @return the company
     */
    public CompanyModel getCompany() {
        return _company;
    }
}

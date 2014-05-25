package org.promasi.ui.exportwizard.editor.dialog.table;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.promasi.ui.exportwizard.editor.dialog.table.model.ProjectTaskTableModel;

/**
 * 
 * @author antoxron
 *
 */
public class ProjectTaskLabelProvider extends LabelProvider 
implements ITableLabelProvider { 

	
	
	
	   public Image getColumnImage(Object element, int index) { 
	      return null; 
	   } 
	   public String getColumnText(Object element, int index) { 
		   ProjectTaskTableModel model = (ProjectTaskTableModel) element; 
	      switch (index) { 
	         case 0 : 
	        	 return model.getTaskName();
	         case 1 : 
	        	 return model.getEquation();
	       
	         default : 
	            return "unknown " + index; 
	      } 
	   } 
	} 

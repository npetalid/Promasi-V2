package org.promasi.coredesigner.ui.dialogs.table.simulation;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableNode;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableSubNode;


public class TreeContentProvider implements ITreeContentProvider {
	   
	  public Object[] getChildren(Object parentElement){
	         if (parentElement instanceof List)
	            return ((List<?>) parentElement).toArray();
	         if (parentElement instanceof TreeTableNode)
	            return ((TreeTableNode) parentElement).getSdObjects().toArray();
	         return new Object[0];
	      }
	 
	      public Object getParent(Object element){
	         if (element instanceof TreeTableSubNode)
	            return ((TreeTableSubNode) element).getSdModel();
	         return null;
	      }
	 
	      public boolean hasChildren(Object element){
	         if (element instanceof List)
	            return ((List<?>) element).size() > 0;
	         if (element instanceof TreeTableNode)
	            return ((TreeTableNode) element).getSdObjects().size() > 0;
	         return false;
	      }
	 
	      public Object[] getElements(Object element){

	         return getChildren(element);
	      }
	 
	      public void dispose(){
	      }
	 
	      public void inputChanged(Viewer viewer, Object oldInput, Object newInput){
	      }
 }
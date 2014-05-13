package org.promasi.coredesigner.model.builder;

/**
 * 
 * @author antoxron
 *
 */
public class ModelBuilderFactory {

	public static final String VISUAL_BUILDER = "Visual Builder";
	
	public static IModelBuilder getInstance( String builderType ) {
		
		IModelBuilder results = null;
		
		if ( ( builderType != null ) && ( !builderType.trim().isEmpty() ) ) {
			
			 if (builderType.equals( VISUAL_BUILDER ) ) {
				results = new VisualModelBuilder(); 
			}
		}
		return  results;	
	}	
}
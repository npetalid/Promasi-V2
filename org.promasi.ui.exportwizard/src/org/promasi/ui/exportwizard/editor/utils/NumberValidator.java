package org.promasi.ui.exportwizard.editor.utils;
/**
 * 
 * @author antoxron
 *
 */
public class NumberValidator {

	public static boolean isInteger( String text ) {
		return text.matches( "(\\d){1,9}" );
	} 
	public static boolean isDouble( String text ) {
		return text.matches( "\\d{1,3}\\.\\d{1,3}" );
	} 
	
	public static boolean isProgressNumber( String text ) {
		return text.matches( "\\d{1,5}\\/\\d{1,5}" );
	} 
	
}

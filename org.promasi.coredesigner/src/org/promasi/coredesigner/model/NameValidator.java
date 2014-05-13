package org.promasi.coredesigner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * used to manage the names of SdObjects
 * 
 * @author antoxron
 *
 */
public class NameValidator {
	
	/**
	 * 
	 */
	private static NameValidator _instance = null;
	/**
	 * 
	 */
	private  Map<String, List<String>> _names = new HashMap<String, List<String>>( );
	/**
	 * 
	 */
	private boolean _validateNames = true;

	
	/**
	 * 
	 * @param names
	 */
	public void setNames( Map<String, List<String>> names ) {
		_names = names;
	}
	/**
	 * 
	 * @return
	 */
	public boolean validateNames( ) {
		return _validateNames;
	}
	/**
	 * 
	 * @param state
	 */
	public void setValidateNames( boolean state ) {
		_validateNames = state;
	}
	/**
	 * 
	 * @return
	 */
	public static NameValidator getInstance( ) {
		if ( _instance == null ) {
			_instance = new NameValidator( );
		}
		return _instance;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean validateName( String name ) {
		boolean results = false;
		if ( validateNames() ) {
			
			if ( ( name != null ) && ( !name.isEmpty() ) ) {
				
				if (  validateString( name ) ) {
					results = true;
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param oldName
	 * @param newName
	 * @return 
	 */
	public boolean validateName( String oldName, String newName ) {
		
		boolean isCorrect = false;
		
		if ( validateNames() ) {
			if ( ( oldName != null ) && ( newName != null ) ) {
				
				if ( ( !oldName.isEmpty() ) && ( !newName.isEmpty() )  ) {
					
						if (  validateString( newName ) ) {
							
							if ( isKeyExist( oldName ) && ( !isKeyExist(newName ) ) ) {
								if ( renameKey( oldName , newName ) ) {
									isCorrect = true;
								}
							}
							else {
								if ( addKey( newName ) ) {
									isCorrect = true;
								}
							}
						}
				}
				
			}
		}
		return isCorrect;
	}
	/**
	 * 
	 * @param oldName
	 * @param newName
	 * @param parent
	 * @return
	 */
	public boolean validateName( String oldName, String newName, String parent ) {
		
		boolean isCorrect = false;

		
		if (validateNames()) {
			if ( ( oldName != null ) && ( newName != null ) && ( parent != null ) ) {
				
				if ( ( !oldName.trim().isEmpty() ) && ( !newName.trim().isEmpty() ) && ( !parent.trim().isEmpty() ) ) {
					
						if (  validateString(newName)  ) {
							
							if (  ( isValueExist( oldName, parent ) ) && ( !isValueExist( newName, parent ) ) ) {
								if (renameValue( oldName, newName, parent ) ) {
									isCorrect = true;
								}
							}
							else {
								if ( addValue( parent, newName ) ) {
									
									isCorrect = true;
									
								}
							}
						}
				}
				
			}
		}
		return isCorrect;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeName( String name ) {
		
		boolean isCorrect = false;
		
		if ( validateNames( ) ) {
			if ( ( name != null ) && ( !name.trim().isEmpty() ) ) {

				if ( validateString( name ) ) {
					if ( removeKey( name ) ) {
						isCorrect = true;
					}
				}
			}
		}
		return isCorrect;
	}
	/**
	 * 
	 * @param name
	 * @param parent
	 * @return
	 */
	public boolean removeName( String name, String parent ) {
		
		boolean isCorrect = false;

		if ( validateNames( ) ) {
			if ( ( name != null ) && ( parent != null ) ) {
				
				if ( ( !name.trim().isEmpty() ) && ( !parent.trim().isEmpty() ) ) {
					
						if ( validateString( name )   && validateString( parent ) ) {
							
							if ( removeValue( parent, name ) ) {
								
								isCorrect = true;
								
							}
						}
				}
				
			}
		}
		return isCorrect;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	private boolean addKey( String key ) {
		
		boolean results = false;
		
		if ( !_names.containsKey( key ) ) {
			_names.put( key, new ArrayList<String>( ) );
			results = true;
		}
		return results;
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean addValue( String key, String value ) {
		boolean results = false;
		
		
		if ( _names.containsKey( key ) ) {
			List<String> objectNames = _names.get( key );
			if ( objectNames != null ) {
								
				if ( objectNames.indexOf( value ) == -1 ) {
					objectNames.add( value );
					_names.put( key, objectNames );
					results = true;
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	private  boolean removeKey( String key ) {
		boolean results = false;
		
		if ( _names.containsKey( key ) ) {
			_names.remove( key );
			results = true;
		}
		return results;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	private boolean isKeyExist( String key ) {
		boolean results = false;
		
		if ( key != null ) {
			results = _names.containsKey( key );
		}
		return results;
	}
	/**
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	private boolean isValueExist( String value, String key ) {
		boolean results = false;
		
		if ( ( value != null ) && ( key != null ) ) {
			List<String> values = _names.get( key );
			if ( values != null ) {
				if ( values.contains( value ) ) {
					results = true;
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param oldName
	 * @param newName
	 * @param key
	 * @return
	 */
	private boolean renameValue( String oldName, String newName, String key ) {
		boolean results = false;
		
		
		if ( ( oldName != null ) && ( newName != null ) && ( key != null ) ) {
			List<String> values = _names.get( key );
			if ( values != null ) {
				if ( values.contains( oldName ) ) {
					int index = values.indexOf( oldName );
					values.remove( index );
					values.add( newName );
					_names.put( key, values );
					results = true;
				}
			}
		}
		
		
		return results;
	}
	/**
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	private boolean renameKey( String oldName, String newName ) {
		boolean results = false;
		
		if ( ( oldName != null ) && ( newName != null ) ) {
			
			if ( _names.containsKey( oldName ) ) {
				List<String> values = _names.get( oldName );
				if ( values != null ) {
					_names.remove( oldName );
					_names.put( newName, values );
					
					results = true;
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean removeValue( String key, String value ) {
		
		boolean results = false;
		
		if ( _names.containsKey( key ) ) {
			List<String> values = _names.get( key );
			if ( values != null ) {
				if ( values.contains( value ) ) {
					if ( values.remove( value ) ) {
						_names.put( key, values );
						results = true;
					}
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean validateString( String name ) {
		return name.matches( "[^\\s][a-zA-Z%\\s]+[0-9]*" );
	} 
}

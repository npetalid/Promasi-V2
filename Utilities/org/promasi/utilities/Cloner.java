package org.promasi.utilities;


/**
 * @author eddiefullmetal
 * 
 */
public final class Cloner
{

    /**
     * Method that checks if the object to clone is null. If it is or if the
     * clone failed it returns null.
     * 
     * @param <T>
     * @param objectToClone
     * @return
     */
    public static <T> T clone ( ICloneable<T> objectToClone )
    {
        if ( objectToClone != null )
        {
            T clonedObject = objectToClone.copy( );
            if ( clonedObject != objectToClone )
            {
                return clonedObject;
            }
        }
        return null;
    }
}

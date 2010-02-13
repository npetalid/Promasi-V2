package org.promasi.utilities;


/**
 * 
 * Convenient interface for the {@link Cloneable} interface. Adds a copy method
 * that supports generics.
 * 
 * @author eddiefullmetal
 * 
 */
public interface ICloneable<T>
        extends Cloneable
{
    T copy ( );
}

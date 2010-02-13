package org.promasi.core;


/**
 * The type of an {@link ISdObject}.
 * 
 * @author eddiefullmetal
 * 
 */
public enum SdObjectType
{
    /**
     * The Stock type has an initial value and this value is depleted according
     * to the equation of the stock and the integration of the system.
     */
    Stock,
    /**
     * The Flow type in terms of functionality is the same as the Variable type,
     * but it represents the rate of change in a stock.
     */
    Flow,
    /**
     * The Variable type represents an sd object that contains a value defined
     * by an equation. This sd object is updated at every step of the system.
     */
    Variable,
    /**
     * The System type represents an internal sd object containing information
     * about system. Ex. the time sd object represents the steps that have
     * passed since the system started
     */
    System,
    /**
     * The Output type is sending its value to an upper layer. Its used when the
     * upper layer is interested in an object of the core and wants to get
     * notified.
     */
    Output
}

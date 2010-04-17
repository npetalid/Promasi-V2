package org.promasi.core;


import java.util.List;
import org.promasi.communication.ICommunicator;


/**
 * Basic interface that represents an object in a system dynamic
 * model(stock,flow etc). An ISdObject has the following characteristics:
 * <ol>
 * <li>An ISdObject is represented in the system by a key.
 * <li>An ISdObject has a type({@link SdObjectType}) which represents the role
 * of the object in the system.
 * <li>An ISdObject has a value used by the system.
 * <li>An ISdObject might depend on other objects in order to calculate its
 * value.
 * </ol>
 *
 * @see SdObjectType
 *
 * @author eddiefullmetal
 *
 */
public interface ISdObject
{

    /**
     * calculates the value of the object. This method is called in every step
     * of the system.
     */
    void calculateValue ( );

    /**
     * Returns the {@link SdObjectType} of this object. This method must not
     * return null, all {@link ISdObject}s must have a defined
     * {@link SdObjectType}.
     *
     * @return The {@link SdObjectType} of the {@link ISdObject}
     */
    SdObjectType getType ( );

    /**
     *
     * Returns the value of the object. This method must not return null,
     * instead a 0 value must be returned.
     *
     * @return The value of the object
     */
    Double getValue ( );

    /**
     * Returns the key of the object. This method must not return null, all
     * {@link ISdObject}s must have a unique key.
     *
     * @return The key of the object
     */
    String getKey ( );

    /**
     * Adds a dependency with another {@link ISdObject}.
     *
     * @param sdObject
     *            The {@link ISdObject} that this object depends.
     */
    void addDependency ( ISdObject sdObject );

    /**
     *
     * Returns the dependencies. This method must not return null, instead an
     * empty {@link List} must be returned.
     *
     * @return The dependencies of this sd object.
     */
    List<ISdObject> getDependencies ( );

    /**
     * @return The {@link SdObjectInfo} about this object. The method can return
     *         null.
     */
    SdObjectInfo getInfo ( );

    /**
     * Adds an external property for this object so that any external
     * application can keep properties for a specific object.
     *
     * @param key
     *            The key of the property.
     * @param property
     *            The value of the property.
     */
    void addProperty ( String key, Object property );

    /**
     * @param key
     * @return The property with the specified key.
     */
    Object getProperty ( String key );

    /**
     * Will register the system communicator.
     * @param communicator
     */
    public void registerCommunicator(ICommunicator communicator);
}

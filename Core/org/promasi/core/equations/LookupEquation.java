package org.promasi.core.equations;


import java.io.Serializable;
import java.util.SortedMap;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.SplineInterpolator;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.UnivariateRealInterpolator;
import org.apache.log4j.Logger;
import org.promasi.communication.ICommunicator;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.utilities.ErrorBuilder;


/**
 * The LookupEquation is used to create user specific graphs. The user defines a
 * graph by specifying the x,y points of the graph and gives an
 * {@link ISdObject} so that the value of the LookupEquation will be calculated
 * according to the value of the {@link ISdObject}. For example if a user
 * defines the graph (1,1)(2,2)(3,3)(4,4) and the {@link ISdObject} has a value
 * of 3.5 the value of the equation will be 3.5. The {@link ISdObject} that uses
 * this {@link IEquation} must have the {@link #_dependentObject} as a
 * dependency. The class follows the javabeans specification.
 *
 * @author eddiefullmetal
 *
 */
public class LookupEquation
        implements IEquation, Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * All the x points.
     */
    private double[] _xValues;

    /**
     * All the y points.
     */
    private double[] _yValues;

    /**
     * The equation calculates its value depending on this object.
     */
    private ISdObject _dependentObject;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( LookupEquation.class );

    /**
     * Initializes the object.
     *
     * @param xyPoints
     *            An array containing the xy pairs of the graph. The equation is
     *            initialized with a {@link SortedMap}(x is the key,y the value)
     *            in order to make sure that the sizes of the {@link #_xValues}
     *            and the {@link #_yValues} will be the same and that the
     *            _xValues[3] will pair with the _yValues[3]. The
     *            {@link SortedMap} is needed cause the x points(keys) must have
     *            an increasing order.
     *
     *
     * @param dependentObject
     *            The {@link #_dependentObject}.
     *
     * @throws IllegalArgumentException
     *             In case the xyPoints is null or empty.
     * @throws NullArgumentException
     *             In case the dependentObject is null.
     *
     */
    public LookupEquation( SortedMap<Double, Double> xyPoints, ISdObject dependentObject )
            throws IllegalArgumentException, NullArgumentException
    {
        // Check arguments
        if ( xyPoints == null || xyPoints.size( ) == 0 )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "LookupEquation", "xyPoints" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        if ( dependentObject == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "LookupEquation", "dependentObject" ) );
            throw new NullArgumentException( "dependentObject" );
        }

        final int sizeOfXyPoints = xyPoints.size( );

        _xValues = new double[sizeOfXyPoints];
        _yValues = new double[sizeOfXyPoints];

        int i = 0;
        for ( double key : xyPoints.keySet( ) )
        {
            _xValues[i] = key;
            _yValues[i] = xyPoints.get( key );
            i++;
        }

        _dependentObject = dependentObject;
    }

    /**
     * Initializes the object.
     */
    public LookupEquation( )
    {
    }

    /**
     * Calculates the value of the equation, using the
     * {@link UnivariateRealInterpolator} and the {@link UnivariateRealFunction}
     * .
     *
     * @return The value of the equation.
     * @throws MathException
     *             In case an error occurs when
     */
    @Override
    public Double calculateValue ( )
            throws MathException
    {
        UnivariateRealInterpolator interpolator = new SplineInterpolator( );
        UnivariateRealFunction function = interpolator.interpolate( _xValues, _yValues );
        // The ISdObject that has a lookup equation as an IEquation must
        // have the _dependentObject as a dependency. That will make sure
        // that the value of the _dependentObject is calculated before the
        // value of the current ISdObject.
        return function.value( _dependentObject.getValue( ) );
    }

    @Override
    public EquationType getType ( )
    {
        return EquationType.Lookup;
    }

    /**
     * @return The {@link #_dependentObject}.
     */
    public ISdObject getDependentObject ( )
    {
        return _dependentObject;
    }

    /**
     * @param object
     *            The {@link #_dependentObject} to set.
     */
    public void setDependentObject ( ISdObject object )
    {
        _dependentObject = object;
    }

    /**
     * @return {@link #_xValues}
     */
    public double[] getXValues ( )
    {
        return _xValues;
    }

    /**
     * @param values
     *            The {@link #_xValues} to set.
     */
    public void setXValues ( double[] values )
    {
        _xValues = values;
    }

    /**
     * @return The {@link #_yValues}.
     */
    public double[] getYValues ( )
    {
        return _yValues;
    }

    /**
     * @param values
     *            The {@link #_yValues} to set.
     */
    public void setYValues ( double[] values )
    {
        _yValues = values;
    }

	@Override
	public void registerCommunicator(ICommunicator communicator) {
		// TODO Auto-generated method stub

	}
}

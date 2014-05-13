package org.promasi.coredesigner.model.builder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdModelConnection;
/**
 * 
 * @author antoxron
 *
 */
public class XmlObject {

	private String name;
	private String type;
	private List<Dependency> dependencies;
	private List<SdModelConnection> connections;
	private double initialValue;
	private String equation;
	
	private int x;
	private int y;
	private int height;
	private int width;
	
	
	private TreeMap<Double,Double> lookupPoints;
	private String calculateString;
	
	
	
	public XmlObject() {
		dependencies = new ArrayList<Dependency>();
		connections = new ArrayList<SdModelConnection>();
		lookupPoints = new TreeMap<Double , Double>();
	}
	

	
	public void addDependency(Dependency dependency) {
		dependencies.add(dependency);
	}
	public void addConnection(SdModelConnection connection) {
		connections.add(connection);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Dependency> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	public List<SdModelConnection> getConnections() {
		return connections;
	}
	public void setConnections(List<SdModelConnection> connections) {
		this.connections = connections;
	}
	public double getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public TreeMap<Double, Double> getLookupPoints() {
		return lookupPoints;
	}

	public void setLookupPoints(TreeMap<Double, Double> lookupPoints) {
		this.lookupPoints = lookupPoints;
	}

	public String getCalculateString() {
		return calculateString;
	}

	public void setCalculateString(String calculateString) {
		this.calculateString = calculateString;
	}
}
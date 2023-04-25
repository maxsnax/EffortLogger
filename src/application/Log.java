/*	Author: Max Schumacher, Group W31
 * 	File: Log.java
 * 	Purpose: Skeleton for a Log object, current has no implementation into Project objects
 * 	Last modication: 04/02/23
 */

package application;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;

public class Log {
	
	private SimpleStringProperty name;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private LocalTime totalTime;
	private SimpleStringProperty lifeCycle = new SimpleStringProperty("");
	private SimpleStringProperty effortCategory = new SimpleStringProperty("");
	private SimpleStringProperty plan = new SimpleStringProperty("");
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	
    /*	=====================================================================================
			Constructors
		===================================================================================== */
	
	Log(String name, LocalDate date) {
		this.name = new SimpleStringProperty(name);
		this.date = date;
	}
	
	Log (String name, LocalDate date, String lifeCycle, String effortCategory, String plan) {
		this.name = new SimpleStringProperty(name);
		this.date = date;
		this.lifeCycle = new SimpleStringProperty(lifeCycle);
		this.effortCategory = new SimpleStringProperty(effortCategory);
		this.plan = new SimpleStringProperty(plan);
	}
	
    /*	=====================================================================================
			Setters and Getters
		===================================================================================== */
	// Name
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	// Date
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	// Time
	public String getStartTime() {
		if (startTime != null) return startTime.toString();
		return "hrs:min:sec";
	}

	public void setStartTime(LocalTime time) {
		this.startTime = time;
	}
	
	public String getFinishTime() {
		if (finishTime != null) return finishTime.toString();
		return "hrs:min:sec";
	}

	public void setFinishTime(LocalTime time) {
		this.finishTime = time;
	}
	
	public String getTotalTime() {
		if (startTime == null || finishTime == null) return "hrs:min:sec";
		return Duration.between(startTime, finishTime).toString();
	}

	// Life Cycle
	public String getLifeCycle() {
		return lifeCycle.get();
	}

	public void setLifeCycle(String lifeCycle) {
		this.lifeCycle = new SimpleStringProperty(lifeCycle);
	}
	
	// Effort Category
	public String getEffortCategory() {
		return effortCategory.get();
	}

	public void setEffortCategory(String effortCategory) {
		this.effortCategory = new SimpleStringProperty(effortCategory);
	}
	
	// Plan
	public String getPlan() {
		return plan.get();
	}

	public void setPlan(String plan) {
		this.plan = new SimpleStringProperty(plan);
	}
	
}

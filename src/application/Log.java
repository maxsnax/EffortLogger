/*	Author: Max Schumacher, Group W31

 * 	File: Log.java
 * 	Purpose: Used for tracking project progress. Tracks the name, creation date of the log,
 * 		the start time, finish time, total time spent, life cycle, effort category, and plan.
 */

package application;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;

public class Log {
	
	private SimpleStringProperty name;
	private LocalDate localDate;
	private SimpleStringProperty date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private SimpleStringProperty lifeCycle = new SimpleStringProperty("");
	private SimpleStringProperty effortCategory = new SimpleStringProperty("");
	private SimpleStringProperty plan = new SimpleStringProperty("");
	
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
	
    /*	=====================================================================================
			Constructors
		===================================================================================== */
	
	Log(String name, LocalDate localDate) {
		this.name = new SimpleStringProperty(name);
		this.localDate = localDate;
		this.date = new SimpleStringProperty(localDate.format(dateFormat));
	}
	
	Log (String name, LocalDate localDate, String lifeCycle, String effortCategory, String plan) {
		this.name = new SimpleStringProperty(name);
		this.localDate = localDate;
		this.date = new SimpleStringProperty(localDate.format(dateFormat));
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
	public LocalDate getLocalDate() {
		return localDate;
	}
	
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}
	
	public String getDate() {
		return date.get();
	}
	
	// Time
	public void setStartTime(LocalTime time) {
		this.startTime = time;
	}
	
	public LocalTime getStartTime() {
		return startTime;	
	}

	public void setFinishTime(LocalTime time) {
		this.finishTime = time;
	}
	
	public LocalTime getFinishTime() {
		return finishTime;
	}
	
	public String getTotalTime() {
		if (startTime == null || finishTime == null) return "";
		Duration time = Duration.between(startTime, finishTime);
		long hours = time.toHours();
		long minutes = time.toHours() % 60;
		return String.format("%02d:%02d", hours, minutes);
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

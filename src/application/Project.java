/*	Author: Max Schumacher, Group W31
 * 	File: Project.java
 * 	Purpose: Skeleton for a Project object
 * 	Last modication: 04/02/23
 */

package application;

import java.time.LocalDate;
import java.util.LinkedList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Project {
	
	private SimpleStringProperty name;
	private LocalDate date;
	private ObservableList<Log> logsList;
	
	public Project(String name, LocalDate date) {
		this.name = new SimpleStringProperty(name);
		this.date = date;
		this.logsList = FXCollections.observableArrayList();
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void addLog(String name, LocalDate date) {
		logsList.add(new Log(name, date));
	}
	
	public void addLog(String name, LocalDate date, String lifeCycle, String effortCategory, String plan) {
		logsList.add(new Log(name, date, lifeCycle, effortCategory, plan));
	}
	
	public void addLog(Log log) {
		logsList.add(log);
	}
	
	public void deleteLog(Log log) {
		logsList.remove(log);
	}
	
	public ObservableList<Log> getLogs() {
		return logsList;
	}
	
}

/*	Author: Max Schumacher, Group W31

 * 	File: Project.java
 * 	Purpose: Used for tracking project progress. Contains a list of the Logs 
 * 		created for the project.
 */

package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Project {
	
	private SimpleStringProperty name;
	private LocalDate localDate;
	private SimpleStringProperty date;
	private ObservableList<Log> logsList;
	private int ID;
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy"); 
	
	public Project(String name, LocalDate localDate) {
		this.name = new SimpleStringProperty(name);
		this.localDate = localDate;
		this.date = new SimpleStringProperty(localDate.format(dateFormat));
		this.logsList = FXCollections.observableArrayList();
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
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
	
	public void addLog(String name, LocalDate localDate) {
		logsList.add(new Log(name, localDate));
	}
	
	public void addLog(String name, LocalDate localDate, String lifeCycle, String effortCategory, String plan) {
		logsList.add(new Log(name, localDate, lifeCycle, effortCategory, plan));
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

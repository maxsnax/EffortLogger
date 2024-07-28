/*	Author: Max Schumacher, Group W31
 * 	File: Controller.java
 * 	Purpose: A functional project management tool which can add, remove, and edit project data
 * 	Last modication: 04/02/23
 */

package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controller {
	
    /*	=====================================================================================
			Objects, lists, and variables used in Project Table and Log Table
		===================================================================================== */
	// Project Table
    @FXML public TableView<Project> projectTable;				// Contains a list of projects, their names, and dates created
    @FXML public TableColumn<Project, String> nameColumn;		// Contains the name field for projects within the TableView
    @FXML public TableColumn<Project, String> dateColumn;		// Contains the date field for projects within the TableView	
    @FXML private TextField projectName;						// Used to create new projects with name of projectName
    @FXML private Label projectNameDisplay;						// Displays on the right portion of the UI in Log editing bay
    
    // Effort Log Table
    @FXML public TableView<Log> logTable;	    				// Contains the name field for Effort Logs stored within Projects
    @FXML public TableColumn<Log, String> logNameColumn;	    // Contains the name field for projects within the TableView
    @FXML public TableColumn<Log, String> logDateColumn;	    // Contains the date field for projects within the TableView
    @FXML private Label logNameDisplay;							// Used to display the name of the Effort Log on the right side of the UI
    @FXML private TextField logDateField = new TextField();
    @FXML private TextField logStartTime = new TextField();
    @FXML private TextField logFinishTime = new TextField();
    @FXML private TextField logTotalTime = new TextField();
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a");
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    @FXML private ChoiceBox<String> logLifeCycleStepBox;		// 
    @FXML private ChoiceBox<String> logEffortCategoryBox;		//
    @FXML private ChoiceBox<String> logPlanBox;					//
    
    private String[] lifeCycles = { "Problem Understanding", "Conceptual Design Plan", "Requirements",
    								"Conceptual Design", "Conceptual Design Review", "Detailed Design Plan",
    								"Detailed Design/Prototype", "Detailed Design Review", "Implementation Plan",
    								"Test Case Generation", "Solution Specification", "Solution Review", 
    								"Solution Implementation", "Unit/System Test", "Reflection", 
    								"Repository Update", "Planning", "Information Gathering", "Information Understanding",
    								"Verifying", "Outlining", "Drafting", "Finalizing",
    								"Team Meeting", "Coach Meeting", "Stakeholder Meeting"};
    
    private String[] effortCategories = {"Plans", "Deliverables", "Interruptions", "Defects", "Others"};
    
    private String[] plans = {"Project Plan", "Risk Managtement Plan", "Concepual Design Plan", 
    						 "Detailed Design Plan", "Implementation Plan"};
    
    /*	=====================================================================================
			Initializing Project Manager UI
		===================================================================================== */
    public void initialize() {
    	
        /*	=====================================================================================
    			Project Table
    		===================================================================================== */
    	// Set up the columns in the Project Table and their dimensions
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
    	dateColumn.setCellValueFactory((new PropertyValueFactory<Project, String>("date")));
    	nameColumn.setMaxWidth(225);
    	dateColumn.setMaxWidth(72);
    	Comparator<String> dateComparator = Comparator.comparing(
    		    dateString -> LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM/dd/yyyy")));

    	// Set the comparator for the dateColumn
    	dateColumn.setComparator(dateComparator);
    	
    	// Update the Project Table properties and allow name editing
    	projectTable.setPlaceholder(new Label("Projects will appear here when added"));
    	
    	// Load dummy data of Projects
    	projectTable.setItems(getProjects());
    	
    	// Displays the selected project's name on the right side of the client
    	projectTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    		if (newSelection != null) {
    			projectNameDisplay.setText("Project: " + newSelection.getName());
    			
    			logTable.getSelectionModel().clearSelection();
    		} else {
    			projectNameDisplay.setText("Project Name");
    		}
    	});
    	
        /*	=====================================================================================
    			Effort Log Table
    		===================================================================================== */
    	// Set up the columns in the Effort Log Table and their dimensions
    	logNameColumn.setCellValueFactory(new PropertyValueFactory<Log, String>("name"));
    	logDateColumn.setCellValueFactory(new PropertyValueFactory<Log, String>("date"));
    	logNameColumn.setMaxWidth(225);
    	logDateColumn.setMaxWidth(72);
    	
    	// Update the table properties and allow name editing
    	logTable.setPlaceholder(new Label("Select a project to display its logs here.\nClick \"Record a new Effort Log\" to add one"));
    	logNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	// Set the options for life cycle, effort category, and plan Choice Boxes
    	logLifeCycleStepBox.getItems().addAll(lifeCycles);
    	logEffortCategoryBox.getItems().addAll(effortCategories);
    	logPlanBox.getItems().addAll(plans);

    	// Displays the Logs belonging to the selected Project
    	projectTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    		if (newSelection != null) {
    			logTable.setItems(newSelection.getLogs());
    			
    	    	// Reset all the times and selections on the deleted log
    			logNameDisplay.setText("Log: ");
    			logDateField.setText("");
    			logStartTime.setText("");
    			logFinishTime.setText("");
    			logTotalTime.setText("");
    	    	logLifeCycleStepBox.getSelectionModel().clearSelection();
    	    	logEffortCategoryBox.getSelectionModel().clearSelection();
    	    	logPlanBox.getSelectionModel().clearSelection();
    		}
    	});
    	
    	// Displays the name of the selected Log
    	logTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, logSelection) -> {
    		if (logSelection != null) {
    			try {
	    			// Name and Date
	    			logNameDisplay.setText("Log: " + logSelection.getName());
	    			logDateField.setText(logSelection.getDate());
	    			// Start time, finish time, total time
	    			logStartTime.setText(logSelection.getStartTime().format(timeFormat));
	    			logFinishTime.setText(logSelection.getFinishTime().format(timeFormat));
	    			logTotalTime.setText(logSelection.getTotalTime().formatted(timeFormat));
	    			// Lifecycle, effort category, plan
	        		logLifeCycleStepBox.setValue(logSelection.getLifeCycle());
	        		logEffortCategoryBox.setValue(logSelection.getEffortCategory());
	        		logPlanBox.setValue(logSelection.getPlan());
    			} catch (Exception e) {
    				
    			}

    		} else {
    			logNameDisplay.setText("Select a log to view or edit it");
    		}
    	});
    	
    	
        /*	=====================================================================================
    			Handles when life cycles, plans, and effort categories are selected from dropdown
    		===================================================================================== */
    	// Assigns seleted Life Cycle to current Log
    	logLifeCycleStepBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, lifeCycleSelection) -> {
    		if (logTable.getSelectionModel().getSelectedItem() == null) {
    			logLifeCycleStepBox.getSelectionModel().clearSelection();
    			return;
    		}
    		logTable.getSelectionModel().getSelectedItem().setLifeCycle(logLifeCycleStepBox.getValue());
    	});
    	
    	// Assigns seleted Effort Category to current Log
    	logEffortCategoryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, lifeCycleSelection) -> {
    		if (logTable.getSelectionModel().getSelectedItem() == null) {
    			logEffortCategoryBox.getSelectionModel().clearSelection();
    			return;
    		}
    		logTable.getSelectionModel().getSelectedItem().setEffortCategory(logEffortCategoryBox.getValue());
    	});
    	
    	// Assigns seleted Plan to current Log
    	logPlanBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, lifeCycleSelection) -> {
    		if (logTable.getSelectionModel().getSelectedItem() == null) {
    			logPlanBox.getSelectionModel().clearSelection();
    			return;
    		}
    		logTable.getSelectionModel().getSelectedItem().setPlan(logPlanBox.getValue());
    	});
    }
    
    /*	=====================================================================================
     		Add/Delete Projects in Project Table
     	===================================================================================== */
    // Adds a new Project to the list with a custom name and the current date
    public void addProjectButtonPushed() {
    	Project newProject = new Project(projectName.getText(), LocalDate.now());
    	if (projectName.getLength() == 0 || projectName.getLength() > 40) return;
    	
    	// Get all the current projects so we can add a new one to it
    	projectTable.getItems().add(newProject);
    }
    
    // Deletes an existing project from the list
    public void deleteProjectButtonPushed() {
    	Project selectedProject;
    	
    	try {
	    	// Gives us the project that were selected
	    	selectedProject = projectTable.getSelectionModel().getSelectedItem();
	  
	    	ObservableList<Log> allLogs = logTable.getItems();
	    	for (int n = 0; n < selectedProject.getLogs().size(); n++) {
	    		Log log = allLogs.get(n);
	    		selectedProject.deleteLog(log);
	    		allLogs.remove(log);
	    		n--;
	    	}
	    	
	    	projectTable.getItems().remove(selectedProject);
	    	projectTable.getSelectionModel().clearSelection();
	    	logTable.getSelectionModel().clearSelection();
	    	
	    	// Reset all the times and selections on the deleted log
			logNameDisplay.setText("Log: ");
			logDateField.setText("");
			logStartTime.setText("");
			logFinishTime.setText("");
			logTotalTime.setText("");
	    	logLifeCycleStepBox.getSelectionModel().clearSelection();
	    	logEffortCategoryBox.getSelectionModel().clearSelection();
	    	logPlanBox.getSelectionModel().clearSelection();
	    	
	    	
    	} catch (Exception e) {
    		
    	}
    
    }
    
    /*	=====================================================================================
			Add/Delete Logs from Log Table
		===================================================================================== */
    // TODO: When the Record a new log button is pushed it will change the scene to the Effort Log Console
    public void addLogButtonPushed() {
    	
    }
    
    public void deleteLogButtonPushed() {
    	Project currentProject;
    	Log selectedLog;
    	
    	try {
	    	// Gives us the project that were selected
    		currentProject = projectTable.getSelectionModel().getSelectedItem();
	    	selectedLog = logTable.getSelectionModel().getSelectedItem();
	    	
	    	ObservableList<Log> allLogs = logTable.getItems();
	    	currentProject.deleteLog(selectedLog);
	    	allLogs.remove(selectedLog);
	    	
	    	logTable.getSelectionModel().clearSelection();
	    	
			logNameDisplay.setText("Log: ");
			logDateField.setText("");
			// Start time, finish time, total time
			logStartTime.setText("");
			logFinishTime.setText("");
			logTotalTime.setText("");
	    	
	    	logLifeCycleStepBox.getSelectionModel().clearSelection();
	    	logEffortCategoryBox.getSelectionModel().clearSelection();
	    	logPlanBox.getSelectionModel().clearSelection();
    	} catch (Exception e) {
    		
    	}
    }

    /*	=====================================================================================
			Helper functions for the Project Table and Log Table's information displays
		===================================================================================== */
    // Method returns an ObservableList of Project objects
    public ObservableList<Project> getProjects() {
    	ObservableList<Project> projectsObservableList = FXCollections.observableArrayList();

    	Project one = new Project("Test", LocalDate.now());
    	Log log = new Log("Testing Log from Project", LocalDate.now());
    	one.addLog(log);
    	one.addLog("Log Two from Project One", LocalDate.now());
    	projectsObservableList.add(one);
    	
    	Project two = new Project("Proyecto Numero Dos", LocalDate.of(2023, Month.APRIL, 1));
    	two.addLog("Log from Project Two", LocalDate.now());
    	projectsObservableList.add(two);
    	
    	Project three = new Project("Proyecto Numero Tres", LocalDate.of(2022, Month.FEBRUARY, 21));
    	three.addLog("Log from Project Three", LocalDate.now());
    	projectsObservableList.add(three);
    	
    	for (int n = 1; n <= 6; n++) {
    		Project newProject = new Project("Project Placeholder " + n, LocalDate.of(2015 + n, 5 + n, 2 + n));

    		for (int i = 1; i < 5; i++) {
    			Log newLog = new Log("Log PlaceHolder " + i, LocalDate.now());
    			newLog.setStartTime(LocalTime.of(11 + i, 15 + i));
    			newLog.setFinishTime(LocalTime.now());
    			newLog.setLifeCycle(lifeCycles[i - 1]);
    			newLog.setPlan(plans[i - 1]);
    			newLog.setEffortCategory(effortCategories[i - 1]);
    			newProject.addLog(newLog);
    		}
    		projectsObservableList.add(newProject);
    	}
    	
    	return projectsObservableList;
    }
    
    public ObservableList<Log> getEffortLogs() {
    	ObservableList<Log> logsObservableList = FXCollections.observableArrayList();
    	Project currentProject = null;

    	try {
    		currentProject = projectTable.getSelectionModel().getSelectedItem();
    		for (Log EffortLog : currentProject.getLogs()) {
    			logsObservableList.add(EffortLog);

    		}
    	} catch (Exception e){
    		
    	}
    	
    	//Log log = new Log("Test Log", LocalDate.now());
		//logsObservableList.add(log);

    	return logsObservableList;
    }
}

module EffortLoggerPro {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	requires java.logging;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}

module ProtoPhase4 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	requires java.logging;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}

module pat.firstscreen {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens pat.C482 to javafx.fxml;
    exports pat.C482;
}
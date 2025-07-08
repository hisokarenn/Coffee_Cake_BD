module br.edu.ufam {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens br.edu.ufam to javafx.fxml;
    opens br.edu.ufam.controller to javafx.fxml;

    exports br.edu.ufam;
    exports br.edu.ufam.model;
}

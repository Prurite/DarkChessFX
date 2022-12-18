module io.github.prurite.darkchessfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires fr.brouillard.oss.cssfx;
    requires org.kordamp.ikonli.javafx;
    requires MaterialFX;

    opens io.github.prurite.darkchessfx to javafx.fxml;
    opens io.github.prurite.darkchessfx.controllers to javafx.fxml;
    exports io.github.prurite.darkchessfx;
    exports io.github.prurite.darkchessfx.components;
    exports io.github.prurite.darkchessfx.model;
    opens io.github.prurite.darkchessfx.components to javafx.fxml;
}
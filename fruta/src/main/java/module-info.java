module com.frota {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.back to javafx.base; // Permite acesso ao pacote com.back
    opens com.frota to javafx.fxml;
    exports com.frota;
}

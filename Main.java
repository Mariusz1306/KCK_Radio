package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        GridPane layout = new GridPane();
        layout.setHgap(20);
        layout.setPadding(new Insets(10));

        MediaPlayer mp = new MediaPlayer();

        //Slider, którym kontrolujemy "czestotliwosc"
        //Trzeba zrobic z niego pokretlo
        Slider slider1 = new Slider(0,100,0);
        slider1.setPrefHeight(50);
        slider1.setPrefWidth(50);
        slider1.setMajorTickUnit(50);
        slider1.setSnapToTicks(true);
        slider1.getStyleClass().add("knobStyle");

        layout.add(slider1,4,0,3,1);

        //Czestotliwosc, nie mozna jej bezposrednio kontrolowac, trzeba uzyc slider1
        Slider slider2 = new Slider(0,100,0);
        slider2.setDisable(true); //Blokujemy bezposrednia kontrole
        slider2.setMajorTickUnit(50);
        slider2.setSnapToTicks(true);
        layout.add(slider2,0,0,3,1);

        //Jesli zmieni sie slider1, to ustaw ta sama wartosc na slider2
        slider1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {

                slider2.adjustValue(slider1.getValue());
            }
        });

        //Wlacznik
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setMaxHeight(10);
        //Jesli jest wlaczone, to podswietl slider2
        toggleButton.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                if (toggleButton.isSelected())
                    slider2.setStyle("-fx-background-color: yellow");
                else
                    slider2.setStyle("");
            }
        });
        layout.add(toggleButton,0,3);

        Slider volume = new Slider(0,100,0);


        GridPane udskPane = new GridPane();
        ToggleGroup udskGroup = new ToggleGroup();
        RadioButton uButton = new RadioButton("U");
        uButton.setToggleGroup(udskGroup);
        RadioButton dButton = new RadioButton("D");
        dButton.setToggleGroup(udskGroup);
        RadioButton sButton = new RadioButton("S");
        sButton.setToggleGroup(udskGroup);
        RadioButton kButton = new RadioButton("K");
        kButton.setToggleGroup(udskGroup);
        udskPane.addRow(0,uButton,dButton,sButton,kButton);
        layout.add(udskPane, 1,3);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Scene.fxml"));
        AnchorPane test = loader.load();
        layout.add(test, 2,0);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("knob/knobOverlay.css").toExternalForm());


        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

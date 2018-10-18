package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
;

public class Main extends Application {

    public int udsk = 0; //u = 1, d = 2, s = 3, k = 4

    @Override
    public void start(Stage stage) throws Exception{


        GridPane layout = new GridPane();
        layout.setHgap(20);

        Slider slider1 = new Slider(0,100,0);
        slider1.setMajorTickUnit(100);
        slider1.setSnapToTicks(true);
        slider1.setStyle(
                "-fx-opacity: 0.1" //set to 0 to hide it
        );
        layout.add(slider1,4,1,3,1);

        Label freqLabel = new Label("Frequency");
        layout.add(freqLabel, 1,0);

        Slider slider2 = new Slider(0,100,0);
        slider2.setDisable(true);
        slider2.setMajorTickUnit(100);
        slider2.setSnapToTicks(true);
        layout.add(slider2,0,1,3,1);

        slider1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {

                slider2.adjustValue(slider1.getValue());
            }
        });

        ToggleButton powerButton = new ToggleButton("~");
        powerButton.setMaxHeight(10);

        powerButton.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                if (powerButton.isSelected())
                    slider2.setStyle("-fx-background-color: yellow");
                else
                    slider2.setStyle("");
            }
        });
        layout.add(powerButton,0,2);

        GridPane udskGridpane = new GridPane();
        ToggleGroup udsk = new ToggleGroup();
        RadioButton uButton = new RadioButton("U");
        uButton.setToggleGroup(udsk);
        udskGridpane.add(uButton,0,0);
        RadioButton dButton = new RadioButton("D");
        dButton.setToggleGroup(udsk);
        udskGridpane.add(dButton,1,0);
        RadioButton sButton = new RadioButton("S");
        sButton.setToggleGroup(udsk);
        udskGridpane.add(sButton,2,0);
        RadioButton kButton = new RadioButton("K");
        kButton.setToggleGroup(udsk);
        udskGridpane.add(kButton,3,0);

        Label testingLabel = new Label();
        layout.add(testingLabel, 0,3);


        layout.add(udskGridpane, 1,2);

        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.show();
    }

    /*
        Possible slider values: 0, 25, 50, 75, 100

     */

    /*private void play_music(Slider freq, ){

    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
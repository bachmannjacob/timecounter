package resources;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToggleSwitch extends Parent {

    private static Color stroke = Color.web("#333333");
    private static Color armed = Color.web("#666666");
    private static Color accent = Color.web("#498205");
    private static Color hover = Color.web("#80a850");

    private BooleanProperty selected = new SimpleBooleanProperty(false);

    private TranslateTransition trnslt = new TranslateTransition(Duration.millis(70));

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public ToggleSwitch() {
        this(43.0, 18.5);
    }

    public ToggleSwitch(Double width, Double height) {
        Rectangle background = new Rectangle(width, height);
        background.setArcWidth(height);
        background.setArcHeight(height);
        background.setFill(Color.WHITE);
        background.setStroke(stroke);
        background.setStrokeWidth(2.0);

        Circle trigger = new Circle(height / 3.7);
        trigger.setCenterX(height / 2);
        trigger.setCenterY(height / 2);
        trigger.setFill(stroke);

        trnslt.setNode(trigger);

        getChildren().addAll(background, trigger);

        selected.addListener((v, oldValue, newValue) -> {
            trnslt.setToX(
                    newValue.booleanValue() ? width - height : 0
            );
            trnslt.play();

            if(newValue.booleanValue()) {
                background.setFill(hover);
                background.setStroke(hover);
                trigger.setFill(Color.WHITE);
            } else {
                background.setFill(Color.WHITE);
                background.setStroke(stroke);
                trigger.setFill(stroke);
            }

        });

        setOnMouseClicked( event -> selected.set(!selected.get()) );

        setOnMousePressed( event -> {
            background.setFill(armed);
            background.setStroke(armed);
            trigger.setFill(Color.WHITE);
        });

        setOnMouseReleased( event -> {
            background.setFill(Color.WHITE);
            background.setStroke(stroke);
            trigger.setFill(stroke);
        });

        setOnMouseDragged( event -> {

        });

        hoverProperty().addListener( (v, oldValue, newValue) -> {
            System.out.println(newValue.booleanValue());

            if(newValue) {
                if(!selected.get()) {
                    background.setStroke(Color.BLACK);
                    trigger.setFill(Color.BLACK);
                } else {
                    background.setFill(hover);
                    background.setStroke(hover);
                }
            } else {
                if(!selected.get()) {
                    background.setStroke(stroke);
                    trigger.setFill(stroke);
                } else {
                    background.setFill(accent);
                    background.setStroke(accent);
                }
            }



        });

    }

}

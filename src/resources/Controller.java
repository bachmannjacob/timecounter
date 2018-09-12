package resources;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML VBox root;

    @FXML Region rgn_time;
    @FXML Region rgn_pause;
    @FXML Region rgn_start;
    @FXML Region rgn_end;

    @FXML Label lbl_TIME;
    @FXML Label lbl_PAUSE;
    @FXML Label lbl_START;
    @FXML Label lbl_END;

    @FXML Separator sprtr_time;
    @FXML Separator sprtr_pause;
    @FXML Separator sprtr_start;
    @FXML Separator sprtr_end;

    @FXML Label lbl_time;
    @FXML Label lbl_pause;
    @FXML Label lbl_start;
    @FXML Label lbl_end;

    @FXML ToggleSwitch tgglswtch;
    @FXML Button btn_pause;
    @FXML Button btn_start;

    private final Double vspace_small = 10.0;
    private final Double vspace_big = 15.0;
    private final Double hspace_small = 80.0;
    private final Double hspace_big = 160.0;

    private Timeline timeline_t;
    private Timeline timeline_p;
    private Integer timer_t = 0;
    private Integer timer_p = 0;

    private String time_t = "0:00:00";
    private String time_p = "0:00:00";
    private String time_start = "0:00:00";
    private String time_stop = "0:00:00";

    private int cut = 3;

    private boolean started = false;
    private boolean paused = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRegion();
        setRegionHalfStep();
        setToggleSwitch();
        setTimelineSprtr();
        initTime();
        initPause();

    }

    private void setRegion() {
        for(Node hregion : root.lookupAll("#hregion")) {
            HBox.setHgrow(hregion, Priority.ALWAYS);
        }

        for(Node vregion : root.lookupAll("#vregion")) {
            VBox.setVgrow(vregion, Priority.ALWAYS);
        }
    }

    private void setRegionHalfStep() {
        setListenerRegion(rgn_time, vspace_big, 3);
        setListenerRegion(rgn_pause, vspace_big, 3);
        setListenerRegion(rgn_start, vspace_small, 9);
        setListenerRegion(rgn_end, vspace_small, 9);
    }

    private void setToggleSwitch() {
        tgglswtch.selectedProperty().addListener( (v, oldValue, newValue) -> {

            cut = (newValue.booleanValue()) ? 0 : 3;

            if(time_t != null) { lbl_time.setText(time_t.substring(0, time_t.length()-cut)); }
            if(time_p != null) { lbl_pause.setText(time_p.substring(0, time_p.length()-cut)); }
            if(time_start != null) { lbl_start.setText(time_start.substring(0, time_start.length()-cut)); }
            if(time_stop != null) { lbl_end.setText(time_stop.substring(0, time_stop.length()-cut)); }
        });
    }

    private void setListenerRegion(Region region, Double max, int step) {
        region.setMaxHeight(max);
        root.heightProperty().addListener((a, b, c) -> {
            region.setPrefHeight((root.getHeight() - 231.0)/step);
        });
    }

    private void setTimelineSprtr() {

        Timeline timeline_sep = new Timeline();
        timeline_sep.setCycleCount(Timeline.INDEFINITE);
        timeline_sep.getKeyFrames().add(
                new KeyFrame(Duration.millis(0.1),
                        event -> {
                            generateWidth();
                        }
                )
        );
        timeline_sep.playFromStart();

    }

    public void startClicked() {

        if(!started) {
            btn_start.setText("Stop");
            time_start = getTime();
            lbl_start.setText(time_start.substring(0, time_start.length()-cut));
            lbl_start.setDisable(false);
            lbl_time.setDisable(false);
        } else {
            btn_start.setText("Start");
            btn_pause.setText("Pause");
            time_stop = getTime();
            lbl_end.setText(time_stop.substring(0, time_stop.length()-cut));
            lbl_end.setDisable(false);
            paused = false;
        }

        btn_pause.setDisable(started);
        started = !started;

    }

    public void pauseClicked() {
        if(!paused) {
            btn_pause.setText("Continue");
            lbl_pause.setDisable(false);
        } else {
            btn_pause.setText("Pause");
        }
        paused = !paused;
    }

    private void initTime() {

        if(timeline_t != null) {
            timeline_t.stop();
        }

        timeline_t = new Timeline();
        timeline_t.setCycleCount(Timeline.INDEFINITE);
        timeline_t.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            if (started && !paused) {
                                timer_t++;
                                time_t = secToStr(timer_t);
                                lbl_time.setText(time_t.substring(0, time_t.length()-cut));
                            }
                        }
                )
        );
        timeline_t.playFromStart();

    }

    private void initPause() {

        if(timeline_p != null) {
            timeline_p.stop();
        }

        timeline_p = new Timeline();
        timeline_p.setCycleCount(Timeline.INDEFINITE);
        timeline_p.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            if (paused) {
                                timer_p++;
                                time_p = secToStr(timer_p);
                                lbl_pause.setText(time_p.substring(0, time_p.length()-cut));
                            }
                        }
                )
        );
        timeline_p.playFromStart();

    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    private void generateWidth() {
        generateWidth(340.0, 400.0, hspace_small, sprtr_time, lbl_time, lbl_TIME);
        generateWidth(340.0, 400.0, hspace_small, sprtr_pause, lbl_pause, lbl_PAUSE);
        generateWidth(340.0, 400.0, hspace_big, sprtr_start, lbl_start, lbl_START);
        generateWidth(340.0, 400.0, hspace_big, sprtr_end, lbl_end, lbl_END);
    }

    private void generateWidth(Double min, Double max, Double space, Separator sprtr, Label lbl_right, Label lbl_left) {

        Double availableWidth = (root.getWidth() < min) ? min : root.getWidth();

        Double width_line = availableWidth - space;
        if(width_line > max) { width_line = max; }
        double width = width_line - lbl_right.getWidth() - lbl_left.getWidth();

        sprtr.setPrefWidth( width );

    }

    private String secToStr(int seconds) {
        StringBuilder result = new StringBuilder();

        List<Integer> list = new ArrayList<>();

        list.add( seconds/3600 );
        list.add( (seconds/60)%60 );
        list.add( seconds%60 );

        for(int num : list) {
            result.append((num < 10) ? "0" + Integer.toString(num) + ":" : Integer.toString(num) + ":");
        }

        return result.substring( (result.charAt(0) == '0') ? 1 : 0 , result.length()-1);
    }

}

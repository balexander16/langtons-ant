package edu.cnm.deepdive.langton.controller;

import edu.cnm.deepdive.langton.model.Terrain;
import edu.cnm.deepdive.langton.view.TerrainView;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class MainController {

  @FXML
  private Slider speed;
  @FXML
  private TerrainView terrainView;
  @FXML
  private ToggleButton runToggle;
  @FXML
  private Slider populationSize;
  private boolean running;
  private Terrain terrain;
  private AnimationTimer timer;


  @FXML
  private void initialize() {
    terrain = new Terrain(2, new Random());
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        terrainView.draw(terrain.getPatches());
      }
    };
  }

  @FXML
  private void toggleRun(ActionEvent actionEvent) {
    if (runToggle.isSelected()) {
      start();
    } else {
      stop();
    }
  }

  private void start() {
    running = true;
    timer.start();
    new Runner().start();
  }

  public void stop() {
    runToggle.setDisable(true);
    running = false;
    timer.stop();

  }


  private class Runner extends Thread {

    @Override
    public void run() {
      while (running) {
        for (int i = 0; i <= speed.getValue(); i ++) {        // I like the effect utilizing the speed slider here best so far
          terrain.tick();
        }
        try {
          Thread.sleep((10 - (long)speed.getValue()) + 1);    // tried a few different things. I like this. so starts at 10 goes down to 1
        } catch (InterruptedException e) {                          // this is decent... not too fast at 10 and not too slow at 1.
          // Do nothing! Duh.
        }
      }
      Platform.runLater(() -> runToggle.setDisable(false));
    }

  }

}

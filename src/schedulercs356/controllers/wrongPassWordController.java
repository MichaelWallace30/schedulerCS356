/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Eggs
 */
public class wrongPassWordController implements Initializable {
    String soundFile = "src/schedulercs356/funstuff/jurass01.mp3";
    
    @FXML
    ImageView imageView1;
    @FXML
    ImageView imageView2;
    @FXML
    Label topLabel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RotateTransition rt = new RotateTransition(Duration.millis(300), imageView2);
        rt.setByAngle(-30);
        
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setAutoReverse(true);
        rt.play();
        
        File soundF = new File(soundFile);
       
        if (soundF.exists()) {
          Media sound = new Media(soundF.toURI().toString());
          MediaPlayer mediaPlayer = new MediaPlayer(sound);
          mediaPlayer.play();
        } else {
          System.out.println(soundF.getAbsolutePath().toString());
          throw new RuntimeException("File does not exist");
        }
    }    
    
    
    private Timeline createBlinker(Node node) {
        Timeline blink = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0.5),
                        new KeyValue(
                                node.opacityProperty(), 
                                0, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(
                                node.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                )
        );
        blink.setCycleCount(3);

        return blink;
    }
    
}

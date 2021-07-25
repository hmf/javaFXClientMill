package org.modernclient

// cSpell:ignore javafx, Initializable

import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.beans.binding.When
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.util.Duration

import java.net.URL
import java.util.ResourceBundle

class FXMLController extends Initializable {

    @FXML
    private var stackPane: StackPane = _
    @FXML
    private var  text2: Text = _ 
    private var rotate: RotateTransition = _

    override def initialize(url: URL, rb: ResourceBundle) = {
        rotate = new RotateTransition(Duration.millis(2500), stackPane)
        rotate.setToAngle(360)
        rotate.setFromAngle(0)
        rotate.setInterpolator(Interpolator.LINEAR)

        rotate.statusProperty().addListener((observableValue, oldValue, newValue) => {
            text2.setText("Was " + oldValue + ", Now " + newValue)
        })

        text2.strokeProperty().bind(new When(rotate.statusProperty()
                .isEqualTo(Animation.Status.RUNNING)).`then`(Color.GREEN).otherwise(Color.RED))
    }

    @FXML
    private def handleMouseClick(mouseEvent: MouseEvent) = {
        if (rotate.getStatus().equals(Animation.Status.RUNNING)) {
            rotate.pause()
        } else {
            rotate.play()
        }
    }    
}

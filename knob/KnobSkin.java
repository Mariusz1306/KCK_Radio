package sample.knob;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;  
import java.util.Optional;  
import javafx.event.EventHandler;  
import javafx.scene.control.Slider;  
import javafx.scene.input.MouseEvent;  
import javafx.scene.layout.StackPane;  
  

@SuppressWarnings("restriction")
public class KnobSkin extends BehaviorSkinBase<Slider, KnobBehavior> {
  
    private double knobRadius;  
    private double minAngle = 90;
    private double maxAngle = 270;
    private double dragOffset;  
  
    private StackPane knob;  
    private StackPane knobOverlay;  
    private StackPane knobDot;  
  
    public KnobSkin(Slider slider) {  
        super(slider, new KnobBehavior(slider));  
        initialize();  
        requestLayout();  
        registerChangeListener(slider.minProperty(), "MIN");  
        registerChangeListener(slider.maxProperty(), "MAX");  
        registerChangeListener(slider.valueProperty(), "VALUE");  
    }  
  
    private void initialize() {  
        knob = new StackPane() {  
            @Override  
            protected void layoutChildren() {  
                knobDot.autosize();  
                knobDot.setLayoutX((knob.getWidth() - knobDot.getWidth()) / 2);  
                knobDot.setLayoutY(5 + (knobDot.getHeight() / 2));  
            }  
  
        };  
        knob.getStyleClass().setAll("sample/knob");
        knobOverlay = new StackPane();  
        knobOverlay.getStyleClass().setAll("knobOverlay");  
        knobDot = new StackPane();  
        knobDot.getStyleClass().setAll("knobDot");  
  
        getChildren().setAll(knob, knobOverlay);  
        knob.getChildren().add(knobDot);  
  
        getSkinnable().setOnMousePressed(new EventHandler<MouseEvent>() {  
            @Override  
            public void handle(MouseEvent me) {  
                double dragStart = mouseToValue(me.getX(), me.getY());  
                double zeroOneValue = (getSkinnable().getValue() - getSkinnable().getMin()) / (getSkinnable().getMax() - getSkinnable().getMin());  
                dragOffset = zeroOneValue - dragStart;  
                getBehavior().knobPressed(me, dragStart);  
            }  
        });  
        getSkinnable().setOnMouseReleased(new EventHandler<MouseEvent>() {  
            @Override  
            public void handle(MouseEvent me) {  
                getBehavior().knobRelease(me, mouseToValue(me.getX(), me.getY()));  
            }  
        });  
        getSkinnable().setOnMouseDragged(new EventHandler<MouseEvent>() {  
            @Override  
            public void handle(MouseEvent me) {  
                getBehavior().knobDragged(me, mouseToValue(me.getX(), me.getY()) + dragOffset);  
            }  
        });  
    }  
  
    private double mouseToValue(double mouseX, double mouseY) {  
        double cx = getSkinnable().getWidth() / 2;  
        double cy = getSkinnable().getHeight() / 2;  
        double mouseAngle = Math.toDegrees(Math.atan((mouseY - cy) / (mouseX - cx)));  
        double topZeroAngle;  
        if (mouseX < cx) {  
            topZeroAngle = 90 - mouseAngle;  
        } else {  
            topZeroAngle = -(90 + mouseAngle);  
        }  
        double value = 1 - ((topZeroAngle - minAngle) / (maxAngle - minAngle));  
        return value;  
    }  
  
    @Override  
    protected void handleControlPropertyChanged(String p) {  
        super.handleControlPropertyChanged(p);  
        requestLayout();  
    }  
  
    void rotateKnob() {  
        Slider s = getSkinnable();  
        double zeroOneValue = (s.getValue() - s.getMin()) / (s.getMax() - s.getMin());  
        double angle = minAngle + ((maxAngle - minAngle) * zeroOneValue);  
        knob.setRotate(angle);  
    }  
  
    private void requestLayout() {  
        final Optional<Slider> slider = Optional.ofNullable((Slider) getNode());  
        slider.ifPresent(s -> s.requestLayout());  
    }  
  
    @Override  
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {  
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);  
        // calculate the available space  
        double x = contentX;  
        double y = contentY;  
        double w = contentWidth;  
        double h = contentHeight;  
        double cx = x + (w / 2);  
        double cy = y + (h / 2);  
  
        // resize thumb to preferred size  
        double knobWidth = knob.prefWidth(-1);
        double knobHeight = knob.prefHeight(-1);
        knobRadius = Math.max(knobWidth, knobHeight) / 2;  
        knob.resize(knobWidth, knobHeight);  
        knob.setLayoutX(cx - knobRadius);  
        knob.setLayoutY(cy - knobRadius);  
        knobOverlay.resize(knobWidth, knobHeight);  
        knobOverlay.setLayoutX(cx - knobRadius);  
        knobOverlay.setLayoutY(cy - knobRadius);  
        rotateKnob();  
    }  
  
    @Override  
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return (leftInset + knob.minWidth(-1) + rightInset);  
    }  
  
    @Override  
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return (topInset + knob.minHeight(-1) + bottomInset);  
    }  
  
    @Override  
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return (leftInset + knob.prefWidth(-1) + rightInset);  
    }  
  
    @Override  
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return (topInset + knob.prefHeight(-1) + bottomInset);  
    }  
  
    @Override  
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return Double.MAX_VALUE;  
    }  
  
    @Override  
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {  
        return Double.MAX_VALUE;  
    }  
}  

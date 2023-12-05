package com.ballsteam.sokiduels.player;

import com.github.strikerx3.jxinput.*;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;


public class ControllerInput extends PlayerInput {
    private final double DEAD_ZONE = 0.133;
    private final double TRIGGER_DEAD_ZONE = 0.3;
    private final XInputDevice DEVICE;
    private final XInputAxes AXES;
    private final XInputButtons BUTTONS;
    private final XInputButtonsDelta BUTTONS_DELTA;
    private boolean leftTriggerFuse = true;
    private boolean rightTriggerFuse = true;
    public boolean LT;
    public boolean LB;
    public boolean RT;
    public boolean RB;
    public ControllerInput(int playerNum) throws XInputNotLoadedException {
        DEVICE = XInputDevice.getDeviceFor(playerNum);
        AXES = DEVICE.getComponents().getAxes();
        BUTTONS = DEVICE.getComponents().getButtons();
        BUTTONS_DELTA = DEVICE.getDelta().getButtons();
    }
    @Override
    public void update(){
        poll();
        setInputValues();
    }
    @Override
    public boolean interacted(){
        poll();
        return !hasPlayer && anyPressed();
    }
    private boolean anyPressed(){
        return
            BUTTONS.b||BUTTONS.a||BUTTONS.x||BUTTONS.y|| BUTTONS.rShoulder || BUTTONS.lShoulder ||
                valueDeltaY()!=0 || valueDeltaX()!=0 || AXES.lt>= TRIGGER_DEAD_ZONE || AXES.rt>= TRIGGER_DEAD_ZONE;
    }
    private void setInputValues() {
        A = BUTTONS_DELTA.isPressed(XInputButton.A);
        B = BUTTONS_DELTA.isPressed(XInputButton.B);
        leftRight();
        upDown();
        triggers();
    }
    private void poll(){
        DEVICE.poll();}
    private void leftRight(){
        if (valueDeltaX()>0){
            RIGHT = valueDeltaX();
            LEFT = 0;
        } else if (valueDeltaX()<0) {
            LEFT = -valueDeltaX();
            RIGHT = 0;
        } else {
            LEFT = 0;
            RIGHT = 0;
        }
    }
    private void upDown(){
        if (valueDeltaY()>0){
            UP = valueDeltaY();
            DOWN = 0;
        } else if (valueDeltaY()<0) {
            DOWN = -valueDeltaY();
            UP = 0;
        } else {
            UP = 0;
            DOWN = 0;
        }
    }

    private void triggers(){
        LB = BUTTONS_DELTA.isPressed(XInputButton.LEFT_SHOULDER);

        LT = leftTriggerFuse && AXES.lt>= TRIGGER_DEAD_ZONE;
        leftTriggerFuse = !RT && AXES.rt< TRIGGER_DEAD_ZONE;

        RB = BUTTONS_DELTA.isPressed(XInputButton.RIGHT_SHOULDER);

        RT = rightTriggerFuse && AXES.rt >= TRIGGER_DEAD_ZONE;
        rightTriggerFuse = !RT && AXES.rt < TRIGGER_DEAD_ZONE;
    }
    private float valueDeltaX(){return dPadX()!=0? dPadX():leftStickX();}
    private float valueDeltaY(){return dPadY()!=0? dPadY():leftStickY();}
    private float dPadY(){return Boolean.compare(BUTTONS.up, BUTTONS.down);}
    private float dPadX(){return Boolean.compare(BUTTONS.right, BUTTONS.left);}
    private float leftStickX() {return Math.abs(AXES.lx) > DEAD_ZONE ? AXES.lx : 0;}
    private float leftStickY() {return Math.abs(AXES.ly) > DEAD_ZONE ? AXES.ly : 0;}
}

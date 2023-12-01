package com.ballsteam.sokiduels.player;

import com.github.strikerx3.jxinput.*;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;


public class ControllerInput extends PlayerInput {
    private final double DEAD_ZONE = 0.133;
    private final XInputDevice DEVICE;
    private final XInputAxes AXES;
    private final XInputButtons BUTTONS;
    private final XInputButtonsDelta BUTTONS_DELTA;
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
        return anyPressed();
    }
    public void setVibration(int leftMotor, int rightMotor){
        //determina el valor usando el siguiente criterio:
        //El número mayor entre 0 y (el número menor entre 65535 y la entrada)
        DEVICE.setVibration(margin(leftMotor),margin(rightMotor));
    }
    private boolean anyPressed(){
        return BUTTONS.b|| BUTTONS.a|| BUTTONS.right|| BUTTONS.left||leftStickX()!=0;
    }
    private void setInputValues() {
        A = BUTTONS_DELTA.isPressed(XInputButton.A);
        B = BUTTONS_DELTA.isPressed(XInputButton.B);
        LEFT_RIGHT = valueDeltaX();
        UP_DOWN = valueDeltaY();
    }
    private void poll(){
        DEVICE.poll();}
    private int margin(int motorValue){return Math.max(Math.min(motorValue,65535),0);}
    private float valueDeltaX(){return dPadX()!=0? dPadX():leftStickX();}
    private float valueDeltaY(){return dPadY()!=0? dPadY():leftStickY();}
    private int dPadX(){return Boolean.compare(BUTTONS.right, BUTTONS.left);}
    private int dPadY(){return Boolean.compare(BUTTONS.up, BUTTONS.down);}
    private float leftStickX() {return Math.abs(AXES.lx) > DEAD_ZONE ? AXES.lx : 0;}
    private float leftStickY(){return Math.abs(AXES.ly)>DEAD_ZONE? AXES.ly:0;}
}

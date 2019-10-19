package entity;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;


public class Player extends Entity{

    private static final float RUN_SPEED = 20; // unit per second
    private static final float TURN_SPEED = 160; // degree per second
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY()))); // get next location of player along X axis
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY()))); // get next location of player along Z axis
        super.increasePosition(dx, 0, dz);
        upwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardSpeed * DisplayManager.getFrameTimeSeconds(), 0);

        if (super.getPosition().y<TERRAIN_HEIGHT){
            upwardSpeed = 0;
            super.getPosition().y = TERRAIN_HEIGHT;
            isInAir = false;
        }

    }

    private void jump(){
        if (!isInAir) {
            this.upwardSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = RUN_SPEED;

        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = - RUN_SPEED;
        }
        else{
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentTurnSpeed = - TURN_SPEED;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.currentTurnSpeed =  TURN_SPEED;
        }
        else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            jump();
        }
    }
}

package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private static final float TERRAIN_HEIGHT = 0;

    private Vector3f position = new Vector3f(0,5,0);
    private float pitch = 20;
    private float yaw ;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(){

        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);

        /* Temporarily disabled for player
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
            position.z -= 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            position.z += 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            position.x += 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            position.x -= 1f;
        }
       if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)){
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)){
            if (position.y > 0.5f) {
                position.y -= 0.2f;
            }
        }
        */
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void  calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f; // change 0.1f  this to change sensitivity
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch(){
        if (Mouse.isButtonDown(1)){  //  TODO: can not go beyond texture
            float pitchChange = Mouse.getDY() * 0.1f; // change 0.1f  this to change sensitivity
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer(){
        if (Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * 0.3f; // change 0.3f  this to change sensitivity
            angleAroundPlayer -= angleChange;
        }
    }
}

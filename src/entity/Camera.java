package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0,5,0);
    private float pitch = 10;
    private float yaw ;
    private float roll;

    public Camera(){}

    public void move(){

        /* Temporarily disabled for player */
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
}

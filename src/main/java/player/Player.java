package player;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

import world.TerrainGenerator;

public class Player implements ActionListener {

    private final SimpleApplication game;
    private final TerrainGenerator terrain;

    private boolean forward;
    private boolean backward;
    private boolean left;
    private boolean right;

    private boolean grounded;
    private float verticalVelocity = 0f;

    private final float moveSpeed = 8f;
    private final float gravity = 24f;
    private final float jumpStrength = 9f;

    // Camera height above the ground.
    private final float eyeHeight = 1.7f;

    public Player(SimpleApplication game) {
        this.game = game;
        this.terrain = new TerrainGenerator();
    }

    public void create() {

        // Keep JME's mouse-look.
        game.getFlyByCamera().setEnabled(true);

        // Stop FlyByCamera from moving us itself.
        game.getFlyByCamera().setMoveSpeed(0f);

        setupInput();

        // Spawn at the center of the island.
        int groundHeight =
            terrain.getHeight(0, 0);

        float spawnY =
            groundHeight + eyeHeight - 0.5f;

        game.getCamera().setLocation(
            new Vector3f(
                0f,
                spawnY,
                0f
            )
        );
    }

    private void setupInput() {

        game.getInputManager().addMapping(
            "PlayerForward",
            new KeyTrigger(KeyInput.KEY_W)
        );

        game.getInputManager().addMapping(
            "PlayerBackward",
            new KeyTrigger(KeyInput.KEY_S)
        );

        game.getInputManager().addMapping(
            "PlayerLeft",
            new KeyTrigger(KeyInput.KEY_A)
        );

        game.getInputManager().addMapping(
            "PlayerRight",
            new KeyTrigger(KeyInput.KEY_D)
        );

        game.getInputManager().addMapping(
            "PlayerJump",
            new KeyTrigger(KeyInput.KEY_SPACE)
        );

        game.getInputManager().addListener(
            this,
            "PlayerForward",
            "PlayerBackward",
            "PlayerLeft",
            "PlayerRight",
            "PlayerJump"
        );
    }

    @Override
    public void onAction(
        String name,
        boolean pressed,
        float tpf
    ) {

        switch (name) {

            case "PlayerForward":
                forward = pressed;
                break;

            case "PlayerBackward":
                backward = pressed;
                break;

            case "PlayerLeft":
                left = pressed;
                break;

            case "PlayerRight":
                right = pressed;
                break;

            case "PlayerJump":

                if (pressed && grounded) {

                    verticalVelocity =
                        jumpStrength;

                    grounded = false;
                }

                break;
        }
    }

    public void update(float tpf) {

        move(tpf);
        applyGravity(tpf);
        keepOnGround();
    }

    private void move(float tpf) {

        Vector3f direction =
            game.getCamera()
                .getDirection()
                .clone();

        // Don't let looking up/down affect walking.
        direction.y = 0f;

        if (direction.lengthSquared() > 0f) {
            direction.normalizeLocal();
        }

        Vector3f cameraLeft =
            game.getCamera()
                .getLeft()
                .clone();

        cameraLeft.y = 0f;

        if (cameraLeft.lengthSquared() > 0f) {
            cameraLeft.normalizeLocal();
        }

        Vector3f movement =
            new Vector3f();

        if (forward) {
            movement.addLocal(direction);
        }

        if (backward) {
            movement.subtractLocal(direction);
        }

        if (left) {
            movement.addLocal(cameraLeft);
        }

        if (right) {
            movement.subtractLocal(cameraLeft);
        }

        if (movement.lengthSquared() > 0f) {

            movement.normalizeLocal();

            movement.multLocal(
                moveSpeed * tpf
            );

            game.getCamera()
                .getLocation()
                .addLocal(movement);
        }
    }

    private void applyGravity(float tpf) {

        verticalVelocity -=
            gravity * tpf;

        game.getCamera()
            .getLocation()
            .y +=
            verticalVelocity * tpf;
    }

    private void keepOnGround() {

        Vector3f position =
            game.getCamera()
                .getLocation();

        float x = position.x;
        float z = position.z;

        // Find the four terrain points
        // surrounding the player.
        int x0 =
            (int) Math.floor(x);

        int z0 =
            (int) Math.floor(z);

        float xFraction =
            x - x0;

        float zFraction =
            z - z0;

        float h00 =
            terrain.getHeight(
                x0,
                z0
            );

        float h10 =
            terrain.getHeight(
                x0 + 1,
                z0
            );

        float h01 =
            terrain.getHeight(
                x0,
                z0 + 1
            );

        float h11 =
            terrain.getHeight(
                x0 + 1,
                z0 + 1
            );

        // Interpolate between the two
        // points along the X direction.
        float h0 =
            h00 +
            (h10 - h00) *
            xFraction;

        float h1 =
            h01 +
            (h11 - h01) *
            xFraction;

        // Interpolate along the Z direction.
        float terrainHeight =
            h0 +
            (h1 - h0) *
            zFraction;

        if (terrainHeight <= 0) {
            return;
        }

        float groundY =
            terrainHeight
            + eyeHeight
            - 0.5f;

        if (position.y <= groundY) {

            position.y = groundY;

            verticalVelocity = 0f;

            grounded = true;
        }
    }
}
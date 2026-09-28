package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import world.village.buildings.HouseDoors;

public class DoorSystem implements ActionListener {

    private final SimpleApplication game;
    private final List<HouseDoors> doors = new ArrayList<>();

    public DoorSystem(SimpleApplication game) {
        this.game = game;
        game.getInputManager().addMapping(
            "InteractDoor",
            new KeyTrigger(KeyInput.KEY_E)
        );
        game.getInputManager().addListener(
            this, "InteractDoor"
        );
    }

    public void register(HouseDoors door) {
        doors.add(door);
    }

    public void update(float tpf) {
        for (HouseDoors door : doors) {
            door.update(tpf);
        }
    }

    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if (!pressed || !"InteractDoor".equals(name)) {
            return;
        }

        HouseDoors nearest = null;
        float best = 3.6f;
        Vector3f player = game.getCamera().getLocation();

        for (HouseDoors door : doors) {
            float distance = player.distance(door.getWorldPosition());
            if (distance < best) {
                best = distance;
                nearest = door;
            }
        }

        if (nearest != null) {
            nearest.toggle();
        }
    }
}
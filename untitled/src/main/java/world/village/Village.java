package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;

import world.village.buildings.House;
import world.village.buildings.HouseStyle;

public class Village {

    private final SimpleApplication game;

    private final Node villageNode;

    public Village(SimpleApplication game) {

        this.game = game;

        villageNode = new Node("Village");
    }

    public void create() {

        createHouses();

        game.getRootNode().attachChild(
            villageNode
        );
    }

    private void createHouses() {

        // =========================
        // COTTAGE
        // =========================

        House house1 =
            new House(
                game.getAssetManager(),
                new Vector3f(85f, 3f, 85f),
                8f,
                8f,
                5f,
                HouseStyle.COTTAGE
            );

        villageNode.attachChild(
            house1.getNode()
        );

        // =========================
        // MODERN HOUSE
        // =========================

        House house2 =
            new House(
                game.getAssetManager(),
                new Vector3f(105f, 3f, 85f),
                10f,
                8f,
                6f,
                HouseStyle.MODERN
            );

        villageNode.attachChild(
            house2.getNode()
        );

        // =========================
        // FARMHOUSE
        // =========================

        House house3 =
            new House(
                game.getAssetManager(),
                new Vector3f(85f, 3f, 105f),
                7f,
                7f,
                4.5f,
                HouseStyle.FARMHOUSE
            );

        villageNode.attachChild(
            house3.getNode()
        );

        // =========================
        // BLACKSMITH
        // =========================

        House house4 =
            new House(
                game.getAssetManager(),
                new Vector3f(110f, 3f, 110f),
                12f,
                9f,
                6f,
                HouseStyle.BLACKSMITH
            );

        villageNode.attachChild(
            house4.getNode()
        );
    }
}
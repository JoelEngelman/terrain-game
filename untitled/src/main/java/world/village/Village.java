package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;

import world.village.buildings.House;

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

        // House 1
        House house1 =
            new House(
                game.getAssetManager(),
                new Vector3f(85f, 3f, 85f),
                8f,
                8f,
                5f
            );

        villageNode.attachChild(
            house1.getNode()
        );

        // House 2
        House house2 =
            new House(
                game.getAssetManager(),
                new Vector3f(105f, 3f, 85f),
                10f,
                8f,
                6f
            );

        villageNode.attachChild(
            house2.getNode()
        );

        // House 3
        House house3 =
            new House(
                game.getAssetManager(),
                new Vector3f(85f, 3f, 105f),
                7f,
                7f,
                4.5f
            );

        villageNode.attachChild(
            house3.getNode()
        );

        // House 4
        House house4 =
            new House(
                game.getAssetManager(),
                new Vector3f(110f, 3f, 110f),
                12f,
                9f,
                6f
            );

        villageNode.attachChild(
            house4.getNode()
        );
    }
}
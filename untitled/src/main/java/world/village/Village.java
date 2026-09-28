package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import world.village.buildings.House;
import world.village.buildings.HouseStyle;

public class Village {

    private final SimpleApplication game;
    private final Node villageNode=new Node("Village");
    private final DoorSystem doors;
    private final VillageEnvironment environment;
    private final VillagePeople people;
    private final GrassField grass;

    public Village(SimpleApplication game){
        this.game=game;
        doors=new DoorSystem(game);
        environment=new VillageEnvironment(game);
        people=new VillagePeople(game);
        grass=new GrassField(game);
    }

    public void create(){
        createHouses();
        environment.create();
        people.create();
        grass.create();
        game.getRootNode().attachChild(villageNode);
    }

    private void createHouses(){
        addHouse(new Vector3f(86f,3f,86f),11f,10f,5.6f,HouseStyle.MODERN);
        addHouse(new Vector3f(108f,3f,86f),13f,9f,6.2f,HouseStyle.SHOP);
        addHouse(new Vector3f(86f,3f,108f),10f,12f,5.4f,HouseStyle.FARMHOUSE);
        addHouse(new Vector3f(111f,3f,110f),14f,11f,6.5f,HouseStyle.BLACKSMITH);
        addHouse(new Vector3f(61f,3f,91f),9f,9f,5.2f,HouseStyle.COTTAGE);
    }

    private void addHouse(
        Vector3f position,
        float width,
        float depth,
        float height,
        HouseStyle style
    ){
        House house=new House(
            game,position,width,depth,height,style,doors
        );
        villageNode.attachChild(house.getNode());
    }

    public void update(float tpf){
        doors.update(tpf);
        people.update(tpf);
        environment.update(tpf);
        grass.update(tpf);
    }
}
import com.jme3.app.SimpleApplication;
import world.World;
import world.graphics.Graphics;
import player.Player;

public class Main extends SimpleApplication {

    private World world;
    private Player player;
    private Graphics graphics;

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }

    @Override
    public void simpleInitApp() {

        graphics = new Graphics(this);
        world = new World(this);
        player = new Player(this);

        graphics.create();
        world.create();
        player.create();
    }

    @Override
    public void simpleUpdate(float tpf) {
        player.update(tpf);
        graphics.update();
    }
}
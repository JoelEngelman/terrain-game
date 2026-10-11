import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.system.AppSettings;

import player.Player;
import world.World;
import world.graphics.Graphics;
import world.ui.SettingsMenu;

public class Main extends SimpleApplication implements ActionListener {

    private World world;
    private Player player;
    private Graphics graphics;
    private SettingsMenu settingsMenu;

    public static void main(String[] args) {
        Main game = new Main();

        AppSettings settings = new AppSettings(true);
        settings.setTitle("Untitled — Island");
        settings.setResolution(1280, 720);
        settings.setFullscreen(true);
        settings.setVSync(true);
        settings.setSamples(4);

        game.setSettings(settings);
        game.setShowSettings(false);
        game.start();
    }

    @Override
    public void simpleInitApp() {
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        inputManager.addMapping(
            "OpenSettings",
            new KeyTrigger(KeyInput.KEY_ESCAPE)
        );
        inputManager.addListener(this, "OpenSettings");

        setDisplayFps(false);
        setDisplayStatView(false);

        graphics = new Graphics(this);
        world = new World(this);
        player = new Player(this);
        settingsMenu = new SettingsMenu(this);

        graphics.create();
        world.create();
        player.create();
        settingsMenu.create();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (!settingsMenu.isOpen()) {
            player.update(tpf);
            world.update(tpf);
            graphics.update(tpf);
        } else {
            settingsMenu.update(tpf);
        }
    }

    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if ("OpenSettings".equals(name) && pressed) {
            settingsMenu.toggle();
        }
    }
}
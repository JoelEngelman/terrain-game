package world.graphics;

import com.jme3.app.SimpleApplication;

public class Graphics {

    private final SimpleApplication game;
    private final Lighting lighting;
    private final Atmosphere atmosphere;

    public Graphics(SimpleApplication game) {
        this.game = game;
        lighting = new Lighting(game);
        atmosphere = new Atmosphere(game);
    }

    public void create() {
        lighting.create();
        atmosphere.create();
    }

    public void update(float tpf) {
        atmosphere.update(tpf);
    }
}
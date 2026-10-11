package world.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;

public class SettingsMenu implements ActionListener {

    private final SimpleApplication game;
    private final Node menu = new Node("Settings Menu");
    private BitmapText title;
    private BitmapText body;
    private boolean open;

    public SettingsMenu(SimpleApplication game) {
        this.game = game;
    }

    public void create() {
        game.getInputManager().addMapping(
            "ToggleFullscreen",
            new KeyTrigger(KeyInput.KEY_F11)
        );
        game.getInputManager().addListener(
            this, "ToggleFullscreen"
        );

        menu.setCullHint(Node.CullHint.Always);

        Geometry panel = new Geometry(
            "Settings Background",
            new Quad(620f, 400f)
        );

        Material panelMaterial = new Material(
            game.getAssetManager(),
            "Common/MatDefs/Misc/Unshaded.j3md"
        );
        panelMaterial.setColor(
            "Color",
            new ColorRGBA(0.035f, 0.045f, 0.055f, 0.96f)
        );
        panel.setMaterial(panelMaterial);
        panel.setLocalTranslation(330f, 160f, 0f);
        menu.attachChild(panel);

        title = new BitmapText(
            game.getAssetManager().loadFont(
                "Interface/Fonts/Default.fnt"
            )
        );
        title.setSize(38f);
        title.setColor(ColorRGBA.White);
        title.setText("SETTINGS");
        title.setLocalTranslation(370f, 500f, 2f);
        menu.attachChild(title);

        body = new BitmapText(
            game.getAssetManager().loadFont(
                "Interface/Fonts/Default.fnt"
            )
        );
        body.setSize(20f);
        body.setColor(new ColorRGBA(0.78f, 0.84f, 0.90f, 1f));
        body.setText(
            "ESC   Resume\n\n" +
            "F11   Toggle Fullscreen\n\n" +
            "WASD  Move\n" +
            "SPACE Jump\n" +
            "E     Interact with doors\n\n" +
            "The island is saved in your current session."
        );
        body.setLocalTranslation(380f, 440f, 2f);
        menu.attachChild(body);

        game.getGuiNode().attachChild(menu);
    }

    public void toggle() {
        open = !open;

        if (open) {
            menu.setCullHint(Node.CullHint.Never);
            game.getFlyByCamera().setEnabled(false);
            game.getInputManager().setCursorVisible(true);
        } else {
            menu.setCullHint(Node.CullHint.Always);
            game.getFlyByCamera().setEnabled(true);
            game.getInputManager().setCursorVisible(false);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void update(float tpf) {
    }

    @Override
    public void onAction(
        String name,
        boolean pressed,
        float tpf
    ) {
        if (!pressed) {
            return;
        }

        if ("ToggleFullscreen".equals(name)) {
            AppSettings settings = game.getContext().getSettings();
            settings.setFullscreen(!settings.isFullscreen());
            game.setSettings(settings);
            game.restart();
        }
    }
}
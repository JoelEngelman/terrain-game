package world.terrain;

public class TerrainIsland {

    private static final float ISLAND_RADIUS =
        TerrainSettings.ISLAND_RADIUS;

    public boolean isLand(int x, int z) {

        float distance = (float) Math.sqrt(
            x * x + z * z
        );

        return distance <= ISLAND_RADIUS;
    }
}
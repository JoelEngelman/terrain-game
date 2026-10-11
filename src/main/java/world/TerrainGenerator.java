package world;

import world.terrain.TerrainFlattener;
import world.terrain.TerrainHeight;
import world.terrain.TerrainRegions;

public class TerrainGenerator {

    private final TerrainHeight terrainHeight = new TerrainHeight();

    private final TerrainRegions terrainRegions = new TerrainRegions();

    private final TerrainFlattener terrainFlattener = new TerrainFlattener(terrainRegions);

    public int getHeight(int x, int z) {

        int originalHeight = terrainHeight.getHeight(x, z);

        return terrainFlattener.flatten(
                x,
                z,
                originalHeight);
    }
}
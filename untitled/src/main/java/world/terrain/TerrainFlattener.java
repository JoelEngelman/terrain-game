package world.terrain;

public class TerrainFlattener {

    private final TerrainRegions regions;

    public TerrainFlattener(TerrainRegions regions) {
        this.regions = regions;
    }

    public int flatten(int x, int z, int originalHeight) {

        TerrainRegions.Region region =
            regions.getRegion(x, z);

        switch (region) {

            case MAIN_VILLAGE:
                return 3;

            case FARM:
                return 3;

            case DOCKS:
                return 2;

            case NPC_AREA:
                return 3;

            case ROAD:
                return 3;

            case FUTURE_TOWN:
                return 3;

            default:
                return originalHeight;
        }
    }
}
package world.terrain;

public class TerrainRegions {

    public enum Region {

        DEFAULT,

        MAIN_VILLAGE,

        FARM,

        DOCKS,

        NPC_AREA,

        ROAD,

        FUTURE_TOWN
    }

    public Region getRegion(int x, int z) {

        // Main village
        if (isInside(
            x,
            z,
            70,
            130,
            70,
            130
        )) {
            return Region.MAIN_VILLAGE;
        }

        // Farm area
        if (isInside(
            x,
            z,
            45,
            85,
            -30,
            20
        )) {
            return Region.FARM;
        }

        // Docks
        if (isInside(
            x,
            z,
            -30,
            30,
            145,
            170
        )) {
            return Region.DOCKS;
        }

        // NPC area
        if (isInside(
            x,
            z,
            -85,
            -45,
            -25,
            25
        )) {
            return Region.NPC_AREA;
        }

        // Road
        if (isInside(
            x,
            z,
            -8,
            8,
            35,
            125
        )) {
            return Region.ROAD;
        }

        // Future town
        if (isInside(
            x,
            z,
            70,
            115,
            55,
            100
        )) {
            return Region.FUTURE_TOWN;
        }

        return Region.DEFAULT;
    }

    private boolean isInside(
        int x,
        int z,
        int minX,
        int maxX,
        int minZ,
        int maxZ
    ) {

        return x >= minX &&
               x <= maxX &&
               z >= minZ &&
               z <= maxZ;
    }
}
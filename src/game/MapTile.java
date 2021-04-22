package game;

public class MapTile {

    private TileType type;
    private boolean walkable;
    private int turnsUntilWalkable; //number of turns until it becomes walkable

    private int controlledBy; // <0 snake1, 0 draw, >0 snake2

    public MapTile(TileType type, boolean walkable, int turnsUntilWalkable){
        this.type = type;
        this.walkable = walkable;
        this.turnsUntilWalkable = turnsUntilWalkable;
        this.controlledBy = 0;
    }

    public boolean isWalkableIn(int turns){
        return turns > turnsUntilWalkable;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int getTurnsUntilWalkable() {
        return turnsUntilWalkable;
    }

    public void setTurnsUntilWalkable(int turnsUntilWalkable) {
        this.turnsUntilWalkable = turnsUntilWalkable;
    }

    public int getControlledBy() {
        return controlledBy;
    }

    public void setControlledBy(int controlledBy) {
        this.controlledBy = controlledBy;
    }

}

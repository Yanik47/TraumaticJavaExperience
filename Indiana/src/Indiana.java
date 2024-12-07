import java.io.IOException;
import java.util.*;

public class Indiana implements Explorer{

    int underWaterMoves = 0;
    PlayerController controller;
    Set<Position> mapa = new HashSet<>();
    Position myPosition = new Position(0, 0);
    int underWater = 0;
    boolean noWater = true;
    boolean umieram = false;
    boolean isExit = false;


    Direction waterDirection = Direction.NORTH;
    @Override
    public void underwaterMovesAllowed(int moves) {
        this.underWaterMoves = (moves+1)/2;
    }

    @Override
    public void setPlayerController(PlayerController controller) {
        this.controller = controller;
    }

    private Direction gdzieWracac(Direction direction){
        switch(direction){
            case NORTH : return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case WEST: return Direction.EAST;
            default: return Direction.WEST;
        }
    }

    private void ground(Position myPosition, Direction direction) throws Exception {

        if(!this.myPosition.equals(myPosition)){
            try {
                moveDirection(direction);
                noWater = true;
                underWater = 0;
            } catch (OnFire e) {
                noWater = true;
                underWater = 0;
                throw new IOException();
            } catch (Flooded e) {
                noWater = false;

                this.waterDirection = direction;

                if(waterDirection == Direction.NORTH || waterDirection == Direction.SOUTH){

                    mapa.add(new Position(myPosition.col()+1, myPosition.row()));
                    mapa.add(new Position(myPosition.col()-1, myPosition.row()));
                }
                else if(waterDirection == Direction.EAST || waterDirection == Direction.WEST){
                    mapa.add(new Position(myPosition.col(), myPosition.row()+1));
                    mapa.add(new Position(myPosition.col(), myPosition.row()-1));
                }

                if(this.underWater < this.underWaterMoves-1){
                    underWater++;
                    System.out.println(underWater);
                    if(underWater == underWaterMoves-2){
                        umieram = true;
                    }
                }
                else{
                    if(umieram) {
                        waterDirection = gdzieWracac(waterDirection);
                        umieram = false;
                    }
                    throw new IOException();
                }

            } catch (Wall e) {
                this.myPosition = gdzieWracac(direction).step(myPosition);
                throw new IOException();

            } catch (Exit e) {
                isExit = true;
                noWater = true;
                underWater = 0;
                throw new Exit();
            }
        }
    }

    private void dfs(Position myPosition, Direction direction) throws Exception{
        if(isExit){
            return;
        }
        try{
            ground(myPosition, direction);
        } catch (IOException e) {
            return;
        }

        if(!noWater){
            if(!mapa.contains(waterDirection.step(myPosition))){
                dfs(waterDirection.step(myPosition), waterDirection);
                try {
                    ground(myPosition, waterDirection);

                } catch (IOException e) {
                    return;
                }
            }
        }
        else {
            if (!mapa.contains(Direction.NORTH.step(myPosition))) {
                dfs(Direction.NORTH.step(myPosition), Direction.NORTH);
            }
            try {
                ground(myPosition, Direction.SOUTH);
            } catch (IOException e) {
                return;
            }
            if (!mapa.contains(Direction.EAST.step(myPosition))) {
                dfs(Direction.EAST.step(myPosition), Direction.EAST);
            }
            try {
                ground(myPosition, Direction.WEST);
            } catch (IOException e) {
                return;
            }
            if (!mapa.contains(Direction.SOUTH.step(myPosition))) {
                dfs(Direction.SOUTH.step(myPosition), Direction.SOUTH);
            }
            try {
                ground(myPosition, Direction.NORTH);
            } catch (IOException e) {
                return;
            }
            if (!mapa.contains(Direction.WEST.step(myPosition))) {
                dfs(Direction.WEST.step(myPosition), Direction.WEST);
            }
            try {
                ground(myPosition, Direction.EAST);
            } catch (IOException ignored) {
            }
        }
    }

    private void moveDirection(Direction direction)throws OnFire, Flooded, Wall, Exit{
        myPosition = direction.step(myPosition);
        mapa.add(myPosition);
        controller.move(direction);
    }
    @Override
    public void findExit() {

        try {
            dfs(myPosition, Direction.NORTH);
        } catch (Exception ignored) {
        }

    }
}

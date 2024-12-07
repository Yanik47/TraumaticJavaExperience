import java.util.HashSet;
import java.util.Set;


public class Controller implements PlayerController {
    Position currentPosition = new Position(4, 3);
    Set<Position> onFireSet = new HashSet<>();
    Set<Position> floodedSet = new HashSet<>();
    Set<Position> exitSet = new HashSet<>();
    Set<Position> wallSet = new HashSet<>();
    Set<Position> repeated = new HashSet<>();

    public void setAll() {
        wallSet.add(new Position(5, 1));
        wallSet.add(new Position(3, 1));
        wallSet.add(new Position(5, 2));
        wallSet.add(new Position(3, 2));
        wallSet.add(new Position(5, 4));
        wallSet.add(new Position(3, 4));
        wallSet.add(new Position(5, 5));
        wallSet.add(new Position(3, 5));
        wallSet.add(new Position(5, 6));
        wallSet.add(new Position(3, 6));
        wallSet.add(new Position(5, 7));
        wallSet.add(new Position(3, 7));
        wallSet.add(new Position(5, 8));
        wallSet.add(new Position(3, 8));
        wallSet.add(new Position(5, 9));
        wallSet.add(new Position(3, 9));
        wallSet.add(new Position(5, 3));
        wallSet.add(new Position(3, 3));
        wallSet.add(new Position(5, 0));
        wallSet.add(new Position(3, 0));

        floodedSet.add(new Position(3, 3));
        floodedSet.add(new Position(2, 3));
        floodedSet.add(new Position(1, 2));
        wallSet.add(new Position(4, 2));

        floodedSet.add(new Position(4, 4));
        floodedSet.add(new Position(4, 5));
        floodedSet.add(new Position(4, 6));
        floodedSet.add(new Position(4, 7));
        floodedSet.add(new Position(4, 8));

        floodedSet.add(new Position (4, 2));
        floodedSet.add(new Position(4, 1));
        floodedSet.add(new Position(4, 0));
        floodedSet.add(new Position(4, -1));

        wallSet.add(new Position(4, 9));
        exitSet.add(new Position(1, 3));


    }

    @Override
    public void move(Direction direction) throws OnFire, Flooded, Wall, Exit {
//        System.out.println("current pozycja" + currentPosition);
        Position futurePosition = direction.step(currentPosition);
        if (repeated.contains(futurePosition)) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        System.out.println(currentPosition);
        if (onFireSet.contains(futurePosition)) {
            currentPosition = futurePosition;
            throw new OnFire();
        }
        //
        if (floodedSet.contains(futurePosition)) {
            currentPosition = futurePosition;
            System.out.println("WODAAAAAAAAAAAAAAAAAAAWODAAAAAAAAAAAAA");
            throw new Flooded();
        }
        if (exitSet.contains(futurePosition)) {
            currentPosition = futurePosition;
            System.out.println("ZNALEZIONEEEEEEEEEEEEEEEEEEEEEEEEE!");
            throw new Exit();
        }
        //brak zmiany pozycji kontrolera
        if (wallSet.contains(futurePosition)) {
            System.out.println("ściana!");
            repeated.add(futurePosition);
            throw new Wall();
        }
        System.out.println("ruszamy się!");
        currentPosition = futurePosition;
//        System.out.println("future pozycja" + futurePosition);
    }
}

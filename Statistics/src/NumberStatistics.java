import java.io.IOException;
import java.util.*;

public class NumberStatistics implements Statistics{

        int length;
        Map<Integer, Set<Position>> numberPositions;

    @Override
    public void sideLength( int length ) {
    //throw new RuntimeException(Integer.toString(length));

        this.length = length;

    }
    @Override
    public void addNumbers(Map<Integer, Set<Position>> numberPositions){

        //this.numberPositions = numberPositions;
        throw new RuntimeException(numberPositions.toString());

    }
    @Override
    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared) {

        int x = position.row();
        int y = position.col();
        int odleglosc1 = 0;
        Map<Integer, Map<Integer, Integer>> liczba_map = new HashMap<>();

        for (Map.Entry<Integer, Set<Position>> entry : numberPositions.entrySet()) {

            Set<Position> positions = new HashSet<>();
            int key = entry.getKey();// dla liczby KEY dostan do pudla
            positions.addAll(entry.getValue());//jej pozycji

            for (Position pos : positions) {//jak masz pozycji  JEDNEJ LICZBY w pudelku
                int deltaX = Math.abs(pos.row() - x);
                int deltaY = Math.abs(pos.col() - y);

                deltaX = Math.min(deltaX, length - deltaX);
                deltaY = Math.min(deltaY, length - deltaY);

                odleglosc1 = deltaX*deltaX + deltaY*deltaY;

                if (odleglosc1 <= maxDistanceSquared) {//jezeli odleglosc jest mniejsza
                    // dodaj jeden do tej dystancji
                    Map<Integer, Integer> odleglosc_map = liczba_map.getOrDefault(key, new HashMap<>());
                    odleglosc_map.put(odleglosc1, odleglosc_map.getOrDefault(odleglosc1, 0) + 1);

                    liczba_map.put(key, odleglosc_map);
                }
            }

        }
        return liczba_map;
    }

}

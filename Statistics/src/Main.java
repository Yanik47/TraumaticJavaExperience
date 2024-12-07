import java.util.*;

public class Main {
    public static void main(String[] args) {
        NumberStatistics numberStats = new NumberStatistics();

        int length = 5;
        numberStats.sideLength(length);

        Map<Integer, Set<Position>> numberPositions = new HashMap<>();
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(5, 5));
        positions.add(new Position(4, 5));
        positions.add(new Position(4, 3));
        positions.add(new Position(1, 2));

        numberPositions.put(1, positions);
        numberStats.addNumbers(numberPositions);
        System.out.println(Arrays.deepToString(numberStats.plaszczyzna));

        //Map<Integer, Set<Position>> numberPositions1 = new HashMap<>();
        Set<Position> positions1 = new HashSet<>();
        positions1.add(new Position(2, 2));
        //positions1.add(new Position(3, 3));
        positions1.add(new Position(2, 3));
        positions1.add(new Position(1, 1));
        numberPositions.put(3, positions1);

        Set<Position> positions2 = new HashSet<>();
        positions2.add(new Position(5, 1));
        positions2.add(new Position(3, 3));

        numberPositions.put(-2, positions2);
        numberStats.addNumbers(numberPositions);
        System.out.println(Arrays.deepToString(numberStats.plaszczyzna));

        numberStats.addNumbers(numberPositions);
        System.out.println(Arrays.deepToString(numberStats.plaszczyzna));


        Position position = new Position(4, 4);
        int maxDistanceSquared = 2;
        Map<Integer, Map<Integer, Integer>> neighbors = numberStats.neighbours(position, maxDistanceSquared);
        System.out.println(Arrays.deepToString(numberStats.sas_tab));
        neighbors.forEach((key, value) -> System.out.println("Ключ: " + key + ", Значение: " + value));
    }
}
/*import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Statistics chujWi = new NumberStatistics();

        chujWi.sideLength(9);
        Set<Position> s1 = new HashSet<>();
        s1.add(new Position(1,5));
        s1.add(new Position(1,9));
        s1.add(new Position(3,7));
        s1.add(new Position(5,4));
        Set<Position> s2 = new HashSet<>();
        s2.add(new Position(1,4));
        s2.add(new Position(9,1));
        s2.add(new Position(9,5));
        Set<Position> s3 = new HashSet<>();
        s3.add(new Position(6,7));
        s3.add(new Position(9,9));
        Set<Position> s4 = new HashSet<>();
        s4.add(new Position(1,1));
        s4.add(new Position(7,6));
        Map<Integer, Set<Position>> pozycjeNumerków = new HashMap<>();
        pozycjeNumerków.put(1,s1);
        pozycjeNumerków.put(2,s2);
        pozycjeNumerków.put(3,s3);
        pozycjeNumerków.put(4,s4);
        chujWi.addNumbers(pozycjeNumerków);

        Map<Integer, Map<Integer, Integer>> numerkiWOdleglosci = chujWi.neighbours(new Position(7,6), 18);

        Set<Integer> klucze = numerkiWOdleglosci.keySet();
        Set<Integer> kluczeTemp;
        for (Integer numerek: klucze) {
            kluczeTemp = numerkiWOdleglosci.get(numerek).keySet();
            for (Integer dystansNumerka: kluczeTemp) {
                System.out.println( "numerek: " + numerek + " | dystans od środka: " + dystansNumerka + " | ilość takich numerków: " + numerkiWOdleglosci.get(numerek).get(dystansNumerka) );
            }
        }
    }
}*/
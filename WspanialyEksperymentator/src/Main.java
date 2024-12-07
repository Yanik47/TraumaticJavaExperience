import java.util.*;
import java.time.LocalTime;
import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        WspanialyEksperymentator eksperymentator = new WspanialyEksperymentator();

        KostkaDoGry kostka = new MyKostkaDoGry();
        eksperymentator.użyjKostki(kostka);
        long czas = (long) 5000.0;
        eksperymentator.czasJednegoEksperymentu(czas);

        int liczbaKostek = 3;
        Map<Integer, Double> wynikMap = eksperymentator.szansaNaWyrzucenieOczek(liczbaKostek);
        System.out.println("Результаты эксперимента (szansaNaWyrzucenieOczek):");
        for (Map.Entry<Integer, Double> entry : wynikMap.entrySet()) {
            System.out.println("Сумма: " + entry.getKey() + ", Вероятность: " + entry.getValue());
        }

        List<Integer> sekwencja = Arrays.asList(1, 2, 3);
        double szansa = eksperymentator.szansaNaWyrzucenieKolejno(sekwencja);
        System.out.println("Вероятность выпадения последовательности: " + szansa);

        Set<Integer> oczka = new HashSet<>(Arrays.asList(1, 2, 5));
        double szansaDowolnaya = eksperymentator.szansaNaWyrzucenieWDowolnejKolejności(oczka);
        System.out.println("Вероятность выпадения заданных значений в любом порядке: " + szansaDowolnaya);
    }
}
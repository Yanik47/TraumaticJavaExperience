import java.util.*;
import java.time.LocalTime;
import java.time.Duration;

class WspanialyEksperymentator implements Eksperymentator{

    private KostkaDoGry kostka;
    private long czasEksperymentu;
    private LocalTime startCzas;

    @Override
    public void użyjKostki(KostkaDoGry kostka) {
        this.kostka = kostka;
    }
    @Override
    public void czasJednegoEksperymentu(long czasEksperymentu) {

        this.czasEksperymentu = czasEksperymentu;
    }
    @Override
    public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek) {
        int iloscRzutow = 0;
        Map<Integer, Double> wynikMap = new HashMap<>();
        int[] temp = new int[liczbaKostek];
        startCzas = LocalTime.now();
        while (LocalTime.now().isBefore(startCzas.plus(Duration.ofMillis((czasEksperymentu))))) {

            //PSEUDOKOD
            // temp = [kostka.rzut]*liczbaKostek
            // wynik = temp.sum()
            //
            //
            // if(map nie ma jeszcze wynik)
            //      map.push_back(wynik, wartosc+=1)
            // else
            //      map.set(wynik, wartosc+=1)
            //
            //Rozdziel wszystkie wartosci na ilosc rzutow

            iloscRzutow++;
            int sum = 0;

            //robienie rzutu kostkami
            for(int i = 0; i<liczbaKostek; i++){
                temp[i] = kostka.rzut();
            }
            //liczenie sumy
            for (int element : temp) {
                sum += element;
            }
            //wpisywanie ilosci okreslonych sum rzutow
            if (!wynikMap.containsKey((Integer) sum)) {
                wynikMap.put((Integer) sum, 1.0);
            } else {
                double count = wynikMap.get((Integer) sum);
                wynikMap.put((Integer) sum, count + 1.0);
            }
        }
            //liczenie prawdopodobienstwa rzutow
        for (Map.Entry<Integer, Double> entry : wynikMap.entrySet()) {
            wynikMap.put(entry.getKey(), entry.getValue() / iloscRzutow);
        }
        Arrays.fill(temp, 0);


        return wynikMap;

    }
    @Override
    public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja) {
        double iloscRzutow = 0;
        double iloscSekwencji = 0;
        int liczbaKostek = sekwencja.size();
        List<Integer> temp = new ArrayList<>();
        startCzas = LocalTime.now();
        while (LocalTime.now().isBefore(startCzas.plus(Duration.ofMillis((czasEksperymentu))))) {

            iloscRzutow++;

            //stworz sekwencje
            for (int i = 0; i < liczbaKostek; i++) {
                temp.add(kostka.rzut());
            }
            //sprawdz czy jest taka jak sekwencja
            if (temp.equals(sekwencja)) {
                iloscSekwencji++;
            }
            temp.clear();

        }
        return iloscSekwencji / iloscRzutow;

    }
    @Override
    public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka) {
        double iloscRzutow = 0;
        double iloscZbiorow = 0;
        int liczbaKostek = oczka.size();
        Set<Integer> oczkaSet = new HashSet<>(oczka);
        startCzas = LocalTime.now();
        while (LocalTime.now().isBefore(startCzas.plus(Duration.ofMillis((czasEksperymentu))))) {
            Set<Integer> temp = new HashSet<>();
            iloscRzutow++;
            for (int i = 0; i < liczbaKostek; i++) {
                temp.add(kostka.rzut());
            }
            if (temp.equals(oczkaSet)) {
                iloscZbiorow++;
            }
        }
        return iloscZbiorow/iloscRzutow;
    }
}

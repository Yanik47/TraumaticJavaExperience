import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
public class LeniwyEksperymentator implements LeniwyBadaczKostekDoGry{
    private ExecutorService exec;
    private static final AtomicInteger uniqueId = new AtomicInteger(0);
    private final Map<Integer, CountDownLatch> latchMap = new ConcurrentHashMap<>();
    private final Map<Integer, Map<Integer, Integer>> wyniki = new ConcurrentHashMap<>();

    private synchronized int identyfikatorZadania() {return uniqueId.getAndIncrement();}
    private synchronized  void zarejestrujWynik(int identyfikator, int wynik){
        Map<Integer, Integer> histogram = this.wyniki.get(identyfikator);
        histogram.put(wynik, histogram.getOrDefault(wynik, 0)+1);
    }
    @Override
    public void fabrykaWatkow(ExecutorService executorService) {
        this.exec = executorService;
    }
    @Override
    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {

        int identyfikator = identyfikatorZadania();
        this.wyniki.put(identyfikator, new HashMap<>());
        if (liczbaRzutow <= 0) {
            return identyfikator;
        }
        exec.submit(() -> {
            CountDownLatch latch = new CountDownLatch(liczbaRzutow);
            this.latchMap.put(identyfikator, latch);
            try {
                for (int i = 0; i < liczbaRzutow; i++) {
                    int wynikRzutu = kostka.rzut();
                    zarejestrujWynik(identyfikator, wynikRzutu);
                    latch.countDown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return identyfikator;
    }
    @Override
    public boolean badanieKostkiZakonczono(int identyfikator) {
        CountDownLatch latch = this.latchMap.get(identyfikator);
        return latch != null && latch.getCount() == 0;
    }
    @Override
    public Map<Integer, Integer> histogram(int identyfikator) {
        CountDownLatch latch = this.latchMap.get(identyfikator);
        try {
            if (latch != null) {
                latch.await(); // Czekaj, aż licznik CountDownLatch osiągnie zero
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(this.wyniki.get(identyfikator) != null)
            return this.wyniki.get(identyfikator);
        else return new HashMap<>();
    }
}













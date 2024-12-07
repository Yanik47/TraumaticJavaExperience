import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class WatkowyEksperymentator implements BadaczKostekDoGry{
    private final Semaphore semafor = new Semaphore(0);
    ThreadFactory myFabryka;
    private final Map<Integer, Map<Integer, Integer>> wyniki = new HashMap<>();
    private final Map<Integer, CountDownLatch> latchMap = new HashMap<>();
    private static final AtomicInteger uniqueId = new AtomicInteger(0);

    private int identyfikatorZadania() {return uniqueId.getAndIncrement();}
    private synchronized  void zarejestrujWynik(int identyfikator, int wynik){
        Map<Integer, Integer> histogram = this.wyniki.get(identyfikator);
        histogram.put(wynik, histogram.getOrDefault(wynik, 0)+1);
    }
    @Override
    public void dozwolonaLiczbaDzialajacychWatkow(int limitWatkow) {
        this.semafor.release(limitWatkow);
    }
    @Override
    public void fabrykaWatkow(ThreadFactory fabryka) {
        this.myFabryka = fabryka;
    }
    @Override
    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {

        int identyfikator = identyfikatorZadania();
        this.wyniki.put(identyfikator, new HashMap<>());

        if (liczbaRzutow <= 0) {
            return identyfikator;
        }

        Runnable zadanie = () -> {
            try {
                sleep(350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            CountDownLatch latch = new CountDownLatch(liczbaRzutow);
            this.latchMap.put(identyfikator, latch);

            try {
                this.semafor.acquire();
                for (int i = 0; i < liczbaRzutow; i++) {
                    int wynikRzutu = kostka.rzut();
                    zarejestrujWynik(identyfikator, wynikRzutu);
                    latch.countDown();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.semafor.release();
            }
        };
        Thread watek = this.myFabryka.getThread(zadanie);
        watek.start();
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
            latch.await(); // Czekaj, aż licznik CountDownLatch osiągnie zero
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this.wyniki.get(identyfikator);
    }


    private static void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}









import java.util.Random;
class MyKostkaDoGry implements KostkaDoGry{
    private Random random = new Random();
    int randomNumber;
    @Override
    public int rzut(){

        randomNumber = random.nextInt(6) + 1;
        return randomNumber;
    }
}

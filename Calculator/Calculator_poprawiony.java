import java.util.Stack;

public class Calculator extends CalculatorOperations{

    public int stack_akt_size = 0;
    public int akumulator = 0;
    public int[] pamiec = new int[MEMORY_SIZE];
    public Stack<Integer> stack = new Stack<>();

    /**
     * Zapisuje podaną wartość w akumulatorze.
     * @param value wartość do zapisania w akumulatorze
     */
    public void setAccumulator( int value ){
        akumulator = value;
    }

    /**
     * Zwraca wartość zapisaną w akumulatorze
     * @return zawartość akumulatora
     */
    public int getAccumulator(){
        return akumulator;
    }

    /**
     * Zwraca zawartość pamięci na pozycji index.
     * @param index pozycja w pamięci
     * @return wartość znajdująca się pod wskazanym indeksem
     */
    public int getMemory( int index ){

        if(index >= MEMORY_SIZE || index < 0){
            System.out.println("Podany niepoprawny index do odczytywania z pamieci");
            return -1;
        }
        else {
            return pamiec[index];
        }

    }
    /**
     * Zapisuje zawartość akumulatora pod pozycją index pamięci
     * @param index pozycja w pamięci
     */
    public void accumulatorToMemory( int index ){
        if(index >= MEMORY_SIZE || index < 0){
            System.out.println("Podany niepoprawny index do zapisywania do pamieci");
        }
        else {
            pamiec[index] = akumulator;
        }
    }

    /**
     * Do akumulatora dodaje przekazaną wartość
     * @param value wartość do dodania do akumulatora
     */
    public void addToAccumulator( int value ){

        if(akumulator + value > 2147483647 || akumulator + value < -2147483648){
            System.out.println("Przekroczono maksymalny rozmiar akumulatora");
        }
        else {
            akumulator += value;
        }
    }

    /**
     * Odejmuje przekazaną wartość od akumulatora
     * @param value wartość odejmowana od akumulatora
     */
    public void subtractFromAccumulator( int value ){

        if(akumulator - value > 2147483647 || akumulator - value < -2147483648){
            System.out.println("Przekroczono maksymalny rozmiar akumulatora");
        }
        else {
            akumulator -= value;
        }
    }

    /**
     * Dodaje zawartość wskazanej pozycji pamięci do akumulatora
     * @param index pozycja w pamięci
     */
    public void addMemoryToAccumulator( int index ){


        if(index >= MEMORY_SIZE || index < 0){
            System.out.println("Podany niepoprawny index");
        }
        else {
            if (akumulator + pamiec[index] > 2147483647 || akumulator + pamiec[index] < -2147483648) {
                System.out.println("Przekroczono maksymalny rozmiar akumulatora");
            }
            else {
                akumulator += pamiec[index];
            }
        }

    }

    /**
     * Odejmuje zawartość wskazanej pozycji pamięci od akumulatora
     * @param index pozycja w pamięci
     */
    public void subtractMemoryFromAccumulator( int index ){

        if(index >= MEMORY_SIZE || index < 0){
            System.out.println("Podany niepoprawny index");
        }
        else {
            if (akumulator - pamiec[index] > 2147483647 || akumulator - pamiec[index] < -2147483648) {
                System.out.println("Przekroczono maksymalny rozmiar akumulatora");
            }
            else {
                akumulator -= pamiec[index];
            }
        }
    }

    /**
     * Przywraca ustawienia początkowe - akumulator ustawiony na 0,
     * na każdej pozycji pamięci 0, stos pusty.
     */
    public void reset(){
        akumulator = 0;
        for (int i = 0; i < MEMORY_SIZE; i++) {
            pamiec[i] = 0;
        }
        stack.clear();
    }

    /**
     * Wymienia zawartość wskazanej pozycji pamięci z akumulatorem
     * @param index pozycja w pamięci
     */
    public void exchangeMemoryWithAccumulator( int index ){
        if(index >= MEMORY_SIZE || index < 0){
            System.out.println("Podany niepoprawny index");
        }
        else {
            int temp = akumulator;
            akumulator = pamiec[index];
            pamiec[index] = temp;
        }
    }

    /**
     * Zapisuje zawartość akumulatora na szczycie stosu. <b>Zawartość akumulatora
     * nie ulega zmianie</b>.
     */
    public void pushAccumulatorOnStack(){


        if(stack_akt_size >= STACK_SIZE){
            System.out.println("Stos jest przepelniony, nie mozna wykonac operacje");
        }
        else {
            stack_akt_size++;
            stack.push(akumulator);
        }

    }

    /**
     * Zdejmuje ze szczytu stosu zawartość akumulatora.
     */
    public void pullAccumulatorFromStack(){
        if(stack_akt_size == 0){
            System.out.println("Stos jest pusty, nie mozna wykonac operacje");
        }
        else {
            stack_akt_size--;
            akumulator = stack.pop();
        }
    }


}

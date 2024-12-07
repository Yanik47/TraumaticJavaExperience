import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammableCalculator implements ProgrammableCalculatorInterface {

    private BufferedReader programCodeReader;
    private String inputLine;
    private LineReader myLineReader;
    private LinePrinter myLinePrinter;
    private boolean isEnd = true;

    @Override
    public void programCodeReader(BufferedReader reader) {
        this.programCodeReader = reader;
    }

    @Override
    public void setStdin(LineReader input) {
        this.myLineReader = input;
    }

    @Override
    public void setStdout(LinePrinter output) {
        this.myLinePrinter = output;
    }

    private String czyToTalinijka(String myLine, int myNumberLine) throws IOException {
        //pierwsza spacja
        int spaceIndex = myLine.indexOf(' ');
        //koniec linijki
        int endIndex = myLine.indexOf('\n');
        //numer linijki
        int numberLine = Integer.parseInt(myLine.substring(0, spaceIndex));
        //jezeli nie taki sam
        if (numberLine < myNumberLine) {
            //wez nowa linijke
            this.inputLine = this.programCodeReader.readLine();
            myLine = this.inputLine + "\n";
            //sprobuj jeszcze raz
            return czyToTalinijka(myLine, myNumberLine);
        } else {
            //zwroc potrzebna linijke
            return myLine.substring(spaceIndex + 1, endIndex);
        }
    }

    private String czyToTalinijkaKodu( int myNumberLine, List<String> myCode, int next) throws IOException {
        //pierwsza spacja
        String myLine = myCode.get(next);
        int spaceIndex = myLine.indexOf(' ');
        //koniec linijki
        int endIndex = myLine.indexOf('\n');
        //numer linijki
        int numberLine = Integer.parseInt(myLine.substring(0, spaceIndex));
        //jezeli nie taki sam
        if (numberLine < myNumberLine) {
            //wez nowa linijke
            next++;
            //sprobuj jeszcze raz
            return czyToTalinijkaKodu(myNumberLine, myCode, next);
        } else {
            //zwroc potrzebna linijke
            return myLine.substring(spaceIndex + 1, endIndex);
        }
    }

    private static int szukamy(String wyrazenie, Map<String, Integer> zmienne) {

        //---------------------JAK WYGLADA LINIJKA?---------------------------------
        String wyrazenieReg = "\\s*-?(\\w+|\\d+)\\s*([+\\-*/])\\s*-?(\\w+|\\d+)?";

        String wyrazenieZapis = "\\s*-?(\\w+|\\d)?";

        Pattern pattern = Pattern.compile(wyrazenieReg);
        Pattern patternZapis = Pattern.compile(wyrazenieZapis);

        Matcher matcher = pattern.matcher(wyrazenie);
        Matcher matcherZapis = patternZapis.matcher(wyrazenie);
        //---------------------------------------------------------------------------

        List<Integer> wartoscZm = new ArrayList<>();
        int result = 0;
        String operandZapis;
        boolean reg = false;


        while (matcher.find()) {
            //---------------------DO CZEGO NALEZY SYMBOL?------------------------------------------------
            reg = true;
            String operand1 = matcher.group(1);
            String operacja = matcher.group(2);
            System.out.println("oerecds" + operacja + "dfsfswert");
            String operand2 = matcher.group(3);
            //----------------------CO WYCIAGNALEM?----------------------------------------------------------
            int value1 = operand1 != null && operand1.matches("\\d+") ? Integer.parseInt(operand1) : zmienne.getOrDefault(operand1, 0);

            if (operand2 != null) {
                int value2 = operand2.matches("\\d+") ? Integer.parseInt(operand2) : zmienne.getOrDefault(operand2, 0);
                wartoscZm.add(value1);
                wartoscZm.add(value2);
                System.out.println("WartoscZM0 " + wartoscZm.get(0));
                System.out.println("WartoscZM1 " + wartoscZm.get(1));
            } else {
                wartoscZm.add(value1);
                System.out.println("WartoscZM0 " + wartoscZm.get(0));
            }

            //----------------------CO MAMY ROBIC?--------------------------------
            switch (operacja) {
                case "+":
                    result = wartoscZm.get(0) + wartoscZm.get(1);
                    break;
                case "-":
                    result = wartoscZm.get(0) - wartoscZm.get(1);
                    break;
                case "*":
                    result = wartoscZm.get(0) * wartoscZm.get(1);
                    break;
                case "/":
                    result = wartoscZm.get(0) / wartoscZm.get(1);
                    break;
                default:
                    result = wartoscZm.get(0);
                    break;
            }
        }
        //-------------------------CO MAM ZAPISAC?--------------------------------

        if (matcherZapis.find() && !reg){
            operandZapis = matcherZapis.group(1);
            int value = operandZapis != null && operandZapis.matches("\\d+") ? Integer.parseInt(operandZapis) : zmienne.getOrDefault(operandZapis, 0);
            //System.out.println("SSSSSSSSSSSSS");
            result = value;
        }
        //--------------------------------------------------------------------------
        System.out.println("dsfsdfs " + result);
        return result;
    }
    private static boolean szukamyPowownania(String wyrazenie, Map<String, Integer> zmienne) {

        //---------------------JAK WYGLADA LINIJKA?---------------------------------
        String wyrazenieReg = "\\s*-?(\\w+|\\d+)\\s*([<=>])\\s*-?(\\w+|\\d+)?";
        Pattern pattern = Pattern.compile(wyrazenieReg);
        Matcher matcher = pattern.matcher(wyrazenie);
        //---------------------------------------------------------------------------
        List<Integer> wartoscZm = new ArrayList<>();
        boolean result = false;

        while (matcher.find()) {
            //---------------------DO CZEGO NALEZY SYMBOL?------------------------------------------------
            String operand1 = matcher.group(1);
            String operacja = matcher.group(2);
            System.out.println("oerecds" + operacja + "dfsfswert");
            String operand2 = matcher.group(3);
            //----------------------CO WYCIAGNALEM?----------------------------------------------------------
            int value1 = operand1 != null && operand1.matches("\\d+") ? Integer.parseInt(operand1) : zmienne.getOrDefault(operand1, 0);
            int value2 = operand2 != null && operand2.matches("\\d+") ? Integer.parseInt(operand2) : zmienne.getOrDefault(operand2, 0);
                wartoscZm.add(value1);
                wartoscZm.add(value2);
                System.out.println("WartoscZM0 " + wartoscZm.get(0));
                System.out.println("WartoscZM1 " + wartoscZm.get(1));

            //----------------------CO MAMY ROBIC?--------------------------------
            switch (operacja) {
                case "<":
                    result = wartoscZm.get(0) < wartoscZm.get(1);
                    break;
                case ">":
                    result = wartoscZm.get(0) > wartoscZm.get(1);
                    break;
                case "=":
                    result = wartoscZm.get(0).equals(wartoscZm.get(1));
                    break;
                default:

                    break;
            }
        }
        //--------------------------------------------------------------------------
        System.out.println("dsfsdfs " + result);
        return result;
    }

    @Override
    public void run(int line) {

        try {
            this.inputLine = this.programCodeReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String myLine = this.inputLine + "\n";
        List<String> myCode = new ArrayList<>();
        myCode.add(myLine);
        System.out.println("To co otrzymalem");
        System.out.println(myLine);
        int poczatek = 0;
        int line1 = line;
        Map<String, Integer> zmienne = new HashMap<>();
        String myCodeLine;

        int indexCode = 0;

        boolean myGOTO = false;


        while (isEnd) {
            //----------------------------WROCILEM? NO FAKTYCZNIE JEST TO REALIZACJA GOTO------------------------
            List<String> myCodeCopy = new ArrayList<>(myCode);
            if(myGOTO && poczatek>0 && !myCodeCopy.isEmpty()){
                //-----------------------ZNAJDZ LINIJKE------------------------
                try {
                    myLine = czyToTalinijkaKodu(line1, myCodeCopy, 0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //-------------------------------------------------------------
                indexCode = myCode.indexOf(myLine);
                line1++;
                //rob linijki z listy
            }

            //-----------------------------CZY NIE JEST POCZATEK?-----------------------------------------------
            else {
                myGOTO = false;
                if (poczatek > 0) {
                    try {
                        this.inputLine = programCodeReader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                //---------------------------------------------------------------------------------------------------
                myLine = this.inputLine + "\n";
                myLine = myLine.replaceAll("^\\s+", "");
                myCodeLine = myLine;
                myCode.add(myCodeLine);
                //---------------------------------CZY JEST PUSTA?-----------------------------------------------
                if (myLine.replaceAll(" ", "").replaceAll("\n", "").isEmpty()) {
                    continue;
                }
                //sprawdzamy czy jest to nasza linijka
                //--------------CZY TO TA LINIJKA?--------------------------
                try {
                    myLine = czyToTalinijka(myLine, line1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //-----------------------------------------------------------
            }

            //------------------------DZIALANIE NA LINIJCE KODU--------------------------------------------------
                    System.out.println("To co otrzymalem1");
                    System.out.println(myLine);


                    int spaceIndex = myLine.indexOf(' ');

                    //----------------------MAMY END?--------------------------
                    if (spaceIndex == -1) {
                        myLine = myLine.toUpperCase();
                        System.out.println("if spaceIndex == -1");
                        System.out.println(myLine);
                    }

                    if(myLine.replaceAll("\n", ""). replaceAll(" ", "").equals("END")){
                        isEnd = false;//ZDARZA SIE GLUPIEMU STUDENTOWI
                        break;

                    }
                    //-------------------ODCINAMY OPERACJE------------------------------
                    String operation = myLine.substring(0, spaceIndex);

                    myLine = myLine.replace(operation, "");
                    operation = operation.toUpperCase();

                    System.out.println("operation");
                    System.out.println(operation);
                    //zostawiamy linijke nad ktora bedzie prowadzona operacja
                    System.out.println(myLine);
                    if (!operation.equals("PRINT") && !operation.equals("LET") &&!operation.equals("IF")) {
                        myLine = myLine.replace(" ", "");
                    }
                    myLine = myLine.replaceAll("^\\s+", "");
                    System.out.println("myLine after");
                    System.out.println(myLine);
                    //-----------------------------------------------------------------------


                    switch (operation) {
                        case "LET":

                            char target = '=';
                            //--------------ODCINAMY ZMIENNA-----------------------------------
                            int indexTarget = myLine.indexOf(target);
                            String zmienna = myLine.substring(0, indexTarget).replaceAll(" ", "");
                            //-------------ZAPISUJEMY WARTOSC ZMIENNE--------------------------
                            System.out.println("zmienna: " + zmienna);
                            int wartoscZmiennej = zmienne.getOrDefault(zmienna, 0);
                            System.out.println("wartosc: " + String.valueOf(wartoscZmiennej));
                            //-------------ZOSTAWIAMY OPERACJE-----------------------------------
                            String letline = myLine.substring(indexTarget+1).replaceAll("\n", "").replaceAll(" ", "");
                            System.out.println("letline: " + letline);

                            zmienne.put(zmienna, wartoscZmiennej);
                            //-------------PATRZYMY CO MAMY ROBIC--------------------------------
                            zmienne.put(zmienna.replaceAll(" ", ""), szukamy(letline, zmienne));
                            System.out.println(zmienne);
                            break;
                            //--------------------------------------------------------------------

                        case "PRINT":
                            //------------------MASZ "STRING"--------------------------------------
                            if (myLine.charAt(0) == '\"' || myLine.charAt(1) == '\"') {
                                this.myLinePrinter.printLine(myLine.replaceAll("\n", ""));
                            }
                            //------------------MASZ ZMIENNA-----------------------------------------
                            else {
                                String zmiennaLine = myLine.replaceAll(" ", "");
                                System.out.println("ZmiennaLine " + zmiennaLine);
                                this.myLinePrinter.printLine(String.valueOf(zmienne.get(zmiennaLine)).replaceAll("\n", ""));
                            }
                            break;
                            //---------------------------------------------------------------------------

                        case "GOTO":
                            line1 = Integer.parseInt(myLine.replaceAll(" ", ""));
                            myGOTO = true;
                            break;


                        case "IF":
                            if(szukamyPowownania(myLine, zmienne)){
                                String regex = "GOTO(\\d+)";
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(myLine);
                                if(matcher.find()) {
                                    line1 = Integer.parseInt(myLine.replaceAll(" ", ""));
                                }
                                myGOTO = true;
                            }
                            break;

                        case "INPUT":
                            //-------------------WYCIAGAMY z WEJSCIA---------------------------------------
                            zmienne.put(myLine.replaceAll(" ", ""), Integer.parseInt(this.myLineReader.readLine()));
                            System.out.println("Mam zmienne");
                            System.out.println(zmienne);
                            System.out.println("Mam bufor");
                            System.out.println(Integer.parseInt(myLineReader.readLine()));
                            //-----------------------------------------------------------------------------
                            break;
                        default:
                            break;
                    }
                    poczatek++;

        }
    }

}

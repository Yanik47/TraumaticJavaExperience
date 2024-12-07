import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammableCalculator implements ProgrammableCalculatorInterface {


    private LineReader myLineReader;

    //List<String> myCode = new ArrayList<>();
    List<String> mySortedCode = new ArrayList<>();
    Map<String, Integer> zmienne = new HashMap<>();

    Stack<String> gosubLine = new Stack<>();
    private LinePrinter myLinePrinter;
    private boolean isEnd = false;

    String prevLine;

    @Override
    public void programCodeReader(BufferedReader reader) {

        String line;

        while(true){
            try {
                line = reader.readLine();
            } catch (IOException e) {
                break;
            }
            this.mySortedCode.add(line);
            //System.out.println(this.mySortedCode);
            if (line == null) {
                break;
            }
        }
        mySortedCode.removeIf(Objects::isNull);
    }

    @Override
    public void setStdin(LineReader input) {
        this.myLineReader = input;

    }

    @Override
    public void setStdout(LinePrinter output) {
        this.myLinePrinter = output;
    }

    private boolean CheckLineNumber(String inputLine, int myNumberLine){
        int numberLine = Integer.parseInt(inputLine);
         return numberLine >= myNumberLine;
    }

    private boolean CheckIf(String ifLine){

        String wyrazenieReg = "\\s*-?(\\w+|\\d+)\\s*([<=>])\\s*-?(\\w+|\\d+)?";
        Pattern pattern = Pattern.compile(wyrazenieReg);
        Matcher matcher = pattern.matcher(ifLine);

        while(matcher.find()){
            String operand1 = matcher.group(1);
            String operacja = matcher.group(2);
            String operand2 = matcher.group(3);

            int value1 = operand1 != null && operand1.matches("\\d+") ? Integer.parseInt(operand1) : zmienne.getOrDefault(operand1, 0);
            int value2 = operand2 != null && operand2.matches("\\d+") ? Integer.parseInt(operand2) : zmienne.getOrDefault(operand2, 0);

            switch (operacja){
                case "<":
                    return value1 < value2;
                case "=":
                    return value1 == value2;
                case ">":
                    return value1 > value2;
            }
        }
        return false;
    }

    private int CheckValue(String myLine){
        String[] parts = myLine.split("\\s");

        int numberOfParts = parts.length;
        int result = 0;

        if(numberOfParts == 2){
            result =  parts[1] != null && parts[1].matches("\\d+") ? Integer.parseInt(parts[1]) : zmienne.getOrDefault(parts[1], 0);
        }

        else{
            parts[1] = parts[1].replaceAll(" ", "");
            parts[3] = parts[3].replaceAll(" ", "");

            int value1 = parts[1] != null && parts[1].matches("\\d+") ? Integer.parseInt(parts[1]) : zmienne.getOrDefault(parts[1], 0);
            int value2 = parts[3] != null && parts[3].matches("\\d+") ? Integer.parseInt(parts[3]) : zmienne.getOrDefault(parts[3], 0);

            result = switch (parts[2].replaceAll(" ", "")) {
                case "+" -> value1 + value2;
                case "-" -> value1 - value2;
                case "*" -> value1 * value2;
                case "/" -> value1 / value2;
                default -> result;
            };

        }
        return result;
    }

    @Override
    public void run(int line) {
        int i = 0;
        prevLine = Integer.toString(line);
        String[] myNiceGosub = new String[0];
        //int currentIndex = 0;
        boolean isGosub = false;
        while (!isEnd) {

            //List<String> myCodeCopy = new ArrayList<>(myCode);

            String inputLine = null;
            if (i < mySortedCode.size()) {
                String[] myLine = mySortedCode.get(i).split(" ", 2);
                if(mySortedCode.size() >= i+2){
                    myNiceGosub = mySortedCode.get(i + 1).split(" ", 2);
                }
                //System.out.println("Szukam");
                if(Integer.parseInt(myLine[0]) >= Integer.parseInt(prevLine)) {
                    inputLine = mySortedCode.get(i);
                    //System.out.println("zlapalem " + inputLine);
                    i++;
                }
                else{
                    i++;
                    continue;
                }
            }


            //System.out.println("4");
            //System.out.println(inputLine);

            //pusta linijka
            if(Objects.requireNonNull(inputLine).replaceAll("^\\s+", "").isEmpty()){
                continue;
            }
            String[] parts = inputLine.split(" ", 2);
            //System.out.println("part0 " + parts[0]);
            //System.out.println("part1 " + parts[1]);

            //czy to ta linijka kodu?
            if (CheckLineNumber(parts[0], Integer.parseInt(prevLine))) {
                //System.out.println("Siema, trafilem do isHide");

                parts = parts[1].split(" ", 2);
                //System.out.println("part2 " + parts[0]);
                //System.out.println("part3 " + parts[1]);
                //parts[0] instrukcja, parts[1] linijka kodu
                parts[0] = parts[0].toUpperCase();
                //System.out.println("part3 " + parts[0]);
                //czy nie konczymy program
                if (parts[0].replaceAll("\n", "").replaceAll(" ", "").equals("END")) {
                    this.isEnd = true;
                    break;
                }
                switch (parts[0]) {
                    case "LET":
                        parts[1] = parts[1].replaceAll("^\\s+", "");
                        String[] instruction1 = parts[1].split("=", 2);
                        //System.out.println(instruction1[0] + "Czesci" + instruction1[1]);
                        zmienne.put(instruction1[0].replaceAll(" ", ""), CheckValue(instruction1[1]));
                        //System.out.println(CheckValue(instruction1[1]));

                        break;

                    case "PRINT":
                        parts[1] = parts[1].replaceAll("^\\s+", "");
                        if (parts[1].charAt(0) == '\"') {
                            this.myLinePrinter.printLine(parts[1].replaceAll("\n", "").replaceAll("\"", ""));
                        } else {
                            String zmiennaLine = parts[1].replaceAll(" ", "");
                            this.myLinePrinter.printLine(String.valueOf(zmienne.get(zmiennaLine)).replaceAll("\"", ""));
                        }

                        break;

                    case "GOTO"://wraca tylko raz
                        prevLine = parts[1].replaceAll(" ", "");
                        //System.out.println("jestem tu");
                        //System.out.println(prevLine);
                        i = 0;
                        break;

                    case "IF":
                        //System.out.println(parts[1]);
                        String[] instruction = parts[1].split("(?i)GOTO", 2);
                        if (CheckIf(instruction[0])) {
                            prevLine = instruction[1].replaceAll(" ", "");
                            i = 0;
                        }
                        break;

                    case "INPUT":
                        zmienne.put(parts[1].replaceAll(" ", ""), Integer.parseInt(this.myLineReader.readLine()));
                        break;

                    case "GOSUB"://wraca tylko raz
                        isGosub = true;
                        prevLine = parts[1].replaceAll(" ", "");
                        this.gosubLine.push(myNiceGosub[0].replaceAll(" ", ""));
                        //System.out.println("jestem tu");
                        i = 0;
                        break;

                    case "RETURN":
                        //System.out.println(gosubLine);
                        if(isGosub && !this.gosubLine.isEmpty()) {
                            prevLine = this.gosubLine.pop();
                            //System.out.println(prevLine);
                            i = 0;
                        }
                        //System.out.println(gosubLine);
                        //System.out.println(prevLine);
                        if(this.gosubLine.isEmpty()){
                        isGosub = false;}
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

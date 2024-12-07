import java.io.BufferedReader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        ProgrammableCalculator calculator = new ProgrammableCalculator();

        String[] inputCode ={
                //"1 PrINT \"Siema\"",
                        "1 GOTO 60",
                        "3 LET g = 2",
                        "6 LET b = b - 1",
                        "7 RETURN",
                        "10 INpUT licznik     ",
                        "15 LET b = 2",
                        "20 PRINT \"PODANO licznik=\"",
                        "25 RETURN",
                        "26 PRINT \"TEGO NIE POWINNO SIE POJAWIC  1 \"",
                        "27 PRINT \"TU MASZ LICZNIK: \"" ,
                        "30 PrINT licznik" ,
                        "32 RETURN",
                        "35 LET c = 1" ,
                        "36 GOSUB 3",
                        "40 LET b = b + 5",
                        "41 PriNT b ",
                        "42 LET b = b + g",
                        "42 Print \"Tu jest twoje b\"",
                        "43 PriNT b ",
                        "44 IF b < 50 GOTO 36" ,
                        "45 RETURN",
                        "46 PRINT \"CO SIE DZIEJE\" ",
                        "47 GOTO 90" ,
                        "50 PRINT \"TEGO NIE POWINNO SIE POJAWIC   2\"",
                        "60 GOSUB 10" ,
                        "62 PRINT \"TO JA NASTEPNY GOSUB\"",
                        "63 GOsub 35" ,
                        "64 PRINT \"TO JA KOLEJNY GOSUB\"",
                        "66 GOto 47",
                        "90 END",
                        "            "};
        String[] inputCode1 ={
                "10 PRINT \"HELLO, WORLD!\"",
                "20 GOTO 80",
                "70 GOTO 70",
                "75 PRINT \"NIE POWINNO SIĘ WYŚWIETLIĆ\"",
                "80 END",
                };
        String[] inputCode2 ={
                "5 LET x = 10",
                "10 INPUT y",
                "15 IF y < 0 GOTO 30",
                "20 PRINT \"PODAŁEŚ DODATNIĄ LICZBĘ\"",
                "25 GOSUB 50",
                "27 PRINT \"WYWOŁANO GOSUB\"",
                "28 LET z = x + y",
                "29 PRINT \"SUMA x + y: \" + z",
                "30 RETURN",
                "50 PRINT \"TO JEST PROCEDURA GOSUB\"",
                "55 PRINT \"WARTOŚĆ x W PROCEDURZE: \"",
                "57 PRINT x",
                "60 RETURN",
                "65 PRINT \"TO JEST KONIEC PROGRAMU\"",
                "70 END"};

        String[] inputCode3 ={
                "1 PRINT \"ROZPOCZYNAMY PROGRAM\"",
                "5 LET x = 0",
                "7 LET i = 0",
                "10 LET i = i + 1",
                "15 PRINT \"Iteracja: \"",
                "17 PRINT i",
                "20 INPUT y",
                "25 IF y > 10 GOTO 50",
                "30 LET x = x + y",
                "32 IF i < 5 GOTO 10",
                "40 PRINT \"SUMA: \"",
                "42 Print x",
                "45 GOTO 70",
                "50 PRINT \"WARUNEK NIE SPEŁNIONY\"",
                "55 GOSUB 80",
                "60 PRINT \"WARTOŚĆ x W PROCEDURZE: \" + x",
                "65 GOTO 10",
                "70 PRINT \"KONIEC PROGRAMU\"",
                "75 END",
                "80 PRINT \"TO JEST PROCEDURA GOSUB\"",
                "85 LET x = x * 2",
                "90 RETURN",};

        String[] inputCode4 ={
                "1 PRINT \"ROZPOCZYNAMY PROGRAM\"",
                "5 LET x = 2",
                "10 PRINT \"WARTOŚĆ x: \"",
                "12 Print x",
                "15 IF x > 5 GOTO 30",
                "20 PRINT \"x JEST MNIEJSZE NIŻ 5\"",
                "25 GOSUB 50",
                "28 GOTO 45",
                "30 PRINT \"x JEST WIĘKSZE NIŻ 5\"",
                "35 GOSUB 50",
                "38 GOTO 45",
                "40 GOSUB 80",
                "45 PRINT \"KONIEC PROGRAMU\"",
                "50 PRINT \"TO JEST PROCEDURA GOSUB\"",
                "55 LET x = x * 2",
                "60 RETURN",
                "65 INPUT y",
                "70 IF y < 0 GOTO 85",
                "75 PRINT \"y JEST DODATNIE\"",
                "80 GOSUB 50",
                "83 GOTO 95",
                "85 PRINT \"y JEST UJEMNE\"",
                "90 GOSUB 80",
                "93 GOTO 95",
                "95 PRINT \"KONIEC TESTU\"",
                "100 END",};

        String[] inputCode5 ={
                "1 PRINT \"ROZPOCZYNAMY PROGRAM\"",
                "5 LET x = 10",
                "10 IF x > 5 GOTO 20",
                "15 GOto 200",
                "18 GOTO 30",
                "20 PRINT \"x JEST WIĘKSZE NIŻ 5\"",
                "25 GOSUB 50",
                "28 GOTO 30",
                "30 LET y = 3",
                "35 IF y > 0 GOTO 45",
                "40 GOSUB 80",
                "43 GOTO 55",
                "45 PRINT \"y JEST DODATNIE\"",
                "50 GOTO 105",
                "55 PRINT \"KONIEC TESTU\"",
                "60 INPUT z",
                "65 IF z > 0 GOTO 80",
                "70 GOSUB 80",
                "73 GOTO 95",
                "80 PRINT \"TO JEST PROCEDURA GOSUB\"",
                "85 LET x = x * 2",
                "90 RETURN",
                "95 PRINT \"KONIEC PROGRAMU\"",
                "100 END",
                "105 LET a = 1",
                "110 PRINT \"WARTOŚĆ a: \" + a",
                "115 IF a < 3 GOTO 125",
                "120 GOSUB 150",
                "123 GOTO 135",
                "125 PRINT \"a JEST MNIEJSZE NIŻ 3\"",
                "135 LET b = 5",
                "140 IF b > 0 GOTO 150",
                "145 GOSUB 180",
                "148 GOTO 160",
                "150 PRINT \"b JEST DODATNIE\"",
                "155 RETURN",
                "160 PRINT \"KONIEC DRUGIEGO TESTU\"",
                "165 INPUT c",
                "170 IF c > 0 GOTO 180",
                "175 GOSUB 180",
                "178 GOTO 195",
                "180 PRINT \"TO JEST DRUGA PROCEDURA GOSUB\"",
                "185 LET c = c + 2",
                "190 RETURN",
                "195 PRINT \"KONIEC PROGRAMU\"",

                "200 END",};


        String[] inputCode6 ={
                "1 goto 60",
                "3 GOTO 49",
                "5 GOTO 9",
                "9 GOTO 54",
                "13 GOTO 19",
                "15 GOTO 24",
                "19 GOTO 5",
                "23 GOTO 32",
                "24 GOTO 61",
                "29 GOTO 15",
                "32 GOTO 45",
                "37 GOTO 38",
                "38 GOTO 29",
                "45 GOTO 37",
                "46 GOTO 23",
                "47 GOTO 46",
                "49 GOTO 13",
                "54 GOTO 47",
                "60 GOTO 61",
                "61 end",
        };

        String[] inputCode7 ={
                "1 GOsub 60",
                "3 GOsub 49",
                "5 GOsub 9",
                "9 GOsub 19",
                "13 return",
                "15 return",
                "19 return",
                "23 Gosub 37",
                "24 return",
                "37 return",
                "49 return",
                "60 return",
                "61 END",
        };

        String inputData = "-5";


        ProgrammableCalculatorInterface.LineReader lineReader = new LineReader(inputData);
        for(int i = 0; i < inputCode6.length; i++) {
            calculator.programCodeReader(new BufferedReader(new StringReader(inputCode6[i])));
        }
        calculator.setStdin(lineReader);
        calculator.setStdout(System.out::println);
        calculator.run(1);
    }

    private static class LineReader implements ProgrammableCalculatorInterface.LineReader {
        private final String input;

        public LineReader(String input) {
            this.input = input;
        }

        @Override
        public String readLine() {
            return input;
        }
    }
}
import java.io.*;
import java.net.*;
import java.math.BigInteger;

class Klient implements NetConnection{

    private BigInteger num;
    @Override
    public void password(String password) {
        this.num = new BigInteger(password);
    }
    @Override
    public void connect(String host, int port) {
        try{

            Socket socket = new Socket(host, port);
            //System.out.println("1");
            InputStream input = socket.getInputStream();
            //System.out.println("2");
            OutputStream output = socket.getOutputStream();
            //System.out.println("3");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            //System.out.println("4");
            PrintWriter writer = new PrintWriter(output, true);
            //System.out.println("6");
            //System.out.println("7");
            String response;
            writer.println("Program");
            while(!(response = reader.readLine()).equals("EOD")){
                //System.out.println("8");
                //System.out.println(response + " 155");
                BigInteger number;
                try{
                    number = new BigInteger(response);
                }catch(NumberFormatException e){
                    continue;
                }
                this.num = num.add(number);

            }
            //System.out.println(num);
            BigInteger serwerNumber;
            //System.out.println("9");
            //System.out.println("10");
            while((response = reader.readLine())!=null){
                //System.out.println(response);
                String[] parts = response.split(" ");

                for(String part : parts){
                    try{
                        serwerNumber = new BigInteger(part);
                    }catch(NumberFormatException e){
                        continue;
                    }
                    if(num.equals(serwerNumber)){
                        System.out.println(num.toString());
                        writer.println(num);
                    }
                    else{
                        writer.println(NetConnection.ODPOWIEDZ_DLA_OSZUSTA);
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

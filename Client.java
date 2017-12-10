import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class Client
{
    public static void main(String[] args) throws IOException {
        System.out.println("-----client-----");
        //SOCKET
        Socket s = new Socket(InetAddress.getByName("127.0.0.1"),50000);
        
        //STREAM DI SCRITTURA E LETTURA
        PrintWriter scrivi = new PrintWriter(s.getOutputStream());
        BufferedReader leggi = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        char[] answer = new char[200]; //←per contenere la stringa che riceviamo dal server
        String operando1 = "", operando2 = "";
        String comando = ""; //←per contenere il comando scritto da linea di console
        Scanner cons_in = new Scanner(System.in); //←scanner utilizzato per leggere il comando in input da console
        int flaginv = 0;
        
        if(leggi.read(answer,0,200)!=0)
        {
            //stampa della prima risposta ricevuta dal server, che conterrà il menù
            System.out.println(answer);
            Arrays.fill(answer,'\0');
            //inserimento primo comando che verrà subito controllato per non entrare nel ciclo inutilemente
            System.out.println("---------------------");
            comando = cons_in.next();
            scrivi.println(comando);
            scrivi.flush();
            while(comando.compareTo("DIS")!=0){
                //lettura e stampa risposte del server con conferma selezione
                while(leggi.read(answer,0,200)==0){
                }
                System.out.println(answer);
                
                flaginv=0;
                if((new String(answer)).contains("invalido"))
                    flaginv = 1;
                    
                Arrays.fill(answer,'\0');
                
                //se il comando non è invalido
                if(flaginv==0){
                    //se il client sceglie radice o logaritmo, invia un solo operando
                    if((comando.compareTo("RAD")==0)||(comando.compareTo("LOG")==0)){
                        //comunico l'operando
                        operando1 = cons_in.next();
                        scrivi.println(operando1);
                        scrivi.flush();
                        //aspetto il risultato
                        while(leggi.read(answer,0,200)==0){
                        }
                        System.out.println("Risultato: "+ new String(answer));
                        Arrays.fill(answer,'\0');
                    }
                    //altrimenti devo inviare due operandi
                    else{
                        //scrivo primo operando
                        operando1 = cons_in.next();
                        scrivi.println(operando1);
                        scrivi.flush();
                        //scrivo secondo operando
                        operando2 = cons_in.next();
                        scrivi.println(operando2);
                        scrivi.flush();
                        //aspetto
                        while(leggi.read(answer,0,200)==0){
                        }
                        //ottengo risultato dal server
                        System.out.println("Risultato: "+ new String(answer));
                        Arrays.fill(answer,'\0');
                    }
                }
                
                //inserimento comando controllato poi in testa
                System.out.println("---------------------");
                comando = cons_in.next();
                scrivi.println(comando);
                scrivi.flush();
            }
        }
        else
        {
            System.out.println("Connessione non accettata dal Server");
        }
        
        //aspetta l'ultima risposta del server di chiusura connessione
        while(leggi.read(answer,0,200)==0){
        }
        System.out.println(answer);
        //chiusura socket e stream
        s.close();
        scrivi.close();
        leggi.close();
    }
}

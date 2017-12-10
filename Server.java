import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Math;
import java.math.*;

class MyClient extends Thread 
{
    int i; //id del threa
    Socket s;
    public MyClient(int i,Socket s){
        this.i = i;
        this.s = s;
        this.start();
    }
    
    private static String calcolo(double op1, double op2, String option){
        String comunica= " ";
        boolean invalido=false;
        double ris;
        
        switch(option){
            case "ADD":
                ris=op1+op2;
                comunica=Double.toString(ris);
                break;
            case "SOT":
                ris=op1-op2;
                comunica=Double.toString(ris);
                break;
            case "MOL":
                ris=op1*op2;
                comunica=Double.toString(ris);
                break;
            case "DIV":
                if(op2!=0){
                    ris=op1/op2;
                    comunica=Double.toString(ris);
                }
                else
                    comunica = "Carattere invalido";    
                break;
            default:
                comunica = "Carattere invalido"; 
        }
        return comunica;
    }
    
    private static String calcolo_lograd(double n,String option){
        String comunica = "";
        double risultato;
        //radice
        if(option.compareTo("RAD")==0){
            if(n>=0){
                risultato = Math.sqrt(n);
                comunica = Double.toString(risultato);
            }
            else{
                comunica = "Carattere invalido";
            }
        }
        //logaritmo
        else{
            if(n>0){
                risultato = Math.log(n);
                comunica = Double.toString(risultato);
            }
            else{
                comunica = "Carattere invalido";
            }
        }
        return comunica;
    }
    
    public static void elabora(PrintWriter s,BufferedReader leggi,double op1,double op2,String l,String c,String mex)throws IOException{
        s.println(mex);
        s.println("Ora puoi inviare 2 operandi!");
        s.flush();
        String risultato = "";
        //riceve operandi inseriti dall'utente
        l = leggi.readLine();
        //conversione in int
        op1 = Double.parseDouble(l);
        l = leggi.readLine();
        op2 = Double.parseDouble(l);
        risultato = calcolo(op1,op2,c);
        s.println(risultato);
        s.println("Ora puoi eseguire un'altra operazione");
        s.flush();
        switch(c){
            case "ADD":
                System.out.println("add("+op1+")+("+op2+")= -> ("+risultato+")");
            break;
            
            case "SOT":
                System.out.println("sot("+op1+")-("+op2+")= -> ("+risultato+")");
            break;
            
            case "MOL":
                System.out.println("mol("+op1+")*("+op2+")= -> ("+risultato+")");
            break;
            
            case "DIV":
                System.out.println("div("+op1+")/("+op2+")= -> ("+risultato+")");
            break;
        }
        System.out.println("Operazione eseguita con successo");
        System.out.println("...in attesa di un altro comando...");
        System.out.println("");
    }
    
    public void run(){
        try{
            System.out.println("il thread (" + i + ") inizia il lavoro");
            Thread.sleep(1000);
            
            //VARIABILI---------------------------------------------------
            PrintWriter scrivi = new PrintWriter(s.getOutputStream());//scrivi sul client
            BufferedReader leggi = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String menu = "MENU \n\t ADD - addizione \n\t SOT - sottrazione \n\t DIV - divisione \n\t MOL - moltiplicazione \n\t RAD - radice quadrata \n\t LOG - logaritmo naturale \n\t"; 
            String comando = "";
            String leggi_un_operando = "";
            String risultato;
            double operando1 = 0, operando2 =0 ;
            
            //SCRITTURA
            scrivi.println("Connessione Client (" + i + ") accettata");
            scrivi.println(menu);
            scrivi.flush();
        
            System.out.println("Client connesso");
            //controllo primo comando per non entrare subito nel ciclo inutilemente
            comando=leggi.readLine();
            
            while(comando.compareTo("DIS")!=0){
            switch(comando){
                case "ADD":
                    System.out.println("Thread ("+this.i+"), scelta: addizione");
                    elabora(scrivi,leggi,operando1,operando2,leggi_un_operando,comando,"Il client ha scelto addizione");
                    break;
                    
                case "SOT":
                    System.out.println("Thread ("+this.i+"), scelta: sottrazione");
                    elabora(scrivi,leggi,operando1,operando2,leggi_un_operando,comando,"Il client ha scelto sottrazione");
                    break;
                    
                case "DIV":
                    System.out.println("Thread ("+this.i+"), scelta: divisione");
                    elabora(scrivi,leggi,operando1,operando2,leggi_un_operando,comando,"Il client ha scelto divisione");
                    break;
                    
                case "MOL":
                    System.out.println("Thread ("+this.i+"), scelta: moltiplicazione");
                    elabora(scrivi,leggi,operando1,operando2,leggi_un_operando,comando,"Il client ha scelto moltiplicazione");
                    break;
                    
                case "RAD":
                    System.out.println("Thread ("+this.i+"), scelta: radice quadrata");
                
                    scrivi.println("Scelta: radice quadrata");
                    scrivi.println("Ora puoi inviare 1 operando!");
                    scrivi.flush();
                    //aspetto l'operando
                    leggi_un_operando = leggi.readLine();
                    //conversione in int
                    operando1 = Double.parseDouble(leggi_un_operando);
                    //funzione di calcolo
                    risultato = calcolo_lograd(operando1,comando);
                    //stampa per debug
                    System.out.println("rad("+operando1+") -> ("+risultato+")");
                    System.out.println("Operazione eseguita con successo");
                    System.out.println("...in attesa di un altro comando...");
                    System.out.println("");
                    //invio risultato al client
                    scrivi.println(calcolo_lograd(operando1,comando));
                    scrivi.println("Ora puoi eseguire un'altra operazione");
                    scrivi.flush();
                    break;
                    
                case "LOG":
                    System.out.println("Thread ("+this.i+"), scelta: logaritmo naturale");
                
                    scrivi.println("Scelta: logaritmo naturale");
                    scrivi.println("Ora puoi inviare 1 operando!");
                    scrivi.flush();
                    //aspetto l'operando
                    leggi_un_operando = leggi.readLine();
                    //conversione in int
                    operando1 = Double.parseDouble(leggi_un_operando);
                    //funzione di calcolo
                    risultato = calcolo_lograd(operando1,comando);
                    //stampa per debug
                    System.out.println("log("+operando1+") -> ("+risultato+")");
                    System.out.println("Operazione eseguita con successo");
                    System.out.println("...in attesa di un altro comando...");
                    System.out.println("");
                    //invio risultato al client
                    scrivi.println(calcolo_lograd(operando1,comando));
                    scrivi.println("Ora puoi eseguire un'altra operazione");
                    scrivi.println("");
                    scrivi.flush();
                    break;
                    
                default:
                    System.out.println("Il client ha inviato un comando errato");
                    scrivi.println("Comando invalido");
                    scrivi.flush();
                    break;
            }
            comando=leggi.readLine();
        }
        
        //invia l'ultima risposta al client di chiusura connessione
        System.out.println("Il client ha chiesto la chiusura");  
        scrivi.println("Richiesta di chiusura accettata");
        scrivi.flush();
        //chiusura socket e stream
        s.close();
        scrivi.close();
        leggi.close();
        
        }catch(Exception e){
             System.out.println("il thread (" + i + ") ha avuto un problema");
        }

        System.out.println("il thread (" + i + ") finisce il lavoro");
    }
}

public class Server
{
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("--------Server---------");
        ServerSocket listener = new ServerSocket(50000);
        int i=1;
        
        while(true){
            //controlla se si è connesso allora crea il thread
            Socket s = listener.accept();
            //Connect nuovaConnessione = new Connect(s);
            if(s.isConnected()==true){
                System.out.println(i + " Client connesso");
                //crea il thread per quel client che si connette
                MyClient thread = new MyClient(i,s);
            }
            i++;
        }
    }
}

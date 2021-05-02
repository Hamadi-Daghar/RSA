

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import algorithmes.chiffrement.RSA.RabinMiller;
import coucheReseau.client.Client;
import donnees.NombreBinaire;
import exceptions.ExceptionCryptographie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed
 */
public class ProtocoleTemoinRabinMiller implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageRecu = "";
            String messageRecu2 = "";
            String messageEnvoyer = "";
            Client client = new Client();
            
            client.receiveMessage();
            messageRecu = client.receiveMessage();
            System.out.println("test");
            messageRecu2 = client.receiveMessage();
            System.out.println("test2");
            NombreBinaire nb1;
            NombreBinaire nb2;
            Boolean bool;
            
            while(!messageRecu2.equals("FIN")){
                
                nb1 = new NombreBinaire(messageRecu);
                nb2 = new NombreBinaire(messageRecu2);
                bool = RabinMiller.temoin(nb1,nb2);
                messageEnvoyer = bool.toString();
                client.sendMessage(messageEnvoyer);
                messageRecu = client.receiveMessage();
                if(messageRecu.equals("OK") || messageRecu.equals("NOK"))
                    messageRecu = client.receiveMessage();
                messageRecu2 = client.receiveMessage();
                
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


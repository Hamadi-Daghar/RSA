

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

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
public class ProtocoleEstPair implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageRecu = "";
            String messageEnvoyer = "";
            Client client = new Client();
            
            client.receiveMessage();
            messageRecu = client.receiveMessage();
            NombreBinaire nb1;
            Boolean bool;
            
            while(!messageRecu.equals("FIN")){
                
                nb1 = new NombreBinaire(messageRecu);
                bool = nb1.estPair();
                messageEnvoyer = bool.toString();
                client.sendMessage(messageEnvoyer);
                messageRecu = client.receiveMessage();
                if(messageRecu.equals("OK") || messageRecu.equals("NOK"))
                    messageRecu = client.receiveMessage();
                if (messageRecu.startsWith("Defi"))
                    messageRecu = client.receiveMessage();
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}




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
public class ProtocoleModulo implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageRecu = "";
            String messageRecu2 = "";
            String messageEnvoyer = "";
            Client client = new Client();
            
            client.receiveMessage();
            messageRecu = client.receiveMessage();
            messageRecu2 = client.receiveMessage();
            NombreBinaire nb1;
            NombreBinaire nb2;
            NombreBinaire nb3;
            
            while(!messageRecu2.equals("FIN")){
                
                nb1 = new NombreBinaire(messageRecu);
                nb2 = new NombreBinaire(messageRecu2);
                nb3 = nb1.modulo(nb2);
                messageEnvoyer = nb3.toString();
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


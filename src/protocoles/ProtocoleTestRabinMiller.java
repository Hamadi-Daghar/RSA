

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
 * @author Hamadi
 */
public class ProtocoleTestRabinMiller implements Protocole{

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
                bool = RabinMiller.testRabinMiller(nb1);
                messageEnvoyer = bool.toString();
                client.sendMessage(messageEnvoyer);
                client.receiveMessage();
                messageRecu = client.receiveMessage();
                if(messageRecu.equals("Defi valide") || messageRecu.equals("Defi echoue"))
                    messageRecu = client.receiveMessage();
                
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


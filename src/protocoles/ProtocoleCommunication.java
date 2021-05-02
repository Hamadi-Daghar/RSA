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
public class ProtocoleCommunication implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageRecu = "";
            String messageEnvoyer = "";
            Client client = new Client();
            messageRecu = client.receiveMessage();
            messageRecu = client.receiveMessage();
            int temp = 0;
            while(!messageRecu.equals("FIN")){
                try
                {
                    temp = Integer.parseInt(messageRecu) + 1;
                    messageEnvoyer = String.valueOf(temp);
                    client.sendMessage(messageEnvoyer);
                }
                catch (NumberFormatException e){
                    
                }
                    
                messageRecu = client.receiveMessage();
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
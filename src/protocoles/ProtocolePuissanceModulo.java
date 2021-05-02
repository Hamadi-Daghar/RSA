

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import algorithmes.generateurdecles.GenerateurDeClesRSA;
import coucheReseau.client.Client;
import donnees.MotBinaire;
import donnees.NombreBinaire;
import exceptions.ExceptionCryptographie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed
 */
public class ProtocolePuissanceModulo implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageRecu = "";
            String messageRecu2 = "";
            String messageRecu3 = "";
            String messageEnvoyer = "";
            Client client = new Client();
            
            client.receiveMessage();
            messageRecu = client.receiveMessage();
            messageRecu2 = client.receiveMessage();
            messageRecu3 = client.receiveMessage();
            
            NombreBinaire nb1;
            NombreBinaire nb2;
            NombreBinaire nb3;
            NombreBinaire res;
            
            while(!messageRecu2.equals("FIN")){
                
                nb1 = new NombreBinaire(messageRecu);
                nb2 = new NombreBinaire(messageRecu2);
                nb3 = new NombreBinaire(messageRecu3);
              
                
                res = nb1.puissanceModulo(nb2, nb3);
                messageEnvoyer = res.toString();
                client.sendMessage(messageEnvoyer);
                client.receiveMessage();
                messageRecu = client.receiveMessage();
                messageRecu2 = client.receiveMessage();
                if(!messageRecu2.equals("FIN"))
                    messageRecu3 = client.receiveMessage();
                
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


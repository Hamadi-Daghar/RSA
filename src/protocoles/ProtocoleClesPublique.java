

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
import donnees.cles.Cles;
import exceptions.ExceptionCryptographie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed
 */
public class ProtocoleClesPublique implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        try {
            String messageEnvoyer = "";
            String messageEnvoyer2 = "";
            String messageEnvoyer3 = "";
            String messageEnvoyer4 = "";
            String messageEnvoyer5 = "";
            String messageRecu = "";
            Client client = new Client();
            Cles clesPubliques;
            messageRecu = client.receiveMessage();
            
            
            while(!messageRecu.equals("FIN")){
                GenerateurDeClesRSA cles = new GenerateurDeClesRSA();
                clesPubliques = cles.genererClePublique();
                
                messageEnvoyer = cles.getP().toString();
                messageEnvoyer2 = cles.getQ().toString();
                messageEnvoyer3 = clesPubliques.getCle("cleRSA_N").asMotBinaire().toString();
                messageEnvoyer4 = cles.getPhi().toString();
                messageEnvoyer5 = clesPubliques.getCle("cleRSA_E").asMotBinaire().toString();
                
                client.sendMessage(messageEnvoyer);
                client.sendMessage(messageEnvoyer2);
                client.sendMessage(messageEnvoyer3);
                client.sendMessage(messageEnvoyer4);
                client.sendMessage(messageEnvoyer5);
                client.receiveMessage();
                
            }
            client.end();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoleCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


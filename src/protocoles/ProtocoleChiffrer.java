

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import algorithmes.chiffrement.AlgorithmeRSA;
import algorithmes.generateurdecles.GenerateurDeClesRSA;
import coucheReseau.client.Client;
import donnees.MotBinaire;
import donnees.NombreBinaire;
import donnees.cles.Cle;
import donnees.cles.CleBinaire;
import donnees.cles.Cles;
import donnees.messages.Message;
import donnees.messages.MessageBinaire;
import exceptions.ExceptionCryptographie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed
 */
public class ProtocoleChiffrer implements Protocole{

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
            
            MotBinaire nb1;
            MotBinaire nb2;
            MessageBinaire message;
            Message res;
            Cles clesPubliques = new Cles();
            Cles clesPrivees = new Cles();
            CleBinaire rsaN;
            CleBinaire rsaE;
            AlgorithmeRSA rsa = new AlgorithmeRSA();
            
            while(!messageRecu2.equals("FIN")){
                
                message = new MessageBinaire(new MotBinaire(messageRecu));
                rsaN = new CleBinaire(new MotBinaire(messageRecu2));
                rsaE = new CleBinaire(new MotBinaire(messageRecu3));
                
                clesPubliques.addCle("cleRSA_N", rsaN);
                clesPubliques.addCle("cleRSA_e", rsaE);
                
                res = rsa.chiffrer(message, clesPubliques, null);
                messageEnvoyer = res.asMotBinaire().toString();
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


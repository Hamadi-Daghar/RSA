

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import algorithmes.chiffrement.AlgorithmeRSA;
import algorithmes.chiffrement.RSA.ParametresRSA;
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
public class ProtocoleDechiffrer implements Protocole{

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
            CleBinaire rsaD;
            AlgorithmeRSA rsa = new AlgorithmeRSA();
            
            while(!messageRecu2.equals("FIN")){
                
                message = new MessageBinaire(new MotBinaire(messageRecu,2*ParametresRSA.getTailleCle()));
                rsaN = new CleBinaire(new MotBinaire(messageRecu2,ParametresRSA.getTailleCle()));
                rsaD = new CleBinaire(new MotBinaire(messageRecu3,ParametresRSA.getTailleCle()));
                clesPubliques = new Cles();
                clesPrivees = new Cles();
                clesPubliques.addCle("cleRSA_N", rsaN);
                clesPrivees.addCle("cleRSA_d", rsaD);
                
                res = rsa.dechiffrer(message, clesPubliques, clesPrivees);
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


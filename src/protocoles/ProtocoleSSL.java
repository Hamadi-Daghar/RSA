/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import algorithmes.chiffrement.Algorithme;
import algorithmes.chiffrement.AlgorithmeRSA;
import algorithmes.generateurdecles.GenerateurDeCles;
import algorithmes.generateurdecles.GenerateurDeClesRSA;
import donnees.MotBinaire;
import donnees.cles.Cle;
import donnees.cles.CleBinaire;
import donnees.cles.Cles;
import donnees.messages.Message;
import donnees.messages.MessageBinaire;
import donnees.messages.MessageString;
import entites.Personne;
import entites.Univers;
import exceptions.ExceptionAlgorithmeNonDefini;
import exceptions.ExceptionConversionImpossible;
import exceptions.ExceptionCryptographie;

/**
 *
 * @author Mohamed
 */
public class ProtocoleSSL implements Protocole{

    @Override
    public void executer() throws ExceptionCryptographie {
        Personne alice = new Personne("Alice");
        Personne bob = new Personne("Bob");
        
        Algorithme algoRSA = new AlgorithmeRSA();
        
        alice.setAlgorithme(algoRSA);
        bob.setAlgorithme(algoRSA);
        
        GenerateurDeCles generateur = new GenerateurDeClesRSA();
        Cles clesPubliquesB = generateur.genererClePublique();
        Cles clesPriveesB = generateur.genererClePrivee();
        Univers.addCles("clesPubliquesBob", clesPubliquesB);
        bob.setClesPrivees(clesPriveesB);
        
        Cles clesPubliquesA = generateur.genererClePublique();
        Cles clesPriveesA =  generateur.genererClePrivee();
        alice.setClesPrivees(clesPriveesA);
        
        
        try {
            
            MessageBinaire NAlice = new MessageBinaire(clesPubliquesA.getCle("cleRSA_N").asMotBinaire());
            MessageBinaire EAlice = new MessageBinaire(clesPubliquesA.getCle("cleRSA_E").asMotBinaire());
            
            
            Cles clesTemp = new Cles();
            Cle cleN = new CleBinaire(alice.chiffrer(NAlice, Univers.getCles("clesPubliquesBob")).asMotBinaire());
            Cle cleE = new CleBinaire(alice.chiffrer(EAlice, Univers.getCles("clesPubliquesBob")).asMotBinaire());
            
            clesTemp.addCle("cleRSA_N", cleN);
            clesTemp.addCle("cleRSA_E", cleE);
            
            Univers.addCles("clesRSA_Alice", clesTemp);
            
            
            Cles clesAliceBob = Univers.getCles("clesRSA_Alice");
            
            Cles clesDechiffrer = new Cles();
            Cle cleNTemp = new CleBinaire(bob.dechiffrer(new MessageBinaire(clesAliceBob.getCle("cleRSA_N").asMotBinaire()), Univers.getCles("clesPubliquesBob")).asMotBinaire());
            Cle cleETemp = new CleBinaire(bob.dechiffrer(new MessageBinaire(clesAliceBob.getCle("cleRSA_E").asMotBinaire()), Univers.getCles("clesPubliquesBob")).asMotBinaire());
            
            clesDechiffrer.addCle("cleRSA_N", cleNTemp);
            clesDechiffrer.addCle("cleRSA_E", cleETemp);
            
            Message chiffre = bob.chiffrer(new MessageString("salut"), clesDechiffrer);
            
            Univers.addMessage("message", chiffre);
            
            Message recup = Univers.getMessage("message");
            
            Message res = alice.dechiffrer(recup, Univers.getCles("clesPubliquesBob"));
                    
        } catch (ExceptionConversionImpossible ex) {
            ex.gerer();
        }
    }
    
}


import algorithmes.chiffrement.RSA.RabinMiller;
import donnees.NombreBinaire;
import exceptions.ExceptionCryptographie;
import interfaceGraphique.Interface;
import protocoles.Protocole;
import protocoles.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matthieu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExceptionCryptographie {
        Interface interfaceG = new Interface();
        interfaceG.setVisible(true);
        Protocole ssl = new ProtocoleSSL();
        ssl.executer();
        
        
        
    }
    
}

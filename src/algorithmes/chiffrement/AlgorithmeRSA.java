package algorithmes.chiffrement;

import algorithmes.chiffrement.RSA.ParametresRSA;
import donnees.MotBinaire;
import donnees.NombreBinaire;
import donnees.cles.Cles;
import donnees.messages.Message;
import donnees.messages.MessageBinaire;
import exceptions.ExceptionConversionImpossible;
import exceptions.ExceptionCryptographie;
import java.util.ArrayList;

public class AlgorithmeRSA implements Algorithme{

    
    @Override
    public String getNom() {
        return "RSA";
    }
    
    /**
     * Permet de chiffrer le morceau d'un message
     * @param morceau
     * @param clesPublique
     * @return
     * @throws ExceptionConversionImpossible 
     */
    public MotBinaire chiffrerMorceau(MotBinaire morceau, Cles clesPublique) throws ExceptionConversionImpossible {
       MotBinaire N = new MotBinaire(clesPublique.getCle("cleRSA_N").asMotBinaire().getBitSet(), ParametresRSA.getTailleCle());
       MotBinaire e = new MotBinaire(clesPublique.getCle("cleRSA_e").asMotBinaire().getBitSet(), ParametresRSA.getTailleCle());
       
       
       MotBinaire mbis = new MotBinaire(morceau.getBitSet(), ParametresRSA.getTailleMorceau());
       
       NombreBinaire m = new NombreBinaire(mbis.getBitSet());
               
       NombreBinaire crypte = m.puissanceModulo(new NombreBinaire(e.getBitSet()), new NombreBinaire(N.getBitSet()));
       
       MotBinaire message = new MotBinaire(crypte.asBitSet(), ParametresRSA.getTailleCle());
       
       return message;
    }
    
    //Déchiffre un morceau (entrée : tailleCle, sortie : tailleMorceau)
    public MotBinaire dechiffrerMorceau(MotBinaire morceau, Cles clesPublique, Cles clesPrivee) throws ExceptionConversionImpossible {
       MotBinaire N = new MotBinaire(clesPublique.getCle("cleRSA_N").asMotBinaire().getBitSet(), ParametresRSA.getTailleCle());
       MotBinaire d = new MotBinaire(clesPrivee.getCle("cleRSA_d").asMotBinaire().getBitSet(), ParametresRSA.getTailleCle());
       
       
       MotBinaire mbis = new MotBinaire(morceau.getBitSet(), ParametresRSA.getTailleCle());
       
       NombreBinaire m = new NombreBinaire(mbis.getBitSet());
               
       NombreBinaire decrypte = m.puissanceModulo(new NombreBinaire(d.getBitSet()), new NombreBinaire(N.getBitSet()));
       
       MotBinaire message = new MotBinaire(decrypte.asBitSet(), ParametresRSA.getTailleMorceau());
       
       return message;
    }

    @Override
    public Message chiffrer(Message message, Cles clesPubliques, Cles clesPrivees) throws ExceptionCryptographie {
        
       MotBinaire mbis = message.asMotBinaire();
       ArrayList<MotBinaire> morceaux;
       
       MessageBinaire res;
       morceaux = mbis.scinder(ParametresRSA.getTailleMorceau());
       
       MotBinaire test = new MotBinaire();
       for(MotBinaire mot : morceaux){
           test = test.concatenation(this.chiffrerMorceau(mot, clesPubliques));
       }
       res = new MessageBinaire(test);
       
       return res;
    }

    @Override
    public Message dechiffrer(Message message, Cles clesPubliques, Cles clesPrivees) throws ExceptionCryptographie {
        MotBinaire mbis = message.asMotBinaire();
        
        ArrayList<MotBinaire> morceaux;

        MessageBinaire res;
        morceaux = mbis.scinder(ParametresRSA.getTailleCle());
        
        
        MotBinaire test = new MotBinaire();
        for(MotBinaire mot : morceaux){
            test = test.concatenation(this.dechiffrerMorceau(mot, clesPubliques, clesPrivees));
            
        }
        res = new MessageBinaire(test);
       
       return res;
       
    }

}

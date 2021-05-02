package algorithmes.chiffrement.RSA;

import donnees.NombreBinaire;
import exceptions.ExceptionConversionImpossible;

/**
 * Description de la classe
 * @author Matthieu
 */
public class RabinMiller {

    
    //Méthode renvoyant si a est un témoin de Miller de n (preuve que n est composé)
    public static boolean temoin(NombreBinaire n, NombreBinaire a) throws ExceptionConversionImpossible {
       NombreBinaire d;
       boolean bool = true;
       int t = 0;
       NombreBinaire nbis = n.soustraction(new NombreBinaire(1));
       while(!nbis.get(t)){
           t++;
       }
       
       d = new NombreBinaire(n.asBitSet().get(t, n.getTaille()));
       NombreBinaire x = a.puissanceModulo(d, n);
       
       if(x.estEgal(new NombreBinaire(1)) || x.estEgal(n.soustraction(new NombreBinaire(1))))
           bool = false;
       
        int i = 0;
        
        while(i< t && bool){
            x = x.puissanceModulo(new NombreBinaire(2), n);
            if(x.estEgal(n.soustraction(new NombreBinaire(1)))){
                bool = false;
            }
            i++;
        }
       
       return bool;
    }
    
    //Test de RabinMiller, test probabilistiquement que n est premier (proba erreur = 1/4^k)
    public static boolean testRabinMiller(NombreBinaire n) throws ExceptionConversionImpossible {
        NombreBinaire a;
        boolean bool = true;
        int i = 0;
        while(i < 25 && bool){
            a = NombreBinaire.random(new NombreBinaire(2), n.soustraction(new NombreBinaire(1)));
            if(temoin(n, a)){
                bool = false;
            }
            i++;
        }
        return bool;
    }
    
    //Renvoie le premier nombre premier supérieur à min
    public static NombreBinaire nombrePremier(NombreBinaire min) throws ExceptionConversionImpossible {
       boolean bool;
       NombreBinaire res = min;
       if(res.get(0))
           bool = false;
       NombreBinaire temp = new NombreBinaire(1);
       while(!testRabinMiller(res)){
           res = res.addition(temp);
           temp.addition(new NombreBinaire(1));
       }
       return res;
    }
}

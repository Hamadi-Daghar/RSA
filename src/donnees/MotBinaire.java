package donnees;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Représentation d'un mot binaire
 * @author Matthieu
 */
public class MotBinaire {

    private BitSet listeBits;                   //Liste des bits
    private int taille;                         //Nombre de bits
    
    //Constructeurs standard
    public MotBinaire() {
        this.listeBits = new BitSet();
        this.taille = 0;
    }
	
    //Constructeur par paramètres avec clonage du bitset
    public MotBinaire(BitSet listeBits,int taille) {
        this.listeBits = new BitSet();
        this.taille = taille;
        for(int i=0;i<this.taille;i++) {
            this.listeBits.set(i,listeBits.get(i));
        } 
    }
	
    //Constructeur à partir d'un long
    public MotBinaire(long valeur) {
        //TODO
    }
    
    //Constructeur à partir d'un byte
    public MotBinaire(byte b) {
        //TODO
    }
    
    //Constructeur à partir d'un caractère (UTF-8)
    public MotBinaire(char c) {
        //TODO
    }
    
    //Constructeur à partir d'une succession de 1 et de 0 
    public MotBinaire(String S) {
        this();
        this.taille = S.length();
        for(int i=0;i<this.taille;i++) {
            this.listeBits.set(this.taille-i-1,S.charAt(i)=='1');
        }
    }
    
    public MotBinaire(String S,int taille) {
        this(S);
        this.taille = taille;
    }
    
    /**
     * Getter de la liste des bits
     * @return le BitSet
     */
    public BitSet getBitSet() {
        return this.listeBits;
    }
	
	
    /**
     * Getter de la taille
     * @return la taille
     */
    public int getTaille() {
        return this.taille;
    }
     
    
    /**
     * Convertion en entier non signé
     * @return un entier
     */
    public int asInteger() {
        //TODO
        return 0;
    }
    /**
     * Interprète le MotBinaire comme une succession de caractère encodé chacun sur 8bits (UTF-8)
     * @return une chaine de caractères
     */
    public String asString()  {
        //TODO
        return null;
    }
    
    //Affichage en binaire (i.e : 6 -> "110")
    @Override
    public String toString() {
        String res = "";
        for(int i=0;i<this.taille;i++) {
            if(this.listeBits.get(this.taille-i-1)) {
                res = res+"1";
            }
            else {
                res = res + "0";
            }
        }
        return res;
    }
    
    /**
     * Renvoie le résultat de this XOR mot2
     * @param mot2 2nd mot binaire
     * @return le résultat du xor
     */
    public MotBinaire xor(MotBinaire mot2) {
        //TODO
        return null;
    }
    
    /**
     * Renvoie le résultat de this + mot2 [2^32]
     * @param mot2 2nd mot binaire
     * @return le résultat de l'addition
     */
     public MotBinaire additionMod2p32(MotBinaire mot2) {
         //TODO
         return null;
     }
    
     /**
      * Scinde le mot binaire en une liste de mot binaire de taille donnée. 
      * @param tailleMorceau taille des morceaux
      * @return la liste des morceaux
      */
     public ArrayList<MotBinaire> scinder(int tailleMorceau) {
        int nbrGroupe = taille/tailleMorceau;
        ArrayList<MotBinaire> grp = new ArrayList<MotBinaire>();
        
        MotBinaire mb = null;
        int k = 0;
        BitSet bs = new BitSet(tailleMorceau);
        
        for (int i=0; i<=taille; i++){
            if(k == tailleMorceau)
            {
                mb = new MotBinaire(bs,tailleMorceau);
                grp.add(mb);
                k = 0;
            }
            bs.set(k,this.listeBits.get(i));
            k++;
        }
        
        int reste = this.taille % tailleMorceau;
        
        if( reste != 0){
        for (int i=taille-reste; i<taille; i++){
            mb = new MotBinaire(bs,reste);
            bs.set(k,this.listeBits.get(i));
            
        }
        grp.add(mb); 

        }
        
        return grp;
    }
     
     /**
      * Concaténation de deux mots binaires
      * @param mot le deuxième mot
      * @return le résultat de la concaténation
      */
     public MotBinaire concatenation(MotBinaire mot) {
         BitSet motFin= new BitSet();
        for(int i = 0; i <this.taille; i++)
        {
            motFin.set(i, this.getBitSet().get(i));
        }
        
        for(int i = 0; i < mot.getTaille(); i ++)
        {
            motFin.set(i+this.taille, mot.getBitSet().get(i));
        }
        MotBinaire temp = new MotBinaire(motFin, this.taille + mot.getTaille());
	return temp;
	
     }
     
}
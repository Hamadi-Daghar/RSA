package donnees;

import exceptions.ExceptionConversionImpossible;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Description de la classe
 * @author Matthieu
 */
public class NombreBinaire {
        
    private BitSet listeBits;
    
    //Génère un nombre binaire aléatoire de "taille" bits au maximum.
    public static NombreBinaire randomAvecTailleMax(int taille) {
       SecureRandom generateur = new SecureRandom();
       int val;
       String nb = "";
       for (int i = 0; i < taille; i++){
           val = generateur.nextInt(2);
           nb += String.valueOf(val);
       }
       NombreBinaire res = new NombreBinaire(nb);
       return res;
    }
    
    
    //renvoie un nombre aléatoire entre min (inclu) et max (non inclu)
    public static NombreBinaire random(NombreBinaire min,NombreBinaire max) throws ExceptionConversionImpossible { 
        NombreBinaire res;
        
        NombreBinaire temp = max.soustraction(min);
        
        do
        {
            res = randomAvecTailleMax(temp.getTaille());
        }while(temp.estInferieurA(res));
                
        return res.addition(min);
    }
    
   
    
    //Set un bit
    public void set(int i, boolean valeur) {
        this.listeBits.set(i,valeur);
    }
    
    //Get un bit
    public boolean get(int i) {
        return this.listeBits.get(i);
    }
    
    
    //Constructeurs standard
    public NombreBinaire() {
        this.listeBits = new BitSet();
    }
    
    //Constructeur clone
    public NombreBinaire(NombreBinaire nombre) {
        this.listeBits = new BitSet();
        for(int i=0;i<nombre.listeBits.length();i++) {
            this.listeBits.set(i,nombre.listeBits.get(i));
        } 
    }
    
    //Constructeur clone
    public NombreBinaire(BitSet bitset) {
        this.listeBits = new BitSet();
        for(int i=0;i<bitset.length();i++) {
            this.listeBits.set(i,bitset.get(i));
        } 
    }
    
    //Constructeur à partir d'un long
    public NombreBinaire(Long valeur) {
        this.listeBits = new BitSet();
        int i = 0;
        while(valeur != 0) {
            this.listeBits.set(i,valeur%2==1);
            valeur /= 2;
            i++;
        }
    }
    
    //Constructeur à partir d'un int
    public NombreBinaire(int valeur) {
        this.listeBits = new BitSet();
        int i = 0;
        while(valeur != 0) {
            this.listeBits.set(i,valeur%2==1);
            valeur /= 2;
            i++;
        }
    }
    
    //Constructeur à partir d'un byte 
    public NombreBinaire(byte b) {
        byte[] bt = new byte[1];
        bt[0] = b;
        this.listeBits = BitSet.valueOf(bt);
    }
    
    //Constructeur à partir d'une chaine de caractère binaire
    public NombreBinaire(String s) {
        this();
        for(int i=0;i<s.length();i++) {
            if(s.charAt(s.length()-i-1) == '1') {
                this.listeBits.set(i,true);
            }
        }
    }
    
    public BitSet asBitSet() {
        return this.listeBits;
    }
    
    public int getTaille() {
        return this.listeBits.length();
    }
    
    //Convertion en entier non signé 
    public int asInteger() throws ExceptionConversionImpossible{
        if(this.listeBits.length() > 31) throw new ExceptionConversionImpossible("Nombre binaire en entier (trop grand)");
        int res = 0;
        for(int i=0;i<this.listeBits.length();i++) {
            if(this.listeBits.get(i)) {
                res += Math.pow(2, i);
            }
        }
        return res;
    }
    
    //Affichage (dans le bon sens cette foi)
    @Override
    public String toString() {
        String res = "";
        for(int i=0;i<this.getTaille();i++) {
            if(this.listeBits.get(i)) {
                res = "1"+res;
            }
            else {
                res = "0"+res;
            }
        }
        if(res == "") {
            res = "0";
        }
        return res;
    }
     
     //Renvoie le résultat de l'addition de this avec mot2
     public NombreBinaire addition(NombreBinaire mot2) {
       int r = 0;
       int b1 = 0;
       int b2 = 0;
       int temp = 0;
       
       BitSet resTemp = new BitSet();
       int j = this.getTaille();
       if(j<mot2.getTaille())
           j = mot2.getTaille();
       for(int i = 0; i <= j; i++)
       {
           
           b1 = (this.get(i)) ? 1 : 0;
           b2 = (mot2.get(i)) ? 1 : 0;
           temp = r + b1 + b2;
           
           switch(temp)
           {
                case (2) :
                    resTemp.set(i, false);
                    r = 1;
                    break;
                case (3) :
                    resTemp.set(i, true);
                    r = 1;
                    break;
                case (1) :
                    resTemp.set(i, true);
                    r = 0;
                    break;
                default :
                    resTemp.set(i, false);
                    r = 0;
                    break;
           }
       }
       
       NombreBinaire res = new NombreBinaire(resTemp);
       return res;
     }
     
     //renvoie le resultat de l'addition de this avec mot3
     public NombreBinaire soustraction(NombreBinaire mot2) {
       int r = 0;
       int b1 = 0;
       int b2 = 0;
       int temp = 0;
       
       BitSet resTemp = new BitSet();
       int j = this.getTaille();
       
       if(j<mot2.getTaille())
           j = mot2.getTaille();
       
       for(int i = 0; i <= j; i++)
       {
           
           b1 = (this.get(i)) ? 1 : 0;
           b2 = (mot2.get(i)) ? 1 : 0;
           temp = b1 - b2 - r;
           
           switch(temp)
           {
                case (0) :
                    resTemp.set(i, false);
                    r = 0;
                    break;
                case (-1) :
                    resTemp.set(i, true);
                    r = 1;
                    break;
                case (-2) :
                    resTemp.set(i, false);
                    r = 1;
                    break;
                default :
                    resTemp.set(i, true);
                    r = 0;
                    break;
           }
       }
       
       NombreBinaire res = new NombreBinaire(resTemp);
       return res;
     }
     
     //Caclule le décalage de n bits (multiplie par 2^n)
     public NombreBinaire decalage(int n) {
       BitSet resTemp = new BitSet();
       for(int i = 0; i < n; i ++)
       {
           resTemp.set(i, false);
       }
       for(int i = 0; i < this.getTaille(); i++)
       {
           resTemp.set(i + n, this.get(i));
       }
       
       NombreBinaire res = new NombreBinaire(resTemp);
       return res;
     }
     
     //Calcul la multiplication de this avec mot2
     public NombreBinaire multiplication(NombreBinaire mot2) {
       int somme = 0;
       NombreBinaire resTemp = new NombreBinaire(0);
       NombreBinaire resTemp2;
       for(int i = 0; i <= mot2.getTaille(); i ++)
       {
           if(mot2.get(i) == true)
           {
               resTemp2 = this.decalage(i);
               resTemp = resTemp.addition(resTemp2);
           }
       }
       NombreBinaire res = resTemp;
       return res;
       
     }
     
     //Renvoie si this est plus petit ou égal à mot2
     public boolean estInferieurA(NombreBinaire mot2) {
       boolean bool = true;
       
       if(this.getTaille() < mot2.getTaille()){
           bool = true;
       }
       else if(this.getTaille() > mot2.getTaille())
       {
           bool = false;
       }
       else if(this.getTaille() == mot2.getTaille())
       {
           boolean sortie = false;
           
           int i = this.getTaille() - 1;
           int b1;
           int b2;
           while(!sortie && i>=0)
           {
               b1 = (this.get(i)) ? 1 : 0;
               b2 = (mot2.get(i)) ? 1 : 0;
               if (b1 < b2){
                   sortie = true;
                   bool = true;
               }
               else if(b1 > b2){
                   sortie = true;
                   bool = false;
               }
               else if (i < 0)
               {
                   sortie = true;
                   bool = true;
               }
               i--;
           }
           
       }
       return bool;
     }
     
     //Calcul this modulo mot2 via une division euclidienne
     public NombreBinaire modulo(NombreBinaire mot2) {
       NombreBinaire a = this;
       NombreBinaire b = mot2;
       NombreBinaire r = a;
       NombreBinaire q = new NombreBinaire(0);
       NombreBinaire b2;
       int n = 0;
       
       while(b.estInferieurA(r))
       {
           n = r.getTaille() - b.getTaille();
           b2 = b.decalage(n);
           
           if(!b2.estInferieurA(r))
           {
               b2 = b.decalage(n-1);
               n--;
           }
           
           r = r.soustraction(b2);
           
           q.set(n, true);
           
       }
       
       return r;
     }  

     //Calcul le quotient dans la division euclidienne de this par mot2
     public NombreBinaire quotient(NombreBinaire mot2) {
       NombreBinaire a = this;
       NombreBinaire b = mot2;
       NombreBinaire r = a;
       NombreBinaire q = new NombreBinaire(0);
       NombreBinaire b2;
       int n = 0;
       
       while(b.estInferieurA(r))
       {
           n = r.getTaille() - b.getTaille();
           b2 = b.decalage(n);
           
           if(!b2.estInferieurA(r))
           {
               b2 = b.decalage(n-1);
               n--;
           }
           
           r = r.soustraction(b2);
           
           q.set(n, true);
           
       }
       
       return q;
     }
     
     //Calcul de this^exposant modulo m par exponentiation modulaire rapide
     public NombreBinaire puissanceModulo(NombreBinaire exposant, NombreBinaire m) throws ExceptionConversionImpossible {
       NombreBinaire p = new NombreBinaire(1);
       NombreBinaire a = this;
       for(int i = 0; i < exposant.getTaille(); i++){
           if(exposant.get(i) == true){
               p = (p.multiplication(a)).modulo(m);
           }
           a = (a.multiplication(a)).modulo(m);
       }
       
       return p;
     }
     
     public boolean estEgal(NombreBinaire mot2) {
       boolean bool = false;
       if(this.getTaille() == 0 && mot2.getTaille() == 0){
           bool = true;
       }
       else if(this.getTaille() == mot2.getTaille())
       {
           boolean sortie = false;
           int i = 0;
           int b1;
           int b2;
           
           while(!sortie  && i>=0)
           {
               b1 = (this.get(i)) ? 1 : 0;
               b2 = (mot2.get(i)) ? 1 : 0;
               if (b1 != b2){
                   sortie = true;
               }
               if(i == this.getTaille() -1)
               {
                   sortie = true;
                   bool = true;
               }
               i++;
           } 
       }
       
       return bool;
     }
     
     //Renvoie si un nombre est pair
     public boolean estPair() throws ExceptionConversionImpossible {
         int temp = this.asInteger();
         boolean bool = false;
         if(temp % 2 == 0)
             bool = true;
       
       return bool;
     }
     
     
     public NombreBinaire PGCD(NombreBinaire mot2) {
       NombreBinaire a = this;
       NombreBinaire b = mot2;
       NombreBinaire b2;
       NombreBinaire n = new NombreBinaire(0);
       
       if(!b.estInferieurA(a)){
           a = mot2;
           b = this;
       }
       
       while(!b.estEgal(n))
       {
           b2 = b;
           b = a.modulo(b);
           a = b2;
           
       }
       return a;
     }
     
     //Calcul de l'inverse modulo nombre
     //Basé sur l'algo d'euclide étendu (adapté).
     public NombreBinaire inverseModulaire(NombreBinaire nombre) {
         NombreBinaire ZERO = new NombreBinaire(0);
            
         NombreBinaire n0 = new NombreBinaire(nombre);
         NombreBinaire b0 = new NombreBinaire(this);
         NombreBinaire t0 = new NombreBinaire(0);
         NombreBinaire t = new NombreBinaire(1);
         
         NombreBinaire q = n0.quotient(b0);
         NombreBinaire r = n0.modulo(b0);
         while(!r.estEgal(ZERO)) {
             NombreBinaire produit = q.multiplication(t);
             NombreBinaire memoire;
             //Gére le fait qu'un nombreBinaire ne peut pas être négatif......
             if(t0.estInferieurA(produit)) {
                memoire = nombre.soustraction(produit.soustraction(t0).modulo(nombre));
             }
             else {
                memoire = t0.soustraction(produit).modulo(nombre);  
             }
             
             t0 = t;
             t = memoire;
             n0 = b0;
             b0 = r;
             q = n0.quotient(b0);
             r = n0.modulo(b0);
         }
         return t;
     }
}

package algorithmes.generateurdecles;

import algorithmes.chiffrement.RSA.ParametresRSA;
import algorithmes.chiffrement.RSA.RabinMiller;
import donnees.MotBinaire;
import donnees.NombreBinaire;
import donnees.cles.CleBinaire;
import donnees.cles.Cles;
import exceptions.ExceptionConversionImpossible;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description de la classe
 * @author Matthieu
 */
public class GenerateurDeClesRSA implements GenerateurDeCles{

    
    private NombreBinaire P;
    private NombreBinaire Q;
    private NombreBinaire N;
    private NombreBinaire phi;
    private NombreBinaire e;
        
    @Override
    public Cles genererClePublique() {
       Cles clesPubliques = new Cles();
       NombreBinaire min = new NombreBinaire();
       NombreBinaire max = new NombreBinaire();
       NombreBinaire pTemp = new NombreBinaire();
       NombreBinaire qTemp = new NombreBinaire();
       min.set(64, true);
       max.set(65, true);
       max = max.soustraction(new NombreBinaire(1));
       
        try {
            do{
                pTemp = NombreBinaire.randomAvecTailleMax(64);
                qTemp = NombreBinaire.randomAvecTailleMax(64);
                
            }while(pTemp.estEgal(qTemp));
            
            this.P = RabinMiller.nombrePremier(pTemp);
            
            this.Q = RabinMiller.nombrePremier(qTemp);
            
            
            this.N = this.P.multiplication(this.Q);
            
            
            this.phi = (this.P.soustraction(new NombreBinaire(1))).multiplication(this.Q.soustraction(new NombreBinaire(1)));
            
            
            
            min = new NombreBinaire(3);
            max = new NombreBinaire(this.phi.soustraction(new NombreBinaire(1)));
            this.e = NombreBinaire.random(min, max);
            
            while(!this.e.PGCD(this.phi).estEgal(new NombreBinaire(1))){
                this.e = NombreBinaire.random(min, max);
                
            }
            
            
            CleBinaire cleN = new CleBinaire(new MotBinaire(this.N.asBitSet(), ParametresRSA.getTailleCle()));
            CleBinaire cleE = new CleBinaire(new MotBinaire(this.e.asBitSet(),ParametresRSA.getTailleCle()));
            
            
            clesPubliques.addCle("cleRSA_N", cleN);
            clesPubliques.addCle("cleRSA_E", cleE);
            
            return clesPubliques;
       
        } catch (ExceptionConversionImpossible ex) {
            ex.gerer();
        }
        return clesPubliques;
    }

    @Override
    public Cles genererClePrivee() {
       Cles clesPrivees = new Cles();
       
       CleBinaire cleP = new CleBinaire(new MotBinaire(this.P.asBitSet(), ParametresRSA.getTailleCle()));
       CleBinaire cleQ = new CleBinaire(new MotBinaire(this.Q.asBitSet(), ParametresRSA.getTailleCle()));
       clesPrivees.addCle("cleRSA_P", cleP);
       clesPrivees.addCle("cleRSA_Q", cleQ);
       
       NombreBinaire dBis = e.inverseModulaire(phi);
       MotBinaire d = new MotBinaire(dBis.asBitSet(), ParametresRSA.getTailleCle());
       CleBinaire cleD = new CleBinaire(d);
       clesPrivees.addCle("cleRSA_d", cleD);
       
       return clesPrivees;
    }
    
    public NombreBinaire getP(){
        return this.P;
    }
    
    public NombreBinaire getQ(){
        return this.Q;
    }
    
    public NombreBinaire getN(){
        return this.N;
    }
    
     public NombreBinaire getPhi(){
        return this.phi;
    }
     
    public NombreBinaire getE(){
        return this.e;
    }
      
    
    public void setP(NombreBinaire P){
        this.P = P;
    }
    
    public void setQ(NombreBinaire Q){
        this.Q = Q;
    }
    
    public void setE(NombreBinaire E){
        this.e = E;
    }
    
    
    public void setPhi(){
        this.phi = (this.P.soustraction(new NombreBinaire(1))).multiplication(this.Q.soustraction(new NombreBinaire(1)));
    }
    
    
    
    

    
    
}

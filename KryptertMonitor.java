import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

class KryptertMonitor{
  private int antallMeldinger;
  private ArrayList<Melding> kryptertListe;
  private int antTelegrafer;
  Lock laas = new ReentrantLock();
  Condition ikkeTomt = laas.newCondition();


  public KryptertMonitor(int antTele){
    kryptertListe = new ArrayList<Melding>();
    antallMeldinger = kryptertListe.size();
    antTelegrafer = antTele;
  }

  public void telleNed(){
    laas.lock();
    try{
      antTelegrafer--;
      if(antTelegrafer == 0){
        ikkeTomt.signalAll();
      }
    }finally{
      laas.unlock();
    }
  }

  //Pass på at ingen meldinger blir liggende for lenge

  public void leggTilKryptert(Melding m){
    laas.lock();
    try{
      kryptertListe.add(m);
      antallMeldinger++;
      ikkeTomt.signalAll();
    }catch(Exception e){
    }finally {
      laas.unlock();
    }
  }

  public Melding hentKryptert(){
    laas.lock();
    try{
      while (antallMeldinger == 0 && antTelegrafer != 0){
        ikkeTomt.await();
      }
      //Fjerner den første meldingen som har ligget der lengst.
      //Er lista tom returnerer remove(0) null.
      Melding hentetMelding = kryptertListe.remove(0);
      antallMeldinger--;
      return hentetMelding;

    }catch(Exception e){
    }
    finally {
      laas.unlock();
    }
    return null;
  }
}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

class DekryptertMonitor{
  private int antallMeldinger;
  private ArrayList<Melding> dekryptertListe;
  private int antKryptografer;
  Lock laas = new ReentrantLock();
  Condition ikkeTomt = laas.newCondition();

  public DekryptertMonitor(int antKrypt){
    dekryptertListe = new ArrayList<Melding>();
    antallMeldinger = dekryptertListe.size(); //Den er jo 0 lang ved opprettelse.
    antKryptografer = antKrypt;
  }

  public void telleNed(){
    laas.lock();
    try{
      antKryptografer--;
      if(antKryptografer == 0){
        ikkeTomt.signalAll();
      }
    }finally{
      laas.unlock();
    }
  }

  /*public int hentAntKrypt(){
    return antKryptografer;
  } */

  public void leggTilDekryptert(Melding m){
    laas.lock();
    try{
      dekryptertListe.add(m);
      antallMeldinger++;
      ikkeTomt.signalAll();
    }catch(Exception e){
    }finally {
      laas.unlock();
    }
  }

  public Melding hentDekryptert(){
    laas.lock();
    try{
      while (antallMeldinger == 0 && antKryptografer != 0){
        ikkeTomt.await();
      }
      //Fjerner den første meldingen som har ligget der lengst.
      Melding hentetMelding = dekryptertListe.remove(0);
      antallMeldinger--;

      return hentetMelding;

    }catch(Exception e){

    }
    finally {
      laas.unlock();
    }return null;
  }

}

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Collections;

class Operasjonsleder implements Runnable{
  DekryptertMonitor dmonitor;
  int antallKanaler;
  ArrayList<Melding>[] meldingListe;
  //Oppbevarer meldingene i et array av arraylister.
  //Jeg vet antall rader, men ikke antall kolonner
  //Altså jeg vet antall kanaler, men ikke hvor mange meldinger som kommer fra hver kanal.

  public Operasjonsleder(DekryptertMonitor dmonitor, int antallKanaler){
    this.dmonitor = dmonitor;
    this.antallKanaler = antallKanaler;
    meldingListe = new ArrayList[antallKanaler];

    for(int i = 0; i < antallKanaler; i++){
      meldingListe[i] = new ArrayList<Melding>();
    }
  }

  @Override
  public void run(){
    try{
      //Skal jeg sjekke om alle kryptografer er ferdige? Kan jeg ikke begynne før?
      Melding melding = dmonitor.hentDekryptert();
      System.out.println("Hentet første melding");
      //Når alle telegrafister er ferdige og lista er tom returneres melding som null.

      while (melding != null){
        int kanal = melding.hentKanalID();

        //Legger til meldingen i lista i rett kanal
        meldingListe[kanal-1].add(melding);

        //Henter ny melding
        melding = dmonitor.hentDekryptert();

      }

      //Ikke flere meldinger igjen.
      System.out.println("Liste ferdig");
      for(int i = 0; i < meldingListe.length; i++){
        Collections.sort(meldingListe[i]);
      }

      System.out.println("Liste sortert");
      skrivTilFil("fil");

      System.out.println("Leder ferdig");
    }catch (Exception e){
      System.out.println(e);
    }
  }

  /*public ArrayList<Melding>[] sorter(ArrayList<Melding>[] liste){


    for(int i = 0; i < antallKanaler; i++){

      for(int j = 0; j < liste[i].size(); j++){

        Melding m = liste[i].get(j);
        int nr = m.hentSekvensNr();
        liste[i].remove(m);

        liste[i].add(nr, m);
      }
    }return liste;

  }*/

  public void skrivTilFil(String utfil){

    try{
      for(int i = 0; i < antallKanaler; i++){
        //Vil øke navnet med 1 for hver gang
        utfil += "1";
        File f =  new File(utfil);

        PrintWriter pw = new PrintWriter(utfil, "utf-8");
        for(int j = 0; j < meldingListe[i].size(); j++){
          Melding m = meldingListe[i].get(j);
          String s = m.hentString();
          pw.append(s + "\n" + "\n");
        }
        pw.close();
      }
    }catch (Exception e){

    }

  }

}

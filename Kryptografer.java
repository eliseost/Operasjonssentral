class Kryptografer implements Runnable{

  private KryptertMonitor kmonitor;
  private DekryptertMonitor dmonitor;

  public Kryptografer(KryptertMonitor kmonitor, DekryptertMonitor dmonitor){
    this.kmonitor = kmonitor;
    this.dmonitor = dmonitor;
  }

  @Override
  public void run(){
    try{
      Melding melding = kmonitor.hentKryptert();
      //Når alle telegrafister er ferdige og lista er tom returneres melding som null.
      while(melding != null){
        String dekryptertStr = Kryptografi.dekrypter(melding.hentString());
        melding.endreString(dekryptertStr);
        //Send dekryptert melding til DekryptertMonitor
        dmonitor.leggTilDekryptert(melding);
        melding = kmonitor.hentKryptert();
      }
      //Kryptograf ferdig.
      dmonitor.telleNed();
      System.out.println("Kryptograf ferdig.");
    }catch (Exception e){

    }

  }

}

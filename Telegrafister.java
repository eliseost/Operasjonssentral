class Telegrafister implements Runnable{

  private Kanal kanal;
  private KryptertMonitor monitor;

  public Telegrafister(Kanal kanal, KryptertMonitor monitor){
    this.kanal = kanal;
    this.monitor = monitor;

  }

  @Override
  public void run(){
    try{
      String beskjed = kanal.lytt();

      int sekvensnr = 0;
      while(beskjed != null){
        Melding nyMelding = new Melding(beskjed, sekvensnr, kanal.hentId());
        //Send til monitor
        monitor.leggTilKryptert(nyMelding);
        sekvensnr++;
        beskjed = kanal.lytt();
      }
      //Når telegraf er ferdig kaller den på monitor sin telle ned for å si i fra.
      monitor.telleNed();
      System.out.println("Telegrafist ferdig");
    }catch (Exception e){
    }
  }


}

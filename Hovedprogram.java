class Hovedprogram{

  public static void main(String[] args){

    int antallTelegrafister = 3;
    int antallKryptografer = 20;
    int antallKanaler = 3;

    Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
    Kanal[] kanaler = ops.hentKanalArray();

    KryptertMonitor kryptMon = new KryptertMonitor(antallTelegrafister);
    DekryptertMonitor dekryptMon = new DekryptertMonitor(antallKryptografer);

    for(int i = 0; i < antallKanaler; i++){
      Runnable telegrafistJobb = new Telegrafister(kanaler[i], kryptMon);
      Thread telegrafist = new Thread(telegrafistJobb);
      telegrafist.start();
    }

    Runnable kryptografJobb = new Kryptografer(kryptMon, dekryptMon);

    for (int i = 0; i < antallKryptografer; i++){
      Thread kryptograf = new Thread(kryptografJobb);
      kryptograf.start();
    }
    Runnable operasjonsJobb = new Operasjonsleder(dekryptMon, antallKanaler);
    Thread operasjonsleder = new Thread(operasjonsJobb);
    operasjonsleder.start();

  }
}

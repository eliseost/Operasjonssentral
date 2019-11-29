class Melding implements Comparable<Melding>{

  private String string;
  private int sekvensnr;
  private int kanalID;


  public Melding(String s, int sekvensnr, int kanalID){
    string = s;
    this.sekvensnr = sekvensnr;
    this.kanalID = kanalID;
  }

  public String hentString(){
    return string;
  }

  public void endreString(String dekryptertS){
    string = dekryptertS;
  }

  public int hentSekvensNr(){
    return sekvensnr;
  }

  public int hentKanalID(){
    return kanalID;
  }

  @Override
  public int compareTo(Melding m){
    return sekvensnr -m.hentSekvensNr();
  }

}

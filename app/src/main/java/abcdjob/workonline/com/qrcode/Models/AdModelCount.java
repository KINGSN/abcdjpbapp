package abcdjob.workonline.com.qrcode.Models;

public class AdModelCount {
    String banneradd,Interstitialadd,rewardadd,generate_clicked;

    public AdModelCount(String banneradd, String Interstitialadd, String rewardadd,String generate_clicked) {
       this. banneradd = banneradd;
      this.  Interstitialadd = Interstitialadd;
      this.  rewardadd = rewardadd;
       this. generate_clicked=generate_clicked;
    }

    public String getGenerate_clicked() {
        return generate_clicked;
    }

    public void setGenerate_clicked(String generate_clicked) {
        this.generate_clicked = generate_clicked;
    }

    public String getBanneradd() {
        return banneradd;
    }

    public void setBanneradd(String banneradd) {
        this.banneradd = banneradd;
    }

    public String getInterstitialadd() {
        return Interstitialadd;
    }

    public void setInterstitialadd(String interstitialadd) {
        Interstitialadd = interstitialadd;
    }

    public String getRewardadd() {
        return rewardadd;
    }

    public void setRewardadd(String rewardadd) {
        this.rewardadd = rewardadd;
    }
}

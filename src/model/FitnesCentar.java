
package model;

public class FitnesCentar {
    
    private int idFitnesCentar,brZaposlenih;
    private String naziv,adresa,broj_tel,email,radnovreme;

    public FitnesCentar() {
    }

    public FitnesCentar(int brZaposlenih, String naziv, String adresa, String broj_tel, String email, String radnovreme) {
        this.brZaposlenih = brZaposlenih;
        this.naziv = naziv;
        this.adresa = adresa;
        this.broj_tel = broj_tel;
        this.email = email;
        this.radnovreme = radnovreme;
    }

    public int getIdFitnesCentar() {
        return idFitnesCentar;
    }

    public void setIdFitnesCentar(int idFitnesCentar) {
        this.idFitnesCentar = idFitnesCentar;
    }

    public int getBrZaposlenih() {
        return brZaposlenih;
    }

    public void setBrZaposlenih(int brZaposlenih) {
        this.brZaposlenih = brZaposlenih;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBroj_tel() {
        return broj_tel;
    }

    public void setBroj_tel(String broj_tel) {
        this.broj_tel = broj_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRadnovreme() {
        return radnovreme;
    }

    public void setRadnovreme(String radnovreme) {
        this.radnovreme = radnovreme;
    }

    @Override
    public String toString() {
        return "FitnesCentar{" + "idFitnesCentar=" + idFitnesCentar + ", brZaposlenih=" + brZaposlenih + ", naziv=" + naziv + ", adresa=" + adresa + ", broj_tel=" + broj_tel + ", email=" + email + ", radnovreme=" + radnovreme + '}';
    }

    
    
}

package model;


public class Zaposleni {
    
private int idZaposleni,godine;
private double plata;
private String ime,prezime,broj_tel,pol,grad,email,username,password,pozicija;

    public Zaposleni() {
    }

    public Zaposleni(int godine, double plata, String ime, String prezime, String broj_tel, String pol, String grad, String email, String username, String password, String pozicija) {
        this.godine = godine;
        this.plata = plata;
        this.ime = ime;
        this.prezime = prezime;
        this.broj_tel = broj_tel;
        this.pol = pol;
        this.grad = grad;
        this.email = email;
        this.username = username;
        this.password = password;
        this.pozicija = pozicija;
    }

    public int getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(int idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public double getPlata() {
        return plata;
    }

    public void setPlata(double plata) {
        this.plata = plata;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBroj_tel() {
        return broj_tel;
    }

    public void setBroj_tel(String broj_tel) {
        this.broj_tel = broj_tel;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    @Override
    public String toString() {
        return "Zaposleni{" + "idZaposleni=" + idZaposleni + ", godine=" + godine + ", plata=" + plata + ", ime=" + ime + ", prezime=" + prezime + ", broj_tel=" + broj_tel + ", pol=" + pol + ", grad=" + grad + ", email=" + email + ", username=" + username + ", password=" + password + ", pozicija=" + pozicija + '}';
    }

    
    


    
}

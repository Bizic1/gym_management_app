
package model;

public class Clan {
    private int idClan,godine;
    private double clanarina;
    private String ime,prezime,broj_tel,pol,grad,email,username,password,trajanje_clan,program;

    public Clan() {
    }

    public Clan(int godine, double clanarina, String ime, String prezime, String broj_tel, String pol, String grad, String email, String username, String password, String trajanje_clan, String program) {
        this.godine = godine;
        this.clanarina = clanarina;
        this.ime = ime;
        this.prezime = prezime;
        this.broj_tel = broj_tel;
        this.pol = pol;
        this.grad = grad;
        this.email = email;
        this.username = username;
        this.password = password;
        this.trajanje_clan = trajanje_clan;
        this.program = program;
    }

    

    public int getIdClan() {
        return idClan;
    }

    public void setIdClan(int idClan) {
        this.idClan = idClan;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public double getClanarina() {
        return clanarina;
    }

    public void setClanarina(double clanarina) {
        this.clanarina = clanarina;
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

    public String getTrajanje_clan() {
        return trajanje_clan;
    }

    public void setTrajanje_clan(String trajanje_clan) {
        this.trajanje_clan = trajanje_clan;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Clan{" + "idClan=" + idClan + ", godine=" + godine + ", clanarina=" + clanarina + ", ime=" + ime + ", prezime=" + prezime + ", broj_tel=" + broj_tel + ", pol=" + pol + ", grad=" + grad + ", email=" + email + ", username=" + username + ", password=" + password + ", trajanje_clan=" + trajanje_clan + ", program=" + program + '}';
    }
    
    
    
}


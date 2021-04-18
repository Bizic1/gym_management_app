
package model;

public class Racun {
    private int idRacun;
    private double iznos;
    private String ime,prezime,program,datum,email;

    public Racun() {
    }

    public Racun(double iznos, String ime, String prezime, String program, String datum, String email) {
        this.iznos = iznos;
        this.ime = ime;
        this.prezime = prezime;
        this.program = program;
        this.datum = datum;
        this.email = email;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(int idRacun) {
        this.idRacun = idRacun;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    @Override
    public String toString() {
        return "Racun{" + "idRacun=" + idRacun + ", iznos=" + iznos + ", ime=" + ime + ", prezime=" + prezime + ", program=" + program + ", datum=" + datum + '}';
    }
    
    
    
    
    
}

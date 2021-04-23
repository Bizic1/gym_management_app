package defaultpaket;

import controller.Controller;
import view.azurirajClanaFrejm;
import view.dodajZaposlenogFrejm;
import view.obrisiFrejm;
import view.prijavaFrejm;
import view.prikaziClanaFrejm;
import view.prikazivanjeClanaFrejm;
import view.promeniInfoFrejm;
import view.zaposleniFrejm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Clan;
import model.FitnesCentar;
import model.Racun;
import model.Zaposleni;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

public class koriscenjeZaposleni extends MouseAdapter {

    zaposleniFrejm frejm;
    prikaziClanaFrejm viewPrikaziClana;
    promeniInfoFrejm viewPromeniInfo;
    azurirajClanaFrejm viewAzurirajClana;
    dodajZaposlenogFrejm viewDodajZaposlenog;
    prijavaFrejm viewPrijava;
    obrisiFrejm viewObrisi;
    prikazivanjeClanaFrejm viewPrikazivanjeClana;
    int izbor;

    Clan modelClan;
    Clan modelClanProvera;
    Clan modelNoviClan;
    Racun modelRacun;
    Racun modelRacunProvera;
    FitnesCentar modelFC;
    Zaposleni modelZaposleni;
    Zaposleni modelZaposleniUser;
    Zaposleni modelZaposleniProvera;
    Zaposleni modelZaposleniEmail;
    Zaposleni modelZaposleniBroj;

    public koriscenjeZaposleni(zaposleniFrejm frejm, int izbor) {
        this.frejm = frejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(dodajZaposlenogFrejm viewDodajZaposlenog, int izbor) {
        this.viewDodajZaposlenog = viewDodajZaposlenog;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(prikaziClanaFrejm viewPrikaziClana, int izbor) {
        this.viewPrikaziClana = viewPrikaziClana;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(promeniInfoFrejm viewPromeniInfo, int izbor) {
        this.viewPromeniInfo = viewPromeniInfo;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(azurirajClanaFrejm viewAzurirajClana, int izbor) {
        this.viewAzurirajClana = viewAzurirajClana;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(zaposleniFrejm frejm, prijavaFrejm viewPrijava, int izbor) {
        this.frejm = frejm;
        this.viewPrijava = viewPrijava;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(obrisiFrejm viewObrisi, int izbor) {
        this.viewObrisi = viewObrisi;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(int izbor) {
        this.izbor = izbor;
    }

    public void obrisiClana() {
        String ime = viewObrisi.getIme_tf().getText();
        String prezime = viewObrisi.getPrezime_tf().getText();
        String id = viewObrisi.getId_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]+$");
        Matcher m, m2, m3;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(viewObrisi, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(viewObrisi, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewObrisi, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(id);
        if (m3.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        modelClan = new Clan();
        modelClan.setIme(ime);
        modelClan.setPrezime(prezime);
        modelClan.setIdClan(Integer.parseInt(id));

        Query q = sesija.createQuery("FROM Clan WHERE ime= :ime AND prezime= :prezime AND id= :id");
        q.setParameter("ime", modelClan.getIme());
        q.setParameter("prezime", modelClan.getPrezime());
        q.setParameter("id", modelClan.getIdClan());
        modelClanProvera = (Clan) q.uniqueResult();
        sesija.cancelQuery();

        if (modelClanProvera == null) {
            JOptionPane.showMessageDialog(viewObrisi, "Clan ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(viewObrisi, "Uspesno ste obrisali clana!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            viewObrisi.dispose();
            FrameControl.obrisiClanaFrejmOtvoren = 0;
            sesija.beginTransaction();
            if (modelClanProvera.getClanarina() != 0) {
                Query q2 = sesija.createQuery("FROM Racun WHERE email= :email");
                q2.setParameter("email", modelClanProvera.getEmail());
                modelRacunProvera = (Racun) q2.uniqueResult();
                sesija.cancelQuery();
                sesija.delete(modelRacunProvera);
            }
            sesija.delete(modelClanProvera);
            sesija.getTransaction().commit();
        }

        if (sesija.isOpen()) {
            sesija.close();
        }

    }

    public void prikaziClana() {
        String ime = viewPrikaziClana.getIme_tf().getText();
        String prezime = viewPrikaziClana.getPrezime_tf().getText();
        String id = viewPrikaziClana.getId_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]+$");
        Matcher m, m2, m3;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(viewPrikaziClana, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(viewPrikaziClana, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewPrikaziClana, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;

        }

        m3 = p2.matcher(id);
        if (m3.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        modelClan = new Clan();
        modelClan.setIme(ime);
        modelClan.setPrezime(prezime);
        modelClan.setIdClan(Integer.parseInt(id));

        Query q = sesija.createQuery("FROM Clan WHERE ime= :ime AND prezime= :prezime AND id= :id");
        q.setParameter("ime", modelClan.getIme());
        q.setParameter("prezime", modelClan.getPrezime());
        q.setParameter("id", modelClan.getIdClan());
        modelClanProvera = (Clan) q.uniqueResult();
        sesija.cancelQuery();

        if (modelClanProvera == null) {
            JOptionPane.showMessageDialog(viewPrikaziClana, "Clan ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
        } else {
            viewPrikaziClana.dispose();
            viewPrikazivanjeClana = new prikazivanjeClanaFrejm();
            Controller controller = new Controller(viewPrikazivanjeClana, modelClanProvera);
        }

        if (sesija.isOpen()) {
            sesija.close();
        }
    }

    public void promeniInformacije() {
        String adresa = viewPromeniInfo.getAdresa_tf().getText();
        String email = viewPromeniInfo.getEmail_tf().getText();
        String radnov = viewPromeniInfo.getRadnov_tf().getText();
        String telefon = viewPromeniInfo.getTelefon_tf().getText();
        String radnici = viewPromeniInfo.getZaposleni_tf().getText();

        Pattern p2 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p3 = Pattern.compile("^[0-9]{2}-[0-9]{2}$");
        Pattern p4 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p5 = Pattern.compile("^[0-9]{1,2}$");
        Matcher m2, m3, m4, m5;
        if (adresa.length() < 5 || adresa.length() > 30) {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Adresa nije pravilno popunjena!\n"
                    + "Adresa sadrzi od 5 do 30 karaktera! ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m2 = p2.matcher(email);
        if (!m2.find()) {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m3 = p3.matcher(radnov);
        if (!m3.find()) {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Radno vreme nije pravilno popunjeno!\n"
                    + "Radno vreme mora biti u formatu 2broja-2broja", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m4 = p4.matcher(telefon);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m5 = p5.matcher(radnici);
        if (m5.find()) {
            if (Integer.valueOf(radnici) < 1 || Integer.valueOf(radnici) > 60) {
                JOptionPane.showMessageDialog(viewPromeniInfo, "Broj radnika nije pravilno popunjen!\n"
                        + "Broj radnika ne sme biti manji od 1, ni veci od 60!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Broj radnika nije pravilno popunjen!\n"
                    + "Broj radnika sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        radnov = radnov.concat("h");
        Session sesija = HibernateUtil.getSessionFactory().openSession();
        sesija.beginTransaction();
        try {
            modelFC = (FitnesCentar) (sesija.load(FitnesCentar.class, 1));

            modelFC.setAdresa(adresa);
            modelFC.setRadnovreme(radnov);
            modelFC.setEmail(email);
            modelFC.setBrZaposlenih(Integer.parseInt(radnici));
            modelFC.setBroj_tel(telefon);
            sesija.update(modelFC);
            sesija.getTransaction().commit();

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(viewPromeniInfo, "Fitnes Centar ne postoji u bazi!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);

        }

        if (sesija.isOpen()) {
            sesija.close();
        }

        JOptionPane.showMessageDialog(viewPromeniInfo, "Uspesno ste azurirali Fitnes Centar!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);

        viewPromeniInfo.dispose();
        FrameControl.promeniInformacijeFrejmOtvoren = 0;
    }

    public void azurirajClana() {
        String ime = viewAzurirajClana.getIme_tf().getText();
        String prezime = viewAzurirajClana.getPrezime_tf().getText();
        String godine = viewAzurirajClana.getGodine_tf().getText();
        String tel = viewAzurirajClana.getTelefon_tf().getText();
        String pol = viewAzurirajClana.getPol_tf().getText();
        String id = viewAzurirajClana.getId_tf().getText();
        String program = viewAzurirajClana.getProgram_tf().getText();
        String clanarina = viewAzurirajClana.getClanarina_tf().getText();
        String grad = viewAzurirajClana.getGrad_tf().getText();
        String email = viewAzurirajClana.getEmail_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[0-9]+$");
        Pattern p6 = Pattern.compile("^[0-9]*\\.[0-9]*$");
        Matcher m, m2, m3, m4, m5, m6, m7, m8;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(id);
        if (m5.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p.matcher(grad);
        if (!m6.find()) {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p4.matcher(email);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p6.matcher(clanarina);
        if (m8.find()) {
            if (Double.parseDouble(clanarina) < 1500 || Double.parseDouble(clanarina) > 15000) {
                JOptionPane.showMessageDialog(viewAzurirajClana, "Clanarina ne sme biti manja od 1500, ni veca od 15000",
                        "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Clanarina nije pravilno popunjena!\n"
                    + "Clanarina mora biti u formatu: 1500.0", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (program.equals("Samostalni") || program.equals("Funkcionalni")
                || program.equals("Personalni")) {

        } else {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Progam sadrzi samo:"
                    + "Samostalni\n"
                    + "Personalni\n"
                    + "Funkcionalni", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        modelClan = new Clan();
        modelClan.setIdClan(Integer.parseInt(id));
        modelClan.setEmail(email);
        modelClan.setBroj_tel(tel);

        Query q = sesija.createQuery("FROM Clan WHERE id= :id");
        q.setParameter("id", modelClan.getIdClan());
        modelClanProvera = (Clan) q.uniqueResult();
        sesija.cancelQuery();

        if (modelClanProvera == null) {
            JOptionPane.showMessageDialog(viewAzurirajClana, "Clan sa tim ID-em ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        } else {

            Query q2 = sesija.createQuery("from Clan where broj_tel = :broj_tel");
            q2.setParameter("broj_tel", modelClan.getBroj_tel());
            Clan cBroj = (Clan) q2.uniqueResult();

            if (cBroj != null) {
                JOptionPane.showMessageDialog(frejm, "Vec postoji takav broj telefona!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }

            Query q3 = sesija.createQuery("from Clan where email = :email");
            q3.setParameter("email", modelClan.getEmail());
            Clan cEmail = (Clan) q3.uniqueResult();

            if (cEmail != null) {
                JOptionPane.showMessageDialog(frejm, "Vec postoji takav email!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }
            sesija.cancelQuery();

            modelNoviClan = (Clan) (sesija.load(Clan.class, modelClan.getIdClan()));
            modelNoviClan.setIme(ime);
            modelNoviClan.setPrezime(prezime);
            modelNoviClan.setGodine(Integer.parseInt(godine));
            modelNoviClan.setBroj_tel(tel);
            modelNoviClan.setPol(pol);
            modelNoviClan.setIdClan(Integer.parseInt(id));
            modelNoviClan.setProgram(program);
            modelNoviClan.setClanarina(Double.parseDouble(clanarina));
            modelNoviClan.setEmail(email);
            modelNoviClan.setGrad(grad);

            sesija.beginTransaction();
            sesija.update(modelNoviClan);
            sesija.getTransaction().commit();

            JOptionPane.showMessageDialog(viewAzurirajClana, "Uspesno ste azurirali korisnika!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            viewAzurirajClana.dispose();
            FrameControl.azurirajClanaFrejmOtvoren = 0;
        }

        if (sesija.isOpen()) {
            sesija.close();
        }
    }

    public void dodajZaposlenog() {

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        String ime = viewDodajZaposlenog.getIme_tf().getText();
        String prezime = viewDodajZaposlenog.getPrezime_tf().getText();
        String godine = viewDodajZaposlenog.getGodine_tf().getText();
        String tel = viewDodajZaposlenog.getTelefon_tf().getText();
        String pol = viewDodajZaposlenog.getPol_tf().getText();
        String username = viewDodajZaposlenog.getUsername_tf().getText();
        String password = viewDodajZaposlenog.getPassword_tf().getText();
        String password2 = viewDodajZaposlenog.getPassword2_tf().getText();
        String grad = viewDodajZaposlenog.getGrad_tf().getText();
        String email = viewDodajZaposlenog.getEmail_tf().getText();
        String plata = viewDodajZaposlenog.getPlata_tf().getText();
        String pozicija = viewDodajZaposlenog.getPozicija_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
        Pattern p6 = Pattern.compile("^[a-z0-9]{6,10}$");
        Pattern p7 = Pattern.compile("^[0-9]*\\.[0-9]*$");
        Matcher m, m2, m3, m4, m5, m6, m7, m8, m9;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(viewDodajZaposlenog, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(viewDodajZaposlenog, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(username);
        if (!m5.find()) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Korisnicko ime nije pravilno popunjeno!\n"
                    + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p6.matcher(password);
        if (m6.find()) {
            if (password.equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(viewDodajZaposlenog, "Korisnicko ime i sifra ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Sifra nije pravilno popunjena!\n"
                    + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password2.equals(password)) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Ponovljena sifra mora da bude jednaka sa sifrom!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p.matcher(grad);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p4.matcher(email);
        if (!m8.find()) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m9 = p7.matcher(plata);
        if (m9.find()) {
            if (Double.parseDouble(plata) < 50000 || Double.parseDouble(plata) > 150000) {
                JOptionPane.showMessageDialog(viewDodajZaposlenog, "Plata nije pravilno popunjena!\n"
                        + "Plata ne sme biti manja od 50000 ni veca od 150000!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Plata nije pravilno popunjena!\n"
                    + "Plata mora biti u formatu 50000.0", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pozicija.equals("Vlasnik") || pozicija.equals("Trener") || pozicija.equals("Menadzer")) {
        } else {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Pozicija nije pravilno popunjena!\n"
                    + "Pozicije: Vlasnik,Trener,Menadzer!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelZaposleni = new Zaposleni(Integer.parseInt(godine), Double.parseDouble(plata), ime, prezime, tel, pol, grad, email, username, password, pozicija);

        Query q = sesija.createQuery("from Zaposleni where username = :username");
        q.setParameter("username", modelZaposleni.getUsername());
        modelZaposleniUser = (Zaposleni) q.uniqueResult();

        if (modelZaposleniUser != null) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Vec postoji takav username!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }

        Query q2 = sesija.createQuery("from Zaposleni where broj_tel = :broj_tel");
        q2.setParameter("broj_tel", modelZaposleni.getBroj_tel());
        modelZaposleniBroj = (Zaposleni) q2.uniqueResult();
        if (modelZaposleniBroj != null) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Vec postoji takav broj telefona!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }

        Query q3 = sesija.createQuery("from Zaposleni where email = :email");
        q3.setParameter("email", modelZaposleni.getEmail());
        modelZaposleniEmail = (Zaposleni) q3.uniqueResult();
        if (modelZaposleniEmail != null) {
            JOptionPane.showMessageDialog(viewDodajZaposlenog, "Vec postoji takav email!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }
        sesija.cancelQuery();
        if (sesija.isOpen()) {
            sesija.close();
        }

        Session novaSesija = HibernateUtil.getSessionFactory().openSession();
        novaSesija.beginTransaction();
        novaSesija.save(modelZaposleni);
        novaSesija.getTransaction().commit();

        if (novaSesija.isOpen()) {
            novaSesija.close();
        }
        JOptionPane.showMessageDialog(viewDodajZaposlenog, "Uspesno ste dodali zaposlenog!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
        FrameControl.dodajZaposlenogFrejmOtvoren = 0;
        viewDodajZaposlenog.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (izbor == 1) {
            if (FrameControl.obrisiClanaFrejmOtvoren == 0) {
                viewObrisi = new obrisiFrejm();
                Controller c = new Controller(viewObrisi);
                FrameControl.obrisiClanaFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za brisanje clanova!");
            }
        } else if (izbor == 2) {
            if (FrameControl.prikaziFrejmOtvoren == 0) {
                viewPrikaziClana = new prikaziClanaFrejm();
                Controller c = new Controller(viewPrikaziClana);
                FrameControl.prikaziFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za prikaz!");
            }
        } else if (izbor == 3) {
            prikaziClana();
        } else if (izbor == 4) {
            if (FrameControl.promeniInformacijeFrejmOtvoren == 0) {
                viewPromeniInfo = new promeniInfoFrejm();
                Controller c = new Controller(viewPromeniInfo);
                FrameControl.promeniInformacijeFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za menjanje informacija!");
            }
        } else if (izbor == 5) {
            promeniInformacije();
        } else if (izbor == 6) {
            if (FrameControl.azurirajClanaFrejmOtvoren == 0) {
                viewAzurirajClana = new azurirajClanaFrejm();
                Controller c = new Controller(viewAzurirajClana);
                FrameControl.azurirajClanaFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za azuriranje clanova!");
            }
        } else if (izbor == 7) {
            azurirajClana();
        } else if (izbor == 8) {
            if (FrameControl.dodajZaposlenogFrejmOtvoren == 0) {
                Session sesija = HibernateUtil.getSessionFactory().openSession();

                String proveriusername = viewPrijava.getUser_tf().getText();
                String proveripassword = viewPrijava.getPassword_tf().getText();

                modelZaposleni = new Zaposleni();
                modelZaposleni.setUsername(proveriusername);
                modelZaposleni.setPassword(proveripassword);

                Query query = sesija.createQuery("FROM Zaposleni WHERE username= :username AND password= :password");
                query.setParameter("username", modelZaposleni.getUsername());
                query.setParameter("password", modelZaposleni.getPassword());
                modelZaposleniProvera = (Zaposleni) query.uniqueResult();
                sesija.cancelQuery();

                if (!modelZaposleniProvera.getPozicija().equals("Vlasnik")) {
                    JOptionPane.showMessageDialog(frejm, "Samo vlasnik teretane moze da dodaje zaposlene!", "Fitnes Centar Aleksandar", JOptionPane.ERROR_MESSAGE);
                    if (sesija.isOpen()) {
                        sesija.close();
                    }
                    return;
                }
                if (sesija.isOpen()) {
                    sesija.close();
                }

                viewDodajZaposlenog = new dodajZaposlenogFrejm();
                Controller c = new Controller(viewDodajZaposlenog);

                FrameControl.dodajZaposlenogFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za dodavanje zaposlenih!");
            }
        } else if (izbor == 9) {
            dodajZaposlenog();
        } else if (izbor == 10) {
            obrisiClana();
        }

    }

}

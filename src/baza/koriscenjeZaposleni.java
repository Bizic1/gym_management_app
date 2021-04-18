package baza;

import defaultpaket.FrameController;
import defaultpaket.HibernateUtil;
import frejmovi.azurirajClanaFrejm;
import frejmovi.dodajZaposlenogFrejm;
import frejmovi.obrisiFrejm;
import frejmovi.prijavaFrejm;
import frejmovi.prikaziClanaFrejm;
import frejmovi.prikazivanjeClanaFrejm;
import frejmovi.promeniInfoFrejm;
import frejmovi.zaposleniFrejm;
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
    prikaziClanaFrejm pcfrejm;
    promeniInfoFrejm pifrejm;
    azurirajClanaFrejm acfrejm;
    dodajZaposlenogFrejm dzfrejm;
    prijavaFrejm pfrejm;
    obrisiFrejm ofrejm;
    int izbor;

    public koriscenjeZaposleni(zaposleniFrejm frejm, int izbor) {
        this.frejm = frejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(dodajZaposlenogFrejm dzfrejm, int izbor) {
        this.dzfrejm = dzfrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(prikaziClanaFrejm pcfrejm, int izbor) {
        this.pcfrejm = pcfrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(promeniInfoFrejm pifrejm, int izbor) {
        this.pifrejm = pifrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(azurirajClanaFrejm acfrejm, int izbor) {
        this.acfrejm = acfrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(zaposleniFrejm frejm, prijavaFrejm pfrejm, int izbor) {
        this.frejm = frejm;
        this.pfrejm = pfrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(obrisiFrejm ofrejm, int izbor) {
        this.ofrejm = ofrejm;
        this.izbor = izbor;
    }

    public koriscenjeZaposleni(int izbor) {
        this.izbor = izbor;
    }

    public void obrisiClana() {
        String ime = ofrejm.getIme_tf().getText();
        String prezime = ofrejm.getPrezime_tf().getText();
        String id = ofrejm.getId_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]+$");
        Matcher m, m2,m3;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(ofrejm, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(ofrejm, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(ofrejm, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        m3 = p2.matcher(id);
        if (m3.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(acfrejm, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(acfrejm, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        Clan c = new Clan();
        c.setIme(ime);
        c.setPrezime(prezime);
        c.setIdClan(Integer.parseInt(id));

        Query q = sesija.createQuery("FROM Clan WHERE ime= :ime AND prezime= :prezime AND id= :id");
        q.setParameter("ime", c.getIme());
        q.setParameter("prezime", c.getPrezime());
        q.setParameter("id", c.getIdClan());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Clan cProvera = (Clan) q.uniqueResult();
        //
        sesija.cancelQuery();

        if (cProvera == null) {
            JOptionPane.showMessageDialog(ofrejm, "Clan ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(ofrejm, "Uspesno ste obrisali clana!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            ofrejm.dispose();
            FrameController.obrisiClanaFrejmOtvoren = 0;
            sesija.beginTransaction();      
            if (cProvera.getClanarina() != 0) {
                Query q2 = sesija.createQuery("FROM Racun WHERE email= :email");
                q2.setParameter("email", cProvera.getEmail());
                //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
                Racun rProvera = (Racun) q2.uniqueResult();
                //
                sesija.cancelQuery();
                sesija.delete(rProvera);
            }
            sesija.delete(cProvera);
            sesija.getTransaction().commit();
        }

        if (sesija.isOpen()) {
            sesija.close();
        }

    }

    public void prikaziClana() {
        String ime = pcfrejm.getIme_tf().getText();
        String prezime = pcfrejm.getPrezime_tf().getText();
        String id = pcfrejm.getId_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]+$");
        Matcher m,m2,m3;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(pcfrejm, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(pcfrejm, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(pcfrejm, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;

        }
        
        m3 = p2.matcher(id);
        if (m3.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(acfrejm, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(acfrejm, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        Clan c = new Clan();
        c.setIme(ime);
        c.setPrezime(prezime);
        c.setIdClan(Integer.parseInt(id));

        Query q = sesija.createQuery("FROM Clan WHERE ime= :ime AND prezime= :prezime AND id= :id");
        q.setParameter("ime", c.getIme());
        q.setParameter("prezime", c.getPrezime());
        q.setParameter("id", c.getIdClan());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Clan cProvera = (Clan) q.uniqueResult();
        //
        sesija.cancelQuery();

        if (cProvera == null) {
            JOptionPane.showMessageDialog(pcfrejm, "Clan ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
        } else {
            pcfrejm.dispose();
            new prikazivanjeClanaFrejm(cProvera).setVisible(true);
        }

        if (sesija.isOpen()) {
            sesija.close();
        }
    }

    public void promeniInformacije() {
        String adresa = pifrejm.getAdresa_tf().getText();
        String email = pifrejm.getEmail_tf().getText();
        String radnov = pifrejm.getRadnov_tf().getText();
        String telefon = pifrejm.getTelefon_tf().getText();
        String radnici = pifrejm.getZaposleni_tf().getText();

        Pattern p2 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p3 = Pattern.compile("^[0-9]{2}-[0-9]{2}$");
        Pattern p4 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p5 = Pattern.compile("^[0-9]{1,2}$");
        Matcher m2, m3, m4, m5;
        if (adresa.length() < 5 || adresa.length() > 30) {
            JOptionPane.showMessageDialog(pifrejm, "Adresa nije pravilno popunjena!\n"
                    + "Adresa sadrzi od 5 do 30 karaktera! ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m2 = p2.matcher(email);
        if (!m2.find()) {
            JOptionPane.showMessageDialog(pifrejm, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m3 = p3.matcher(radnov);
        if (!m3.find()) {
            JOptionPane.showMessageDialog(pifrejm, "Radno vreme nije pravilno popunjeno!\n"
                    + "Radno vreme mora biti u formatu 2broja-2broja", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m4 = p4.matcher(telefon);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(pifrejm, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        m5 = p5.matcher(radnici);
        if (m5.find()) {
            if (Integer.valueOf(radnici) < 1 || Integer.valueOf(radnici) > 60) {
                JOptionPane.showMessageDialog(pifrejm, "Broj radnika nije pravilno popunjen!\n"
                        + "Broj radnika ne sme biti manji od 1, ni veci od 60!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(pifrejm, "Broj radnika nije pravilno popunjen!\n"
                    + "Broj radnika sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }
        radnov = radnov.concat("h");
        Session sesija = HibernateUtil.getSessionFactory().openSession();
        sesija.beginTransaction();
        try {
            FitnesCentar fc = (FitnesCentar) (sesija.load(FitnesCentar.class, 1));

            fc.setAdresa(adresa);
            fc.setRadnovreme(radnov);
            fc.setEmail(email);
            fc.setBrZaposlenih(Integer.parseInt(radnici));
            fc.setBroj_tel(telefon);
            sesija.update(fc);
            sesija.getTransaction().commit();

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(pifrejm, "Fitnes Centar ne postoji u bazi!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);

        }

        if (sesija.isOpen()) {
            sesija.close();
        }

        JOptionPane.showMessageDialog(pifrejm, "Uspesno ste azurirali Fitnes Centar!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);

        pifrejm.dispose();
        FrameController.promeniInformacijeFrejmOtvoren = 0;
    }

    public void azurirajClana() {
        String ime = acfrejm.getIme_tf().getText();
        String prezime = acfrejm.getPrezime_tf().getText();
        String godine = acfrejm.getGodine_tf().getText();
        String tel = acfrejm.getTelefon_tf().getText();
        String pol = acfrejm.getPol_tf().getText();
        String id = acfrejm.getId_tf().getText();
        String program = acfrejm.getProgram_tf().getText();
        String clanarina = acfrejm.getClanarina_tf().getText();
        String grad = acfrejm.getGrad_tf().getText();
        String email = acfrejm.getEmail_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[0-9]+$");
        //preuzeto sa https://stackoverflow.com/questions/10516967/regexp-for-a-double
        Pattern p6 = Pattern.compile("^[0-9]*\\.[0-9]*$");
        ///
        Matcher m, m2, m3, m4, m5, m6, m7, m8;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(acfrejm, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(acfrejm, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(acfrejm, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(acfrejm, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(acfrejm, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(acfrejm, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(acfrejm, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(id);
        if (m5.find()) {
            if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 99) {
                JOptionPane.showMessageDialog(acfrejm, "ID mora biti izmedju 1 i 99!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(acfrejm, "ID sadrzi samo brojeve!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p.matcher(grad);
        if (!m6.find()) {
            JOptionPane.showMessageDialog(acfrejm, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p4.matcher(email);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(acfrejm, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p6.matcher(clanarina);
        if (m8.find()) {
            if (Double.parseDouble(clanarina) < 1500 || Double.parseDouble(clanarina) > 15000) {
                JOptionPane.showMessageDialog(acfrejm, "Clanarina ne sme biti manja od 1500, ni veca od 15000",
                        "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(acfrejm, "Clanarina nije pravilno popunjena!\n"
                    + "Clanarina mora biti u formatu: 1500.0", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (program.equals("Samostalni") || program.equals("Funkcionalni")
                || program.equals("Personalni")) {

        } else {
            JOptionPane.showMessageDialog(acfrejm, "Progam sadrzi samo:"
                    + "Samostalni\n"
                    + "Personalni\n"
                    + "Funkcionalni", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        Clan c = new Clan();
        c.setIdClan(Integer.parseInt(id));
        c.setEmail(email);
        c.setBroj_tel(tel);

        Query q = sesija.createQuery("FROM Clan WHERE id= :id");
        q.setParameter("id", c.getIdClan());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Clan cProvera = (Clan) q.uniqueResult();
        //
        sesija.cancelQuery();

        if (cProvera == null) {
            JOptionPane.showMessageDialog(acfrejm, "Clan sa tim ID-em ne postoji!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        } else {

            Query q2 = sesija.createQuery("from Clan where broj_tel = :broj_tel");
            q2.setParameter("broj_tel", c.getBroj_tel());
            //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
            Clan cBroj = (Clan) q2.uniqueResult();
            //

            if (cBroj != null) {
                JOptionPane.showMessageDialog(frejm, "Vec postoji takav broj telefona!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }

            Query q3 = sesija.createQuery("from Clan where email = :email");
            q3.setParameter("email", c.getEmail());
            //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
            Clan cEmail = (Clan) q3.uniqueResult();
            //

            if (cEmail != null) {
                JOptionPane.showMessageDialog(frejm, "Vec postoji takav email!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }
            sesija.cancelQuery();

            Clan cNovi = (Clan) (sesija.load(Clan.class, c.getIdClan()));
            cNovi.setIme(ime);
            cNovi.setPrezime(prezime);
            cNovi.setGodine(Integer.parseInt(godine));
            cNovi.setBroj_tel(tel);
            cNovi.setPol(pol);
            cNovi.setIdClan(Integer.parseInt(id));
            cNovi.setProgram(program);
            cNovi.setClanarina(Double.parseDouble(clanarina));
            cNovi.setEmail(email);
            cNovi.setGrad(grad);

            sesija.beginTransaction();
            sesija.update(cNovi);
            sesija.getTransaction().commit();

            JOptionPane.showMessageDialog(acfrejm, "Uspesno ste azurirali korisnika!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            acfrejm.dispose();
            FrameController.azurirajClanaFrejmOtvoren = 0;
        }

        if (sesija.isOpen()) {
            sesija.close();
        }
    }

    public void dodajZaposlenog() {

        Session sesija = HibernateUtil.getSessionFactory().openSession();

        String ime = dzfrejm.getIme_tf().getText();
        String prezime = dzfrejm.getPrezime_tf().getText();
        String godine = dzfrejm.getGodine_tf().getText();
        String tel = dzfrejm.getTelefon_tf().getText();
        String pol = dzfrejm.getPol_tf().getText();
        String username = dzfrejm.getUsername_tf().getText();
        String password = dzfrejm.getPassword_tf().getText();
        String password2 = dzfrejm.getPassword2_tf().getText();
        String grad = dzfrejm.getGrad_tf().getText();
        String email = dzfrejm.getEmail_tf().getText();
        String plata = dzfrejm.getPlata_tf().getText();
        String pozicija = dzfrejm.getPozicija_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
        Pattern p6 = Pattern.compile("^[a-z0-9]{6,10}$");
        //regex preuzet sa https://stackoverflow.com/questions/10516967/regexp-for-a-double
        Pattern p7 = Pattern.compile("^[0-9]*\\.[0-9]*$");
        ///
        Matcher m, m2, m3, m4, m5, m6, m7, m8, m9;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(dzfrejm, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(dzfrejm, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(dzfrejm, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(dzfrejm, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(username);
        if (!m5.find()) {
            JOptionPane.showMessageDialog(dzfrejm, "Korisnicko ime nije pravilno popunjeno!\n"
                    + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p6.matcher(password);
        if (m6.find()) {
            if (password.equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(dzfrejm, "Korisnicko ime i sifra ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Sifra nije pravilno popunjena!\n"
                    + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password2.equals(password)) {
            JOptionPane.showMessageDialog(dzfrejm, "Ponovljena sifra mora da bude jednaka sa sifrom!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p.matcher(grad);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(dzfrejm, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p4.matcher(email);
        if (!m8.find()) {
            JOptionPane.showMessageDialog(dzfrejm, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m9 = p7.matcher(plata);
        if (m9.find()) {
            if (Double.parseDouble(plata) < 50000 || Double.parseDouble(plata) > 150000) {
                JOptionPane.showMessageDialog(dzfrejm, "Plata nije pravilno popunjena!\n"
                        + "Plata ne sme biti manja od 50000 ni veca od 150000!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Plata nije pravilno popunjena!\n"
                    + "Plata mora biti u formatu 50000.0", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pozicija.equals("Vlasnik") || pozicija.equals("Trener") || pozicija.equals("Menadzer")) {
        } else {
            JOptionPane.showMessageDialog(dzfrejm, "Pozicija nije pravilno popunjena!\n"
                    + "Pozicije: Vlasnik,Trener,Menadzer!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Zaposleni z = new Zaposleni(Integer.parseInt(godine), Double.parseDouble(plata), ime, prezime, tel, pol, grad, email, username, password, pozicija);

        Query q = sesija.createQuery("from Zaposleni where username = :username");
        q.setParameter("username", z.getUsername());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Zaposleni zUsername = (Zaposleni) q.uniqueResult();
        //

        if (zUsername != null) {
            JOptionPane.showMessageDialog(dzfrejm, "Vec postoji takav username!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }

        Query q2 = sesija.createQuery("from Zaposleni where broj_tel = :broj_tel");
        q2.setParameter("broj_tel", z.getBroj_tel());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Zaposleni zBroj = (Zaposleni) q2.uniqueResult();
        //

        if (zBroj != null) {
            JOptionPane.showMessageDialog(dzfrejm, "Vec postoji takav broj telefona!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }

        Query q3 = sesija.createQuery("from Zaposleni where email = :email");
        q3.setParameter("email", z.getEmail());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Zaposleni zEmail = (Zaposleni) q3.uniqueResult();
        //

        if (zEmail != null) {
            JOptionPane.showMessageDialog(dzfrejm, "Vec postoji takav email!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            sesija.close();
            return;
        }
        sesija.cancelQuery();
        if (sesija.isOpen()) {
            sesija.close();
        }

        Session novaSesija = HibernateUtil.getSessionFactory().openSession();
        novaSesija.beginTransaction();
        novaSesija.save(z);
        novaSesija.getTransaction().commit();

        if (novaSesija.isOpen()) {
            novaSesija.close();
        }
        JOptionPane.showMessageDialog(dzfrejm, "Uspesno ste dodali zaposlenog!", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
        FrameController.dodajZaposlenogFrejmOtvoren = 0;
        dzfrejm.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (izbor == 1) {
            if (FrameController.obrisiClanaFrejmOtvoren == 0) {
                new obrisiFrejm().setVisible(true);
                FrameController.obrisiClanaFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za brisanje clanova!");
            }
        } else if (izbor == 2) {
            if (FrameController.prikaziFrejmOtvoren == 0) {
                new prikaziClanaFrejm().setVisible(true);
                FrameController.prikaziFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za prikaz!");
            }
        } else if (izbor == 3) {
            prikaziClana();
        } else if (izbor == 4) {
            if (FrameController.promeniInformacijeFrejmOtvoren == 0) {
                new promeniInfoFrejm().setVisible(true);
                FrameController.promeniInformacijeFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za menjanje informacija!");
            }
        } else if (izbor == 5) {
            promeniInformacije();
        } else if (izbor == 6) {
            if (FrameController.azurirajClanaFrejmOtvoren == 0) {
                new azurirajClanaFrejm().setVisible(true);
                FrameController.azurirajClanaFrejmOtvoren = 1;
            } else {
                JOptionPane.showMessageDialog(frejm, "Vec je otvoren prozor za azuriranje clanova!");
            }
        } else if (izbor == 7) {
            azurirajClana();
        } else if (izbor == 8) {
            if (FrameController.dodajZaposlenogFrejmOtvoren == 0) {
                Session sesija = HibernateUtil.getSessionFactory().openSession();

                String proveriusername = pfrejm.getUser_tf().getText();
                String proveripassword = pfrejm.getPassword_tf().getText();

                Zaposleni z1 = new Zaposleni();
                z1.setUsername(proveriusername);
                z1.setPassword(proveripassword);

                Query query = sesija.createQuery("FROM Zaposleni WHERE username= :username AND password= :password");
                query.setParameter("username", z1.getUsername());
                query.setParameter("password", z1.getPassword());
                //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
                Zaposleni z1Provera = (Zaposleni) query.uniqueResult();
                //
                sesija.cancelQuery();

                if (!z1Provera.getPozicija().equals("Vlasnik")) {
                    JOptionPane.showMessageDialog(frejm, "Samo vlasnik teretane moze da dodaje zaposlene!", "Fitnes Centar Aleksandar", JOptionPane.ERROR_MESSAGE);
                    if (sesija.isOpen()) {
                        sesija.close();
                    }
                    return;
                }
                if (sesija.isOpen()) {
                    sesija.close();
                }
                new dodajZaposlenogFrejm().setVisible(true);
                FrameController.dodajZaposlenogFrejmOtvoren = 1;
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

package baza;

import defaultpaket.FrameController;
import defaultpaket.HibernateUtil;
import frejmovi.registracijaFrejm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Clan;
import org.hibernate.Query;
import org.hibernate.Session;

public class registracijaKorisnika extends MouseAdapter {

    registracijaFrejm frejm;

    public registracijaKorisnika(registracijaFrejm frejm) {
        this.frejm = frejm;
    }

    public void registrujKorisnika() {
        String ime = frejm.getIme_tf().getText();
        String prezime = frejm.getPrezime_tf().getText();
        String godine = frejm.getGodine_tf().getText();
        String tel = frejm.getTelefon_tf().getText();
        String pol = frejm.getPol_tf().getText();
        String username = frejm.getUsername_tf().getText();
        String password = frejm.getPassword_tf().getText();
        String password2 = frejm.getPassword2_tf().getText();
        String grad = frejm.getGrad_tf().getText();
        String email = frejm.getEmail_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
        Pattern p6 = Pattern.compile("^[a-z0-9]{6,10}$");
        Matcher m, m2, m3, m4, m5, m6, m7, m8;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(frejm, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(frejm, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(frejm, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(frejm, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(frejm, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(frejm, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(frejm, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(username);
        if (!m5.find()) {
            JOptionPane.showMessageDialog(frejm, "Korisnicko ime nije pravilno popunjeno!\n"
                    + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p6.matcher(password);
        if (m6.find()) {
            if (password.equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(frejm, "Korisnicko ime i sifra ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(frejm, "Sifra nije pravilno popunjena!\n"
                    + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password2.equals(password)) {
            JOptionPane.showMessageDialog(frejm, "Ponovljena sifra mora da bude jednaka sa sifrom!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p.matcher(grad);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(frejm, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p4.matcher(email);
        if (!m8.find()) {
            JOptionPane.showMessageDialog(frejm, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcije = {"Da", "Ne"};

        int x = JOptionPane.showOptionDialog(frejm, "Da li sigurno zelite da se registrujete?",
                "Registracija",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcije, opcije[0]);

        if (x == 0) {

            Clan c = new Clan(Integer.parseInt(godine), 0, ime, prezime, tel, pol, grad, email, username, password, "Nema", "Nema");
            Session sesija = HibernateUtil.getSessionFactory().openSession();

            Query q = sesija.createQuery("from Clan where username = :username");
            q.setParameter("username", c.getUsername());
             //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
            Clan cUsername = (Clan) q.uniqueResult();
            //

            if (cUsername != null) {
                JOptionPane.showMessageDialog(frejm, "Vec postoji takav username!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }

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
            if (sesija.isOpen()) {
                sesija.close();
            }

            Session novaSesija = HibernateUtil.getSessionFactory().openSession();
            novaSesija.beginTransaction();
            novaSesija.save(c);
            novaSesija.getTransaction().commit();

            if (novaSesija.isOpen()) {
                novaSesija.close();
            }
            JOptionPane.showMessageDialog(frejm, "Uspesno ste se registrovali!", "Registracija", JOptionPane.INFORMATION_MESSAGE);
            FrameController.RegistracioniFrejmOtvoren = 0;
            frejm.dispose();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        registrujKorisnika();
    }

}

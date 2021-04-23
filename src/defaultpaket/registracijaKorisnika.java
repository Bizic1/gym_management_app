package defaultpaket;

import view.registracijaFrejm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Clan;
import org.hibernate.Query;
import org.hibernate.Session;

public class registracijaKorisnika extends MouseAdapter {

    registracijaFrejm view;
    Clan model;
    Clan modelUser;
    Clan modelBroj;
    Clan modelEmail;

    public registracijaKorisnika(registracijaFrejm view) {
        this.view = view;
    }

    public void registrujKorisnika() {
        String ime = view.getIme_tf().getText();
        String prezime = view.getPrezime_tf().getText();
        String godine = view.getGodine_tf().getText();
        String tel = view.getTelefon_tf().getText();
        String pol = view.getPol_tf().getText();
        String username = view.getUsername_tf().getText();
        String password = view.getPassword_tf().getText();
        String password2 = view.getPassword2_tf().getText();
        String grad = view.getGrad_tf().getText();
        String email = view.getEmail_tf().getText();

        Pattern p = Pattern.compile("^[A-Z]{1}[a-z]{2,14}$");
        Pattern p2 = Pattern.compile("^[0-9]{1,2}$");
        Pattern p3 = Pattern.compile("^[0-9]{3}/[0-9]{3}/[0-9]{3,4}$");
        Pattern p4 = Pattern.compile("^[a-zA-Z0-9]{2,15}+@[a-zA-Z]{3,7}+.[a-zA-Z]{2,3}$");
        Pattern p5 = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
        Pattern p6 = Pattern.compile("^[a-z0-9]{6,10}$");
        Matcher m, m2, m3, m4, m5, m6, m7, m8;

        m = p.matcher(ime);
        if (!m.find()) {
            JOptionPane.showMessageDialog(view, "Ime nije pravilno popunjeno!\n"
                    + "Ime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m2 = p.matcher(prezime);
        if (m2.find()) {
            if (prezime.equals(ime)) {
                JOptionPane.showMessageDialog(view, "Ime i prezime ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(view, "Prezime nije pravilno popunjeno!\n"
                    + "Prezime mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m3 = p2.matcher(godine);
        if (m3.find()) {
            if (Integer.parseInt(godine) < 8 || Integer.parseInt(godine) > 75) {
                JOptionPane.showMessageDialog(view, "Godine moraju biti izmedju 8 i 75!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(view, "Godine nisu pravilno popunjene!\n"
                    + "Godine mogu da sadrze najvise 2 broja ", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m4 = p3.matcher(tel);
        if (!m4.find()) {
            JOptionPane.showMessageDialog(view, "Broj telefona nije pravilno popunjen!\n"
                    + "Broj telefona mora biti u formatu ***/***/***!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pol.equals("Musko") || pol.equals("Zensko")) {
        } else {
            JOptionPane.showMessageDialog(view, "Pol nije pravilno popunjen!\n"
                    + "Pol mora biti popunjen sa: 'Musko' ili 'Zensko' !\n"
                    + "Pocetno slovo mora biti veliko!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m5 = p5.matcher(username);
        if (!m5.find()) {
            JOptionPane.showMessageDialog(view, "Korisnicko ime nije pravilno popunjeno!\n"
                    + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m6 = p6.matcher(password);
        if (m6.find()) {
            if (password.equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(view, "Korisnicko ime i sifra ne smeju biti isti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(view, "Sifra nije pravilno popunjena!\n"
                    + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password2.equals(password)) {
            JOptionPane.showMessageDialog(view, "Ponovljena sifra mora da bude jednaka sa sifrom!",
                    "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m7 = p.matcher(grad);
        if (!m7.find()) {
            JOptionPane.showMessageDialog(view, "Grad nije pravilno popunjen!\n"
                    + "Grad mora sadrzati samo slova, od 3 do 20 karaktera!\n"
                    + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        m8 = p4.matcher(email);
        if (!m8.find()) {
            JOptionPane.showMessageDialog(view, "Email nije pravilno popunjen!\n"
                    + "Email mora biti u formatu: slovabrojevi(2-15)@slova(3-7).slova(2,3)", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcije = {"Da", "Ne"};

        int x = JOptionPane.showOptionDialog(view, "Da li sigurno zelite da se registrujete?",
                "Registracija",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcije, opcije[0]);

        if (x == 0) {

            model = new Clan(Integer.parseInt(godine), 0, ime, prezime, tel, pol, grad, email, username, password, "Nema", "Nema");
            Session sesija = HibernateUtil.getSessionFactory().openSession();

            Query q = sesija.createQuery("from Clan where username = :username");
            q.setParameter("username", model.getUsername());

            modelUser = (Clan) q.uniqueResult();

            if (modelUser != null) {
                JOptionPane.showMessageDialog(view, "Vec postoji takav username!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }

            Query q2 = sesija.createQuery("from Clan where broj_tel = :broj_tel");
            q2.setParameter("broj_tel", model.getBroj_tel());

            modelBroj = (Clan) q2.uniqueResult();

            if (modelBroj != null) {
                JOptionPane.showMessageDialog(view, "Vec postoji takav broj telefona!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }

            Query q3 = sesija.createQuery("from Clan where email = :email");
            q3.setParameter("email", model.getEmail());
            modelEmail = (Clan) q3.uniqueResult();

            if (modelEmail != null) {
                JOptionPane.showMessageDialog(view, "Vec postoji takav email!", "Registracija", JOptionPane.ERROR_MESSAGE);
                sesija.close();
                return;
            }
            sesija.cancelQuery();
            if (sesija.isOpen()) {
                sesija.close();
            }

            Session novaSesija = HibernateUtil.getSessionFactory().openSession();
            novaSesija.beginTransaction();
            novaSesija.save(model);
            novaSesija.getTransaction().commit();

            if (novaSesija.isOpen()) {
                novaSesija.close();
            }
            JOptionPane.showMessageDialog(view, "Uspesno ste se registrovali!", "Registracija", JOptionPane.INFORMATION_MESSAGE);
            FrameControl.RegistracioniFrejmOtvoren = 0;
            view.dispose();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        registrujKorisnika();
    }

}

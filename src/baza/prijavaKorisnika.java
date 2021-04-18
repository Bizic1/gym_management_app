package baza;

import defaultpaket.FrameController;
import defaultpaket.HibernateUtil;
import frejmovi.clanFrejm;
import frejmovi.prijavaFrejm;
import frejmovi.zaposleniFrejm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import model.Clan;
import model.Zaposleni;
import org.hibernate.Query;
import org.hibernate.Session;

public class prijavaKorisnika extends MouseAdapter {

    prijavaFrejm frejm;

    public prijavaKorisnika(prijavaFrejm frejm) {
        this.frejm = frejm;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (FrameController.clanFrejmOtvoren == 1 || FrameController.zaposleniFrejmOtvoren == 1) {
            JOptionPane.showMessageDialog(frejm, "Vec ste ulogovani!",
                    "Prijava", JOptionPane.ERROR_MESSAGE);
            FrameController.prijavaFrejmOtvoren = 0;
            frejm.dispose();
        } else {
            String username = frejm.getUser_tf().getText();
            String password = frejm.getPassword_tf().getText();

            Pattern p = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
            Pattern p1 = Pattern.compile("^[a-z0-9]{6,10}$");
            Matcher m, m2;

            m = p.matcher(username);
            if (!m.find()) {
                JOptionPane.showMessageDialog(frejm, "Korisnicko ime nije pravilno uneseno!\n"
                        + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                        + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Prijava", JOptionPane.ERROR_MESSAGE);
                return;
            }

            m2 = p1.matcher(password);
            if (m2.find()) {
                if (password.equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(frejm, "Korisnicko ime i sifra ne smeju biti isti!", "Registracija", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(frejm, "Sifra nije pravilno unesena!\n"
                        + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                        "Prijava", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] opcije = {"Clan", "Zaposleni"};

            int x = JOptionPane.showOptionDialog(frejm, "Izaberite da li ste clan ili zaposleni",
                    "Prijava",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcije, opcije[0]);

            if (x == 0) {
                ulogujClana(username, password);
            } else if (x == 1) {
                ulogujZaposlenog(username, password);
            }
        }
    }

    public void ulogujClana(String username, String password) {

        Clan c = new Clan();
        c.setUsername(username);
        c.setPassword(password);

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        Query q = sesija.createQuery("FROM Clan WHERE username= :username AND password= :password");
        q.setParameter("username", c.getUsername());
        q.setParameter("password", c.getPassword());
         //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Clan cProvera = (Clan) q.uniqueResult();
        //

        if (cProvera == null) {
            JOptionPane.showMessageDialog(frejm, "Neuspesna prijava!\n"
                    + "Proverite da li ste lepo uneli korisnicko ime ili sifru!", "Prijava", JOptionPane.ERROR_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(frejm, "Uspesno ste se prijavili!", "Prijava", JOptionPane.INFORMATION_MESSAGE);
            FrameController.prijavaFrejmOtvoren = 0;
            frejm.dispose();
            new clanFrejm(frejm).setVisible(true);
            FrameController.clanFrejmOtvoren = 1;
        }
        sesija.cancelQuery();

        if (sesija.isOpen()) {
            sesija.close();
        }

    }

    public void ulogujZaposlenog(String username, String password) {

        Zaposleni z = new Zaposleni();
        z.setUsername(username);
        z.setPassword(password);

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        Query q = sesija.createQuery("FROM Zaposleni WHERE username= :username AND password= :password");
        q.setParameter("username", z.getUsername());
        q.setParameter("password", z.getPassword());
         //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Zaposleni zProvera = (Zaposleni) q.uniqueResult();
        //
        if (zProvera == null) {
            JOptionPane.showMessageDialog(frejm, "Neuspesna prijava!\n"
                    + "Proverite da li ste lepo uneli korisnicko ime ili sifru!", "Prijava", JOptionPane.ERROR_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(frejm, "Uspesno ste se prijavili!", "Prijava", JOptionPane.INFORMATION_MESSAGE);
            FrameController.prijavaFrejmOtvoren = 0;
            frejm.dispose();
            new zaposleniFrejm(frejm).setVisible(true);
            FrameController.zaposleniFrejmOtvoren = 1;
        }
        sesija.cancelQuery();

        if (sesija.isOpen()) {
            sesija.close();
        }

    }
}

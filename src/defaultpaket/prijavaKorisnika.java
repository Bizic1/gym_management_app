package defaultpaket;

import controller.Controller;
import view.clanFrejm;
import view.prijavaFrejm;
import view.zaposleniFrejm;
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

    prijavaFrejm view;
    zaposleniFrejm viewZaposleni;
    clanFrejm viewClan;
    Clan modelClan;
    Clan modelClanProvera;
    Zaposleni modelZaposleni;
    Zaposleni modelZaposleniProvera;

    public prijavaKorisnika(prijavaFrejm view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (FrameControl.clanFrejmOtvoren == 1 || FrameControl.zaposleniFrejmOtvoren == 1) {
            JOptionPane.showMessageDialog(view, "Vec ste ulogovani!",
                    "Prijava", JOptionPane.ERROR_MESSAGE);
            FrameControl.prijavaFrejmOtvoren = 0;
            view.dispose();
        } else {
            String username = view.getUser_tf().getText();
            String password = view.getPassword_tf().getText();

            Pattern p = Pattern.compile("^[A-Z]{1}[a-z0-9]{5,10}$");
            Pattern p1 = Pattern.compile("^[a-z0-9]{6,10}$");
            Matcher m, m2;

            m = p.matcher(username);
            if (!m.find()) {
                JOptionPane.showMessageDialog(view, "Korisnicko ime nije pravilno uneseno!\n"
                        + "Korisnicko ime moze da sadrzi slova i brojeve, od 6 do 10 karaktera!\n"
                        + "Pocetno slovo mora biti veliko, ostala ne smeju biti!", "Prijava", JOptionPane.ERROR_MESSAGE);
                return;
            }

            m2 = p1.matcher(password);
            if (m2.find()) {
                if (password.equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(view, "Korisnicko ime i sifra ne smeju biti isti!", "Registracija", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(view, "Sifra nije pravilno unesena!\n"
                        + "Sifra moze da sadrzi samo mala slova i brojeve, od 6 do 10 karaktera!",
                        "Prijava", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] opcije = {"Clan", "Zaposleni"};

            int x = JOptionPane.showOptionDialog(view, "Izaberite da li ste clan ili zaposleni",
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

        modelClan  = new Clan();
        modelClan.setUsername(username);
        modelClan.setPassword(password);

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        Query q = sesija.createQuery("FROM Clan WHERE username= :username AND password= :password");
        q.setParameter("username", modelClan.getUsername());
        q.setParameter("password", modelClan.getPassword());
        modelClanProvera = (Clan) q.uniqueResult();
        if (modelClanProvera == null) {
            JOptionPane.showMessageDialog(view, "Neuspesna prijava!\n"
                    + "Proverite da li ste lepo uneli korisnicko ime ili sifru!", "Prijava", JOptionPane.ERROR_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(view, "Uspesno ste se prijavili!", "Prijava", JOptionPane.INFORMATION_MESSAGE);
            FrameControl.prijavaFrejmOtvoren = 0;
            view.dispose();
            viewClan = new clanFrejm();
            Controller c = new Controller(viewClan, view);
            FrameControl.clanFrejmOtvoren = 1;
        }
        sesija.cancelQuery();
        if (sesija.isOpen()) {
            sesija.close();
        }

    }

    public void ulogujZaposlenog(String username, String password) {

        modelZaposleni = new Zaposleni();
        modelZaposleni.setUsername(username);
        modelZaposleni.setPassword(password);

        Session sesija = HibernateUtil.getSessionFactory().openSession();
        Query q = sesija.createQuery("FROM Zaposleni WHERE username= :username AND password= :password");
        q.setParameter("username", modelZaposleni.getUsername());
        q.setParameter("password", modelZaposleni.getPassword());
        modelZaposleniProvera = (Zaposleni) q.uniqueResult();
        if (modelZaposleniProvera == null) {
            JOptionPane.showMessageDialog(view, "Neuspesna prijava!\n"
                    + "Proverite da li ste lepo uneli korisnicko ime ili sifru!", "Prijava", JOptionPane.ERROR_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(view, "Uspesno ste se prijavili!", "Prijava", JOptionPane.INFORMATION_MESSAGE);
            FrameControl.prijavaFrejmOtvoren = 0;
            view.dispose();
            viewZaposleni = new zaposleniFrejm();
            Controller c = new Controller(viewZaposleni, view);
            FrameControl.zaposleniFrejmOtvoren = 1;
        }
        sesija.cancelQuery();

        if (sesija.isOpen()) {
            sesija.close();
        }

    }
}

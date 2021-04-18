package baza;

import defaultpaket.HibernateUtil;
import frejmovi.clanFrejm;
import frejmovi.prijavaFrejm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Clan;
import model.Racun;
import org.hibernate.Query;
import org.hibernate.Session;

public class koriscenjeClan extends MouseAdapter {

    clanFrejm frejm;
    prijavaFrejm pfrejm;
    int izbor;

    public koriscenjeClan(clanFrejm frejm, prijavaFrejm pfrejm, int izbor) {
        this.frejm = frejm;
        this.pfrejm = pfrejm;
        this.izbor = izbor;
        updateTF();
    }

    //preuzeto sa: https://stackoverflow.com/questions/6168498/how-to-put-a-timer-on-a-jlabel-to-update-itself-every-second
    public void updateTF() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unos = frejm.getProgram_tf().getText();
                if (unos.equals("1")) {
                    frejm.getClanarina_tf().setText("1500.00");
                } else if (unos.equals("2")) {
                    frejm.getClanarina_tf().setText("3000.00");
                } else if (unos.equals("3")) {
                    frejm.getClanarina_tf().setText("5000.00");
                }
            }
        });
        timer.start();
    }
    //

    @Override
    public void mouseClicked(MouseEvent e) {
        if (izbor == 1) {
            potvrdiClanarinu();
        } else {
            pregledPrograma();
        }
    }

    public void pregledPrograma() {

        JOptionPane.showMessageDialog(frejm, "Cena clanarine:\n"
                + "Program: 1 - Samostalni trening, cena : 1500din" + "\n"
                + "Program: 2 - Personalni trening, cena : 3000din" + "\n"
                + "Program: 3 - Funkcionalni trening, cena : 5000din", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);

    }

    public void potvrdiClanarinu() {
        Session sesija = HibernateUtil.getSessionFactory().openSession();
        
        //preuzeto sa: https://stackoverflow.com/questions/4905416/how-do-i-add-one-month-to-current-date-in-java
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        //
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String unos = frejm.getProgram_tf().getText();

        Clan c = new Clan();
        c.setUsername(pfrejm.getUser_tf().getText());
        c.setPassword(pfrejm.getPassword_tf().getText());

        Query q = sesija.createQuery("FROM Clan WHERE username= :username AND password= :password");
        q.setParameter("username", c.getUsername());
        q.setParameter("password", c.getPassword());
        //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Clan cProvera = (Clan) q.uniqueResult();
        //
        sesija.cancelQuery();

        Query q2 = sesija.createQuery("FROM Racun WHERE email= :email");
        q2.setParameter("email", cProvera.getEmail());
         //preuzeto sa : https://stackoverflow.com/questions/6469246/how-to-check-if-a-data-exist-on-a-table-using-hibernate
        Racun rProvera = (Racun) q2.uniqueResult();
        //
        sesija.cancelQuery();
        if (rProvera == null) {       

            if (unos.equals("1") || unos.equals("2") || unos.equals("3")) {
                String upis = "";
                if (unos.equals("1")) {
                    upis = "Samostalni";
                }
                if (unos.equals("2")) {
                     upis = "Personalni";
                }
                if (unos.equals("3")) {
                    upis = "Funkcionalni";
                }
                Racun r = new Racun(Double.parseDouble(frejm.getClanarina_tf().getText()), cProvera.getIme(),
                        cProvera.getPrezime(), upis, sdf.format(cal2.getTime()), cProvera.getEmail());
                sesija.beginTransaction();
                sesija.save(r);
                sesija.getTransaction().commit();

                sesija.beginTransaction();
                Clan noviClan = (Clan) sesija.load(Clan.class, cProvera.getIdClan());
                noviClan.setTrajanje_clan(sdf.format(cal.getTime()));
                noviClan.setProgram(upis);
                noviClan.setClanarina(Double.parseDouble(frejm.getClanarina_tf().getText()));
                sesija.save(noviClan);
                sesija.getTransaction().commit();

                if (sesija.isOpen()) {
                    sesija.close();
                }

                JOptionPane.showMessageDialog(frejm, "Uspesno ste platili clanarinu!\n"
                        + "Program: " + upis + "\n"
                        + "Cena: " + frejm.getClanarina_tf().getText() + "\n"
                        + "Trajanje clanarine: " + sdf.format(cal.getTime()), "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frejm, "Neuspesna potvrda clanarine!\n"
                        + "Program mora da bude u formatu 1,2 ili 3!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frejm, "Neuspesna potvrda clanarine!\n"
                    + "Vec imate aktivnu clanarinu!\n"
                    + "Clanarina traje do: " + cProvera.getTrajanje_clan(), "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            if (sesija.isOpen()) {
                sesija.close();
            }
        }

    }
}

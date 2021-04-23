package defaultpaket;

import view.clanFrejm;
import view.prijavaFrejm;
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

    clanFrejm viewClan;
    prijavaFrejm viewPrijava;
    int izbor;
    Clan modelClan;
    Clan modelClanProvera;
    Clan modelnoviClan;
    Racun modelRacun;
    Racun modelRacunProvera;
    

    public koriscenjeClan(clanFrejm viewClan, prijavaFrejm viewPrijava, int izbor) {
        this.viewClan = viewClan;
        this.viewPrijava = viewPrijava;
        this.izbor = izbor;
        updateTF();
    }

    public void updateTF() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unos = viewClan.getProgram_tf().getText();
                if (unos.equals("1")) {
                    viewClan.getClanarina_tf().setText("1500.00");
                } else if (unos.equals("2")) {
                    viewClan.getClanarina_tf().setText("3000.00");
                } else if (unos.equals("3")) {
                    viewClan.getClanarina_tf().setText("5000.00");
                }
            }
        });
        timer.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (izbor == 1) {
            potvrdiClanarinu();
        } else {
            pregledPrograma();
        }
    }

    public void pregledPrograma() {

        JOptionPane.showMessageDialog(viewClan, "Cena clanarine:\n"
                + "Program: 1 - Samostalni trening, cena : 1500din" + "\n"
                + "Program: 2 - Personalni trening, cena : 3000din" + "\n"
                + "Program: 3 - Funkcionalni trening, cena : 5000din", "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);

    }

    public void potvrdiClanarinu() {
        Session sesija = HibernateUtil.getSessionFactory().openSession();
        
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String unos = viewClan.getProgram_tf().getText();

        modelClan = new Clan();
        modelClan.setUsername(viewPrijava.getUser_tf().getText());
        modelClan.setPassword(viewPrijava.getPassword_tf().getText());

        Query q = sesija.createQuery("FROM Clan WHERE username= :username AND password= :password");
        q.setParameter("username", modelClan.getUsername());
        q.setParameter("password", modelClan.getPassword());
        modelClanProvera = (Clan) q.uniqueResult();
        
        sesija.cancelQuery();

        Query q2 = sesija.createQuery("FROM Racun WHERE email= :email");
        q2.setParameter("email", modelClanProvera.getEmail());
       
        modelRacunProvera = (Racun) q2.uniqueResult();
        sesija.cancelQuery();
        if (modelRacunProvera == null) {       

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
                modelRacun = new Racun(Double.parseDouble(viewClan.getClanarina_tf().getText()), modelClanProvera.getIme(),
                        modelClanProvera.getPrezime(), upis, sdf.format(cal2.getTime()), modelClanProvera.getEmail());
                sesija.beginTransaction();
                sesija.save(modelRacun);
                sesija.getTransaction().commit();

                sesija.beginTransaction();
                modelnoviClan = (Clan) sesija.load(Clan.class, modelClanProvera.getIdClan());
                modelnoviClan.setTrajanje_clan(sdf.format(cal.getTime()));
                modelnoviClan.setProgram(upis);
                modelnoviClan.setClanarina(Double.parseDouble(viewClan.getClanarina_tf().getText()));
                sesija.save(modelnoviClan);
                sesija.getTransaction().commit();

                if (sesija.isOpen()) {
                    sesija.close();
                }

                JOptionPane.showMessageDialog(viewClan, "Uspesno ste platili clanarinu!\n"
                        + "Program: " + upis + "\n"
                        + "Cena: " + viewClan.getClanarina_tf().getText() + "\n"
                        + "Trajanje clanarine: " + sdf.format(cal.getTime()), "Fitnes Centar Ahilej", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(viewClan, "Neuspesna potvrda clanarine!\n"
                        + "Program mora da bude u formatu 1,2 ili 3!", "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(viewClan, "Neuspesna potvrda clanarine!\n"
                    + "Vec imate aktivnu clanarinu!\n"
                    + "Clanarina traje do: " + modelClanProvera.getTrajanje_clan(), "Fitnes Centar Ahilej", JOptionPane.ERROR_MESSAGE);
            if (sesija.isOpen()) {
                sesija.close();
            }
        }

    }
}

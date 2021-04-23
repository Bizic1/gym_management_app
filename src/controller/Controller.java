package controller;

import defaultpaket.FrameControl;
import defaultpaket.HibernateUtil;
import defaultpaket.koriscenjeClan;
import defaultpaket.koriscenjeZaposleni;
import defaultpaket.prijavaKorisnika;
import defaultpaket.registracijaKorisnika;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import model.Clan;
import model.FitnesCentar;
import org.hibernate.Session;
import view.azurirajClanaFrejm;
import view.clanFrejm;
import view.dodajZaposlenogFrejm;
import view.oNamaFrejm;
import view.obrisiFrejm;
import view.pocetniFrejm;
import view.prijavaFrejm;
import view.prikaziClanaFrejm;
import view.prikazivanjeClanaFrejm;
import view.promeniInfoFrejm;
import view.registracijaFrejm;
import view.zaposleniFrejm;

public class Controller {

    azurirajClanaFrejm viewAzurirajClana;
    dodajZaposlenogFrejm viewDodajZaposlenog;
    oNamaFrejm viewONama;
    obrisiFrejm viewObrisi;
    pocetniFrejm viewPocetni;
    prijavaFrejm viewPrijava;
    prikaziClanaFrejm viewPrikaziClana;
    prikazivanjeClanaFrejm viewPrikazivanjeClana;
    promeniInfoFrejm viewPromeniInfo;
    registracijaFrejm viewRegistracija;
    zaposleniFrejm viewZaposleni;
    clanFrejm viewClan;

    Clan modelClan;
    FitnesCentar modelFC;

    public Controller() {
        viewPocetni = new pocetniFrejm();
        viewPocetni.setVisible(true);
        pocetniListeneri();
    }

    public Controller(zaposleniFrejm viewZaposleni, prijavaFrejm viewPrijava) {
        this.viewZaposleni = viewZaposleni;
        this.viewZaposleni.setVisible(true);
        this.viewPrijava = viewPrijava;
        zaposleniListeneri();
    }

    public Controller(azurirajClanaFrejm viewAzurirajClana) {
        this.viewAzurirajClana = viewAzurirajClana;
        this.viewAzurirajClana.setVisible(true);
        azurirajClanaListeneri();
    }

    public Controller(prikaziClanaFrejm viewPrikaziClana) {
        this.viewPrikaziClana = viewPrikaziClana;
        this.viewPrikaziClana.setVisible(true);
        prikaziClanaListeneri();
    }

    public Controller(clanFrejm viewClan, prijavaFrejm viewPrijava) {
        this.viewClan = viewClan;
        this.viewClan.setVisible(true);
        this.viewPrijava = viewPrijava;
        clanListeneri();
    }

    public Controller(prikazivanjeClanaFrejm viewPrikazivanjeClana, Clan modelClan) {
        this.viewPrikazivanjeClana = viewPrikazivanjeClana;
        izmeniLabele(modelClan);
        this.viewPrikazivanjeClana.setVisible(true);
        this.modelClan = modelClan;
        prikazivanjeClanaListeneri();

    }

    public Controller(promeniInfoFrejm viewPromeniInfo) {
        this.viewPromeniInfo = viewPromeniInfo;
        this.viewPromeniInfo.setVisible(true);
        promeniInfoListeneri();
    }

    public Controller(dodajZaposlenogFrejm viewDodajZaposlenog) {
        this.viewDodajZaposlenog = viewDodajZaposlenog;
        this.viewDodajZaposlenog.setVisible(true);
        dodajZaposlenogListeneri();
    }

    public Controller(obrisiFrejm viewObrisi) {
        this.viewObrisi = viewObrisi;
        this.viewObrisi.setVisible(true);
        obrisiListeneri();
    }

    public void obrisiListeneri() {
        viewObrisi.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewObrisi.dispose();
                FrameControl.obrisiClanaFrejmOtvoren = 0;
            }

        });
        viewObrisi.getObrisi_btn().addMouseListener(new koriscenjeZaposleni(viewObrisi, 10));
        viewObrisi.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.obrisiClanaFrejmOtvoren = 0;
            }

        });
    }

    public void dodajZaposlenogListeneri() {
        viewDodajZaposlenog.getDodaj_btn().addMouseListener(new koriscenjeZaposleni(viewDodajZaposlenog, 9));
        viewDodajZaposlenog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.dodajZaposlenogFrejmOtvoren = 0;
                viewDodajZaposlenog.dispose();
            }

        });
        viewDodajZaposlenog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.dodajZaposlenogFrejmOtvoren = 0;
            }

        });
    }

    public void promeniInfoListeneri() {
        viewPromeniInfo.getPotvrdi_btn().addMouseListener(new koriscenjeZaposleni(viewPromeniInfo, 5));
        viewPromeniInfo.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewPromeniInfo.dispose();
                FrameControl.promeniInformacijeFrejmOtvoren = 0;
            }

        });
        viewPromeniInfo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.promeniInformacijeFrejmOtvoren = 0;
            }

        });
    }

    public void prikazivanjeClanaListeneri() {
        viewPrikazivanjeClana.getZatvori_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.prikaziFrejmOtvoren = 0;
                viewPrikazivanjeClana.dispose();
            }

        });
        viewPrikazivanjeClana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.prikaziFrejmOtvoren = 0;
            }

        });
    }

    public void prikaziClanaListeneri() {
        viewPrikaziClana.getPrikazi_btn().addMouseListener(new koriscenjeZaposleni(viewPrikaziClana, 3));
        viewPrikaziClana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.prikaziFrejmOtvoren = 0;
            }

        });
        viewPrikaziClana.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewPrikaziClana.dispose();
                FrameControl.prikaziFrejmOtvoren = 0;
            }

        });
    }

    public void azurirajClanaListeneri() {
        viewAzurirajClana.getAzuriraj_btn().addMouseListener(new koriscenjeZaposleni(viewAzurirajClana, 7));
        viewAzurirajClana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.azurirajClanaFrejmOtvoren = 0;
            }

        });
    }

    public void clanListeneri() {
        viewClan.getPregled_btn().addMouseListener(new koriscenjeClan(viewClan, viewPrijava, 2));
        viewClan.getPotvrdi_btn().addMouseListener(new koriscenjeClan(viewClan, viewPrijava, 1));
        viewClan.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.clanFrejmOtvoren = 0;
            }

        });
        viewClan.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.clanFrejmOtvoren = 0;
                viewClan.dispose();
            }

        });
    }

    public void zaposleniListeneri() {
        viewZaposleni.getObrisi_btn().addMouseListener(new koriscenjeZaposleni(viewZaposleni, 1));
        viewZaposleni.getDodaj_btn().addMouseListener(new koriscenjeZaposleni(viewZaposleni, viewPrijava, 8));
        viewZaposleni.getPromeni_btn().addMouseListener(new koriscenjeZaposleni(viewZaposleni, 4));
        viewZaposleni.getPrikazi_btn().addMouseListener(new koriscenjeZaposleni(2));
        viewZaposleni.getAzuriraj_btn().addMouseListener(new koriscenjeZaposleni(viewZaposleni, 6));
        viewZaposleni.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.zaposleniFrejmOtvoren = 0;
            }

        });
    }

    public void oNamaListeneri() {
        viewONama.getZatvori_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.oNamaFrejmOtvoren = 0;
                viewONama.dispose();
            }
        });

        viewONama.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.oNamaFrejmOtvoren = 0;
            }

        });
    }

    public void prijavaListeneri() {
        viewPrijava.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.prijavaFrejmOtvoren = 0;
            }

        });

        viewPrijava.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.prijavaFrejmOtvoren = 0;
                viewPrijava.dispose();
            }

        });

        viewPrijava.getPrijava_btn().addMouseListener(new prijavaKorisnika(viewPrijava));
    }

    public void registracijaListeneri() {
        viewRegistracija.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameControl.RegistracioniFrejmOtvoren = 0;
            }
        });

        viewRegistracija.getRegistracija_btn().addMouseListener(new registracijaKorisnika(viewRegistracija));
        viewRegistracija.getOtkazi_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FrameControl.RegistracioniFrejmOtvoren = 0;
                viewRegistracija.dispose();
            }

        });
    }

    public void pocetniListeneri() {
        viewPocetni.getRegistracija_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameControl.RegistracioniFrejmOtvoren == 0) {
                    viewRegistracija = new registracijaFrejm();
                    viewRegistracija.setVisible(true);
                    registracijaListeneri();
                    FrameControl.RegistracioniFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor za Registraciju!");
                }

            }

        });
        viewPocetni.getOnama_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameControl.oNamaFrejmOtvoren == 0) {
                    viewONama = new oNamaFrejm();
                    oNamaListeneri();
                    izmeniFitnesCentar();
                    viewONama.setVisible(true);
                    FrameControl.oNamaFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor 'O nama'!");
                }
            }

        });
        viewPocetni.getPrijava_btn().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameControl.prijavaFrejmOtvoren == 0) {
                    viewPrijava = new prijavaFrejm();
                    viewPrijava.setVisible(true);
                    prijavaListeneri();
                    FrameControl.prijavaFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor za prijavu!");
                }
            }

        });
    }

    public void izmeniLabele(Clan modelClan) {
        viewPrikazivanjeClana.getTelefon_tf().setText(modelClan.getBroj_tel());
        viewPrikazivanjeClana.getClanarina_tf().setText(String.valueOf(modelClan.getClanarina()));
        viewPrikazivanjeClana.getEmail_tf().setText(modelClan.getEmail());
        viewPrikazivanjeClana.getGodine_tf().setText(String.valueOf(modelClan.getGodine()));
        viewPrikazivanjeClana.getGrad_tf().setText(modelClan.getGrad());
        viewPrikazivanjeClana.getId_tf().setText(String.valueOf(modelClan.getIdClan()));
        viewPrikazivanjeClana.getIme_tf().setText(modelClan.getIme());
        viewPrikazivanjeClana.getPrezime_tf().setText(modelClan.getPrezime());
        viewPrikazivanjeClana.getPol_tf().setText(modelClan.getPol());
        viewPrikazivanjeClana.getProgram_tf().setText(modelClan.getProgram());
    }

    public static FitnesCentar izvuciFitnesCentar() {
        Session sesija = HibernateUtil.getSessionFactory().openSession();
        sesija.beginTransaction();

        FitnesCentar fc = (FitnesCentar) (sesija.load(FitnesCentar.class, 1));

        sesija.getTransaction().commit();

        if (sesija.isOpen()) {
            sesija.close();
        }

        return fc;
    }

    public void izmeniFitnesCentar() {
        modelFC = izvuciFitnesCentar();
        viewONama.getAdresa_l().setText(modelFC.getAdresa());
        viewONama.getTelefon_l().setText(modelFC.getBroj_tel());
        viewONama.getEmail_l().setText(modelFC.getEmail());
        viewONama.getNaziv_l().setText(modelFC.getNaziv());
        viewONama.getRadnov_l().setText(modelFC.getRadnovreme());
        viewONama.getZaposleni_l().setText(String.valueOf(modelFC.getBrZaposlenih()));
    }

}

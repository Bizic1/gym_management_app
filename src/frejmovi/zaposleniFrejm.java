/*
frejm se otvara kada se uloguje zaposleni korisnik
 */
package frejmovi;

import baza.koriscenjeZaposleni;
import defaultpaket.FrameController;
import javax.swing.ImageIcon;


public class zaposleniFrejm extends javax.swing.JFrame {
    
    private javax.swing.JButton dodaj_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton obrisi_btn;
    private javax.swing.JButton prikazi_btn;
    private javax.swing.JButton promeni_btn; 
    private javax.swing.JButton azuriraj_btn;
    prijavaFrejm pfrejm;
    
    public zaposleniFrejm() {
        napraviKomponente();
    }

    public zaposleniFrejm(prijavaFrejm pfrejm) {
        this.pfrejm = pfrejm;
        napraviKomponente();
    }
                     
    private void napraviKomponente() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        obrisi_btn = new javax.swing.JButton();
        dodaj_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        promeni_btn = new javax.swing.JButton();
        prikazi_btn = new javax.swing.JButton();
        azuriraj_btn = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(153, 0, 153))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

   
        setTitle("Fitnes Centar Ahilej");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setToolTipText("");

        obrisi_btn.setBackground(new java.awt.Color(60, 63, 66));
        obrisi_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        obrisi_btn.setForeground(new java.awt.Color(204, 204, 204));
        obrisi_btn.setText("OBRISI CLANA");
        obrisi_btn.setToolTipText("");
        obrisi_btn.setFocusable(false);
        obrisi_btn.addMouseListener(new koriscenjeZaposleni(this,1));
        

        dodaj_btn.setBackground(new java.awt.Color(60, 63, 66));
        dodaj_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        dodaj_btn.setForeground(new java.awt.Color(204, 204, 204));
        dodaj_btn.setText("DODAJ ZAPOSLENOG");
        dodaj_btn.setToolTipText("");
        dodaj_btn.setFocusable(false);
        dodaj_btn.addMouseListener(new koriscenjeZaposleni(this,pfrejm, 8));


        jLabel1.setBackground(new java.awt.Color(60, 63, 66));
        jLabel1.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Fitnes Centar");

        jLabel2.setFont(new java.awt.Font("Monotype Corsiva", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setText("Ahilej");

        promeni_btn.setBackground(new java.awt.Color(60, 63, 66));
        promeni_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        promeni_btn.setForeground(new java.awt.Color(204, 204, 204));
        promeni_btn.setText("PROMENI INFORMACIJE");
        promeni_btn.setToolTipText("");
        promeni_btn.setFocusable(false);
        promeni_btn.addMouseListener(new koriscenjeZaposleni(this, 4));


        prikazi_btn.setBackground(new java.awt.Color(60, 63, 66));
        prikazi_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        prikazi_btn.setForeground(new java.awt.Color(204, 204, 204));
        prikazi_btn.setText("PRIKAZI CLANA");
        prikazi_btn.setToolTipText("");
        prikazi_btn.setFocusable(false);
        prikazi_btn.addMouseListener(new koriscenjeZaposleni(2));


        azuriraj_btn.setBackground(new java.awt.Color(60, 63, 66));
        azuriraj_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        azuriraj_btn.setForeground(new java.awt.Color(204, 204, 204));
        azuriraj_btn.setText("AZURIRAJ CLANA");
        azuriraj_btn.setToolTipText("");
        azuriraj_btn.setFocusable(false);
        azuriraj_btn.addMouseListener(new koriscenjeZaposleni(this, 6));

        //preuzeto sa: https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameController.zaposleniFrejmOtvoren = 0;
            }

        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(promeni_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dodaj_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(prikazi_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(obrisi_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(azuriraj_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dodaj_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(promeni_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prikazi_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(azuriraj_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(obrisi_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //preuzeto sa: https://stackoverflow.com/questions/1614772/how-to-change-jframe-icon
        ImageIcon img = new ImageIcon("lib/icon.png");
        setIconImage(img.getImage());
        //

        pack();
        
        setLocationRelativeTo(null);
    }                        
                 
}


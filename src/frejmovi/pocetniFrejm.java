/*
Frejm koji se prikazuje cim se pokrene program
 */
package frejmovi;

import defaultpaket.FrameController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class pocetniFrejm extends javax.swing.JFrame {

    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel naziv_l;
    private javax.swing.JButton onama_btn;
    private javax.swing.JButton prijava_btn;
    private javax.swing.JButton registracija_btn;

    public pocetniFrejm() {
        napraviKomponente();
    }

    private void napraviKomponente() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        registracija_btn = new javax.swing.JButton();
        onama_btn = new javax.swing.JButton();
        prijava_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        naziv_l = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fitnes Centar Ahilej");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setToolTipText("");

        registracija_btn.setBackground(new java.awt.Color(60, 63, 66));
        registracija_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        registracija_btn.setForeground(new java.awt.Color(204, 204, 204));
        registracija_btn.setText("REGISTRACIJA");
        registracija_btn.setToolTipText("");
        registracija_btn.setFocusable(false);
        registracija_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameController.RegistracioniFrejmOtvoren == 0) {
                    new registracijaFrejm().setVisible(true);
                    FrameController.RegistracioniFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor za Registraciju!");
                }
                
            }

        });

        onama_btn.setBackground(new java.awt.Color(60, 63, 66));
        onama_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        onama_btn.setForeground(new java.awt.Color(204, 204, 204));
        onama_btn.setText("O NAMA");
        onama_btn.setToolTipText("");
        onama_btn.setFocusable(false);
        onama_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameController.oNamaFrejmOtvoren == 0) {
                    new oNamaFrejm().setVisible(true);
                    FrameController.oNamaFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor 'O nama'!");
                }
            }

        });

        prijava_btn.setBackground(new java.awt.Color(60, 63, 66));
        prijava_btn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        prijava_btn.setForeground(new java.awt.Color(204, 204, 204));
        prijava_btn.setText("PRIJAVA");
        prijava_btn.setToolTipText("");
        prijava_btn.setFocusable(false);
        prijava_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FrameController.prijavaFrejmOtvoren == 0) {
                    new prijavaFrejm().setVisible(true);
                    FrameController.prijavaFrejmOtvoren = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Vec je otvoren prozor za prijavu!");
                }
            }

        });

        jLabel1.setBackground(new java.awt.Color(60, 63, 66));
        jLabel1.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Fitnes Centar");

        naziv_l.setFont(new java.awt.Font("Monotype Corsiva", 1, 48)); // NOI18N
        naziv_l.setForeground(new java.awt.Color(204, 0, 51));
        naziv_l.setText("Ahilej");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(naziv_l, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(118, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(registracija_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(prijava_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(onama_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(149, 149, 149))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(96, 96, 96))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(naziv_l, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(prijava_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(registracija_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(onama_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        //preuzeto sa: https://stackoverflow.com/questions/1614772/how-to-change-jframe-icon
        ImageIcon img = new ImageIcon("lib/icon.png");
        setIconImage(img.getImage());
        //

        pack();

        setLocationRelativeTo(null);
    }

}

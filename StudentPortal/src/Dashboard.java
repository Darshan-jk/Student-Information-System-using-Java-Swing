/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
/**
 *
 * @author JKD
 */
public class Dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dashboard.class.getName());
    private String currentUser;
    private File newImageFile = null;
    /**
     * Creates new form Dashboard
     */
    public Dashboard(String name, String user, String dob, String phone, byte[] imageData) {
        initComponents();
        this.currentUser = user;
        
        setTitle("Student Portal Dashboard");
        setResizable(false);

        Name.setText("Name : " + name);
        username.setText("User Name : " + user);
        dateofbirth.setText("D.O.B : " + dob);
        contact.setText("Contact : " + phone);

        // 🔥 Show Image
        if (imageData != null) {
            ImageIcon icon = new ImageIcon(imageData);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

            JLabel picLabel = new JLabel(new ImageIcon(img));
            picLabel.setHorizontalAlignment(JLabel.CENTER);

            jPanel1.removeAll();
            jPanel1.setLayout(new java.awt.BorderLayout());
            jPanel1.add(picLabel, java.awt.BorderLayout.CENTER);

            jPanel1.revalidate();
            jPanel1.repaint();
        }

        logout.addActionListener(e -> logoutAction());
    }
    
    private void logoutAction() {
        new LoginPage().setVisible(true);
        this.dispose();
    }

    public Dashboard() {
        initComponents(); // default (optional)
    }
    
    private void showEditDialog() {

        JTextField userField = new JTextField(currentUser);
        JTextField nameField = new JTextField(Name.getText().replace("Name : ", ""));
        JTextField dobField = new JTextField(dateofbirth.getText().replace("D.O.B : ", ""));
        JTextField phoneField = new JTextField(contact.getText().replace("Contact : ", ""));

        JButton chooseImageBtn = new JButton("Change Image");

        chooseImageBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                newImageFile = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "New Image Selected!");
            }
        });

        Object[] fields = {
            "Username:", userField,
            "Name:", nameField,
            "DOB:", dobField,
            "Phone:", phoneField,
            chooseImageBtn
        };

        int option = JOptionPane.showConfirmDialog(
            this,
            fields,
            "Edit Profile",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            updateProfileFull(
                userField.getText(),
                nameField.getText(),
                dobField.getText(),
                phoneField.getText()
            );
        }
    }
    
    private void updateProfileFull(String newUser, String name, String dob, String phone) {

        // ✅ Basic validation
        if (newUser.isEmpty() || name.isEmpty() || dob.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Enter valid 10-digit phone number!");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection con = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_portal",
                "root",
                "Darshanjk2003@"
            );

            // 🔥 CHECK duplicate username (except current user)
            String checkQuery = "SELECT 1 FROM users WHERE username=? AND username<>?";
            java.sql.PreparedStatement checkPst = con.prepareStatement(checkQuery);
            checkPst.setString(1, newUser);
            checkPst.setString(2, currentUser);

            java.sql.ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }

            String query;

            // 🔥 If image updated
            if (newImageFile != null) {
                query = "UPDATE users SET username=?, name=?, dob=?, phone=?, image=? WHERE username=?";
                java.sql.PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, newUser);
                pst.setString(2, name);
                pst.setString(3, dob);
                pst.setString(4, phone);

                java.io.FileInputStream fis = new java.io.FileInputStream(newImageFile);
                pst.setBinaryStream(5, fis, (int) newImageFile.length());

                pst.setString(6, currentUser);

                pst.executeUpdate();

            } else {
                // 🔥 Without image
                query = "UPDATE users SET username=?, name=?, dob=?, phone=? WHERE username=?";
                java.sql.PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, newUser);
                pst.setString(2, name);
                pst.setString(3, dob);
                pst.setString(4, phone);
                pst.setString(5, currentUser);

                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Profile Updated!");

            // ✅ Update UI
            currentUser = newUser;
            Name.setText("Name : " + name);
            username.setText("User Name : " + newUser);
            dateofbirth.setText("D.O.B : " + dob);
            contact.setText("Contact : " + phone);

            // 🔥 Refresh image
            if (newImageFile != null) {
                ImageIcon icon = new ImageIcon(newImageFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                JLabel picLabel = new JLabel(new ImageIcon(img));
                picLabel.setHorizontalAlignment(JLabel.CENTER);

                jPanel1.removeAll();
                jPanel1.setLayout(new java.awt.BorderLayout());
                jPanel1.add(picLabel, java.awt.BorderLayout.CENTER);
                jPanel1.revalidate();
                jPanel1.repaint();
            }

            newImageFile = null;
            con.close();

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Username already taken!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update Failed!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Name = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        dateofbirth = new javax.swing.JLabel();
        contact = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Student Portal Dashboard");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
        );

        Name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Name.setText("Name          :");

        username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        username.setText("User Name  :");

        dateofbirth.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dateofbirth.setText("D.O.B           :");

        contact.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contact.setText("Contact        :");

        logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logout.setText("Log Out");

        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(logout))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateofbirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateofbirth, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(logout)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        showEditDialog();
    }//GEN-LAST:event_btnEditActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Name;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel contact;
    private javax.swing.JLabel dateofbirth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logout;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}

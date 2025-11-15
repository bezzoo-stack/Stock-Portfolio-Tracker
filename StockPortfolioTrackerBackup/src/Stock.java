import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Stock extends JFrame {
    private Timer autoRefreshTimer; // single timer only
    private String loggedInUsername;

    public Stock() {
        initComponents();
        DatabaseConnection.Database();

        // Load initial table data
        loadStocks();
        loadOwnedStocks();
        updateBalanceLabel();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // ------------------------
        // Manual Refresh Button
        // ------------------------
        jButton1.addActionListener(e -> {
            refreshAllTables();
            System.out.println("Manual refresh done!");
        });

        startAutoRefresh();
    }
    private void refreshAllTables() {
    loadStocks();       // Load all available stocks
    loadOwnedStocks();  // Load only owned stocks for logged-in user
    updateBalanceLabel();
    }

    private void loadStocks() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        DatabaseConnection.loadDataToTable(model);
    }

    // ------------------------
    // Load owned stocks
    // ------------------------
    private void loadOwnedStocks() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        DatabaseConnection.loadOwnedStocksToTable(model);
    }

    // ------------------------
    // Refresh both tables
    // ------------------------

    // ------------------------
    // Auto refresh every 5 sec
    // ------------------------
    private void startAutoRefresh() {
        autoRefreshTimer = new Timer(5000, e -> {
            DatabaseConnection.updateStockPrices(); // update database first
            refreshAllTables();                      // then refresh UI
        });

        autoRefreshTimer.start();
    }
private void updateBalanceLabel() {
    if (loggedInUsername != null) {
        double balance = DatabaseConnection.getUserBalance(loggedInUsername);
        jLabel2.setText("₱ " + String.format("%.2f", balance));
    } else {
        jLabel2.setText("₱ 0.00");
    }
}

public void setLoggedInUser(String username) {
    this.loggedInUsername = username;                 // store username
    jLabel4.setText("Logged in as: " + username);    // update username label
    updateBalanceLabel();                             // update balance label for this user
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Company", "Industry", "Symbol", "Price", "Day Change", "Gain/Loss"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("SELL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("BUY");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("TRANSACTION");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Company", "Industry", "Symbol", "Price", "Day Change", "Gain/Loss"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setText("Balance:");

        jLabel2.setText("jLabel2");

        jLabel3.setText("Welcome!");

        jLabel4.setText("jLabel4");

        jButton5.setText("SIGNOUT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton3)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Transaction transactionFrame = new Transaction();  //create object of Transaction JFrame
        transactionFrame.setVisible(true);                 //show the Transaction window
        this.dispose();       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.addActionListener(e -> {
        int row = jTable2.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a stock to sell!"); return; }

        String company = jTable2.getValueAt(row, 0).toString();
        String industry = jTable2.getValueAt(row, 1).toString();
        String symbol = jTable2.getValueAt(row, 2).toString();
        double price = Double.parseDouble(jTable2.getValueAt(row, 3).toString());
        String dayChange = jTable2.getValueAt(row, 4).toString();
        String gainLoss = jTable2.getValueAt(row, 5).toString();

        double balance = DatabaseConnection.getUserBalance(loggedInUsername);
        DatabaseConnection.updateUserBalance(loggedInUsername, balance + price);

        DatabaseConnection.removeOwnedStock(company, industry, symbol, price, dayChange, gainLoss);

        loadOwnedStocks();
        updateBalanceLabel();
    });

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    jButton3.addActionListener(e -> {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a stock to buy!"); return; }

        String company = jTable1.getValueAt(row, 0).toString();
        String industry = jTable1.getValueAt(row, 1).toString();
        String symbol = jTable1.getValueAt(row, 2).toString();
        double price = Double.parseDouble(jTable1.getValueAt(row, 3).toString());
        String dayChange = jTable1.getValueAt(row, 4).toString();
        String gainLoss = jTable1.getValueAt(row, 5).toString();

        double balance = DatabaseConnection.getUserBalance(loggedInUsername);
        if (balance < price) { JOptionPane.showMessageDialog(this, "Not enough balance!"); return; }

        DatabaseConnection.updateUserBalance(loggedInUsername, balance - price);
        DatabaseConnection.buyStock(company, industry, symbol, price, dayChange, gainLoss);

        loadStocks();
        loadOwnedStocks();
        updateBalanceLabel();
    });


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    String username = loggedInUsername; // get the username

    // Show thank-you message
    JOptionPane.showMessageDialog(this, "Thank you for using Stoxly, " + username + "!");

    // Open login frame
    Login loginFrame = new Login();
    loginFrame.setVisible(true);

    // Close current frame
    this.dispose();



    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Stock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}

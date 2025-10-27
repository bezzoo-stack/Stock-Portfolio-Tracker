public class Stock extends javax.swing.JFrame {

    public Stock() {
    initComponents();
     DatabaseConnection.Database(); 
        loadStocks(); 
        loadOwnedStocks(); 
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true);

        // Manual refresh for both tables
        jButton1.addActionListener(e -> {
            javax.swing.table.DefaultTableModel model1 =
                (javax.swing.table.DefaultTableModel) jTable1.getModel();
            javax.swing.table.DefaultTableModel model2 =
                (javax.swing.table.DefaultTableModel) jTable2.getModel();

            DatabaseConnection.updateStockPrices(); 
            DatabaseConnection.loadDataToTable(model1);
            DatabaseConnection.loadOwnedStocksToTable(model2);

            System.out.println("Manual refresh done!");
        });
        
        

        // Buy button — move from available to owned
        jButton3.addActionListener(e -> {
            int row = jTable1.getSelectedRow();
            if (row == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Select a stock to buy!");
                return;
            }

            String company = jTable1.getValueAt(row, 0).toString();
            String industry = jTable1.getValueAt(row, 1).toString();
            String symbol = jTable1.getValueAt(row, 2).toString();
            double price = Double.parseDouble(jTable1.getValueAt(row, 3).toString());

            DatabaseConnection.buyStock(company, industry, symbol, price);
            loadStocks();
            loadOwnedStocks();
            System.out.println("Bought stock: " + company);
        });

        // Remove button — remove from owned stocks
        jButton2.addActionListener(e -> {
            int row = jTable2.getSelectedRow();
            if (row == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Select a stock to remove!");
                return;
            }

            String symbol = jTable2.getValueAt(row, 2).toString(); 
            DatabaseConnection.removeOwnedStock(symbol);
            loadOwnedStocks();
            System.out.println("Removed owned stock: " + symbol);
        });
    }

    // Load available stocks
    private void loadStocks() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();

        DatabaseConnection.loadDataToTable(model);

        javax.swing.Timer timer = new javax.swing.Timer(5000, e -> {
            DatabaseConnection.updateStockPrices(); 
            DatabaseConnection.loadDataToTable(model); 
        });
        timer.start();
    }

    // Load owned stocks
    private void loadOwnedStocks() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable2.getModel();

        DatabaseConnection.loadOwnedStocksToTable(model);

        javax.swing.Timer timer = new javax.swing.Timer(5000, e -> {
            DatabaseConnection.updateStockPrices();
            DatabaseConnection.loadOwnedStocksToTable(model);
        });
        timer.start();
 

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

        jButton2.setText("SELL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("BUY");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(26, 26, 26))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}

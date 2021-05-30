/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htlwrn.swingairlines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Elvin
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        worldMap.setPicture();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDialog = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelStart = new javax.swing.JLabel();
        jLabelEnd = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel3.setText("Folgende Flughäfen wurden gefunden:");

        javax.swing.GroupLayout jPanelDialogLayout = new javax.swing.GroupLayout(jPanelDialog);
        jPanelDialog.setLayout(jPanelDialogLayout);
        jPanelDialogLayout.setHorizontalGroup(
            jPanelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDialogLayout.createSequentialGroup()
                .addGroup(jPanelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDialogLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel3))
                    .addGroup(jPanelDialogLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanelDialogLayout.setVerticalGroup(
            jPanelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDialogLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(45, 45, 45)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setLayout(new java.awt.GridLayout(3, 2));

        jLabelStart.setText("Startflughafen");
        jPanel2.add(jLabelStart);

        jLabelEnd.setText("Endflughafen");
        jPanel2.add(jLabelEnd);
        jPanel2.add(jTextField1);
        jPanel2.add(jTextField2);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);

        jPanel1.add(jPanel2);

        jButton1.setText("Flughafen suchen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        createDatabaseConnection();
        tree = new Tree();
        tree.setMain(this);
        worldMap.setMain(this);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeDatabaseConnection();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        if(!(jTextField1.getText().equals("") || jTextField2.getText().equals(""))) {
            startAirport = null;
            endAirport = null;
            if(searchInDB()) {
                setLabels();
                List<Airport> childAirports = getChildAirports(startAirport);
                tree.clear();
                tree.setAttributes(startAirport, endAirport, childAirports);
                tree.createTree();
                setAirports();
                worldMap.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bitte beide Felder ausfüllen!");
        }
        
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    private Airport startAirport, endAirport;
    private Tree tree;
    
    public Connection con = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelEnd;
    private javax.swing.JLabel jLabelStart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelDialog;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    private void createDatabaseConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/Airlines", "app", "app");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeDatabaseConnection() {
        try {
           if (con != null) con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean searchInDB() {
        
        Statement stmt = null;
        ResultSet rs = null;
        
        List<Airport> startList = new LinkedList<>();
        List<Airport> endList = new LinkedList<>();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select ID, NAME, IATA, LATITUDE, LONGITUDE from AIRPORT");
            Airport currAirport = null;
            
            while(rs.next()) {
                if(rs.getString("name").toLowerCase().contains(jTextField1.getText().toLowerCase()) 
                        || jTextField1.getText().toLowerCase().equals(rs.getString("IATA").toLowerCase())
                        || rs.getString("name").toLowerCase().contains(jTextField2.getText().toLowerCase())
                        || jTextField2.getText().toLowerCase().equals(rs.getString("IATA").toLowerCase())) {
                    
                    currAirport = new Airport(Integer.parseInt(rs.getString("id")), rs.getString("name").trim(), rs.getString("iata").trim()
                                            , Double.parseDouble(rs.getString("latitude")), Double.parseDouble(rs.getString("longitude")));

                    if(jTextField1.getText().toLowerCase().equals(rs.getString("iata").toLowerCase().trim()) || jTextField1.getText().toLowerCase().equals(rs.getString("name").toLowerCase().trim())) {
                        startAirport = currAirport;
                    }
                    
                    if(jTextField2.getText().toLowerCase().equals(rs.getString("iata").toLowerCase().trim()) || jTextField2.getText().toLowerCase().equals(rs.getString("name").toLowerCase().trim())) {
                        endAirport = currAirport;
                    }
                    
                    if(rs.getString("name").toLowerCase().contains(jTextField1.getText().toLowerCase())) {
                        startList.add(currAirport);
                    }
                    
                    if(rs.getString("name").toLowerCase().contains(jTextField2.getText().toLowerCase())) {
                        endList.add(currAirport);
                    }
                    
                }
            }
            
            if(startAirport == null) {
                if(startList.size() == 1) {
                    startAirport = new Airport(startList.get(0).getID(), startList.get(0).getName(), startList.get(0).getIata(), startList.get(0).getLatitude(), startList.get(0).getLongitude());
                } else if(startList.size() > 0){
                    String name = getNameFromCB(startList);
                    if(!name.isEmpty()) {
                        int id = getID(startList, name);
                        String iata = getIata(startList, name);
                        Double latitude = getLatitude(startList, name);
                        Double longitude = getLongitude(startList, name);
                        startAirport = new Airport(id, name, iata, latitude, longitude);
                    }
                } else {
                    return false;
                }
            }
            
            if(endAirport == null) {
                if(endList.size() == 1) {
                    endAirport = new Airport(endList.get(0).getID(), endList.get(0).getName(), endList.get(0).getIata(), endList.get(0).getLatitude(), endList.get(0).getLongitude());
                } else if(endList.size() > 0) {
                    String name = getNameFromCB(endList);
                    if(!name.isEmpty()) {
                        int id = getID(endList, name);
                        String iata = getIata(endList, name);
                        Double latitude = getLatitude(endList, name);
                        Double longitude = getLongitude(endList, name);
                        endAirport = new Airport(id, name, iata, latitude, longitude);
                    }
                } else {
                    return false;
                }
            }
            
            return true;
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    private String getNameFromCB(List<Airport> list) {
        
        jComboBox1.removeAllItems();
        for(Airport airp : list) {
            jComboBox1.addItem(airp.getName());
        }
        
        int res = JOptionPane.showConfirmDialog(this, jPanelDialog,
                "Flughafenwahl", JOptionPane.OK_CANCEL_OPTION);
        
        if(res == JOptionPane.OK_OPTION) {
            return jComboBox1.getSelectedItem().toString();
        }
        
        return "";
    }

    private String getIata(List<Airport> list, String name) {
        
        for(Airport airp : list) {
            if(name.equals(airp.getName())) {
                return airp.getIata();
            }
        }
        return "";
    }

    private void setLabels() {
        
        jTextField1.setText(startAirport.getIata());
        jLabel1.setText(startAirport.getName());
        jTextField2.setText(endAirport.getIata());
        jLabel2.setText(endAirport.getName());
        
    }
    
    private Double getLatitude(List<Airport> list, String name) {
        for(Airport airp : list) {
            if(name.equals(airp.getName())) {
                return airp.getLatitude();
            }
        }
        return 0.0;
    }

    private Double getLongitude(List<Airport> list, String name) {
        for(Airport airp : list) {
            if(name.equals(airp.getName())) {
                return airp.getLongitude();
            }
        }
        return 0.0;
    }
    
    
    private int getID(List<Airport> list, String name) {
        for(Airport airp : list) {
            if(name.equals(airp.getName())) {
                return airp.getID();
            }
        }
        return 0;
    }
    
    
    private void setAirports() {
        worldMap.start = startAirport;
        worldMap.end = endAirport;
        worldMap.routeList = tree.getRouteList();
    }

    public List<Airport> getChildAirports(Airport airport) {
       
        Statement stmt = null;
        ResultSet rs = null;
        
        List<Airport> list = new LinkedList<>();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select AIRPORT.ID, AIRPORT.NAME, AIRPORT.IATA, AIRPORT.LATITUDE, AIRPORT.LONGITUDE, "
                    + "AIRPORT1, AIRPORT2 from ROUTE join AIRPORT on ROUTE.AIRPORT2 = AIRPORT.ID where AIRPORT1 = " + airport.getID());
            
            while(rs.next()) {
                
                Airport airp = new Airport(Integer.parseInt(rs.getString("id")), rs.getString("name").trim(), rs.getString("iata").trim()
                                            , Double.parseDouble(rs.getString("latitude")), Double.parseDouble(rs.getString("longitude")));
                list.add(airp);
            }
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        /*for(Airport a : list)
            System.out.println(a.getName());*/
        return list;
    }

    public Airport getAirportFromIata(String iata) {
        
        Statement stmt = null;
        ResultSet rs = null;
        Airport airp = null;
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select ID, LATITUDE, LONGITUDE, NAME from AIRPORT where IATA = '" + iata + "'");
            
            if(rs.next()) {
                
                airp = new Airport(Integer.parseInt(rs.getString("id")), rs.getString("name").trim(), iata
                                            , Double.parseDouble(rs.getString("latitude")), Double.parseDouble(rs.getString("longitude")));
            }
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return airp;
    }

}

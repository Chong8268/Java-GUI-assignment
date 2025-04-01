package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class MaintainProgrammeFrame extends JFrame {
    private String host = "jdbc:derby://localhost:1527/collegedb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "Programme";
    private Connection conn;
    private PreparedStatement stmt;
    
    private JTextField jtfCode = new JTextField();
    private JTextField jtfName = new JTextField();
    private JTextField jtfFaculty = new JTextField();
    private JButton jbtAdd = new JButton("Create");
    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtUpdate = new JButton("Update");
    private JButton jbtDelete = new JButton("Delete");
    
    public MaintainProgrammeFrame() {
        JPanel jpCenter = new JPanel(new GridLayout(3, 2));
        jpCenter.add(new JLabel("Programme Code"));
        jpCenter.add(jtfCode);
        jpCenter.add(new JLabel("Programme Name"));
        jpCenter.add(jtfName);
        jpCenter.add(new JLabel("Faculty"));
        jpCenter.add(jtfFaculty);
        add(jpCenter);
        
        JPanel jpSouth = new JPanel();
        jpSouth.add(jbtAdd);
        jpSouth.add(jbtRetrieve);
        jpSouth.add(jbtUpdate);
        jpSouth.add(jbtDelete);
        add(jpSouth, BorderLayout.SOUTH);
        
        jbtRetrieve.addActionListener(new RetrieveListener());
        jbtAdd.addActionListener(new CreateListener());
        jbtUpdate.addActionListener(new UpdateListener());
        jbtDelete.addActionListener(new DeleteListener());
        
        createConnection();
       
    }
    
    public ResultSet selectRecord(String code) {
        String queryStr = "SELECT * FROM " + tableName + " WHERE Code = ?";
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryStr);
            stmt.setString(1, code);
           
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return rs;
    }
    
    private class RetrieveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String code = jtfCode.getText();
                ResultSet rs = selectRecord(code);
                if (rs.next()) {
                    jtfName.setText(rs.getString("Name"));
                    jtfFaculty.setText(rs.getString("Faculty"));
                }
                else {
                    JOptionPane.showMessageDialog(null, "No such programme code.", "RECORD NOT FOUND", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
 
    }
    
    public class CreateListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String code = jtfCode.getText();
            String name = jtfName.getText();
            String faculty = jtfFaculty.getText();
            
            if(code.isEmpty() || name.isEmpty() || faculty.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in all the information", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    String statement = "INSERT INTO " + tableName + " (CODE, NAME, FACULTY) VALUES (?,?,?)";
                    stmt = conn.prepareStatement(statement);
                    stmt.setString(1, code);
                    stmt.setString(2,name);
                    stmt.setString(3, faculty);
                    
                    int rowInsert = stmt.executeUpdate();
                    if(rowInsert > 0){
                        JOptionPane.showMessageDialog(null,"Insert Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    }else{
                        JOptionPane.showMessageDialog(null,"Failed to insert", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public class UpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String code = jtfCode.getText();
            String name = jtfName.getText();
            String faculty = jtfFaculty.getText();
            
            if(code.isEmpty() || name.isEmpty() || faculty.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in the information");
            }else{
                try{
                    ResultSet rs = selectRecord(code);
                    if(rs.next()){
                    
                    String update = "UPDATE " + tableName + " SET NAME = ?, FACULTY = ? WHERE CODE = ?";
                    stmt = conn.prepareStatement(update);
                    stmt.setString(1,name);
                    stmt.setString(2,faculty);
                    stmt.setString(3,code);
                    
                    int rowUpdate = stmt.executeUpdate();
                    if(rowUpdate > 0){
                        JOptionPane.showMessageDialog(null, "Update Successfully","Success", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    }
                    }else{
                        JOptionPane.showMessageDialog(null,"No such Programme Code", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public class DeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String code = jtfCode.getText();
            
            if(code.isEmpty()){
                JOptionPane.showMessageDialog(null, "The Programme code are empty", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                String delete = "DELETE FROM " + tableName + " WHERE CODE = ?";
                
                try{
                    stmt = conn.prepareStatement(delete);
                    stmt.setString(1,code);
                    
                    int rowDelete = stmt.executeUpdate();
                    if(rowDelete > 0){
                        JOptionPane.showMessageDialog(null,"Programme Delete Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    }else{
                        JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
    }
        
    private void createConnection() {
        try {
            conn = DriverManager.getConnection(host, user, password);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void shutDown() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearField(){
        jtfCode.setText("");
        jtfName.setText("");
        jtfFaculty.setText("");
    }
    
    public static void main(String[] args) {
        MaintainProgrammeFrame frm = new MaintainProgrammeFrame();
        frm.setTitle("Programme CRUD");
        frm.setSize(600, 200);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
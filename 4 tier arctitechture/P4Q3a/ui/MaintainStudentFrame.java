package ui;


import domain.Programme;
import domain.Student;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaintainStudentFrame extends JFrame {
    private String host = "jdbc:derby://localhost:1527/collegedb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "Student";
    private Connection conn;
    private PreparedStatement stmt;
    
    private JTextField jtfID = new JTextField();
    private JTextField jtfIC = new JTextField();
    private JTextField jtfName = new JTextField();
    private JTextField jtfLevel = new JTextField();
    private JTextField jtfProgCode = new JTextField();
    private JTextField jtfYear = new JTextField();
    private JButton jbtAdd = new JButton("Create");
    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtUpdate = new JButton("Update");
    private JButton jbtDelete = new JButton("Delete");

    public MaintainStudentFrame() {
        
        JPanel jpCenter = new JPanel(new GridLayout(6, 2));
        jpCenter.add(new JLabel("Student ID"));
        jpCenter.add(jtfID);
        jpCenter.add(new JLabel("Student IC"));
        jpCenter.add(jtfIC);
        jpCenter.add(new JLabel("Student Name"));
        jpCenter.add(jtfName);
        jpCenter.add(new JLabel("Level"));
        jpCenter.add(jtfLevel);
        jpCenter.add(new JLabel("Programme Code"));
        jpCenter.add(jtfProgCode);
        jpCenter.add(new JLabel("Year"));
        jpCenter.add(jtfYear);
        add(jpCenter);

        JPanel jpSouth = new JPanel();
        jpSouth.add(jbtAdd);
        jpSouth.add(jbtRetrieve);
        jpSouth.add(jbtUpdate);
        jpSouth.add(jbtDelete);
        add(jpSouth, BorderLayout.SOUTH);
        
        jbtAdd.addActionListener(new AddListener());
        jbtRetrieve.addActionListener(new RetrieveListener());
        jbtUpdate.addActionListener(new UpdateListener());
        jbtDelete.addActionListener(new DeleteListener());

        createConnection();
        setTitle("Student CRUD");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public class AddListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String studentID = jtfID.getText();
            String studentIC = jtfIC.getText();
            String studentName = jtfName.getText();
            String studentLevel = jtfLevel.getText();
            String programmeCode = jtfProgCode.getText();
            String year = jtfYear.getText();
            
            if(studentID.isEmpty() || studentIC.isEmpty() || studentName.isEmpty() || studentLevel.isEmpty() || programmeCode.isEmpty() || year.isEmpty()){
                JOptionPane.showMessageDialog(null,"Please fill in all the information","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                String addStudent = "INSERT INTO " + tableName + " (ID,IC,Name,Level,ProgrammeCode,YR) VALUES (?,?,?,?,?,?)";
                
                try{
                    stmt = conn.prepareStatement(addStudent);
                    stmt.setString(1, studentID);
                    stmt.setString(2, studentIC);
                    stmt.setString(3, studentName);
                    stmt.setString(4, studentLevel);
                    stmt.setString(5, programmeCode);
                    stmt.setString(6, year);
                    
                    int rowInsert = stmt.executeUpdate();
                    if(rowInsert > 0){
                        JOptionPane.showMessageDialog(null,"Student Insert Successfull","Success",JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    }
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"SQL Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public class RetrieveListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String id = jtfID.getText();
                ResultSet rs = selectedRecord(id);
                if(rs.next()){
                    jtfIC.setText(rs.getString("IC"));
                    jtfName.setText(rs.getString("NAME"));
                    jtfLevel.setText(rs.getString("Level"));
                    jtfProgCode.setText(rs.getString("ProgrammeCode"));
                    jtfYear.setText(rs.getString("YR"));
                }else{
                    JOptionPane.showMessageDialog(null,"No such Student!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            } 
        }
    }
    
    public class UpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String studentID = jtfID.getText();
            String studentIC = jtfIC.getText();
            String studentName = jtfName.getText();
            String studentLevel = jtfLevel.getText();
            String programmeCode = jtfProgCode.getText();
            String year = jtfYear.getText();
            
            try{
                ResultSet rs = selectedRecord(studentID);
                if(rs.next()){
                    String update = "UPDATE " + tableName + " SET IC = ?, Name = ?, Level = ?, ProgrammeCode = ? ,YR = ? WHERE ID = ?";
                    stmt = conn.prepareStatement(update);
                    stmt.setString(1,studentIC);
                    stmt.setString(2,studentName);
                    stmt.setString(3,studentLevel);
                    stmt.setString(4,programmeCode);
                    stmt.setString(5,year);
                    stmt.setString(6,studentID);
                    
                    int rowUpdate = stmt.executeUpdate();
                    if(rowUpdate > 0){
                        JOptionPane.showMessageDialog(null,"Update Successfully", "Successfull",JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"No such student founds", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public class DeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String ID = jtfID.getText();
            
            String delete = "DELETE FROM " + tableName + " WHERE ID = ?";
            
            try{
                stmt = conn.prepareStatement(delete);
                stmt.setString(1,ID);
                
                int rowDelete = stmt.executeUpdate();
                if(rowDelete > 0){
                    JOptionPane.showMessageDialog(null,"Delete Successfull","Success",JOptionPane.INFORMATION_MESSAGE);
                    clearField();
                }else{
                    JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.ERROR_MESSAGE);
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"ds");
            }
        }
    }
    
    public ResultSet selectedRecord(String studentID){
        String query = "SELECT * FROM " + tableName + " WHERE ID = ?";
        ResultSet rs = null;
        try{
            stmt = conn.prepareStatement(query); 
            stmt.setString(1, studentID);
            
            rs = stmt.executeQuery();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return rs;
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(host, user, password);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearField(){
        jtfID.setText("");
        jtfIC.setText("");
        jtfName.setText("");
        jtfLevel.setText("");
        jtfProgCode.setText("");
        jtfYear.setText("");
    }
    
    public static void main(String[] args) {
        new MaintainStudentFrame();
    }
}
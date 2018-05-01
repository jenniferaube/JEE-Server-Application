/**
 * @author Jennifer Aube
 * CST8277Assignment4_SwingClient_CORBA.java
 * Created by: Jennifer Aube, Zachary Brule, Hiral Nilesh Bhatt, Evandro Ramos da Silva, John Ferguson
 * Date created: April 11, 2018
 * Code modified from Stanley Pieda DemoStuffSwingClient_CORBA
 * Purpose: Swing client will connect to Java EE Server, add a fishstick and view fishsticks
 */
package cst8277assignment4_swingclient_corba;
import business.FishStickFacadeRemote;
import entity.FishStick;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author Jennifer
 */
public class CST8277Assignment4_SwingClient_CORBA extends JFrame{

    private final FishStickFacadeRemote remoteFishStick;
    private JTextField recordNumTextField = new JTextField();
    private JTextField omegaTextField = new JTextField();
    private JTextField lambdaTextField = new JTextField();
    private JTextArea FishStickViewJTextArea = new JTextArea(10, 0); // 10 rows, 0 columns
    
    public CST8277Assignment4_SwingClient_CORBA() {
        buildGUI();
        remoteFishStick = getRemoteSession();
    }
    private FishStickFacadeRemote getRemoteSession() {
        FishStickFacadeRemote session = null;
        // CORBA properties and values and lookup taken after earlier work provided by
        // Todd Kelley (2016) Personal Communication
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        try {
            JOptionPane.showMessageDialog(this, "Trying for a session...");
            InitialContext ic = new InitialContext();
            session = (FishStickFacadeRemote)ic.lookup("java:global/CST8277Assignment4/CST8277Assignment4-ejb/FishStickFacade");
            JOptionPane.showMessageDialog(this, "Got a session :) ");
        } catch (NamingException e) {
            JOptionPane.showMessageDialog(this, "Problem. \n Cause: \n" + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Problem. \n Cause: \n" + e.getMessage());
        }
        return session;
    }
    private void buildGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Swing Client Assignment4 - CORBA By: Jennifer Aube");
        GridLayout westGridLayout = new GridLayout(4, 1); // rows, columns
        westGridLayout.setHgap(10);
        westGridLayout.setVgap(10);
        
        JPanel dataEntryWestJPanel = new JPanel(westGridLayout);
        dataEntryWestJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        GridLayout dataEntryGridLayout = new GridLayout(4, 1); // rows, columns
        dataEntryGridLayout.setHgap(10);
        dataEntryGridLayout.setVgap(10);
        
        JPanel dataEntryCenterJPanel = new JPanel(dataEntryGridLayout);
        dataEntryCenterJPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.LEFT);
        buttonLayout.setHgap(10);
        
        JPanel dataEntryButtonJPanel = new JPanel(buttonLayout);
        JPanel dataEntryJPanel = new JPanel(new BorderLayout());
        
        JPanel dataViewJPanel = new JPanel(new GridLayout(1, 1));        
        dataViewJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel recordNumLabel = new JLabel("RecordNumber");
        JLabel omegaLabel = new JLabel("Omega");
        JLabel lambdaLabel = new JLabel("Lambda");
        
        JButton addJButton = new JButton("Add FishStick");
        JButton viewAllJButton = new JButton("View All FishStick");
        
        recordNumLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//top,left,bottom,right
        omegaLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        lambdaLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        FishStickViewJTextArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addJButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        viewAllJButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        dataEntryWestJPanel.add(recordNumLabel);
        dataEntryWestJPanel.add(omegaLabel);
        dataEntryWestJPanel.add(lambdaLabel);
        dataEntryButtonJPanel.add(addJButton);
        dataEntryButtonJPanel.add(viewAllJButton);
        dataEntryCenterJPanel.add(recordNumTextField);
        dataEntryCenterJPanel.add(omegaTextField);
        dataEntryCenterJPanel.add(lambdaTextField);
        dataEntryCenterJPanel.add(dataEntryButtonJPanel);
        dataEntryJPanel.add(dataEntryWestJPanel, BorderLayout.WEST);
        dataEntryJPanel.add(dataEntryCenterJPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(FishStickViewJTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dataViewJPanel.add(scrollPane);
        this.getContentPane().add(dataEntryJPanel, BorderLayout.NORTH);
        this.getContentPane().add(dataViewJPanel, BorderLayout.CENTER);
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(remoteFishStick != null){
                        FishStick fs = new FishStick();
                        fs.setRecordNum(Integer.parseInt(recordNumTextField.getText()));
                        fs.setOmega(omegaTextField.getText());
                        fs.setLambda(lambdaTextField.getText());
                       // System.out.println(fs.getRecordNum());
                        remoteFishStick.create(fs);
                    }
                    else{
                        JOptionPane.showMessageDialog(CST8277Assignment4_SwingClient_CORBA.this,"Problem. No remote object available");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CST8277Assignment4_SwingClient_CORBA.this,"Problem Cause: \n" + ex.getMessage());
                }
            }
        });
        viewAllJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(remoteFishStick != null){
                        List<FishStick> fishsticks = remoteFishStick.findAll();
                        FishStickViewJTextArea.setText("");
                        for (FishStick f : fishsticks) {
                            FishStickViewJTextArea.append(String.format(//Formatted output for each record.
                    "FishStick :: ID:%d - Record number:%d, Lambda:%s, Omega:%s %n",f.getId(), f.getRecordNum(),f.getLambda().toString(),f.getOmega().toString()));
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(CST8277Assignment4_SwingClient_CORBA.this,"Problem. No remote object available");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CST8277Assignment4_SwingClient_CORBA.this,"Problem Cause: \n" + ex.getMessage());
                }
            }
        });
        //this.pack();
           this.setSize(600, 400);
        // null causes window to be centered on screen
        // see: stackoverflow.com (). How to set JFrame to appear centered, regardless of monitor resolution? Retrieved from
        // https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        }
        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            // TODO code application logic here
            CST8277Assignment4_SwingClient_CORBA swingClient = new CST8277Assignment4_SwingClient_CORBA();
        }

}

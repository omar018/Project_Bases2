import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Votar extends JPanel{
    private Main m;
    public Votar(){
        m = new Main();
        String [] candidatos = m.getCandidatos();
        this.setLayout(null);

        JLabel candid = new JLabel(Arrays.toString(candidatos));       
        candid.setBounds(10,50,80,25);
        this.add(candid);

        JLabel userLabel = new JLabel("Usuario");
        userLabel.setBounds(10, 20, 80,25);;
        this.add(userLabel);

        JTextField userName = new JTextField(100);
        userName.setBounds(100,20,200,25);
        this.add(userName);
        
        JLabel voteLabel = new JLabel("Voto");
        voteLabel.setBounds(10, 80, 80,25);
        this.add(voteLabel);

        JTextField userVote = new JTextField(50);
        userVote.setBounds(100, 80, 165, 25);
        this.add(userVote);
        //panel.add(userVote);


    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.print.event.PrintEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Votar extends JPanel implements ActionListener{
    private Main m;
    private JLabel tituloCandidatos;
    private JLabel userLabel;
    private JLabel voteLabel;
    private JTextField userVote;
    private JTextField userName;
    private JButton botonVolver;
    private List<String> votantes;
    private JFrame frame;
    private JTextArea textArea;
    private FileReader reader;
    private PrintWriter escritor;
    public Votar(JFrame currentFrame){
        m = new Main();
        //conseguir la lista de candidatos que está en el hashmap cuentaCandidatos
        try {
            escritor = new PrintWriter("candidatos.txt");
            for(Entry<String,String[]> entry : m.cuentaCandidatos.entrySet()){
                String key = entry.getKey();
                String[] value = entry.getValue();
                //System.out.println("llave "+ key + " votos " + value);
                escritor.print(key + " " + Arrays.toString(value));
            }
            escritor.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //leer la lista de candidatos que está en el archivo candidatos.txt
        try {
            reader = new FileReader("candidatos.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        textArea = new JTextArea(5,20);
        try {
            textArea.read(reader, "candidatos.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setEnabled(false);
        this.add(textArea);
        //lista de documentos de los votantes
        votantes= new ArrayList<String>();
        this.frame = currentFrame;

        //String [] candidatos = m.getCandidatos();
        this.setBounds(100,100, 50, 50);
        this.setLayout(new GridLayout(0,1));

        tituloCandidatos = new JLabel("Candidatos");
        tituloCandidatos.setBounds(10,-10,80,25);
        this.add(tituloCandidatos);


        userLabel = new JLabel("Usuario");
        userLabel.setBounds(10, 40, 80,25);;
        this.add(userLabel);

        userName = new JTextField(30);
        userName.setBounds(100,40,20,25);
        this.add(userName);
        
        voteLabel = new JLabel("Voto");
        voteLabel.setBounds(10, 100, 80,25);
        this.add(voteLabel);

        userVote = new JTextField(30);
        userVote.setBounds(100, 100, 20, 25);
        this.add(userVote);

        JButton botonVotar = new JButton("Votar");
        botonVotar.setBounds(70,130,80,25);
        this.add(botonVotar);

        botonVotar.addActionListener(this);

        botonVolver = new JButton("Volver");
        botonVolver.setBounds(70,150,80,25);
        this.add(botonVolver);

        botonVolver.addActionListener(this);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String textoBoton = "";
        Object obj = e.getSource();
        JButton b = null;
        if (obj instanceof JButton){
            b = (JButton)obj;
        }
        textoBoton = b.getText();
        if (textoBoton.equals("Votar")){
            String usuario = userName.getText();
            String voto = userVote.getText();
            if (votantes.contains(usuario)){             // esto es lo que se usa para revisar que no vote dos veces
                JOptionPane.showMessageDialog(frame, "El mismo votante no puede votar dos veces", "Error", JOptionPane.ERROR_MESSAGE);
            }else{ // aqui guardo el votante y le sumo un voto al candidato elegido
                votantes.add(usuario);
                String [] nompar = m.cuentaCandidatos.get(voto);
                Integer voteCount = m.candidatosVotos.get(nompar);
                m.candidatosVotos.put(nompar,voteCount+1);
                m.subirVotoCadena(voto, usuario);
                JOptionPane.showMessageDialog(frame, "Usted ha votado!", "Éxito", JOptionPane.OK_OPTION);
            }



            // este es el voto del usuario que tenemos que guardar en la blockchain
        }else if (textoBoton.equals("Volver")){
            //aqui cambia al panel de resultados
            new GUI();
        }



    }
}

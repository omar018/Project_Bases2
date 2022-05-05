import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.util.Arrays;

public class Resultados extends JPanel implements ActionListener{
    private JLabel tituloCandidatos;
    private JLabel candidatos;
    private JButton volver;
    private PrintWriter escritor;
    private String candidatoVotoCount;
    private Main m;
    private JTextArea textArea;
    FileReader reader;
    private JButton botonVolver;
    public Resultados(){
        m = new Main();
        // aqui escribimos todos los candidatos con sus votos en un archivo llamado resultados.txt
        try {
            escritor = new PrintWriter("resultados.txt");
            for(Entry<String[],Integer> entry : m.candidatosVotos.entrySet()){
                String[] key = entry.getKey();
                Integer value = entry.getValue();
                //System.out.println("llave "+ key + " votos " + value);
                escritor.println(Arrays.toString(key) + " cantidad de votos: " + value);
            }
            escritor.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            reader = new FileReader("resultados.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        textArea = new JTextArea(10,20);
        try {
            textArea.read(reader,"resultados.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setEnabled(false);
        this.setBounds(100,100, 50, 50);
        this.setLayout(new GridLayout(0,1));

        this.add(textArea);
        botonVolver = new JButton("Volver");
        botonVolver.setBounds(70,150,80,25);
        this.add(botonVolver);

        botonVolver.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new GUI();
    }
}

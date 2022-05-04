import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.*;
import java.util.Arrays;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
public class GUI extends Main implements ActionListener{

    private JFrame frame;
    private JLabel candid;
    private JPanel panel;

    public GUI(){
        frame = new JFrame();
        
        JButton botonVotar = new JButton("Votar");
        botonVotar.addActionListener(this);
        JButton botonResultados = new JButton("Resultados");
        botonResultados.setPreferredSize(new Dimension(150,80));
        botonResultados.addActionListener(this);


        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200, 200,200, 200));
        panel.setLayout(new GridLayout(0,1));
        panel.add(botonVotar);
        panel.add(botonResultados);


        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Interfaz de votos");
        frame.setSize(200,200);
        frame.pack();
        frame.setVisible(true);
        // frame.getContentPane().removeAll();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String textoBoton = "";
        Object obj = e.getSource();
        JButton b = null;
        if (obj instanceof JButton){
            b = (JButton)obj;
        }
        textoBoton = b.getText();
        if (textoBoton.equals("Votar")){
            //aqui tendría que cambiar de panel
            Votar v = new Votar();
            System.out.print("votar");
            frame.setVisible(false);
            nuevoPanel(v);
            frame.setVisible(true);
        }else if (textoBoton.equals("Resultados")){
            //aqui cambia al panel de resultados
            Resultados r = new Resultados();
            nuevoPanel(r);
        }
    }
    public JFrame getFrame(){
        return this.frame;
    }
    public JPanel getPanel(){
        return this.panel;
    }
    public void nuevoPanel (JPanel panelActual){
        panel.removeAll();
        panel.add(panelActual);
        panel.repaint();
        panel.revalidate();
    }
}
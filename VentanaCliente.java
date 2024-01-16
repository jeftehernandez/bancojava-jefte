
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class VentanaCliente {
    private JTextArea areaCodigo;
    private JTextArea areaMonto;
    private JTextArea areaTexto;
    private JTextArea areaRepetir;
    private JScrollPane scrollPane;
    String nada; 
    public VentanaCliente(){
        areaTexto = new JTextArea();
        areaTexto.setForeground(Color.white);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 18));
        areaTexto.setBackground(Color.black);
        scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBounds(35, 260, 420, 220);
        //cliente = new Cliente(null,this);
    }

    public void ventanaCliente(){
        JFrame ventana = new JFrame("Cliente ATM");
        JPanel panel = new JPanel();
        panel.setLayout(null); // Desactiva el layout manager predeterminado
        // Crear un JLabel para mostrar el mensaje
        JLabel mensajeLabel = new JLabel("Cliente ATM");
        mensajeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mensajeLabel.setBounds(150, 20, 200, 30);
        // Crear un JTextArea para el código
        areaCodigo = new JTextArea();
        areaCodigo.setBounds(150, 75, 110, 25);

        JLabel mensajeCodigo = new JLabel("Codigo:");
        mensajeCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeCodigo.setBounds(25, 75, 115, 20);

        JLabel mensajeMonto = new JLabel("Monto:");
        mensajeMonto.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeMonto.setBounds(25, 130, 115, 20);
        // Crear ventana para repetir"
        JLabel mensajeRepetir = new JLabel("Repetir:");
        mensajeRepetir.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeRepetir.setBounds(25,175,115,20);
        
        //crea un JButton "Retirar"
        JButton boton1 = new JButton("Retirar");
        boton1.setBounds(175, 225, 120, 25);
        
        boton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                String informacion = " ";
                // Acción a realizar cuando se hace clic en el botón "Retirar"
                boton1.setEnabled(false);
                String codigo = areaCodigo.getText();
                String monto = areaMonto.getText();
                String repetir =areaRepetir.getText();
                int veces = Integer.parseInt(repetir);
                informacion = informacion.concat(codigo + " " + monto ); //concatena la informacion
                System.out.println("informacion en la ventana cliente: " + informacion);
                
                for(int i =0;i < veces;i++){
                    Cliente cliente = new(informacion,VentanaCliente.this);
                    //Thread[] hilo = new Thread[i];
                    //Thread hilo = new Thread( new Cliente(informacion,VentanaCliente.this));
                    //hilo.start();
                    //hilo[i].join();
                }
                
                
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ee){
                    ee.printStackTrace();
                }
                //cliente(informacion,VentanaCliente.this);
                //cliente()
                //cliente.enviar_mensaje(informacion);
                //cliente = new Cliente(informacion, VentanaCliente.this);
                boton1.setBackground(Color.GRAY);
                boton1.setEnabled(true);
            }
        });

        // //cadena = RecogeInformacion(cadena)
        areaMonto = new JTextArea();
        areaMonto.setBounds(150, 125, 110, 25);
        areaRepetir = new JTextArea();
        areaRepetir.setBounds(150,170,110,25);
       
        // Agregar los componentes al panel en el orden deseado
        panel.add(mensajeLabel);
        panel.add(mensajeCodigo);
        panel.add(mensajeMonto);
        panel.add(mensajeRepetir);
        panel.add(areaMonto);
        panel.add(areaCodigo);
        panel.add(areaRepetir);
        panel.add(boton1);
        panel.add(scrollPane);
        
         // Configurar el comportamiento de la ventana al cerrar
         ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         // Establecer el color de fondo del panel
         panel.setBackground(Color.LIGHT_GRAY);
 
         // Establecer el tamaño de la ventana
         ventana.setSize(550, 550);
 
         // Agregar el panel a la ventana
         ventana.add(panel);
 
         // Hacer visible la ventana
         ventana.setVisible(true);
    }

    public void MostrarInformacion(String informacion){
        areaTexto.setText(informacion);
    }


    public static void main(String[] args) {
        VentanaCliente cliente = new VentanaCliente();
        cliente.ventanaCliente();
    }
}

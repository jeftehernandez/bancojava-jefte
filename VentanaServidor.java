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
public class VentanaServidor {
    private JTextArea areaCodigo;
    private JTextArea areaTexto;
    private JScrollPane scrollPane;
    private StringBuilder textoCompleto;

    public VentanaServidor(){
        textoCompleto = new StringBuilder();
        areaTexto = new JTextArea();
        areaTexto.setForeground(Color.white);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 18));
        areaTexto.setBackground(Color.GRAY);
        scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBounds(35, 218, 420, 220);
    }

    public void ventanaServidor(){
        //String cadena = "Mensaje para el cliente desde el servidor ATM";
        JFrame ventana = new JFrame("Servidor ATM");
        JPanel panel = new JPanel();
        panel.setLayout(null); // Desactiva el layout manager predeterminado
        // Crear un JLabel para mostrar el mensaje
        JLabel mensajeLabel = new JLabel("Servidor ATM Multiusuario");
        mensajeLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        mensajeLabel.setBounds(150, 20, 200, 30);
        // Crear un JTextArea para el código
        areaCodigo = new JTextArea("5050");
        ////areaTexto.setFont(new Font(cadena, Font.PLAIN, 18));
        areaCodigo.setFont(new Font("5050", Font.PLAIN,18));
        areaCodigo.setBounds(150, 75, 110, 25);

        JLabel mensajeCodigo = new JLabel("Puerto:");
        mensajeCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeCodigo.setBounds(25, 75, 115, 20);

        // Crear un JButton "Retirar"
        JButton boton1 = new JButton("Iniciar Servidor");
        boton1.setBounds(175, 180, 120, 25);
        boton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String inicio_server = null;
                int puerto;
                inicio_server = areaCodigo.getText();
                System.out.println("el inicio del servidor..." + inicio_server);
                puerto = Integer.parseInt(inicio_server);
                //Thread hilo = new Thread( new Cliente(informacion,VentanaCliente.this));
                Thread hilo = new Thread( new Servidor(puerto,VentanaServidor.this));
                hilo.start();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ee){
                    ee.printStackTrace();
                }
                boton1.setBackground(Color.GRAY);
                boton1.setEnabled(true);
            }
        });

        // Agregar los componentes al panel en el orden deseado
        panel.add(mensajeLabel);
        panel.add(mensajeCodigo);
        //panel.add(areaMonto);
        panel.add(areaCodigo);
        panel.add(boton1);
        panel.add(scrollPane);

         // Configurar el comportamiento de la ventana al cerrar
         ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         // Establecer el color de fondo del panel
         panel.setBackground(Color.LIGHT_GRAY);

         // Establecer el tamaño de la ventana
         ventana.setSize(520, 520);

         // Agregar el panel a la ventana
         ventana.add(panel);

         // Hacer visible la ventana
         ventana.setVisible(true);

    }
    public void MostrarInformacion(String informacion){
        //textoCompleto.append(nuevoTexto).append("\n");
        textoCompleto.append(informacion).append("\n");
        areaTexto.setText(textoCompleto.toString());
        //areaTexto.setText(informacion);
    }
    public static void main(String[] args) {
        VentanaServidor servidor = new VentanaServidor();
        servidor.ventanaServidor();
    }
}

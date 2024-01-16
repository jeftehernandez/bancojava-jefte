import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Servidor implements Runnable {

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    private int puerto;
    private VentanaServidor ventana;
    Object bandera = new Object();

    public Servidor(int port, VentanaServidor ventana) {
        this.ventana = ventana;
        this.puerto = port;
    }

    @Override
    public void run(){
        try{
            serverSocket = new ServerSocket(puerto);//crea el sertver socket
            ventana.MostrarInformacion("Esperando conexion entrante en el puerto " + puerto + "...");
        }catch(IOException e){
            e.printStackTrace();
            ventana.MostrarInformacion("Error al escuchar el puerto: " + e.getMessage());
        }
        while(true){
            socket = null;
            try{
                socket = serverSocket.accept();//acepta conexion
            }catch(IOException ee){
                ee.printStackTrace();
            }
            try{
                bufferDeEntrada = new DataInputStream(socket.getInputStream());
                bufferDeSalida = new DataOutputStream(socket.getOutputStream());
                Thread hilo = new Thread(new Servidor_auxiliar(socket, bufferDeEntrada, bufferDeSalida, ventana, bandera));
                hilo.start();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}



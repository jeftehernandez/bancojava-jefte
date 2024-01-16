//package socket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.util.Scanner;
public class Cliente implements Runnable{
    private Socket socket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    private VentanaCliente ventana;
    private String mensaje;
   public Cliente(String mensaje, VentanaCliente ventana){
      this.mensaje = mensaje;
      this.ventana = ventana;
   }
    public void levantarConexion() {
        try {
            socket = new Socket("localhost", 5050);
            System.out.println("Conectado a :" + socket.getInetAddress().getHostName());//muestro mensaje al puerto que me he conectado
        } catch (Exception e){
            System.out.println("Error al levantar conexión...!");
            System.exit(0);
        }
    }

    public void abrirFlujos() {
        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            System.out.println("No se pudieron abrir los flujos de entrada y salida para el cliente..!" + e);
        }
    }

    public void enviar() {
        try {
            bufferDeSalida.writeUTF(mensaje);
            System.out.println("metodo para enviar mensaje del cliente");
            bufferDeSalida.flush();
        } catch (IOException e){
            System.out.println("No se pudo enviar mensaje del cliente...!" + e);
        }
    }

    public void cerrarConexion() {
        try {
            bufferDeEntrada.close();
            bufferDeSalida.close();
            socket.close();
            System.out.println("conexiones cerradas");
        } catch (IOException e) {
            System.out.println("no se pudo cerrar conexiones del cliente correctamente" + e);
        }finally{
            System.exit(0);
        }
    }

    public void ejecutarConexion() {//metodo que corre un hilo
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                levantarConexion();
                abrirFlujos();
                enviar();
                //Thread.currentThread().interrupt();//hace una pausa
                //Thread.sleep();
            }
        });
        hilo.start();//inicia el hilo
    }

    public void run(){//hilo principal
      try{
         Thread.sleep(1000);
      }catch(InterruptedException e){
         e.printStackTrace();
      }
      ejecutarConexion();
      try{
         Thread.sleep(1000);
      }catch(InterruptedException e){
         e.printStackTrace();
      }
      recibirDatos();
    }
    
    public void recibirDatos(){
        String st = "";
        try {
            while(true){
                st =  bufferDeEntrada.readUTF();
                ventana.MostrarInformacion(st);//escribe mensaje en la ventana
                System.out.println("mensaje servidor: " + st);
                System.out.println("\n");
            }
        } catch (IOException e) {
            //System.out.println("No se ha podido recibir mensaje del servidor...!" + e);
        }
    }

}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Servidor_auxiliar implements Runnable{
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Socket socket;
    private VentanaServidor ventana;
    Object bandera = new Object();
    private String saldo;
    private String respuesta;
    private BaseDeDatos base = new BaseDeDatos(bandera);
    /*constructor vacio */
    public Servidor_auxiliar(){
        
    }
     /*constructor con parametros */
    public Servidor_auxiliar(Socket sock , DataInputStream in, DataOutputStream out, VentanaServidor ventana, Object bandera){
        this.entrada = in;
        this.salida = out;
        this.socket=sock;
        this.ventana = ventana;
        this.bandera = bandera;
    }
    /*funcion que recibe datos */
    public String recibirDatos(){
        String mensaje;
        try{
            mensaje = entrada.readUTF();
            base.VerificaCodigo(mensaje);
            if(base.VerificaCodigo(mensaje)){//si es verdadero, retorna el saldo al metodo enviaDatos() para ser enviados
                saldo = base.VerificarSaldo(mensaje);
            }
            else{
                saldo = "Codigo de cliente incorrecto!!";
            }
            //ventana.MostrarInformacion(mensaje);//escribe mensaje en la ventana
            System.out.println("mensaje Cliente: " + mensaje);
        }catch(IOException e){
            e.printStackTrace();
        }
        return saldo;
    }

    public void enviarDatos(String respuesta){
        
        try{
            System.out.println("estoy en el metodo enviar");
            respuesta.concat("El banco entrega fondos de:" + " " + respuesta);
            salida.writeUTF(respuesta);//envia respuesta al cliente
            salida.flush();
            ventana.MostrarInformacion(respuesta);
        }catch(IOException e){
            e.printStackTrace();
            ventana.MostrarInformacion("Error al enviar mensaje al cliente: " + e.getMessage());
        }
    }

    public void cerrarConexiones(){
        try{
            entrada.close();
            salida.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
             respuesta = recibirDatos();
            enviarDatos(respuesta);
        }finally{
            cerrarConexiones();

        }
      
    }  
    
}

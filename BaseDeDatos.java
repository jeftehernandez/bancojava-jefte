import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseDeDatos {
    private String usuario = "root";
	private String password = "";
    private String url = "";
    private Connection conexion;
    private Statement base;
    private ResultSet rs;
    private String [] subcadenas;
    private String [] fragmentos;
    Object bandera = new Object();

    public BaseDeDatos(Object bandera){
        this.bandera = bandera;
    }
    public boolean VerificaCodigo(String codigo){
        boolean codigoValido = false;
        int i;
        subcadenas = codigo.split(" ");
        fragmentos = new String[subcadenas.length];
        i=0;
        for(String subcadena : subcadenas){
            fragmentos[i]  = subcadena;
            i++;
        }

        try{
            url = "jdbc:mysql://localhost:3306/banco";
            conexion = DriverManager.getConnection (url, usuario, password);
            String consulta = "SELECT * FROM clientes WHERE Codigo = ?";
            try(PreparedStatement statement = conexion.prepareStatement(consulta)){//crea la consulta
                statement.setString(1, fragmentos[1]);
                try(ResultSet rs = statement.executeQuery()){
                    codigoValido = rs.next();
                }   
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                conexion.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        return codigoValido;
    }

    public String VerificarSaldo(String codigo){
        float saldo;
        int i;
        float saldo_total = 0;
        String saldo_cadena = null;
        String mensaje_retiro = null;
        String string = "EL Banco a entregado fondos de:";
        String aporte = "Su saldo es:";
        String actualizar;
        //boolean devuelve = false;
        subcadenas = codigo.split(" ");
        fragmentos = new String[subcadenas.length];
        i=0;
        for(String subcadena : subcadenas){
            fragmentos[i]  = subcadena;
            i++;
        }

        System.out.println("este es el codigo que necesito " + fragmentos [0] + fragmentos [1]);
        try{
            url = "jdbc:mysql://localhost:3306/banco";
            conexion = DriverManager.getConnection (url, usuario, password);
            System.out.println ("Coneccion Database OKKK!!!");
            try{
                base = conexion.createStatement();
                Class.forName ("com.mysql.jdbc.Driver");
                rs = base.getResultSet();
                synchronized(bandera){
                    rs = base.executeQuery("SELECT * FROM clientes WHERE Codigo = '"+fragmentos [1]+"'");
                
                    rs.next();
                    saldo = rs.getFloat("Saldo");
                   
                    if(Float.parseFloat(fragmentos [2]) <= saldo){
                        saldo_total = saldo - Float.parseFloat(fragmentos [2]);
                        saldo_cadena =  String.valueOf(saldo_total);
                        mensaje_retiro = string + " " + fragmentos [2] + " \n" + aporte + saldo_cadena;
                       //synchronized(bandera){
                        bandera.notify();
                        actualizar = "UPDATE clientes SET Saldo = '"+saldo_total+"' WHERE Codigo = '"+fragmentos [1]+"'";
                        base.executeUpdate(actualizar); //actualiza la base de datos
                       //}
                    }
                    else{
                        mensaje_retiro = "saldo insuficiente en su cuenta!!!";
                    }
                }
               
                System.out.println("Su saldo es: " + saldo_total);
                rs.close();//cerrar
				base.close();//cerrar
            }catch(Exception ee){
                ee.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        synchronized(bandera){//sincroniza para enviar mensaje , solo un hilo puede enviar mensaje a la vez
            return mensaje_retiro;
        }
    }
    
}

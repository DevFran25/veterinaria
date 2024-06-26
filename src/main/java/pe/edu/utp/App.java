package pe.edu.utp;
import jakarta.servlet.MultipartConfigElement;

import pe.edu.utp.business.*;
import pe.edu.utp.servlets.*;
import pe.edu.utp.util.*;
import pe.edu.utp.utils.*;

import java.net.URL;


/**
 * Hello UTP!
 *
 */
public class App
{

    public static RegistroCategoria RegCategoria = new RegistroCategoria();
    public static RegistroProducto RegProducto = new RegistroProducto();
    public static RegistroUsuario RegUsuario = new RegistroUsuario();
    public static RegistroCita registroCita = new RegistroCita();
    public static RegistroTrabajador RegTrabajador = new RegistroTrabajador();
    public static  RegistroCliente RegCliente = new RegistroCliente();


    public static void main( String[] args ) throws Exception {

        DataAccess dao = new DataAccessMariaDB(AppConfig.getConnectionStringCFN());

        //Cargar plantillas()

        String path = "src\\main\\resources\\web\\";
        JettyAdvUTP webserver = new JettyAdvUTP(8085,path);

        //USUARIO
        webserver.addServlet(ListarUsuarioServlet.class,"/listar_usuario");
        webserver.addServlet(UsuarioController.class, "/register_usuario");

        //TRABAJADOR
        webserver.addServlet(ListarTrabajadorServlet.class, "/listar_trabajador");
        webserver.addServlet(TrabajadorController.class, "/register_trabajador");

        //CATEGORIA
        webserver.addServlet(ListarCategoriaServlet.class,"/listar_categoria");
        webserver.addServlet(CategoriaController.class, "/register_categorias")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        //PRODUCTO
        webserver.addServlet(ListarProductoServlet.class,"/listar_producto");
        webserver.addServlet(ProductoController.class, "/register_producto")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));
        webserver.addServlet(CombosForProductos.class,"/add_producto");

        //CITAS
        webserver.addServlet(CitaController.class, "/api/crear_cita");


        URL myURL = new URL("http://localhost:8085/dashboard.html");
        System.out.println("*********************************************************");
        System.out.println("CLICK AQUI PARA ABRIR LA APLICACION:" + myURL);
        System.out.println("*********************************************************");
        webserver.start();

    }

}

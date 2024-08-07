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
public class App {

    public static RegistroCategoria RegCategoria = new RegistroCategoria();
    public static RegistroProducto RegProducto = new RegistroProducto();
    public static RegistroUsuario RegUsuario = new RegistroUsuario();
    public static RegistroCita registroCita = new RegistroCita();
    public static RegistroTrabajador RegTrabajador = new RegistroTrabajador();
    public static RegistroCliente RegCliente = new RegistroCliente();
    public static RegistroMascota RegMascotas = new RegistroMascota();
    public static RegistroServicios RegServicios =new RegistroServicios();
    public static RegistroVentas RegVentas = new RegistroVentas();
    public static RegistrarDetalle RegDetalle = new RegistrarDetalle();
    public static RegistroCitaServicio registroCitaServicio = new RegistroCitaServicio();

    public static void main(String[] args) throws Exception {

        DataAccess dao = new DataAccessMariaDB(AppConfig.getConnectionStringCFN());

        // Cargar plantillas()

        String path = "src\\main\\resources\\web\\";
        JettyAdvUTP webserver = new JettyAdvUTP(8085, path);

        //Index
        webserver.addServlet(ReportServlet.class, "/home");

        // USUARIO
        webserver.addServlet(ListarUsuarioServlet.class, "/listar_usuario");

        //VENTAS
        webserver.addServlet(VentaController.class, "/register_venta");
        webserver.addServlet(ListadoVentaServlet.class, "/listar_venta");
        webserver.addServlet(ListarDetalleServlet.class, "/listar_detalle");
        webserver.addServlet(DetalleTrabajadorServlet.class, "/detalle_trabajador");
        webserver.addServlet(CombosForVentas.class, "/add_ventas");
        webserver.addServlet(ProductosJsonServlet.class, "/productos_json");

        // TRABAJADOR
        webserver.addServlet(ListarTrabajadorServlet.class, "/listar_trabajador");
        webserver.addServlet(TrabajadorController.class, "/register_trabajador");

        // CLIENTE
        webserver.addServlet(ListarClienteServlet.class, "/listar_cliente");

        // CATEGORIA
        webserver.addServlet(ListarCategoriaServlet.class, "/listar_categoria");
        webserver.addServlet(CategoriaController.class, "/register_categorias")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        // PRODUCTO
        webserver.addServlet(ListarProductoServlet.class, "/listar_producto");
        webserver.addServlet(ProductoController.class, "/register_producto")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));
        webserver.addServlet(CombosForProductos.class, "/add_producto");

        // CITAS
        webserver.addServlet(CitaController.class, "/api/crear_cita");
        webserver.addServlet(ListarCitasServlets.class, "/listar_citas");

        // Listar citas servicio
        webserver.addServlet(ListarCitasServiciosServelt.class, "/listarServiciosCitas");

        // MASCOTAS
        webserver.addServlet(ListarMascotaServlet.class, "/listar_mascota");
        webserver.addServlet(MascotaController.class, "/register_mascotas").getRegistration()
                .setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        // SERVICIO
        webserver.addServlet(ListarServicios.class, "/listar_servicios");
        webserver.addServlet(ServiceController.class, "/register_servicios").getRegistration()
                .setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        // REGISTRAR CLIENTE
        webserver.addServlet( RegistrarClienteController.class, "/registrarCliente" );

        // LOGIN CLIENTE
        webserver.addServlet( LoginServlet.class, "/login" );

        //LOGOUT
        webserver.addServlet(LogoutServlet.class, "/logout");

        //INICIO
        webserver.addServlet(HomeServlet.class, "/inicio");

        webserver.addServlet(MascotaClienteController.class, "/add_cliente_mascota")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        webserver.addServlet(MascotaClienteServlet.class, "/getMascotas");

        webserver.addServlet(ClienteController.class, "/clientes");

        webserver.addServlet(AdminLoginController.class, "/admin/login");

        webserver.addServlet(LogoutAdminServlet.class, "/logoutAdmin");

        URL myURL = new URL("http://localhost:8085/home");
        System.out.println("*********************************************************");
        System.out.println("CLICK AQUI PARA ABRIR LA APLICACION:" + myURL);
        System.out.println("*********************************************************");
        webserver.start();

    }

}

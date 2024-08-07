package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.util.AppConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/register_trabajador")
public class TrabajadorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Captura de datos usuario
        String email = req.getParameter("txtusername");
        String password = req.getParameter("txtPASS");
        String estadoUsuario = "Activo";
        String rol = "TRABAJADOR";

        //Captura de datos trabajador
        String dniTrabajador = req.getParameter("txtdni");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String cargo = req.getParameter("txtcargo");
        String salario = req.getParameter("txtsalario");
        String direccion = req.getParameter("txtdireccion");
        String celular = req.getParameter("txtcelular");
        String fecha_contrato = LocalDate.now().toString();
        String estado = "Activo";

        try {
            // Validaciones
           //

            Double salarioStr = Double.parseDouble(salario);

            // Registro Usuario
            Usuario usuario = new Usuario(email, password, estadoUsuario);
            usuario.setRol(rol);
            App.RegUsuario.registrarUsuario(usuario);

            // Obtener el ID del usuario generado
            int idUsuario = usuario.getId();

            if (idUsuario == 0) {
                throw new SQLException("Error al registrar el usuario, ID no generado.");
            }

            // Registro Trabajador con el ID del usuario obtenido
            Trabajador trabajador = new Trabajador(dniTrabajador, idUsuario, nombre, apellidos, cargo, salarioStr, direccion, celular, fecha_contrato, estado);
            App.RegTrabajador.registrarTrabajador(trabajador);



            resp.sendRedirect("/listar_trabajador");

        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            String errorPagePath = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }
}


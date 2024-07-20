package pe.edu.utp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add_ventas")
public class CombosForVentas extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Cargar la página HTML
        String html = TextUTP.read("src\\main\\resources\\web\\add_detalle_ventas.html");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(html);
    }
}

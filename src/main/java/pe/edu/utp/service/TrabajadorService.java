package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TrabajadorService {
    private final Connection cnn;
    public TrabajadorService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addTrabajador(Trabajador tra) throws SQLException, IOException {
        String consulta = String.format("CALL registrarTrabajador(?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, tra.getDni());
            pstmt.setInt(2, tra.getUsuario_id());
            pstmt.setString(3, tra.getNombre());
            pstmt.setString(4, tra.getApellido());
            pstmt.setString(5, tra.getCargo());
            pstmt.setDouble(6, tra.getSalario());
            pstmt.setString(7, tra.getDireccion());
            pstmt.setString(8, tra.getCelular());
            pstmt.setString(9, tra.getFecha_contrato());
            pstmt.setString(10, tra.getEstado());

            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    //Metodo para ListarTrabajadores
    public List<Trabajador> getAllTrabajador() throws SQLException, NotFoundException {
        List<Trabajador> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarTrabajador()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                String dni = rst.getString("dni_trabajador");
                //int usuario_id = rst.getInt("usuario_id");
                String nombre = rst.getString("nombre");
                String apellidos = rst.getString("apellidos");
                String cargo = rst.getString("cargo");
                Double salario = rst.getDouble("salario");
                String direccion = rst.getString("direccion");
                String celular = rst.getString("celular");
                String fecha_contrato = rst.getString("fecha_contratacion");
                String estado = rst.getString("estado");
                //Nombre Usuario
                String nombreUsuario = rst.getString("usuario_nombre");

                Trabajador trabajador = new Trabajador(dni,nombre,apellidos,cargo,salario,direccion,celular,fecha_contrato,estado,nombreUsuario);
                lista.add(trabajador);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ningun colaborador en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

}

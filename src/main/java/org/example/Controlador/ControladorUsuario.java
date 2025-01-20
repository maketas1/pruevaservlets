package org.example.Controlador;


import jakarta.servlet.http.HttpServletRequest;
import org.example.Modelo.DAOGenerico2;
import org.example.Modelo.Usuario;

import java.time.LocalDate;
import java.util.List;

public class ControladorUsuario {

    private DAOGenerico2<Usuario> daoUsuario;

    public ControladorUsuario() {
        this.daoUsuario = new DAOGenerico2<>(Usuario.class);
    }

    public Usuario postUsuario(HttpServletRequest request) {
        Usuario usuario = new Usuario();

        if (request.getParameter("operacion").equals("create")) {
            usuario = registrarUsuario(request);
        } else if (request.getParameter("operacion").equals("update")) {
            usuario = update(request);
        }

        return usuario;
    }

    public Usuario registrarUsuario(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setDni(request.getParameter("dni"));
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setTipo(request.getParameter("tipo"));
        daoUsuario.add(usuario);
        return usuario;
    }

    public void registrarPenalizacion(Usuario usuario, int diasPenalizacion) {
        LocalDate fechaFinPenalizacion = LocalDate.now().plusDays(diasPenalizacion);
        usuario.setPenalizacionHasta(fechaFinPenalizacion);
        daoUsuario.update(usuario);
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios = daoUsuario.getAll();
        return usuarios;
    }

    public Usuario buscarUsuario(HttpServletRequest request) {
        String dni = request.getParameter("dni");
        Usuario usuario = daoUsuario.getById(dni);
        return usuario;
    }

    public Usuario update(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setDni(request.getParameter("dni"));
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setTipo(request.getParameter("tipo"));
        return daoUsuario.update(usuario);
    }

    public Usuario delete(HttpServletRequest request) {
        Usuario usuario = buscarUsuario(request);
        daoUsuario.deleteUsuario(usuario);
        return usuario;
    }
}

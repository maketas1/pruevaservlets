package org.example.Controlador;


import jakarta.servlet.http.HttpServletRequest;
import org.example.Modelo.DAOGenerico2;
import org.example.Modelo.Libro;

import java.util.List;

public class ControladorLibro {

    private DAOGenerico2<Libro, String> daoLibro;

    public ControladorLibro() {
        this.daoLibro = new DAOGenerico2<>(Libro.class, String.class);
    }

    public Libro postLibro(HttpServletRequest request) {
        Libro libro = new Libro();
        if (request.getParameter("operacion").equals("create")) {
            libro = registrarLibro(request);
        } else if (request.getParameter("operacion").equals("update")) {
            libro = update(request);
        }

        return libro;
    }

    public Libro registrarLibro(HttpServletRequest request) {
        String id = request.getParameter("idLibro");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        Libro libro = new Libro(id, titulo, autor);
        daoLibro.add(libro);

        return libro;
    }

    public List<Libro> getAll() {
        List<Libro> libro = daoLibro.getAll();
        return libro;
    }

    public Libro getById(HttpServletRequest request) {
        String id = request.getParameter("idLibro");
        Libro libro = daoLibro.getById(id);
        return libro;
    }

    public Libro update(HttpServletRequest request) {
        String id = request.getParameter("idLibro");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        Libro libro = new Libro(id, titulo, autor);
        daoLibro.update(libro);

        return libro;
    }

    public Libro delete(HttpServletRequest request) {
        String id = request.getParameter("idLibro");
        Libro libro = daoLibro.getById(id);
        daoLibro.deleteUsuario(libro);
        return libro;
    }
}

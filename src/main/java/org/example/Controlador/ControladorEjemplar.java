package org.example.Controlador;


import jakarta.servlet.http.HttpServletRequest;
import org.example.Modelo.DAOGenerico;
import org.example.Modelo.Ejemplar;
import org.example.Modelo.Libro;

import java.util.List;

public class ControladorEjemplar {

    private DAOGenerico<Ejemplar> daoEjemplar;
    private ControladorLibro controladorLibro = new ControladorLibro();
    private Ejemplar ejemplar = new Ejemplar();

    public ControladorEjemplar() {
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class);
    }

    public Ejemplar postEjemplar(HttpServletRequest request) {
        if (request.getParameter("operacion").equals("create")) {
            ejemplar = registrarEjemplar(request);
        } else if (request.getParameter("operacion").equals("update")) {
            ejemplar = update(request);
        }

        return ejemplar;
    }

    public Ejemplar registrarEjemplar(HttpServletRequest request) {
        Libro libro = controladorLibro.getById(request);
        ejemplar.setLibro(libro);
        ejemplar.setEstado(request.getParameter("estado"));
        daoEjemplar.add(ejemplar);
        return ejemplar;
    }

    public int obtenerStockDisponible(HttpServletRequest request) {
        String isbn = request.getParameter("isbn");
        List<Ejemplar> ejemplares = daoEjemplar.getAll();
        return (int) ejemplares.stream()
                .filter(e -> e.getLibro().equals(isbn) && e.getEstado().equalsIgnoreCase("Disponible"))
                .count();
    }

    public List<Ejemplar> getAll() {
        List<Ejemplar> ejemplar = daoEjemplar.getAll();
        return ejemplar;
    }

    public Ejemplar getById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("idEjemplar"));
        Ejemplar ejemplar = daoEjemplar.getById(id);
        return ejemplar;
    }

    public Ejemplar update(HttpServletRequest request) {
        Libro libro = controladorLibro.getById(request);
        ejemplar.setLibro(libro);
        ejemplar.setEstado(request.getParameter("estado"));
        return daoEjemplar.update(ejemplar);
    }

    public Ejemplar delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("idEjemplar"));
        ejemplar = daoEjemplar.getById(id);
        daoEjemplar.deleteUsuario(ejemplar);
        return ejemplar;
    }
}

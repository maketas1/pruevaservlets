package org.example.Controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.example.Modelo.DAOGenerico;
import org.example.Modelo.Ejemplar;
import org.example.Modelo.Prestamo;
import org.example.Modelo.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ControladorPrestamo {

    private DAOGenerico<Prestamo> daoPrestamo;
    private DAOGenerico<Ejemplar> daoEjemplar;
    private ControladorUsuario controladorUsuario = new ControladorUsuario();
    private ControladorEjemplar controladorEjemplar = new ControladorEjemplar();

    public ControladorPrestamo() {
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class);
    }

    public Prestamo postPrestamo(HttpServletRequest request) {
        Prestamo prestamo = new Prestamo();

        if (request.getParameter("operacion").equals("create")) {
            prestamo = registrarPrestamo(request);
        } else if (request.getParameter("operacion").equals("update")) {
            prestamo = update(request);
        } else if (request.getParameter("operacion").equals("devolucion")) {
            prestamo = registrarDevolucion(request);
        }

        return prestamo;
    }

    public List<Prestamo> getAll() {
        List<Prestamo> prestamos = daoPrestamo.getAll();
        return prestamos;
    }

    public Prestamo getById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("idPrestamo"));
        Prestamo prestamo = daoPrestamo.getById(id);
        return prestamo;
    }

    public Prestamo update(HttpServletRequest request) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(Integer.parseInt(request.getParameter("idPrestamo")));
        prestamo.setEjemplar(controladorEjemplar.getById(request));
        prestamo.setUsuario(controladorUsuario.buscarUsuario(request));
        return daoPrestamo.update(prestamo);
    }

    public Prestamo delete(HttpServletRequest request) {
        Prestamo prestamo = getById(request);
        daoPrestamo.deleteUsuario(prestamo);
        return prestamo;
    }

    public Prestamo registrarPrestamo(HttpServletRequest request) {
        Usuario usuario = controladorUsuario.buscarUsuario(request);
        Ejemplar ejemplar = controladorEjemplar.getById(request);
        if (usuario.getPenalizacionHasta() != null && !usuario.getPenalizacionHasta().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El usuario tiene una penalización activa y no puede realizar préstamos.");
        }

        List<Prestamo> prestamosActivos = daoPrestamo.getAll();
        long prestamosUsuario = prestamosActivos.stream()
                .filter(p -> p.getUsuario().equals(usuario) && p.getFechaDevolucion() == null)
                .count();

        if (prestamosUsuario >= 3) {
            throw new IllegalArgumentException("El usuario ya tiene 3 préstamos activos.");
        }

        if (!ejemplar.getEstado().equalsIgnoreCase("Disponible")) {
            throw new IllegalArgumentException("El ejemplar no está disponible para préstamo.");
        }

        Prestamo prestamo = new Prestamo(usuario, ejemplar, LocalDate.now());
        ejemplar.setEstado("Prestado");
        daoEjemplar.update(ejemplar);
        daoPrestamo.add(prestamo);

        return prestamo;
    }

    public Prestamo registrarDevolucion(HttpServletRequest request) {
        Ejemplar ejemplar = controladorEjemplar.getById(request);
        Usuario usuario = controladorUsuario.buscarUsuario(request);
        Prestamo prestamo = new Prestamo();
        prestamo.setEjemplar(ejemplar);
        prestamo.setUsuario(usuario);
        prestamo.setId(Integer.valueOf(request.getParameter("idPrestamo")));

        prestamo.setFechaDevolucion(LocalDate.now());
        daoPrestamo.update(prestamo);

        ejemplar.setEstado("Disponible");
        daoEjemplar.update(ejemplar);

        long diasAtraso = ChronoUnit.DAYS.between(prestamo.getFechaDevolucion(), LocalDate.now());
        if (diasAtraso > 0) {
            int diasPenalizacion = (int) diasAtraso * 15;
            controladorUsuario.registrarPenalizacion(usuario, diasPenalizacion);
        }

        return prestamo;
    }
}

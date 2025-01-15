package org.example.Controlador;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Modelo.DAOGenerico;
import org.example.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "librosServlet", value = "/libros-servlet")
public class LibrosServlet extends HttpServlet {

        DAOGenerico<Libro, String> daolibro;

    public void init(){
            daolibro = new DAOGenerico<>(Libro.class,String.class);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        if (request.getParameter("operacion").equals("create")) {
            String id = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");

            Libro libro = new Libro(id, titulo, autor);
            daolibro.add(libro);
            String json = conversorJson.writeValueAsString(libro);
            impresora.println(json);
        } else if (request.getParameter("operacion").equals("update")) {
            String id = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");

            Libro libro = new Libro(id, titulo, autor);
            daolibro.update(libro);
            String json = conversorJson.writeValueAsString(libro);
            impresora.println(json);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        String id = request.getParameter("id");

        Libro libro = daolibro.getById(id);
        daolibro.delete(libro);
        String json = conversorJson.writeValueAsString(libro);
        impresora.println(json);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        if (request.getParameter("operacion").equals("byId")) {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            Libro libro = daolibro.getById(request.getParameter("id"));
            System.out.println("En java " + libro.toString());

            String json = conversorJson.writeValueAsString(libro);
            System.out.println("En json " + json);
            impresora.println(json);
        } else if(request.getParameter("operacion").equals("all")) {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            List<Libro> listaLibros = daolibro.getAll();
            System.out.println("En java" + listaLibros);

            String json_response = conversorJson.writeValueAsString(listaLibros);
            System.out.println("En java json" + json_response);
            impresora.println(json_response);
        }

    }

    public void destroy(){

    }
}

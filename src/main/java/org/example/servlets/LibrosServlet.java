package org.example.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Controlador.ControladorLibro;
import org.example.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "librosServlet", value = "/libros-servlet")
public class LibrosServlet extends HttpServlet {

    ControladorLibro controladorLibro;

    public void init(){
        controladorLibro = new ControladorLibro();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Libro libro = controladorLibro.postLibro(request);
        String json = conversorJson.writeValueAsString(libro);
        impresora.println(json);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Libro libro = controladorLibro.delete(request);
        String json = conversorJson.writeValueAsString(libro);
        impresora.println(json);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        if (request.getParameter("operacion").equals("byId")) {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            Libro libro = controladorLibro.getById(request);
            System.out.println("En java " + libro.toString());

            String json = conversorJson.writeValueAsString(libro);
            System.out.println("En json " + json);
            impresora.println(json);
        } else if(request.getParameter("operacion").equals("all")) {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            List<Libro> listaLibros = controladorLibro.getAll();
            System.out.println("En java" + listaLibros);

            String json_response = conversorJson.writeValueAsString(listaLibros);
            System.out.println("En java json" + json_response);
            impresora.println(json_response);
        }

    }

    public void destroy(){

    }
}

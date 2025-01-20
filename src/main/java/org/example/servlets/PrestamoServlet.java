package org.example.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Controlador.ControladorPrestamo;
import org.example.Modelo.Prestamo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "prestamoServlet", value = "/prestamo-servlet")
public class PrestamoServlet extends HttpServlet {

    ControladorPrestamo controladorPrestamo;

    public void init(){
        controladorPrestamo = new ControladorPrestamo();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Prestamo prestamo = controladorPrestamo.postPrestamo(request);
        String json = conversorJson.writeValueAsString(prestamo);
        impresora.println(json);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Prestamo prestamo = controladorPrestamo.delete(request);
        String json = conversorJson.writeValueAsString(prestamo);
        impresora.println(json);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        if (request.getParameter("operacion").equals("byId")) {

            Prestamo prestamo = controladorPrestamo.getById(request);

            String json = conversorJson.writeValueAsString(prestamo);
            impresora.println(json);
        } else if(request.getParameter("operacion").equals("all")) {
            List<Prestamo> listaPrestamos = controladorPrestamo.getAll();

            String json_response = conversorJson.writeValueAsString(listaPrestamos);
            impresora.println(json_response);
        }
    }

    public void destroy(){

    }
}

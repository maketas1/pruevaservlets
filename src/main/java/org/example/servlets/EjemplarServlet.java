package org.example.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Controlador.ControladorEjemplar;
import org.example.Modelo.Ejemplar;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ejemplarServlet", value = "/ejemplar-servlet")
public class EjemplarServlet extends HttpServlet {

    ControladorEjemplar controladorEjemplar;

    public void init(){
        controladorEjemplar = new ControladorEjemplar();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Ejemplar ejemplar = controladorEjemplar.postEjemplar(request);
        String json = conversorJson.writeValueAsString(ejemplar);
        impresora.println(json);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Ejemplar ejemplar = controladorEjemplar.delete(request);
        String json = conversorJson.writeValueAsString(ejemplar);
        impresora.println(json);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        if (request.getParameter("operacion").equals("byId")) {

            Ejemplar ejemplar = controladorEjemplar.getById(request);

            String json = conversorJson.writeValueAsString(ejemplar);
            impresora.println(json);
        } else if(request.getParameter("operacion").equals("all")) {
            List<Ejemplar> listaLibros = controladorEjemplar.getAll();

            String json_response = conversorJson.writeValueAsString(listaLibros);
            impresora.println(json_response);
        } else if(request.getParameter("operacion").equals("stockDisponible")) {
            int stock = controladorEjemplar.obtenerStockDisponible(request);
            String json_response = conversorJson.writeValueAsString(stock);
            impresora.println(json_response);
        }

    }

    public void destroy(){

    }
}

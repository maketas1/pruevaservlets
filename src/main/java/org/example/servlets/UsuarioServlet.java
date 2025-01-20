package org.example.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Controlador.ControladorUsuario;
import org.example.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "usuarioServlet", value = "/usuario-servlet")
public class UsuarioServlet extends HttpServlet {

    ControladorUsuario controladorUsuario;

    public void init(){
        controladorUsuario = new ControladorUsuario();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Usuario usuario = controladorUsuario.postUsuario(request);
        String json = conversorJson.writeValueAsString(usuario);
        impresora.println(json);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Usuario usuario = controladorUsuario.delete(request);
        String json = conversorJson.writeValueAsString(usuario);
        impresora.println(json);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        if (request.getParameter("operacion").equals("byId")) {

            Usuario usuario = controladorUsuario.buscarUsuario(request);

            String json = conversorJson.writeValueAsString(usuario);
            impresora.println(json);
        } else if(request.getParameter("operacion").equals("all")) {
            List<Usuario> listaUsuarios = controladorUsuario.getAll();

            String json_response = conversorJson.writeValueAsString(listaUsuarios);
            impresora.println(json_response);
        }
    }

    public void destroy(){

    }
}

package org.example;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "calculadora", value = "/calculadora")
public class Calculadora extends HttpServlet {

    ObjectMapper conversorJson;
    public void init() {
        conversorJson = new ObjectMapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        String operador = request.getParameter("operador");
        int resultado = 0;
        String operadorMinusculas = operador.toLowerCase();

        switch (operadorMinusculas) {
            case "suma":
                resultado = num1 + num2;
                break;
            case "resta":
                resultado = num1 - num2;
                break;
            case "multiplicacion":
                resultado = num1 * num2;
                break;
            case "division":
                resultado = num1 / num2;
                break;
        }

        String resultado1 = "Resultado de " + operador + " " + num1 + " y " + num2 + " = " + resultado;
        PrintWriter out = response.getWriter();
    }

}

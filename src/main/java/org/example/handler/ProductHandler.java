package org.example.handler;

import com.google.gson.Gson;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ProductHandler extends HttpServlet {

    List<Product> productList = new ArrayList<>(
            Arrays.asList(
                    new Product(1, "one"),
                    new Product(2, "two"),
                    new Product(3, "three")
            )
    );


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String requestId = req.getParameter("id");
        if (requestId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Id cannot null");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Integer productId = Integer.parseInt(requestId);
            Optional<Product> product = productList.stream()
                    .filter(product1 -> productId.equals(product1.getId()))
                    .findFirst();
            if (product.isPresent()) {
                String productJson = new Gson().toJson(product.get());
                out.print(productJson);
            } else {
                out.print("A");
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");

        if (name == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Id cannot null");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            productList.add(new Product(productList.size() + 1, name));
            out.println("<h1> Inserted successfully </h1>");
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String requestId = req.getParameter("id");
        if (requestId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Id cannot null");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            Integer productId = Integer.parseInt(requestId);
            Optional<Product> product = productList.stream()
                    .filter(product1 -> productId.equals(product1.getId()))
                    .findFirst();
            if (product.isPresent()) {
                Predicate<Product> isQualified = item -> productId.equals(item.getId());
                productList.removeIf(isQualified);
                out.println("<h1> Deleted Successfully </h1>");
            } else {
                out.println("<h1> Invalid id </h1>");
            }
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestId = req.getParameter("id");
        String requestName = req.getParameter("name");

        PrintWriter out = resp.getWriter();

        if (requestId == null || requestName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Id cannot null");
        } else {
            Integer productId = Integer.parseInt(requestId);
            String productName = requestName;

            Optional<Product> result = productList.stream().parallel()
                    .filter(item -> productId.equals(item.getId())).findAny();

            if (!result.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                out.println("<h1> Id not found </h1>");
            } else {
                productList.stream()
                        .filter(item -> item.getId() == productId)
                        .findFirst()
                        .ifPresent(item -> item.setName(productName));
                out.println("<h1> Updated sucessfully </h1>");
            }

        }
    }


}

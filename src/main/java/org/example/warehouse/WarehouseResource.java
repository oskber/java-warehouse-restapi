package org.example.warehouse;

import entities.Category;
import entities.Product;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.WarehouseService;

import java.util.List;

@Path("/")
public class WarehouseResource {
    private WarehouseService warehouseService;

    public WarehouseResource() {
    }

    @Inject
    public WarehouseResource(WarehouseService warehouseService) {
        System.out.println("WarehouseResource created");
        this.warehouseService = warehouseService;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Welcome to the warehouse api!";
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<Product> products = warehouseService.getAllProducts();
        return Response.ok(products).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") String id) {
        return warehouseService.getProductById(id)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/products/categories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") Category category) {
        return Response.ok(warehouseService.getProductsByCategory(category))
                .build();
    }
}
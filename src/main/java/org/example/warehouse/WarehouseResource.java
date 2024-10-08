package org.example.warehouse;

import entities.Category;
import entities.Product;
import interceptor.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;

@Path("/")
@Log
public class WarehouseResource {
    private WarehouseService warehouseService;

    private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

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
    public Response getAllProducts(@QueryParam("page") @DefaultValue("1") int page,
                                   @QueryParam("size") @DefaultValue("10") int size) {
        List<Product> products = warehouseService.getAllProducts(page, size);
        return Response.ok(products).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") String id) {
        return warehouseService.getProductById(id)
                .map(product -> Response.ok(product).build())
                .orElseGet(() -> {
                    logger.error("Invalid id {}", id);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @GET
    @Path("/products/categories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") Category category) {
        return Response.ok(warehouseService.getProductsByCategory(category))
                .build();
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid Product product) {
        warehouseService.addProduct(product);
        logger.info("Product added: {}", product);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/search")
    @Produces("text/plain")
    public String search(@QueryParam("name") String name, @QueryParam("pages") int pages) {
        return name + " " + pages;
    }
}
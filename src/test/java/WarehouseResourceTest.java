import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.Category;
import entities.Product;
import exceptionmapper.IllegalProductWarehouseStateException;
import exceptionmapper.IllegalProductWarehouseStateExceptionMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.example.warehouse.ObjectMapperProvider;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.WarehouseService;
import org.example.warehouse.WarehouseResource;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseResourceTest {

    Dispatcher dispatcher;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        WarehouseService service = new WarehouseService();
        WarehouseResource warehouseResource = new WarehouseResource(service);
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(warehouseResource);

        ExceptionMapper<IllegalProductWarehouseStateException> exMapper =
                new IllegalProductWarehouseStateExceptionMapper();
        dispatcher.getProviderFactory().register(exMapper);

        dispatcher.getProviderFactory().register(new ObjectMapperProvider());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }

    @Test
    public void callingApiShouldReturnWelcomeMessage() throws URISyntaxException, UnsupportedEncodingException {
        MockHttpRequest request = MockHttpRequest.get("/");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
        assertEquals("Welcome to the warehouse api!", response.getContentAsString());
    }

    @Test
    public void whenPostingProductShouldReturn201Created() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
            MockHttpRequest request = MockHttpRequest.post("/products");
            String json = objectMapper.writeValueAsString(new Product("1", "Product1", Category.ELECTRONICS, 5, LocalDate.now(), LocalDate.now()));
            request.content(json.getBytes());
            request.contentType(MediaType.APPLICATION_JSON);
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(201, response.getStatus());
        }
}

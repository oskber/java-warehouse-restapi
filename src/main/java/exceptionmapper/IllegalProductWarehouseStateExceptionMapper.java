package exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class IllegalProductWarehouseStateExceptionMapper implements ExceptionMapper<IllegalProductWarehouseStateException> {
    private static final Logger logger = LoggerFactory.getLogger(IllegalProductWarehouseStateExceptionMapper.class);

    @Override
    public Response toResponse(IllegalProductWarehouseStateException e) {
        logger.error("Exception of type IllegalProductWarehouseStateException happened.");
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity("This endpoint doesn't seem to accept your request")
                .build();
    }
}
import com.oaiscloud.billing.core.domain.exceptions.ConflictException
import com.oaiscloud.billing.core.domain.exceptions.ExceptionResponse
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ExceptionHandlerProvider : ExceptionMapper<WebApplicationException> {
    @Override
    override fun toResponse(e: WebApplicationException): Response {
        val message = e.message ?: "Internal server error";
        val status: Response.Status = when(e) {
            is BadRequestException -> Response.Status.BAD_REQUEST
            is NotFoundException -> Response.Status.NOT_FOUND
            is ConflictException -> Response.Status.CONFLICT
            else -> Response.Status.INTERNAL_SERVER_ERROR
        }

        return Response.status(status)
            .entity(ExceptionResponse(message))
            .build();
    }
}
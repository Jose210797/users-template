package com.template.users.shared.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.template.users.shared.responses.CustomResponse;
import com.template.users.shared.services.ServerErrorLoggingService;

class GlobalHandlerErrorsTest {

    private GlobalHandlerErrors globalHandlerErrors;
    private ServerErrorLoggingService serverErrorLoggingService;

    @BeforeEach
    void setUp() {
        serverErrorLoggingService = mock(ServerErrorLoggingService.class);
        globalHandlerErrors = new GlobalHandlerErrors(serverErrorLoggingService);
    }

    @Test
    void testHandleGenericExceptions_ShouldGenerateErrorIdAndLogError() {
        Exception testException = new RuntimeException("Test exception message");
        String expectedErrorId = "ERR-20241116143022-000001-A7B9C2";
        
        when(serverErrorLoggingService.logServerError(any(Exception.class), any()))
            .thenReturn(expectedErrorId);

        CustomResponse<Object> response = globalHandlerErrors.handleGenericExceptions(testException);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertTrue(response.getMessage().contains(expectedErrorId), 
            "El mensaje debería contener el ID de error: " + expectedErrorId);
        assertTrue(response.getMessage().contains("Ocurrió un error inesperado"));
        
        verify(serverErrorLoggingService).logServerError(eq(testException), any());
    }

    @Test
    void testHandleGenericExceptions_MessageFormat() {
        Exception testException = new RuntimeException("Test exception");
        String errorId = "ERR-TEST-123";
        
        when(serverErrorLoggingService.logServerError(any(), any())).thenReturn(errorId);

        CustomResponse<Object> response = globalHandlerErrors.handleGenericExceptions(testException);

        String expectedMessage = String.format(
            "Ocurrió un error inesperado. Por favor contacte al administrador y proporcione el ID de error: %s", 
            errorId
        );
        assertEquals(expectedMessage, response.getMessage());
    }
}
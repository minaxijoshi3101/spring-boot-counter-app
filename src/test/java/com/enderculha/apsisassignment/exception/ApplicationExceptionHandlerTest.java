package com.enderculha.apsisassignment.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.enderculha.apsisassignment.dto.ErrorDto;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

class ApplicationExceptionHandlerTest {

  private final ApplicationExceptionHandler handler = new ApplicationExceptionHandler();

  private final WebRequest webRequest = mock(WebRequest.class);
  private final MethodArgumentNotValidException methodArgumentNotValidException = mock(
      MethodArgumentNotValidException.class);
  private final BindingResult bindingResult = mock(BindingResult.class);

  private static final String TEST_ERROR_MESSAGE = "testMessage";
  private static final String TEST_ERROR_DETAILS = "testError";

  @Test
  public void testHandleClientException() {

    ClientException clientException = new ClientException(HttpStatus.BAD_REQUEST, TEST_ERROR_MESSAGE,
        TEST_ERROR_DETAILS);
    ErrorDto actualBody = (ErrorDto) handler.handleClientException(clientException, webRequest).getBody();

    assertEquals(HttpStatus.BAD_REQUEST, actualBody.getStatus());
    assertEquals(TEST_ERROR_MESSAGE, actualBody.getMessage());
    assertEquals(Arrays.asList(TEST_ERROR_DETAILS), actualBody.getErrors());
  }

  @Test
  public void testHandleMethodArgumentNotValid() {

    FieldError fieldError = new FieldError("testObject", "testField", TEST_ERROR_MESSAGE);
    ObjectError objectError = new ObjectError("testGlobalObjectName", "testGlobalMessage");
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));
    when(bindingResult.getGlobalErrors()).thenReturn(Arrays.asList(objectError));

    ErrorDto actualBody = (ErrorDto) handler
        .handleMethodArgumentNotValid(methodArgumentNotValidException, new HttpHeaders(), HttpStatus.BAD_REQUEST,
            webRequest).getBody();

    assertEquals(HttpStatus.BAD_REQUEST, actualBody.getStatus());
    assertEquals("Input Validation Error", actualBody.getMessage());
    assertEquals("testField: " + TEST_ERROR_MESSAGE, actualBody.getErrors().get(0));
    assertEquals("testGlobalObjectName: testGlobalMessage", actualBody.getErrors().get(1));

  }

  @Test
  public void testHandleDefaultException() {

    Exception exception = new Exception(TEST_ERROR_MESSAGE);
    ErrorDto actualBody = handler.handleDefaultException(exception).getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualBody.getStatus());
    assertEquals(TEST_ERROR_MESSAGE, actualBody.getMessage());

  }


}

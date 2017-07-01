package controllers;

import authorization.AuthenticationService;
import domain.OrderService;
import models.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import requests.CreateOrderRequest;
import responses.CreateOrderResponse;
import responses.ErrorResponse;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    private OrderService orderService;
    private AuthenticationService authenticationService;

    public OrderController() { init(); }

    public void init() {
        this.orderService = new OrderService();
        this.authenticationService = new AuthenticationService();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces ="application/json")
    public ResponseEntity<?> getOrder(@PathVariable(value = "id") Integer id,
                                      @RequestHeader(value = "x-session-token") String token) {
        LOGGER.info(String.format("Getting order: %s", id));
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate session
        if(!authenticationService.authorized(token)
                || !authenticationService.hasPermission(token, "read")) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Unauthorized session."));
            LOGGER.warn(String.format("Failed to get order %s", errorResponse.getErrors(), id));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
        }

        Order order = orderService.getOrder(id);
        if(order == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Order not found."));
            LOGGER.warn(String.format("Failed to get order %s not found", errorResponse.getErrors(), id));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces ="application/json")
    @ResponseBody
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                         BindingResult bindingResult,
                                         @RequestHeader(value = "x-session-token") String token) {
        LOGGER.info(String.format("Creating order: %s", request.toString()));
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate session
        if(!authenticationService.authorized(token)
                || !authenticationService.hasPermission(token, "buy")) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Unauthorized session."));
            LOGGER.warn(String.format("Failed to create order %s %s", errorResponse.getErrors(), request.toString()));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
        }

        // Handle bad request validation
        if(bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            errorResponse.setErrors(errors);
            LOGGER.warn(String.format("Failed to create order %s %s", errors.toString(), request.toString()));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
        }

        // Handle invalid data in the request
        orderService.assertValidCreateOrderRequest(request);

        // Create the order by request
        try {
            Integer orderId = orderService.createOrder(request);
            LOGGER.info(String.format("Created order %s", orderId));
            return new ResponseEntity<>(
                    new CreateOrderResponse(orderId), headers, HttpStatus.CREATED);
        } catch (InvalidParameterException e) {
            LOGGER.warn(String.format("Failed to create order %s %s", e.getMessage(), request.toString()));
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList(e.getMessage()));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
        }
    }
}

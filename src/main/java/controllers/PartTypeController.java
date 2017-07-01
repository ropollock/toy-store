package controllers;

import authorization.AuthenticationService;
import domain.PartTypeService;
import models.PartType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import requests.PatchPartTypeRequest;
import responses.ErrorResponse;
import responses.GetPartTypesResponse;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/part-types")
public class PartTypeController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    private AuthenticationService authenticationService;
    private PartTypeService partTypeService;

    public PartTypeController() { init(); }

    public void init() {
        this.authenticationService = new AuthenticationService();
        this.partTypeService = new PartTypeService();
    }

    @RequestMapping(method = RequestMethod.GET, produces ="application/json")
    public ResponseEntity<?> getPartType(@RequestHeader(value = "x-session-token") String token) {
        LOGGER.info("Getting all part types");
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate session
        if(!authenticationService.authorized(token)
                || !authenticationService.hasPermission(token, "read")) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Unauthorized session."));
            LOGGER.warn("Failed to get all part types unauthorized.");
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
        }

        List<PartType> partTypes = partTypeService.getPartTypes();
        return new ResponseEntity<>(new GetPartTypesResponse(partTypes), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, produces ="application/json", value = "/{id}")
    public ResponseEntity<?>  patchPartType(@PathVariable(value = "id") Integer id,
                                            @Valid @RequestBody PatchPartTypeRequest request,
                                            BindingResult bindingResult,
                                            @RequestHeader(value = "x-session-token") String token) {
        LOGGER.info(String.format("Patching part type %s surcharge to %s", id, request.getSurcharge()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate session
        if(!authenticationService.authorized(token)
                || !authenticationService.hasPermission(token, "write")) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Unauthorized session."));
            LOGGER.warn("Failed to patch part type unauthorized.");
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
        }

        // Handle bad request validation
        if(bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            errorResponse.setErrors(errors);
            LOGGER.warn(String.format("Failed to patch part type %s %s", errors.toString(), request.toString()));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
        }

        // Patch part type
        try {
            partTypeService.updateSurcharge(id, request.getSurcharge());
            return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Not found."));
            LOGGER.warn(String.format("Failed to patch part type %s not found.", id));
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
        }
    }
}

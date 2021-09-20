package scalable.solutions.orders.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import scalable.solutions.orders.dao.OrderDTO;
import scalable.solutions.orders.service.OrderService;

import java.util.Collection;

import static scalable.solutions.orders.controllers.GlobalExceptionHandler.ErrorMessageData;

@RestController
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@SecurityRequirement(name = "integration")
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Operation(description = "Get a list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class)))
    })
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/orders")
    public ResponseEntity<Collection<OrderDTO>> orders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @Operation(description = "Find and retrieve an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class)))
    })
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> order(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Operation(description = "Create new order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order has been created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class)))
    })
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PostMapping(value = "/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @Operation(description = "Update an existing order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class)))
    })
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PutMapping(value = "/order")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order) {
        return ResponseEntity.ok(orderService.updateOrder(order));
    }

    @Operation(description = "Delete an existing order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageData.class)))
    })
    @PreAuthorize("hasRole('role_admin')")
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }
}

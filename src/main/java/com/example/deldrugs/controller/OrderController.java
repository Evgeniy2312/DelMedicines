package com.example.deldrugs.controller;

import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.order.OrderStatusDTO;
import com.example.deldrugs.entity.order.Order;
import com.example.deldrugs.entity.order.OrderStatus;
import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.service.OrderService;
import com.example.deldrugs.util.ConverterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderDTO orderDTO, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        orderDTO.setUser(user);
        if(orderService.addOrder(orderDTO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/updateOrder")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody OrderStatusDTO orderStatusDTO){
        if(orderService.updateOrderStatus(ConverterDTO.getOrderForUpd(orderStatusDTO))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getByUser/{id}")
    public ResponseEntity<List<OrderDTO>> getOrderByUser(@PathVariable long id){
        List<Order> orders = orderService.getByUser(id);
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getOrdersForResp(orders), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<OrderDTO>> getOrderByStatus(@PathVariable String status){
        List<Order> orders = orderService.getOrdersByStatus(OrderStatus.valueOf(status.toUpperCase(Locale.ENGLISH)));
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getOrdersForResp(orders), HttpStatus.OK);
    }



}

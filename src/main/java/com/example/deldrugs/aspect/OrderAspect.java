package com.example.deldrugs.aspect;

import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.order.OrderStatusDTO;
import com.example.deldrugs.entity.order.Order;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class OrderAspect {

    private final Logger logger = LoggerFactory.getLogger(OrderAspect.class);


    @Pointcut("execution(public * com.example.deldrugs.controller.OrderController.addOrder(..)) && args(orderDTO, ..)")
    public void addOrder(OrderDTO orderDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.OrderController.updateOrder(..)) && args(orderDTO)")
    public void updateOrder(OrderStatusDTO orderDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.OrderController.getOrderByUser(..)) && args(id)")
    public void getOrderByUser(long id) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.OrderController.getOrderByStatus(..)) && args(status)")
    public void getOrderByStatus(String status) {}

    @AfterReturning(pointcut = "addOrder(orderDTO)", argNames = "orderDTO, resp", returning = "resp")
    public void addOrd(OrderDTO orderDTO, ResponseEntity<Order> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Order was added by user");
        }
    }

    @AfterReturning(pointcut = "updateOrder(orderDTO)", argNames = "orderDTO, resp", returning = "resp")
    public void updOrd(OrderStatusDTO orderDTO, ResponseEntity<Order> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Order was updated to status {} by user", orderDTO.getStatus());
        }
    }


    @AfterReturning(pointcut = "getOrderByUser(userId)", argNames = "userId, resp", returning = "resp")
    public void getOrdByUserId(long userId, ResponseEntity<List<OrderDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Orders were received by user with id {}", userId);
        }
    }

    @AfterReturning(pointcut = "getOrderByStatus(status)", argNames = "status, resp", returning = "resp")
    public void getOrdByStatus(String status, ResponseEntity<List<OrderDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Orders were received withs status {}", status);
        }
    }
}

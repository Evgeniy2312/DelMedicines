package com.example.deldrugs.service;

import com.example.deldrugs.dao.MedicineRepository;
import com.example.deldrugs.dao.OrderRepository;
import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.order.Order;
import com.example.deldrugs.entity.order.OrderStatus;
import com.example.deldrugs.util.ConverterDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MedicineRepository medicineRepository;

    public boolean addOrder(OrderDTO orderDTO) {
        testAddress(orderDTO);
        Order order = ConverterDTO.getOrder(orderDTO);
        if (order.getMedicine().size() <= 4 &&
                testSubAmountOfMedicine(order.getMedicine())){
            subAmountOfMedicine(order.getMedicine());
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    private void testAddress(OrderDTO order) {
        if (order.getAddress() == null){
            Address address = order.getUser().getAddress();
            order.setAddress(address);
        }
    }

    private boolean testSubAmountOfMedicine(List<Medicine> list) {
        for (Medicine medicine : list) {
            Medicine medicine1 = medicineRepository.getById(medicine.getId());
            if ((medicine1.getAmount() - medicine.getAmount()) < 0){
                return false;
            }
        }
        return true;
    }

    private void subAmountOfMedicine(List<Medicine> medicines){
        medicines.forEach(medicine -> medicine.setAmount(medicineRepository.getById(medicine.getId()).getAmount() - medicine.getAmount()));
        for (Medicine medicine: medicines) {
            Medicine medicine1 = medicineRepository.getById(medicine.getId());
            medicine1.setAmount(medicine1.getAmount() - medicine.getAmount());
            medicineRepository.save(medicine1);
        }
    }

    public boolean updateOrderStatus(Order order){
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        if(orderOptional.isPresent() && !orderOptional.get().getOrderStatus().equals(order.getOrderStatus())){
            orderOptional.get().setOrderStatus(order.getOrderStatus());
            orderRepository.save(orderOptional.get());
            return true;
        }
        return false;
    }

    public List<Order> getByUser(long userId){
        return orderRepository.getOrdersByUserId(userId);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus){
        return orderRepository.getOrdersByOrderStatus(orderStatus);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }


    public boolean deleteOrder(long id){
        if(orderRepository.existsById(id)){
            orderRepository.delete(orderRepository.getById(id));
            return true;
        }
        return false;
    }




}

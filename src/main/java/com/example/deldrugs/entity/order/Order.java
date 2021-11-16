package com.example.deldrugs.entity.order;

import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany
    @MapsId("order_id")
    @JoinColumn(name = "medicine_id",unique = false,insertable = false, updatable = false)
    private List<Medicine> medicine = new ArrayList<>();
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User user;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Address address;
    @Enumerated
    private OrderStatus orderStatus;
    private LocalDateTime localDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(medicine, order.medicine) && Objects.equals(user, order.user) && Objects.equals(address, order.address) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicine, user, address, orderStatus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "medicine=" + medicine +
                ", user=" + user +
                ", address=" + address +
                ", orderStatus=" + orderStatus +
                ", localDateTime=" + localDateTime +
                '}';
    }
}

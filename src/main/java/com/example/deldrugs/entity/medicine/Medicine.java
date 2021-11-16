package com.example.deldrugs.entity.medicine;


import com.example.deldrugs.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 4000)
    private String description;
    private int amount;
    private double price;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User user;
    @Enumerated
    private CategoryOfMedicine category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(name, medicine.name) && Objects.equals(user, medicine.user) && category == medicine.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user, category);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}

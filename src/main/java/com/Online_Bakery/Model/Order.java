package com.Online_Bakery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long amount;

    private String orderStatus;

    @ManyToOne
    private Address delivery_address;

    @OneToMany
    private List<OrderItems> items;

    private int totalItems;

    private Long total_price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

//    private Payment payment;
}

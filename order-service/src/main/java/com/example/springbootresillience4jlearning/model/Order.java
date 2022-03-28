package com.example.springbootresillience4jlearning.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "ORDER_R4J")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String category;

}

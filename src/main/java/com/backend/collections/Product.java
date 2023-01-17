package com.backend.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private int inventory;
    private boolean enabled;
    private int min;
    private int max;
}

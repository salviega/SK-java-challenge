package com.backend.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Purchase {
    @Id
    private String id;
    private String date;
    private String idType;
    private String clientName;
    private ArrayList<Item> Shopping;

}

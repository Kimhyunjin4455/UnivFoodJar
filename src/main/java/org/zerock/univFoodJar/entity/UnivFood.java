package org.zerock.univFoodJar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UnivFood extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uno;
    private String restaurantName;
    private String foodField;
    private String signatureMenu;
    private String contact;
    private String address;

    public void changeRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }
    public void changeSignatureMenu(String signatureMenu){
        this.signatureMenu = signatureMenu;
    }
    public void changeContact(String contact){
        this.contact = contact;
    }
    public void changeAddress(String address){
        this.address = address;
    }


}


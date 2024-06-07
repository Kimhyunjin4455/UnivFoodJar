package org.zerock.univFoodJar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "univFood")
public class UnivFoodImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private UnivFood univFood;

}


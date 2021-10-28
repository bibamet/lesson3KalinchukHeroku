package com.example.lesson1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_seq")
    @SequenceGenerator(name = "weapon_seq", sequenceName = "weapon_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String detailedName;

    private Integer range;

    private Integer damage;

    private Boolean broken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_entity_id")
    private PlayerEntity playerEntity;

}

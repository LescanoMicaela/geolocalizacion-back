package com.daw.proyecto.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Colony implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int cats;

    private boolean registration;


    private Geolocation location;

    @OneToMany(mappedBy = "id.colony", fetch = FetchType.EAGER)
    private List<Feeding> feeding;

    private LocalDate createDate;

    private LocalDate lowDate;
}

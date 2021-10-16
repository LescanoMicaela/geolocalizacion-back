package com.daw.proyecto.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Alimentacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch = FetchType.LAZY)
    Colonia colonia;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dia;
    private boolean agua;
    private boolean comida;
    private boolean hayComida;
    private boolean hayAgua;


}

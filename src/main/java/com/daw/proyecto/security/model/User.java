package com.daw.proyecto.security.model;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Feeding;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type User.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
    private List<Colony> colonies;

    @OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
    private List<Feeding> feeding;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean registration;
    private String name;
    private String surname;
    private String surname2;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private boolean enabled;
}

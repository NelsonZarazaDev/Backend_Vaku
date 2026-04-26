package com.Vaku.Vaku.apiRest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class PersonsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long persId;
    @NotBlank(message = "Los nombres no pueden estar vacios")
    private String persNames;
    @NotBlank(message = "Los apellidos no pueden estar vacios")
    private String persLastNames;
    @NotBlank(message = "El documento no puede estar vacio")
    private String persDocument;
    @NotBlank(message = "Los sexo no puede estar vacios")
    private String persSex;
    @NotBlank(message = "La direccion no puede estar vacios")
    private String persAddress;
    private LocalDate persDateBirth;
    @NotBlank(message = "El rol no pueden estar vacios")
    private String persRole;
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El telefono no tiene un formato valido")
    private String persPhone;
    @Email(message = "El email no tiene un formato valido")
    private String persEmail;
    private String persPassword;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "persons", fetch = FetchType.LAZY)
    private Set<EmployeesEntity> employees;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "persons", fetch = FetchType.LAZY)
    private Set<ParentsEntity> parents;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "persons", fetch = FetchType.LAZY)
    private Set<ChildrensEntity> childrens;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CitysEntity citys;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return persPassword;
    }

    @Override
    public String getUsername() {
        if (persEmail != null && !persEmail.isBlank()) {
            return persEmail;
        }
        return persDocument;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

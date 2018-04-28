package com.salvador.user.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (unique = true, nullable = false)
    @Email (message ="Please provide a valid email.")
    @NotEmpty (message = "Please provide an email.")
    private String email;

    @Column (name = "first_name", nullable = false)
    @NotEmpty (message = "Please provide your first name.")
    private String firstName;

    @Column (name = "last_name", nullable = false)
    @NotEmpty (message = "Please provide your last name.")
    private String lastName;

    @Column (name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column (name = "confirmation_token")
    private String confirmationToken;

    @ManyToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable ( name = "users_roles",
                    joinColumns = {@JoinColumn (name = "user_id")},
                    inverseJoinColumns = {@JoinColumn (name = "role_id")})
    private Set<Role> roles = new HashSet();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

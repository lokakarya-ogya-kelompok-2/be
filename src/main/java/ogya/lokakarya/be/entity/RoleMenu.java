package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="TBL_APP_ROLE_MENU")
public class RoleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

}

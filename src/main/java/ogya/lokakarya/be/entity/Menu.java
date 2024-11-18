package ogya.lokakarya.be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_APP_MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name ="MENU_NAME" , length = 30)
    private String menuName;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

//    @ManyToMany(mappedBy = "menu")
//    private Set<Role> roles = new HashSet<>();
}

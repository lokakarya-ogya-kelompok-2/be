package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_DIVISION")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "DIVISION_NAME", length = 50)
    private String divisionName;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY)
    private List<AccessDivision> accessDivisions;

    // @ManyToMany(mappedBy = "divisions")
    // private Set<User> users = new HashSet<>();
}

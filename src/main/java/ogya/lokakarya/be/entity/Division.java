package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "DIVISION_NAME", length = 50, unique = true)
    private String divisionName;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    // @ManyToMany(mappedBy = "divisions")
    // private Set<User> users = new HashSet<>();
}

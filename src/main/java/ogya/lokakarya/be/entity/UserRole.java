package ogya.lokakarya.be.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TBL_APP_USER_ROLE")
public class UserRole {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)  DEFAULT (UUID_TO_BIN(UUID()))")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @PrePersist
    private void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}

package ogya.lokakarya.be.entity.common;

import java.util.Date;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@MappedSuperclass
@Data
public class AuditMetadata {
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}

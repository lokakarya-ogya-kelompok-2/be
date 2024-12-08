package ogya.lokakarya.be.dto.achievement;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.Achievement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AchievementReq {
    @NotBlank(message = "Achievement cannot be blank")
    @Size(max = 100, message = "Achievement cannot exceed 100 characters")
    @JsonProperty("achievement")
    private String achievementName;

    @NotNull(message = "Group ID cannot be null")
    @JsonProperty("group_id")
    private UUID groupAchievementId;

    @NotNull(message = "Enabled status cannot be null")
    @JsonProperty("enabled")
    private Boolean enabled;

    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setAchievement(this.achievementName);
        achievement.setEnabled(enabled);
        return achievement;
    }
}

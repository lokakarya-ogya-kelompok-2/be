package ogya.lokakarya.be.dto.achievement;

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

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AchievementReq {
    @NotBlank(message = "Achievement cannot be blank")
    @Size(max = 100, message = "Achievement cannot exceed 100 characters")
    @JsonProperty("achievement")
    private String achievement;

    @NotNull(message = "Group ID cannot be null")
    @JsonProperty("group_id")
    private UUID groupAchievement;

    @NotNull(message = "Enabled status cannot be null")
    @JsonProperty("enabled")
    private Boolean enabled;

    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setAchievement(this.achievement);
        achievement.setEnabled(enabled);
        return achievement;
    }
}

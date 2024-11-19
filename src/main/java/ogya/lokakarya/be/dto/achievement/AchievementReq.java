package ogya.lokakarya.be.dto.achievement;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("achievement")
    private String achievement;

    @JsonProperty("group_id")
    private UUID groupAchievement;

    @JsonProperty("enabled")
    private Boolean enabled;

    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setAchievement(this.achievement);
        achievement.setEnabled(enabled);
        return achievement;
    }
}

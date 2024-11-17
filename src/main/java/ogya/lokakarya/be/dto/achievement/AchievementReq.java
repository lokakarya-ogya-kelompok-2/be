package ogya.lokakarya.be.dto.achievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Achievement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AchievementReq {
    @JsonProperty("achievement")
    private String achievement;

//    @JsonProperty("group_id")
//    private GroupAchievement groupAchievement;

    @JsonProperty("enabled")
    private Boolean enabled;

    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setAchievement(this.achievement);
        achievement.setEnabled(enabled);
        return achievement;
    }
}

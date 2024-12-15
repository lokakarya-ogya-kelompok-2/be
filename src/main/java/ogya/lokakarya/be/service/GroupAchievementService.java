package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;

public interface GroupAchievementService {
    GroupAchievementDto create(GroupAchievementReq data);

    List<GroupAchievementDto> getAllGroupAchievements(GroupAchievementFilter filter);

    GroupAchievementDto getGroupAchievementById(UUID id);

    GroupAchievementDto updateGroupAchievementById(UUID id,
            GroupAchievementReq groupAchievementReq);

    boolean deleteGroupAchievementById(UUID id);
}

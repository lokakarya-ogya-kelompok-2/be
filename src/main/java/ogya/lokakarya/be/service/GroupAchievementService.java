package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;

import java.util.List;
import java.util.UUID;

public interface GroupAchievementService {
    GroupAchievementDto create(GroupAchievementReq data);
    List<GroupAchievementDto> getAllGroupAchievements();
    GroupAchievementDto getGroupAchievementById(UUID id);
    GroupAchievementDto updateGroupAchievementById(UUID id, GroupAchievementReq groupAchievementReq);
    boolean deleteGroupAchievementById(UUID id);
}

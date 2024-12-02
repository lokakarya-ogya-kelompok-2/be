package ogya.lokakarya.be.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RoleMenuService {
    void update(Map<UUID, List<UUID>> roleMenuData);
}

package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;

import java.util.List;
import java.util.UUID;

public interface EmpDevPlanService {
    EmpDevPlanDto create(EmpDevPlanReq data);
    List<EmpDevPlanDto> getAllEmpDevPlans();
    EmpDevPlanDto getEmpDevPlanById(UUID id);
    EmpDevPlanDto updateEmpDevPlanById(UUID id, EmpDevPlanReq empDevPlanReq);
    boolean deleteEmpDevPlanById(UUID id);
}

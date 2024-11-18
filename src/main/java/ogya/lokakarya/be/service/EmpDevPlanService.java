package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.EmpDevPlan;

import java.util.List;
import java.util.UUID;

public interface EmpDevPlanService {
    EmpDevPlan create(EmpDevPlanReq data);
    List<EmpDevPlanDto> getAllEmpDevPlans();
    EmpDevPlanDto getEmpDevPlanById(UUID id);
    EmpDevPlanDto updateEmpDevPlanById(UUID id, EmpDevPlanReq empDevPlanReq);
    boolean deleteEmpDevPlanById(UUID id);
}

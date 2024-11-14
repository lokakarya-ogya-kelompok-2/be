package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.repository.division.DivisionRepository;
import ogya.lokakarya.be.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DivisionServiceimpl implements DivisionService {
    @Autowired
    private DivisionRepository divisionRepository;

    @Override
    public Division create(CreateDivision data){
        return divisionRepository.save(data.toEntity());
    }

    @Override
    public List<DivisionDto> getAllDivisions() {
        List<DivisionDto> listResult=new ArrayList<>();
        List<Division> roleList=divisionRepository.findAll();
        for(Division division : roleList) {
            DivisionDto result= convertToDto(division);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public DivisionDto getDivisionById(UUID id) {
        Optional<Division> listData;
        try{
            listData=divisionRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        Division data= listData.get();
        return convertToDto(data);
    }

    @Override
    public DivisionDto updateDivisionById(UUID id, CreateDivision createDivision) {
        Optional<Division> listData= divisionRepository.findById(id);
        if(listData.isPresent()){
            Division division= listData.get();
            if(!createDivision.getDivisionName().isBlank()){
                division.setDivisionName(createDivision.getDivisionName());
            }
            DivisionDto divisionDto= convertToDto(division);
            return divisionDto;
        }
        return null;
    }

    @Override
    public boolean deleteDivisionById(UUID id) {
        Optional<Division> listData= divisionRepository.findById(id);
        if(listData.isPresent()){
            divisionRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }


    private DivisionDto convertToDto(Division data){
        DivisionDto result = new DivisionDto(data);
        return result;
    }
}

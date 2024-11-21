package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuReq;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.repository.MenuRepository;
import ogya.lokakarya.be.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuDto create(MenuReq data) {
        return new MenuDto(menuRepository.save(data.toEntity()));
    }

    @Override
    public List<MenuDto> getAllMenus() {
        List<MenuDto> listResult=new ArrayList<>();
        List<Menu> roleList=menuRepository.findAll();
        for(Menu menu : roleList) {
            MenuDto result= convertToDto(menu);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public MenuDto getMenuById(UUID id) {
        Optional<Menu> listData;
        try{
            listData=menuRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        Menu data= listData.get();
        return convertToDto(data);
    }

    @Override
    public MenuDto updateMenuById(UUID id, MenuReq menuReq) {
        Optional<Menu> listData= menuRepository.findById(id);
        if(listData.isPresent()){
            Menu menu= listData.get();
            if(!menuReq.getMenuName().isBlank()){
                menu.setMenuName(menuReq.getMenuName());
            }
            MenuDto menuDto= convertToDto(menu);
            menuRepository.save(menu);
            return menuDto;
        }
        return null;
    }

    @Override
    public boolean deleteMenuById(UUID id) {
        Optional<Menu> listData= menuRepository.findById(id);
        if(listData.isPresent()){
            menuRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private MenuDto convertToDto(Menu data){
        MenuDto result = new MenuDto(data);
        return result;
    }
}

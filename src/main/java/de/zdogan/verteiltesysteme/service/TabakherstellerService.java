package de.zdogan.verteiltesysteme.service;

import de.zdogan.verteiltesysteme.model.RequestResult;
import de.zdogan.verteiltesysteme.model.database.Tabakhersteller;
import de.zdogan.verteiltesysteme.repository.TabakherstellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TabakherstellerService {
    private TabakherstellerRepository tabakherstellerRepository;

    @Autowired
    public TabakherstellerService(TabakherstellerRepository tabakherstellerRepository){
        this.tabakherstellerRepository = tabakherstellerRepository;
    }

    public List<Tabakhersteller> findAll(){
        return tabakherstellerRepository.findAll();
    }

    public List<Tabakhersteller> getOne(Integer herstellerid) {
        List<Tabakhersteller> help = tabakherstellerRepository.findAll();
        return help.stream().filter(p -> p.id().equals(herstellerid)).collect(Collectors.toList());

    }

    public RequestResult<Tabakhersteller> insertTabakhersteller(Tabakhersteller tabakhersteller) {
        RequestResult<Tabakhersteller> result = new RequestResult<Tabakhersteller>().success(false).represantation(null);
        try{
            tabakhersteller.id(null);
            tabakherstellerRepository.saveAndFlush(tabakhersteller);
            result.message("Successfully Inserted!").success(true).represantation(tabakhersteller);
        }catch (Exception e){
            result.message("Error: Insert unsuccessfull").success(false).represantation(null);
        }
        return result;
    }

    public RequestResult<Tabakhersteller> deleteTabakhersteller(Integer tabakherstellerId) {
        RequestResult<Tabakhersteller> result = new RequestResult<Tabakhersteller>().success(false).represantation(null);
        Tabakhersteller actualTabakhersteller = tabakherstellerRepository.findById(tabakherstellerId).orElse(null);
        try {
            tabakherstellerRepository.delete(actualTabakhersteller);
            result.message("Successfully Deleted!").success(true).represantation(null);
        } catch (Exception e){
            result.message("Error: Delete unsuccessfull").success(false).represantation(actualTabakhersteller);
        }
        return result;
    }

    public RequestResult<Tabakhersteller> patchTabakhersteller(Integer id, Tabakhersteller tabakhersteller) {
        RequestResult<Tabakhersteller> result = new RequestResult<Tabakhersteller>().success(false).represantation(null);
        Tabakhersteller oldTabakhersteller = tabakherstellerRepository.findById(id).orElse(null);

        if(oldTabakhersteller.id()==null){
            result.message("Error: Tabakhersteller does not exist in Database").success(false).represantation(null);
        }else{
            try {
                tabakhersteller.id(oldTabakhersteller.id());
                if(tabakhersteller.name()==null){
                    tabakhersteller.name(oldTabakhersteller.name());
                }
                if(tabakhersteller.adresse()==null){
                    tabakhersteller.adresse(oldTabakhersteller.adresse());
                }
                tabakherstellerRepository.saveAndFlush(tabakhersteller);
                result.message("Successfully Patched").success(true).represantation(tabakhersteller);
            } catch (Exception e) {
                result.message("Error: Patch unsuccessfull").success(false).represantation(oldTabakhersteller);
            }
        }

        return result;

    }
}

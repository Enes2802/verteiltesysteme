package de.zdogan.verteiltesysteme.service;

import de.zdogan.verteiltesysteme.model.RequestResult;
import de.zdogan.verteiltesysteme.model.database.Tabakhersteller;
import de.zdogan.verteiltesysteme.model.database.Tabaksorte;
import de.zdogan.verteiltesysteme.repository.TabakherstellerRepository;
import de.zdogan.verteiltesysteme.repository.TabaksorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TabaksortenService {

    private final TabaksorteRepository tabaksorteRepository;
    private final TabakherstellerRepository tabakherstellerRepository;

    @Autowired
    public TabaksortenService(TabaksorteRepository tabaksorteRepository, TabakherstellerRepository tabakherstellerRepository){
        this.tabaksorteRepository = tabaksorteRepository;
        this.tabakherstellerRepository = tabakherstellerRepository;
    }

    public List<Tabaksorte> findAll() {
        return tabaksorteRepository.findAll();
    }

    public List<Tabaksorte> findAllByHersteller(Integer herstellerId) {
        Tabakhersteller tabakhersteller = tabakherstellerRepository.getOne(herstellerId);
        return tabakhersteller.tabaksorteList();
    }

    public List<Tabaksorte> getOne(Integer herstellerId,Integer sortenId) {
        Tabakhersteller tabakhersteller = tabakherstellerRepository.findById(herstellerId).orElse(null);
        return tabakhersteller.tabaksorteList().stream().filter(p -> p.id().equals(sortenId)).collect(Collectors.toList());
    }

    public RequestResult<Tabaksorte> insertTabaksorte(Tabaksorte tabaksorte, Integer herstellerId){
        RequestResult<Tabaksorte> result = new RequestResult<Tabaksorte>().success(false).represantation(null);
        Tabakhersteller actualTabakhersteller = tabakherstellerRepository.getOne(herstellerId);
        if(actualTabakhersteller==null){
            result.message("Error: Tabakhersteller does not exist!").success(false).represantation(null);
        } else{
            try{
                tabaksorte.id(null);
                tabaksorte.tabakhersteller(actualTabakhersteller);
                tabaksorteRepository.saveAndFlush(tabaksorte);
                result.message("Insert successfull!").success(true).represantation(tabaksorte);
            } catch (Exception e){
                result.message("Error: Insert unsuccessfull!").success(false).represantation(null);
            }
        }
        return result;
    }

    public RequestResult<Tabaksorte> deleteTabaksorte(Integer herstellerId, Integer sorteId) {
        RequestResult<Tabaksorte> result = new RequestResult<Tabaksorte>().success(false).represantation(null);
        Tabakhersteller actualTabakhersteller = tabakherstellerRepository.findById(herstellerId).orElse(null);
        Tabaksorte actualTabaksorte = tabaksorteRepository.getOne(sorteId);
        if (actualTabakhersteller == null) {
            result.message("Error: Tabakhersteller does not exist!").success(false).represantation(null);
        }else if(actualTabaksorte == null) {
            result.message("Error: Tabaksorte does not exist!").success(false).represantation(null);
        } else {
            try{
                tabaksorteRepository.delete(actualTabaksorte);
                result.message("Successfully Deleted!").success(true).represantation(null);
            } catch (Exception e) {
                result.message("Error: Delete unsuccessfull").success(false).represantation(actualTabaksorte);
            }
        }
        return result;
    }

    public RequestResult<Tabaksorte> patchTabaksorte(Integer herstellerId, Tabaksorte tabaksorte, Integer sorteId) {
        RequestResult<Tabaksorte> result = new RequestResult<Tabaksorte>().success(false).represantation(null);
        Tabakhersteller actualTabakhersteller = tabakherstellerRepository.getOne(herstellerId);
        Tabaksorte actualTabaksorte = tabaksorteRepository.getOne(sorteId);
        if (actualTabakhersteller == null) {
            result.message("Error: Tabakhersteller does not exist!").success(false).represantation(null);
        }else if(actualTabaksorte == null) {
            result.message("Error: Tabaksorte does not exist!").success(false).represantation(null);
        } else {
            try {
                tabaksorte.id(actualTabaksorte.id());
                tabaksorte.tabakhersteller(actualTabaksorte.tabakhersteller());
                if (tabaksorte.name() == null) {
                    tabaksorte.name(actualTabaksorte.name());
                }
                if (tabaksorte.geschmack() == null) {
                    tabaksorte.geschmack(actualTabaksorte.geschmack());
                }
                if (tabaksorte.preis() == 0.0) {
                    tabaksorte.preis(actualTabaksorte.preis());
                }
                tabaksorteRepository.saveAndFlush(tabaksorte);
                result.message("Patched Successfully").success(true).represantation(tabaksorte);
            } catch (Exception e) {
                result.message("Error: Patch unsuccessfull").success(false).represantation(actualTabaksorte);
            }
        }
        return result;
    }
}

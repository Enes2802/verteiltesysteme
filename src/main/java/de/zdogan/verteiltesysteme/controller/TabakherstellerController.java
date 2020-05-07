package de.zdogan.verteiltesysteme.controller;

import de.zdogan.verteiltesysteme.model.RequestResult;
import de.zdogan.verteiltesysteme.model.database.Tabakhersteller;
import de.zdogan.verteiltesysteme.service.TabakherstellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TabakherstellerController {

    private final TabakherstellerService tabakherstellerService;

    @Autowired
    public TabakherstellerController(TabakherstellerService tabakherstellerService){
        this.tabakherstellerService = tabakherstellerService;
    }

    @GetMapping(
            value = "/hersteller",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<Tabakhersteller>> getAllTabakhersteller(){
        List<Tabakhersteller> tabakherstellerList = tabakherstellerService.findAll();
        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").withSelfRel().withType("GET")));
        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").withRel("insert hersteller").withType("POST")));

        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("specific hersteller").withType("GET")));
        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("delete hersteller").withType("DELETE")));
        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("patch hersteller").withType("PATCH")));

        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("sorten").withType("GET")));
        tabakherstellerList.forEach(tabakhersteller -> tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("insert sorten").withType("POST")));

        return new ResponseEntity<>(tabakherstellerList, HttpStatus.OK);
    }

    @GetMapping(
            value ="/hersteller/{herstellerid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<Tabakhersteller>> getOneTabakhersteller(@PathVariable(name="herstellerid") Integer herstellerid) {
        List<Tabakhersteller> tabakherstellerList = tabakherstellerService.getOne(herstellerid);
        tabakherstellerList.forEach(tabakhersteller ->tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withSelfRel().withType("GET")));
        tabakherstellerList.forEach(tabakhersteller ->tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("delete hersteller").withType("DELETE")));
        tabakherstellerList.forEach(tabakhersteller ->tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("patch hersteller").withType("PATCH")));

        tabakherstellerList.forEach(tabakhersteller ->tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("sorten").withType("GET")));
        tabakherstellerList.forEach(tabakhersteller ->tabakhersteller.add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("insert sorten").withType("POST")));
        return new ResponseEntity<>(tabakherstellerList,HttpStatus.OK);
    }

    @PostMapping(
            value="/hersteller",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestResult<Tabakhersteller>> insertTabakhersteller(@RequestBody Tabakhersteller tabakhersteller){
        RequestResult<Tabakhersteller> rawTabakhersteller = tabakherstellerService.insertTabakhersteller(tabakhersteller);
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").withSelfRel().withType("POST"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("get hersteller").withType("GET"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("delete hersteller").withType("DELETE"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("patch hersteller").withType("PATCH"));

        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("sorten").withType("GET"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("insert sorten").withType("POST"));

        return new ResponseEntity<>(rawTabakhersteller,HttpStatus.OK);
    }

    @DeleteMapping(
            value="/hersteller/{herstellerid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<RequestResult<Tabakhersteller>> deleteTabakhersteller(@PathVariable(name="herstellerid") Integer herstellerid ) {
        return new ResponseEntity<>(tabakherstellerService.deleteTabakhersteller(herstellerid), HttpStatus.OK);
    }

    @PatchMapping(
            value ="/hersteller/{herstellerid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RequestResult<Tabakhersteller>> patchTabakhersteller(@PathVariable(name="herstellerid") Integer herstellerid, @RequestBody Tabakhersteller tabakhersteller ) {
        RequestResult<Tabakhersteller> rawTabakhersteller = tabakherstellerService.patchTabakhersteller(herstellerid,tabakhersteller);
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withSelfRel().withType("PATCH"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("get hersteller").withType("GET"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").withRel("post hersteller").withType("POST"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).withRel("delete hersteller").withType("DELETE"));

        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("sorten").withType("GET"));
        rawTabakhersteller.represantation().add(linkTo(TabakherstellerController.class).slash("hersteller").slash(tabakhersteller.id()).slash("sorten").withRel("post sorte").withType("POST"));
        return new ResponseEntity<>(rawTabakhersteller,HttpStatus.OK);
    }

}

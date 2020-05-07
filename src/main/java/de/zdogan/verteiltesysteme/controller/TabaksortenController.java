package de.zdogan.verteiltesysteme.controller;

import de.zdogan.verteiltesysteme.model.RequestResult;
import de.zdogan.verteiltesysteme.model.database.Tabaksorte;
import de.zdogan.verteiltesysteme.service.TabaksortenService;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TabaksortenController {

    private final TabaksortenService tabaksortenService;

    @Autowired
    public TabaksortenController(TabaksortenService tabaksortenService) {
        this.tabaksortenService = tabaksortenService;
    }

    @GetMapping(
            value="/sorten",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<Tabaksorte>> getAllTabaksorten(){
        List<Tabaksorte> tabaksorteList = tabaksortenService.findAll();
        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("sorten").withSelfRel().withType("GET")));
        return new ResponseEntity<>(tabaksorteList,HttpStatus.OK);
    }

    @GetMapping(
            value="/hersteller/{herstellerid}/sorten",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<Tabaksorte>> getAllTabaksortenByHersteller(@PathVariable(name="herstellerid") Integer herstellerid) {
        List<Tabaksorte> tabaksorteList = tabaksortenService.findAllByHersteller(herstellerid);
        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").withSelfRel().withType("GET")));
        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").withRel("post sorte").withType("POST")));

        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("specific sorte").withType("GET")));
        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("delete sorte").withType("DELETE")));
        tabaksorteList.forEach(tabaksorte -> tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("patch sorte").withType("PATCH")));
        return new ResponseEntity<>(tabaksorteList, HttpStatus.OK);
    }

    @GetMapping(
            value="/hersteller/{herstellerid}/sorten/{sorteid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<Tabaksorte>> getTabaksorte(@PathVariable(name="herstellerid") Integer herstellerid, @PathVariable(name="sorteid") Integer sorteid) {
        List<Tabaksorte> tabaksorteList = tabaksortenService.getOne(herstellerid,sorteid);
        tabaksorteList.forEach(tabaksorte ->tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withSelfRel().withType("GET")));
        tabaksorteList.forEach(tabaksorte ->tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("delete sorte").withType("DELETE")));
        tabaksorteList.forEach(tabaksorte ->tabaksorte.add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("patch sorte").withType("PATCH")));
        return new ResponseEntity<>(tabaksorteList,HttpStatus.OK);
    }

    @PostMapping(
            value ="/hersteller/{herstellerid}/sorten",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RequestResult<Tabaksorte>> insertTabaksorte(@PathVariable(name="herstellerid") Integer herstellerid, @RequestBody Tabaksorte tabaksorte){
        RequestResult<Tabaksorte> rawTabaksorte = tabaksortenService.insertTabaksorte(tabaksorte,herstellerid);
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").withSelfRel().withType("POST"));
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").withRel("sorten").withType("GET"));

        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("specific sorte").withType("GET"));
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("delete sorte").withType("DELETE"));
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("patch sorte").withType("PATCH"));
        return new ResponseEntity<>(rawTabaksorte,HttpStatus.OK);
    }

    @DeleteMapping(
            value ="/hersteller/{herstellerid}/sorten/{sorteid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<RequestResult<Tabaksorte>> deleteTabaksorte(@PathVariable(name="herstellerid") Integer herstellerid,@PathVariable(name="sorteid") Integer sorteid) {
        return new ResponseEntity<>(tabaksortenService.deleteTabaksorte(herstellerid,sorteid),HttpStatus.OK);
    }

    @PatchMapping(
            value ="/herstelelr/{herstellerid}/sorten/{sorteid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RequestResult<Tabaksorte>> patchTabaksorte(
            @PathVariable(name="herstellerid") Integer herstellerid,
            @PathVariable(name="sorteid") Integer sorteid,
            @RequestBody Tabaksorte tabaksorte) {
        RequestResult<Tabaksorte> rawTabaksorte = tabaksortenService.patchTabaksorte(herstellerid,tabaksorte,sorteid);
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withSelfRel().withType("PATCH"));
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("specific sorte").withType("GET"));
        rawTabaksorte.represantation().add(linkTo(TabaksortenController.class).slash("hersteller").slash(herstellerid).slash("sorten").slash(tabaksorte.id()).withRel("delete sorte").withType("DELETE"));

        return new ResponseEntity<>(rawTabaksorte, HttpStatus.OK);
    }
}

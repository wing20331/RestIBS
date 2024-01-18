package com.ibs.Ibs2.Controller;

import com.ibs.Ibs2.Logic.Pet;
import com.ibs.Ibs2.Logic.PetModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petmodel = PetModel.getInstance();

    private static final AtomicInteger newId = new AtomicInteger(1);


    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet){
        petmodel.add(pet, newId.getAndIncrement());
        if (petmodel.getAll().size()!=1){
            return "Питомец успешно создан";
        }else {
            return "Вы создали своего первого питомца";
        }
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petmodel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petmodel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> deletePet(@RequestBody Map<String, Integer> req){
        int id = req.get("id");
        petmodel.getAll().remove(id);
        return petmodel.getAll();
    }


}

package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.dto.CharacterDto;
import de.neuefische.asterixapi.dto.CharacterUpdateDto;
import de.neuefische.asterixapi.model.Character;
import de.neuefische.asterixapi.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
public class AsterixController {

    CharacterService service;

    @Autowired
    public AsterixController(CharacterService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Character> getAllCharacters(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String profession) {
        return service.getAllCharacters(name, profession);
    }

    @GetMapping("/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return service.getAverageAgeByProfession(profession);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable String id) {
        return service.getCharacterById(id);
    }

    @PostMapping
    public Character addCharacter(@RequestBody CharacterDto characterDto) {
        return service.addCharacter(characterDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable String id, @RequestBody CharacterUpdateDto updateDto) {
        return service.updateCharacter(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        return service.deleteCharacter(id);
    }

}
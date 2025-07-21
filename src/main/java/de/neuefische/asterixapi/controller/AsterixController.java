package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.neuefische.asterixapi.model.Character;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
public class AsterixController {

    CharacterRepository repo;

    @Autowired
    public AsterixController(CharacterRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    public List<Character> getAllCharacters() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Character addCharacter(@RequestBody Character character) {
        return repo.save(character);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable String id, @RequestBody Character character) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Character oldChar = getCharacterById(id).getBody();

        Character updatedChar = repo.save(oldChar
                .withName(character.name())
                .withAge(character.age())
                .withProfession(character.profession()));

        return ResponseEntity.ok(updatedChar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
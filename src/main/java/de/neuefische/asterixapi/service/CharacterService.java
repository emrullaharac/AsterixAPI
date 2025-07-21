package de.neuefische.asterixapi.service;

import de.neuefische.asterixapi.model.Character;
import de.neuefische.asterixapi.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    CharacterRepository repo;

    @Autowired
    public CharacterService(CharacterRepository repo) {
        this.repo = repo;
    }

    public List<Character> getAllCharacters(String name, String profession) {
        if (name != null && profession != null) {
            return repo.findByNameAndProfession(name, profession);
        } else if (name != null) {
            return repo.findByName(name);
        } else if (profession != null) {
            return repo.findByProfession(profession);
        }
        return repo.findAll();
    }

    public double getAverageAgeByProfession(String profession) {
        List<Character> characters = repo.findByProfession(profession);
        return characters.stream()
                .mapToInt(Character::age)
                .average()
                .orElse(0.0);
    }

    public ResponseEntity<Character> getCharacterById(String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public Character addCharacter(Character character) {
        return repo.save(character);
    }

    public ResponseEntity<Character> updateCharacter(String id, Character character) {
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

    public ResponseEntity<Void> deleteCharacter(String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

package de.neuefische.asterixapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import de.neuefische.asterixapi.model.Character;

import java.util.List;

@Repository
public interface CharacterRepository extends MongoRepository<Character, String> {
    List<Character> findByName(String name);
    List<Character> findByProfession(String profession);
    List<Character> findByNameAndProfession(String name, String profession);
}

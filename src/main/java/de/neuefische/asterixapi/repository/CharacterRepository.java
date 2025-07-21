package de.neuefische.asterixapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import de.neuefische.asterixapi.model.Character;

@Repository
public interface CharacterRepository extends MongoRepository<Character, String> {

}

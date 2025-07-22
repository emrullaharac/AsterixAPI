package de.neuefische.asterixapi.service;

import de.neuefische.asterixapi.dto.CharacterUpdateDto;
import de.neuefische.asterixapi.model.Character;
import de.neuefische.asterixapi.repository.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CharacterServiceTest {
    private CharacterRepository repo;
    private IdService idService;
    private CharacterService service;

    @BeforeEach
    void setUp() {
        repo = mock(CharacterRepository.class);
        idService = mock(IdService.class);
        service = new CharacterService(repo, idService);
    }

    @Test
    void getAllCharacters_returnsAll() {
        //GIVEN
        List<Character> characters = List.of(
                new Character("1", "Asterix", 35, "Warrior"),
                new Character("2", "Obelix", 35, "Supplier")
        );
        when(repo.findAll()).thenReturn(characters);
        //WHEN
        List<Character> actual = service.getAllCharacters(null, null);

        //THEN
        assertEquals(2, actual.size());
        assertEquals("Asterix", actual.getFirst().name());
    }

    @Test
    void getCharacterById_returnsCharacter_whenFound() {
        //GIVEN
        Character character = new  Character("1", "Asterix", 35, "Warrior");
        when(repo.findById("1")).thenReturn(Optional.of(character));

        //WHEN
        var response = service.getCharacterById("1");

        //THEN
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Asterix", response.getBody().name());
    }

    @Test
    void updateCharacter_updatesCharacter_whenExists() {
        //GIVEN
        String id = "1";
        Character oldChar = new   Character(id, "Asterix", 35, "Warrior");
        CharacterUpdateDto updateDto = new CharacterUpdateDto("Obelix", 40, "Supplier");
        Character updatedChar = oldChar
                .withName(updateDto.name())
                .withAge(updateDto.age())
                .withProfession(updateDto.profession());

        when(repo.findById(id)).thenReturn(Optional.of(oldChar));
        when(repo.save(any(Character.class))).thenReturn(updatedChar);

        //WHEN
        ResponseEntity<Character> response = service.updateCharacter(id,  updateDto);

        //THEN
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Obelix", response.getBody().name());
        assertEquals(40, response.getBody().age());
        assertEquals("Supplier", response.getBody().profession());
        verify(repo).save(any(Character.class));
    }

    @Test
    void updateCharacter_returnsNotFound_whenCharacterDoesNotExist() {
        //GIVEN
        String id = "nonexistent-id";
        CharacterUpdateDto updateDto = new CharacterUpdateDto("Obelix", 40, "Supplier");
        when(repo.findById(id)).thenReturn(Optional.empty());

        //WHEN
        ResponseEntity<Character> response = service.updateCharacter(id,  updateDto);

        //THEN
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(repo, never()).save(any());

    }

    @Test
    void deleteCharacter_shouldDelete_whenCharacterExists() {
        //GIVEN
        String id = "1";
        when(repo.existsById(id)).thenReturn(true);

        //WHEN
        ResponseEntity<Void> response = service.deleteCharacter("1");

        //THEN
        assertEquals(204, response.getStatusCode().value());
        verify(repo).deleteById(id);
    }

    @Test
    void deleteCharacter_shouldReturnNotFound_whenCharacterDoesNotExist() {
        //GIVEN
        String id = "nonexistent-id";
        when(repo.existsById(id)).thenReturn(false);

        //WHEN
        ResponseEntity<Void> response = service.deleteCharacter(id);

        //THEN
        assertEquals(404, response.getStatusCode().value());
        verify(repo, never()).deleteById(any());

    }
}
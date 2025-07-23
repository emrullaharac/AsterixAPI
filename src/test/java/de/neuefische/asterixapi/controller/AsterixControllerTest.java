package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.model.Character;
import de.neuefische.asterixapi.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AsterixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository repo;

    @Test
    void getAllCharacters_shouldReturnListOfOneCharacter_whenCalled() throws Exception {
        //GIVEN
        Character character = new  Character("1", "Asterix", 35, "Warrior");
        repo.save(character);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters"))
        //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                  [
                                    {
                                      "id": "1",
                                      "name": "Asterix",
                                      "age": 35,
                                      "profession": "Warrior"
                                    }
                                  ]
                                  """
                ));
    }

    @Test
    void getCharacterById_shouldReturnCharacter_whenCalled() throws Exception {
        //GIVEN
        Character character = new Character("1", "Asterix", 35, "Warrior");
        repo.save(character);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                    {
                                      "id": "1",
                                      "name": "Asterix",
                                      "age": 35,
                                      "profession": "Warrior"
                                    }
                                  """
                ));
    }

    @Test
    void deleteCharacter_shouldRemoveCharacterFromDb() throws Exception {
        //GIVEN
        Character character = new Character("1", "Asterix", 35, "Warrior");
        repo.save(character);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/asterix/characters/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
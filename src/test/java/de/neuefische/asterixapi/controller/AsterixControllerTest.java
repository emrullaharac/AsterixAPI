package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.model.Character;
import de.neuefische.asterixapi.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
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
    void addCharacter_shouldReturnCharacter_whenCalledWithCharacterDtoObject() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/asterix/characters")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                  {
                                    "name": "Obelix",
                                    "age": 38,
                                    "profession": "Supplier"
                                  }
                                """))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                  {
                                    "name": "Obelix",
                                    "age": 38,
                                    "profession": "Supplier"
                                  }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

    }

    @Test
    void updateCharacter_shouldReturnUpdatedCharacter_whenCalledWithValidData() throws Exception {
        //GIVEN
        Character character = new Character("2", "Idefix", 4, "Dog");
        repo.save(character);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/asterix/characters/2")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "name": "Idefix",
                                    "age": 5,
                                    "profession": "Dog"
                                }
                                """
                        ))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "id": "2",
                                    "name": "Idefix",
                                    "age": 5,
                                    "profession": "Dog"
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
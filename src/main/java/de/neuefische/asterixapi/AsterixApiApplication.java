package de.neuefische.asterixapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsterixApiApplication {

    /**
     Programmierung: Id-Generierung
     • Erstelle eine IdService Klasse, in der du eine Methode bereitstellst, um eine zufällige ID zu generieren.
     • Verwende diese Klasse im CharacterService, um die ID für einen neuen Charakter zu generieren. (Verwende Dependency Injection)
     • Erstelle einen Unit-Test für die addCharacter Methode im CharacterService, mocke auch den IdService.

     Bonus: Strikte Tests
     • Überprüfe in jedem Unit-Test, dass die Methode deines Mocks nur einmal aufgerufen wurde und keine anderen Methoden aufgerufen wurden.

     Bonus: Statisches Mocking
     Erstelle einen Unit-Test für die IdService Klasse.
     • Recherchiere, wie man den statischen Methodenaufruf UUID.randomUUID() mocken kann.

     Bonus: Datum/ArgumentCaptor
     • Erweitere deine Character-Klasse, um den Erstellungszeitstempel einzuschließen.
     • Verwende die addCharacter Methode im CharacterService, um den aktuellen Erstellungszeitstempel zu bestimmen und zu speichern.
     • Ändere deinen Unit-Test und überprüfe, dass der Erstellungszeitstempel nach dem 1. Januar 2020 und vor 21 Uhr liegt. Verwende den ArgumentCaptor von Mockito Link

     Bonus: Zufälliger Charakter
     • Erstelle eine Methode public void setSeed(long seed) im CharacterService.
     • Erstelle eine Methode getRandomCharacter im CharacterService, die einen Seed verwendet, um einen zufälligen Charakter zurückzugeben.
     • Schreibe einen Unit-Test für diese Methode, setze den Seed auf einen festen Wert, sodass jedes Mal derselbe Charakter ausgewählt wird.

     Bonus: Ohne Mockito
     • Schreibe alle deine Tests ohne Mockito.
     */

    public static void main(String[] args) {
        SpringApplication.run(AsterixApiApplication.class, args);
    }

}

package de.neuefische.asterixapi.dto;

import lombok.With;

@With
public record CharacterDto(String name, int age, String profession) {
}

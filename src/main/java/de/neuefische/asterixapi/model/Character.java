package de.neuefische.asterixapi.model;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Document("characters")
public record Character(String id, String name, int age, String profession) {
}
package progi.projekt.security;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

/*
 * Klasa dodana za potrebe tutoriala:
 * https://devcenter.heroku.com/articles/preparing-a-spring-boot-app-for-production-on-heroku
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }
}

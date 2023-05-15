package server.player.character.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.player.attributes.levels.types.ClassesAttributeTypes;
import server.player.character.dto.Character;
import server.player.character.dto.CreateCharacterRequest;
import server.player.character.repository.PlayerCharacterRepository;
import server.util.TestCharacterUtil;

@MicronautTest
public class PlayerCharacterServiceTest {

    // This test will be very similar to
    // PlayerCharacterRepository test as there's limited functionality
    @Inject PlayerCharacterService playerCharacterService;

    @Inject PlayerCharacterRepository playerCharacterRepository;

    private static final String TEST_USERNAME = "USER";
    private static final String TEST_CHARACTER_NAME = "CHARACTER";

    @BeforeEach
    void cleanDb() {
        playerCharacterRepository.deleteByCharacterName(TEST_CHARACTER_NAME);
    }

    @Test
    void testSaveCharacterAndGetCharacterForUser() {
        // Given
        CreateCharacterRequest createCharacterRequest = createBasicCharacterRequest();

        Character testCharacter =
                TestCharacterUtil.getBasicTestCharacter(
                        TEST_USERNAME, TEST_CHARACTER_NAME, Map.of("key", "value"));

        // When
        Character character =
                playerCharacterService.createCharacter(createCharacterRequest, TEST_USERNAME);

        // Then
        Assertions.assertThat(character)
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(testCharacter);

        Assertions.assertThat(character.getUpdatedAt()).isNotNull();
    }

    public static CreateCharacterRequest createBasicCharacterRequest() {
        CreateCharacterRequest createCharacterRequest = new CreateCharacterRequest();
        createCharacterRequest.setName(TEST_CHARACTER_NAME);
        Map<String, String> appearanceInfo = Map.of("key", "value");
        createCharacterRequest.setAppearanceInfo(appearanceInfo);
        createCharacterRequest.setClassName(ClassesAttributeTypes.FIGHTER.getType());

        return createCharacterRequest;
    }
}

package service;

import entity.Preference;
import infrastructure.database.PreferenceRepository;
import dto.PreferenceDTO;

import java.util.Optional;

/**
 * PreferenceService
 * Manages user music preferences and their updates.
 * <p>
 * Key responsibilities:
 * - Creating and updating user preferences
 * - Validating preference data format
 * <p>
 * Dependencies:
 * - PreferenceRepository for data persistence
 * <p>
 * Usage example:
 * PreferenceService prefService = new PreferenceService(preferenceRepository);
 * Result<Preference> result = prefService.updatePreferences(new PreferenceDTO(userId, songs, genres, artists, albums));
 */
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public Result<Preference> updatePreferences(PreferenceDTO prefDTO) {
        try {
            Preference preference = preferenceRepository
                    .findByUserId(prefDTO.userId())
                    .orElse(new Preference());

            preference.setUserId(prefDTO.userId());
            preference.setSongs(prefDTO.songs());
            preference.setGenres(prefDTO.genres());
            preference.setArtists(prefDTO.artists());
            preference.setAlbums(prefDTO.albums());

            if (preference.getPreferenceId() == 0) {
                preferenceRepository.save(preference);
            }
            else {
                preferenceRepository.update(preference);
            }

            return Result.success(preference);
        }
        catch (Exception e) {
            return Result.failure("Failed to update preferences: " + e.getMessage());
        }
    }

    public Result<Preference> getPreferences(int userId) {
        try {
            Optional<Preference> preferenceOptional = preferenceRepository.findByUserId(userId);
            return preferenceOptional.map(Result::success).orElseGet(() ->
                    Result.failure("Preferences not found for user ID: " + userId));
        }
        catch (Exception e) {
            return Result.failure("Failed to retrieve preferences: " + e.getMessage());
        }
    }
}

package service;

import entity.Preference;
import infrastructure.database.PreferenceRepository;
import dto.PreferenceDTO;

/**
 * PreferenceService
 * Manages user music preferences and their updates.
 *
 * Key responsibilities:
 * - Creating and updating user preferences
 * - Validating preference data format
 *
 * Dependencies:
 * - PreferenceRepository for data persistence
 *
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
            } else {
                preferenceRepository.update(preference);
            }

            return Result.success(preference);
        } catch (Exception e) {
            return Result.failure("Failed to update preferences: " + e.getMessage());
        }
    }
}

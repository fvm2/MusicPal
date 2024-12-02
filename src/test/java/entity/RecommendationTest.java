package entity;

import junit.framework.TestCase;

public class RecommendationTest extends TestCase {
    private Recommendation recommendation;

    public void setUp() throws Exception {
        super.setUp();
        // Create test recommendation
        recommendation = new Recommendation(1, "Bohemian Rhapsody by Queen", "Song", 0);
        recommendation.setRecommendationId(1);
    }

    public void testGetRecommendationId() {
        assertEquals(1, recommendation.getRecommendationId());
    }

    public void testSetRecommendationId() {
        recommendation.setRecommendationId(2);
        assertEquals(2, recommendation.getRecommendationId());
    }

    public void testGetUserId() {
        assertEquals(1, recommendation.getUserId());
    }

    public void testSetUserId() {
        recommendation.setUserId(2);
        assertEquals(2, recommendation.getUserId());
    }

    public void testGetContent() {
        assertEquals("Bohemian Rhapsody by Queen", recommendation.getContent());
    }

    public void testSetContent() {
        recommendation.setContent("Stairway to Heaven by Led Zeppelin");
        assertEquals("Stairway to Heaven by Led Zeppelin", recommendation.getContent());
    }

    public void testGetType() {
        assertEquals("Song", recommendation.getType());
    }

    public void testSetType() {
        // Test valid types
        recommendation.setType("Album");
        assertEquals("Album", recommendation.getType());
        recommendation.setType("Artist");
        assertEquals("Artist", recommendation.getType());

        // Test invalid type
        try {
            recommendation.setType("InvalidType");
            fail("Should throw IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException e) {
            assertEquals("Type must be 'Song', 'Album', or 'Artist'", e.getMessage());
        }
    }

    public void testGetBy() {
        assertEquals(0, recommendation.getBy()); // 0 indicates AI recommendation
    }

    public void testSetBy() {
        recommendation.setBy(5); // Set as recommendation by user with ID 5
        assertEquals(5, recommendation.getBy());
    }

    public void testGetLiked() {
        assertNull(recommendation.getLiked()); // Initially null (not rated)
    }

    public void testSetLiked() {
        recommendation.setLiked(true);
        assertTrue(recommendation.getLiked());

        recommendation.setLiked(false);
        assertFalse(recommendation.getLiked());

        recommendation.setLiked(null);
        assertNull(recommendation.getLiked());
    }

    public void testRate() {
        // Test like
        recommendation.rate(true);
        assertTrue(recommendation.getLiked());

        // Test dislike
        recommendation.rate(false);
        assertFalse(recommendation.getLiked());
    }

    // Additional test cases for edge cases and validation

    public void testConstructorValidation() {
        // Test empty constructor
        Recommendation emptyRec = new Recommendation();
        assertNull(emptyRec.getContent());
        assertNull(emptyRec.getType());
        assertNull(emptyRec.getLiked());

        // Test full constructor
        Recommendation fullRec = new Recommendation(2, "Dark Side of the Moon by Pink Floyd", "Album", 3);
        assertEquals(2, fullRec.getUserId());
        assertEquals("Dark Side of the Moon by Pink Floyd", fullRec.getContent());
        assertEquals("Album", fullRec.getType());
        assertEquals(3, fullRec.getBy());
        assertNull(fullRec.getLiked());
    }

    public void testNullType() {
        try {
            recommendation.setType(null);
            fail("Should throw IllegalArgumentException for null type");
        } catch (IllegalArgumentException e) {
            assertEquals("Type must be 'Song', 'Album', or 'Artist'", e.getMessage());
        }
    }
}
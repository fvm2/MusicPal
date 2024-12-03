package entity;

import junit.framework.TestCase;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class UserTest extends TestCase {
    private User user;

    public void setUp() throws Exception {
        super.setUp();
        // Create a test user with sample data
        user = new User("John", "Doe", "john@example.com", "Canada", "password123");
        user.setId(1);
        user.setThread("thread_123");
        user.setFriends(Arrays.asList(2, 3, 4));
    }

    public void testGetId() {
        assertEquals(1, user.getId());
    }

    public void testSetId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    public void testTestGetName() {
        assertEquals("John", user.getName());
    }

    public void testTestSetName() {
        user.setName("Jane");
        assertEquals("Jane", user.getName());
    }

    public void testGetSurname() {
        assertEquals("Doe", user.getSurname());
    }

    public void testSetSurname() {
        user.setSurname("Smith");
        assertEquals("Smith", user.getSurname());
    }

    public void testGetEmail() {
        assertEquals("john@example.com", user.getEmail());
    }

    public void testSetEmail() {
        user.setEmail("jane@example.com");
        assertEquals("jane@example.com", user.getEmail());
    }

    public void testGetCountry() {
        assertEquals("Canada", user.getCountry());
    }

    public void testSetCountry() {
        user.setCountry("USA");
        assertEquals("USA", user.getCountry());
    }

    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    public void testSetPassword() {
        user.setPassword("newpassword123");
        assertEquals("newpassword123", user.getPassword());
    }

    public void testGetThread() {
        assertEquals("thread_123", user.getThread());
    }

    public void testSetThread() {
        user.setThread("new_thread_456");
        assertEquals("new_thread_456", user.getThread());
    }

    public void testGetFriends() {
        List<Integer> expectedFriends = Arrays.asList(2, 3, 4);
        assertEquals(expectedFriends, user.getFriends());
    }

    public void testSetFriends() {
        List<Integer> newFriends = Arrays.asList(5, 6, 7);
        user.setFriends(newFriends);
        assertEquals(newFriends, user.getFriends());
    }

    // Additional tests for edge cases and special behaviors

    public void testEmptyFriendsList() {
        user.setFriends(new ArrayList<>());
        assertTrue(user.getFriends().isEmpty());
    }

    public void testNullValues() {
        User emptyUser = new User();
        assertNull(emptyUser.getName());
        assertNull(emptyUser.getSurname());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getCountry());
        assertNull(emptyUser.getPassword());
        assertNotNull(emptyUser.getFriends()); // Should return empty list, not null
    }

    public void testFriendsListImmutability() {
        List<Integer> originalFriends = user.getFriends();
        List<Integer> modifiedFriends = user.getFriends();
        modifiedFriends.add(5);

        // Original friends list should remain unchanged
        assertEquals(Arrays.asList(2, 3, 4), originalFriends);
    }

    public void testEqualsAndHashCode() {
        User user1 = new User("John", "Doe", "john@example.com", "USA", "password123");
        User user2 = new User("Jane", "Smith", "john@example.com", "Canada", "password456");
        User user3 = new User("John", "Doe", "jane@example.com", "USA", "password123");

        // Users with same email should be equal
        assertEquals(user1, user2);
        // Users with different emails should not be equal
        assertFalse(user1.equals(user3));
        // Same hash code for same email
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
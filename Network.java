import java.util.ArrayList;
import java.util.List;

/** Represents a social network. The network has users, who follow other users.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;    // The users in this network (an array of User objects)
    private int userCount;   // Actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network with some users. The only purpose of this constructor 
     *  is to allow testing the toString and getUser methods, before 
     *  implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        String nameLower = name.toLowerCase();
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().toLowerCase().equals(nameLower)) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
     *  If the network is full, does nothing and returns false;
     *  If the given name is already a user in this network, does nothing and returns false;
     *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        // Check if the network is full
        if (userCount >= users.length) {
            return false;
        }
        // Check if user already exists
        if (getUser(name) != null) {
            return false;
        }
        // Create new user and add to the array
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        // Retrieve the User objects for both names
        User user1 = getUser(name1);
        User user2 = getUser(name2);

        // If either user doesn't exist, return false
        if (user1 == null || user2 == null) {
            return false;
        }

        // Attempt to have user1 follow user2
        return user1.addFollowee(name2); 
    }

    /** For the user with the given name, recommends another user to follow. 
     *  The recommended user is the user that has the maximal number of mutual followees
     *  with this user. If there's a tie, you can return the first or any one of them.
     *  If the user doesn't exist or there's no possible recommendation, returns null. */
    public String recommendWhoToFollow(String name) {
        // 1) Find the user with the given name.
        User currentUser = getUser(name);
        if (currentUser == null) {
            return null; // If user does not exist
        }

        // Get current user’s followees (list of users that currentUser follows).
        List<User> currentFollowees = currentUser.getfCount();

        int bestIntersectionCount = -1;
        User bestCandidate = null;

        // 2) Check every other user in the network as a potential recommendation.
        for (int i = 0; i < userCount; i++) {
            User other = users[i];
            // Skip the same user
            if (other == currentUser) {
                continue;
            }
            // Skip if current user already follows 'other'
            if (currentFollowees.contains(other)) {
                continue;
            }

            // 3) Calculate mutual followees between currentUser and 'other'
            int intersectionCount = 0;
            for (User f : currentFollowees) {
                if (other.getfCount().contains(f)) {
                    intersectionCount++;
                }
            }

            // 4) Track the user with the largest number of mutual followees
            if (intersectionCount > bestIntersectionCount) {
                bestIntersectionCount = intersectionCount;
                bestCandidate = other;
            }
        }

        // Return the best candidate's name, or null if none found
        return (bestCandidate == null) ? null : bestCandidate.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all users. */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }

        String mostPopular = null;
        int maxCount = -1;

        // For each user, count how many times they're followed
        for (int i = 0; i < userCount; i++) {
            String candidateName = users[i].getName();
            int count = followeeCount(candidateName);
            if (count > maxCount) {
                maxCount = count;
                mostPopular = candidateName;
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears 
     *  in the follows lists of all the users in this network. 
     *  Note: A name can appear 0 or 1 times in each list (no duplicates in a user's list). */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            // Check if users[i] follows the user whose name is `name`
            for (User followee : users[i].getFollowees()) {
                if (followee.getName().equals(name)) {
                    count++;
                    // Each user can follow someone at most once, so break after counting
                    break;
                }
            }
        }
        return count;
    }

    /** Returns a textual description of all the users in this network, and who they follow. */
    public String toString() {
        if (userCount == 0) {
            return "Network:";
        }

        StringBuilder sb = new StringBuilder("Network:\n");
        for (int i = 0; i < userCount; i++) {
            sb.append(users[i].toString()).append("\n");
        }
        return sb.toString().trim();
    }
}

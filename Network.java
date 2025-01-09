/**
 * Represents a social network. The network has users, who follow other users.
 * Each user is an instance of the User class.
 */
public class Network {

    private User[] users;    
    private int userCount;   

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users. 
     * The only purpose of this constructor is to allow testing 
     * the toString and getUser methods, before implementing other methods.
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Returns how many users are currently in this network. */
    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Finds in this network, and returns, the user that has the given name.
     * If there is no such user, returns null.
     * Notice that the method receives a String, and returns a User object.
     */
    public User getUser(String name) {
        if (name == null) {
            return null;
        }
        String nameLower = name.toLowerCase();
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().toLowerCase().equals(nameLower)) {
                return users[i];
            }
        }
        return null;
    }

    /**
     * Adds a new user with the given name to this network.
     * If the network is full, does nothing and returns false;
     * If the given name is already a user in this network, does nothing and returns false;
     * Otherwise, creates a new user with the given name, adds the user to this network, and returns true.
     */
    public boolean addUser(String name) {
        if (userCount >= users.length) {
            return false;
        }
        if (getUser(name) != null) {
            return false;
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /**
     * Makes the user with name1 follow the user with name2. If successful, returns true.
     * If any of the two names is not a user in this network,
     * or if the "addFollowee" call fails (already follows or list is full), returns false.
     */
    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);

        if (user1 == null || user2 == null) {
            return false;
        }

        if (user1 == user2) {
            return false;
        }

        return user1.addFollowee(name2); 
    }

    /**
     * For the user with the given name, recommends another user to follow. 
     * The recommended user is the one that has the maximal number of mutual followees
     * with this user. If there's a tie, you can return the first or any one of them.
     * If the user doesn't exist or there's no possible recommendation, returns null.
     */
    public String recommendWhoToFollow(String name) {
        User currentUser = getUser(name);
        if (currentUser == null) {
            return null; // user not found
        }

        String[] currentFollows = currentUser.getfFollows();
        int currentFCount = currentUser.getfCount();

        int bestIntersectionCount = -1;
        User bestCandidate = null;

        for (int i = 0; i < userCount; i++) {
            User other = users[i];

            if (other == currentUser) {
                continue;
            }
            if (currentUser.follows(other.getName())) {
                continue;
            }

            int intersectionCount = 0;

            String[] otherFollows = other.getfFollows();
            int otherFCount = other.getfCount();

            for (int j = 0; j < currentFCount; j++) {
                String currFolloweeName = currentFollows[j];
                if (currFolloweeName == null) {
                    continue; 
                }
                for (int k = 0; k < otherFCount; k++) {
                    if (otherFollows[k] == null) {
                        continue;
                    }
                    if (otherFollows[k].equalsIgnoreCase(currFolloweeName)) {
                        intersectionCount++;
                        break; 
                    }
                }
            }

            if (intersectionCount > bestIntersectionCount) {
                bestIntersectionCount = intersectionCount;
                bestCandidate = other;
            }
        }

        return (bestCandidate == null) ? null : bestCandidate.getName();
    }

    /**
     * Computes and returns the name of the most popular user in this network:
     * The user who appears the most in the follow lists of all users.
     * If there are no users, returns null.
     */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }

        String mostPopular = null;
        int maxCount = -1;

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

    /**
     * Returns the number of times that the given name appears in
     * the follow lists of all the users in this network.
     * Note: Each user can follow someone at most once, so in each user's array
     * we either see this name 0 or 1 times.
     */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            String[] arr = users[i].getfFollows();
            int fCount = users[i].getfCount();

            for (int j = 0; j < fCount; j++) {
                if (arr[j] != null && arr[j].equalsIgnoreCase(name)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Returns a textual description of all the users in this network, 
     * and who they follow.
     */
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

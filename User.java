/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
 public class User {

    static int maxfCount = 10;

    private String name;       
    private String[] follows;  
    private int fCount;        

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; 
        fCount = 0;                      
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     *  to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        String name1 = name.toLowerCase();
        for (int i = 0; i < fCount; i++){
            if (follows[i].toLowerCase().equals(name1)){
                return true;
            }
        }
        return false;
    }
    /** Makes this user follow the given name. If successful, returns true. 
     *  If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {
        String nameLower = name.toLowerCase();
    
        for (int i = 0; i < fCount; i++) {
            if (follows[i].toLowerCase().equals(nameLower)) {
                return false;
            }
        }
    
        if (fCount >= follows.length) {
            return false;
        }
    
        follows[fCount] = name;
        fCount++;
        return true;
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     *  If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {
        if (name == null){
            return false;
        }
        String name2 = name.toLowerCase();
        for (int i = 0; i < fCount; i++) {
            if (follows[i].toLowerCase().equals(name2)) {
                for (int j = i; j < fCount - 1; j++) {
                    follows[j] = follows[j + 1];
                }
                  
            follows[fCount - 1] = null;
            
            fCount--;
            return true;
        }
    }
    
    return false;
}

    /** Counts the number of users that both this user and the other user follow.
    /*  Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
        int counter = 0;

        for (int i = 0; i < this.fCount; i++){
            if (other.follows(this.follows[i])){
                counter++;
            }
        }
        return counter;
    }

    /** Checks is this user is a friend of the other user.
     *  (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {
        return this.follows(other.getName()) && other.follows(this.getName());
    }
    

    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}

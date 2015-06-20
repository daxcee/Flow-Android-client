package Utils;

public final class Constants {

    private Constants(){
        //prevent object instantiation
        throw new AssertionError();
    }

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "flow";

    public static final String EventEntityName = "events";

    public enum EventEntityAttributes {
        id ("id"),
        date ("date"),
        title("title"),
        venue("venue"),
        city("city"),
        country("country");

        private final String name;

        private EventEntityAttributes(String s) {
            name = s;
        }

        public boolean equalsName(String otherName){
            return (otherName != null) && name.equals(otherName);
        }

        public String toString(){
            return name;
        }

    }
}

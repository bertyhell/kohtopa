package data;

public class ProgramSettings {
    private static String username;
    private static String password;

    public static String getUsername() {
	return username;
    }

    public static void setUsername(String username) {
	ProgramSettings.username = username;
    }
    
    public static String getPassword() {
	return password;
    }

    public static void setPassword(String password) {
	ProgramSettings.password = password;
    }
}

package session.handler;

public class HostEntry {
    private int id;
    private String ip;
    private String username;
    private String password;
    private String port;

    public HostEntry(int id, String ip, String username, String password) {
        this.id = id;
        this.ip = ip;
        this.username = username;
        this.password = password;
       
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    

    @Override
    public String toString() {
        return id + " : " + ip + " (" + username + ") " + " (" + password +")" ;
    }
}

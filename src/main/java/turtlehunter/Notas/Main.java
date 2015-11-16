package turtlehunter.Notas;

import org.json.simple.JSONObject;
import org.mapdb.DBMaker;
import org.mapdb.Store;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.SocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.UUID;

public class Main implements Container {
    private int count = 0;

    public static void main(String[] args) {
        Singletons.main = new Main();

    }

    public Main() {
        try {

            SocketAddress address = new InetSocketAddress(8080);
            Container container = this;
            SocketProcessor server = new ContainerSocketProcessor(container);
            Connection connection = new SocketConnection(server);
            connection.connect(address);

            Singletons.db = DBMaker
                    .appendFileDB(new File("database"))
                    .encryptionEnable("potatotemp")
                    .closeOnJvmShutdown()
                    .fileMmapEnableIfSupported() // only enable on supported platforms
                    .fileMmapCleanerHackEnable() // closes file on DB.close()
                    .make();
            Store.forDB(Singletons.db).fileLoad();
            Singletons.database = Singletons.db.treeMap("database");
            Singletons.userDB = Singletons.db.treeMap("users");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handle(Request request, Response response) {
        try {
            debug(request);
            long time = System.currentTimeMillis();

            response.setValue("Content-Type", "application/json");
            response.setValue("Server", "TurtleServer/1.0 (Simple 6.0.1)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);
            response = parse(request, response);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Response parse(Request request, Response response) {
        try {
            PrintStream body = response.getPrintStream();
            String file = request.getAddress().toString().substring(1);
            Query query = request.getQuery();
            String[] resource = file.split("/");
            HashMap<String, String> output = new HashMap<String, String>();
            switch (resource[0]) {
                case "status":
                    output.put("version", "1.0");
                    output.put("online", "true");
                    output.put("error", "false");
                case "login":
                    String username = query.get("username");
                    String password = query.get("username");
                    Usuario usuario = (Usuario) Singletons.database.getOrDefault(username, null);
                    if(checkHash(usuario.getPassword(), password)) {
                        Usuario user = usuario.clone();
                        user.setToken(UUID.randomUUID().toString());
                        Singletons.database.put(username, user);
                        Singletons.userDB.put(username, user.getToken());
                        output.put("error", "false");
                        output.put("token", user.getToken());
                    } else {
                        output.put("error", "true");
                        output.put("message", "Invalid username or password");
                    }
                case "default":
                    response.setCode(404);
                    output.put("error", "true");
                    output.put("message", "Not found");
            }
            body.println(JSONObject.toJSONString(output));
            Singletons.db.commit();
            count++;
            if(count>10000) {
                Singletons.db.compact();
                count = 0;
            }
            //String value = query.get(key);
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private boolean checkHash(String password, String password1) {
        return BCrypt.checkpw(password1, password);
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void debug(Request request) {
        try {
            System.out.println("Request received: "+request.getClientAddress()+"    Content:"+request.getContent()+"    Parameters: "+request.getAttributes()+"     Resource:"+request.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

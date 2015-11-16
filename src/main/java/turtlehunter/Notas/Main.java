package turtlehunter.Notas;

import org.mapdb.DBMaker;
import org.mapdb.Store;
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

public class Main implements Container {
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
            Singletons.database = Singletons.db.treeSet("database");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handle(Request request, Response response) {
        try {
            debug(request);
            PrintStream body = response.getPrintStream();
            long time = System.currentTimeMillis();

            response.setValue("Content-Type", "text/plain");
            response.setValue("Server", "TurtleServer/1.0 (Simple 6.0.1)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);

            body.println("Hello World");
            body.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void debug(Request request) {
        try {
            System.out.println("Request received: "+request.getClientAddress()+"    Content:"+request.getContent()+"    Parameters: "+request.getAttributes()+"     Resource:"+request.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

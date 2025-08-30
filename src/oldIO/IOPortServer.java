package oldIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IOPortServer extends IOPortP {
    private BufferedReader listener;
    private BufferedWriter writer;
    private ServerSocket serverSocket;

    public IOPortServer(String host, int address) {
        super(host, address);


        try {
            serverSocket = new ServerSocket(address);
            System.out.println("prot: " + address);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("address " + clientSocket.getInetAddress());

                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void handleClient(Socket socket) {
        //TODO: Spawn a new thread per client
        try{
            listener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;
            while ((line = listener.readLine()) != null) {
                System.out.println("input:" + line);
//                String response = handle(line);
//                output.write(response);
//                output.newLine();
//                output.flush();
            }
        } catch (IOException e) {
            System.err.println( e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    @Override
    public void send(Object message) {
        String sMessage =message.toString();
        try {
            writer.write(sMessage);
            writer.newLine();
            //New line? Idk
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object responce() {
        return null;
    }

    @Override
    public void disconnect() {

    }
}

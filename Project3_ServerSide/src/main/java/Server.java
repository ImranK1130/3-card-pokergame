import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public abstract class Server {
	static int count2 = 0;
	static ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;

	Server(int port) {
		server = new TheServer(port);
		server.start();
	}

	protected abstract void acceptCallback(String message);

	public class TheServer extends Thread {
		private int port;

		public TheServer(int port) {
			this.port = port;
		}

		public void run() {
			try (ServerSocket mysocket = new ServerSocket(port);) {
				System.out.println("Server is waiting for a client!");

				while (true) {
					count2++;
					ClientThread c = new ClientThread(mysocket.accept(), count2);
					ServerStatus.updateServerLog("Player has connected to server: " + "Player #" + count2);
					clients.add(c);
					c.start();


				}
			} catch (Exception e) {
				ServerStatus.updateServerLog("Server socket did not launch");
			}
		}
	}

	class ClientThread extends Thread {
		Socket connection;
		int count;
		ObjectInputStream in;
		ObjectOutputStream out;

		ClientThread(Socket s, int count) {
			this.connection = s;
			this.count = count2;
		}

		public void updateClients(String message) {
			for (int i = 0; i < clients.size(); i++) {
				ClientThread t = clients.get(i);
				try {
					t.out.writeObject(message);
				} catch (SocketException e) {
					System.out.println("Player #" + t.count + " has disconnected.");
					ServerStatus.updateServerLog("Player #" + t.count + " has disconnected.");
					clients.remove(t);
					count--;
					ServerStatus.updateServerLog2(count);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void updateSpecificClient(String message, int count) {
			int index = count-1;
			ClientThread t = clients.get(index);
			try {
				t.out.writeObject(message);
			} catch (SocketException e) {
				System.out.println("Player #" + t.count + " has disconnected.");
				ServerStatus.updateServerLog("Player #" + t.count + " has disconnected.");
				clients.remove(t);
				count--;
				ServerStatus.updateServerLog2(count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void updateSpecificClientWinnings(int data, int count) {
			int index = count-1;
			ClientThread t = clients.get(index);
			try {
				t.out.writeObject(data);
			} catch (SocketException e) {
				System.out.println("Player #" + t.count + " has disconnected.");
				ServerStatus.updateServerLog("Player #" + t.count + " has disconnected.");
				clients.remove(t);
				count--;
				ServerStatus.updateServerLog2(count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void run() {
			try {
				out = new ObjectOutputStream(connection.getOutputStream());
				in = new ObjectInputStream(connection.getInputStream());
				connection.setTcpNoDelay(true);
			} catch (Exception e) {
				System.out.println("Streams not open");
			}

			updateClients("new Player on server: Player #" + count);

			while (true) {
				try {
					String data = in.readObject().toString();
					if ("client_disconnect".equals(data)) {
						break;
					}
					ServerStatus.updateServerLog("PLayer: " + count + " sent: " + data); //might uncomment

					//-------------------------------------------------------------------------------------------------
					if(data.equals("Deal_Button_Clicked")) {
						int[] array = (int[]) in.readObject();
//						for(int i = 0; i < array.length; i++) {
//							System.out.print(array[i] + " ");
//						}
//						System.out.println(); // print a newline character to separate from the next output

						PokerInfo.transfer_wager(array);


					}

					if(data.equals("Sending_All_For_One")){

						int[] temp = (int[]) in.readObject();
						System.out.println("HI");

						for (int i =0 ; i<9 ;i++){
							System.out.println(temp[i]);
						}
						PokerInfo.transferAllforone(temp);
					}

					if(data.equals("Update_One_for_All")){
						int[] temp2 = (int[]) in.readObject();
						System.out.println("HI22");

						for (int i =0 ; i<9 ;i++){
							System.out.println(temp2[i]);
						}
						PokerInfo.transferAllforone(temp2);


					}

					if(data.equals("Sending_player_cards")){
						int[] temp = (int[]) in.readObject();
						PokerInfo.transfer_player_cards(temp);
					}

					if(data.equals("Fold_Button_Clicked")){
						int[] temp = (int[]) in.readObject();
						System.out.println("HI");
						PokerInfo.transfer_dealer_cards(temp);
					}

					//--------------------------------------------------------------------------------------------------
					if(data.equals("Folding_in_process")){
//						String display = in.readObject().toString();
						updateClients("Player #:" + count + "has folded! Dealer's cards are revealed.");
//						updateClients("Player #" + count + " just folded");
						PokerInfo.Folded(this,count);


					}

					//-------------------------------------------------------------------------------------------------

					if(data.equals("Continue_Button_clicked")){

						PokerInfo.Continue(this,count);

					}

					//--------------------------------------------------------------------------------------------------
					if(data.equals("Display_on_clients")){
						String display = in.readObject().toString();
						updateClients("Player #" + count + " said: " + display);
					}

					//--------------------------------------------------------------------------------------------------
//					updateClients("Player #" + count + " said: " + data);
				} catch (Exception e) {
					ServerStatus.updateServerLog("PLayer: " + count + "....closing down!");


					updateClients("Player #" + count2 + " has left the server!");
					clients.remove(this);
					count2--;
					break;
				}
			}
		}
	}


}

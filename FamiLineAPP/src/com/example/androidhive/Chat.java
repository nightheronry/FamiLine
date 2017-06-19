package com.example.androidhive;

import io.socket.SocketIO;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

public class Chat extends Thread {
    private SocketIO socket;
    private ChatCallback callback;
    
    public Chat(ChatCallbackAdapter callback) {
        this.callback = new ChatCallback(callback);
    }
    
    @Override
    public void run() {
        try {
			socket = new SocketIO("http://203.64.183.224:3030", callback);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
    public void initalbum(String FamilyId) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("familyid", FamilyId);
            socket.emit("initalbum", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch ( Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.toString());
		} catch ( Error e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.toString());
		}
    }
    public void uploadPhotos(String albumid, String uploadphotos){
    	 
    	 try {
             JSONObject json = new JSONObject();
             json.putOpt("albumid", albumid);
             socket.emit("uploadphoto", json);
         } catch (JSONException ex) {
             ex.printStackTrace();
         }
    }
    public void sendMessage(String message) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("message", message);
            socket.emit("sendchat", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    public void join(String nickname) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("nickname", nickname);
            socket.emit("nickname", json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

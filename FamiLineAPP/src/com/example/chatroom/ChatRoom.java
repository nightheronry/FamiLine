package com.example.chatroom;

import info.androidhive.slidingmenu.ChatRoomFragment;
import io.socket.SocketIO;

import com.example.chatroom.ChatRoomCallback;
import com.example.chatroom.ChatRoomAdapter;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoom extends Thread {
    private SocketIO socket;
    private ChatRoomCallback callback;
    
    public ChatRoom(ChatRoomAdapter callback) {
        this.callback = new ChatRoomCallback(callback);
    }
    
    @Override
    public void run() {
        try {
			socket = new SocketIO("http://203.64.183.224:3000", callback);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    public void login(String account, String password){
    	JSONObject json = new JSONObject();
        try {
			json.putOpt("account", account );
			json.putOpt("password", password );
			socket.emit("login", json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    public void sendMessage(String message) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("message", message);
            json.putOpt("time", ChatRoomFragment.getTime());
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

package com.example.chatroom;

import info.androidhive.slidingmenu.R;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidhive.Constant;
import com.example.chatroom.MessageDB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 
 * @author way
 */
public class ChatActivity extends Activity implements ChatRoomAdapter,OnClickListener  {
	private Button mBtnSend;//send button
	private Button mBtnBack;//back button
	private EditText mEditTextContent;
	private TextView mFriendName;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private ChatRoom chat;
	private MessageDB messageDB;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		messageDB = new MessageDB(this);
		initView();
		initData();
		chat = new ChatRoom(this);
		chat.start();
	}

	
	@SuppressLint("CutPasteId")
	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.chat_send);
		mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	send();
            }
    });
		mBtnBack = (Button) findViewById(R.id.chat_back);
		mBtnBack.setOnClickListener(this);
		mFriendName = (TextView) findViewById(R.id.chat_name);
		mEditTextContent = (EditText) findViewById(R.id.chat_editmessage);
	}

	
	public void initData() {
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mAdapter.getCount() - 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_send:
			this.send();
			break;
		case R.id.chat_back:
			finish();
			break;
		}
	}

	
	public void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity("Me",null, contString, 0, false);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mListView.getCount() - 1);
	        MediaPlayer.create(getApplicationContext(), R.raw.msg).start();
			mEditTextContent.setText("");
			chat.sendMessage(contString);
			
		}
	}


	@Override
	public void callback(JSONArray data) throws JSONException {
		// TODO Auto-generated method stub
		
	}


	@Override
    public void on(String event, JSONObject obj) {
        try {
            if (event.equals("user message")) {
            	Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("user_message", obj.getString("user") + ": " + obj.getString("message") + "\n");
            	bundle.putString("user", obj.getString("user"));
            	bundle.putString("message", obj.getString("message") );
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
            	//chattextView.append(obj.getString("user") + ": " + obj.getString("message") + "\n");
            }

            else if (event.equals("announcement")) {
            	Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("announcement", "123");
            	bundle.putString("user", obj.getString("user") );
            	bundle.putString("action", obj.getString("action"));
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
            	//chattextView.append(obj.getString("user") + " " + obj.getString("action") + "\n");
            	
            }

            else if (event.equals("nicknames")) {
                JSONArray names = obj.names();
                String str = "";
                for (int i=0; i < names.length(); i++) {
                    if (i != 0)
                        str += ", ";
                    str += names.getString(i);
                }
                Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("data_nickename", str);
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
                //nameeditText.setText(str);
            }
            else if (event.equals("login_success")) {
            	String username = obj.getString("name");
            	
            	Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("username", username);
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
            	
            }
            else if (event.equals("data_connect")) {
                Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("data_connect", "login_failed");
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
            }
            else if (event.equals("login_failed")) {
                Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("login_failed", "login_failed");
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
                //nameeditText.setText(str);
            }
            else if (event.equals("connect_failed")) {
                Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("connect_failed", "connect_failed");
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
                //nameeditText.setText(str);
            }else if (event.equals("connect_failed")) {
                Message msg = new Message();
            	Bundle bundle = new Bundle();
            	bundle.putString("connect_failed", "connect_failed");
            	msg.setData(bundle);
            	uiMessageHandler.sendMessage(msg);
                //nameeditText.setText(str);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMessage(JSONObject json) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		Message msg = new Message();
    	Bundle bundle = new Bundle();
    	bundle.putString("data_connect", "done!\n");
    	msg.setData(bundle);
    	uiMessageHandler.sendMessage(msg);
	}


	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConnectFailure() {
		// TODO Auto-generated method stub
		
	}
	@SuppressLint("HandlerLeak")
	Handler uiMessageHandler = new Handler(){
		
			@Override
			public void handleMessage(Message msg){
				
				String data = msg.getData().getString("user_message");
				if ( data != null ) {
					ChatMsgEntity entity = new ChatMsgEntity(msg.getData().getString("user"),null, msg.getData().getString("message"), 0, true);
					mDataArrays.add(entity);
					mAdapter.notifyDataSetChanged();
					mListView.setSelection(mListView.getCount() - 1);
			        MediaPlayer.create(getApplicationContext(), R.raw.msg).start();
					
				}
				else {
					   if(msg.getData().getString("data_nickename") != null ){
					      data = msg.getData().getString("data_nickename");
					      mFriendName.setText(data);
				         }
					     else {
					    	 if(msg.getData().getString("data_connect") != null){
					    		data = msg.getData().getString("data_connect");
					    		
					    		ChatMsgEntity entity = new ChatMsgEntity(Constant.username,
				        				   null, msg.getData().getString("data_connect"), 1, true);
								entity.setMessage(data);
								entity.setMsgType(false);
								mDataArrays.add(entity);
								mAdapter.notifyDataSetChanged();
							    MediaPlayer.create(getApplicationContext(), R.raw.msg).start();
							    chat.join(Constant.username);

					    		 
					     }else{
					    	   if(msg.getData().getString("data_disconnect") != null)
					    		   Toast.makeText(ChatActivity.this, "Connection lost", Toast.LENGTH_LONG).show();
					    	       
					           else{
					        	   if(msg.getData().getString("data_connectfail") != null)
					        		   Toast.makeText(ChatActivity.this, "error!", Toast.LENGTH_LONG).show();
					        	   else{
						        	   if(msg.getData().getString("announcement") != null)
						        	   {
						        		   ChatMsgEntity entity = new ChatMsgEntity(msg.getData().getString("user"),
						        				   null, msg.getData().getString("action"), 2, true);
										   mDataArrays.add(entity);
										   mAdapter.notifyDataSetChanged();
										   mListView.setSelection(mListView.getCount() - 1);
									       MediaPlayer.create(getApplicationContext(), R.raw.msg).start();
						        	   }
						        		   
					               }
					           }
				}
					   }
	            //super.handleMessage(msg);
	        }
		  }
	    };
}
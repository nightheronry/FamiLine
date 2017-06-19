package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.chatroom.*;
import com.example.androidhive.Constant;
import com.example.chatroom.MessageDB;
import com.example.chatroom.ChatMsgEntity;
import com.example.chatroom.ChatMsgViewAdapter;



import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 
 * @author way
 */
@SuppressLint("ShowToast")
public class ChatRoomFragment extends Fragment implements ChatRoomAdapter,OnClickListener  {
	private Button mBtnSend;//send button
	private Button mBtnBack;//back button
	private EditText mEditTextContent;
	private TextView mFriendName;
	private ListView mListView;
	private MessageDB messageDB;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private ChatRoom chat;
	private int i_theme=info.androidhive.slidingmenu.MainActivity.chatroomtheme;
	private LinearLayout chatLayout;
	private int[] resIds = new int[] { R.drawable.chatbg, R.drawable.bg_default,
			R.drawable.registerbg, R.drawable.bg_main, R.drawable.bg_photo};
	public ChatRoomFragment(){}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	messageDB = new MessageDB(getActivity());
    	
    	chat = new ChatRoom(this);
		chat.start();
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		initData();
		View rootView = inflater.inflate(R.layout.chat, container, false);
		chatLayout = (LinearLayout) rootView.findViewById(R.id.LinearLayout1);
		chatLayout.setBackgroundResource(resIds[i_theme]);
		mListView = (ListView) rootView.findViewById(R.id.listview);
		mBtnSend = (Button) rootView.findViewById(R.id.chat_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) rootView.findViewById(R.id.chat_back);
		mBtnBack.setOnClickListener(this);
		mFriendName = (TextView) rootView.findViewById(R.id.chat_name);
		mFriendName.setText(Constant.username);
		mEditTextContent = (EditText) rootView.findViewById(R.id.chat_editmessage);
		mAdapter = new ChatMsgViewAdapter(getActivity(), mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mAdapter.getCount() - 1);
		 return rootView;
	}
	
	
	public void initData() {
		List<ChatMsgEntity> list = messageDB.getMsg(224);
		if (list.size() > 0) {
			for (ChatMsgEntity entity : list) {
				if (entity.getName().equals("")) {
					entity.setName("Mike");
				}
				if (entity.getImg() < 0) {
					entity.setImg(0);
				}
				mDataArrays.add(entity);
			}
			Collections.reverse(mDataArrays);
		}		
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_send:
			send();
			break;
		case R.id.chat_back:
			Intent i = new Intent(getActivity(), com.sky.gallery.MainActivity.class);
			startActivity(i);
			break;
		}
	}
	@Override
	  public void onResume() {
		chatLayout.setBackgroundResource(
    			resIds[info.androidhive.slidingmenu.MainActivity.chatroomtheme]);
	     super.onResume();
	  }
	@Override
	public void onDestroy() {
		super.onDestroy();
		messageDB.close();
	}
	@SuppressLint("NewApi")
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Fragment onResume
        	chatLayout.setBackgroundResource(
        			resIds[info.androidhive.slidingmenu.MainActivity.chatroomtheme]);
        } else {
            //Fragment onPause
        	
        }
    }
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			chat.sendMessage(contString);
			mEditTextContent.setText("");
			ChatMsgEntity entity = new ChatMsgEntity("ME",getTime(), contString, 0, false);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mListView.getCount() - 1);
	        MediaPlayer.create(getActivity(), R.raw.msg).start();
	        messageDB.saveMsg(224, entity);
			
	        
		}else{
			Toast.makeText(getActivity(), "Typ anything", 0).show();}
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
            	bundle.putString("user message", obj.getString("user") + ": " + obj.getString("message") + "\n");
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
            	//chattextView.append(obj.getString("user") + " " + obj.getString("action") + "\n");
            	
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
		Message msg = new Message();
    	Bundle bundle = new Bundle();
    	bundle.putString("data_disconnect", "Connection lost");
    	msg.setData(bundle);
    	uiMessageHandler.sendMessage(msg);
    	//ShowAlertDialog("Error", "Connection lost");
		
	}


	@Override
	public void onConnectFailure() {
		// TODO Auto-generated method stub
		Message msg = new Message();
    	Bundle bundle = new Bundle();
    	bundle.putString("data_connectfail", "error!\n");
    	msg.setData(bundle);
    	uiMessageHandler.sendMessage(msg);
		
	}

    public static String getTime(){
    	Calendar c = Calendar.getInstance(); 

    	 String  year = Integer.toString(c.get(Calendar.YEAR));
    	 String  month = Integer.toString(c.get(Calendar.MONTH));
    	 String  day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
    	 String  hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
    	 String  minute = Integer.toString(c.get(Calendar.MINUTE));
    	 String  second   = Integer.toString(c.get(Calendar.SECOND));
    	 int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
         String weekDay=null;
    	    if (Calendar.MONDAY == dayOfWeek) {
    	        weekDay = "Mon";
    	    } else if (Calendar.TUESDAY == dayOfWeek) {
    	        weekDay = "Tue";
    	    } else if (Calendar.WEDNESDAY == dayOfWeek) {
    	        weekDay = "Wed";
    	    } else if (Calendar.THURSDAY == dayOfWeek) {
    	        weekDay = "Thu";
    	    } else if (Calendar.FRIDAY == dayOfWeek) {
    	        weekDay = "Fri";
    	    } else if (Calendar.SATURDAY == dayOfWeek) {
    	        weekDay = "Sat";
    	    } else if (Calendar.SUNDAY == dayOfWeek) {
    	        weekDay = "Sun";
    	    }
    	 return year+"/"+month+"/"+day+"  "+hour+":"+minute+"("+weekDay+")";
    	 
    }
	
	@SuppressLint("HandlerLeak")
	Handler uiMessageHandler = new Handler(){
		
			@Override
			public void handleMessage(Message msg){
				
				String data = msg.getData().getString("user message");
				if ( data != null ) {
					ChatMsgEntity entity = new ChatMsgEntity(msg.getData().getString("user"),getTime(), msg.getData().getString("message"), 1, true);
					messageDB.saveMsg(224, entity);
					mDataArrays.add(entity);
					mAdapter.notifyDataSetChanged();
					mListView.setSelection(mListView.getCount() - 1);
			        MediaPlayer.create(getActivity(), R.raw.msg).start();
					
				}
				else {
					   if(msg.getData().getString("data_nickename") != null ){
					      data = msg.getData().getString("data_nickename");
					      mFriendName.setText(data);
				         }
					     else {
					    	 if(msg.getData().getString("data_connect") != null){
					    		 data = msg.getData().getString("data_connect");
					    		 ChatMsgEntity entity = new ChatMsgEntity();
									entity.setMessage(data);
									entity.setDate(getTime());
									entity.setMsgType(true);
									entity.setImg(2);
									mDataArrays.add(entity);
									mAdapter.notifyDataSetChanged();
									mListView.setSelection(mListView.getCount() - 1);
							        MediaPlayer.create(getActivity(), R.raw.msg).start();
					    		 
					     }else{
					    	   if(msg.getData().getString("data_disconnect") != null)
					    		   Toast.makeText(getActivity(), "Connection lost", Toast.LENGTH_LONG);
					    	       
					           else{
					        	   if(msg.getData().getString("data_connectfail") != null)
					        		   Toast.makeText(getActivity(), "error!", Toast.LENGTH_LONG);
					        	   else{
						        	   if(msg.getData().getString("announcement") != null)
						        	   {
						        		   ChatMsgEntity entity = new ChatMsgEntity(msg.getData().getString("user"),getTime(), msg.getData().getString("action"), 1, true);
										   mDataArrays.add(entity);
										   mAdapter.notifyDataSetChanged();
										   mListView.setSelection(mListView.getCount() - 1);
									       MediaPlayer.create(getActivity(), R.raw.msg).start();
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
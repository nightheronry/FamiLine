package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.example.androidhive.Chat;
import com.example.androidhive.ChatCallbackAdapter;
import com.example.androidhive.FullImageActivity;
import com.example.androidhive.GrideAdapter;
import com.example.androidhive.NetWorking;
import com.example.androidhive.TouchModeActivity;

import android.R.integer;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidhive.Constant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.Toast;


public class GridFragment extends Fragment implements ChatCallbackAdapter {
	public Chat chat;
	NetWorking networking;
	public static DisplayMetrics dm;
	GridView gridView ;
	public static int imageCol = 3;
	Bundle bundle;
	GrideAdapter imageAdapter;
	GridFragment myfragment;
    Animation animation;
    int counter=0;
		public GridFragment(){}
		
		@Override
		public void onCreate(Bundle saveInsatnceSate){
			
		 super.onCreate(saveInsatnceSate);
		 
		  
			if(saveInsatnceSate==null) 
			 {chat = new Chat(this);
			  chat.start();
			 }
			else
			{
				try {

			        super.onConfigurationChanged(this.getResources().getConfiguration());
			        // if LANDSCAPE col=3 and if PORTRAIT col=4
			        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			            imageCol = 5;
			        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			            imageCol = 3;
			        }
			        gridView.setNumColumns(imageCol);
			        gridView.setAdapter(new GrideAdapter(getActivity(),MainActivity.list));
			        gridView.startLayoutAnimation();
			        //ia.notifyDataSetChanged();
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
				
				
			}
			
		}
		
		@Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("curChoice", 1);
	        //counter=outState.getInt("curChoice");
	        	
	        
	    }
		
		
		/*@Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	    if (savedInstanceState != null) {
	            // Restore last state for checked position.
	            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
	        }
	    }*/
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.grid_layout, container, false);
	        dm = new DisplayMetrics();
	        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
	        configuration();
	        imageAdapter = new GrideAdapter(getActivity(), MainActivity.list);
	        gridView = (GridView) rootView.findViewById(R.id.grid_view);
	        gridView.setNumColumns(imageCol);
	    	gridView.setAdapter(imageAdapter);
	    	gridView.setOnItemClickListener(new OnItemClickListener() {
	    		@Override
	    		public void onItemClick(AdapterView<?> parent, View v,
	    				int position, long id) {
	    			
	    			// Sending image id to FullScreenActivity
	    			Intent i = new Intent(getActivity(), FullImageActivity.class);
	    			// passing array index
	    			i.putExtra("id", position);
	    			startActivity(i);
	    		}
	    	});
	    	
	    	/*chat = new Chat(this);
			chat.start();*/
	    	
	        return rootView;
	    }
		
		@Override  
		public void onPause() {  
		      super.onPause();  
		      Log.d("Fragment 1", "onPause"); 
		      
		       
		    } 
		
	    
		public void configuration(){
			try {
				 
		        
		        // if LANDSCAPE col=3 and if PORTRAIT col=4
		        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		            imageCol = 5;
		        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
		            imageCol = 3;
		        }
		        
		        
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		}
		
		
		
		public void onConfigurationChanged(Configuration newConfig) {
		    try {

		        super.onConfigurationChanged(newConfig);
		        // if LANDSCAPE col=3 and if PORTRAIT col=4
		        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		            imageCol = 5;
		        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
		            imageCol = 3;
		        }
		        gridView.setNumColumns(imageCol);
		        gridView.setAdapter(new GrideAdapter(getActivity(),MainActivity.list));
		        gridView.startLayoutAnimation();
		        //ia.notifyDataSetChanged();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		}
		
		
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			
			getActivity().getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		

		@SuppressLint("HandlerLeak")
		Handler uiMessageHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				String data = msg.getData().getString("data");
				if (data != null) {
					/*
					String[] newalbumid_and_name = msg.getData().getString("data").split("@");
					String newalbum_name = newalbumid_and_name [0];
					String newalbumid = newalbumid_and_name [1];*/
					
					for(int i=0 ;i<MainActivity.list.size() ;i++){
						Set set =MainActivity.list.get(i).keySet();
				        Iterator it = set.iterator();
				        String key = (String) it.next();
				        String tempkey[]=null;
				        tempkey = key.split("@");
				        if( tempkey[0] == data){
				        	MainActivity.list.remove(i);
				        	if(MainActivity.list.get(i).get(tempkey)==null)
				        	System.out.println(MainActivity.list.get(i).get(tempkey));
				            break;
				        }
				      }imageAdapter.notifyDataSetChanged();
				       
	              
				} else {
					if (msg.getData().getString("albumshow") != null) {
						System.out.println("do albumshow");
						//String coverimage = msg.getData().getString("coverimage");
						String[] newalbumid_and_name = msg.getData().getString("newalbumname").split("@");
						String newalbum_name = newalbumid_and_name [0];
						String newalbumid = newalbumid_and_name [1];
						String ablbumKeyString=newalbumid+"@"+newalbum_name+"@"+"defaultAlbum.png";
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.album);
						HashMap<String, Bitmap> map = new HashMap<String,Bitmap>();
			        	map.put(ablbumKeyString, bitmap);
			        	MainActivity.list.add(map);
			        	imageAdapter.notifyDataSetChanged();
						Toast.makeText(getActivity().getBaseContext(), 
			                    " new images added" , 
			                    Toast.LENGTH_LONG).show();
						/*
						if(!(newalbumid_and_name.length==0))
							// Download files and write into SDcard
						    {networking = new NetWorking(imageAdapter, newalbum);
							
							}*/
				                    
					} else {
						if (msg.getData().getString("data_connect") != null) {
							data = msg.getData().getString("data_connect");

							/*
							 * new AlertDialog.Builder(MainActivity.this).setTitle(
							 * "TYPE NIKNAME").setView(layout)
							 * .setPositiveButton("OK", new
							 * DialogInterface.OnClickListener() { public void
							 * onClick(DialogInterface dialog, int id) { nickname =
							 * nicknameText.getText().toString();
							 * 
							 * } }).show();
							 */

						} else if (msg.getData().getString("data_disconnect") != null) {
							MainActivity.list=null;
							ShowAlertDialog("Error", "Connection lost");
						} else if (msg.getData().getString("data_connectfail") != null) {

						} else if (msg.getData().getString("initalbum_success") != null) {
	                         System.out.println("do initalbum_success");
	                         
							// use¡u@¡vto split the "data"
							String[] albumids    = msg.getData().getString("albumid").split("@");
							String[] albumnames  = msg.getData().getString("albumname").split("@");
							String[] photosnames = msg.getData().getString("cover").split("@");
							String   total       = msg.getData().getString("albumtotal");
							System.out.println(albumnames[0]);
							if(!(albumids.length==0))
							// Download files and write into SDcard
						    {networking = new NetWorking(imageAdapter, albumids, albumnames, photosnames);
							
							Toast.makeText(getActivity().getBaseContext(), 
				                    "Total " + Integer.parseInt(total) + " images downloaded" , 
				                    Toast.LENGTH_LONG).show();
							/*for (int i = 0; i < photosnames.length; ++i) {
								AlbumManager.insertAlbum(albumids[i], albumnames[i], photosnames[i], total);
								Constant.albumtotal = Integer.parseInt(total);
								
								System.out.println(Constant.albumtotal+"albumtotal");
								System.out.println(albumids.length+"albumids.length");
							}
							*/
							// 2013/12/13 Download step has completed then will maintain DB W/R
							/*
							// Then read and show them
					        for(int i=0; i<Constant.albumtotal; ++i){
					        	String externalStorageState = Environment.getExternalStorageState();  
					            // Environment.MEDIA_MOUNTED mean the ExternalStorageState can be readed
					             if(externalStorageState.equals(Environment.MEDIA_MOUNTED)){ 
					        	    String path = AlbumManager.getAlbumData(albumnames[i]).albumcover;
					        	    System.out.println(albumnames[i]+"AlbumData");
					        	    Bitmap tempBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+
					        	    		"/test/" + path);
					        	    HashMap<String, Bitmap> map = new HashMap<String,Bitmap>();
						        	map.put(path, tempBitmap);
						        	list.add(map);
					        	    
					         
					             }else{ShowAlertDialog( "ERROR", "Cannot read the SDCard" );}
					        }*/

						    }else{Toast.makeText(getActivity().getBaseContext(), 
				                    "NO Album exited!!" , 
				                    Toast.LENGTH_LONG).show();}	
						}
						// super.handleMessage(msg);
					}
				}
		    }
		};
		
		@Override
		public void on(String event, JSONObject obj) {
			try {
				if (event.equals("albumdelete")) {
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("data", obj.getString("data") );
					msg.setData(bundle);
					// uiMessageHandler.sendMessage(msg);
					// chattextView.append(obj.getString("user") + ": " +
					// obj.getString("message") + "\n");
				}

				else if (event.equals("albumnamemodify")) {
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("albumnamemodify" ,obj.getString("albumname"));
					bundle.putString("fileid" ,obj.getString("fileid"));
					bundle.putString("albumname" ,obj.getString("albumname"));
					msg.setData(bundle);
					// uiMessageHandler.sendMessage(msg);
					// chattextView.append(obj.getString("user") + " " +
					// obj.getString("action") + "\n");

				}

				else if (event.equals("albumshow")) {
					/*
					 * get album cover ,family-id(familyid) and album total
					 * number(albumnum) from server and to identify the album if is
					 * needed by familyid.
					 */
	               /* if(obj.getString("familyid")==Constant.familyid){*/
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("albumshow", "albumshow");
					bundle.putString("coverimage", obj.getString("image"));
					bundle.putString("newalbumname", obj.getString("data"));
					msg.setData(bundle);
					uiMessageHandler.sendMessage(msg);
					
				} else if (event.equals("initalbum_success")) {
	                System.out.println("initalbum_success");
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("initalbum_success", "initalbum_success");
					bundle.putString("albumid", obj.getString("albumid"));
					bundle.putString("albumname", obj.getString("albumname"));
					bundle.putString("cover", obj.getString("cover"));
					bundle.putString("albumtotal", obj.getString("total"));
					msg.setData(bundle);
					uiMessageHandler.sendMessage(msg);
					// nameeditText.setText(str);
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
		
		
		@Override
		public void onMessage(String message) {
		}

		@Override
		public void onMessage(JSONObject json) {
		}

		@Override
		public void onConnect() {
			chat.initalbum(Constant.familyid);
		}

		@Override
		public void onDisconnect() {
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("data_disconnect", "Connection lost");
			msg.setData(bundle);
			// uiMessageHandler.sendMessage(msg);
			// ShowAlertDialog("Error", "Connection lost");
		}

		@Override
		public void onConnectFailure() {
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("data_connectfail", "error!\n");
			msg.setData(bundle);
			// uiMessageHandler.sendMessage(msg);
			// chattextView.append("error!\n");
		}

		private void ShowAlertDialog(String title, String message) {

			Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
			MyAlertDialog.setTitle(title);
			MyAlertDialog.setMessage(message);
			MyAlertDialog.show();
		}

		@Override
		public void callback(JSONArray data) throws JSONException {
			// TODO Auto-generated method stub

		}
		
	
	}

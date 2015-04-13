package com.techblogon.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Main extends Activity
{
	Button btnSignIn,btnSignUp;
	DBAdapter DBAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     // create a instance of SQLite Database
	     DBAdapter =new DBAdapter(this);
	     DBAdapter = DBAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),CreateActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(Activity_Main.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");
	
		    // get the Refferences of views
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					// fetch the Password form database for respective user name
					String storedPassword= DBAdapter.getSinlgeEntry(userName);
					
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						Toast.makeText(Activity_Main.this, "Login OK", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(Activity_Main.this, "Nombre o Password incorrecto", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		DBAdapter.close();
	}
}

package com.manuni.usercalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.manuni.usercalldetails.databinding.ActivityPhoneBinding;

import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {
    ActivityPhoneBinding binding;

    String nameOfNumber = "";
    String number;

    List<CallData> data;

    private CallAdapter adapter;

    private ProgressDialog dialog;



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(PhoneActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        dialog.show();

        data = new ArrayList<>();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
                @SuppressLint("HardwareIds") String phoneNumberOwn = telephonyManager.getLine1Number();
              /*  String simName = telephonyManager.getSimOperatorName();

                if (simName == null || simName.isEmpty()){
                    simName = "Unknown SIM";
                }*/

                binding.phoneNumber.setText(phoneNumberOwn);

              String simNameOfUser =  getSimName(phoneNumberOwn);

                Toast.makeText(this, ""+simNameOfUser, Toast.LENGTH_SHORT).show();

              binding.simName.setText(simNameOfUser);
             /* if (prefix.equals("017")){
                  binding.simName.setText("Grameenphone");
              }else if (prefix.equals("019")){
                  binding.simName.setText("Banglalink");
              }else if (prefix.equals("018")){
                  binding.simName.setText("Robi");
              }else if (prefix.equals("016")){
                  binding.simName.setText("Airtel");
              }else {
                  binding.simName.setText("Unknown");
              }*/






                if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED){

                    String[] projection = {CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.DATE,CallLog.Calls.DURATION};
                    Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,projection,null,null,null);

                    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
                    String[] projectionOf = {ContactsContract.PhoneLookup.DISPLAY_NAME};

                    if (cursor != null){
                        while (cursor.moveToNext()){
                             number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                            @SuppressLint("Range") int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                            @SuppressLint("Range") long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                            @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));



                          String nameO =  getContactNameFromNumber(PhoneActivity.this,number);


/*

                                if (cursorName != null && cursor.moveToFirst()) {
                                    nameOfNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                                }else {
                                    nameOfNumber = "No name";
                                }
*/


                            CallData callData = new CallData(number,type,date,duration,nameO);


                            data.add(callData);




                        }

                        cursor.close();
                      //  cursorName.close();

                        adapter = new CallAdapter(PhoneActivity.this,data);
                        binding.callLogsRV.setAdapter(adapter);
                        binding.callLogsRV.setHasFixedSize(true);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhoneActivity.this);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(PhoneActivity.this,linearLayoutManager.getOrientation());

                        binding.callLogsRV.addItemDecoration(dividerItemDecoration);

                        binding.callLogsRV.setLayoutManager(linearLayoutManager);

                        adapter.notifyDataSetChanged();

                        dialog.dismiss();

                    }


                }else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},11);
                }


            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},10);
            }
        }else {
            @SuppressLint("HardwareIds") String phoneNumberOwn = telephonyManager.getLine1Number();
           // String simName = telephonyManager.getSimOperatorName();

            binding.phoneNumber.setText(phoneNumberOwn);
            String simNameOfUser =  getSimName(phoneNumberOwn);

            Toast.makeText(this, ""+simNameOfUser, Toast.LENGTH_SHORT).show();

            binding.simName.setText(simNameOfUser);
             /* if (prefix.equals("017")){
                  binding.simName.setText("Grameenphone");
              }else if (prefix.equals("019")){
                  binding.simName.setText("Banglalink");
              }else if (prefix.equals("018")){
                  binding.simName.setText("Robi");
              }else if (prefix.equals("016")){
                  binding.simName.setText("Airtel");
              }else {
                  binding.simName.setText("Unknown");
              }*/




            String[] projection = {CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.DATE,CallLog.Calls.DURATION};
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,projection,null,null,null);

            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] projectionOf = {ContactsContract.PhoneLookup.DISPLAY_NAME};

            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
                Cursor cursorName = getContentResolver().query(uri, projectionOf, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                        @SuppressLint("Range") int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                        @SuppressLint("Range") long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                        @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));



                        String nameO =  getContactNameFromNumber(PhoneActivity.this,number);
                       /* if (cursorName != null && cursor.moveToFirst()) {
                            nameOfNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        } else {
                            nameOfNumber = "No name";
                        }
*/

                        CallData callData = new CallData(number, type, date, duration,nameO);


                        data.add(callData);


                    }

                    cursor.close();
                    //cursorName.close();

                    adapter = new CallAdapter(PhoneActivity.this, data);
                    binding.callLogsRV.setAdapter(adapter);
                    binding.callLogsRV.setHasFixedSize(true);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhoneActivity.this);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(PhoneActivity.this, linearLayoutManager.getOrientation());

                    binding.callLogsRV.addItemDecoration(dividerItemDecoration);

                    binding.callLogsRV.setLayoutManager(linearLayoutManager);

                    adapter.notifyDataSetChanged();


                    dialog.dismiss();
                }
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},12);
            }



        }





    }

    @SuppressLint("Range")
    private String getContactNameFromNumber(Context context, String usNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(usNumber));
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}; // Use the correct column name

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)); // Use the correct column index
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "No name"; // Return default value if contact name is not found for the given number.
    }

   /* String phoneNumber = "+8801723456789";

    boolean contains017 = checkIfContains017(phoneNumber);*/
         //   System.out.println("Contains 017 after +88: " + contains017);




  /*  private  String checkIfContains017(String phoneNumber) {
        int indexPlus88 = phoneNumber.indexOf("+88");

        if (indexPlus88 != -1 && phoneNumber.length() > indexPlus88 + 5) {
            String substringAfterPlus88 = phoneNumber.substring(indexPlus88 + 3, indexPlus88 + 6);
            return substringAfterPlus88;
        }


        return phoneNumber;
    }*/

    private String getSimName(String phoneNumber) {
        int indexPlus88 = phoneNumber.indexOf("+88");// ei "+88" portion tuku koto tomo index a ache ta ber kore indexOf() method...ekhane eti ache 0 index a

        if (indexPlus88 != -1 && phoneNumber.length() > indexPlus88 + 5) {
            String prefix = phoneNumber.substring(indexPlus88 + 3, indexPlus88 + 6);//0+3, 0+6

            switch (prefix) {
                case "017":
                    return "Grameenphone";
                case "019":
                    return "Banglalink";
                case "018":
                    return "Robi";
                case "016":
                    return "Airtel";
                default:
                    return "Unknown";
            }
        }

        return "Unknown";
    }

}
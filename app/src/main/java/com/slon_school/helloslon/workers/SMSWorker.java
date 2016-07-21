package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.telephony.SmsManager;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 14.07.2016.
 */
public class SMSWorker extends Worker {

    private ArrayList<Key> keys;
    private enum State {Start, PhoneNumber, Writing, Send}
    private State state;
    private SmsManager smsManager;

    private String phoneNumber;
    private String message;

    public SMSWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<>();
        keys.add(new Key("отправь смс"));
        keys.add(new Key("написать смс"));
        keys.add(new Key("отправить смс"));
        keys.add(new Key("напиши смс"));

        state = State.Start;

        smsManager = SmsManager.getDefault();
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return true;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Key error = new Key("ошибка");
        Key exit = new Key("отмена");

        if ((arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) && (state == State.Start) ){
            return new HelpMan(R.raw.sms_help,activity).getHelp();
        } else if (arguments.get().size() == 0 && state == State.Start) {
              return start(arguments);
        } else if (state == State.PhoneNumber) {
            if (arguments.contains(exit)) {
                return exit();
            } else {
                return writePhoneNumber(arguments);
            }
        } else if (state == State.Writing) {
            if (arguments.contains(error)) {
                return start(arguments);
            } else if (arguments.contains(exit)){
                return exit();
            }
            return writeText(arguments);
        }

        return new Response("Повтори ещё раз, я не понял", true);
    }

    private Response start(Key arguments) {
        state = State.PhoneNumber;
        return new Response("Скажи мне номер телефона, на который ты хочешь отправить смс", true);
    }

    private Response writeText(Key arguments){
        state = State.Start;
        message = arguments.toString();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        return new Response("Отправлен смс на номер " + phoneNumber, false);
    }

    private Response writePhoneNumber(Key arguments){
        phoneNumber = arguments.get().get(0);
        state = State.Writing;
        return new Response("Скажи мне, что ты хочешь отправить",true);
    }

    private Response exit() {
        state = State.Start;
        return new Response("Окей, не будем отправлять смс, что дальше?", false);
    }










   /* private String retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = activity.getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        //Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        return contactNumber;
        //Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

   /* private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

       // Log.d(TAG, "Contact Name: " + contactName);
    }*/





}

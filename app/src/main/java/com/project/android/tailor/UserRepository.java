package com.project.android.tailor;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UserRepository {

    private UserDao mUserDao;
    private Context mContext;

    UserRepository(Context context){
        TailorDatabase db=TailorDatabaseAccessor.getInstance(context);
        mUserDao=db.userDao();
        mContext=context;
    }

    public void insert(User user){
        new insertAsyncTask(mUserDao,mContext).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User,Void,Boolean>{

        private UserDao userDao;
        private Context context;
        private String username;

        insertAsyncTask(UserDao dao, Context context){
            userDao=dao;
            this.context=context;
        }

        protected Boolean doInBackground(final User... users){
            try{
                username=users[0].username;
                userDao.insertUser(users[0]);
                return true;
            }catch(Exception e){
                Log.e("MyApp", Log.getStackTraceString(e));
                return false;
            }
        }

        protected void onPostExecute(Boolean success){

            if(success==true){
                 Toast.makeText(context,username+" added",Toast.LENGTH_LONG).show();
           //Log.d("MyApp","success");
            }else{
                Toast.makeText(context,"Account creation failed",Toast.LENGTH_LONG).show();
               // Log.d("MyApp","failure");
            }
        }
    }
}

package com.example.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

//this class used to connect the data from the backend to the recycler view when bind

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<messageModel> mMessages; // a list for the messages
    private Context mContext;
    private String mUserId;

    //constructor
    public ChatAdapter(Context context, String userId, List<messageModel> messages) {
        mMessages = messages;
        mUserId = userId;
        mContext = context;
    }


    //returns the view for the item_chat, since the root attachment is false
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext(); //get parent information.
        LayoutInflater inflater = LayoutInflater.from(context); //inflate the parent view, recycler view
        View contactView = inflater.inflate(R.layout.item_chat, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        messageModel message = mMessages.get(position);
        final boolean isMe = message.getUserId() != null && message.getUserId().equals(mUserId);

        //depending on if the message is you and where to place the circle image
        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }

        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
        Glide.with(mContext).load(getProfileUrl(message.getUserId())).into(profileView);
        holder.body.setText(message.getBody());
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            Log.e("adapter","Something when wrong here");
            e.printStackTrace();
        }
        return "https://www.gravatar.com/avatar/HASH";
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageOther;
        ImageView imageMe;
        TextView body;

        public ViewHolder(View itemView) {
            super(itemView);
            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);
            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
            body = (TextView)itemView.findViewById(R.id.tvBody);
        }
    }

}

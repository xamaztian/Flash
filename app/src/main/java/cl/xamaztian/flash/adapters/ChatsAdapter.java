package cl.xamaztian.flash.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.xamaztian.flash.R;
import cl.xamaztian.flash.data.CurrentUser;
import cl.xamaztian.flash.data.Nodes;
import cl.xamaztian.flash.models.Chat;

public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ChatHolder> {

    private ChatsListener listener;

    public ChatsAdapter(LifecycleOwner lifecycleOwner, ChatsListener chatsListener) {
        super(new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(new Nodes().userChat(new CurrentUser().getUid()), Chat.class)
                .setLifecycleOwner(lifecycleOwner)
                .build());
        listener = chatsListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatHolder holder, int position, @NonNull Chat model) {
        Picasso.get().load(model.getPhoto()).centerCrop().fit().into(holder.bubbleImageView);
        holder.textView.setText(model.getReceiver());
        if (model.isNotification())
            holder.notificationView.setVisibility(View.VISIBLE);
        else
            holder.notificationView.setVisibility(View.GONE);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat auxChat = getItem(holder.getAdapterPosition());
                listener.clicked(auxChat);
            }
        });

    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ChatHolder(view);
    }

    public class ChatHolder extends RecyclerView.ViewHolder {

        private BubbleImageView bubbleImageView;
        private TextView textView;
        private View notificationView;

        public ChatHolder(View itemView) {
            super(itemView);
            bubbleImageView = itemView.findViewById(R.id.photoBiv);
            textView = itemView.findViewById(R.id.emailTv);
            notificationView = itemView.findViewById(R.id.notificationV);
        }


    }

}

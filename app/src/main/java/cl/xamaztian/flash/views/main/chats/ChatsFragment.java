package cl.xamaztian.flash.views.main.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.xamaztian.flash.R;
import cl.xamaztian.flash.adapters.ChatsAdapter;
import cl.xamaztian.flash.adapters.ChatsListener;
import cl.xamaztian.flash.models.Chat;
import cl.xamaztian.flash.views.chat.ChatActivity;

public class ChatsFragment extends Fragment implements ChatsListener{

    public static final String CHAT ="cl.xamaztian.flash.views.main.chats.KEY.CHAT";

    private RecyclerView recyclerView;
    private ChatsAdapter adapter;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.chatsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        adapter = new ChatsAdapter(getActivity(), this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void clicked(Chat chat) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(CHAT, chat);
        startActivity(intent);
    }
}

package com.tydeya.familycircle.conversationpart.chatpart;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.family.conversation.messages.PersonMessage;
import com.tydeya.familycircle.user.User;

public class CorrespondenceFragment extends Fragment {

    private View root;
    private RecyclerView chatRecyclerView;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private ImageButton sendButton;
    private TextInputEditText inputField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_correspondence, container, false);
        chatRecyclerView = root.findViewById(R.id.chat_recycler_view);
        sendButton = root.findViewById(R.id.chat_send_message_button);
        inputField = root.findViewById(R.id.chat_input_field);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(), User.getInstance().getFamily().getFamilyConversations()
                .get(MessagingActivity.correspondencePosition).getMessages());
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        sendButton.setOnClickListener((view -> {
            if (!inputField.getText().toString().equals("")) {
                PersonMessage actualMessage = new PersonMessage(null, inputField.getText().toString(), User.getInstance().getUserFamilyMember());
                User.getInstance().getFamily().getFamilyConversations().get(MessagingActivity.correspondencePosition).getMessages().add(actualMessage);
                inputField.setText("");
                chatRecyclerView.scrollToPosition(chatRecyclerViewAdapter.getItemCount() - 1);
            }
        }));

        Context context = getContext();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        /*
        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore.collection("/Families").get();
        querySnapshotTask.addOnSuccessListener(queryDocumentSnapshots -> {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        });
        querySnapshotTask.addOnFailureListener(e -> {
            Toast.makeText(context, "Suck", Toast.LENGTH_LONG).show();
        });*/

        DocumentReference documentReference = firebaseFirestore.collection("Families").document("Yakovlev-nn");
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            Toast.makeText(context, documentSnapshot.getString("name"), Toast.LENGTH_LONG).show();
        });
        documentReference.get().addOnFailureListener(e -> {
            Log.d("ASMR", e.toString());
        });
    }
}

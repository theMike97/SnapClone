package floatingheads.snapclone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.Message;

/**
 * Created by QuinnSalas on 3/29/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

      static final class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;

        ChatViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_username);
            message = view.findViewById(R.id.item_message);
        }
    }

    private List<Message> chatList = new ArrayList<>();

    public void clearData() {
        chatList.clear();
    }

    public void addData(Message data) {
        chatList.add(data);
    }

    public void remove(int position) {
        chatList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_display, parent, false);
        return new ChatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Message data = chatList.get(position);
        holder.message.setText(data.getMessage());
        holder.name.setText(data.getUser());
    }
}

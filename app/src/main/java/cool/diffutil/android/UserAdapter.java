package cool.diffutil.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final AsyncListDiffer<User> mDiffer = new AsyncListDiffer(this, DIFF_CALLBACK);

    @Override
    public int getItemCount() {
        int size = mDiffer.getCurrentList().size();
        return size;
    }

    public void submitList(List<User> list) {
        mDiffer.submitList(list);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new UserViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = mDiffer.getCurrentList().get(position);
        holder.bindView(user, position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView mIdView;
        TextView mFirstNameView;
        TextView mLastNameView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mIdView = itemView.findViewById(R.id.tv_id);
            mFirstNameView = itemView.findViewById(R.id.tv_first_name);
            mLastNameView = itemView.findViewById(R.id.tv_last_name);
        }

        public void bindView(User user, int position) {
            mIdView.setText("Id：" + user.getUid());
            mFirstNameView.setText(user.getFirstName());
            mLastNameView.setText(user.getLastName());
        }
    }


    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull User oldUser, @NonNull User newUser) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldUser.getUid() == newUser.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldUser.equals(newUser);
        }
    };
}
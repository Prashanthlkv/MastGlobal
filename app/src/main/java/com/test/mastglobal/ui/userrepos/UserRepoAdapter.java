package com.test.mastglobal.ui.userrepos;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.mastglobal.R;
import com.test.mastglobal.vo.UserRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRepoAdapter extends RecyclerView.Adapter<UserRepoAdapter.RepoViewHolder>{

    private List<UserRepo>mList;

    public UserRepoAdapter(List<UserRepo>list){
        mList = list;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_repoinfo, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * Static inner ViewHolder class
     */
    public static class RepoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView mTxtName;
        @BindView(R.id.tv_desc)
        TextView mTxtDesc;
        @BindView(R.id.tv_issues)
        TextView mTxtIssues;
        @BindView(R.id.tv_forks)
        TextView mTxtForks;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
        /**
         * Method which binds the view.
         * @param userRepo
         */
        public  void bindView(UserRepo userRepo){
            mTxtName.setText(userRepo.getName());
            mTxtDesc.setText(userRepo.getDescription());
            mTxtIssues.setText(mTxtIssues.getContext().getString(R.string.txt_open_issues , userRepo.getOpen_issues()));
            mTxtForks.setText(mTxtForks.getContext().getString(R.string.txt_open_issues , userRepo.getForks()));
        }


    }


}

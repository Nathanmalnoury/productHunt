package fr.ec.producthunt.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import fr.ec.producthunt.R;
import fr.ec.producthunt.data.DataProvider;
import fr.ec.producthunt.data.SyncService;
import fr.ec.producthunt.data.model.Post;

public class PostsFragments extends Fragment {

    private static final int PROGRESS_CHILD = 1;
    private static final int LIST_CHILD = 0;

    private DataProvider dataProvider;
    private PostAdapter postAdapter;
    private ViewAnimator viewAnimator;

    private SyncPostReceiver syncPostReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Callback callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_post_list_fragment, container, false);

        syncPostReceiver = new SyncPostReceiver();

        postAdapter = new PostAdapter();

        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        refreshPosts();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        ListView listView = rootView.findViewById(R.id.list_item);
        listView.setEmptyView(rootView.findViewById(R.id.empty_element));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Post post = (Post) parent.getAdapter().getItem(position);
                callback.onClickPost(post);

            }
        });
        viewAnimator = rootView.findViewById(R.id.main_view_animator);
        listView.setAdapter(postAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataProvider = DataProvider.getInstance(getActivity().getApplication());
        loadPosts();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SyncPostReceiver.ACTION_LOAD_POSTS);
        LocalBroadcastManager.getInstance(this.getContext())
                .registerReceiver(syncPostReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(syncPostReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:
                refreshPosts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SyncPostReceiver extends BroadcastReceiver {
        public static final String ACTION_LOAD_POSTS = "fr.ec.producthunt.data.action.LOAD_POSTS";

        public SyncPostReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ACTION_LOAD_POSTS)) {
                loadPosts();
            }
        }
    }

    private void refreshPosts() {
        SyncService.startSyncPosts(getContext());
    }

    private void loadPosts() {
        FetchPostsAsyncTask fetchPostsAsyncTask = new FetchPostsAsyncTask();
        fetchPostsAsyncTask.execute();
        Toast.makeText(getContext(), "Post Refreshed", Toast.LENGTH_SHORT).show();

    }

    private class FetchPostsAsyncTask extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewAnimator.setDisplayedChild(PROGRESS_CHILD);
        }

        @Override
        protected List<Post> doInBackground(Void... params) {
            List<Post> posts = dataProvider.getPostsFromDatabase();
            return dataProvider.getPostsFromDatabase();
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            if (posts != null && !posts.isEmpty()) {
                postAdapter.showPosts(posts);
            }
            viewAnimator.setDisplayedChild(LIST_CHILD);
        }
    }

    public interface Callback {
        void onClickPost(Post post);
    }
}

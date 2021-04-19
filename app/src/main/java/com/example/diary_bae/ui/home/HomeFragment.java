package com.example.diary_bae.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary_bae.EditEntryActivity;
import com.example.diary_bae.R;
import com.example.diary_bae.adapters.EntriesAdapter;
import com.example.diary_bae.database.DbAccessObject;
import com.example.diary_bae.pojo.EntryDetails;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    DbAccessObject dao;
    ArrayList<EntryDetails> entryDetails;
    EntriesAdapter entriesAdapter;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dao = new DbAccessObject(requireContext());
        dao.openDb();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateRecyclerView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int position = ((EntriesAdapter)recyclerView.getAdapter()).getPosition();
        if(item.getItemId()==R.id.edit_menuItem){
            Intent intent =new Intent(getActivity(), EditEntryActivity.class);
            intent.putExtra("position", position);
            intent.putParcelableArrayListExtra("entryDetails", entryDetails);
            this.startActivity(intent);
        }
        else if(item.getItemId()==R.id.delete_menuItem){
            dao.deleteRow(entryDetails.get(position).getId());
            populateRecyclerView();
            Toast.makeText(getContext(),"Deleted",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                entriesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                entriesAdapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void populateRecyclerView() {
        recyclerView = requireView().findViewById(R.id.rvAllEntries);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        entryDetails = dao.getEntryDetails();
        entriesAdapter = new EntriesAdapter(requireContext(), requireActivity(),entryDetails);
        recyclerView.setAdapter(entriesAdapter);
        registerForContextMenu(recyclerView);
    }
}
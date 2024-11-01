package com.example.cse441_music.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.Adapter.SongAdapter;
import com.example.cse441_music.Controller.SongController;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private List<Song> songList;
    private EditText editTextSearch;
    private Spinner spinnerSearch;
    private SongController songController;

    // Biến hỗ trợ phân trang
    private boolean isLoading = false;
    private int currentPage = 1;
    private final int songsPerPage = 20; // Số bài hát cần tải mỗi lần
    private  String selectedTag = "all tags";

    private TextView check_json;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        spinnerSearch = view.findViewById(R.id.spinnerSearch);



        String[] values = {"all tags", "songs", "artists", "albums"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, values);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(adapterSpinner);


        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        editTextSearch = view.findViewById(R.id.editTextSearch);

        songList = new ArrayList<>();
        adapter = new SongAdapter(getContext(), songList);
        recyclerView.setAdapter(adapter);

        songController = new SongController();


        fetchSongs(getRandomLetter(),selectedTag, currentPage);

        // Lắng nghe sự kiện Enter trên bàn phím để tìm kiếm
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String query = editTextSearch.getText().toString();
                    currentPage = 1; // Reset về trang đầu tiên khi tìm kiếm mới
                    selectedTag = spinnerSearch.getSelectedItem().toString();

                    fetchSongs(query, selectedTag, currentPage);

                    // Ẩn bàn phím
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });

        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTag = spinnerSearch.getSelectedItem().toString();
                currentPage = 1; // Reset lại trang khi có tìm kiếm mới
                fetchSongs(editTextSearch.getText().toString(), selectedTag, currentPage); // Gọi hàm fetchSongs với selectedTag
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có giá trị được chọn
            }
        });

        // Thêm sự kiện OnScrollListener để tải thêm khi cuộn đến cuối danh sách
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    // Cuộn đến cuối danh sách thì tải thêm
                    loadMoreSongs();
                }
            }
        });

        return view;
    }

    private void fetchSongs(String query, String selectTag, int page) {
        isLoading = true; // Đang tải dữ liệu

        new Thread(() -> {
            try {
                List<Song> songs = songController.getSongs(query, selectTag, page, songsPerPage); // Thay đổi để gọi phương thức mới

                requireActivity().runOnUiThread(() -> {
                    if (page == 1) {
                        songList.clear(); // Xóa danh sách nếu là trang đầu tiên
                    }
                    songList.addAll(songs); // Thêm bài hát vào danh sách
                    adapter.notifyDataSetChanged(); // Cập nhật Adapter
                    isLoading = false; // Hoàn thành tải dữ liệu
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Hàm tải thêm bài hát khi cuộn đến cuối danh sách
    private void loadMoreSongs() {
        currentPage++; // Tăng số trang
        fetchSongs(editTextSearch.getText().toString(),selectedTag, currentPage);
    }

    // Lấy một chữ cái ngẫu nhiên để tìm bài hát ban đầu
    private String getRandomLetter() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
        int randomIndex = (int) (Math.random() * alphabet.length());
        return String.valueOf(alphabet.charAt(randomIndex));
    }
}

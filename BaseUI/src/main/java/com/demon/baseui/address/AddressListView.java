package com.demon.baseui.address;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;
import com.demon.baseui.gilde.GlideUtil;
import com.demon.baseui.list.ListUtil;
import com.demon.baseui.list.adapter.GroupAdapter;
import com.demon.baseui.list.holder.DataViewHolder;
import com.demon.baseui.list.holder.GroupViewHolder;
import com.demon.baseui.view.CircleImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddressListView extends FrameLayout implements WordsNavigation.onWordsChangeListener {
    private static final String TAG = "AddressListView";
    private TextView tvLetter;
    private RecyclerView rvAddress;
    private WordsNavigation wnWord;
    private Handler handler;
    private List<AddressPerson> list = new ArrayList<>();
    private Context mContext;
    private GroupAdapter<AddressPerson> adapter;
    private Listener listener;
    private LinearLayoutManager linearLayoutManager;

    public interface Listener {
        void OnItemClickListener(AddressPerson person);
    }

    public AddressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.widget_address, this);
        tvLetter = findViewById(R.id.tv_letter);
        wnWord = findViewById(R.id.wn_words);
        rvAddress = findViewById(R.id.rv_address);
        //设置列表点击滑动监听
        handler = new Handler();
        wnWord.setOnWordsChangeListener(this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        rvAddress.setLayoutManager(linearLayoutManager);
        adapter = new GroupAdapter<AddressPerson>(mContext, R.layout.list_address_group, R.layout.list_address, list) {
            @Override
            public int getViewType(int position) {
                String a = list.get(position).getHeaderWord();
                String b = list.get(position - 1).getHeaderWord();
                return a.equals(b) ? 1 : 0;
            }

            @Override
            public void bindGroup(GroupViewHolder holder, int position, AddressPerson person) {
                String letter = person.getHeaderWord();
                holder.setText(R.id.tv_letter, letter);
            }

            @Override
            public void bindItem(DataViewHolder holder, int position, final AddressPerson person) {
                CircleImageView civAvatar = holder.getView(R.id.civ_avatar);
                GlideUtil.setImage(mContext, person.getIcon(), civAvatar, R.drawable.base_people);
                holder.setText(R.id.tv_name, person.getName());
                holder.setText(R.id.tv_desc, person.getDesc());
                holder.getConvertView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnItemClickListener(person);
                    }
                });
            }
        };
        rvAddress.setAdapter(adapter);
        rvAddress.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                wnWord.setTouchIndex(list.get(linearLayoutManager.findFirstVisibleItemPosition()).getHeaderWord());
            }
        });
    }


    public void setAddressList(List<AddressPerson> personList) {
        list.addAll(personList);
        //对集合排序
        Collections.sort(list, new Comparator<AddressPerson>() {
            @Override
            public int compare(AddressPerson lhs, AddressPerson rhs) {
                //根据拼音进行排序
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
        int length = list.size() - 1;
        List<AddressPerson> noLetterList = new ArrayList<>();
        for (int i = length; i >= 0; i--) {
            AddressPerson ap = list.get(i);
            if (ap.getHeaderWord().equals("#")) {
                list.remove(ap);
                noLetterList.add(0, ap);
            }
        }
        list.addAll(noLetterList);
        adapter.notifyDataSetChanged();
    }

    //手指按下字母改变监听回调
    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < list.size(); i++) {
            String headerWord = list.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表移动到制定位置
                ListUtil.moveToPosition(linearLayoutManager, rvAddress, i);
                return;
            }
        }
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tvLetter.setText(words);
        tvLetter.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //1s后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvLetter.setVisibility(View.GONE);
            }
        }, 500);
    }

    /*设置手指按下字母改变监听*/
    public void setOnItemClickListener(Listener listener) {
        this.listener = listener;
    }

}

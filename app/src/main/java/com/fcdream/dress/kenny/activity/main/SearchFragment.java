package com.fcdream.dress.kenny.activity.main;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.utils.AndroidUtils;

/**
 * Created by shmdu on 2017/10/23.
 */
@BindLayout(layout = R.layout.fragment_search)
public class SearchFragment extends BaseMainPageFragment {

    @BindView(id = R.id.search_edit_text)
    private EditText searchEditText;

    @BindView(id = R.id.search_edit_image, click = true, clickEvent = "dealHandleSearch")
    private ImageView searchImage;

    @Override
    public String getFragmentType() {
        return BaseMainFragmentIface.TYPE_SEARCH;
    }

    @Override
    public String getPageName() {
        return "page_main_search";
    }

    @Override
    protected void initView(Activity activity, View sourceView) {
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                dealSearch();
                return true;
            }
            return false;
        });
    }

    private void dealSearch() {
        String searchText = searchEditText.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            return;
        }
        AndroidUtils.hideSoftInput(getContext(), searchEditText);
        if (isFragmentIfaceValid()) {
            ifaceReference.get().handleSpeech(searchText);
        }
    }

    @Override
    protected void initData(Activity activity) {

    }

    public void dealHandleSearch(View view) {
        dealSearch();
    }
}

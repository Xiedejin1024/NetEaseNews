package com.example.neteasenews.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.databinding.FragmentHomeBinding
import com.example.neteasenews.ui.detail.popuwindow.CustomPopupWindow
import com.example.neteasenews.ui.home.headline.HeadlineFragment
import com.example.neteasenews.ui.home.sport.NativeFragment
import com.example.neteasenews.ui.home.sport.SportsFragment
import com.gyf.immersionbar.ImmersionBar
import com.ogaclejapan.smarttablayout.SmartTabLayout


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private var isShow = false

    private var viewPager: ViewPager? = null

    private var tabLayout: SmartTabLayout? = null

    private lateinit var arrayTitle: Array<String>

    private lateinit var adapter: HeadlineFragmentAdapter

    private val mInfo = ArrayList<FragmentInfo>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorTextPrimary)
            .fitsSystemWindows(true)
            .init()
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        arrayTitle = NetEaseApplication.context.resources.getStringArray(R.array.headTitle)
        for (i in arrayTitle.indices) {
            var fragmentInfo: FragmentInfo
            val titleName = arrayTitle[i]
            when (i) {
                0 -> {
                    fragmentInfo = FragmentInfo(HeadlineFragment(), titleName)
                }

                1 -> {
                    fragmentInfo = FragmentInfo(SportsFragment(), titleName)
                }

                2 -> {
                    fragmentInfo = FragmentInfo(NativeFragment(), titleName)
                }

                else -> {
                    fragmentInfo = FragmentInfo(EmptyFragment(), titleName)
                }
            }
            mInfo.add(fragmentInfo)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        tabLayout = binding.titleBar.tabLayout
        adapter = fragmentManager?.let { HeadlineFragmentAdapter(it, mInfo) }!!
        viewPager?.adapter = adapter
        tabLayout?.setViewPager(viewPager)

        binding.titleBar.ivEditedTitle.setOnClickListener {
            //从底部跳出popuwindow功能
            /* val commentdPopupWindow = CustomPopupWindow.headlineTitlePopupWindow(requireContext())
             commentdPopupWindow.showAsDropDown(binding.titleBar.ivEditedTitle, Gravity.BOTTOM,0,0)
             if (isShow) {//隐藏
                 Animation sort_hide = AnimationUtils . loadAnimation (getContext(), R.anim.ll_sort_hide);
                 ll_sort.startAnimation(sort_hide);
                 sort_hide.setAnimationListener(new Animation . AnimationListener () {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {
                         ll_sort.setVisibility(View.GONE);

                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });

                 Animation add_hide = AnimationUtils . loadAnimation (getContext(), R.anim.add_hide);
                 add_hide.setFillAfter(true);
                 add_view.startAnimation(add_hide);

                 Animation tag_hide = AnimationUtils . loadAnimation (getContext(), R.anim.tag_hide);
                 tag_ll.startAnimation(tag_hide);
                 tag_hide.setAnimationListener(new Animation . AnimationListener () {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {
                         tag_ll.setVisibility(View.GONE);
                         mTvsortBtn.setText("排序删除");
                         mRuleAdapter.setDelect();
                         String showcontent = mRuleAdapter . getContent ();

                         String Noshowcontent = mRuleNoshowAdapter . getContent ();

                         SPutils.putStringValue(
                             getActivity().getApplicationContext(),
                             SHOU_CONTENT,
                             showcontent
                         );
                         SPutils.putStringValue(
                             getActivity().getApplicationContext(),
                             NO_SHOU_CONTENT,
                             Noshowcontent
                         );

                         if (!lastContent.equals(showcontent)) {
                             String[] split = showcontent . split ("-");
                             ArrayList<FragmentInfo> mNewInfo = new ArrayList<>();
                             for (int i = 0; i < split.length; i++) {
                                 FragmentInfo fragmentInfo;
                                 String newstitle = split [i];
                                 if (i == 0) {
                                     fragmentInfo =
                                         new FragmentInfo (new HeadlineFragment (), newstitle);

                                 } else {
                                     fragmentInfo =
                                         new FragmentInfo (new EmptyFragment (), newstitle);
                                 }
                                 mNewInfo.add(fragmentInfo);
                             }
                             mAdapter.updatas(mNewInfo);
                             viewPagerTab.setViewPager(mViewpager);
                         }
                         lastContent = showcontent;
                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });

                 EventBus.getDefault().post(new ShowHideEvent (true));

             } else {
                 Animation add_show = AnimationUtils . loadAnimation (getContext(), R.anim.add_show);
                 add_show.setFillAfter(true);
                 add_view.startAnimation(add_show);
                 ll_sort.setVisibility(View.VISIBLE);
                 Animation sort_show = AnimationUtils . loadAnimation (getContext(), R.anim.ll_sort_show);
                 ll_sort.startAnimation(sort_show);

                 Animation tag_show = AnimationUtils . loadAnimation (getContext(), R.anim.tag_show);
                 tag_ll.setVisibility(View.VISIBLE);
                 tag_show.setFillAfter(true);
                 tag_ll.startAnimation(tag_show);
                 EventBus.getDefault().post(new ShowHideEvent (false));
             }
             isShow = !isShow;*/
        }

    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}
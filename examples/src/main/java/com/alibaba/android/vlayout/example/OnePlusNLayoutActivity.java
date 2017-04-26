/*
 * MIT License
 *
 * Copyright (c) 2016 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.alibaba.android.vlayout.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.RecyclablePagerAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelperEx;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author villadora
 */
public class OnePlusNLayoutActivity extends Activity {

    private static final boolean BANNER_LAYOUT = false;

    private static final boolean LINEAR_LAYOUT = true;

    private static final boolean ONEN_LAYOUT = true;

    private static final boolean GRID_LAYOUT = true;

    private static final boolean STICKY_LAYOUT = true;

    private static final boolean HORIZONTAL_SCROLL_LAYOUT = true;

    private static final boolean SCROLL_FIX_LAYOUT = true;

    private TextView mFirstText;
    private TextView mLastText;

    private TextView mCountText;

    private TextView mTotalOffsetText;

    private Runnable trigger;

    final static Random random = new Random(System.currentTimeMillis());
    private static final String[] images = new String[]{"http://userimage8.360doc.com/16/1112/21/19550642_201611122109150340740202.jpg",
            "http://userimage8.360doc.com/16/1112/21/19550642_201611122109150324791289.jpg",
            "http://userimage8.360doc.com/16/1112/21/19550642_201611122109150496167432.jpg",
            "http://userimage8.360doc.com/16/1112/21/19550642_201611122109150621492559.jpg",
            "http://userimage8.360doc.com/16/1112/21/19550642_201611122109150652638513.jpg",
            "http://userimage8.360doc.com/16/1112/21/19550642_201611122109160324399511.jpg"};

    private static final String[] targetUrls = new String[]{"http://www.baidu.com",
            "http://www.sina.com.cn",
            "http://www.alibaba.com",
            "http://www.taobao.com",
            "http://www.aliexpress.com",
            "http://www.360.cn"};

    private static final String[] titles = new String[]{"淘宝", "阿里巴巴", "聚划算", "天猫"};


    /**
     * POJO数据,写个简单的数据POJO，用来根据不同的类型，绘制不同的数据
     */
    public static class FloorResult {
        List<FloorInfo> floorInfos;

        public static class FloorInfo {
            List<FloorData> floorDataList;
            public String floorType;

            public static class FloorData {
                public String title;
                public String imageUrl;
                public String targetUrl;
            }
        }
    }

    static FloorResult floorResult = new FloorResult();


    static {
        floorResult.floorInfos = new ArrayList<>();
        FloorResult.FloorInfo bannerFloor = new FloorResult.FloorInfo();
        bannerFloor.floorType = "banner";
        bannerFloor.floorDataList = new ArrayList<>();
        // 构建单个Banner数据
        FloorResult.FloorInfo.FloorData floorData1 = new FloorResult.FloorInfo.FloorData();
        floorData1.imageUrl = images[random.nextInt(images.length)];
        floorData1.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData floorData2 = new FloorResult.FloorInfo.FloorData();
        floorData2.imageUrl = images[random.nextInt(images.length)];
        floorData2.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData floorData3 = new FloorResult.FloorInfo.FloorData();
        floorData3.imageUrl = images[random.nextInt(images.length)];
        floorData3.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData floorData4 = new FloorResult.FloorInfo.FloorData();
        floorData4.imageUrl = images[random.nextInt(images.length)];
        floorData4.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        bannerFloor.floorDataList.add(floorData1);
        bannerFloor.floorDataList.add(floorData2);
        bannerFloor.floorDataList.add(floorData3);
        bannerFloor.floorDataList.add(floorData4);
        // 添加BannerFloor
        floorResult.floorInfos.add(bannerFloor);


        FloorResult.FloorInfo titleFloorInfo1 = new FloorResult.FloorInfo();
        titleFloorInfo1.floorType = "title";
        titleFloorInfo1.floorDataList = new ArrayList<>();

        FloorResult.FloorInfo.FloorData titleFloorData1 = new FloorResult.FloorInfo.FloorData();
        titleFloorData1.title = titles[random.nextInt(titles.length)];
        titleFloorData1.imageUrl = images[random.nextInt(images.length)];
        titleFloorData1.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        titleFloorInfo1.floorDataList.add(titleFloorData1);
        // item1FloorInfo.floorDataList.add(itemFloorData7);
        // 这里最多有七个元素,最少5个元素
        floorResult.floorInfos.add(titleFloorInfo1);


        FloorResult.FloorInfo itemFloorInfo = new FloorResult.FloorInfo();
        itemFloorInfo.floorType = "grid";
        itemFloorInfo.floorDataList = new ArrayList<>();

        FloorResult.FloorInfo.FloorData itemFloorData1 = new FloorResult.FloorInfo.FloorData();
        itemFloorData1.imageUrl = images[random.nextInt(images.length)];
        itemFloorData1.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData2 = new FloorResult.FloorInfo.FloorData();
        itemFloorData2.imageUrl = images[random.nextInt(images.length)];
        itemFloorData2.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData3 = new FloorResult.FloorInfo.FloorData();
        itemFloorData3.imageUrl = images[random.nextInt(images.length)];
        itemFloorData3.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData4 = new FloorResult.FloorInfo.FloorData();
        itemFloorData4.imageUrl = images[random.nextInt(images.length)];
        itemFloorData4.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        itemFloorInfo.floorDataList.add(itemFloorData1);
        itemFloorInfo.floorDataList.add(itemFloorData2);
        itemFloorInfo.floorDataList.add(itemFloorData3);
        itemFloorInfo.floorDataList.add(itemFloorData4);

        floorResult.floorInfos.add(itemFloorInfo);


        FloorResult.FloorInfo titleFloorInfo = new FloorResult.FloorInfo();
        titleFloorInfo.floorType = "title";
        titleFloorInfo.floorDataList = new ArrayList<>();

        FloorResult.FloorInfo.FloorData titleFloorData = new FloorResult.FloorInfo.FloorData();
        titleFloorData.title = titles[random.nextInt(titles.length)];
        titleFloorData.imageUrl = images[random.nextInt(images.length)];
        titleFloorData.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        titleFloorInfo.floorDataList.add(titleFloorData);
        // item1FloorInfo.floorDataList.add(itemFloorData7);
        // 这里最多有七个元素,最少5个元素
        floorResult.floorInfos.add(titleFloorInfo);


        FloorResult.FloorInfo item1FloorInfo = new FloorResult.FloorInfo();
        item1FloorInfo.floorType = "one";
        item1FloorInfo.floorDataList = new ArrayList<>();

        FloorResult.FloorInfo.FloorData itemFloorData5 = new FloorResult.FloorInfo.FloorData();
        itemFloorData5.imageUrl = images[random.nextInt(images.length)];
        itemFloorData5.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData6 = new FloorResult.FloorInfo.FloorData();
        itemFloorData6.imageUrl = images[random.nextInt(images.length)];
        itemFloorData6.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData7 = new FloorResult.FloorInfo.FloorData();
        itemFloorData7.imageUrl = images[random.nextInt(images.length)];
        itemFloorData7.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        FloorResult.FloorInfo.FloorData itemFloorData8 = new FloorResult.FloorInfo.FloorData();
        itemFloorData8.imageUrl = images[random.nextInt(images.length)];
        itemFloorData8.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        item1FloorInfo.floorDataList.add(itemFloorData1);
        item1FloorInfo.floorDataList.add(itemFloorData2);
        item1FloorInfo.floorDataList.add(itemFloorData3);
        item1FloorInfo.floorDataList.add(itemFloorData4);
        item1FloorInfo.floorDataList.add(itemFloorData5);
        item1FloorInfo.floorDataList.add(itemFloorData6);
        // item1FloorInfo.floorDataList.add(itemFloorData7);
        // 这里最多有七个元素,最少5个元素
        floorResult.floorInfos.add(item1FloorInfo);


        FloorResult.FloorInfo titleFloorInfo2 = new FloorResult.FloorInfo();
        titleFloorInfo2.floorType = "title";
        titleFloorInfo2.floorDataList = new ArrayList<>();

        FloorResult.FloorInfo.FloorData titleFloorData2 = new FloorResult.FloorInfo.FloorData();
        titleFloorData2.title = titles[random.nextInt(titles.length)];
        titleFloorData2.imageUrl = images[random.nextInt(images.length)];
        titleFloorData2.targetUrl = targetUrls[random.nextInt(targetUrls.length)];
        titleFloorInfo2.floorDataList.add(titleFloorData2);
        // item1FloorInfo.floorDataList.add(itemFloorData7);
        // 这里最多有七个元素,最少5个元素
        floorResult.floorInfos.add(titleFloorInfo2);


        FloorResult.FloorInfo item1FloorInfo1 = new FloorResult.FloorInfo();
        item1FloorInfo1.floorType = "one";
        item1FloorInfo1.floorDataList = new ArrayList<>();

        item1FloorInfo1.floorDataList.add(itemFloorData1);
        item1FloorInfo1.floorDataList.add(itemFloorData2);
        item1FloorInfo1.floorDataList.add(itemFloorData3);
        item1FloorInfo1.floorDataList.add(itemFloorData4);
        item1FloorInfo1.floorDataList.add(itemFloorData5);
        // item1FloorInfo.floorDataList.add(itemFloorData7);
        // 这里最多有七个元素,最少5个元素
        floorResult.floorInfos.add(item1FloorInfo);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mFirstText = (TextView) findViewById(R.id.first);
        mLastText = (TextView) findViewById(R.id.last);
        mCountText = (TextView) findViewById(R.id.count);
        mTotalOffsetText = (TextView) findViewById(R.id.total_offset);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_view);

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText position = (EditText) findViewById(R.id.position);
                if (!TextUtils.isEmpty(position.getText())) {
                    try {
                        int pos = Integer.parseInt(position.getText().toString());
                        recyclerView.scrollToPosition(pos);
                    } catch (Exception e) {
                        Log.e("VlayoutActivity", e.getMessage(), e);
                    }
                } else {
                    recyclerView.requestLayout();
                }
            }
        });


        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                mFirstText.setText("First: " + layoutManager.findFirstVisibleItemPosition());
                mLastText.setText("Existing: " + MainViewHolder.existing + " Created: " + MainViewHolder.createdTimes);
                mCountText.setText("Count: " + recyclerView.getChildCount());
                mTotalOffsetText.setText("Total Offset: " + layoutManager.getOffsetToStart());
            }
        });


        recyclerView.setLayoutManager(layoutManager);

        // layoutManager.setReverseLayout(true);

        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((LayoutParams) view.getLayoutParams()).getViewPosition();
                outRect.set(4, 4, 4, 4);
            }
        };


        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

        recyclerView.setRecycledViewPool(viewPool);

        // recyclerView.addItemDecoration(itemDecoration);

        viewPool.setMaxRecycledViews(0, 20);

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        recyclerView.setAdapter(delegateAdapter);

        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();


        for (final FloorResult.FloorInfo floorInfo : floorResult.floorInfos) {
            if (floorInfo.floorType.equals("banner")) {
                // 这里虽然传递了floorInfo.floorDataList,但是这里并没有用到，因为在PagerAdapter中会重新用到
                adapters.add(new SubImageAdapter(this, new LinearLayoutHelper(), floorInfo.floorDataList) {

                    @Override
                    public void onViewRecycled(MainImageViewHolder holder) {
                        if (holder.itemView instanceof ViewPager) {
                            ((ViewPager) holder.itemView).setAdapter(null);
                        }
                    }

                    @Override
                    public MainImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        if (viewType == 1)
                            return new MainImageViewHolder(
                                    LayoutInflater.from(OnePlusNLayoutActivity.this).inflate(R.layout.view_pager, parent, false));

                        return super.onCreateViewHolder(parent, viewType);
                    }

                    // 定制一个特别的ViewType
                    @Override
                    public int getItemViewType(int position) {
                        return 1;
                    }

                    // Banner 的情况返回Count为1
                    @Override
                    public int getItemCount() {
                        return 1;
                    }


                    @Override
                    protected void onBindViewHolderWithOffset(MainImageViewHolder holder, int position, int offsetTotal) {

                    }

                    @Override
                    public void onBindViewHolder(MainImageViewHolder holder, int position) {
                        if (holder.itemView instanceof ViewPager) {
                            ViewPager viewPager = (ViewPager) holder.itemView;

                            viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));

                            // from position to get adapter
                            viewPager.setAdapter(new PagerAdapter(OnePlusNLayoutActivity.this, this, viewPool, floorInfo.floorDataList));
                        }
                    }
                });
            } else if (floorInfo.floorType.equals("grid")) {
                GridLayoutHelper layoutHelper;
                layoutHelper = new GridLayoutHelper(4);
                layoutHelper.setMargin(0, 10, 0, 10);
                layoutHelper.setHGap(3);
                layoutHelper.setAspectRatio(4f);
                adapters.add(new SubImageAdapter(this, layoutHelper, floorInfo.floorDataList));
            } else if (floorInfo.floorType.equals("one")) {
                OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
                //helper.setBgColor(0xff876384);
                // helper.setMargin(0, 10, 0, 10);
                // helper.setColWeights(new float[]{20f, 80f, 0f, 60f, 20f});
                helper.setAspectRatio(4);
                adapters.add(new SubImageAdapter(OnePlusNLayoutActivity.this, helper, floorInfo.floorDataList));
            } else if (floorInfo.floorType.equals("title")) {
                adapters.add(new SubImageAdapter(this, new LinearLayoutHelper(), floorInfo.floorDataList) {


                    @Override
                    public MainImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        if (viewType == 2)
                            return new MainImageViewHolder(
                                    LayoutInflater.from(OnePlusNLayoutActivity.this).inflate(R.layout.item_title, parent, false));

                        return super.onCreateViewHolder(parent, viewType);
                    }

                    // 定制一个特别的ViewType
                    @Override
                    public int getItemViewType(int position) {
                        return 2;
                    }

                    // Banner 的情况返回Count为1
                    @Override
                    public int getItemCount() {
                        return 1;
                    }


                    @Override
                    protected void onBindViewHolderWithOffset(MainImageViewHolder holder, int position, int offsetTotal) {

                    }

                    @Override
                    public void onBindViewHolder(MainImageViewHolder holder, int position) {
                        TextView tv_title = (TextView) holder.itemView.findViewById(R.id.tv_title);
                        tv_title.setText(floorInfo.floorDataList.get(position).title);
                    }
                });
            }
        }


//        if (BANNER_LAYOUT) {
//            adapters.add(new SubAdapter(this, new LinearLayoutHelper(), 1) {
//
//                @Override
//                public void onViewRecycled(MainViewHolder holder) {
//                    if (holder.itemView instanceof ViewPager) {
//                        ((ViewPager) holder.itemView).setAdapter(null);
//                    }
//                }
//
//                @Override
//                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                    if (viewType == 1)
//                        return new MainViewHolder(
//                                LayoutInflater.from(OnePlusNLayoutActivity.this).inflate(R.layout.view_pager, parent, false));
//
//                    return super.onCreateViewHolder(parent, viewType);
//                }
//
//                @Override
//                public int getItemViewType(int position) {
//                    return 1;
//                }
//
//                @Override
//                protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {
//
//                }
//
//                @Override
//                public void onBindViewHolder(MainViewHolder holder, int position) {
//                    if (holder.itemView instanceof ViewPager) {
//                        ViewPager viewPager = (ViewPager) holder.itemView;
//
//                        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//
//                        // from position to get adapter
//                        viewPager.setAdapter(new PagerAdapter(OnePlusNLayoutActivity.this,this, viewPool,));
//                    }
//                }
//            });
//        }

//        if (GRID_LAYOUT) {
//            GridLayoutHelper layoutHelper;
//            layoutHelper = new GridLayoutHelper(4);
//            layoutHelper.setMargin(0, 10, 0, 10);
//            layoutHelper.setHGap(3);
//            layoutHelper.setAspectRatio(4f);
//            adapters.add(new SubImageAdapter(this, layoutHelper, 8));
//        }

//        if (HORIZONTAL_SCROLL_LAYOUT) {
//
//        }

//        if (GRID_LAYOUT) {
//            GridLayoutHelper layoutHelper;
//            layoutHelper = new GridLayoutHelper(2);
//            layoutHelper.setMargin(0, 10, 0, 10);
//            layoutHelper.setHGap(3);
//            layoutHelper.setAspectRatio(3f);
//            adapters.add(new SubImageAdapter(this, layoutHelper, 2));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
//            // helper.setBgColor(0xff876384);
//            helper.setMargin(10, 10, 10, 10);
//            helper.setPadding(10, 10, 10, 10);
//            adapters.add(new SubImageAdapter(this, helper, 3) {
//                @Override
//                public void onBindViewHolder(MainImageViewHolder holder, int position) {
//                    super.onBindViewHolder(holder, position);
////                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
////                    layoutParams.leftMargin = 10;
////                    layoutParams.topMargin = 10;
////                    layoutParams.rightMargin = 10;
////                    layoutParams.bottomMargin = 10;
////                    holder.itemView.setLayoutParams(layoutParams);
//                }
//            });
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
//            // helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            adapters.add(new SubImageAdapter(this, helper, 4));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
//            // helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            adapters.add(new SubImageAdapter(this, helper, 5));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            // helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            adapters.add(new SubImageAdapter(this, helper, 5));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            helper.setColWeights(new float[]{40f, 45f, 15f, 60f, 0f});
//            adapters.add(new SubImageAdapter(this, helper, 5));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            helper.setColWeights(new float[]{20f, 80f, 0f, 60f, 20f});
//            helper.setAspectRatio(4);
//            adapters.add(new SubImageAdapter(this, helper, 5));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            adapters.add(new SubImageAdapter(this, helper, 6));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            adapters.add(new SubImageAdapter(this, helper, 7));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            helper.setBgColor(0xff876384);
//            helper.setMargin(0, 10, 0, 10);
//            helper.setColWeights(new float[]{40f, 45f, 15f, 60f, 0f, 30f, 30f});
//            adapters.add(new SubImageAdapter(this, helper, 7));
//        }
//
//        if (ONEN_LAYOUT) {
//            OnePlusNLayoutHelperEx helper = new OnePlusNLayoutHelperEx();
//            // helper.setBgColor(0xffed7612);
//            // helper.setMargin(50, 50, 50, 50);
//            // helper.setPadding(50, 50, 50, 50);
//            helper.setColWeights(new float[]{30f, 20f, 50f, 40f, 30f, 35f, 35f});
//            adapters.add(new SubImageAdapter(this, helper, 7) {
//                @Override
//                public void onBindViewHolder(MainImageViewHolder holder, int position) {
//                    super.onBindViewHolder(holder, position);
////                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
////                    layoutParams.leftMargin = 10;
////                    layoutParams.topMargin = 10;
////                    layoutParams.rightMargin = 10;
////                    layoutParams.bottomMargin = 10;
////                    holder.itemView.setLayoutParams(layoutParams);
//                }
//            });
//        }

//        if (STICKY_LAYOUT) {
//            StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
//            layoutHelper.setAspectRatio(4);
//            adapters.add(new SubAdapter(this, layoutHelper, 1, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)));
//        }
//
//        if (SCROLL_FIX_LAYOUT) {
//            ScrollFixLayoutHelper layoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.BOTTOM_RIGHT, 20, 20);
//            layoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_LEAVE);
//            adapters.add(new SubAdapter(this, layoutHelper, 1) {
//                @Override
//                public void onBindViewHolder(MainViewHolder holder, int position) {
//                    super.onBindViewHolder(holder, position);
//                    LayoutParams layoutParams = new LayoutParams(50, 50);
//                    holder.itemView.setLayoutParams(layoutParams);
//                }
//            });
//        }
//
//        if (LINEAR_LAYOUT) {
//            adapters.add(new SubAdapter(this, new LinearLayoutHelper(), 100));
//        }


        delegateAdapter.setAdapters(adapters);

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        trigger = new Runnable() {
            @Override
            public void run() {
                // recyclerView.scrollToPosition(22);
                // recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.requestLayout();
                // mainHandler.postDelayed(trigger, 1000);
            }
        };


        mainHandler.postDelayed(trigger, 1000);
    }

    // RecyclableViewPager

    static class PagerAdapter extends RecyclablePagerAdapter<MainImageViewHolder> {
        private List<FloorResult.FloorInfo.FloorData> floorDataList;
        private Context mContext;

        public PagerAdapter(Context mContext, SubImageAdapter adapter, RecyclerView.RecycledViewPool pool, List<FloorResult.FloorInfo.FloorData> floorDataList) {
            super(adapter, pool);
            this.floorDataList = floorDataList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return floorDataList.size();
        }

        @Override
        public void onBindViewHolder(MainImageViewHolder viewHolder, final int position) {
            // only vertical
            viewHolder.itemView.setLayoutParams(
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView iv = (ImageView) viewHolder.itemView.findViewById(R.id.iv_image);
            Glide
                    .with(mContext)
                    .load(images[random.nextInt(5)])
                    .placeholder(R.drawable.abc_ab_share_pack_holo_dark)
                    .crossFade()
                    .into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(floorDataList.get(position).targetUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }
    }


    static class SubImageAdapter extends DelegateAdapter.Adapter<MainImageViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private LayoutParams mLayoutParams;
        private List<FloorResult.FloorInfo.FloorData> floorDataList;


        public SubImageAdapter(Context context, LayoutHelper layoutHelper, List<FloorResult.FloorInfo.FloorData> floorDataList) {
            this(context, layoutHelper, floorDataList, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubImageAdapter(Context context, LayoutHelper layoutHelper, List<FloorResult.FloorInfo.FloorData> floorDataList, @NonNull LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.floorDataList = floorDataList;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
        }

        @Override
        public void onBindViewHolder(MainImageViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainImageViewHolder holder, final int position, int offsetTotal) {
            ImageView iv = (ImageView) holder.itemView.findViewById(R.id.iv_image);
            Glide
                    .with(mContext)
                    .load(images[random.nextInt(5)])
                    .placeholder(R.drawable.abc_ab_share_pack_holo_dark)
                    .crossFade()
                    .into(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(floorDataList.get(position).targetUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return floorDataList.size();
        }
    }


    static class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }

    static class MainImageViewHolder extends RecyclerView.ViewHolder {

        public MainImageViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
}

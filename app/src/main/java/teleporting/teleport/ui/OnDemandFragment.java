package teleporting.teleport.ui;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teleporting.teleport.Adapter.OnDemandCardViewAdapter;
import teleporting.teleport.Data.OnDemandData;
import teleporting.teleport.R;

public class OnDemandFragment extends AppCompatActivity implements OnDemandCardViewAdapter.ItemClickListener {
    TextView mSource, mDestination;

    private RecyclerView recyclerView;
    private OnDemandCardViewAdapter adapter;
    private List<OnDemandData> mSegmentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_on_demand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Inflate the layout for this fragment
       /* mSource = (TextView)view.findViewById(R.id.source);
        mDestination = (TextView)view.findViewById(R.id.destination);
        mSource.setOnClickListener(this);*/
        initCollapsingToolbar();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mSegmentList = new ArrayList<>();
        adapter = new OnDemandCardViewAdapter(this, mSegmentList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCategories();
/*
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageResource(R.drawable.delivery);*/
    }

    /**
     * Adding few albums for testing
     */
    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.cloths,
                R.drawable.gifts,
                R.drawable.groceries,
                R.drawable.panipuri,
                R.drawable.medicines};

        OnDemandData a = new OnDemandData("Pick my Groceries", "Groceries", covers[0]);
        mSegmentList.add(a);

        a = new OnDemandData("Pick my Mediciens", "Health", covers[1]);
        mSegmentList.add(a);

        a = new OnDemandData("Pick my Gifts", "Gifts", covers[2]);
        mSegmentList.add(a);

        a = new OnDemandData("Pick my food", "Food", covers[3]);
        mSegmentList.add(a);

        a = new OnDemandData("Pick my cloths", "Apparel", covers[4]);
        mSegmentList.add(a);

        mSegmentList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onCliclCardView() {

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

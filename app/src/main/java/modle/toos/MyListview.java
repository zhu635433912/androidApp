package modle.toos;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.deguan.xuelema.androidapp.R;

/**
 *
 */

public class MyListview extends ListView implements AbsListView.OnScrollListener{
    /**
     * 当前滑动的ListView　position
     */
    private int slidePosition;
    /**
     * 手指按下X的坐标
     */
    private int downY;
    /**
     * 手指按下Y的坐标
     */
    private int downX;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * ListView的item
     */
    private View itemView;
    /**
     * 滑动类
     */
    private Scroller scroller;
    private static final int SNAP_VELOCITY = 600;
    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;
    /**
     * 是否响应滑动，默认为不响应
     */
    private boolean isSlide = false;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop;
    /**
     *  移除item后的回调接口
     */
    private RemoveListener mRemoveListener;
    /**
     * 用来指示item滑出屏幕的方向,向左或者向右,用一个枚举值来标记
     */
    private RemoveDirection removeDirection;

    // 滑动删除方向的枚举值
    public enum RemoveDirection {
        RIGHT, LEFT;
    }

    View footel;//底部布局
    View hiade;//顶部布局文件
    int hedeerHeinght;//顶部布局文件的高度
    int firstVisibleItem;//当前第一个可见的itme位置
    int scrollState;//当前滚动状态

    boolean isRemark;//标记，当前是在listview最顶端按下的
    int startY;//开始的Y轴值


    int state;//当前状态
    final int NONE=0;//正常状态
    final int PULL=1;//提示下拉状态
    final int RELESE=2;//提示释放状态
    final int REFLASHING=3;//刷新状态

    IReflashListener iReflashListenerl;//刷新数据接口
    private IReflashListener iReflashListener;

    public MyListview(Context context) {
        super(context);
        initView(context);
    }
    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //初始化界面添加布局文件到listview
    public void initView(Context context){
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        //获取布局文件
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取底部布局
        footel= inflater.inflate(R.layout.llistviewtop, null);
                //获取顶部布局
        hiade = inflater.inflate(R.layout.llistviewtop, null);
        //获取顶部布局高度
        measureView(hiade);
        hedeerHeinght=hiade.getMeasuredHeight();
        topPadding(-hedeerHeinght);

        this.addHeaderView(hiade);
        //设置滚动监听
        this.setOnScrollListener(this);
    }

    //设置listview布局的上边距
    private void topPadding(int topadding){
        hiade.setPadding(hiade.getPaddingLeft(),topadding,hiade.getPaddingRight(),hiade.getPaddingBottom() );
        hiade.invalidate();
    }


    /*
    通知父布局，占用的宽高.
     */
    private void measureView(View view){
        ViewGroup.LayoutParams p=view.getLayoutParams();
        if (p==null){
            p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //获取子布局宽度
        int width=ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight=p.height;
        if (tempHeight>0){
            height=MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else {
            height=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }




    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState=scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;
    }

        //删除监听
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                addVelocityTracker(ev);

                // 假如scroller滚动还没有结束，我们直接返回
                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(ev);
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();

                slidePosition = pointToPosition(downX, downY);

                // 无效的position, 不做任何处理
                if (slidePosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }

                // 获取我们点击的item view
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
                        || (Math.abs(ev.getX() - downX) > mTouchSlop && Math
                        .abs(ev.getY() - downY) < mTouchSlop)) {
                    isSlide = true;

                }
                break;
            }
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 往右滑动，getScrollX()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离，所以向右边滑动为负值
     */
    private void scrollRight() {
        removeDirection = RemoveDirection.RIGHT;
        final int delta = (screenWidth + itemView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新itemView
    }
    /**
     * 向左滑动，根据上面我们知道向左滑动为正值
     */
    private void scrollLeft() {
        removeDirection = RemoveDirection.LEFT;
        final int delta = (screenWidth - itemView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新itemView
    }

    /**
     * 根据手指滚动itemView的距离来判断是滚动到开始位置还是向左或者向右滚动
     */
    private void scrollByDistanceX() {
        // 如果向左滚动的距离大于屏幕的二分之一，就让其删除
        if (itemView.getScrollX() >= screenWidth / 2) {
            scrollLeft();
        } else if (itemView.getScrollX() <= -screenWidth / 2) {
            scrollRight();
        } else {
            // 滚回到原始位置,为了偷下懒这里是直接调用scrollTo滚动
            itemView.scrollTo(0, 0);
        }

    }
    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

            postInvalidate();

            // 滚动动画结束的时候调用回调接口
            if (scroller.isFinished()) {
                if (mRemoveListener == null) {

                   // throw new NullPointerException("RemoveListener is null, we should called setRemoveListener()");
                }

                itemView.scrollTo(0, 0);
                mRemoveListener.removeItem(removeDirection, slidePosition);
            }
        }
    }
    /**
     * 添加用户的速度跟踪器
     *
     * @param event
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    /**
     * 移除用户速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
    /**
     * 获取X方向的滑动速度,大于0向右滑动，反之向左
     *
     * @return
     */
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return velocity;
    }

    //简单滑动
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(ev);
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:

                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
                    itemView.scrollBy(deltaX, 0);

                    return true;  //拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:
                    int velocityX = getScrollVelocity();
                    if (velocityX > SNAP_VELOCITY) {
                        scrollRight();
                    } else if (velocityX < -SNAP_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistanceX();
                    }

                    recycleVelocityTracker();
                    // 手指离开的时候就不响应左右滚动
                    isSlide = false;
                    break;
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    // 加载最新数据；
                    reflashViewByState();
                    iReflashListener.onReflash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //判断移动过程中的操作
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - hedeerHeinght;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > hedeerHeinght + 30
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < hedeerHeinght + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }


    //刷新数据结束接口
    public void reflashComplet(){
        state=NONE;
        isRemark=false;
        reflashViewByState();
    }
    /**
     * 根据当前状态，改变界面显示；
     */
    private void reflashViewByState() {
        switch (state) {
            case NONE:
                topPadding(-hedeerHeinght);
                break;
            case PULL:
                break;
            case RELESE:

                break;
            case REFLASHING:
                topPadding(50);
                break;
        }
    }

    public void setInterface(IReflashListener iReflashListener){
        this.iReflashListener = iReflashListener;
    }

    /**
     * 刷新数据接口
     * @author Administrator
     */
    public interface IReflashListener{
        public void onReflash();
    }

    /**
     * 设置滑动删除的回调接口
     * @param removeListener
     */
    public void setRemoveListener(RemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }
    /**
     *
     * 当ListView item滑出屏幕，回调这个接口
     * 我们需要在回调方法removeItem()中移除该Item,然后刷新ListView
     *
     * @author xiaanming
     *
     */
    public interface RemoveListener {
        public void removeItem(RemoveDirection direction, int position);
    }
}

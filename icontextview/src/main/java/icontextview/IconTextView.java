package icontextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.icontextview.R;


/**
 * Created by Administrator on 2018/7/27.
 */

public class IconTextView extends LinearLayout{
    /**
     * 图标在左边
     */
    public final static int ICON_AT_LEFT = 0;

    /**
     * 图标在上面
     */
    public final static int ICON_AT_TOP = 1;

    /**
     *
     * 图标在下面
     */
    public final static int ICON_AT_BOTTOM = 2;

    /**
     * 图标在右边
     */
    public final static int ICON_AT_RIGHT = 3;

    private int mIconAt;
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private Drawable mDrawable;
    private float mIconMarginLeft;
    private float mIconMarginRight;
    private float mIconMarginTop;
    private float mIconMarginBottom;

    private ImageView mImageView;
    private TextView mTextView;

    private Context mContext;

    public IconTextView(Context context) {
        this(context,null);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.IconTextView);
        mIconAt = typedArray.getInt(R.styleable.IconTextView_iconAt,ICON_AT_LEFT);
        mText = typedArray.getString(R.styleable.IconTextView_text);
        if(mText == null){
            mText = "";
        }
        mTextSize = typedArray.getDimension(R.styleable.IconTextView_textSize,0);
        mTextColor = typedArray.getColor(R.styleable.IconTextView_textColor, Color.DKGRAY);
        mDrawable = typedArray.getDrawable(R.styleable.IconTextView_src);
        mIconMarginLeft = typedArray.getDimension(R.styleable.IconTextView_iconPaddingLeft,0);
        mIconMarginRight = typedArray.getDimension(R.styleable.IconTextView_iconPaddingRight,0);
        mIconMarginTop = typedArray.getDimension(R.styleable.IconTextView_iconPaddingTop,0);
        mIconMarginBottom = typedArray.getDimension(R.styleable.IconTextView_iconPaddingBottom,0);
        typedArray.recycle();

        mImageView = new ImageView(mContext);
        mTextView = new TextView(mContext);

        if(mDrawable != null){
            mImageView.setImageDrawable(mDrawable);
        }
        mImageView.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);

        mTextView.setText(mText);
        if(mTextSize != 0){
            mTextView.setTextSize(mTextSize);
        }
        mTextView.setTextColor(mTextColor);
        mTextView.setEllipsize(TextUtils.TruncateAt.END);
        mTextView.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);

        //虽然IconTextView是继承于LinearLayout,但还是不应视为ViewGroup类型控件
        //所以移除xml上的子控件
        if(getChildCount() > 0){
            removeAllViews();
        }

        addChild();
    }

    private void addChild(){
        switch(mIconAt) {
            case ICON_AT_LEFT:
            case ICON_AT_TOP:
                addView(mImageView);
                addView(mTextView);
                break;

            case ICON_AT_RIGHT:
            case ICON_AT_BOTTOM:
                addView(mTextView);
                addView(mImageView);
                break;
        }
        setLayoutParams();
    }

    private void setLayoutParams(){
        LayoutParams iconParams = (LayoutParams) mImageView.getLayoutParams();
        LayoutParams textParams = (LayoutParams) mTextView.getLayoutParams();

        switch(mIconAt){
            case ICON_AT_LEFT:
            case ICON_AT_RIGHT:
                setOrientation(LinearLayout.HORIZONTAL);
                textParams.gravity = Gravity.CENTER_VERTICAL;
                iconParams.gravity = Gravity.CENTER_VERTICAL;
                break;

            case ICON_AT_TOP:
            case ICON_AT_BOTTOM:
                setOrientation(LinearLayout.VERTICAL);
                textParams.gravity = Gravity.CENTER_HORIZONTAL;
                iconParams.gravity = Gravity.CENTER_HORIZONTAL;
                break;
        }
        mTextView.setLayoutParams(textParams);
        mImageView.setLayoutParams(iconParams);

        iconParams.leftMargin = (int) mIconMarginLeft;
        iconParams.rightMargin = (int) mIconMarginRight;
        iconParams.topMargin = (int) mIconMarginTop;
        iconParams.bottomMargin = (int) mIconMarginBottom;
        mImageView.setLayoutParams(iconParams);
    }

    /**
     * 获取负责显示文本的控件
     * @return
     */
    public TextView getTextView(){
        return mTextView;
    }

    /**
     * 获取负责显示图标的控件
     * @return
     */
    public ImageView getImageView(){
        return mImageView;
    }
}

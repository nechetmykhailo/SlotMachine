package com.mypc.slotmachine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mypc.slotmachine.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.adapters.AbstractWheelAdapter;

public class SlotMachineAdapter extends AbstractWheelAdapter {
    // Image size
    final int IMAGE_WIDTH = 60;
    final int IMAGE_HEIGHT = 36;
    private final int items[] = new int[]{
            R.mipmap.combination_1,
            R.mipmap.combination_2,
            R.mipmap.combination_3,
            R.mipmap.combination_4,
            R.mipmap.combination_5,
            R.mipmap.combination_6,
            R.mipmap.combination_7
    };
    private List<SoftReference<Bitmap>> images;
    private Context context;

    public SlotMachineAdapter(Context context) {
        this.context = context;
        images = new ArrayList<>(items.length);
        for (int id : items) {
            images.add(new SoftReference<>(loadImage(id)));
        }
    }

    private Bitmap loadImage(int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
        bitmap.recycle();
        return scaled;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }

    // Layout params for image view
    final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        ImageView img;
        if (cachedView != null) {
            img = (ImageView) cachedView;
        } else {
            img = new ImageView(context);
        }
        img.setLayoutParams(params);
        SoftReference<Bitmap> bitmapRef = images.get(index);
        Bitmap bitmap = bitmapRef.get();
        if (bitmap == null) {
            bitmap = loadImage(items[index]);
            images.set(index, new SoftReference<>(bitmap));
        }
        img.setImageBitmap(bitmap);

        return img;
    }
}
